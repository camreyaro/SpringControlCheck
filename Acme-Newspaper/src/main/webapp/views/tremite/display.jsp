<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<ul style="list-style-type: disc">

			
	<li><b><spring:message code="master.page.title"></spring:message></b>
		<jstl:out value="${tremite.title}" /></li>

	<li><b><spring:message code="article.moment"></spring:message></b>
		<jstl:out value="${tremite.moment}" /></li>
		
</ul>