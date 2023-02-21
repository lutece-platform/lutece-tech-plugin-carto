<jsp:useBean id="managecartoGeometryType" scope="session" class="fr.paris.lutece.plugins.carto.web.GeometryTypeJspBean" />
<% String strContent = managecartoGeometryType.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
