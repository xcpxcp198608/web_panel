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

    <c:if test="${username eq 'wiatec'}">
        <div style="width: 100%">
            <div style="width: 5%; display: block; float: left;">
                <select id="seUpdateLevel" aria-describedby="basic-addon19" class="form-control"
                        style="text-align: center;">
                    <option value="0">Level</option>
                    <option value="1">1</option>
                    <option value="2">2</option>
                    <option value="3">3</option>
                    <option value="4">4</option>
                    <option value="5">fto</option>
                </select>
            </div>

            <div style="width: 5%; display: block; float: left;">
                <select id="seDays" aria-describedby="basic-addon19" class="form-control"
                        style="text-align: center;">
                    <option value="-1">Days</option>
                    <option value="0">0</option>
                    <option value="1">1</option>
                    <option value="7">7</option>
                    <option value="30">30</option>
                    <option value="92">92</option>
                    <option value="183">183</option>
                    <option value="365">365</option>
                </select>
            </div>

            <div style="width: 10%; display: block; float: left;">
                <button type="button" class="btn btn-default" id="btUpdateLevel" title="update">
                    <span class="glyphicon glyphicon-open" aria-hidden="true"></span> UpdateLevel
                </button>
            </div>
        </div>
        <br/>
        <br/>
    </c:if>

    <div style="clear: both">
        <div style="width: 25%; display: block; float: left;">
            <button type="button" class="btn btn-default" id="btActivate" title="activate">
                <span class="glyphicon glyphicon-ok" aria-hidden="true"></span> Activate
            </button>

            <button type="button" class="btn btn-default" id="btLimited" title="limited">
                <span class="glyphicon glyphicon-lock" aria-hidden="true"></span> Limited
            </button>

            <button type="button" class="btn btn-default" id="btDelete" title="limited">
                <span class="glyphicon glyphicon-lock" aria-hidden="true"></span> Delete
            </button>
        </div>

        <div style="width: 60%; display: block; float: left">
            <div class="input-group">
                <span class="input-group-addon" id="basic-addon1">Search</span>
                <input type="text" class="form-control" aria-describedby="basic-addon1" id="ipSearch"
                       placeholder="type in keyword(username, mac, email, first name, last name, expires time)">
            </div>
        </div>
        <div style="width: 15%; display: block; float: right; font-size: 15px; font-weight: 500;
            text-align: right; align-content: center">
            <span id="spOnlineCount" style="color: #008500; height: 100%; line-height: 100%"></span>
            /
            <span id="spTotalCount" style="height: 100%; line-height: 100%"></span>
        </div>
    </div>

    <div>
        <table class="table table-bordered table-hover table-striped table-condensed"
               id="tbUsers">
            <thead style="background-color: #566778;">
            <tr>
                <th></th>
                <th>ID</th>
                <th>Username</th>
                <th>Mac</th>
                <th>Email</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>ExpiresTime</th>
                <th>Status</th>
                <th>
                    <select id="seLevel" style="width: 100%; height: 100%;
                            background-color: rgba(0,0,0,0); border: none; text-align: center">
                        <option value="-1">Level</option>
                        <option value="0">0</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="fto">fto</option>
                    </select>
                </th>
                <th>Online</th>
                <th>More</th>
            </tr>
            </thead>
            <tbody style="font-size: 12px;">
            <c:forEach items="${authRegisterUserInfoList}" var="authRegisterUserInfo" varStatus="status">
                <tr>
                    <td><input type="radio" name="rdUser" value="${authRegisterUserInfo.id}"
                               currentRow="${status.index}" currentStatus="${authRegisterUserInfo.emailStatus}"></td>
                    <td>${authRegisterUserInfo.id}</td>
                    <td>${authRegisterUserInfo.username}</td>
                    <td>${authRegisterUserInfo.mac}</td>
                    <td>${authRegisterUserInfo.email}</td>
                    <td>${authRegisterUserInfo.firstName}</td>
                    <td>${authRegisterUserInfo.lastName}</td>
                    <td>${authRegisterUserInfo.expiresTime}</td>
                    <td>
                        <c:if test="${authRegisterUserInfo.emailStatus == 1}">
                            <span class="glyphicon glyphicon-ok" aria-hidden="true" style="color: #9f9f9f"
                                  status="${authRegisterUserInfo.emailStatus}"></span>
                        </c:if>
                        <c:if test="${authRegisterUserInfo.emailStatus == 0}">
                            <span class="glyphicon glyphicon-remove" aria-hidden="true" style="color: #a01c34"
                                  status="${authRegisterUserInfo.emailStatus}"></span>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${authRegisterUserInfo.level == 0}">
                            <span style="color: #a01c34">${authRegisterUserInfo.level}</span>
                        </c:if>
                        <c:if test="${authRegisterUserInfo.level == 5}">
                            <span>fto</span>
                        </c:if>
                        <c:if test="${authRegisterUserInfo.level != 0 && authRegisterUserInfo.level != 5}">
                            <span>${authRegisterUserInfo.level}</span>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${authRegisterUserInfo.online == 'true'}">
                            <span class="glyphicon glyphicon-link" aria-hidden="true" style="color: #008500"
                                  online="${authRegisterUserInfo.online}"></span>
                        </c:if>
                        <c:if test="${authRegisterUserInfo.online == 'false'}">
                            <span class="glyphicon glyphicon-link" aria-hidden="true" style="color: #9f9f9f"
                                  online="${authRegisterUserInfo.online}"></span>
                        </c:if>
                    </td>
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

<rapid:override name="details">
    <table class="table table-condensed" id="tbUserDetails" style="color: #0ab980">
        <thead>
        <tr>
            <td>#</td><td>value</td>
        </tr>
        </thead>
        <tbody>
        <tr><td>Username</td><td></td></tr>
        <tr><td>Password</td><td></td></tr>
        <tr><td>Mac</td><td></td></tr>
        <tr><td>FirstName</td><td></td></tr>
        <tr><td>LastName</td><td></td></tr>
        <tr><td>Email</td><td></td></tr>
        <tr><td>Phone</td><td></td></tr>
        <tr><td>CreateTime</td><td></td></tr>
        <tr><td>ActivateTime</td><td></td></tr>
        <tr><td>Level</td><td></td></tr>
        <tr><td>ExpiresTime</td><td></td></tr>
        <tr><td>Status</td><td></td></tr>
        <tr><td>Country</td><td></td></tr>
        <tr><td>Region</td><td></td></tr>
        <tr><td>City</td><td></td></tr>
        <tr><td>TimeZone</td><td></td></tr>
        <tr><td>LastOnLineTime</td><td></td></tr>
        </tbody>
    </table>
</rapid:override>

<%@ include file="../base.jsp"%>