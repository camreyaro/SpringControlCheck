
<%--
 * action-2.jsp
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


<form:form action="folder/edit.do"
	modelAttribute="folder">
	<fieldset>
	<jstl:if test="${folder.id !=0 }">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		<form:hidden path="parent"/>


		
		
		
		
		<legend>folder</legend>
		
		<form:label path="name" placeholder="Name...">
			<spring:message code="folder.name"/>
		</form:label>
		<form:input path="name"/>
		<form:errors cssClass="error" path="name"/>
		<br/>

	</jstl:if>
	
	<jstl:if test="${folder.id == 0 }">
		<form:hidden path="id"/>
		<form:hidden path="version"/>
		
		

		
		<legend>folder</legend>
		
		<form:label path="name">
			<spring:message code="folder.name"/>
		</form:label>
		<form:input path="name"/>
		<form:errors cssClass="error" path="name"/>
		<br/>

		<br/>

	</jstl:if>
	
	<input type="submit" name="save" value="<spring:message code="folder.create.save" />" />


	<jstl:if test="${folder.id != 0}">
	
	<input type="submit" name="delete" value="<spring:message code="folder.delete" />" />
	
	</jstl:if>
	
	<input type="button" name="cancel" value="<spring:message code="folder.create.cancel" />" 
				onclick="javascript: location.replace('folder/list.do')"/>
	
	
	</fieldset>
	
	
	
	</form:form>














