<%@ include file='/jcore/doInitPage.jspf' %><%
%><%@ include file='/jcore/portal/doPortletParams.jspf' %><%
%><%@ page import="com.jalios.jcmsplugin.jbook.*" %><%

PortletJBook box = (PortletJBook)portlet;

boolean refineWorkspace = box.getRefineWorkspace();
Category topicRoot = box.getFirstTopicRoot(loggedMember);

%><%@ include file='/plugins/JBookPlugin/jsp/app/doJBook.jspf' %>