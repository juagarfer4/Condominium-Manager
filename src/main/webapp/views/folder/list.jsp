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


	<display:table name="folders" id="row" requestURI="${requestURI}" pagesize="5" class="table table-striped table-hover table-responsive">
	
		<display:column titleKey="folder.name">
			<jstl:if test="${row.name=='INBOX'}">
				<spring:message code="folder.name.inbox"></spring:message>
			</jstl:if>
			<jstl:if test="${row.name=='OUTBOX'}">
				<spring:message code="folder.name.outbox"></spring:message>
			</jstl:if>
			<jstl:if test="${row.name=='TRASH'}">
				<spring:message code="folder.name.trash"></spring:message>
			</jstl:if>
		</display:column>
		
		<display:column><a href="message/owner/list.do?folderId=${row.id}"><input type="button" class="btn btn-primary" name="List"
		value="<spring:message code="folder.messages"/>" /></a></display:column>
	
	</display:table>

<br />

<!-- 		<a href="message/owner/create.do"><input type="button" name="Create" -->
<%-- 		value="<spring:message code="message.create"/>" /></a> --%>
		
		&nbsp;

		<a href="<spring:url value='/' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="folder.back"/>" /></a>
