<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.jalios.jcmsplugin.jbook.*"%>
<%@ page import="com.jalios.jcmsplugin.jbook.data.*"%>
<%@ page import="com.jalios.jcmsplugin.jbook.ui.*" %>
<%@ include file='/plugins/SmartPhonePlugin/jsp/common/header.jspf' %>
<jsp:useBean id="appHandler" scope="page" class="com.jalios.jcmsplugin.jbook.ui.JMobileJBookAppHandler">
<jsp:setProperty name="appHandler" property="request"  value="<%= request %>"/>
<jsp:setProperty name="appHandler" property="response" value="<%= response %>"/>
<jsp:setProperty name="appHandler" property="*" />
</jsp:useBean>
<%
appHandler.init();

SmartPhoneService service = new DefaultSmartPhoneService("jcmsplugin.smartphone.service.jbook");
if (!service.isEnabled(jcmsContext)) {
  sendRedirect(SmartPhoneConstants.FORBIDDEN_JSP_PATH);
  return;
}

boolean showCatalog = appHandler.showCatalog();
boolean showMyBorrowings = appHandler.showMyBorrowings();
%>
<div id="service-jbook" class="service-jbook-page" data-role="page" data-dom-cache="true">

  <%-- Topbar Menu --%>
  <%@ include file='/plugins/SmartPhonePlugin/jsp/common/doServiceTopbarMenu.jspf' %>

  <%-- App Title --%>
  <smartphone:breadcrumb icon="jcmsplugin-jbook-app" service="<%= service %>" />

  <div class="nav-menu">
    <% if (appHandler.hasBorrowings()) { %>
    <div data-role="navbar">
      <ul>
        <li><a class="<%= showCatalog ? "ui-btn-active" : "" %>"  href="<%= appHandler.getCatalogUrl() %>"><%= glp("jcmsplugin.jbook.jmobile.tab.catalog") %></a></li>
        <li><a class="<%= showMyBorrowings ? "ui-btn-active" : "" %>" href="<%= appHandler.getMyBorrowingsUrl() %>"><%= glp("jcmsplugin.jbook.jmobile.tab.borrowings") %></a></li>
      </ul>
    </div>
    <% } %>

    <%-- MY BORROWINGS --%>
    <% if (showMyBorrowings) { %>
    <%@ include file='/plugins/JBookPlugin/jmobile/app/jbook_borrowings.jspf' %>
    <% } %>

    <%-- CATALOG --%>
    <% if (showCatalog) { %>
    <%@ include file='/plugins/JBookPlugin/jmobile/app/jbook_catalog.jspf' %>
    <% } %>

  </div>

</div>


<%@ include file='/plugins/SmartPhonePlugin/jsp/common/footer.jspf' %>