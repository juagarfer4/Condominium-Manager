 <%--
 * login.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
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

<link rel="stylesheet" href="styles/login.css" type="text/css">

<script type="text/javascript" src="scripts/login.js"></script>

<form:form action="j_spring_security_check" modelAttribute="credentials">

<%-- 	<form:label path="username"> --%>
<%-- 		<spring:message code="security.username" /> --%>
<%-- 	</form:label> --%>
<%-- 	<form:input path="username" />	 --%>
<%-- 	<form:errors class="error" path="username" /> --%>
<!-- 	<br /> -->

<%-- 	<form:label path="password"> --%>
<%-- 		<spring:message code="security.password" /> --%>
<%-- 	</form:label> --%>
<%-- 	<form:password path="password" />	 --%>
<%-- 	<form:errors class="error" path="password" /> --%>
<!-- 	<br /> -->
	
<%-- 	<jstl:if test="${showError == true}"> --%>
<!-- 		<div class="error"> -->
<%-- 			<spring:message code="security.login.failed" /> --%>
<!-- 		</div> -->
<%-- 	</jstl:if> --%>
	
<%-- 	<input type="submit" value="<spring:message code="security.login" />" /> --%>


	<section id="login">
	    <div class="container">
	    	<div class="row">
	    	    <div class="col-xs-12">
	        	    <div class="form-wrap">
	                <h1><spring:message code="security.login.loginmessage" /></h1>
<%-- 	                <form:form action="j_spring_security_check" modelAttribute="credentials"> --%>
	                    <form:form role="form" action="j_spring_security_check" modelAttribute="credentials" method="post" id="login-form" autocomplete="off">
	                        <div class="form-group">
	                            <label for="email" class="sr-only">Email</label>
	                            <form:input  name="username" path="username" id="username" class="form-control" placeholder="User name"/>
	                        </div>
	                        <div class="form-group">
	                            <label for="key" class="sr-only">Password</label>
	                            <form:password path="password" name="password" id="password" class="form-control" placeholder="Password" />
	                        </div>
	                        <input type="submit" id="btn-login" class="btn btn-custom btn-lg btn-block" value="<spring:message code="security.login" />" >
	                    </form:form>
	                    
	                    <jstl:if test="${showError == true}"> 
							<div class="error">
								<spring:message code="security.login.failed" />
							</div>
						</jstl:if>
						
						
	                    <hr>
	        	    </div>
	    		</div> <!-- /.col-xs-12 -->
	    	</div> <!-- /.row -->
	    </div> <!-- /.container -->
	</section>
	
	
</form:form>