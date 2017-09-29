<%--
 * list.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



	<display:table name="administrators" id="row" requestURI="${requestURI}" pagesize="5" class="table table-striped table-hover table-responsive">
		
		<spring:message code="administrator.name" var="name" />
		<display:column title="${name}" property="name" />
	
		<spring:message code="administrator.surname" var="surname" />
		<display:column title="${surname}" property="surname" />
		
		<spring:message code="administrator.email" var="email" />
		<display:column title="${email}" property="email"/>
		
		<spring:message code="administrator.contactPhone" var="contactPhone" />
		<display:column title="${contactPhone}" property="contactPhone"/>
		
		<spring:message code="administrator.homePage" var="homePage" />
		<display:column title="${homePage}" property="homePage"/>
	
	</display:table>

<br />

		<a href="<spring:url value='/' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="administrator.back"/>" /></a>
