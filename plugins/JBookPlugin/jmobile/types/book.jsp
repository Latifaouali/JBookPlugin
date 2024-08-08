<%@ include file='/plugins/SmartPhonePlugin/jsp/common/header.jspf' %>
<%@ page import="com.jalios.jcmsplugin.jbook.*" %>
<%@ page import="com.jalios.jcmsplugin.jbook.data.*" %>

<% Publication pub = getPublicationParameter("id"); %>
<%@ include file='/plugins/SmartPhonePlugin/jsp/core/pub/doPubCommon.jspf' %>
<div id="pub-default" data-role="page" class="pub-page pub-default">
  <%@ include file='/plugins/SmartPhonePlugin/jsp/core/pub/doPubTopbarMenu.jspf' %>
  <div data-role="content" class="pub-content">
    <div class="card card-publication">
      <%@ include file='/plugins/SmartPhonePlugin/jsp/core/pub/doPubHeader.jspf' %>
      <%@ include file='/plugins/SmartPhonePlugin/jsp/core/pub/doPubLock.jspf' %>
      <% if (Util.notEmpty(pub.getDataImage()) || Util.notEmpty(pub.getAbstract())) { %>
      <div class="card-item pub-body">
        <jalios:include target="SMARTPHONE_PUB_BODY_TOP" />

          <%-- BOOK STATUS --%>
          <%
          Book book = (Book)pub;
          JBookBorrowing currentBorrowing = book.getCurrentBorrowing();
          boolean isBorrowed = currentBorrowing != null;
          boolean isBorrowedByMe = isBorrowed && currentBorrowing.getBorrower() == loggedMember;
          String bookId = book.getId();

          String borrowingDuration = "";
          if (isBorrowed) {
            borrowingDuration = DateUtil.formatDuration(currentBorrowing.getBorrowingDate(), new Date(), userLocale);
          }
          %>
          <% if (isBorrowedByMe) { %>
          <jalios:message title="" dismissable="false">
          <jalios:buffer name="borrowingDate"><jalios:date date="<%= currentBorrowing.getBorrowingDate() %>" /></jalios:buffer>
          <%= glp("jcmsplugin.jbook.borrowing.status.borrowed-by-me", borrowingDate, borrowingDuration) %>
          </jalios:message>

          <% } else if (isBorrowed) { %>
          <jalios:message title="" dismissable="false" level="<%= MessageLevel.WARN %>">
          <jalios:buffer name="borrowingDateBuffer"><jalios:date date="<%= currentBorrowing.getBorrowingDate() %>" /></jalios:buffer>
          <jalios:buffer name="borrowerBuffer"><jalios:link data="<%= currentBorrowing.getAuthor()%>" /></jalios:buffer>
          <%= glp("jcmsplugin.jbook.borrowing.status.borrowed", borrowerBuffer, borrowingDateBuffer, borrowingDuration) %>
          </jalios:message>
          <% } %>

        <%-- BORROWING BUTTONS --%>
        <div class="jbook-buttons">
        <% if (isBorrowedByMe) { %>
        <a href="plugins/JBookPlugin/jsp/action/release.jsp?jmobile=true&amp;book=<%= bookId %>" class="ui-btn btn-primary confirm"><%= glp("jcmsplugin.jbook.btn.release") %></a>
        <% } else if (isBorrowed) { %>
        <a href="plugins/SmartPhonePlugin/jsp/action/recommendation.jsp?recommendedDataId=<%= bookId %>&amp;alertLevelKey=action&amp;members=<%= JcmsUtil.getId(currentBorrowing.getAuthor()) %>" class="ui-btn"><%= glp("jcmsplugin.jbook.btn.contact") %></a>
        <a href="plugins/JBookPlugin/jsp/action/borrow.jsp?jmobile=true&amp;book=<%= bookId %>" class="ui-btn confirm"><%= glp("jcmsplugin.jbook.btn.force-borrow") %></a>
        <% } else { %>
        <a href="plugins/JBookPlugin/jsp/action/borrow.jsp?jmobile=true&amp;book=<%= bookId %>" class="ui-btn btn-primary confirm"><%= glp("jcmsplugin.jbook.btn.borrow") %></a>
        <% } %>
        </div>

        <%-- IMAGE --%>
        <% if (Util.notEmpty(pub.getDataImage())) { %>
        <jalios:thumbnail css="thumbnail nobr center-block" path="<%= pub.getDataImage() %>" width='480' height="640" ><jalios:icon src="image" /></jalios:thumbnail>
        <% } %>

        <jalios:wiki css="wiki zoomable-images" attributeMap="<%= smartPhoneManager.getWikiAttributeMap() %>"><%= pub.getAbstract() %></jalios:wiki>

        <%-- INFO --%>
        <jalios:buffer name="info">
          <%-- reference --%>
          <% String isbn = book.getIsbn(userLang); %>
          <% if (Util.notEmpty(isbn)) { %>
          <dt><%= glp("types.Book.fields.isbn.label") %></dt>
          <dd><%= isbn %></dd>
          <% } %>
        </jalios:buffer>

        <jalios:include target="SMARTPHONE_PUB_BODY_BOTTOM" />

      </div><%-- EOF .pub-body --%>
      <% } %>


      <%@ include file='/plugins/SmartPhonePlugin/jsp/core/pub/doPubMeta.jspf' %>
      <jalios:include jsp='/plugins/SmartPhonePlugin/jsp/core/pub/doPubFooter.jsp' />
    </div><%-- EOF .pub --%>


    <jalios:include target="SMARTPHONE_PUB_FOOTER" />
  </div><%-- EOF .content --%>
</div><%-- EOF #pub-default --%>
<%@ include file='/plugins/SmartPhonePlugin/jsp/common/footer.jspf' %>