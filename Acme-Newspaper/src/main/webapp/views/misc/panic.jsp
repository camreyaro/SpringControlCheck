<%--
 * panic.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<p><spring:message code="panic.text" /> <code>${name}</code>.</p>

<center>
		<b>Score: </b><span id="score">0</span>
		<br/>
		<div id="game"></div>
		</center>
		<script>init();</script>

<!--  
<h2><spring:message code="panic.message" /></h2>

<p style="font-family: 'Courier New'">
	${exception}
</p>

<h2><spring:message code="panic.stack.trace" /></h2>

<p style="font-family: 'Courier New'">	
	${stackTrace}
</p>-->