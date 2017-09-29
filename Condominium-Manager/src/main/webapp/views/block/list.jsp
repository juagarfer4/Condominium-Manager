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


	<display:table name="blocks" id="row" requestURI="${requestURI}" pagesize="5" class="table table-striped table-hover table-responsive">
	
		<spring:message code="block.number" var="number" />
		<display:column title="${number}" property="number" />
	
		<spring:message code="block.code" var="code" />
		<display:column title="${code}" property="code" />
		
		<spring:message code="block.address" var="address" />
		<display:column title="${address}" property="address" />
		
		<spring:message code="block.numberOfFloors" var="numberOfFloors" />
		<display:column title="${numberOfFloors}" property="numberOfFloors"/>
		
		<spring:message code="block.numberOfDoors" var="numberOfDoors" />
		<display:column title="${numberOfDoors}" property="numberOfDoors"/>
		
		<security:authorize access="hasRole('ADMINISTRATOR')">
		
			<display:column><a href="block/administrator/edit.do?blockId=${row.id}"><input type="button" class="btn btn-primary" name="Edit"
				value="<spring:message code="block.edit"/>" /></a></display:column>
				
				
						<display:column>
				<div class="btn-group">
  				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
    				<spring:message code="daily.actions"/> <span class="caret"></span>
  				</button>
 
  				<ul class="dropdown-menu" role="menu">
  				
				
				    <li><a href="property/administrator/create.do?blockId=${row.id}"><spring:message code="property.create"/></a></li>
				    <li><a href="property/administrator/list.do?blockId=${row.id}"><spring:message code="block.properties"/></a></li>
				   
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
  				
				
				    <li><a href="property/manager/list.do?blockId=${row.id}"><spring:message code="block.properties"/></a></li>
				    <li><a href="neighborsboard/manager/list.do?blockId=${row.id}"><spring:message code="block.neighborsboards"/></a></li>
				    <li><a href="neighborsboard/manager/create.do?blockId=${row.id}"><spring:message code="block.neighborsboard.create"/></a></li>
				    <li><a href="chargehistory/manager/list.do?blockId=${row.id}"><spring:message code="block.chargehistory.list"/></a></li>
				    <li><a href="chargehistory/manager/create.do?blockId=${row.id}"><spring:message code="block.chargehistory.create"/></a></li>
  				</ul>
				</div>
				</display:column>
				
				<display:column>
				<div class="btn-group">
  				<button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
    				<spring:message code="communication.actions"/> <span class="caret"></span>
  				</button>
 
  				<ul class="dropdown-menu" role="menu">
				    <li><a href="announcement/manager/listbyblock.do?blockId=${row.id}"><spring:message code="block.announcements"/></a></li>
					<li><a href="incidence/manager/listbyblock.do?blockId=${row.id}"><spring:message code="block.incidences"/></a></li>
					<li><a href="announcement/manager/createbyblock.do?blockId=${row.id}"><spring:message code="block.announcements.create"/></a></li>
  				</ul>
				</div>
				</display:column>
				
		</security:authorize>
		
	</display:table>


<br />

		<security:authorize access="hasRole('MANAGER')">
			<a href="<spring:url value='community/manager/list.do' />"><input type="button" class="btn btn-primary" name="Back"
			value="<spring:message code="block.community.back"/>" /></a>
		</security:authorize>
		
		<security:authorize access="hasRole('ADMINISTRATOR')">
			<a href="<spring:url value='community/administrator/list.do' />"><input type="button" class="btn btn-primary" name="Back"
			value="<spring:message code="block.community.back"/>" /></a>
		</security:authorize>

		<a href="<spring:url value='/' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="block.back"/>" /></a>
