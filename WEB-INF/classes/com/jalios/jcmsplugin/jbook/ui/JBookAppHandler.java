package com.jalios.jcmsplugin.jbook.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;

import com.jalios.jcms.Category;
import com.jalios.jcms.JcmsUtil;
import com.jalios.jcms.Member;
import com.jalios.jcms.Publication;
import com.jalios.jcms.QueryResultSet;
import com.jalios.jcms.comparator.ComparatorManager;
import com.jalios.jcms.handler.QueryHandler;
import com.jalios.jcms.taglib.settings.BasicSettings;
import com.jalios.jcms.taglib.settings.ControlSettings;
import com.jalios.jcms.taglib.settings.impl.DateSettings;
import com.jalios.jcms.taglib.settings.impl.EnumerateSettings;
import com.jalios.jcms.taglib.settings.impl.MemberSettings;
import com.jalios.jcms.uicomponent.BreadcrumbItem;
import com.jalios.jcms.uicomponent.DataAttribute;
import com.jalios.jcmsplugin.jbook.JBookManager;
import com.jalios.jcmsplugin.jbook.data.JBookBorrowing;
import com.jalios.util.Util;

import generated.Book;

public class JBookAppHandler extends QueryHandler  {
  
  protected JBookManager mgr = JBookManager.getInstance();
  
  protected QueryResultSet resultSet;
  protected Book book;
  protected Category topic;
  protected boolean showTopics;
  protected View view = View.CATALOG;
  protected Category topicRoot;
  protected boolean refineWorkspace;
  protected boolean isCollaborativeSpace;
  
  @Override
  public void init() {
    isCollaborativeSpace = getWorkspace().isCollaborativeSpace();
    initTopic();
    initQuery();
  }

  private void initTopic() {
    if (topicRoot == null) {
      topicRoot = mgr.getTopicRoot();
    }
    
    Set<Category> catSet = getCategorySet("cids");
    
    if (Util.isEmpty(catSet)) {
      setCids(JcmsUtil.getId(topicRoot));
      catSet = getCategorySet("cids");
    }
    
    topic = Util.getFirst(catSet);
    
    showTopics = 
        Util.isEmpty(getText()) && 
        Util.isEmpty(getMids()) && 
        getBeginDate() == null &&
        getEndDate() == null;

  }
  
  private void initQuery() {
    setTypes("generated.Book");   
    setDateType("pdate");
    setSort("title");
    if (showTopics) {
      setExactCat(showTopics);
    }
    
    if (refineWorkspace) {
      setWorkspace(workspace); 
    }
    
    if (getPageSize() == 10) {
      setPageSize(12);
    }

  }
  
  public boolean showAppTitle() {
    return !isCollaborativeSpace;
  }
  
  public String getAppTitle() {          

    switch(view) {

    case MY_BORROWINGS:
      return glp("jcmsplugin.jbook.app.view.my-borrowings");

    case ALL_BORROWINGS:
      return glp("jcmsplugin.jbook.app.view.all-borrowings");

    default:
    case CATALOG:
      return glp("jcmsplugin.jbook.app.catalog.title");
    }

  }
  
  public String getAppUrl() {
    return "plugins/JBookPlugin/jsp/app/jbook.jsp";
  }
  
  public String getAppUrl(Category cat) {
    
    StringBuilder sb = new StringBuilder(mgr.getAppUrlPrefix(workspace, userLocale));

    if (Util.notEmpty(getText())) {
      sb.append("text=" + getText() + "&amp;");
    }
    
    if (cat != null) {
      sb.append("cids=" + cat.getId());
    }
    
    return sb.toString();
  }
  
  public String getCatalogUrl() {
    return getViewUrl(View.CATALOG);
  }
  
  public String getMyBorrowingsUrl() {
    return getViewUrl(View.MY_BORROWINGS);
  }
  
  public String getAllBorrowingsUrl() {
    return getViewUrl(View.ALL_BORROWINGS);
  }  
  
  private String getViewUrl(View view) {
    return getAppUrl() + "?view=" + view;
  }  

  @Override
  public QueryResultSet getResultSet() {
    if (resultSet == null) {
      resultSet = super.getResultSet();
    }
    return resultSet;
  }
  
  public SortedSet<Publication> getSortedResultSet() {
    Comparator comparator = ComparatorManager.getComparator(Publication.class, getSort());
    return getResultSet().getAsSortedSet(comparator);
  } 
  
  
  public boolean showAddBook() {
    
    if (!isLogged) {
      return false;
    }
    
    return loggedMember.canPublishSome(Book.class);
  }
  
  public String getAddBookUrl() {
    String params = "";
    Category topic = getSelectedTopic();
    if (topic != null) {
      params = "?cids=" + topic.getId();
    }
    return "types/Book/editBookModal.jsp" + params;
  }

  public String getEditBookUrl() {
    if (book == null) {
      return null;
    }
    return "types/Book/editBookModal.jsp?id=" + book.getId();
  }



  public boolean showBook() {
    return getSelectedBook() != null;
    
  }
  
  public Book getSelectedBook() {
    return book;
  }
  
  public void setBook(String id) {
    book = channel.getData(Book.class, id);
  }

  // --------------------------------------------------------
  // Catalog Management
  // --------------------------------------------------------
  
  public boolean showCatalog() {
    return view == View.CATALOG && !showBook();
  }

  public boolean showMyBorrowings() {
    return view == View.MY_BORROWINGS && !showBook();
  }
  
