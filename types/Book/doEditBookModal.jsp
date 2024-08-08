<%-- This file has been automatically generated. --%>
<%--
  @Summary: Book modal content editor
  @Category: Generated
  @Author: JCMS Type Processor
  @Customizable: True
  @Requestable: False
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file='/jcore/doInitPage.jspf' %>
<%
  EditBookHandler formHandler = (EditBookHandler)request.getAttribute("formHandler");
  ServletUtil.backupAttribute(pageContext, "classBeingProcessed");
  request.setAttribute("classBeingProcessed", generated.Book.class);
%>
<%-- Description ------------------------------------------------------------ --%>
<jalios:field name="description" formHandler="<%= formHandler %>">
  <jalios:control />
</jalios:field>
<%-- Image ------------------------------------------------------------ --%>
<jalios:field name="image" formHandler="<%= formHandler %>">
  <jalios:control />
</jalios:field>
<%-- Topics ------------------------------------------------------------ --%>
<%
TypeFieldEntry tfe = channel.getTypeFieldEntry(Book.class, "topics", true);
CategorySettings topicsSettings = (CategorySettings)(tfe.getControlSettings());
topicsSettings.root(com.jalios.jcmsplugin.jbook.JBookManager.getInstance().getTopicRoot(workspace, loggedMember));
%>
<jalios:field name="topics" formHandler="<%= formHandler %>">
  <jalios:control settings="<%= topicsSettings %>"/>
</jalios:field>
<%-- Isbn ------------------------------------------------------------ --%>
<jalios:field name="isbn" formHandler="<%= formHandler %>">
  <jalios:control />
</jalios:field>
<% if (formHandler.getPublication() != null) { %>
<input type="hidden" name="id" value="<%= formHandler.getPublication().getId() %>" />
<% } %>
<jalios:include target="EDIT_PUB_MAINTAB" targetContext="div" />
<jalios:include jsp="/jcore/doEditExtraData.jsp" />
<% ServletUtil.restoreAttribute(pageContext , "classBeingProcessed"); %><%-- **********4A616C696F73204A434D53 *** SIGNATURE BOUNDARY * DO NOT EDIT ANYTHING BELOW THIS LINE *** --%><%
%><%-- txvukhVw1C1j2PGRV6zlDA== --%>