<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <base href="<%=basePath%>">

  <title>SignIn--Preferred customer program</title>

  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0" />
  <meta name="apple-mobile-web-app-capable" content="yes" />
  <meta name="format-detection" content="telephone=no" />

  <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/4.0.0/css/bootstrap.min.css"
        integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdn.bootcss.com/octicons/4.4.0/font/octicons.min.css">
  <script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>

  <script type="application/javascript" language="JavaScript" src="Resource/js/base.js"></script>
  <link rel="stylesheet" type="text/css" href="Resource/css/base.css">
  <link rel="shortcut icon" href="Resource/img/btv.ico">

  <style>

    .form-signIn {
      width: 100%;
      max-width: 60%;
      max-height: 100%;
      margin: 30% auto 0 auto;
    }

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

<div id="bootLoading">

</div>

<div style="width: 100%; height: 100%; background-image: url(Resource/img/b2.jpg); background-position-x: center;
    background-position-y: center; position: absolute; left: 0; top: 0">

    <div style="padding: 20px">
      <span class="text-light" style="padding: 20px;">Preferred Customer Program</span>
    </div>

    <form class="form-signIn" action="/panel/signin" method="post">
      <input type="hidden" name="type" value="0">
      <div class="form-row">
        <div class="col-5">
          <input type="text" class="form-control" placeholder="Username" name="username">
        </div>
        <div class="col-5">
          <input type="password" class="form-control" placeholder="Password" name="password">
        </div>
        <div class="col-2">
          <button type="submit" class="btn btn-primary">Sign In</button>
        </div>
      </div>

      <p class="mt-5 mb-3 text-secondary text-center">&copy; 2017-2018</p>
    </form>

</div>

<script src="https://cdn.bootcss.com/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://cdn.bootcss.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
</body>
</html>