package com.jalios.jcmsplugin.jbook.data;

import com.jalios.jcms.Content;
import com.jalios.jcms.Member;
import com.jalios.jcmsplugin.jbook.JBookManager;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public abstract class AbstractBook extends Content {
  private transient JBookManager mgr = JBookManager.getInstance();
  
  public AbstractBook() {}
  
  public AbstractBook(AbstractBook other) {
    super(other);
  }
  
  public JBookBorrowing getCurrentBorrowing() {
    return this.mgr.getCurrentBorrowing(this);
  }
  
  public List<JBookBorrowing> getBorrowingList() {
    return this.mgr.getBorrowingList(this);
  }
  
  public Set<Member> getPreviousBorrowerSet() {
    return this.mgr.getPreviousBorrowerSet(this);
  }
  
  public abstract String getDescription(String paramString);
  
  public abstract String getImage();
  
  public String getAppDisplayUrl(Locale userLocale) {
	  return JBookManager.getInstance().getAppUrlPrefix(workspace, userLocale) + "book=" + getId();
	}
}


