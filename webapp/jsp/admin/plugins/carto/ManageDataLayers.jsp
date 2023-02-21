<jsp:useBean id="managecartoDataLayer" scope="session" class="fr.paris.lutece.plugins.carto.web.DataLayerJspBean" />
<% String strContent = managecartoDataLayer.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
