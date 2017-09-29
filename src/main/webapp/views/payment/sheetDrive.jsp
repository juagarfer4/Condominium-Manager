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




<form:form action="${actionURI}" modelAttribute="googleSheetForm">

	<form:hidden path="communityId" />

	<acme:textbox code ="payment.urlSheet"  path="urlSheet" cssClass="form-control"/>
	<form:errors path="urlSheet" cssClass="error" />
	<br />
	<br />

	<a href="<spring:url value='payment/manager/tutorial.do' />"><spring:message code="payment.tutorial"/></a>
	<br />
	<br />

	<a href="<spring:url value='payment/manager/downloadgoogledrive.do' />"><spring:message code="payment.example"/></a>
	<br />
	<br />

	<input type="submit" name="savesheetdrive" class="btn btn-primary"
		value="<spring:message code="payment.import"/>" />

	<a href="<spring:url value='community/manager/list.do' />"><input
		type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="neighborsboard.cancel"/>" /></a>




</form:form>