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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>



<security:authorize access="hasRole('ADMINISTRATOR')">
	<br />
	<form action="community/administrator/search.do">
		<input type="text"  name="address" placeholder="<spring:message code="community.search.placeholder"/>" class="form-control" style="display: inline;">
		<button type="submit" class="btn btn-primary"><spring:message code="community.search.button"/></button>
	</form>
</security:authorize>

<security:authorize access="hasRole('MANAGER')">
	<br />
	<form action="community/manager/search.do">
		<input type="text" name="address" placeholder="<spring:message code="community.search.placeholder"/>" class="form-control" style="display: inline;" >
		<button type="submit" class="btn btn-primary"><spring:message code="community.search.button"/></button>
	</form>
</security:authorize>

<br />

	<display:table name="communities" id="row" requestURI="${requestURI}" pagesize="5" class="table table-striped table-hover table-responsive">
	
		<spring:message code="community.name" var="name" />
		<display:column title="${name}" property="name" />
	
		<spring:message code="community.address" var="address" />
		<display:column title="${address}" property="address" />
						
		<display:column titleKey="community.budget">
			<fmt:formatNumber type="number" minFractionDigits="2"
				value="${row.budget}" />
			<jstl:out value="euros"></jstl:out>
		</display:column>
		
		<security:authorize access="hasRole('ADMINISTRATOR')">
		
		<display:column><a href="community/administrator/edit.do?communityId=${row.id}"><input type="button"  class="btn btn-primary" name="Edit"
				value="<spring:message code="community.edit"/>" /></a></display:column>
				
			<display:column>
				<div class="btn-group">
  				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
    				<spring:message code="daily.actions"/> <span class="caret"></span>
  				</button>
 
  				<ul class="dropdown-menu" role="menu">
				    
				    <li><a href="block/administrator/create.do?communityId=${row.id}"><spring:message code="block.create"/></a></li>
				    <li><a href="block/administrator/list.do?communityId=${row.id}"><spring:message code="block.list"/></a></li>
  				</ul>
				</div>
			</display:column>
				
		</security:authorize>
		
		<security:authorize access="hasRole('MANAGER')">
			<display:column>
				<div class="btn-group">
  				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
    				<spring:message code="daily.actions"/> <span class="caret"></span>
  				</button>
 
  				<ul class="dropdown-menu" role="menu">
				    <li><a href="block/manager/list.do?communityId=${row.id}"><spring:message code="block.list"/></a></li>
				    <li><a href="amanager/dashboard.do?communityId=${row.id}"><spring:message code="manager.dashboard"/></a></li>
  				</ul>
				</div>
			</display:column>
			
			<display:column>
				<div class="btn-group">
  				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
    				<spring:message code="communication.actions"/> <span class="caret"></span>
  				</button>
 
  				<ul class="dropdown-menu" role="menu">
				    <li><a href="announcement/manager/listbycommunity.do?communityId=${row.id}"><spring:message code="community.announcements"/></a></li>
				    <li><a href="announcement/manager/createbycommunity.do?communityId=${row.id}"><spring:message code="community.announcements.create"/></a></li>
				    <li><a href="incidence/manager/listbycommunity.do?communityId=${row.id}"><spring:message code="community.incidences"/></a></li>
  				</ul>
				</div>
			</display:column>
				
			<display:column>
				<div class="btn-group">
  				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
    				<spring:message code="budget.actions"/> <span class="caret"></span>
  				</button>
 
  				<ul class="dropdown-menu" role="menu">
				    <li><a href="payment/manager/list.do?communityId=${row.id}"><spring:message code="community.payments"/></a></li>
				    <li><a href="payment/manager/create.do?communityId=${row.id}"><spring:message code="payment.create"/></a></li>
				    <li><a href="payment/manager/importer.do?communityId=${row.id}"><spring:message code="payment.import.csv"/></a></li>
				    <li><a href="payment/manager/sheetdrive.do?communityId=${row.id}"><spring:message code="payment.sheet"/></a></li>
				    <li><a href="budgethistory/manager/list.do?communityId=${row.id}"><spring:message code="budgetHistory.list"/></a></li>
				    <li><a href="budgethistory/manager/create.do?communityId=${row.id}"><spring:message code="budgetHistory.create"/></a></li>
  				</ul>
				</div>
			</display:column>
		
<%-- 			<display:column><a href="block/manager/list.do?communityId=${row.id}"><input type="button" name="List" --%>
<%-- 				value="<spring:message code="block.list"/>" /></a></display:column> --%>
				
<%-- 			<display:column><a href="announcement/manager/listbycommunity.do?communityId=${row.id}"><input type="button" name="List" --%>
<%-- 				value="<spring:message code="community.announcements"/>" /></a></display:column> --%>
				
<%-- 			<display:column><a href="incidence/manager/listbycommunity.do?communityId=${row.id}"><input type="button" name="List" --%>
<%-- 				value="<spring:message code="community.incidences"/>" /></a></display:column> --%>
				
<%-- 			<display:column><a href="announcement/manager/createbycommunity.do?communityId=${row.id}"><input type="button" name="List" --%>
<%-- 				value="<spring:message code="community.announcements.create"/>" /></a></display:column> --%>
				
<%-- 			<display:column><a href="payment/manager/list.do?communityId=${row.id}"><input type="button" name="List" --%>
<%-- 				value="<spring:message code="community.payments"/>" /></a></display:column> --%>
				
<%-- 				<display:column><a href="payment/manager/create.do?communityId=${row.id}"><input type="button" name="List" --%>
<%-- 				value="<spring:message code="payment.create"/>" /></a></display:column> --%>
				
<%-- 				<display:column><a href="contract/manager/create.do?communityId=${row.id}"><input type="button" name="Assign" --%>
<%-- 				value="<spring:message code="contract.create"/>" /></a></display:column> --%>
				
<%-- 				<display:column><a href="contract/manager/list.do?communityId=${row.id}"><input type="button" name="List" --%>
<%-- 				value="<spring:message code="contract.list"/>" /></a></display:column> --%>
				
			<display:column>
				<div class="btn-group">
  				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
    				<spring:message code="contract.actions"/> <span class="caret"></span>
  				</button>
 
  				<ul class="dropdown-menu" role="menu">
				    <li><a href="contract/manager/create.do?communityId=${row.id}"><spring:message code="contract.create"/></a></li>
				    <li><a href="contract/manager/list.do?communityId=${row.id}"><spring:message code="contract.list"/></a></li>
  				</ul>
				</div>
			</display:column>
				
<%-- 				<display:column><a href="budgethistory/manager/list.do?communityId=${row.id}"><input type="button" name="List" --%>
<%-- 				value="<spring:message code="budgetHistory.list"/>" /></a></display:column> --%>
				
<%-- 				<display:column><a href="budgethistory/manager/create.do?communityId=${row.id}"><input type="button" name="Create" --%>
<%-- 				value="<spring:message code="budgetHistory.create"/>" /></a></display:column> --%>
				
<%-- 				<display:column><a href="amanager/dashboard.do?communityId=${row.id}"><input type="button" name="List" --%>
<%-- 				value="<spring:message code="manager.dashboard"/>" /></a></display:column> --%>
		</security:authorize>
		
		
	</display:table>

<br />

		<a href="<spring:url value='/' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="community.back"/>" /></a>
