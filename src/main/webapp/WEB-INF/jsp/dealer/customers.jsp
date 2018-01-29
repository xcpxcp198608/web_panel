<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<rapid:override name="title">
    Customers
</rapid:override>

<rapid:override name="css_js">
    <script type="text/javascript" src="Resource/js/dealer/customers.js"></script>
</rapid:override>

<rapid:override name="content">

    <div class="row" style="padding: 10px 10px 0 10px;">
        <div style="width: 100%; background-color: #0815a8; height: 3px"></div>
        <div style="width: 100%; background-color: #ffffff;">
            <span class="text-center text-muted" style="padding: 10px">
                <abbr>The detail information of customers:</abbr>
            </span>
            <span class="badge badge-success text-center" id="onLineUsers"
                  data-toggle="tooltip" title="total online numbers!">
                    0
            </span>

            <span class="text-center" data-toggle="tooltip">
                    -
            </span>

            <span class="badge badge-dark text-center" id="totalUsers"
                  data-toggle="tooltip" title="total customer numbers!">
                    ${fn:length(authRentUserInfoList)}
            </span>
        </div>

        <div style="width: 100%; padding: 10px; background-color: white">
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
                    <a id="btActivate" data-toggle="tooltip" title="activate the device">
                        <span class="badge badge-success text-center" style="padding: 3px">
                            <i class="fa fa-check fa-lg"></i>&nbsp;Activate
                        </span>
                    </a>
                    <a id="btLimited" data-toggle="tooltip" title="limited the device">
                        <span class="badge badge-warning text-center" style="padding: 3px">
                            <i class="fa fa-lock fa-lg"></i>&nbsp;Limited
                        </span>
                    </a>
                </div>
            </div>
        </div>

        <div style="width: 100%; padding: 10px; background-color: white; overflow: scroll">
            <table class="table table-sm table-hover table-striped" id="tbUsers">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Item</th>
                        <th>ClientKey</th>
                        <th>Sales</th>
                        <th>Dealer</th>
                        <th>Mac</th>
                        <th>Name</th>
                        <th>ActivateTime</th>
                        <th>Plan</th>
                        <th>Status</th>
                        <th>Online</th>
                        <th>More</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${authRentUserInfoList}" var="authRentUserInfo" varStatus="status">
                    <tr>
                        <td><input type="radio" name="rdUser" value="${authRentUserInfo.clientKey}"
                                   currentRow="${status.index}" currentStatus="${authRentUserInfo.status}"></td>
                        <td>${status.index+1}</td>
                        <td>${authRentUserInfo.clientKey}</td>
                        <td>${authRentUserInfo.salesName}</td>
                        <td>${authRentUserInfo.dealerName}</td>
                        <td>${authRentUserInfo.mac}</td>
                        <td>${authRentUserInfo.firstName}&nbsp;${authRentUserInfo.lastName}</td>
                        <td>${fn:substring(authRentUserInfo.activateTime, 0, 19)}</td>
                        <td>${authRentUserInfo.category}</td>
                        <td>
                            <c:if test="${authRentUserInfo.status == 'activate'}">
                                <span class="text-success">${authRentUserInfo.status}</span>
                            </c:if>
                            <c:if test="${authRentUserInfo.status == 'deactivate'}">
                                <span class="text-danger">${authRentUserInfo.status}</span>
                            </c:if>
                            <c:if test="${authRentUserInfo.status == 'limited'}">
                                <span class="text-danger">${authRentUserInfo.status}</span>
                            </c:if>
                            <c:if test="${authRentUserInfo.status == 'canceled'}">
                                <span class="text-secondary">${authRentUserInfo.status}</span>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${authRentUserInfo.online == 'true'}">
                                <span class="text-success">
                                    <i class="fa fa-circle fa-lg" online="${authRentUserInfo.online}"></i>
                                </span>
                            </c:if>
                            <c:if test="${authRentUserInfo.online == 'false'}">
                                <span class="text-secondary">
                                    <i class="fa fa-circle-o fa-lg" online="${authRentUserInfo.online}"></i>
                                </span>
                            </c:if>
                        </td>
                        <td>
                            <a title="show user's details">
                                 <span class="text-info">
                                    <i class="fa fa-external-link-square fa-lg"></i>
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

<%@ include file="base_dealer.jsp"%>

