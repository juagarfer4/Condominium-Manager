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


	<display:table name="owners" id="row" requestURI="${requestURI}" pagesize="5" class="table table-striped table-hover table-responsive">
	
		<spring:message code="owner.name" var="name" />
		<display:column title="${name}" property="name" />
	
		<spring:message code="owner.surname" var="surname" />
		<display:column title="${surname}" property="surname" />
		
		<spring:message code="owner.email" var="email" />
		<display:column title="${email}" property="email"/>
		
		<spring:message code="owner.phone" var="phone" />
		<display:column title="${phone}" property="phone"/>
		
	</display:table>

<br />

	<security:authorize access="hasRole('OWNER')">
		<a href="<spring:url value='property/owner/list.do' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="owner.back.owner"/>" /></a>
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
		<a href="<spring:url value='community/manager/list.do' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="owner.back.manager"/>" /></a>
	</security:authorize>

		<a href="<spring:url value='/' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="owner.back"/>" /></a>
