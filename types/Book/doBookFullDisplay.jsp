<%@ page contentType="text/html; charset=UTF-8" %><%
%><%@ include file='/jcore/doInitPage.jspf' %><%
%><%@ page import="com.jalios.jcms.handler.*" %><%
%><%@ page import="com.jalios.jcmsplugin.jbook.*" %><%
%><%@ page import="com.jalios.jcmsplugin.jbook.data.*" %><%
%><%@ page import="com.jalios.jcmsplugin.jbook.ui.*" %><%
%><% 

Book book = (Book)request.getAttribute(PortalManager.PORTAL_PUBLICATION);

JBookAppHandler appHandler = (JBookAppHandler)request.getAttribute("jbook.appHandler");
if (appHandler == null) {
  sendRedirect(book.getAppDisplayUrl(userLocale));
  return;
}

JBookBorrowing currentBorrowing = book.getCurrentBorrowing();
boolean isBorrowed = currentBorrowing != null;
boolean isBorrowedByMe = isBorrowed && currentBorrowing.getBorrower() == loggedMember;

%><%@ include file='/front/doFullDisplay.jspf' %>
<div class="fullDisplay Book <%= book.canBeEditedFieldByField(loggedMember) ? "unitFieldEdition" : "" %>" itemscope="itemscope">

  <%-- Title --%>
  <h1><%= book.getTitle(userLang) %></h1>
  
  <%-- Image --%>
  <% if (Util.notEmpty(book.getImage())) { %>
  <div class="image">
    <jalios:thumbnail data="<%= book %>" width="1200" height="400"/>
  </div>
  <% } %>

  <%-- BORROWING STATUS --%>
  <%@ include file='/plugins/JBookPlugin/jsp/app/doJBookStatus.jspf' %>

  <%-- BUTTONS --%>
  <%@ include file='/plugins/JBookPlugin/jsp/app/doJBookButtons.jspf' %>
   
  <%-- METADATA --%>
  <dl class="dl-horizontal">
    <%-- Description --%>
    <% String description = book.getDescription(userLang);  %>
    <% if (Util.notEmpty(description)) { %>
    <dt><%= channel.getTypeFieldLabel(Book.class, "description", userLang) %></dt>
    <dd><jalios:wysiwyg data='<%= book %>' field='description'><%= description %></jalios:wysiwyg></dd>
    <% } %>
  
    <%-- Topics --%>
    <% Set<Category> topicSet = book.getTopics(loggedMember);  %>
    <% if (Util.notEmpty(topicSet)) { %>
    <dt><%= channel.getTypeFieldLabel(Book.class, "topics", userLang) %></dt>
    <dd>
      <ul class="list-unstyled">
        <jalios:foreach collection="<%= topicSet %>" name="topic" type="Category">
        <li><a class="meta-cat" href="<%= appHandler.getAppUrl(topic) %>"><%= topic.getName(userLang) %></a></li>
        </jalios:foreach>
      </ul>
    </dd>
    <% } %>
    
    <%-- previous borrowers --%>
    <% Set<Member> borrowerSet = book.getPreviousBorrowerSet(); %>
    <% if (Util.notEmpty(borrowerSet)) { %>
    <dt><%= glp("jcmsplugin.jbook.lbl.prev-borrowers") %></dt>
    <dd>
      <ul class="list-unstyled">
        <jalios:foreach collection="<%= borrowerSet %>" type="Member" name="itBorrower" max="5">
        <% if (itBorrower != null) { %>
        <li><jalios:memberphoto member="<%= itBorrower %>" size="<%= PhotoSize.ICON %>" /> <jalios:link data="<%= itBorrower %>" /></li>
        <% } %>
        </jalios:foreach>
      </ul>    
    </dd>
    <% } %>
  </dl>

  <%-- RELATED RESOURCES --%>
  <%
  Collection pubRelatedCollection = QueryManager.getInstance().getRelatedPublicationSet(book, loggedMember, Book.class);
  int pubRelatedMax = 6;
  %>
  <%@ include file="/front/publication/doPublicationRelated.jspf" %>
</div>
  
    
