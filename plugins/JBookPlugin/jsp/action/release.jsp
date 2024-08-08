<% request.setAttribute("CheckCSRF", Boolean.TRUE); %><%
%><%@ include file='/jcore/doInitPage.jspf' %><%
%><%@ page import="com.jalios.jcmsplugin.jbook.*" %><%
%><%
Book book = getDataParameter("book", Book.class);

if (book == null || !isLogged) {
  sendForbidden(request, response);
  return;
}

JBookManager.getInstance().releaseBook(book, loggedMember);
sendRedirect("plugins/JBookPlugin/jsp/app/jbook.jsp?book=" + book.getId());
%>