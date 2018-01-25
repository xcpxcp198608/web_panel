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

</head>

<body>

<div style="width: 100%; height: 100%; background-image: url(Resource/img/bg.jpg); position: absolute; left: 0; top: 0">
  <div style="margin: 200px auto auto;  width: 400px; height: 330px; background-color: #2c343c;
          box-shadow: 0 0 8px #000000;">
    <div class="container" style="width: 350px; height: 270px; margin: 25px auto auto">
      <br/>
      <h4 style="width: 100%; text-align: center; color: whitesmoke;">PREFERRED CUSTOMER PROGRAM</h4>
      <br/>
      <form action="/panel/signin" method="post">
        <div class="input-group mb-3">
          <div class="input-group-prepend">
             <span class="input-group-text" id="basic-addon1">
                <span data-feather="user"></span>
             </span>
          </div>
          <input type="text" class="form-control" placeholder="Username" aria-label="Username"
                 aria-describedby="basic-addon1" name="username">
        </div>

        <div class="input-group mb-3">
          <div class="input-group-prepend">
              <span class="input-group-text" id="basic-addon2">
                  <span data-feather="lock"></span>
              </span>
          </div>
          <input type="password" class="form-control" placeholder="Password" aria-label="Password"
                 aria-describedby="basic-addon2" name="password">
        </div>

        <div style="color: whitesmoke">
          <div class="custom-control custom-radio custom-control-inline">
            <input type="radio" id="customRadioInline1" name="type" class="custom-control-input" value="3" checked>
            <label class="custom-control-label" for="customRadioInline1">S</label>
          </div>
          <div class="custom-control custom-radio custom-control-inline">
            <input type="radio" id="customRadioInline2" name="type" class="custom-control-input" value="2">
            <label class="custom-control-label" for="customRadioInline2">D</label>
          </div>
          <div class="custom-control custom-radio custom-control-inline">
            <input type="radio" id="customRadioInline3" name="type" class="custom-control-input" value="1">
            <label class="custom-control-label" for="customRadioInline3">A</label>
          </div>
          <div class="custom-control custom-radio custom-control-inline">
            <input type="radio" id="customRadioInline4" name="type" class="custom-control-input" value="0">
            <label class="custom-control-label" for="customRadioInline4">M</label>
          </div>
        </div>
        <br/>
        <button id="bt_submit" type="submit" class="btn btn-primary" style="width: 100%">SignIn</button>
      </form>
      <span id="error_message" style="color: red;"></span>
    </div>
  </div>
</div>

<script src="https://cdn.bootcss.com/popper.js/1.12.9/umd/popper.min.js"
        integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://cdn.bootcss.com/bootstrap/4.0.0/js/bootstrap.min.js"
        integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>
<script src="https://unpkg.com/feather-icons/dist/feather.min.js"></script>
<script>
    feather.replace();
    feather.icons.x.toSvg({ 'width': 16, 'height':16})
</script>
</body>
</html>