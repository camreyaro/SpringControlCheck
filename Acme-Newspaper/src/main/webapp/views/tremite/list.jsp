<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<style type="text/css">

.level1{background-color: lightyellow;}
.level2{background-color: moccasin;}
.level3{background-color: blue;}

</style>

<!-- Listing grid -->

<display:table pagesize="3" class="displaytag" 
	name="tremites"  requestURI="tremite/administrator/list.do" id="row">
	
	<jstl:choose>
		<jstl:when test="${row.gauge eq 1}">
			<jstl:set value="level1" var="style"/>
		</jstl:when>
		
		<jstl:when test="${row.gauge eq 2}">
			<jstl:set value="level2" var="style"/>
		</jstl:when>
		
		<jstl:when test="${row.gauge eq 3}">
			<jstl:set value="level3" var="style"/>
		</jstl:when>
	</jstl:choose>
	
	<spring:message code="tremite.title" var="titleHeader" />
	<display:column title="${titleHeader}">
	<a href="tremite/display.do?tremiteId=${row.id}">${row.title}</a>
	</display:column>
	
	<spring:message code="tremite.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"/>
	
	<spring:message code="tremite.finalMode" var="savedModeHeader" />
	<display:column property="isFinal" title="${savedModeHeader}"/>
	
	<spring:message code="tremite.gauge" var="gauge" />
	<display:column property="gauge" class="${style}" title="${gauge}"/>
	
	<spring:message code="tremite.tickerTitle" var="tickerTitle" />
	<display:column property="ticker" title="${tickerTitle}"/>
	
	<spring:message code="tremite.moment" var="momentHeader" />
	<spring:message code="master.page.date.format" var="dateFormat" />
	<display:column property="moment" format="{0,date,${dateFormat}}" title="${momentHeader}"/>
	
	<spring:message code="master.page.newspaper" var="newspaperFormat" />
	<display:column title="${newspaperFormat}">
	<a href="newspaper/display.do?newspaperId=${row.newspaper.id}">${row.newspaper.title}</a>
	</display:column>
	
	
	<display:column >
		<jstl:if test="${row.isFinal eq false or empty row.newspaper}">
			<a href="tremite/administrator/edit.do?tremiteId=${row.getId()}"><spring:message code="master.page.edit"/></a><br/>
		</jstl:if>	
	</display:column>
	
	
</display:table>

<a href="tremite/administrator/create.do"><spring:message code="tremite.create"/></a>