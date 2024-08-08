<%@page import="com.jalios.jcmsplugin.smartphone.SmartPhoneManager"%>
<%@page import="com.jalios.jcmsplugin.jbook.*"%>
<%@page import="com.jalios.jcmsplugin.jbook.data.*"%>
<%@ page contentType="text/html; charset=UTF-8" %><%
%><%@page import="com.jalios.jcms.taglib.list.ListItemTag"%><%
%><%@ include file="/plugins/SmartPhonePlugin/jsp/components/list/doInitMobileDataListItem.jspf" %><%
css += " jmobile-borrowable-resource";
AbstractBook resource = (AbstractBook)data;

JBookBorrowing borrowing = resource.getCurrentBorrowing();
Member borrower = borrowing == null ? null : borrowing.getAuthor();
boolean isBorrowed = borrower != null;
boolean isBorrowedByMe = borrower == loggedMember;
%>
<jalios:listItem
    css="<%= css %>"
    dataAttribute="<%= dataAttribute %>"
    icon="<%= icon %>"
    link="<%= link %>"
    linkCss="<%= linkCss %>"
    linkDataAttribute="<%= linkDataAttribute %>"
    template="jmobile-detailed"
    text="<%= text %>">
  <jalios:buffer name="LIST_ITEM_IMAGE">
    <span class="itemlist-item-image">
    <% String image = data.getDataImage(); %>
    <% if (Util.notEmpty(image)) { %>
    <jalios:thumbnail css="is-object-fit" path="<%= image %>" square="true"  width="60" height="60" />
    <% } else { %>
    <jalios:thumbnail css="is-object-fit" path="images/jalios/layout/defaultDataListItemImage.png" square="true"  width="60" height="60" />
    <% } %>
    </span>
  </jalios:buffer>

  <jalios:buffer name="LIST_ITEM_META">
    <% if (isBorrowedByMe ) { %>
    <%= glp("jcmsplugin.jbook.lbl.borrowed-by-you") %>
    <% } else if (isBorrowed) { %>
    <%= glp("jcmsplugin.jbook.lbl.borrowed-by", borrower.getFullName()) %>
    <% } else { %>
    <%= glp("jcmsplugin.jbook.lbl.available") %>
    <% } %>
  </jalios:buffer>

  <%@ include file="/plugins/SmartPhonePlugin/jsp/components/list/doDataListItemText.jspf" %>
  <%@ include file="/plugins/SmartPhonePlugin/jsp/components/list/doDataListItemActions.jspf" %>
</jalios:listItem>