<%--
 * panic.jsp
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

<%@taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- <p><spring:message code="panic.text" /> <code>${name}</code>.</p> --%>

<%-- <h2><spring:message code="panic.message" /></h2> --%>

<!-- <p style="font-family: 'Courier New'"> -->
<%-- 	${exception} --%>
<!-- </p> -->

<%-- <h2><spring:message code="panic.stack.trace" /></h2> --%>

<!-- <p style="font-family: 'Courier New'">	 -->
<%-- 	${stackTrace} --%>
<!-- </p> -->
<!-- <br><br> -->
 <!--container start-->
    <div class="gray-bg">
    <div class="fof">
            <!-- 404 error -->
        <div class="container  error-inner wow flipInX">
            <h1 class="text-center" style="color:#fd7364;"><spring:message code="misc.notfound" /></h1>
            <p class="text-center"><spring:message code="misc.error"/></p>
            <br>
          	 <p class="text-center"> <a class="btn btn-primary btn-sm" href=""><spring:message code="misc.back"/></a></p>
        </div>
        <!-- /404 error -->
        </div>
    </div>
    <br><br>