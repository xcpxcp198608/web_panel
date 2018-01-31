<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<rapid:override name="title">
    Sales Detail
</rapid:override>

<rapid:override name="css_js">
    <script type="text/javascript" src="Resource/js/admin/sales_detail.js"></script>
</rapid:override>

<rapid:override name="content">

    <div class="row">
        <div class="col-4" style="padding: 0 0 0 10px">
            <div style="width: 100%; background-color: #0815a8; height: 3px"></div>
            <div style="background-color: #ffffff; padding: 10px">
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-warning ba-strong">
                        ${fn:length(authSalesInfoList)}
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >total number of sales</span>
                </div>
            </div>
        </div>
        <div class="col-4" style="padding: 0 10px 0 10px">
            <div style="width: 100%; background-color: #0815a8; height: 3px"></div>
            <div style="background-color: #ffffff; padding: 10px">
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-warning ba-strong" id="maxVolumeSales">
                            xxx
                    </span>
                    <span class="badge badge-success ba-strong" id="maxVolume">
                            0
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >the maximum sales volume</span>
                </div>
            </div>
        </div>
        <div class="col-4" style="padding: 0 10px 0 0">
            <div style="width: 100%; background-color: #0815a8; height: 3px"></div>
            <div style="background-color: #ffffff; padding: 10px">
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-danger ba-strong" id="totalCommission">
                            0
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >the total commission of current month</span>
                </div>
            </div>
        </div>
    </div>

    <div class="row" style="padding: 20px 10px 0 10px;">
        <div style="width: 100%; background-color: #c11021; height: 3px;"></div>
        <div style="width: 100%; background-color: #ffffff;">
            <span class="text-center text-muted" style="padding: 10px">
                <abbr>Security deposit credit note:</abbr>
            </span>
        </div>
    </div>

    <div class="row" style="padding: 0 10px">
        <div class="col-12" style="background-color: #ffffff">
            <a id="btCheck" data-toggle="tooltip" title="check means security deposit has already return">
                <span class="badge badge-primary text-center">
                    <i class="fa fa-check fa-lg"></i>&nbsp;Check
                </span>
            </a>
        </div>
    </div>
    <div class="row" style="padding: 0 10px 0 10px">
        <div style="background-color: white; width: 100%; padding: 10px;">
            <table class="table table-sm table-hover" id="tbDevices">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Item</th>
                        <th>Mac</th>
                        <th>Rented</th>
                        <th data-toggle="tooltip" title="Security Deposit Credit Note">SD CN</th>
                        <th data-toggle="tooltip" title="security deposit is returned">Returned</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${rentedDeviceRentInfoList}" var="deviceRentInfo" varStatus="status">
                        <tr>
                            <td><input type="radio" name="rdDevice" value="${deviceRentInfo.salesId}"
                                       currentRow="${status.index}"></td>
                            <td>${status.index+1}</td>
                            <td>${deviceRentInfo.mac}</td>
                            <td>
                                <c:if test="${deviceRentInfo.rented == true}">
                                    <span class="text-success"><i class="fa fa-check-circle"></i></span>
                                </c:if>
                                <c:if test="${deviceRentInfo.rented == false}">
                                    <span class="text-secondary"><i class="fa fa-close"></i></span>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${deviceRentInfo.rented == true}">
                                    <span class="text-success"><i class="fa fa-check-circle"></i></span>
                                </c:if>
                                <c:if test="${deviceRentInfo.rented == false}">
                                    <span class="text-secondary"><i class="fa fa-close"></i></span>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${deviceRentInfo.rented == true}">
                                    <span class="text-success"><i class="fa fa-check-circle"></i></span>
                                </c:if>
                                <c:if test="${deviceRentInfo.rented == false}">
                                    <span class="text-secondary"><i class="fa fa-close"></i></span>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>


</rapid:override>

