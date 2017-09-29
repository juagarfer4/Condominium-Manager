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


	<display:table name="invoices" id="row" requestURI="${requestURI}" pagesize="5" class="table table-striped table-hover table-responsive">
	
		<spring:message code="invoice.creationMoment" var="creationMoment" />
		<display:column title="${creationMoment}" property="creationMoment" format="{0,date,dd/MM/yyyy}" sortable="true"/>
	
		<spring:message code="invoice.paymentMoment" var="paymentMoment" />
		<display:column title="${paymentMoment}" property="paymentMoment" format="{0,date,dd/MM/yyyy}" sortable="true"/>
		
		<display:column titleKey="invoice.amount">
			<fmt:formatNumber type="number" minFractionDigits="2"
				value="${row.amount}" />
			<jstl:out value="euros"></jstl:out>
		</display:column>
		
<%-- 		<spring:message code="invoice.paid" var="paid" /> --%>
<%-- 		<display:column title="${paid}" property="paid" /> --%>

		<display:column titleKey="invoice.paid">
							<jstl:if test="${row.paid}">
								<spring:message code="invoice.status.paid"></spring:message>
							</jstl:if>
							<jstl:if test="${!row.paid}">
								<spring:message code="invoice.status.notpaid"></spring:message>
							</jstl:if>
		</display:column>
		
		<jstl:if test="${row.paid==false}">
		<security:authorize access="hasRole('OWNER')">
<%-- 			<display:column> --%>
<%-- 			<jstl:if test="${row.paid==false}"> --%>
<%-- 				<a href="invoice/owner/edit.do?invoiceId=${row.id}"><input type="button" class="btn btn-primary" name="edit" --%>
<%-- 				value="<spring:message code="invoice.pay"/>" /></a> --%>
<%-- 				<jstl:if test="${row.property.block.community.email!=null}"> --%>
<%-- 					<a href="invoice/owner/paypal.do?invoiceId=${row.id}"><input type="button" class="btn btn-primary" name="edit" --%>
<%-- 					value="<spring:message code="invoice.paypal"/>" /></a> --%>
<%-- 				</jstl:if> --%>
<%-- 			</jstl:if> --%>
<%-- 			</display:column> --%>
				<display:column>
				<div class="btn-group">
  				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
    				<spring:message code="invoice.pay.actions"/> <span class="caret"></span>
  				</button>
 
  				<ul class="dropdown-menu" role="menu">
  				
  					<li><a href="invoice/owner/edit.do?invoiceId=${row.id}"><spring:message code="invoice.pay"/></a></li>
					<jstl:if test="${row.property.block.community.email!=null}">
						<li><a href="invoice/owner/paypal.do?invoiceId=${row.id}"><spring:message code="invoice.paypal"/></a></li>
  					</jstl:if>
  				
				
				   
				    
  				</ul>
				</div>
				</display:column>
		</security:authorize>
		</jstl:if>
		
	</display:table>

<br />

	<security:authorize access="hasRole('OWNER')">
		<a href="<spring:url value='property/owner/list.do' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="invoice.back.properties"/>" /></a>
	</security:authorize>

		<a href="<spring:url value='/' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="invoice.back"/>" /></a>
