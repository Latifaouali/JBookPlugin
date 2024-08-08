package com.jalios.jcmsplugin.jbook.data;

import static com.jalios.jcmsplugin.jbook.data.JBookBorrowing.BOOK_ID_FIELD;
import static com.jalios.jcmsplugin.jbook.data.JBookBorrowing.BORROWER_ID_FIELD;
import static com.jalios.jcmsplugin.jbook.data.JBookBorrowing.BORROWING_DATE_FIELD;
import static com.jalios.jcmsplugin.jbook.data.JBookBorrowing.RELEASE_DATE_FIELD;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.jalios.jcms.Member;
import com.jalios.jcms.db.AbstractDBQueryBuilder;
import com.jalios.jcms.db.DBConstants;
import com.jalios.jcms.db.HibernateUtil;
import com.jalios.jcms.workspace.Workspace;

import generated.Book;

public class JBookBorrowingQueryBuilder extends AbstractDBQueryBuilder<JBookBorrowing> {

  private AbstractBook book;
  private Member borrower;
  private boolean current;
  private boolean released;
  private Workspace workspace;

  
  public JBookBorrowingQueryBuilder() {
    setDefaultOrder(Order.desc(BORROWING_DATE_FIELD));
  }
  
  @Override
  protected Class<JBookBorrowing> getDBDataClass() {
    return JBookBorrowing.class;
  }
  
  public JBookBorrowingQueryBuilder book(AbstractBook book) {
    this.book = book;
    return this;
  }
  
  public JBookBorrowingQueryBuilder borrower(Member borrower) {
    this.borrower = borrower;
    return this;
  }
  
  public JBookBorrowingQueryBuilder workspace(Workspace workspace) {
    this.workspace = workspace;
    return this;
  }
  
  public JBookBorrowingQueryBuilder current() {
    this.current = true;
    return this;
  }
  
  public JBookBorrowingQueryBuilder released() {
    this.released = true;
    return this;
  }

  public JBookBorrowingQueryBuilder orderByBorrowingDate() {
    return (JBookBorrowingQueryBuilder)(orderBy(Order.desc(BORROWING_DATE_FIELD)));
  }
  
  @Override
  protected void addCriterions(Criteria criteria) {
  
    addBookCriterion(criteria);
    addBorrowerCriterion(criteria);
    addCurrentCriterion(criteria);  
    addReleasedCriterion(criteria);
    addWorkspaceCriterion(criteria);
  }
  
  private void addBookCriterion(Criteria criteria) {
    if (book != null) {
      criteria.add(Restrictions.eq(BOOK_ID_FIELD, book.getId()));
    }
  }

  private void addBorrowerCriterion(Criteria criteria) {
    if (borrower != null) {
      criteria.add(Restrictions.eq(BORROWER_ID_FIELD, borrower.getId()));
    }
  }

  private void addCurrentCriterion(Criteria criteria) {
    if (current) {
      criteria.add(Restrictions.isNull(RELEASE_DATE_FIELD));
    }
  }

  private void addReleasedCriterion(Criteria criteria) {
    if (released) {
      criteria.add(Restrictions.isNotNull(RELEASE_DATE_FIELD));
    }
  }

  
  private void addWorkspaceCriterion(Criteria criteria) {
    if (workspace != null) {
      criteria.add(getWorkspaceCriterion());
    }
  }

  private Criterion getWorkspaceCriterion() {
	    // Retreive all the book ids in this workspace
	    List<String> bookIdList = Collections.emptyList();
	    Criteria bookCriteria = HibernateUtil.createCriteria(AbstractBook.class);
	    bookCriteria.setProjection(Projections.property(DBConstants.ROW_ID_FIELD));
	    bookCriteria.add(Restrictions.eq(DBConstants.WORKSPACE_ID_FIELD, workspace.getId()));
	    List<Long> bookRowIdList = bookCriteria.list();
	    if (bookRowIdList != null) {
	      bookIdList = bookRowIdList.stream()
	          .map(rowId -> HibernateUtil.buildJcmsId(Book.class, rowId))
	          .collect(Collectors.toList());
	    }

	    // Add a restriction on these book ids
	    return HibernateUtil.buildInCriterion(BOOK_ID_FIELD, bookIdList);
	  }

}
