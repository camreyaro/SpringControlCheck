<%--
 * edit.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="tremite/administrator/edit.do" modelAttribute="tremite">

	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<jstl:if test="${tremite.isFinal eq false}">
	<acme:textbox code="master.page.title" path="title" />
	<br>
	<acme:textbox code="master.page.description" path="description" />
	<br>
	<acme:textbox code="master.page.moment" path="moment" placeholder="dd/MM/yyyy HH:mm"/>
	<br>	
	<form:label path="gauge">
		<spring:message code="tremite.gauge"/>
	</form:label><br/>
	<form:select path="gauge">
		<form:option value='1'>1</form:option>
		<form:option value='2'>2</form:option>
		<form:option value='3'>3</form:option>
	</form:select>
	<form:errors cssClass="error" path="gauge" />
	<br/>
	<br>
	<form:label path="isFinal">
		<spring:message code="tremite.saveAs"/>
	</form:label><br/>
	<form:select path="isFinal">
		<form:option value='true'><spring:message code="tremite.finalMode"/></form:option>
		<form:option value='false'><spring:message code="tremite.draftMode"/></form:option>
	</form:select>
	<form:errors cssClass="error" path="isFinal" />
	<br/><br>
	</jstl:if>
	
	<jstl:if test="${tremite.isFinal eq true}">
	<form:hidden path="title" />
	<form:hidden path="description" />
	<form:hidden path="gauge" />
	<form:hidden path="moment" />
	<form:hidden path="isFinal" />
	
	<acme:select path="newspaper" items="${newspapers}" code ="master.page.newspaper" itemLabel="title"/>
	<br/>
	</jstl:if>
	
	<jstl:if test="${tremite.isFinal eq false and tremite.id !=0}">
	<input type="submit" name="delete"
		value="<spring:message code="tremite.delete" />"
		onclick="return confirm('<spring:message code="tremite.confirm.delete" />')" />
	</jstl:if>
	
	<acme:submit code="master.page.save"  name="save" />
	<acme:cancel code="master.page.return" url="tremite/administrator/list.do" />

</form:form>
