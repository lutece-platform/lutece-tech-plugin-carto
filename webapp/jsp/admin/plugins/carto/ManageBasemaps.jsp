<jsp:useBean id="managereferentielcartographieBasemap" scope="session" class="fr.paris.lutece.plugins.carto.web.BasemapJspBean" />
<% String strContent = managereferentielcartographieBasemap.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
