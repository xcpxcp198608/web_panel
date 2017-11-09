<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<rapid:override name="title">
    Error
</rapid:override>
<rapid:override name="css_js">
</rapid:override>

<rapid:override name="navigation">

</rapid:override>

<rapid:override name="content">
    <%=exception.getCause().getMessage() %><br>

</rapid:override>

<rapid:override name="details">

</rapid:override>
<%@ include file="WEB-INF/jsp/base.jsp"%>