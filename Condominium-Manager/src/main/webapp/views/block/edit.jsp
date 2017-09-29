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


<form:form action="${actionURI}" modelAttribute="block">
	<form:hidden path="id" />
	<form:hidden path="version" />
	
	<form:hidden path="community" />
	<form:hidden path="properties" />
	<form:hidden path="incidences" />
	<form:hidden path="announcements" />
	<form:hidden path="neighborsBoards" />
	<form:hidden path="chargeHistories" />
	<form:hidden path="code" />
	

	<jstl:if test="${block.id == 0}">
	<acme:textbox code="block.number" path="number" cssClass="form-control" />
	<br />
	</jstl:if>
	<jstl:if test="${block.id != 0}">
	<form:hidden path="number" />
	</jstl:if>
	
		<acme:textbox code="block.address" path="address"  cssClass="form-control"/>
	<br />	
		<acme:textbox code="block.numberOfFloors" path="numberOfFloors"  cssClass="form-control"/>
	<br />
		<acme:textbox code="block.numberOfDoors" path="numberOfDoors"  cssClass="form-control"/>
	<br />

	<acme:submit code="block.register" name="save" />
	&nbsp;
	
	<a href="<spring:url value='community/administrator/list.do' />"><input type="button"  class="btn btn-primary" name="Back"
		value="<spring:message code="block.cancel"/>" /></a>







</form:form>
