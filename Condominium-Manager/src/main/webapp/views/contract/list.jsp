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


	<display:table name="contracts" id="row" requestURI="${requestURI}" pagesize="5" class="table table-striped table-hover table-responsive">
	
		<display:column titleKey="contract.salary">
			<fmt:formatNumber type="number" minFractionDigits="2"
				value="${row.salary}" />
			<jstl:out value="euros"></jstl:out>
		</display:column>
	
		<spring:message code="contract.arrivalDate" var="arrivalDate" />
		<display:column title="${arrivalDate}" property="arrivalDate" format="{0,date,dd/MM/yyyy}" sortable="true"/>
	
		<spring:message code="contract.departureDate" var="departureDate" />
		<display:column title="${departureDate}" property="departureDate" format="{0,date,dd/MM/yyyy}" sortable="true"/>
	
		<spring:message code="contract.community" var="community" />
		<display:column title="${community}" property="community.name" />
		
		<spring:message code="contract.employee" var="employee" />
		<display:column title="${employee}" property="employee.name" />
	
		<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<jstl:if test="${row.departureDate==null}">
				<a href="contract/manager/edit.do?contractId=${row.id}"><input type="button" class="btn btn-primary" name="Edit"
					value="<spring:message code="contract.edit"/>" /></a>
			</jstl:if>
		</display:column>
		</security:authorize>
	
	</display:table>

<br />

<a href="<spring:url value='/' />"><input type="button" class="btn btn-primary" name="Back"
	value="<spring:message code="contract.back"/>" /></a>
