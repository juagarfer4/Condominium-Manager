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

<form:form action="${actionURI}" modelAttribute="neighborForm">

	<acme:textbox code ="neighbor.userAccount.username"  path="username" cssClass="form-control"/>
	<br />
	<acme:password code ="neighbor.userAccount.password"  path="password" cssClass="form-control"/>
	<br />
	<acme:password code ="neighbor.userAccount.passwordVerificada"  path="passwordVerificada" cssClass="form-control"/>
	<br />
		<acme:textbox code ="neighbor.name"  path="name" cssClass="form-control"/>
	<br />
		<acme:textbox code ="neighbor.surname"  path="surname" cssClass="form-control"/>
	<br />
		<acme:textbox code ="neighbor.email"  path="email" cssClass="form-control"/>
	<br />
		<acme:textbox code ="neighbor.phone"  path="phone" cssClass="form-control"/>
	<br />
	<acme:checkbox path="condition" url="privacy/lopd-lssi.do" code="neighbor.laws" />
	<br />
		<acme:submit code="neighbor.register" name="save" />
	&nbsp;
	
	<a href="<spring:url value='/' />"><input type="button" class="btn btn-primary"name="Back"
		value="<spring:message code="neighbor.cancel"/>" /></a>

	
</form:form>
