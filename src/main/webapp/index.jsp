<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
  String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
  <base href="<%=basePath%>">

  <title>SignIn--BTV RENTAL SYSTEM</title>

  <meta http-equiv="pragma" content="no-cache">
  <meta http-equiv="cache-control" content="no-cache">
  <meta http-equiv="expires" content="0">
  <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
  <meta http-equiv="description" content="This is my page">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0" />  
  <meta name="apple-mobile-web-app-capable" content="yes" />  
  <meta name="format-detection" content="telephone=no" />
  <link rel="stylesheet"
        href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
        integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u"
        crossorigin="anonymous">
  <script type="application/javascript" language="JavaScript" src="Resource/js/jquery-3.2.1.min.js"></script>
  <script type="application/javascript" language="JavaScript" src="Resource/js/common.js"></script>
  <script type="application/javascript" language="JavaScript" src="Resource/js/base.js"></script>
  <link rel="stylesheet" type="text/css" href="Resource/css/base.css">
  <link rel="shortcut icon" href="Resource/img/btv.ico">
  <style type="text/css">
    #div_sign_in {margin: 200px auto auto;  width: 400px; height: 300px; background-color: #2c343c;
      box-shadow: 0 0 8px #1d2bfc;}
    #div_sign_in_1 {width: 300px; height: 250px; margin: 25px auto auto}
    h3 {width: 100%; text-align: center; color: whitesmoke}
    #error_message {color: red; font-size: 16px}
    #bt_submit {width: 100%}
  </style>
</head>

<body style="background-color: #bbbbbb">
<div id="div_sign_in">
  <div id="div_sign_in_1">
    <br/>
    <h3>BTV RENTAL SYSTEM</h3>
    <br/>
    <form action="/panel/signin" method="post">
      <div class="input-group">
        <span class="input-group-addon" id="basic-addon1">
          <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
        </span>
        <input type="text" class="form-control" placeholder="Username" aria-describedby="basic-addon1" name="username">
      </div>
      <br/>
      <div class="input-group">
        <span class="input-group-addon" id="basic-addon2">
          <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
        </span>
        <input type="password" class="form-control" placeholder="Password" aria-describedby="basic-addon2" name="password">
      </div>
      <br/>
      <button id="bt_submit" type="submit" class="btn btn-primary">SignIn</button>
    </form>
    <span id="error_message"></span>
  </div>
</div>
</body>
</html>
