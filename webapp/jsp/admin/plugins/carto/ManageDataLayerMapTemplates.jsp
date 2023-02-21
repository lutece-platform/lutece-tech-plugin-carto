<jsp:useBean id="managecartoDataLayerMapTemplate" scope="session" class="fr.paris.lutece.plugins.carto.web.DataLayerMapTemplateJspBean" />
<% String strContent = managecartoDataLayerMapTemplate.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