<rapid:override name="modal">
    <div class="modal fade" id="modalCreate" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h6 class="modal-title" id="exampleModalLongTitle">Create Sales</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <div class="col-6">
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon1">
                                        <i class="fa fa-user fa-lg"></i>
                                    </span>
                                </div>
                                <input type="text" class="form-control" placeholder="Username" id="ipUsername"
                                       aria-label="Username" aria-describedby="basic-addon1">
                            </div>
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon3">
                                        <i class="fa fa-child fa-lg"></i>
                                    </span>
                                </div>
                                <input type="text" class="form-control" placeholder="FirstName" id="ipFirstName"
                                       aria-label="Username" aria-describedby="basic-addon3">
                            </div>
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon5">
                                        <i class="fa fa-envelope-o fa-lg"></i>
                                    </span>
                                </div>
                                <input type="email" class="form-control" placeholder="Email" id="ipEmail"
                                       aria-label="Username" aria-describedby="basic-addon5">
                            </div>
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon26">
                                <i class="fa fa-credit-card fa-lg"></i>
                            </span>
                                </div>
                                <input type="number" class="form-control" placeholder="Debit card number" id="ipBank"
                                       aria-label="Username" aria-describedby="basic-addon26">
                            </div>
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon7">
                                <i class="fa fa-lock fa-lg"></i>
                            </span>
                                </div>
                                <input type="password" class="form-control" placeholder="Password" id="ipPassword"
                                       aria-label="Username" aria-describedby="basic-addon7">
                            </div>
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon8">
                                        <i class="fa fa-lock fa-lg"></i>
                                    </span>
                                </div>
                                <input type="password" class="form-control" placeholder="Password" id="ipPassword1"
                                       aria-label="Username" aria-describedby="basic-addon8">
                            </div>
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon12">
                                        <i class="fa fa-briefcase fa-lg"></i>
                                    </span>
                                </div>
                                <select class="custom-select" id="ipDealerId">
                                    <option value="0">Choose Dealer</option>
                                    <c:forEach items="${authDealerInfoList}" var="authDealerInfo">
                                        <option value="${authDealerInfo.id}">${authDealerInfo.username}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon23">
                                        <i class="fa fa-asterisk fa-lg"></i>
                                    </span>
                                </div>
                                <select class="custom-select" id="ipGoldCategory">
                                    <option value="normal">Choose Gold Category</option>
                                    <c:forEach items="${salesGoldCategoryInfoList}" var="salesGoldCategoryInfo">
                                        <option value="${salesGoldCategoryInfo.category}">${salesGoldCategoryInfo.category}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>



                        <div class="col-6">
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon2">
                                        <i class="fa fa-bookmark fa-lg"></i>
                                    </span>
                                </div>
                                <input type="number" class="form-control" placeholder="SSN" id="ipSSN"
                                       aria-label="Username" aria-describedby="basic-addon2">
                            </div>
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon4">
                                        <i class="fa fa-flag fa-lg"></i>
                                    </span>
                                </div>
                                <input type="text" class="form-control" placeholder="LastName" id="ipLastName"
                                       aria-label="Username" aria-describedby="basic-addon4">
                            </div>
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon6">
                                        <i class="fa fa-phone fa-lg"></i>
                                    </span>
                                </div>
                                <input type="number" class="form-control" placeholder="Phone" id="ipPhone"
                                       aria-label="Username" aria-describedby="basic-addon6">
                            </div>
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon9">
                                        <i class="fa fa-credit-card fa-lg"></i>
                                    </span>
                                </div>
                                <input type="number" class="form-control" placeholder="Credit card number" id="ipCreditCard"
                                       aria-label="Username" aria-describedby="basic-addon9">
                            </div>
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon10">
                                        <i class="fa fa-calendar fa-lg"></i>
                                    </span>
                                </div>
                                <input type="number" class="form-control" placeholder="Expires date" id="ipExpirationDate"
                                       aria-label="Username" aria-describedby="basic-addon10">
                            </div>
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon111">
                                        <i class="fa fa-star fa-lg"></i>
                                    </span>
                                </div>
                                <input type="number" class="form-control" placeholder="Security Key" id="ipSecurityKey"
                                       aria-label="Username" aria-describedby="basic-addon111">
                            </div>
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon13">
                                    <i class="fa fa-tags fa-lg"></i>
                                </span>
                                </div>
                                <select class="custom-select" id="ipActivateCategory">
                                    <option value="">Choose Activate Category</option>
                                    <c:forEach items="${salesActivateCategoryInfoList}" var="salesActivateCategoryInfo">
                                        <option value="${salesActivateCategoryInfo.category}">${salesActivateCategoryInfo.category}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <span id="errorCreate" class="badge badge-danger"></span>
                    <button type="button" class="btn btn-sm btn-primary" id="btSubmitCreate">Create</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="modalUpdate" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h6 class="modal-title">Update password</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="input-group input-group-sm mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon11">
                                <i class="fa fa-lock fa-lg"></i>
                            </span>
                        </div>
                        <input type="password" class="form-control" placeholder="Password" id="ipPassword3"
                               aria-label="Username" aria-describedby="basic-addon11">
                    </div>
                    <div class="input-group input-group-sm mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon32">
                                <i class="fa fa-lock fa-lg"></i>
                            </span>
                        </div>
                        <input type="password" class="form-control" placeholder="Password" id="ipPassword4"
                               aria-label="Username" aria-describedby="basic-addon32">
                    </div>
                </div>
                <div class="modal-footer">
                    <span id="errorUpdate" class="badge badge-danger"></span>
                    <button type="button" class="btn btn-sm btn-primary" id="btSubmitUpdate">Update</button>
                </div>
            </div>
        </div>
    </div>
</rapid:override>

<%@ include file="base_admin.jsp"%>

