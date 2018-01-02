<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<rapid:override name="title">
    Customers
</rapid:override>
<rapid:override name="css_js">
    <link rel="stylesheet" href="Resource/css/users/users.css" />
    <script type="application/javascript" src="Resource/js/users/users.js"></script>
</rapid:override>

<rapid:override name="navigation">
    <ul>
        <li>
            <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
            <a href="/panel/manager/">Home</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
            <a href="/panel/manager/users">Customers</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
            <a href="/panel/signout">SignOut</a>
        </li>
    </ul>
</rapid:override>

<rapid:override name="content">

    <div>
        <div style="width: 15%; display: block; float: left;">
            <button type="button" class="btn btn-default" id="btActivate" title="activate">
                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span> A
            </button>

            <button type="button" class="btn btn-default" id="btLimited" title="limited">
                <span class="glyphicon glyphicon-lock" aria-hidden="true"></span> L
            </button>

            <button type="button" class="btn btn-default" id="btCanceled" title="canceled">
                <span class="glyphicon glyphicon-remove-sign" aria-hidden="true"></span> C
            </button>
        </div>

        <div style="width: 75%; display: block; float: left">
            <div class="input-group">
                <span class="input-group-addon" id="basic-addon1">Search</span>
                <input type="text" class="form-control" placeholder="type in keyword"
                       aria-describedby="basic-addon1" id="ipSearch">
            </div>
        </div>
        <div style="width: 10%; display: block; float: right; font-size: 15px; font-weight: 500;
            text-align: right; align-content: center">
            <span id="spOnlineCount" style="color: #008500; height: 100%; line-height: 100%">123111</span>
            /
            <span id="spTotalCount" style="height: 100%; line-height: 100%">111111</span>
        </div>
    </div>
    <hr/>

    <div>
        <table class="table table-bordered table-hover table-striped table-condensed"
               id="tbUsers">
            <thead style="background-color: #566778;">
            <tr>
                <th></th>
                <th>#</th>
                <th>Username</th>
                <th>Mac</th>
                <th>Email</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>ExpiresTime</th>
                <th>Online</th>
                <th>More</th>
            </tr>
            </thead>
            <tbody style="font-size: 12px">
            <c:forEach items="${authRegisterUserInfoList}" var="authRegisterUserInfo" varStatus="status">
                <tr>
                    <td><input type="radio" name="rdUser" value="${authRegisterUserInfo.id}"
                               currentRow="${status.index}" currentStatus="${authRegisterUserInfo.status}"></td>
                    <td>${status.index+1}</td>
                    <td>${authRegisterUserInfo.username}</td>
                    <td>${authRegisterUserInfo.mac}</td>
                    <td>${authRegisterUserInfo.email}</td>
                    <td>${authRegisterUserInfo.firstName}</td>
                    <td>${authRegisterUserInfo.lastName}</td>
                    <td>${authRegisterUserInfo.expiresTime}</td>
                    <td>1</td>
                    <td>
                        <a  title="show details">
                            <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</rapid:override>
<%@ include file="../base.jsp"%>