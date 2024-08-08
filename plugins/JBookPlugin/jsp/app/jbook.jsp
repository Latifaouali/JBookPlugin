<%@ include file='/jcore/doInitPage.jspf' %><%
%><%@ page import="com.jalios.jcmsplugin.jbook.*" %><%
%><%
boolean refineWorkspace = false;
Category topicRoot = JBookManager.getInstance().getTopicRoot();
%>

<div class="ajax-refresh-div" data-jalios-ajax-refresh-url="plugins/JBookPlugin/jsp/app/jbook.jsp">
  <%@ include file='/plugins/JBookPlugin/jsp/app/doJBook.jspf' %>
</div>

<%@ include file='/jcore/doFooter.jspf' %>