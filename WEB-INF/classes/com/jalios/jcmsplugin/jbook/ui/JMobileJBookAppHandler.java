package com.jalios.jcmsplugin.jbook.ui;

import java.util.Set;

import com.jalios.jcms.Category;
import com.jalios.util.Util;

import generated.Book;

public class JMobileJBookAppHandler extends JBookAppHandler {

	  protected static final String APP_URL = "plugins/JBookPlugin/jmobile/app/jbook.jsp";

	  protected boolean hasBorrowings = false;

	  @Override
	  public void init() {

	    hasBorrowings = mgr.getCurrentBorrowingCount(loggedMember) > 0;
	    if (view == null) {
	      view = getDefaultView();
	    }

	    super.init();

	    Category topicRoot = mgr.getTopicRoot();
	    if (getSelectedTopic() == null && topicRoot != null) {
	      setCids(topicRoot.getId());
	    }

	  }

	  protected View getDefaultView() {
	    if (hasBorrowings) {
	      return View.MY_BORROWINGS;
	    }

	    return View.CATALOG;
	  }

	  public boolean hasBorrowings() {
	    return hasBorrowings;
	  }

	  public boolean showParentLink() {
	    if (!showCategories()) {
	      return false;
	    }

	    Category currTopic = getSelectedTopic();
	    if (currTopic == null) {
	      return false;
	    }

	    if (currTopic == mgr.getTopicRoot()) {
	      return false;
	    }

	    return true;
	  }

	  public String getParentName() {
	    Category currTopic = getSelectedTopic();
	    if (currTopic == null) {
	      return null;
	    }

	    return currTopic.getName(userLang);
	  }

	  public String getParentUrl() {
	    Category currTopic = getSelectedTopic();
	    if (currTopic == null) {
	      return null;
	    }

	    return getAppUrl(currTopic.getParent());
	  }

	  public boolean showCategories() {
	    return Util.isEmpty(getText());
	  }

	  public String getMyBorrowingsUrl() {
	    return APP_URL + "?view=" + View.MY_BORROWINGS;
	  }


	  public String getCatalogUrl() {
	    return APP_URL + "?view=" + View.CATALOG;
	  }

	  public String getAppUrl(Category cat) {
	    return getCatalogUrl() + "&amp;cids=" + cat.getId();
	  }

	  public Set<Book> getBookSet() {
	    Set set = getSortedResultSet();
	    return set;
	  }
	}
