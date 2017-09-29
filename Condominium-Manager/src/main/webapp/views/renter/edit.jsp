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


<form:form action="${actionURI}" modelAttribute="renter">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="property" />
	
	<jstl:if test="${renter.id == 0}">
		<acme:textbox code ="renter.name"  path="name" cssClass="form-control"/>
	<br />
		<acme:textbox code ="renter.surname"  path="surname" cssClass="form-control"/>
	<br />
		<acme:textbox code ="renter.email"  path="email" cssClass="form-control"/>
	<br />
		<acme:textbox code ="renter.arrivalDate"  path="arrivalDate" cssClass="form-control"/>
	<br />
	</jstl:if>
	<jstl:if test="${renter.id != 0}">
		<form:hidden path="name" />
		<form:hidden path="surname" />
		<form:hidden path="email" />
		<form:hidden path="arrivalDate" />
	</jstl:if>
	
		<acme:textbox code ="renter.departureDate"  path="departureDate" cssClass="form-control"/>
	<br />
	
		<acme:submit code="renter.register" name="save" />
	&nbsp;
	
	<a href="<spring:url value='/' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="renter.cancel"/>" /></a>





	
	
</form:form>
