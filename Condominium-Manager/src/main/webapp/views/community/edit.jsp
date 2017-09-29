<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>


<form:form action="${actionURI}" modelAttribute="community">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="payments" />
	<form:hidden path="incidences" />
	<form:hidden path="announcements" />
	<form:hidden path="blocks" />
	<form:hidden path="contracts" />
	<form:hidden path="budgetHistories" />
	
	


	<br />
		<acme:textbox code ="community.name"  path="name" cssClass="form-control" />
	<br />
		<acme:textbox code ="community.address"  path="address" cssClass="form-control"/>
	<br />
		<acme:textbox code ="community.email"  path="email"/>
	<br />
	
	<jstl:if test="${community.id == 0}">
		<acme:textbox code ="community.budget"  path="budget" cssClass="form-control"/>
		<br />
	</jstl:if>
	<jstl:if test="${community.id != 0}">
		<form:hidden path="budget" />
		<br />
	</jstl:if>
		
		
	<br />
	
		<acme:submit code="community.register" name="save" />
	&nbsp;
	
	<a href="<spring:url value='/' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="community.cancel"/>" /></a>





	
	
</form:form>
