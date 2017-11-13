<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
            <a href="/panel/signout">SignOut</a>
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

    <div style="width: 100%; height: 40px">
        <div style="width: 20%; display: block; float: left;">
            <button type="button" class="btn btn-default" id="btCreate">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Create
            </button>

            <button type="button" class="btn btn-default" id="btUpdate">
                <span class="glyphicon glyphicon-edit" aria-hidden="true"></span> Update
            </button>
        </div>
        <div style="width: 80%; display: block; float: left;">
            <div class="input-group">
                <span class="input-group-addon" id="basic-addon1">Search</span>
                <input type="text" class="form-control" placeholder="type in keyword"
                       aria-describedby="basic-addon1" id="ipSearch">
            </div>
        </div>
    </div>

    <div style="clear: both; margin-top: 5px; box-shadow: 0 0 5px #000;">
        <table class="table table-bordered table-hover table-striped table-condensed"
               id="tbSales">
            <thead style="background-color: #566778;">
            <tr id="trSales">
                <th colspan="2">#</th>
                <th>Username</th>
                <th>Password</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>SSN</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Time</th>
                <th>Users</th>
            </tr>
            </thead>
            <tbody>

            <c:forEach items="${authSalesInfoList}" var="authSalesInfo" varStatus="status">
                <tr>
                    <td><input type="radio" name="update" value="${authSalesInfo.id}" currentRow="${status.index}"></td>
                    <td>${status.index+1}</td>
                    <td>${authSalesInfo.username}</td>
                    <td><input type="text" value="${authSalesInfo.password}" title="pwd" size="12"/></td>
                    <td>${authSalesInfo.firstName}</td>
                    <td>${authSalesInfo.lastName}</td>
                    <td>
                        ${fn:substring(authSalesInfo.ssn, 0, 3)}-${fn:substring(authSalesInfo.ssn, 3, 5)}-${fn:substring(authSalesInfo.ssn, 5, 9)}
                    </td>
                    <td>${authSalesInfo.email}</td>
                    <td>${authSalesInfo.phone}</td>
                    <td class="tdRows12">${fn:substring(authSalesInfo.createTime, 0, 19)}</td>
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

<rapid:override name="details">
    <div style="margin: 20px">
        <div id="div_create">
            <h3 style="width: 100%; text-align: center; color: whitesmoke">CREATE SALES ACCOUNT</h3>
            <br/>

            <div>
                <div style="width: 47%; height: 200px; display: block; float: left">
                    <div class="input-group">
                    <span class="input-group-addon" id="basic-addon3">
                      <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                    </span>
                        <input id="ipUsername" type="text" class="form-control" placeholder="Username" aria-describedby="basic-addon3" name="username">
                    </div>
                    <br/>
                    <div class="input-group">
                    <span class="input-group-addon" id="basic-addon4">
                      <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
                    </span>
                        <input id="ipPassword" type="password" class="form-control" placeholder="Password(length >= 6)" aria-describedby="basic-addon4" name="password">
                    </div>
                    <br/>
                    <div class="input-group">
                    <span class="input-group-addon" id="basic-addon5">
                      <span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>
                    </span>
                        <input id="ipFirstName" type="text" class="form-control" placeholder="FirstName" aria-describedby="basic-addon5" name="firstName">
                    </div>
                    <br/>
                    <div class="input-group">
                    <span class="input-group-addon" id="basic-addon6">
                      <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
                    </span>
                        <input id="ipEmail" type="email" class="form-control" placeholder="Email" aria-describedby="basic-addon6" name="email">
                    </div>
                    <br/>
                </div>

                <div style="width: 47%; height: 200px; display: block; float: right">
                    <div class="input-group">
                    <span class="input-group-addon" id="basic-addon7">
                      <span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
                    </span>
                        <input id="ipSSN" type="number" class="form-control" placeholder="SSN" aria-describedby="basic-addon7" name="ssn">
                    </div>
                    <br/>
                    <div class="input-group">
                    <span class="input-group-addon" id="basic-addon8">
                      <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
                    </span>
                        <input id="ipPassword1" type="password" class="form-control" placeholder="Password(length >= 6)" aria-describedby="basic-addon8" name="password1">
                    </div>
                    <br/>
                    <div class="input-group">
                    <span class="input-group-addon" id="basic-addon9">
                      <span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>
                    </span>
                        <input id="ipLastName" type="text" class="form-control" placeholder="LastName" aria-describedby="basic-addon9" name="lastName">
                    </div>
                    <br/>
                    <div class="input-group">
                    <span class="input-group-addon" id="basic-addon10">
                      <span class="glyphicon glyphicon-phone" aria-hidden="true"></span>
                    </span>
                        <input id="ipPhone" type="number" class="form-control" placeholder="Phone" aria-describedby="basic-addon10" name="phone">
                    </div>
                    <br/>
                </div>
            </div>

            <br style="clear: both"/>
            <div style="width: 70%; margin: 20px auto; clear: both">
                <button id="btSubmitCreate" type="submit" class="btn btn-primary" style="width: 100%; margin: auto">
                    Create
                </button>
            </div>
            <h4 id="errorMessage" style="color: red; width: 100%; text-align: center"></h4>
        </div>
    </div>

</rapid:override>

<%@ include file="../base.jsp"%>