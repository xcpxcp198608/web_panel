<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<rapid:override name="title">
    Commission
</rapid:override>
<rapid:override name="css_js">
    <link rel="stylesheet" href="Resource/css/admin/commission.css"/>
    <script type="application/javascript" src="Resource/js/admin/commission.js"></script>
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
    <div style="width: 100%; height: 180px">
        <div style="width: 40%; height: 100%; display: block; float: left;">
            <a style="width:100%; height: 30px; line-height: 30px; font-size: 20px">Category</a>
            <table class="table table-bordered table-hover table-striped" id="tbCategory" style=" box-shadow: 0 0 5px #000; font-size: 14px;">
                <thead style="background-color: #566778;">
                    <tr><td>#</td><td>Price</td><td>Deposit</td><td>Expires</td><td>Bonus</td><td>LD</td><td>Dealer</td><td>Sales</td></tr>
                </thead>
                <tbody>
                    <c:forEach items="${commissionCategoryInfoList}" var="commissionCategoryInfo">
                        <tr>
                            <td>${commissionCategoryInfo.category}</td>
                            <td>${commissionCategoryInfo.price}</td>
                            <td>${commissionCategoryInfo.deposit}</td>
                            <td>${commissionCategoryInfo.expires}</td>
                            <td>${commissionCategoryInfo.bonus}</td>
                            <td>${commissionCategoryInfo.ldCommission}</td>
                            <td>${commissionCategoryInfo.dealerCommission}</td>
                            <td>${commissionCategoryInfo.salesCommission}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div style="width: 20%; height: 100%; display: block; float: left">
            <div id="B1Chart" style="width: 100%; height: 180px"></div>
        </div>
        <div style="width: 20%; height: 100%; display: block; float: left">
            <div id="P1Chart" style="width: 100%; height: 180px"></div>
        </div>
        <div style="width: 20%; height: 100%; display: block; float: left">
            <div id="P2Chart" style="width: 100%; height: 180px"></div>
        </div>
    </div>

    <br/>

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
                    Income distribution
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

        <div style="width: 100%; height: 198px; box-shadow: 0 0 5px #000;">
            <div style="width: 20%; display: block; float: left">
                <table class="table table-bordered table-condensed" id="tbYearIncome"
                    style="background-color: #2c343c; color: whitesmoke">
                    <tbody>
                        <tr><td>Income</td><td style="font-style: italic; color: orange">0</td></tr>
                        <tr><td>TxFee</td><td style="font-style: italic; color: orange">0</td></tr>
                        <tr><td>Deposit</td><td style="font-style: italic; color: orange">0</td></tr>
                        <tr><td>LD</td><td style="font-style: italic; color: orange">0</td></tr>
                        <tr><td>Dealer</td><td style="font-style: italic; color: orange">0</td></tr>
                        <tr><td>Sales</td><td style="font-style: italic; color: orange">0</td></tr>
                    </tbody>
                </table>
            </div>

            <div id="yearIncomeChart" style="width: 80%; height:198px; display: block; float: right; background-color: #2c343c"></div>

        </div>
        <div style="width: 100%; height: 5px"></div>

        <div style="width: 100%; height: 300px; background-color: #2c343c; box-shadow: 0 0 5px #000;">
            <div id="monthIncomeChart" style="width: 100%; height: 300px;"></div>
        </div>

        <div style="width: 100%; box-shadow: 0 0 5px #000; overflow: scroll">
            <table  class="table table-bordered table-hover table-striped" id="tbMonthIncome">
                <thead style="background-color: #566778;" >
                    <tr id="trIncome">
                        <td>#</td><td>Total</td>
                    </tr>
                </thead>
                <tbody>
                    <tr><td>Income</td><td class="tdRows14 redFont">0</td></tr>
                    <tr><td>Deposit</td><td class="tdRows14 redFont">0</td></tr>
                    <tr><td>LD</td><td class="tdRows14 redFont">0</td></tr>
                    <tr><td>Dealer</td><td class="tdRows14 redFont">0</td></tr>
                    <tr><td>Sales</td><td class="tdRows14 redFont">0</td></tr>
                </tbody>
            </table>
        </div>
    </div>

    <hr/>

    <div style="width: 100%; clear: both">
        <table id="tbTotal" class="table table-striped">
            <thead>
                <tr>
                    <td>Count</td>
                    <td>Amount</td>
                    <td>TxFee</td>
                    <td>Deposit</td>
                    <td>LD</td>
                    <td>Dealer</td>
                    <td>Sales</td>
                </tr>
            </thead>
            <tbody>
                <tr id="trTotal">
                    <td class="tdTotal">0</td>
                    <td class="tdTotal">0</td>
                    <td class="tdTotal">0</td>
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
            <input type="text" class="form-control" placeholder="type in keyword (client key, sales, transaction id)"
                   aria-describedby="basic-addon1" id="ipSearch">
        </div>
    </div>

    <div style="width: 100%; box-shadow: 0 0 5px #000; margin-top: 8px">
        <table class="table table-bordered table-hover table-striped" id="tbOrders">
            <thead style="background-color: #566778;">
                <tr>
                    <td>#</td>
                    <td>ClientKey</td>
                    <td>Sales</td>
                    <td>TransactionId</td>
                    <td>Amount</td>
                    <td>TxFee</td>
                    <td>Deposit</td>
                    <td>LD</td>
                    <td>Dealer</td>
                    <td>Sales</td>
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
                            <option value="lease">contracted</option>
                            <option value="rent">monthly</option>
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
                        <td>${authorizeTransactionInfo.salesName}</td>
                        <td style="font-size: 12px">${authorizeTransactionInfo.transactionId}</td>
                        <td>${authorizeTransactionInfo.amount}</td>
                        <td>${authorizeTransactionInfo.txFee}</td>
                        <td>${authorizeTransactionInfo.deposit}</td>
                        <td>${authorizeTransactionInfo.ldCommission}</td>
                        <td>${authorizeTransactionInfo.dealerCommission}</td>
                        <td>${authorizeTransactionInfo.salesCommission}</td>
                        <td style="font-size: 12px">${fn:substring(authorizeTransactionInfo.createTime, 0, 19)}</td>
                        <td>${authorizeTransactionInfo.category}</td>
                        <td>${authorizeTransactionInfo.type}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

</rapid:override>
<%@ include file="../base.jsp"%>