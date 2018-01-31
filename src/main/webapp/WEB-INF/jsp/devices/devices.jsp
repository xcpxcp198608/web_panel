<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">

    <title>Device--Preferred customer program</title>

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
    <script
            src="https://code.jquery.com/jquery-3.2.1.js"
            integrity="sha256-DZAnKJ/6XZ9si04Hgrsxu/8s717jcIzLy3oi35EouyE="
            crossorigin="anonymous"></script>
    <link rel="shortcut icon" href="Resource/img/btv.ico">
    <script type="application/javascript" src="Resource/js/device/devices.js"></script>

</head>
<body>
    <div id="content" style="padding: 10px">
        <div style="width: 100%; height: 40px;">
            <div style="width: 9%; display: block; float: left;">
                <button type="button" class="btn btn-default" id="btCheckIn" title="create a sales">
                    <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Check In
                </button>
            </div>

            <div style="width: 86%; display: block; float: left;">
                <div class="input-group">
                    <span class="input-group-addon" id="basic-addon1">Search</span>
                    <input type="text" class="form-control" placeholder="type in keyword"
                           aria-describedby="basic-addon1" id="ipSearch">
                </div>
            </div>

            <div style="width: 5%; display: block; float: right; font-size: 18px; font-weight: 500;
            text-align: right; align-content: center">
                <span id="spTotalCount" style="height: 34px; line-height: 34px"></span>
            </div>
        </div>
        <br/>


        <div>
            <table class="table table-bordered table-hover table-striped table-condensed"
                   id="tbDevices">
                <thead style="background-color: #566778;">
                <tr>
                    <th>#</th>
                    <th>Id</th>
                    <th>Mac</th>
                    <th>Sales</th>
                    <th>CheckInTime</th>
                </tr>
                </thead>
                <tbody style="font-size: 14px">
                <c:forEach items="${deviceRentInfoList}" var="deviceRentInfo" varStatus="status">
                    <tr>
                        <td><input type="radio" name="rdDevice" value="${deviceRentInfo.mac}"
                                   currentRow="${status.index}"></td>
                        <td>${status.index+1}</td>
                        <td>${deviceRentInfo.mac}</td>
                        <td>${deviceRentInfo.salesName}</td>
                        <td>${deviceRentInfo.createTime}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
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
                <div style="margin: 20px">
                    <div id="div_create">
                        <h3 style="width: 100%; text-align: center; color: whitesmoke">CHECK IN DEVICE</h3>
                        <br/>

                        <div style="width: 60%; display: block; margin: 20px auto">
                            <div class="input-group">
                                <span class="input-group-addon" id="basic-addon6">
                                    <span class="glyphicon glyphicon-barcode" aria-hidden="true"></span>
                                </span>
                                <input id="ipMac" type="email" class="form-control" placeholder="Mac"
                                                   aria-describedby="basic-addon6" name="mac" maxlength="17">
                            </div>
                             <br/>
                        </div>

                        <br style="clear: both"/>
                        <div style="width: 60%; margin: 15px auto; clear: both">
                            <button id="btSubmitCheckIn" type="submit" class="btn btn-primary" style="width: 100%; margin: auto">
                                Check In
                            </button>
                        </div>
                        <h5 id="errorMessage" style="color: red; width: 100%; text-align: center"></h5>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>