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


<h3>
	<spring:message code="manager.dashboard.req1" />
</h3>

<display:table name="invoices" id="row" requestURI="${requestURI}" pagesize="5" class="table table-striped table-hover table-responsive">

	<spring:message code="invoice.creationMoment" var="creationMoment" />
	<display:column title="${creationMoment}" property="creationMoment" format="{0,date,dd/MM/yyyy}" sortable="true"/>

	<spring:message code="invoice.paymentMoment" var="paymentMoment" />
	<display:column title="${paymentMoment}" property="paymentMoment" format="{0,date,dd/MM/yyyy}" sortable="true"/>
	
	<spring:message code="invoice.amount" var="amount" />
	<display:column title="${amount}" property="amount" />
	
	<display:column titleKey="invoice.paid">
							<jstl:if test="${row.paid}">
								<spring:message code="invoice.status.paid"></spring:message>
							</jstl:if>
							<jstl:if test="${!row.paid}">
								<spring:message code="invoice.status.notpaid"></spring:message>
							</jstl:if>
		</display:column>

</display:table>
<br />
<br />
<br />


<h3>
	<spring:message code="manager.dashboard.req2" />
</h3>
	 <p>
		 <jstl:out value="${totalInvoices}"/>
	 </p>
<br />
<br />
<br />

<h3>
	<spring:message code="manager.dashboard.req3" />
</h3>

<display:table name="payments" id="row" requestURI="${requestURI}" pagesize="5" class="table table-striped table-hover table-responsive">

	<spring:message code="payment.paymentMoment" var="paymentMoment" />
	<display:column title="${paymentMoment}" property="paymentMoment" format="{0,date,dd/MM/yyyy}" sortable="true"/>
	
	<spring:message code="payment.amount" var="amount" />
	<display:column title="${amount}" property="amount" />
	
	<spring:message code="payment.description" var="description" />
	<display:column title="${description}" property="description" />

</display:table>
<br />
<br />
<br />


<h3>
	<spring:message code="manager.dashboard.req4" />
</h3>
	 <p>
		 <jstl:out value="${totalPayments}"/>
	 </p>
<br />
<br />


<a href="<spring:url value='/' />"><input type="button" class="btn btn-primary" name="Back"
	value="<spring:message code="manager.back"/>" /></a>
