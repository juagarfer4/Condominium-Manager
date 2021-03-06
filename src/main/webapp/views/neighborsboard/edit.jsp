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


<form:form action="${actionURI}" modelAttribute="neighborsBoard">

	<form:hidden path="id" />
    <form:hidden path="version" />
    <form:hidden path="date" />
    <form:hidden path="block" />
    <form:hidden path="attendants" />
    
    	<acme:textarea code ="neighborsboard.record"  path="record" cssClass="form-control"/>
	<br />
	
		<acme:submit code="neighborsboard.register" name="save" />
	&nbsp;
	
	<a href="<spring:url value='community/manager/list.do' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="neighborsboard.cancel"/>" /></a>



	
</form:form>
