package com.jalios.jcmsplugin.jbook.component;

import java.util.Set;

import com.jalios.jcms.Category;
import com.jalios.jcms.JcmsUtil;
import com.jalios.jcms.Member;
import com.jalios.jcms.categorybrowser.CategoryBrowser;
import com.jalios.jcms.categorybrowser.CategoryBrowserContext;
import com.jalios.jcms.portlet.PortletCollection;
import com.jalios.jcmsplugin.jbook.JBookManager;
import com.jalios.jcmsplugin.jbook.data.AbstractBook;

import generated.PortletJBook;

public class JBookCategoryBrowser implements CategoryBrowser {

  private static final String URL_ATTR = "jbook.url";

  private JBookManager mgr = JBookManager.getInstance();

  @Override
  public boolean accept(CategoryBrowserContext ctx) {
    if (!(ctx.getPublication() instanceof AbstractBook)) {
      return false;
    }

    PortletCollection cs = ctx.getPublication().getWorkspace().getCollaborativeSpace();
    Category cat = ctx.getCategory();
    Member mbr = ctx.getMember();

    // Search in topic root
    if (cs == null) {
      if (cat.hasAncestor(mgr.getTopicRoot())) {
        ctx.setAttribute(URL_ATTR, "plugins/JBookPlugin/jsp/app/jbook.jsp?cids=" + cat.getId());
        return true;
      }
      return false;
    }
    else {
      PortletJBook portlet = cs.findFirstPortlet(PortletJBook.class);
      if (portlet != null) {
        Set<Category> topicRootSet = portlet.getTopicRoot(mbr);
        if (topicRootSet != null) {
          for(Category root: topicRootSet) {
            if (cat.hasAncestor(root)) {
              ctx.setAttribute(URL_ATTR, cs.getDisplayUrl(mbr.getLocale()) + "?portlet=" + portlet.getId() + "&cids=" + cat.getId());
              return true;
            }
          }
        }
        else {
          if (cat.hasAncestor(mgr.getTopicRoot())) {
            ctx.setAttribute(URL_ATTR, cs.getDisplayUrl(mbr.getLocale()) + "?portlet=" + portlet.getId() + "&cids=" + cat.getId());
            return true;
          }
        }
      }
    }

    return false;
  }

  @Override
  public String getUrl(CategoryBrowserContext ctx) {
    return (String)ctx.getAttribute(URL_ATTR);
  }

  @Override
  public String getLabel(CategoryBrowserContext ctx) {
    return JcmsUtil.glp(ctx.getLanguage(), "category.category-browser.jbook");
  }

  @Override
  public String getIcon(CategoryBrowserContext ctx) {
    return "jcmsplugin-jbook";
  }

}
