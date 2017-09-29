<%--
 * layout.jsp
 *
 * Copyright (C) 2014 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<base
	href="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="shortcut icon" href="favicon.ico"/> 


<!--  Referencias a scripts -->
<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/jquery-ui.js"></script>
<script type="text/javascript" src="scripts/jmenu.js"></script>
<script type="text/javascript" src="scripts/jquery.min.js"></script>
<script type="text/javascript" src="scripts/bootstrap.min.js"></script>
<script src="scripts/bootstrap.js"></script>
<script src="scripts/responsiveslides.min.js"></script> 



<!--  Referencias a hojas de estilos -->
<link rel="stylesheet" href="styles/common.css" type="text/css">
<link rel="stylesheet" href="styles/jmenu.css" media="screen"
	type="text/css" />
<!--  <link rel="stylesheet" href="styles/displaytag.css" type="text/css">-->
<link rel="stylesheet" href="styles/bootstrap.min.css" media="screen"
	type="text/css" />
<link rel="stylesheet" href="styles/lightbox.css">
<link rel="stylesheet" href="styles/bootstrap-theme.min.css" media="screen"
	type="text/css" />
<link href="styles/style.css" rel='stylesheet' type='text/css' />




<!--//theme style-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Goaway Responsive web template, Bootstrap Web Templates, Flat Web Templates, Andriod Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyErricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>


<title><tiles:insertAttribute name="title" ignore="true" /></title>

<script type="text/javascript">
	$(document).ready(function() {
		$("#jMenu").jMenu();
	});

	function askSubmission(msg, form) {
		if (confirm(msg))
			form.submit();
	}
</script>

</head>

<body>
<!-- HEADER -->
	<div >
		<tiles:insertAttribute name="header" />
	</div>
	
<!-- CONTAINER -->
	<div class="container" style="width: 97%;">
	<br/>
	<br/>
<!-- ESTE TITULO NO DEBE MOSTRARSE -->
<!-- 		<h1> -->
<%-- 			<tiles:insertAttribute name="title" /> --%>
<!-- 		</h1> -->
<jstl:if test="${(requestURI != index)||(requestURI != login)}">
		<div class="ftr-grid1 h4">
		<h4>
			<tiles:insertAttribute name="title" />
		</h4>
		</div>
</jstl:if>	
<jstl:if test="${(actionURI != index)||(actionURI != login)}">
		<div class="ftr-grid1 h4">
		<h4>
			<tiles:insertAttribute name="title" />
		</h4>
		</div>
</jstl:if>	


		<tiles:insertAttribute name="body" />	
		<jstl:if test="${message != null}">
			<br />
			<span class="message"><spring:message code="${message}" /></span>
		</jstl:if>	
		
	</div>
	<br />
<!-- FOOTER -->
	<div >
		<tiles:insertAttribute name="footer" />
	</div>
	
<!-- otras referencias a scripts -->

<script src="scripts/lightbox-plus-jquery.min.js"></script>
<!---->
<!--/animatedcss-->
<script type="text/javascript" src="scripts/move-top.js"></script>
<script type="text/javascript" src="scripts/easing.js"></script>
<!--/script-->
<script type="text/javascript">
			jQuery(document).ready(function($) {
				$(".scroll").click(function(event){		
					event.preventDefault();
					$('html,body').animate({scrollTop:$(this.hash).offset().top},900);
				});
			});
</script>
<!--script-->
<script type="text/javascript">
		$(document).ready(function() {
				/*
				var defaults = {
				containerID: 'toTop', // fading element id
				containerHoverID: 'toTopHover', // fading element hover id
				scrollSpeed: 1200,
				easingType: 'linear' 
				};
				*/
		$().UItoTop({ easingType: 'easeOutQuart' });
});
</script>
<a href="#to-top" id="toTop" style="display: block;"> <span id="toTopHover" style="opacity: 1;"> </span></a>
<!---->

	
</body>
</html>