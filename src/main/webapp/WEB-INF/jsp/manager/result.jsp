<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<rapid:override name="title">
    Result
</rapid:override>
<rapid:override name="css_js">

</rapid:override>

<rapid:override name="content">

    <div style="width: 30%; margin: 100px auto">
        <h2 style="width: 100%; text-align: center">${resultInfo.message}</h2>
    </div>

</rapid:override>
<%@ include file="base1.jsp"%>