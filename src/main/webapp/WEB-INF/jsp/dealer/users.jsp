<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<rapid:override name="title">
    Users
</rapid:override>
<rapid:override name="css_js">
    <link rel="stylesheet" href="Resource/css/admin/users.css"/>
    <script type="application/javascript" src="Resource/js/admin/users.js"></script>
</rapid:override>

<rapid:override name="navigation">
    <ul>
        <li>
            <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
            <a href="/panel/dealer/">Home</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-th-list " aria-hidden="true"></span>
            <a href="/panel/dealer/sales">Sales</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
            <a href="/panel/dealer/users">Customers</a>
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

        <div style="width: 80%; display: block; float: left">
            <div class="input-group">
                <span class="input-group-addon" id="basic-addon1">Search</span>
                <input type="text" class="form-control" placeholder="type in keyword (key, sales, mac, first name, last name, activate date)"
                       aria-describedby="basic-addon1" id="ipSearch">
            </div>
        </div>
        <div style="width: 5%; display: block; float: right; font-size: 18px; font-weight: 500;
            text-align: right; align-content: center">
            <span id="spOnlineCount" style="color: #008500; height: 100%; line-height: 100%"></span>
            /
            <span id="spTotalCount" style="height: 100%; line-height: 100%"></span>
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
                    <th>ClientKey</th>
                    <th>Sales</th>
                    <th>Mac</th>
                    <th>FirstName</th>
                    <th>LastName</th>
                    <th>ActivateTime</th>
                    <th>
                        <select id="seCategory" style="width: 100%; height: 100%;
                            background-color: rgba(0,0,0,0); border: none; text-align: center">
                            <option value="">Plan</option>
                            <option value="B1">B1</option>
                            <option value="P1">P1</option>
                            <option value="P2">P2</option>
                        </select>
                    </th>
                    <th>
                        <select id="seStatus" style="width: 100%; height: 100%;
                            background-color: rgba(0,0,0,0); border: none; text-align: center">
                            <option value="">Status</option>
                            <option value="activate">activate</option>
                            <option value="deactivate">deactivate</option>
                            <option value="limited">limited</option>
                            <option value="canceled">canceled</option>
                        </select>
                    </th>
                    <th>Online</th>
                    <th>More</th>
                </tr>
            </thead>
            <tbody style="font-size: 14px">
                <c:forEach items="${authRentUserInfoList}" var="authRentUserInfo" varStatus="status">
                <tr>
                    <td><input type="radio" name="rdUser" value="${authRentUserInfo.clientKey}"
                               currentRow="${status.index}" currentStatus="${authRentUserInfo.status}"></td>
                    <td>${status.index+1}</td>
                    <td>${authRentUserInfo.clientKey}</td>
                    <td>${authRentUserInfo.salesName}</td>
                    <td>${authRentUserInfo.mac}</td>
                    <td>${authRentUserInfo.firstName}</td>
                    <td>${authRentUserInfo.lastName}</td>
                    <td>${fn:substring(authRentUserInfo.activateTime, 0, 19)}</td>
                    <td>${authRentUserInfo.category}</td>
                    <td>
                        <c:if test="${authRentUserInfo.status == 'activate'}">
                            <a style="color: #00b300;">${authRentUserInfo.status}</a>
                        </c:if>
                        <c:if test="${authRentUserInfo.status == 'deactivate'}">
                            <a style="color: #f00;" href="/panel/admin/activate/${authRentUserInfo.clientKey}">
                                    ${authRentUserInfo.status}
                            </a>
                        </c:if>
                        <c:if test="${authRentUserInfo.status == 'limited'}">
                            <a style="color: #f00;">${authRentUserInfo.status}</a>
                        </c:if>
                        <c:if test="${authRentUserInfo.status == 'canceled'}">
                            <a style="color: #777;">${authRentUserInfo.status}</a>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${authRentUserInfo.online == 'true'}">
                            <span class="glyphicon glyphicon-link" aria-hidden="true" style="color: #00b300"
                                  online="${authRentUserInfo.online}"></span>
                        </c:if>
                        <c:if test="${authRentUserInfo.online == 'false'}">
                            <span class="glyphicon glyphicon-link" aria-hidden="true" style="color: #9f9f9f"
                                  online="${authRentUserInfo.online}"></span>
                        </c:if>
                    </td>
                    <td>
                        <a style="cursor: pointer" title="show user's details">
                            <span class="glyphicon glyphicon-stats" aria-hidden="true"></span>
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
            <tr><td>ClientKey</td><td></td></tr>
            <tr><td>Mac</td><td></td></tr>
            <tr><td>Plan</td><td></td></tr>
            <tr><td>FirstName</td><td></td></tr>
            <tr><td>LastName</td><td></td></tr>
            <tr><td>Email</td><td></td></tr>
            <tr><td>Phone</td><td></td></tr>
            <tr><td>CardNumber</td><td></td></tr>
            <tr><td>Deposit</td><td></td></tr>
            <tr><td>FirstPay</td><td></td></tr>
            <tr><td>MonthPay</td><td></td></tr>
            <tr><td>CreateTime</td><td></td></tr>
            <tr><td>ActivateTime</td><td></td></tr>
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