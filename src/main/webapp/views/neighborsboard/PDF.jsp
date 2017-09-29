 <%--
 * login.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
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


<spring:message code="neighborsboard.PDF" />

<br />

	<security:authorize access="hasRole('OWNER')">
		<a href="<spring:url value='property/owner/list.do' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="neighborsboard.back.properties"/>" /></a>
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
		<a href="<spring:url value='community/manager/list.do' />"><input type="button" name="Back"
			value="<spring:message code="neighborsboard.back.communities"/>" /></a>
	</security:authorize>
	
	<a href="<spring:url value='/' />"><input type="button" name="Back"
		value="<spring:message code="neighborsboard.back"/>" /></a>

<br />