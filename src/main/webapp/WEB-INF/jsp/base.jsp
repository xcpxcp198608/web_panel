<%@page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
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
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, user-scalable=1, minimum-scale=1.0, maximum-scale=1.0"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="format-detection" content="telephone=no"/>

    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">

    <script src="https://code.jquery.com/jquery-3.2.1.js"></script>

    <script type="application/javascript" src="https://cdn.bootcss.com/echarts/4.0.2/echarts.min.js" ></script>

    <script type="application/javascript" language="JavaScript" src="Resource/js/base.js"></script>
    <link rel="stylesheet" type="text/css" href="Resource/css/base.css">
    <link rel="shortcut icon" href="Resource/img/btv.ico">
    <rapid:block name="css_js" />

    <style>
        .loading{
            width: 100%; height: 100%; position: fixed; top: 0; left: 0; z-index: 2000; background: #dddddd;
        }
        .loading .pic{
            width: 60px; height: 50px; position: absolute; top: 0; bottom: 0; left: 0; right: 0; margin: auto;
        }
        .loading .pic i{
            display: block; float: left; width: 6px; height: 50px; background: #1a233c; margin: 0 2px;
            -webkit-transform: scaleY(0.3);
            -ms-transform: scaleY(0.3);
            transform: scaleY(0.3); -webkit-animation: loading 1.2s infinite; animation: loading 1.2s infinite;
        }
        .loading .pic i:nth-child(2){-webkit-animation-delay: 0.1s;animation-delay: 0.1s}
        .loading .pic i:nth-child(3){-webkit-animation-delay: 0.2s;animation-delay: 0.2s}
        .loading .pic i:nth-child(4){-webkit-animation-delay: 0.3s;animation-delay: 0.3s}
        .loading .pic i:nth-child(5){-webkit-animation-delay: 0.4s;animation-delay: 0.4s}
        .loading .pic i:nth-child(6){-webkit-animation-delay: 0.5s;animation-delay: 0.5s}

        @-webkit-keyframes loading {
            0%, 40%, 100%{-webkit-transform: scaleY(0.4);transform: scaleY(0.4)}
            20%{-webkit-transform: scaleY(1);transform: scaleY(1)}
        }

        @keyframes loading {
            0%, 40%, 100%{-webkit-transform: scaleY(0.4);transform: scaleY(0.4)}
            20%{-webkit-transform: scaleY(1);transform: scaleY(1)}
        }

    </style>

    <script>
        $(function () {
            document.onreadystatechange = function(){
                if(document.readyState === 'complete'){
                    $('.loading').fadeOut();
                }
            }
        });
    </script>

</head>

<body>

    <div class="loading">
        <div class="pic">
            <i></i>
            <i></i>
            <i></i>
            <i></i>
            <i></i>
            <i></i>
        </div>
    </div>

    <div class="container-fluid">
        <rapid:block name="modal"/>

        <div class="row" style="height: 100%">
            <div class="col-2 " style="background-color: #1a233c; box-shadow: 0 0 8px #000000; z-index: 1004; padding: 0">
                <br/>
                <div class="text-center">
                    <div class="col-12" style="padding: 0 35%">
                        <img src="Resource/img/logo_legacy_white.png" alt="logo" style="width: 100%">
                    </div>
                </div>
                <div class="text-center">
                    <div class="col-12" style="padding: 0">
                        <span style="color: #dddddd" ><small>preferred customer program</small></span>
                    </div>
                </div>

                <hr/>

                <rapid:block name="navigation"/>
            </div>


            <div class="col-10" >
                <div class="row d-flex" style="background-color: #ffffff; box-shadow: 0 0 8px #000000; z-index: 1003;">
                    <div class="col-11 text-center" style="padding: 10px">
                        <span class="text-secondary">welcome to preferred customer program!</span>
                    </div>
                    <div class="col-1 text-center" style="padding: 10px">
                        <span class="badge badge-dark">${username}</span>
                    </div>
                </div>

                <div id="p-content" style="margin-top: 10px">
                    <div class="container-fluid" style="padding:10px">
                    <rapid:block name="content"/>
                    </div>
                </div>
            </div>
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
            <h4 id="notice_message" style="color: #ff1268; font-size: 20px; width: 100%;
            text-align: center;"></h4>
        </div>
    </div>


    <script src="https://cdn.bootcss.com/popper.js/1.12.9/umd/popper.min.js"
            integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
            crossorigin="anonymous"></script>
    <script src="https://cdn.bootcss.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
</body>
</html>
