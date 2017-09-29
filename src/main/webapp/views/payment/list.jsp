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


	<display:table name="payments" id="row" requestURI="${requestURI}" pagesize="5" class="table table-striped table-hover table-responsive">
	
		<spring:message code="payment.paymentMoment" var="paymentMoment" />
		<display:column title="${paymentMoment}" property="paymentMoment" format="{0,date,dd/MM/yyyy}" sortable="true"/>
		
		<display:column titleKey="payment.amount">
			<fmt:formatNumber type="number" minFractionDigits="2"
				value="${row.amount}" />
			<jstl:out value="euros"></jstl:out>
		</display:column>
		
		<security:authorize access="hasRole('OWNER')">	
		<display:column><a href="payment/owner/display.do?paymentId=${row.id}"><input type="button" class="btn btn-primary" name="Display"
				value="<spring:message code="payment.display"/>" /></a></display:column>
		</security:authorize>
		
		<security:authorize access="hasRole('MANAGER')">	
		<display:column><a href="payment/manager/display.do?paymentId=${row.id}"><input type="button" class="btn btn-primary" name="Display"
				value="<spring:message code="payment.display"/>" /></a></display:column>
		</security:authorize>
		
	</display:table>

<br />

	<security:authorize access="hasRole('MANAGER')">	
		<a href="<spring:url value='community/manager/list.do' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="payment.back.communities"/>" /></a>
	</security:authorize>
	
	<security:authorize access="hasRole('OWNER')">
		<a href="<spring:url value='property/owner/list.do' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="payment.back.properties"/>" /></a>
	</security:authorize>
