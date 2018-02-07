<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<rapid:override name="title">
    Commission
</rapid:override>
<rapid:override name="css_js">
    <script type="application/javascript" src="Resource/js/admin/commission.js"></script>
</rapid:override>


<rapid:override name="content">
    <div>
        <div style="width: 100%; background-color: #0815a8; height: 3px"></div>
        <div style="width: 100%; padding: 0 20px 10px 20px; background-color: white">
            <span class="text-muted">The detail of rental plan:</span>
            <table class="table table-sm table-hover table-striped table-dark" id="tbCategory">
                <thead>
                    <tr><td>#</td><td>Price</td><td>Expires</td><td>Bonus</td>
                        <td>Deposit</td><td>MonthPay</td><td>SVCCharge</td>
                        <td>LD</td><td>LDE</td><td>Dealer</td><td>Sales</td>
                        <td>Activation</td><td>LD-A</td><td>LDE-A</td>
                        <td>Dealer-A</td><td>Sales-A</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${commissionCategoryInfoList}" var="commissionCategoryInfo">
                        <tr>
                            <td>${commissionCategoryInfo.category}</td>
                            <td>${commissionCategoryInfo.price}</td>
                            <td>${commissionCategoryInfo.expires}</td>
                            <td>${commissionCategoryInfo.bonus}</td>
                            <td style="color: greenyellow">${commissionCategoryInfo.deposit}</td>
                            <td style="color: greenyellow">${commissionCategoryInfo.monthPay}</td>
                            <td style="color: greenyellow">${commissionCategoryInfo.svcCharge}</td>
                            <td style="color: greenyellow">${commissionCategoryInfo.ldCommission}</td>
                            <td style="color: greenyellow">${commissionCategoryInfo.ldeCommission}</td>
                            <td style="color: greenyellow">${commissionCategoryInfo.dealerCommission}</td>
                            <td style="color: greenyellow">${commissionCategoryInfo.salesCommission}</td>
                            <td style="color: #0676f1">${commissionCategoryInfo.activatePay}</td>
                            <td style="color: #0676f1">${commissionCategoryInfo.ldActivationComm}</td>
                            <td style="color: #0676f1">${commissionCategoryInfo.ldeActivationComm}</td>
                            <td style="color: #0676f1">${commissionCategoryInfo.dealerActivationComm}</td>
                            <td style="color: #0676f1">${commissionCategoryInfo.salesActivationComm}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

    <div style="width: 100%; background-color: white; padding: 0 10px 0 10px">
        <div class="row">
            <div class="col-4">
                <div id="B1Chart" style="width: 100%; height: 150px"></div>
            </div>
            <div class="col-4">
                <div id="P1Chart" style="width: 100%; height: 150px"></div>
            </div>
            <div class="col-4">
                <div id="P2Chart" style="width: 100%; height: 150px"></div>
            </div>
        </div>
    </div>



    <div style="width: 100%; margin-top: 10px; background-color: white">
        <div style="width: 100%; background-color: #0815a8; height: 3px"></div>
        <span style="padding: 10px" class="text-muted">Details of confirmed sales amount by month in year:</span>
        <div class="row">
            <div class="col-3">
                <div style="padding: 20px 20px 0 20px ; overflow: scroll">
                    <table class="table table-sm table-hover table-striped table-dark" id="tbYearIncome">
                        <tbody>
                            <tr><td>Income</td><td style="font-style: italic; color: orange">0</td></tr>
                            <tr><td>Deposit</td><td style="font-style: italic; color: greenyellow">0</td></tr>
                            <tr><td>LD</td><td style="font-style: italic; color: greenyellow">0</td></tr>
                            <tr><td>LDE</td><td style="font-style: italic; color: greenyellow">0</td></tr>
                            <tr><td>Dealer</td><td style="font-style: italic; color: greenyellow">0</td></tr>
                            <tr><td>Reps</td><td style="font-style: italic; color: greenyellow">0</td></tr>
                            <tr><td>SvcCharge</td><td style="font-style: italic; color: greenyellow">0</td></tr>
                            <tr><td>TxFee</td><td style="font-style: italic; color: greenyellow">0</td></tr>
                            <tr><td>Activation</td><td style="font-style: italic; color: #0676f1">0</td></tr>
                            <tr><td>LD-A</td><td style="font-style: italic; color: #0676f1">0</td></tr>
                            <tr><td>LDE-A</td><td style="font-style: italic; color: #0676f1">0</td></tr>
                            <tr><td>Dealer-A</td><td style="font-style: italic; color: #0676f1">0</td></tr>
                            <tr><td>Sales-A</td><td style="font-style: italic; color: #0676f1">0</td></tr>
                        </tbody>
                    </table>
                </div>
            </div>
            <div class="col-9">
                <div id="yearIncomeChart" style="width: 100%; height: 320px"></div>
            </div>
        </div>
    </div>


    <div style="width: 100%; margin-top: 10px; background-color: white">
        <div style="width: 100%; background-color: #0815a8; height: 3px"></div>
        <div>
            <div class="row">
                <div class="col-5">
                    <span class="text-center text-muted" style="padding: 0 10px 10px 10px">Details of confirmed sales amount by day in a month:</span>
                </div>
                <div class="col-4 text-left text-dark">
                    <span><span id="aYear">2018</span>-<span id="aMonth">01</span></span>
                </div>
                <div class="col-3 text-right">
                    <a id="btPreviousMonth" data-toggle="tooltip" title="Previous month">
                        <span class="badge badge-info text-center">Previous</span>
                    </a>
                    <a id="btNextMonth" data-toggle="tooltip" title="Next month">
                        <span class="badge badge-info text-center">Next</span>
                    </a>
                    <span>&nbsp;&nbsp;</span>
                </div>
            </div>
        </div>


        <div>
            <div id="monthIncomeChart" style="width: 100%; height: 250px;"></div>
        </div>

        <div style="padding: 0 20px 10px 20px; overflow: scroll">
            <table  class="table table-sm table-hover table-striped table-dark" id="tbMonthIncome">
                <thead>
                    <tr id="trMonthIncome">
                        <td>#</td><td>Total</td>
                    </tr>
                </thead>
                <tbody>
                    <tr><td>Income</td><td class="text-danger">0</td></tr>
                    <tr><td>Deposit</td><td class="text-warning">0</td></tr>
                    <tr><td>LD</td><td class="text-warning">0</td></tr>
                    <tr><td>LDE</td><td class="text-warning">0</td></tr>
                    <tr><td>Dealer</td><td class="text-warning">0</td></tr>
                    <tr><td>Reps</td><td class="text-warning">0</td></tr>
                    <tr><td>SvcCharge</td><td class="text-warning">0</td></tr>
                    <tr><td>Tx-Fee</td><td class="text-warning">0</td></tr>
                    <tr><td>Activation</td><td class="text-primary">0</td></tr>
                    <tr><td>LD-A</td><td class="text-primary">0</td></tr>
                    <tr><td>LDE-A</td><td class="text-primary">0</td></tr>
                    <tr><td>Dealer-A</td><td class="text-primary">0</td></tr>
                    <tr><td>Rep-A</td><td class="text-primary">0</td></tr>
                </tbody>
            </table>
        </div>

    </div>
</rapid:override>
<%@ include file="base_admin.jsp"%>