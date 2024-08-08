<%@page import="com.jalios.jcmsplugin.smartphone.SmartPhoneManager"%>
<%@page import="com.jalios.jcmsplugin.jbook.*"%>
<%@page import="com.jalios.jcmsplugin.jbook.data.*"%>
<%@ page contentType="text/html; charset=UTF-8" %><%
%><%@page import="com.jalios.jcms.taglib.list.ListItemTag"%><%
%><%@ include file="/plugins/SmartPhonePlugin/jsp/components/list/doInitMobileDataListItem.jspf" %><%
css += " jmobile-jbook-borrowing";
JBookBorrowing borrowing = (JBookBorrowing) data;
AbstractBook book = borrowing.getBook();
%>
<% if (book != null) { %>
<jalios:listItem
    css="<%= css %>"
    dataAttribute="<%= dataAttribute %>"
    icon="<%= icon %>"
    link="<%= book.getDisplayUrl(userLocale) %>"
    linkCss="<%= linkCss %>"
    linkDataAttribute="<%= linkDataAttribute %>"
    template="jmobile-detailed"
    text="<%= book.getTitle(userLang) %>">
  <jalios:buffer name="LIST_ITEM_IMAGE">
    <span class="itemlist-item-image">
    <% String image = book.getDataImage(); %>
    <% if (Util.notEmpty(image)) { %>
    <jalios:thumbnail css="is-object-fit" path="<%= image %>" square="true"  width="60" height="60" />
    <% } else { %>
    <jalios:thumbnail css="is-object-fit" path="images/jalios/layout/defaultDataListItemImage.png" square="true"  width="60" height="60" />
    <% } %>
    </span>
  </jalios:buffer>

   <jalios:buffer name="LIST_ITEM_META">
    <span class="borrowing-info">
      <span class="itemlist-item-date"><%= glp("jcmsplugin.jbook.lbl.borrowed-at") %> <jalios:date date="<%= borrowing.getCdate() %>" /></span>
    </span>
  </jalios:buffer>

  <%@ include file="/plugins/SmartPhonePlugin/jsp/components/list/doDataListItemText.jspf" %>
  <%@ include file="/plugins/SmartPhonePlugin/jsp/components/list/doDataListItemActions.jspf" %>
</jalios:listItem>
<% } %>