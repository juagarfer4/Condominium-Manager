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
			<b><spring:message code="incidence.name" /></b><br />
			<jstl:out value="${incidence.name}"></jstl:out>
			<br />
			<br />
			
			<b><spring:message code="incidence.creationMoment" /></b><br />
			<jstl:out value="${incidence.creationMoment}"></jstl:out>
			<br />
			<br />
			
			<b><spring:message code="incidence.description" /></b><br />
			<jstl:out value="${incidence.description}"></jstl:out>
			<br />
			<br />
			
<%-- 			<b><spring:message code="incidence.status" /></b><br /> --%>
<%-- 			<jstl:out value="${incidence.status}"></jstl:out> --%>
<!-- 			<br /> -->
<!-- 			<br /> -->

			<b><spring:message code="incidence.status" /></b><br />
			<jstl:if test="${incidence.status=='UNREAD'}">
				<spring:message code="incidence.status.unread" />
			</jstl:if>
			<jstl:if test="${incidence.status=='PENDING'}">
				<spring:message code="incidence.status.pending" />
			</jstl:if>
			<jstl:if test="${incidence.status=='SOLVED'}">
				<spring:message code="incidence.status.solved" />
			</jstl:if>
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
		value="<spring:message code="incidence.back.communities"/>" /></a>
	</security:authorize>
	
	<security:authorize access="hasRole('OWNER')">
		<a href="<spring:url value='property/owner/list.do' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="incidence.back.properties"/>" /></a>
	</security:authorize>
