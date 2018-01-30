<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<rapid:override name="title">
    Transactions
</rapid:override>

<rapid:override name="css_js">
    <script type="text/javascript" src="Resource/js/admin/transactions.js"></script>
</rapid:override>

<rapid:override name="content">

    <div class="row">
        <div class="col-3" style="padding: 0 0 0 10px">
            <div style="width: 100%; background-color: #1b740a; height: 3px"></div>
            <div style="background-color: #ffffff; padding: 10px">
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-danger ba-strong" id="totalAmount">
                            0
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >total amount</span>
                </div>
            </div>
        </div>
        <div class="col-3" style="padding: 0 5px 0 10px">
            <div style="width: 100%; background-color: #1b740a; height: 3px"></div>
            <div style="background-color: #ffffff; padding: 10px">
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-warning ba-strong" id="totalLdeCommission">
                            0
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >total lde</span>
                </div>
            </div>
        </div>
        <div class="col-3" style="padding: 0 10px 0 5px">
            <div style="width: 100%; background-color: #1b740a; height: 3px"></div>
            <div style="background-color: #ffffff; padding: 10px">
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-warning ba-strong" id="totalDealersCommission">
                            0
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >total dealer</span>
                </div>
            </div>
        </div>
        <div class="col-3" style="padding: 0 10px 0 0">
            <div style="width: 100%; background-color: #1b740a; height: 3px"></div>
            <div style="background-color: #ffffff; padding: 10px">
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-warning ba-strong" id="totalSalesCommission">
                            0
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >total sales</span>
                </div>
            </div>
        </div>
    </div>

    <div class="row" style="padding: 10px 10px 0 10px;">
        <div style="width: 100%; background-color: #0815a8; height: 3px"></div>
        <div style="width: 100%; background-color: #ffffff;">
            <span class="text-center text-muted" style="padding: 10px">
                <abbr>List of all transactions :</abbr>
            </span>
            <span class="badge badge-dark text-center" id="totalRecorders"
                  data-toggle="tooltip" title="total transaction recorder numbers!">
                    ${fn:length(authorizeTransactionRentalInfoList)}
            </span>
        </div>

        <div style="width: 100%; padding: 10px; background-color: white">
            <div class="row">
                <div class="col-2">
                    <a><span class="badge badge-info text-center">search index</span></a>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <label class="radio-inline">
                        <input type="radio" name="searchRadio" checked value="0">
                        <span class="badge badge-secondary" data-toggle="tooltip"
                              title="search transaction without index" >none</span>
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="searchRadio" value="2">
                        <span class="badge badge-secondary" data-toggle="tooltip"
                              title="search transaction with index client key">client key</span>
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="searchRadio" value="3">
                        <span class="badge badge-secondary" data-toggle="tooltip"
                              title="search transaction with index transaction id">transaction id</span>
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="searchRadio" value="4">
                        <span class="badge badge-secondary" data-toggle="tooltip"
                              title="search transaction with index trading time">trading time</span>
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="searchRadio" value="5">
                        <span class="badge badge-secondary" data-toggle="tooltip"
                              title="search transaction with index sales">sales</span>
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="searchRadio" value="6">
                        <span class="badge badge-secondary" data-toggle="tooltip"
                              title="search transaction with index plan">plan</span>
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="searchRadio" value="7">
                        <span class="badge badge-secondary" data-toggle="tooltip"
                              title="search transaction with index type">type</span>
                    </label>
                </div>
            </div>
            <div class="row">
                <div class="col-12">
                    <div class="input-group input-group-sm mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text">
                                <i class="fa fa-search fa-lg"></i>
                            </span>
                        </div>
                        <input type="text" class="form-control" id="ipSearch">
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-6">
                    <a id="btResendInvoice" data-toggle="tooltip" title="resend the invoice to customer">
                        <span class="badge badge-warning text-center" style="padding: 3px">
                            <i class="fa fa-send fa-lg"></i>&nbsp;Resend Invoice
                        </span>
                    </a>
                </div>
            </div>
        </div>

        <div style="width: 100%; padding: 10px; background-color: white; overflow: scroll">
            <table class="table table-sm table-hover table-striped" id="tbOrders">
                <thead style="background-color: #769abb;">
                    <tr>
                        <td>#</td>
                        <td>Item</td>
                        <td>ClientKey</td>
                        <td>TransactionId</td>
                        <td>TradingTime</td>
                        <td>Sales</td>
                        <td>Plan</td>
                        <td>Type</td>
                        <td>Amount</td>
                        <td>TxFee</td>
                        <td>Deposit</td>
                        <td>LD</td>
                        <td>LDE</td>
                        <td>Dealer</td>
                        <td>Sales</td>
                        <td>Invoice</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${authorizeTransactionRentalInfoList}" var="authorizeTransactionRentalInfo" varStatus="status">
                        <tr>
                            <td><input class="radio radio-sm" type="radio" name="rdTransactions" value="${authorizeTransactionRentalInfo.clientKey}"
                                       currentRow="${status.index}"></td>
                            <td>${status.index + 1}</td>
                            <td>${authorizeTransactionRentalInfo.clientKey}</td>
                            <td>${authorizeTransactionRentalInfo.transactionId}</td>
                            <td>${authorizeTransactionRentalInfo.createTime}</td>
                            <td>${authorizeTransactionRentalInfo.salesName}</td>
                            <td>${authorizeTransactionRentalInfo.category}</td>
                            <td>${authorizeTransactionRentalInfo.type}</td>
                            <td>${authorizeTransactionRentalInfo.amount}</td>
                            <td>${authorizeTransactionRentalInfo.txFee}</td>
                            <td>${authorizeTransactionRentalInfo.deposit}</td>
                            <td>${authorizeTransactionRentalInfo.ldCommission}</td>
                            <td>${authorizeTransactionRentalInfo.ldeCommission}</td>
                            <td>${authorizeTransactionRentalInfo.dealerCommission}</td>
                            <td>${authorizeTransactionRentalInfo.salesCommission}</td>
                            <td>
                                <a href="http://www.ldlegacy.com:8899/static/panel/invoice/ld_invoice_${authorizeTransactionRentalInfo.transactionId}.pdf"
                                   target="_blank">
                                    <span class="text-secondary">
                                        <i class="fa fa-bookmark-o"></i>
                                    </span>
                                </a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</rapid:override>

<rapid:override name="modal">
    <div class="modal fade" id="modalDetail" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h6 class="modal-title" id="exampleModalLongTitle">Customer detail</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <table class="table table-sm table-hover" id="tbUserDetails">
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
                </div>
                <div class="modal-footer">
                    <span id="errorDetail" class="badge badge-danger"></span>
                </div>
            </div>
        </div>
    </div>

</rapid:override>

<%@ include file="base_admin.jsp"%>

