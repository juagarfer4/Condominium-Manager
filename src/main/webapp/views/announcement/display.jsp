<%--
 * show.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<div class="dn-breadcrumbs">
  <div class="dn-breadcrumbs-background-services"></div>
  <div class="container">
    <div class="row">
      <div class="span16">
        <ul class="breadcrumb">
			<b><spring:message code="announcement.name" /></b><br />
			<jstl:out value="${announcement.name}"></jstl:out>
			<br />
			<br />
			<b><spring:message code="announcement.creationMoment" /></b><br />
			<jstl:out value="${announcement.creationMoment}"></jstl:out>
			<br />
			<br />
			<b><spring:message code="announcement.description" /></b><br />
			<jstl:out value="${announcement.description}"></jstl:out>
			<br />
			<br />
        </ul>
      </div>
    </div>
  </div>
</div>

<br />

	<security:authorize access="hasRole('MANAGER')">	
		<a href="<spring:url value='community/manager/list.do' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="announcement.back.communities"/>" /></a>
	</security:authorize>
	
	<security:authorize access="hasRole('OWNER')">
		<a href="<spring:url value='property/owner/list.do' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="announcement.back.properties"/>" /></a>
	</security:authorize>
