<%--
 * index.jsp
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


<!-- INDEX DE WELCOME -->

<div class="banner">
	 <div class="container">
		 <div class="banner-grids">
			<div class="col-md-6 banner-grid grid1">
				  <div class="package">
					 <h3><a><spring:message code="welcome.manager.name"/></a></h3>
					 <ul>
						 <li><p><spring:message code="welcome.manager.description"/></p></li>
					 </ul>
				 </div>
			 </div>
			 <div class="col-md-6 banner-grid">
				 <div class="package">
					 <h3><a><spring:message code="welcome.owner.name"/></a></h3>
					 <ul>
						 <li><p><spring:message code="welcome.owner.description"/></p></li>
					 </ul>
				 </div>
			 </div>
			 <div class="clearfix"></div>
		 </div>
	 </div>
</div>
<!---->
<div class="welcome">
	 <div class="container">
		 <div class="welcome-grids">
			  <div class="col-md-6 welcm-pic">
				 <img src="images/wel.jpg" class="img-responsive" alt=""/>
			  </div>
			  <div class="col-md-6 wel-info">
				 <h2><spring:message code="welcome.welcome.title"/></h2>
				 <h4><spring:message code="welcome.welcome.subtitle"/></h4>
				 <p><spring:message code="welcome.welcome.description"/></p>
			  </div>
			  <div class="clearfix"></div>
		 </div>
	 </div>
</div>
<!---->
<div class="choose">
	 <h3><spring:message code="welcome.management.title"/></h3>
	 <div class="container">		 
		 <div class="choos-girds">
			 <div class="col-md-4 choos-grid">
<!-- 				 <span class="glyphicon glyphicon-user" aria-hidden="true"></span> -->
				 <span class="glyphicon glyphicon-heart" ></span>
				 <h4><spring:message code="welcome.management.communities"/></h4>
				 <p><spring:message code="welcome.management.description1"/></p>
			 </div>
			 <div class="col-md-4 choos-grid">
<!-- 				 <span class="glyphicon glyphicon-heart" aria-hidden="true"></span> -->
				 <span class="glyphicon glyphicon-heart" ></span>
				 <h4><spring:message code="welcome.management.blocks"/></h4>
				 <p><spring:message code="welcome.management.description2"/></p>
			 </div>
			 <div class="col-md-4 choos-grid">
<!-- 				 <span class="glyphicon glyphicon-bullhorn" aria-hidden="true"></span> -->
				 <span class="glyphicon glyphicon-heart" ></span>
				 <h4><spring:message code="welcome.management.properties"/></h4>
				 <p><spring:message code="welcome.management.description3"/></p>
			 </div>
			 <div class="clearfix"></div>
		 </div>
	 </div>
</div>

</br>
</br>
</br>
