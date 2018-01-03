<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<rapid:override name="title">
    Reset
</rapid:override>
<rapid:override name="css_js">

</rapid:override>

<rapid:override name="content">

    <div style="width: 25%; margin: 100px auto">
        <h2 style="width: 100%; text-align: center">REST PASSWORD</h2>
        <br/>
        <form action="user/update" method="post">
            <input type="hidden" value="${authRegisterUserInfo.username}" name="username">
            <div class="input-group">
                <span class="input-group-addon" id="basic-addon1">
                  <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
                </span>
                <input type="password" id="reset_password1" class="form-control" placeholder="New Password" aria-describedby="basic-addon1" name="password1">
            </div>
            <br/>
            <div class="input-group">
                <span class="input-group-addon" id="basic-addon2">
                  <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
                </span>
                <input type="password" id="reset_password" class="form-control" placeholder="Repeat Password" aria-describedby="basic-addon2" name="password">
            </div>
            <br/>
            <br/>
            <button id="btReset" type="submit" class="btn btn-primary" style="width: 100%">Reset</button>
        </form>
    </div>
    <div style="clear: both"><span id="error_message_reset" style="color: red; font-size: 18px; text-align: center"></span></div>

</rapid:override>
<%@ include file="../base1.jsp"%>