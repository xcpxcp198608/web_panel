<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<rapid:override name="title">
    Distribution
</rapid:override>
<rapid:override name="css_js">
    <script type="application/javascript" src="Resource/js/world.js"></script>
    <script type="application/javascript" src="Resource/js/admin/distribution.js"></script>
</rapid:override>


<rapid:override name="content">
        <div style="width: 100%">
            <div style="width: 100%; background-color: #0815a8; height: 3px"></div>
            <div style="background-color: #ffffff; width: 100%">
                <div id="chart_distribution" style="width: 100%; height: 680px;"></div>
            </div>
        </div>
</rapid:override>
<%@ include file="base_admin.jsp"%>