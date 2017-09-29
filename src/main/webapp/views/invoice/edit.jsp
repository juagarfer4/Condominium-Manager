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


<form:form action="${actionURI}" modelAttribute="invoice">

	<form:hidden path="id" />
    <form:hidden path="version" />
    <form:hidden path="creationMoment" />
    <form:hidden path="property" />
    <form:hidden path="paid" />
	
	<jstl:if test="${invoice.id == 0}">
		<acme:textbox code ="payment.amount"  path="amount" cssClass="form-control"/>
	<br />
	</jstl:if>
	
	<jstl:if test="${invoice.id != 0}">
		  <form:hidden path="amount" />
		  <spring:message code="invoice.register.owner" />
	
	<br />
	<br />
		  
	</jstl:if>
	
		<acme:submit code="invoice.register" name="save" />
	&nbsp;
	
	<security:authorize access="hasRole('MANAGER')">
		<a href="<spring:url value='community/manager/list.do' />"><input type="button" class="btn btn-primary" name="Back"
			value="<spring:message code="invoice.cancel"/>" /></a>
	</security:authorize>

<security:authorize access="hasRole('OWNER')">
		<a href="<spring:url value='property/owner/list.do' />"><input type="button" class="btn btn-primary" name="Back"
			value="<spring:message code="invoice.cancel"/>" /></a>
</security:authorize>

</form:form>