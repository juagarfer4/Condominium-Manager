<%--
 * header.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>


<!-- header -->
<div class="top-header">
	 <div class="container">
		 <div class="logo">
<!-- 			 <h1><a href="index.html"><span class="glyphicon glyphicon-road" aria-hidden="true"></span>Goaway</a></h1> -->
			 <h1><a href=""><span class="glyphicon glyphicon-home"> </span> ConMan</a></h1>
		 </div>		 
	 </div>
</div>
<!---->
<div class="top-menu">
	 <div class="container">
	  <div class="content white">
		 <nav class="navbar navbar-default">
			 <div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>				
			 </div>
			 <!--/navbar header-->		
			 <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
				 <ul class="nav navbar-nav">
					 <li class="active"><a href="">Home</a></li>
					 <li class="dropdown">
					 	 <security:authorize access="hasRole('ADMINISTRATOR')">
					        <a href="#" class="scroll dropdown-toggle" data-toggle="dropdown"><spring:message code="master.page.administrator.action" /><b class="caret"></b></a>
								<ul class="dropdown-menu">
						         	<li><a href="administrator/administrator/create.do"><spring:message code="master.page.register.administrator" /></a></li>
									<li><a href="amanager/administrator/create.do"><spring:message code="master.page.register.manager" /></a></li>
									<li><a href="owner/administrator/create.do"><spring:message code="master.page.register.owner" /></a></li>
									<li><a href="employee/administrator/create.do"><spring:message code="master.page.register.employee" /></a></li>
									<li><a href="community/administrator/create.do"><spring:message code="master.page.register.community" /></a></li>
									<li><a href="community/administrator/list.do"><spring:message code="master.page.list.administrator.community" /></a></li>
								</ul>
					        </security:authorize>
					        	
					        <security:authorize access="hasRole('MANAGER')">
						        <a href="#" class="scroll dropdown-toggle" data-toggle="dropdown"><spring:message code="master.page.manager.action" /><b class="caret"></b></a>
									<ul class="dropdown-menu">
						         		<li><a href="community/manager/list.do"><spring:message code="master.page.list.manager.community" /></a></li>
						         	</ul>
					        </security:authorize>
					        
					         <security:authorize access="hasRole('OWNER')">
						         <a href="#" class="scroll dropdown-toggle" data-toggle="dropdown"><spring:message code="master.page.owner.action" /><b class="caret"></b></a>
									<ul class="dropdown-menu">
						         		<li><a href="property/owner/list.do"><spring:message code="master.page.list.owner.property" /></a></li>
						         		<li><a href="folder/owner/list.do"><spring:message code="master.page.list.owner.folder" /></a></li>
						         	</ul>
					        </security:authorize>
					        
					        <security:authorize access="hasRole('EMPLOYEE')">
						        <a href="#" class="scroll dropdown-toggle" data-toggle="dropdown"><spring:message code="master.page.employee.action" /><b class="caret"></b></a>
									<ul class="dropdown-menu">
						         		<li><a href="contract/employee/list.do"><spring:message code="master.page.list.employee.manager" /></a></li>
						         	</ul>
					        </security:authorize>
					        
					        
					        <security:authorize access="isAnonymous()">
					         <a href="#" class="scroll dropdown-toggle" data-toggle="dropdown"><spring:message code="master.page.anonymous.action" /><b class="caret"></b></a>
									<ul class="dropdown-menu">
										<li><a href="security/login.do"><span class="glyphicon glyphicon-log-in"></span> <spring:message code="master.page.login" /> </a></li>	
					         		</ul>
	        				</security:authorize>
					 </li>					
					 <li class="dropdown">
						<a href="#" class="scroll dropdown-toggle" data-toggle="dropdown"><spring:message code="master.page.legal" /><b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="privacy/cookies.do"><spring:message code="master.page.legal.transpositions" /></a></li>
							<li><a href="privacy/lopd-lssi.do"><spring:message code="master.page.legal.lopd-lssi" /></a></li>
							<li><a href="privacy/unsubscribe.do"><spring:message code="master.page.legal.unsubscribe" /></a></li>
						</ul>
					 </li>
					 <li class="dropdown">
						<a href="#" class="scroll dropdown-toggle" data-toggle="dropdown"><spring:message code="master.page.lang" /><b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a  href="?language=en">	<spring:message code="master.page.en" /></a></li>
							<li><a  href="?language=es">	<spring:message code="master.page.es" /></a></li>
<%-- 							<li><a  href="?language=en"><img src="images/en.gif" />		<spring:message code="master.page.en" /></a></li> --%>
<%-- 							<li><a  href="?language=es"><img src="images/es.gif" />		<spring:message code="master.page.es" /></a></li> --%>
						</ul>
					 </li>
<!-- 					 <li><a href="contact.html">Contact</a></li> -->
					 <security:authorize access="isAnonymous()">
	       		 		<li><a href="security/login.do"><span class="glyphicon glyphicon-log-in"></span> <spring:message code="master.page.login" /> </a></li>
	        		</security:authorize>
	        		
	       		 	<security:authorize access="isAuthenticated()">
		       		 	 <li class="dropdown">
							<a href="#" class="scroll dropdown-toggle" data-toggle="dropdown"><security:authentication property="principal.username" /><b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="j_spring_security_logout"> <span class="glyphicon glyphicon-log-out"></span> <spring:message code="master.page.logout" /></a></li>
							</ul>
						 </li>
	        		</security:authorize>
				 </ul>
				</div>
			  <!--/navbar collapse-->
		 </nav>
		  <!--/navbar-->		 
	  </div>
	 <div class="clearfix"></div>
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
		<script type="text/javascript" src="scripts/bootstrap-3.1.1.min.js"></script>
		</div>
</div>