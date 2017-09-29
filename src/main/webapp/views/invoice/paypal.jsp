<%--
 * action-1.jsp
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
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form name='formTpv' method='post'
					action='https://www.sandbox.paypal.com/cgi-bin/webscr'>
<security:authorize access="hasRole('OWNER')">
<jstl:if test="${invoice.property.block.community.email!=null}">
<spring:message code="invoice.register.owner.paypal" />
<br />
<br /> 		
					<input name="cmd" type="hidden" value="_cart"> 
					<input name="upload" type="hidden" value="1"> 
					<input name="business" type="hidden" value="${invoice.property.block.community.email}"> 
					<input name="shopping_url" type="hidden" value="http://localhost:8080/Condominium-Manager"> 
					<input name="currency_code" type="hidden" value="EUR"> 
					<input name="return" type="hidden" value="http://localhost:8080/Condominium-Manager/invoice/owner/success.do?invoiceId=${invoice.id}">
					<input type='hidden' name='cancel_return' value='http://localhost:8080/Condominium-Manager/invoice/owner/failure.do'>
					<input name="rm" type="hidden" value="2">
					
						<input name="item_number_1" type="hidden"
							value="1">
						<input name="item_name_1" type="hidden"
							value="${invoice.property.block.community.name}">
						<input name="amount_1" type="hidden"
							value="${invoice.amount}">
						<input name="quantity_1" type="hidden" value="1">
					
					<button class="btn btn-primary btn-orange uppercase"><spring:message code="invoice.paypal"></spring:message></button>
				
				&nbsp;
		<a href="<spring:url value='property/owner/list.do' />"><input type="button" class="btn btn-primary" name="Back"
			value="<spring:message code="invoice.cancel"/>" /></a>
		</jstl:if>
</security:authorize>
</form>