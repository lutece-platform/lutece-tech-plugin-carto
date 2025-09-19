<jsp:useBean id="managecartoGeometryType" scope="session" class="fr.paris.lutece.plugins.carto.web.GeometryTypeJspBean" />
${ pageContext.setAttribute( 'strContent', geometryTypeJspBean.processController ( pageContext.request , pageContext.response ) ) }

<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

${ pageContext.getAttribute( 'strContent' ) }

<%@ include file="../../AdminFooter.jsp" %>
