<jsp:useBean id="managecartoMapTemplate" scope="session" class="fr.paris.lutece.plugins.carto.web.MapTemplateJspBean" />
<% String strContent = managecartoMapTemplate.processController ( request , response ); %>

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<%= strContent %>

<%@ include file="../../AdminFooter.jsp" %>
