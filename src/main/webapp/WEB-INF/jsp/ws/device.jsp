<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<rapid:override name="title">
    Device
</rapid:override>
<rapid:override name="css_js">
    <link rel="stylesheet" href="Resource/css/ws/device.css" />
    <script type="application/javascript" src="Resource/js/ws/device.js"></script>
</rapid:override>

<rapid:override name="content">

    <div style="width: 30%; margin: 100px auto">
        <a id="send">send</a> <a id="close">close</a>
    </div>

</rapid:override>
<%@ include file="../base1.jsp"%>