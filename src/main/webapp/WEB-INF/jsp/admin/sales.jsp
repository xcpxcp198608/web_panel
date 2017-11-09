<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<rapid:override name="title">
    Sales
</rapid:override>
<rapid:override name="css_js">
    <script type="text/javascript" src="Resource/js/admin/sales.js"></script>
</rapid:override>

<rapid:override name="navigation">
    <ul>
        <li>
            <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
            <a href="/panel/admin/">Home</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
            <a href="/panel/admin/sales">Sales</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
            <a href="/panel/admin/users">Users</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-usd" aria-hidden="true"></span>
            <a href="/panel/admin/commission">Commission</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
            <a href="/panel/admin/signout">SignOut</a>
        </li>
    </ul>
</rapid:override>

<rapid:override name="content">

    <div>
        <div style="width: 49%; height: 200px; display: block; float: left; background-color: #2c343c;
        box-shadow: 0 0 5px #000;">
            <div id="chart_sales_volume" style="width: 100%; height: 100%; background-color: #2c343c;"></div>
        </div>
        <div style="width: 49%; height: 200px; display: block; float: right; background-color: #2c343c;
        box-shadow: 0 0 5px #000;">
            <div id="chart_sales_amount" style="width: 100%; height: 100%; background-color: #2c343c;"></div>
        </div>
    </div>
    <br/>
    <div style="height: 20px; clear: both">&nbsp;</div>
    <div style="clear: both">
        <table class="table table-bordered table-hover table-striped table-condensed">
            <thead style="background-color: #566778; color: #bbb">
            <tr>
                <th>#</th>
                <th>Username</th>
                <th>Password</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Users</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${authSalesInfoList}" var="authSalesInfo" varStatus="status">
                <tr>
                    <td>${status.index+1}</td>
                    <td>${authSalesInfo.username}</td>
                    <td>${authSalesInfo.password}</td>
                    <td>${authSalesInfo.firstName}</td>
                    <td>${authSalesInfo.lastName}</td>
                    <td>${authSalesInfo.email}</td>
                    <td>${authSalesInfo.phone}</td>
                    <td>
                        <a href="/panel/admin/users/${authSalesInfo.id}">
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