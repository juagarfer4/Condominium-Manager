<%--
 * show.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!-- <div class="dn-breadcrumbs"> -->
<!--   <div class="dn-breadcrumbs-background-services"></div> -->
<!--   <div class="container"> -->
<!--     <div class="row"> -->
<!--       <div class="span16"> -->
<!--         <ul class="breadcrumb"> -->
			
<div class="jumbotron">
  <div class="container">
		
			<h3><spring:message code="payment.step1" /></h3>
			<br />
			<spring:message code="payment.step1.1" />
			<br />
			<spring:message code="payment.step1.2" />
			<br />
			<spring:message code="payment.step1.3" />
			<br />
			<br />
			<div><img src="images/step1.png" class="img-responsive" alt="" style="text-align:center;"/></div>
			<br />
			<h3><spring:message code="payment.step2" /></h3>
			<br />
			<spring:message code="payment.step2.1" />
			<br />
			<spring:message code="payment.step2.2" />
			<br />
			<br />
			<div><img src="images/step2.png" class="img-responsive" alt="" style="align: center;"/></div>
			<br />
			<h3><spring:message code="payment.step3" /></h3>
			<br />
			<spring:message code="payment.step3.1" />
			<br />
			<spring:message code="payment.step3.2" />
			<br />
			<br />
			<div><img src="images/step3.png" class="img-responsive" alt="" style="align: center;"/></div>
			<br />
			<h3><spring:message code="payment.step4" /></h3>
			<br />
			<spring:message code="payment.step4.1" />
			<br />
			<br />
			<div><img src="images/step4.png" class="img-responsive" alt="" style="align: center;"/></div>
			<br />
			<br />
			<h3><spring:message code="payment.step5" /></h3>
			<br />
			<spring:message code="payment.step5.1" />
			<br />
			<br />
			<div><img src="images/step5.png" class="img-responsive" alt="" style="align: center;"/></div>
			
	</div>
	</div>
<!-- 		</ul> -->
<!--       </div> -->
<!--     </div> -->
<!--   </div> -->
<!-- </div> -->

<br />

	<security:authorize access="hasRole('MANAGER')">	
		<a href="<spring:url value='community/manager/list.do' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="payment.back.communities"/>" /></a>
	</security:authorize>
	
	<security:authorize access="hasRole('OWNER')">
		<a href="<spring:url value='property/owner/list.do' />"><input type="button" class="btn btn-primary" name="Back"
		value="<spring:message code="payment.back.properties"/>" /></a>
	</security:authorize>
