<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<rapid:override name="title">
    Devices
</rapid:override>
<rapid:override name="css_js">
    <link rel="stylesheet" href="Resource/css/admin/devices.css"/>
    <script type="application/javascript" src="Resource/js/admin/devices.js"></script>
</rapid:override>

<rapid:override name="navigation">
    <ul>
        <li>
            <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
            <a href="/panel/admin/">Home</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>
            <a href="/panel/admin/dealer">Dealer</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
            <a href="/panel/admin/sales">Sales</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
            <a href="/panel/admin/users">Customers</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-usd" aria-hidden="true"></span>
            <a href="/panel/admin/commission">Commission</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-hdd" aria-hidden="true"></span>
            <a href="/panel/admin/devices">Devices</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
            <a href="/panel/signout">SignOut</a>
        </li>
    </ul>
</rapid:override>

<rapid:override name="content">

    <div style="width: 100%; height: 40px">
        <div style="width: 9%; display: block; float: left;">
            <button type="button" class="btn btn-default" id="btCheckIn" title="create a sales">
                <span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Check In
            </button>
        </div>

        <div style="width: 86%; display: block; float: left;">
            <div class="input-group">
                <span class="input-group-addon" id="basic-addon1">Search</span>
                <input type="text" class="form-control" placeholder="type in keyword"
                       aria-describedby="basic-addon1" id="ipSearch">
            </div>
        </div>

        <div style="width: 5%; display: block; float: right; font-size: 18px; font-weight: 500;
            text-align: right; align-content: center">
            <span id="spTotalCount" style="height: 100%; line-height: 100%"></span>
        </div>
    </div>
    <br/>


    <div>
        <table class="table table-bordered table-hover table-striped table-condensed"
               id="tbDevices">
            <thead style="background-color: #566778;">
            <tr>
                <th>#</th>
                <th>Id</th>
                <th>Mac</th>
                <th>Dealer</th>
                <th>CheckInTime</th>
            </tr>
            </thead>
            <tbody style="font-size: 14px">
            <c:forEach items="${deviceRentInfoList}" var="deviceRentInfo" varStatus="status">
                <tr>
                    <td><input type="radio" name="rdDevice" value="${deviceRentInfo.mac}"
                               currentRow="${status.index}"></td>
                    <td>${status.index+1}</td>
                    <td>${deviceRentInfo.mac}</td>
                    <td>${deviceRentInfo.dealerName}</td>
                    <td>${deviceRentInfo.createTime}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</rapid:override>

<rapid:override name="details">
    <div style="margin: 20px">
        <div id="div_create">
            <h3 style="width: 100%; text-align: center; color: whitesmoke">CHECK IN DEVICE</h3>
            <br/>

            <div style="width: 60%; display: block; margin: 20px auto">
                <div class="input-group">
                <span class="input-group-addon" id="basic-addon6">
                  <span class="glyphicon glyphicon-barcode" aria-hidden="true"></span>
                </span>
                    <input id="ipMac" type="email" class="form-control" placeholder="Mac"
                           aria-describedby="basic-addon6" name="mac" maxlength="17">
                </div>
                <br/>
                <div class="input-group">
                <span class="input-group-addon" id="basic-addon7">
                  <span class="glyphicon glyphicon-hand-right" aria-hidden="true"></span>
                </span>
                    <select class="form-control" id="ipDealerId">
                        <option value="0">Choose Dealer</option>
                        <c:forEach items="${authDealerInfoList}" var="authDealerInfo">
                            <option value="${authDealerInfo.id}">${authDealerInfo.username}</option>
                        </c:forEach>
                    </select>
                </div>
                <br/>
            </div>

            <br style="clear: both"/>
            <div style="width: 60%; margin: 15px auto; clear: both">
                <button id="btSubmitCheckIn" type="submit" class="btn btn-primary" style="width: 100%; margin: auto">
                    Check In
                </button>
            </div>
            <h5 id="errorMessage" style="color: red; width: 100%; text-align: center"></h5>
        </div>
    </div>
</rapid:override>
<%@ include file="../base.jsp"%>