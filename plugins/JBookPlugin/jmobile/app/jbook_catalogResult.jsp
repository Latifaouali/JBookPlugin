<%@ include file="/plugins/SmartPhonePlugin/jsp/common/doInitSmartPhonePage.jspf" %><%
%><%@ page import="com.jalios.jcmsplugin.jbook.*" %><%
%><%@ page import="com.jalios.jcmsplugin.jbook.data.*" %><%
%><jsp:useBean id="appHandler" scope="page" class="com.jalios.jcmsplugin.jbook.ui.JMobileJBookAppHandler"><%
  %><jsp:setProperty name="appHandler" property="request"       value="<%= request %>"/><%
  %><jsp:setProperty name="appHandler" property="response"      value="<%= response %>"/><%
  %><jsp:setProperty name="appHandler" property="*" /><%
%></jsp:useBean><%
appHandler.init();%>

<%@ include file='/plugins/JBookPlugin/jmobile/app/jbook_catalogItems.jspf' %>