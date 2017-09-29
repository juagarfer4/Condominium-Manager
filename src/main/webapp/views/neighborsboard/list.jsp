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


	<display:table name="neighborsBoards" id="row" requestURI="${requestURI}" pagesize="5" class="table table-striped table-hover table-responsive">
	
		<spring:message code="neighborsboard.date" var="date" />
		<display:column title="${date}" property="date" format="{0,date,dd/MM/yyyy}" />
	
	<%-- 	<spring:message code="neighborsboard.record" var="record" /> --%>
	<%-- 	<display:column title="${record}" property="record" /> --%>
		
		<security:authorize access="hasRole('MANAGER')">
		<display:column><a href="owner/manager/list.do?neighborsBoardId=${row.id}"><input type="button" class="btn btn-primary" name="list"
				value="<spring:message code="neighborsboard.attendants"/>" /></a></display:column>
		
		<display:column><a href="neighborsboard/manager/add.do?neighborsBoardId=${row.id}"><input type="button" class="btn btn-primary" name="create"
				value="<spring:message code="neighborsboard.add"/>" /></a></display:column>
				
		<display:column><a href="neighborsboard/manager/PDF.do?neighborsBoardId=${row.id}"><input type="button" class="btn btn-primary" name="create"
				value="<spring:message code="neighborsboard.download"/>" /></a></display:column>
				
		</security:authorize>
		
		
		
		<security:authorize access="hasRole('OWNER')">
		<display:column><a href="owner/owner/list.do?neighborsBoardId=${row.id}"><input type="button" class="btn btn-primary" name="list"
				value="<spring:message code="neighborsboard.attendants"/>" /></a></display:column>
		
		<display:column><a href="neighborsboard/owner/PDF.do?neighborsBoardId=${row.id}"><input type="button" class="btn btn-primary" name="create"
				value="<spring:message code="neighborsboard.download"/>" /></a></display:column>
		</security:authorize>
		
	
		
	</display:table>

<br />

	<security:authorize access="hasRole('OWNER')">
		<a href="<spring:url value='property/owner/list.do' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="neighborsboard.back.properties"/>" /></a>
	</security:authorize>
	
	<security:authorize access="hasRole('MANAGER')">
		<a href="<spring:url value='community/manager/list.do' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="neighborsboard.back.communities"/>" /></a>
	</security:authorize>
	
	<a href="<spring:url value='/' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="neighborsboard.back"/>" /></a>
