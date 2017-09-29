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


<form:form action="${actionURI}" modelAttribute="property">
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="renters" />
	<form:hidden path="invoices" />
	<form:hidden path="block" />
	
	<jstl:if test="${property.id == 0}">
	<br />
<%-- 		<acme:textbox code ="property.floor"  path="floor"/> --%>
<!-- 	<br /> -->


<%-- 	<form:label path="floor"> --%>
<%-- 				<spring:message code="property.floor" /> --%>
<%-- 			</form:label> --%>
<%-- 			<form:select path="floor" > --%>
<%-- 				<form:option label="-----------" value="0"/> --%>
<%-- 				<form:options items="${floors}" itemLabel="intValue" itemValue="id" /> --%>
<%-- 			</form:select> --%>
<%-- 			<form:errors cssClass="error" path="floor" /> --%>
<!-- 	<br /> -->

		<form:label path="floor">
				<spring:message code="property.floor" />
			</form:label>

		<form:select path="floor" cssClass="form-control">
			<jstl:forEach var="i" begin="1" end="${property.block.numberOfFloors}">
				<form:option label="${i}" value="${i}"/>
			</jstl:forEach>
		</form:select>
		<form:errors cssClass="error" path="floor" />
		<br />
	
		<acme:textbox code ="property.door"  path="door" cssClass="form-control"/>
	<br />
	</jstl:if>
	<jstl:if test="${property.id != 0}">
		<form:hidden path="floor" />
		<form:hidden path="door" />
	</jstl:if>
	
	<acme:select items="${owners}" itemLabel="identifier"  code="property.owner" path="owner" cssClass="form-control"/>
	<br />
	
		<acme:submit code="property.register" name="save" />
	&nbsp;
	
	<a href="<spring:url value='community/administrator/list.do' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="property.cancel"/>" /></a>





	
	
</form:form>
