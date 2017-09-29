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


<form:form action="${actionURI}" modelAttribute="chargeHistory">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="block" />
	<form:hidden path="mandateBeginning" />
	

	<jstl:if test="${chargeHistory.id == 0}">
	<br />
	<acme:textbox code="chargehistory.mandateEnding" path="mandateEnding" cssClass="form-control" />
	<br />
	<acme:checkbox code="chargehistory.isPresident" path="isPresident" />
	<br />
	<acme:select items="${owners}" itemLabel="identifier"  code="chargehistory.owner" path="owner" cssClass="form-control"/>
	<br />
	
	</jstl:if>

	<jstl:if test="${chargeHistory.id != 0}">

	<form:hidden path="owner" />
	<form:hidden path="isPresident" />
	
	<acme:textbox code="chargehistory.mandateEnding" path="mandateEnding" cssClass="form-control"/>
	<br />
	
	</jstl:if>


	<br />

	<acme:submit code="chargehistory.register" name="save" />
	&nbsp;
	
	<a href="<spring:url value='/' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="chargehistory.cancel"/>" /></a>







</form:form>
