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


	<display:table name="properties" id="row" requestURI="${requestURI}" pagesize="5" class="table table-striped table-hover table-responsive">
	
		<spring:message code="property.floor" var="floor" />
		<display:column title="${floor}" property="floor" />
	
		<spring:message code="property.door" var="door" />
		<display:column title="${door}" property="door" />
		
		<spring:message code="property.block.community.name" var="community.name" />
		<display:column title="${community.name}" property="block.community.name"/>
		
		<spring:message code="property.block.number" var="number" />
		<display:column title="${number}" property="block.number"/>
		
		<security:authorize access="hasRole('OWNER')">
		
			<display:column>
				<div class="btn-group">
  				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
    				<spring:message code="daily.actions"/> <span class="caret"></span>
  				</button>
 
  				<ul class="dropdown-menu" role="menu">
  				
  				<li><a href="renter/owner/list.do?propertyId=${row.id}"><spring:message code="property.renters"/></a></li>
				<li><a href="neighborsboard/owner/list.do?blockId=${row.block.id}"><spring:message code="property.block.neighborsboards"/></a></li>
  				
				
				   
				    
  				</ul>
				</div>
				</display:column>
					<display:column>
				<div class="btn-group">
  				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
    				<spring:message code="communication.actions"/> <span class="caret"></span>
  				</button>
 
  				<ul class="dropdown-menu" role="menu">
  				
  				<li><a href="message/owner/create.do?propertyId=${row.id}"><spring:message code="message.create"/></a></li>
  				
				<li><a href="announcement/owner/listbyblock.do?blockId=${row.block.id}"><spring:message code="property.block.announcements"/></a></li>
			
				<li><a href="announcement/owner/listbycommunity.do?communityId=${row.block.community.id}"><spring:message code="property.community.announcements"/></a></li>
			
				<li><a href="incidence/owner/listbyblock.do?blockId=${row.block.id}"><spring:message code="property.block.incidences"/></a></li>
			
				<li><a href="incidence/owner/listbycommunity.do?communityId=${row.block.community.id}"><spring:message code="property.community.incidences"/></a></li>
			
				<li><a href="incidence/owner/createbyblock.do?blockId=${row.block.id}"><spring:message code="incidence.block.create"/></a></li>
			
				<li><a href="incidence/owner/createbycommunity.do?communityId=${row.block.community.id}"><spring:message code="incidence.community.create"/></a></li>
				
				    
  				</ul>
				</div>
				</display:column>
					<display:column>
				<div class="btn-group">
  				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
    				<spring:message code="budget.actions"/> <span class="caret"></span>
  				</button>
 
  				<ul class="dropdown-menu" role="menu">
  				
				
				    <li><a href="invoice/owner/list.do?propertyId=${row.id}"><spring:message code="property.invoices"/></a></li>
				    <li><a href="payment/owner/list.do?communityId=${row.block.community.id}"><spring:message code="community.payments"/></a></li>
				    <li><a href="budgethistory/owner/list.do?communityId=${row.block.community.id}"><spring:message code="budgetHistory.list"/></a></li>
				    
  				</ul>
				</div>
				</display:column>
			
			
			
			
				
	
		</security:authorize>
		
		<security:authorize access="hasRole('MANAGER')">
		
		
		
				<display:column>
				<div class="btn-group">
  				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
    				<spring:message code="budget.actions"/> <span class="caret"></span>
  				</button>
 
  				<ul class="dropdown-menu" role="menu">
  				
				
				    <li><a href="invoice/manager/create.do?propertyId=${row.id}"><spring:message code="invoice.create"/></a></li>
				    
  				</ul>
				</div>
				</display:column>
		
				<display:column>
				<div class="btn-group">
  				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
    				<spring:message code="property.actions"/> <span class="caret"></span>
  				</button>
 
  				<ul class="dropdown-menu" role="menu">
  				
				
				    <li><a href="renter/manager/list.do?propertyId=${row.id}"><spring:message code="property.renters"/></a></li>
				    <li><a href="renter/manager/create.do?propertyId=${row.id}"><spring:message code="renter.create"/></a></li>
				    
  				</ul>
				</div>
				</display:column>
		
		</security:authorize>
		
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<display:column><a href="property/administrator/edit.do?propertyId=${row.id}"><input type="button" class="btn btn-primary" name="edit"
			value="<spring:message code="property.edit"/>" /></a></display:column>
		</security:authorize>
		
	</display:table>

<br />

		<security:authorize access="hasRole('MANAGER')">
			<a href="<spring:url value='community/manager/list.do' />"><input type="button" class="btn btn-primary" name="Back"
			value="<spring:message code="property.community.back"/>" /></a>
		</security:authorize>
		
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<a href="<spring:url value='community/administrator/list.do' />"><input type="button" class="btn btn-primary" name="Back"
			value="<spring:message code="property.community.back"/>" /></a>
		</security:authorize>

		<a href="<spring:url value='/' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="property.back"/>" /></a>
