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


	<display:table name="messages" id="row" requestURI="${requestURI}" pagesize="5" class="table table-striped table-hover table-responsive">
	
		<spring:message code="message.moment" var="moment" />
		<display:column title="${moment}" property="moment" format="{0,date,dd/MM/yyyy HH:mm}" sortable="true"/>
		
		<spring:message code="message.subject" var="subject" />
		<display:column title="${subject}" property="subject" />
		
		<jstl:if test="${row.folder.name!='OUTBOX'}">
			<spring:message code="message.sender.identifier" var="identifier" />
			<display:column title="${identifier}" property="sender.identifier" />
		</jstl:if>
		
		<jstl:if test="${row.folder.name!='INBOX'}">
			<spring:message code="message.recipient.identifier" var="identifier" />
			<display:column title="${identifier}" property="recipient.identifier" />
		</jstl:if>
		
		<display:column><a href="message/owner/display.do?messageId=${row.id}"><input type="button" class="btn btn-primary" name="Display"
				value="<spring:message code="message.display"/>" /></a></display:column>
				
		<display:column><a href="message/owner/delete.do?messageId=${row.id}"><input class="btn btn-primary" onclick="return confirm('<spring:message code="message.confirm.delete"/>')" type="button" name="Display"
				value="<spring:message code="message.delete"/>" /></a></display:column>
	
	</display:table>

<br />

		<a href="<spring:url value='folder/owner/list.do' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="message.back"/>" /></a>
