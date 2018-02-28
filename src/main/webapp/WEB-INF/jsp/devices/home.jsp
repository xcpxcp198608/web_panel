<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<rapid:override name="title">
    Home
</rapid:override>
<rapid:override name="css_js">
    <script type="application/javascript" src="Resource/js/devices/home.js"></script>
</rapid:override>


<rapid:override name="content">

    <div class="row" style="padding: 0 10px 0 10px">
        <div style="width: 100%; background-color: #0815a8; height: 3px"></div>
        <div class="col-5" style="padding: 10px; background-color: white">
            <span class="text-center text-muted" style="padding: 10px"></span>
        </div>
        <div class="col-4 text-left text-darks" style="background-color: white; padding: 10px">
            <span><span id="aYear">2018</span>-<span id="aMonth">01</span></span>
        </div>
        <div class="col-3 text-right" style="padding: 10px; background-color: white">
            <a id="btPreviousMonth" data-toggle="tooltip" title="Previous Month">
                <span class="badge badge-info text-center">Previous</span>
            </a>
            <a id="btNextMonth" data-toggle="tooltip" title="Next Month">
                <span class="badge badge-info text-center">Next</span>
            </a>
        </div>
    </div>




</rapid:override>
<%@ include file="base_devices.jsp"%>