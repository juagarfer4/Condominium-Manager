<%--
 * footer.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<jsp:useBean id="date" class="java.util.Date" />


<!-- 	<div class="navbar navbar-default navbar-fixed-bottom"> -->
<!-- 		<div class="container" style="width: 97%;"> -->
<%-- 			<div class="navbar-text"><spring:message code="master.page.footer.cookies.1" /> --%>
<%-- 				<a class="navbar-btn btn-info btn" href="privacy/cookies.do"><spring:message code="master.page.footer.cookies.info" /></a> --%>
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->

<!-- El footer se divide en dos modulos: -->
<!-- Container: Este es el modulo mas ancho superior el cual contiene la newsletter y la info junto a lo de las redes sociales. -->
<!-- Copywrite: Este modulo es el de abajo donde viene quien ha diseñado la pagina y demas. El copywrite hay que dejarlo por el tema de derechos -->

<div class="footer">
	 <div class="container">
		 <div class="footer-grids">
			 <div class="col-md-6 news-ltr">
				 <h4><spring:message code="master.page.whatarewe.title" /></h4>
				 <p><spring:message code="master.page.whatarewe.message" /></p>
<!-- 				 <form>					  -->
<!-- 					  <input type="text" class="text" value="Enter Email" onfocus="this.value = '';" onblur="if (this.value == '') {this.value = 'Enter Email';}"> -->
<!-- 					 <input type="submit" value="Subscribe"> -->
<!-- 					 <div class="clearfix"></div> -->
<!-- 				 </form> -->
			</div>
			 <div class="col-md-6 ftr-grid1">
				 <h4><spring:message code="master.page.whyus.title" /></h4>
				 <p><spring:message code="master.page.whyus.message" /></p>
<!-- 				 <div class="social">							 -->
<!-- 					<a href="#"><i class="facebook"></i></a> -->
<!-- 					<a href="#"><i class="twitter"></i></a> -->
<!-- 					<a href="#"><i class="dribble"></i></a>	 -->
<!-- 					<a href="#"><i class="google"></i></a>	 -->
<!-- 					<a href="#"><i class="youtube"></i></a>	 -->
<!-- 			      </div> -->
			 </div>			 
			 <div class="clearfix"></div>
		 </div>		 
	 </div>
</div>

<div class="copywrite">
	 <div class="container">
<!-- 			 <p> © 2015 Goaway. All rights reserved | Design by <a href="http://w3layouts.com/">W3layouts</a></p> -->
	 </div>
</div>
