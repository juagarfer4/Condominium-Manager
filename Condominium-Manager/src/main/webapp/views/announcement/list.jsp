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


	<display:table name="announcements" id="row" requestURI="${requestURI}" pagesize="5" class="table table-striped table-hover table-responsive">
	
		<spring:message code="announcement.name" var="name" />
		<display:column title="${name}" property="name" />
	
		<spring:message code="announcement.creationMoment" var="creationMoment" />
		<display:column title="${creationMoment}" property="creationMoment" format="{0,date,dd/MM/yyyy}" />
		
		<jstl:if test="${block != null}">
			<spring:message code="announcement.block.number" var="block.number" />
			<display:column title="${block}" property="block.number"/>
		</jstl:if>
		
		<jstl:if test="${community != null}">
			<spring:message code="announcement.community.name" var="community.name" />
			<display:column title="${community}" property="community.name"/>
		</jstl:if>
		
		<security:authorize access="hasRole('OWNER')">	
		<display:column><a href="announcement/owner/display.do?announcementId=${row.id}"><input type="button"  class="btn btn-primary" name="Display"
				value="<spring:message code="announcement.display"/>" /></a></display:column>
		</security:authorize>
		
		<security:authorize access="hasRole('MANAGER')">	
		<display:column><a href="announcement/manager/display.do?announcementId=${row.id}"><input type="button" class="btn btn-primary" name="Display"
				value="<spring:message code="announcement.display"/>" /></a></display:column>
		</security:authorize>
		
		<security:authorize access="hasRole('MANAGER')">
			<display:column>
				<a href="announcement/manager/edit.do?announcementId=${row.id}"><input type="button"  class="btn btn-primary" name="edit"
				value="<spring:message code="announcement.edit"/>" /></a>
			</display:column>
		</security:authorize>
		
		
	</display:table>

<br />

		<security:authorize access="hasRole('OWNER')">
			<a href="<spring:url value='property/owner/list.do' />"><input type="button" class="btn btn-primary" name="Back"
			value="<spring:message code="announcement.back.properties"/>" /></a>
		</security:authorize>
		
		<security:authorize access="hasRole('MANAGER')">
			<a href="<spring:url value='community/manager/list.do' />"><input type="button" class="btn btn-primary" name="Back"
			value="<spring:message code="announcement.back.communities"/>" /></a>
		</security:authorize>

		<a href="<spring:url value='/' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="announcement.back"/>" /></a>