  public boolean showAllBorrowings() {
    return view == View.ALL_BORROWINGS && !showBook();
  }

  public boolean showFilters() {
    return view == View.CATALOG;
  }
  
  public boolean showNoResult() {
    return Util.isEmpty(getResultSet()) && !showTopics();
  }
  
  public boolean showTopics() {
    return showTopics;
  }
  
  public Set<Category> getTopicSet() {
    Category topic = getSelectedTopic();
    if (topic != null) {
      return topic.getChildrenSet();
    }
    Category root = topicRoot;
    if (root == null) {
      return null;
    }
    return root.getChildrenSet();
  }
  
  // ------------------------------------------------------------------
  // Breadcrumbs
  // ------------------------------------------------------------------

  public List<BreadcrumbItem> getBreadcrumbItems() {
    List<BreadcrumbItem> items = new ArrayList<>();

    items.add(
        new BreadcrumbItem()
        .label(glp("jcmsplugin.jbook.app.catalog.root"))
        .url(getAppUrl() + "?view=" + View.CATALOG)
        .attributes(new DataAttribute().addData("data-jalios-action", "ajax-refresh")));
    
    List<Category> breadcrumb = getBreadCrumbCatList();
    if (Util.isEmpty(breadcrumb)) {
      return items;
    }
    
    for(Category cat: breadcrumb) {
      items.add(
          new BreadcrumbItem()
          .label(cat.getName(userLang))
          .url(getAppUrl(cat))
          .attributes(new DataAttribute().addData("data-jalios-action", "ajax-refresh")));
    }
    
    return items;
  }
  
  private List<Category> getBreadCrumbCatList() {
    Category currentCat = getSelectedTopic();
    if (currentCat == null || currentCat == topicRoot) {
      return Collections.emptyList();
    }
    
    List<Category> list = currentCat.getAncestorList(topicRoot, false);
    Collections.reverse(list);
    list.add(currentCat);
    
    return list;
  }
  
  
  public Category getSelectedTopic() {
    return topic;
  }

  public void setTopicRoot(Category c) {
    topicRoot = c;
  }
  
  public Category getTopicRoot() {
    return topicRoot;
  }

  public void setRefineWorkspace(String v) {
    refineWorkspace = Util.toBoolean(v, false);
  }
  
  // --------------------------------------------------------
  // Borrowing
  // --------------------------------------------------------
  
  public List<JBookBorrowing> getAllBorrowingList() {
    if (refineWorkspace) {
      return mgr.getAllCurrentBorrowingList(getWorkspace());
    }
    return mgr.getAllCurrentBorrowingList();
  }
  
  public List<JBookBorrowing> getMyBorrowingList() {   
    if (refineWorkspace) {
      return mgr.getCurrentBorrowingList(loggedMember, getWorkspace());
    }
    return mgr.getCurrentBorrowingList(loggedMember);
  }
  

  // --------------------------------------------------------
  // Filter settings
  // --------------------------------------------------------

  public MemberSettings getMemberSettings() {
    Member mbr = channel.getMember(Util.getFirst(getMids()));
    MemberSettings settings = new MemberSettings()
        .name("mids")
        .value(mbr)
        .placeholder("jcmsplugin.jbook.app.filter.mbr")
        .onChange("ajax-refresh");
     
    // Hide the clear button if there's no value
    if (mbr == null) {
      settings.addOption(BasicSettings.HIDE_CLEAR_BUTTON, Boolean.TRUE);
    }
    
    return settings;
  }
  

  public DateSettings getBeginDateSettings() {
    Date beginDate = getBeginDate();
    DateSettings settings = new DateSettings()
    .name("beginDateStr")
    .value(beginDate)
    .placeholder("jcmsplugin.jbook.app.filter.begin-date")
    .onChange("ajax-refresh");
    
    // Hide the clear button if there's no value
    if (beginDate == null) {
      settings.addOption(BasicSettings.HIDE_CLEAR_BUTTON, Boolean.TRUE);
    }
    
    return settings;
  }

  public DateSettings getEndDateSettings() {
    Date endDate = getEndDate();
    DateSettings settings = new DateSettings()
    .name("endDateStr")
    .value(endDate)
    .placeholder("jcmsplugin.jbook.app.filter.end-date")
    .onChange("ajax-refresh");
    
    // Hide the clear button if there's no value
    if (endDate == null) {
      settings.addOption(BasicSettings.HIDE_CLEAR_BUTTON, Boolean.TRUE);
    }

    return settings;
  }
  
  // --------------------------------------------------------
  // View Management
  // --------------------------------------------------------

  public enum View {
    CATALOG, MY_BORROWINGS, ALL_BORROWINGS;
  }
  
  public void setView(String v) {
    try {
      this.view = View.valueOf(v);
    }
    catch(IllegalArgumentException ignore) {
      // EMPTY
    }
  }

  public boolean showViews() {
    return !isCollaborativeSpace;
  }
  
  public ControlSettings getViewSettings() {
    EnumerateSettings settings = new EnumerateSettings()
        .name("view")
        .value(view)
        .enumLabels("jcmsplugin.jbook.app.view.catalog", "jcmsplugin.jbook.app.view.my-borrowings", "jcmsplugin.jbook.app.view.all-borrowings")
        .enumValues(View.CATALOG.toString(), View.MY_BORROWINGS.toString(), View.ALL_BORROWINGS.toString())
        .onChange("ajax-refresh");

    return settings;
  }  
  

}
