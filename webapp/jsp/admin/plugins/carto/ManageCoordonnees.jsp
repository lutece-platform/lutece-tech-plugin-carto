<jsp:useBean id="managecartoCoordonnee" scope="session" class="fr.paris.lutece.plugins.carto.web.CoordonneeJspBean" />
<% String strContent = managecartoCoordonnee.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
