<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<rapid:override name="title">
    Panel
</rapid:override>
<rapid:override name="css_js">
    <link rel="stylesheet" href="Resource/css/sales/home.css"/>
    <script src="Resource/js/sales/home.js"></script>
</rapid:override>

<rapid:override name="navigation">
    <ul>
        <li>
            <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
            <a href="/panel/sales/">My</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
            <a href="/panel/sales/users">Customers</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
            <a href="/panel/signout">SignOut</a>
        </li>
    </ul>
</rapid:override>

<rapid:override name="content">
    <div style="width: 100%;">

        <div style="width: 100%; height: 34px; ">
            <div style="width: 10%; display: block; float: left;">
                <div style="margin: auto; height: 34px">
                    <a style="display: block; float: left; height: 34px; line-height: 34px;
                    font-size: 20px" id="aYear">2017</a>
                    <a style="display: block; float: left; height: 34px; line-height: 34px;
                    font-size: 20px">-</a>
                    <a style="display: block; float: left; height: 34px; line-height: 34px;
                    font-size: 20px" id="aMonth">11</a>
                </div>
            </div>

            <div style="width: 80%; display: block; float: left">
                <a style="width:100%; height: 34px; line-height: 34px; font-size: 20px; text-align: center; display: block">
                    My Commission
                </a>
            </div>

            <div style="width: 10%; display: block; float: left">
                <div style="display: block; float: right; align-content: center; margin-top: 3px">
                    <button type="button" class="btn btn-default" aria-label="Left Align" id="btPreviousMonth"
                            disabled="disabled" title="previous month">
                        <span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span>
                    </button>
                    <button type="button" class="btn btn-default" aria-label="Left Align" id="btNextMonth"
                            disabled="disabled" title="next month">
                        <span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>
                    </button>
                </div>
            </div>
        </div>

        <div style="width: 100%; height: 166px; box-shadow: 0 0 5px #000;">
            <div style="width: 20%; height:166px; display: block; float: left; background-color: #2c343c;">
                <table class="table table-bordered table-condensed" id="tbYearCommission"
                       style="background-color: #2c343c; color: whitesmoke">
                    <tbody>
                    <tr><td>Commission</td><td style="font-style: italic; color: orange">0</td></tr>
                    </tbody>
                </table>
            </div>
            <div id="chartYearCommission" style="width: 80%; height:166px; display: block; float: right; background-color: #2c343c">

            </div>
        </div>
        <div style="width: 100%; height: 5px"></div>

        <div style="width: 100%; height: 300px; background-color: #2c343c; box-shadow: 0 0 5px #000;">
            <div id="chartMonthCommission" style="width: 100%; height: 300px;"></div>
        </div>

        <div style="width: 100%; box-shadow: 0 0 5px #000; overflow: scroll">
            <table  class="table table-bordered table-hover table-striped" id="tbMonthCommission">
                <thead style="background-color: #566778;" >
                <tr id="trMonthCommission">
                    <td>#</td><td>Total</td>
                </tr>
                </thead>
                <tbody>
                <tr><td>Commission</td><td class="tdRows14 redFont">0</td></tr>
                </tbody>
            </table>
        </div>
    </div>

    <div style="margin-top: 10px">
        <div style="width: 100%; clear: both">
            <table id="tbTotal" class="table table-striped">
                <thead>
                <tr>
                    <td>Count</td>
                    <td>Amount</td>
                    <td>Deposit</td>
                    <td>Commission</td>
                </tr>
                </thead>
                <tbody>
                <tr id="trTotal">
                    <td class="tdTotal">0</td>
                    <td class="tdTotal">0</td>
                    <td class="tdTotal">0</td>
                    <td class="tdTotal">0</td>
                </tr>
                </tbody>
            </table>
        </div>

        <div style="width: 100%;">
            <div class="input-group">
                <span class="input-group-addon" id="basic-addon1">Search</span>
                <input type="text" class="form-control" placeholder="type in keyword (client key, transaction id)"
                       aria-describedby="basic-addon1" id="ipSearch">
            </div>
        </div>

        <div style="width: 100%; box-shadow: 0 0 5px #000; margin-top: 8px">
            <table class="table table-bordered table-hover table-striped" id="tbOrders">
                <thead style="background-color: #566778;">
                <tr>
                    <td>#</td>
                    <td>ClientKey</td>
                    <td>TransactionId</td>
                    <td>Amount</td>
                    <td>Deposit</td>
                    <td>Commission</td>
                    <td>
                        <input type="date" id="ipDate" placeholder="TradingTime" style="width: 100%; height: 100%;
                            background-color: rgba(0,0,0,0); border: none; text-align: center">
                    </td>
                    <td>
                        <select id="sePlan" style="width: 100%; height: 100%;
                            background-color: rgba(0,0,0,0); border: none; text-align: center">
                            <option value="">Plan</option>
                            <option value="B1">B1</option>
                            <option value="P1">P1</option>
                            <option value="P2">P2</option>
                        </select>
                    </td>
                    <td>
                        <select id="seType" style="width: 100%; height: 100%;
                            background-color: rgba(0,0,0,0); border: none; text-align: center">
                            <option value="">Type</option>
                            <option value="contracted">contracted</option>
                            <option value="monthly">monthly</option>
                            <option value="renew">renew</option>
                        </select>
                    </td>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${authorizeTransactionInfoList}" var="authorizeTransactionInfo" varStatus="status">
                    <tr style="font-size: 14px">
                        <td>${status.index + 1}</td>
                        <td>${authorizeTransactionInfo.clientKey}</td>
                        <td style="font-size: 12px">${authorizeTransactionInfo.transactionId}</td>
                        <td>${authorizeTransactionInfo.amount}</td>
                        <td>${authorizeTransactionInfo.deposit}</td>
                        <td style="color: orange">${authorizeTransactionInfo.salesCommission}</td>
                        <td style="font-size: 12px">${fn:substring(authorizeTransactionInfo.createTime, 0, 19)}</td>
                        <td>${authorizeTransactionInfo.category}</td>
                        <td>${authorizeTransactionInfo.type}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>


</rapid:override>
<%@ include file="../base.jsp"%>