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


<form:form action="${actionURI}" modelAttribute="incidence">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="community" />
	<form:hidden path="block" />
	<form:hidden path="creationMoment" />

	<jstl:if test="${incidence.id == 0}">
		<form:hidden path="status" />
			<acme:textbox code ="incidence.name"  path="name" cssClass="form-control"/>
		<br />
	</jstl:if>
	
	<acme:textarea code ="incidence.description"  path="description" cssClass="form-control"/>
		<br />
		
	<jstl:if test="${incidence.id != 0}">
	<form:hidden path="name" />
	
	<spring:message code="incidence.status" />
	<form:select  path="status" cssClass="form-control" >
		<form:option  label="PENDING" value="PENDING" />
		<form:option label="SOLVED" value="SOLVED"></form:option>
	</form:select>
	</jstl:if>
	<br />
	<br />
		<acme:submit code="incidence.register" name="save" />
	&nbsp;
	
	<security:authorize access="hasRole('OWNER')">
		<a href="<spring:url value='property/owner/list.do' />"><input type="button" class="btn btn-primary" name="Back"
			value="<spring:message code="incidence.cancel"/>" /></a>
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
		<a href="<spring:url value='community/manager/list.do' />"><input type="button" class="btn btn-primary" name="Back"
			value="<spring:message code="incidence.cancel"/>" /></a>
	</security:authorize>





	
	
</form:form>
