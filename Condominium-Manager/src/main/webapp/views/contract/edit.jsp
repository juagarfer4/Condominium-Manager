<%--
 * action-1.jsp
 *
 * Copyright (C) 2013 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags"%>


<form:form action="${actionURI}" modelAttribute="contract">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="community" />
	


	<jstl:if test="${contract.id == 0}">
		<acme:textbox code="contract.arrivalDate" path="arrivalDate" cssClass="form-control"/>
	<br />
		<acme:textbox code="contract.salary" path="salary" cssClass="form-control"/>
	<br />
		<acme:select items="${employees}" itemLabel="identifier"  code="contract.employee" path="employee" cssClass="form-control"/>
	<br />
	</jstl:if>
	<jstl:if test="${contract.id != 0}">
		<form:hidden path="arrivalDate" />
		<form:hidden path="salary" />
		<form:hidden path="employee" />
	</jstl:if>
		<acme:textbox code="contract.departureDate" path="departureDate" cssClass="form-control" />
	<br />



	<br />

	<acme:submit code="contract.register" name="save" />
	&nbsp;
	
	<a href="<spring:url value='community/manager/list.do' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="contract.cancel"/>" /></a>







</form:form>
