<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title><rapid:block name="title"/>--Preferred customer program</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=1, minimum-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="format-detection" content="telephone=no"/>
    <link rel="stylesheet"
          href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
          crossorigin="anonymous">
    <script type="application/javascript" language="JavaScript" src="Resource/js/jquery-3.2.1.min.js"></script>
    <script type="application/javascript" language="JavaScript" src="Resource/js/echarts.min.js"></script>
    <script type="application/javascript" language="JavaScript" src="Resource/js/common.js"></script>
    <script type="application/javascript" language="JavaScript" src="Resource/js/base.js"></script>
    <link rel="stylesheet" type="text/css" href="Resource/css/base.css">
    <link rel="shortcut icon" href="Resource/img/btv.ico">
    <rapid:block name="css_js" />

</head>

<body>

    <div id="header">
        <a id="header_title">PREFERRED CUSTOMER PROGRAM</a>
        <a id="username" style="font-size: 16px">${username}</a>
    </div>

    <div id="navigation">
        <rapid:block name="navigation"/>
    </div>

    <div id="content">
        <rapid:block name="content" />
    </div>

    <div id="loading" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%;
        background-color: rgba(0,0,0,0.3); z-index: 1000; display: none">
        <div style="width: 25%; margin: 350px auto">
            <div class="progress">
                <div class="progress-bar progress-bar-danger  progress-bar-striped active" role="progressbar"
                     aria-valuenow="100" aria-valuemin="0" aria-valuemax="100" style="width: 100%">
                </div>
            </div>
        </div>
    </div>

    <div id="notice" style="position: absolute; top: 0; left: 0; width: 100%; height: 100%;
        background-color: rgba(0,0,0,0.3); z-index: 1001; display: none">
        <div style="width:50%; margin: 300px auto">
            <h4 id="notice_message" style="color: greenyellow; font-size: 20px; width: 100%;
            text-align: center;"></h4>
        </div>
    </div>

    <div id="details" style="width: 100%; height: 100%; z-index: 999; position: absolute; left: 0;
        top: 0; background-color: rgba(0,0,0,0.3); display: none; ">
        <div style="width: 50%; height: 460px; margin: 150px auto; background-color: #2c343c;
            box-shadow: 0 0 5px #000">
            <span class="glyphicon glyphicon-remove" aria-hidden="true" id="closeDetails"
                  style="float: right; color: whitesmoke; margin: 10px; font-size: 20px; cursor: pointer"></span>
            <div style="margin: 30px; height: 400px; overflow: scroll; clear: both">
                <rapid:block name="details"/>
            </div>
        </div>
    </div>

</body>
</html>
