<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<rapid:override name="title">
    Sales
</rapid:override>

<rapid:override name="css_js">
    <script type="text/javascript" src="Resource/js/admin/sales.js"></script>
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
                    <span class="badge badge-primary ba-strong" id="maxVolumeSales">
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

    <div class="row" style="padding: 10px 10px 0 10px;">
        <div style="width: 100%; background-color: #c11021; height: 3px;"></div>
    </div>
    <div class="row" style="padding: 0px 10px 0 10px">
        <div class="col-5" style=" background-color: white">
            <span class="text-muted" style="padding: 0 10px 10px 10px"></span>
        </div>
        <div class="col-4 text-left text-darks" style="background-color: white">
            <span><span id="aYear">2018</span>-<span id="aMonth">01</span></span>
        </div>
        <div class="col-3 text-right" style="background-color: white">
            <a id="btPreviousMonth" data-toggle="tooltip" title="previous month">
                <span class="badge badge-info text-center">Previous</span>
            </a>
            <a id="btNextMonth" data-toggle="tooltip" title="next month">
                <span class="badge badge-info text-center">Next</span>
            </a>
        </div>
    </div>

    <div class="row" style="padding: 0 10px 0 10px">
        <div class="col-12" style="background-color: white">
            <nav>
                <div class="nav nav-tabs nav-dark" id="nav-tab" role="tablist">
                    <a class="nav-item nav-link active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab" aria-controls="nav-home" aria-selected="true">Total Commission</a>
                    <%--<a class="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false">Sales Commission</a>--%>
                    <%--<a class="nav-item nav-link" id="nav-contact-tab" data-toggle="tab" href="#nav-contact" role="tab" aria-controls="nav-contact" aria-selected="false">Activation Commission</a>--%>
                </div>
            </nav>
            <div class="tab-content" id="nav-tabContent" style="margin-top: 10px">
                <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
                    <table class="table table-sm table-hover table-dark" id="tbTotalCommissionByMonth">
                        <thead>
                        <tr>
                            <th>Item</th>
                            <th>Username</th>
                            <th>Volume</th>
                            <th>TotalCommission</th>
                            <th>Check</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <div class="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
                    <table class="table table-sm table-hover table-dark" id="tbCommissionByMonth">
                        <thead>
                        <tr>
                            <th>Item</th>
                            <th>Username</th>
                            <th>Volume</th>
                            <th>Commission</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
                <div class="tab-pane fade" id="nav-contact" role="tabpanel" aria-labelledby="nav-contact-tab">
                    <table class="table table-sm table-hover table-dark" id="tbActivationCommByMonth">
                        <thead>
                        <tr>
                            <th>Item</th>
                            <th>Username</th>
                            <th>Volume</th>
                            <th>ActivationComm</th>
                        </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>



    <div class="row" style="padding: 20px 10px 0 10px;">
        <div style="width: 100%; background-color: #0815a8; height: 3px"></div>
        <div style="width: 100%; background-color: #ffffff;">
            <span class="text-center text-muted" style="padding: 10px">
                <abbr>The detail information of all sales:</abbr>
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
                <div class="col-1">
                    <a id="btCreate" data-toggle="tooltip" title="create new account">
                        <span class="badge badge-primary text-center">
                            <i class="fa fa-plus fa-lg"></i>&nbsp;Create
                        </span>
                    </a>
                </div>
                <div class="col-1">
                    <a id="btUpdate" data-toggle="tooltip" title="Choose a sales first from the list to update his password">
                        <span class="badge badge-warning text-center">
                            <i class="fa fa-pencil fa-lg"></i>&nbsp;Update
                        </span>
                    </a>
                </div>
            </div>
        </div>

        <div style="width: 100%; padding: 10px; background-color: white; overflow: scroll">
            <table class="table table-sm table-hover table-striped table-dark" id="tbSales">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Item</th>
                        <th>Username</th>
                        <th>Password</th>
                        <th>Dealer</th>
                        <th>SSN</th>
                        <th>Email</th>
                        <th>BankInfo</th>
                        <th>Phone</th>
                        <th>Time</th>
                        <th>Gold</th>
                        <th data-toggle="tooltip" title="Security Deposit Credit Note" >SDCN</th>
                        <th data-toggle="tooltip" title="show all users under this sales" >Users</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${authSalesInfoList}" var="authSalesInfo" varStatus="status">
                        <tr>
                            <td><input type="radio" name="update" value="${authSalesInfo.id}" currentRow="${status.index}"></td>
                            <td>${status.index+1}</td>
                            <td>${authSalesInfo.username}</td>
                            <td>${authSalesInfo.password}</td>
                            <td>${authSalesInfo.dealerName}</td>
                            <td>
                                ${fn:substring(authSalesInfo.ssn, 0, 3)}-${fn:substring(authSalesInfo.ssn, 3, 5)}-${fn:substring(authSalesInfo.ssn, 5, 9)}
                            </td>
                            <td>${authSalesInfo.email}</td>
                            <td>${authSalesInfo.bankInfo}</td>
                            <td>${authSalesInfo.phone}</td>
                            <td>${authSalesInfo.createTime}</td>
                            <td>
                                <c:if test="${authSalesInfo.gold == true}">
                                    <span class="text-warning"><i class="fa fa-star"></i></span>
                                </c:if>
                                <c:if test="${authSalesInfo.gold == false}">
                                    <span class="text-muted"><i class="fa fa-star-o"></i></span>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${authSalesInfo.sdcn == true}">
                                    <span class="text-danger"><i class="fa fa-fire"></i></span>
                                </c:if>
                                <c:if test="${authSalesInfo.sdcn == false}">
                                    <span class="text-muted"><i class="fa fa-fire"></i></span>
                                </c:if>
                            </td>
                            <td>
                                <a href="/panel/admin/users/2/${authSalesInfo.id}">
                                    <i class="fa fa-eye"></i>
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
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-6">
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon3">
                                        <i class="fa fa-child fa-lg"></i>
                                    </span>
                                </div>
                                <input type="text" class="form-control" placeholder="FirstName" id="ipFirstName"
                                       aria-label="Username" aria-describedby="basic-addon3">
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon4">
                                        <i class="fa fa-flag fa-lg"></i>
                                    </span>
                                </div>
                                <input type="text" class="form-control" placeholder="LastName" id="ipLastName"
                                       aria-label="Username" aria-describedby="basic-addon4">
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-6">
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon5">
                                        <i class="fa fa-envelope-o fa-lg"></i>
                                    </span>
                                </div>
                                <input type="email" class="form-control" placeholder="Email" id="ipEmail"
                                       aria-label="Username" aria-describedby="basic-addon5">
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon6">
                                        <i class="fa fa-phone fa-lg"></i>
                                    </span>
                                </div>
                                <input type="number" class="form-control" placeholder="Phone" id="ipPhone"
                                       aria-label="Username" aria-describedby="basic-addon6">
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-6">
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon7">
                                    <i class="fa fa-lock fa-lg"></i>
                                </span>
                                </div>
                                <input type="password" class="form-control" placeholder="Password" id="ipPassword"
                                       aria-label="Username" aria-describedby="basic-addon7">
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon8">
                                        <i class="fa fa-lock fa-lg"></i>
                                    </span>
                                </div>
                                <input type="password" class="form-control" placeholder="Password" id="ipPassword1"
                                       aria-label="Username" aria-describedby="basic-addon8">
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-6">
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
                        </div>
                        <div class="col-6">
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon13">
                                    <i class="fa fa-tags fa-lg"></i>
                                </span>
                                </div>
                                <select class="custom-select" id="ipActivateCategory">
                                    <option value="">Choose Activate Category</option>
                                    <c:forEach items="${salesActivateCategoryInfoList}" var="salesActivateCategoryInfo">
                                        <option value="${salesActivateCategoryInfo.category}">${salesActivateCategoryInfo.description}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-12">
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon26">
                                    <i class="fa fa-credit-card fa-lg"></i>
                                </span>
                                </div>
                                <input type="number" class="form-control" placeholder="Debit card number" id="ipBank"
                                       aria-label="Username" aria-describedby="basic-addon26">
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-12">
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon9">
                                        <i class="fa fa-credit-card fa-lg"></i>
                                    </span>
                                </div>
                                <input type="number" class="form-control" placeholder="Credit card number" id="ipCreditCard"
                                       aria-label="Username" aria-describedby="basic-addon9">
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-6">
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon10">
                                        <i class="fa fa-calendar fa-lg"></i>
                                    </span>
                                </div>
                                <input type="number" class="form-control" placeholder="Expires date" id="ipExpirationDate"
                                       aria-label="Username" aria-describedby="basic-addon10">
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text" id="basic-addon111">
                                        <i class="fa fa-star fa-lg"></i>
                                    </span>
                                </div>
                                <input type="number" class="form-control" placeholder="Security Key" id="ipSecurityKey"
                                       aria-label="Username" aria-describedby="basic-addon111">
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-12">
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon222">
                                <i class="fa fa-asterisk fa-lg"></i>
                            </span>
                                </div>
                                <input type="number" class="form-control" placeholder="zip code" id="ipZipCode"
                                       aria-label="zipcode" aria-describedby="basic-addon222">
                            </div>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-12">
                            <div class="input-group input-group-sm mb-3">
                                <div class="input-group-prepend">
                                <span class="input-group-text" id="basic-addon223">
                                    <i class="fa fa-home fa-lg"></i>
                                </span>
                                </div>
                                <input type="text" class="form-control" placeholder="billing address" id="ipBillingAddress"
                                       aria-label="billingaddress" aria-describedby="basic-addon223">
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

