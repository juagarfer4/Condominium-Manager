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


	<display:table name="chargeHistories" id="row"
		requestURI="${requestURI}" pagesize="5" class="table table-striped table-hover table-responsive">
	
	
		<spring:message code="chargehistory.mandateBeginning"
			var="mandateBeginning" />
		<display:column title="${mandateBeginning}" property="mandateBeginning"  format="{0,date,dd/MM/yyyy}" sortable="true"/>
	
		<spring:message code="chargehistory.mandateEnding" var="mandateEnding" />
		<display:column title="${mandateEnding}" property="mandateEnding"  format="{0,date,dd/MM/yyyy}" sortable="true"/>
	
		<spring:message code="chargehistory.isPresident.list" var="isPresident" />
		<display:column title="${isPresident}" property="isPresident" />
	
		<spring:message code="chargehistory.owner" var="identifier" />
		<display:column title="${identifier}" property="owner.identifier" />
	
		<spring:message code="chargehistory.block" var="code" />
		<display:column title="${code}" property="block.code" />
	
		<display:column>
		<jstl:if test="${row.mandateEnding==null}">
			
				<a href="chargehistory/manager/edit.do?chargeHistoryId=${row.id}"><input
					type="button" class="btn btn-primary" name="Edit"
					value="<spring:message code="chargehistory.edit"/>" /></a>
			
		</jstl:if>
		</display:column>
	
	</display:table>


<br />

<a href="<spring:url value='/' />"><input type="button"  class="btn btn-primary" name="Back"
	value="<spring:message code="chargehistory.back"/>" /></a>
