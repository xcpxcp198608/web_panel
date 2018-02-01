<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<rapid:override name="title">
    Sales
</rapid:override>
<rapid:override name="css_js">
    <script type="application/javascript" src="Resource/js/dealer/sales.js"></script>
</rapid:override>


<rapid:override name="content">

    <div class="row" style="padding: 10px 10px 0 10px;">
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
                    <a id="btCreate" data-toggle="tooltip" title="create new sales">
                        <span class="badge badge-primary text-center">
                            <i class="fa fa-plus fa-lg"></i>&nbsp;Create
                        </span>
                    </a>
                </div>
                <div class="col-1">
                    <a id="btUpdate" data-toggle="tooltip" title="update sales password, choose a radio before click">
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
                <tr id="trSales">
                    <th></th>
                    <th>#</th>
                    <th>Username</th>
                    <th>Password</th>
                    <th>Dealer</th>
                    <th>SSN</th>
                    <th>Email</th>
                    <th>BankInfo</th>
                    <th>Phone</th>
                    <th>Time</th>
                    <th>Gold</th>
                    <th>Users</th>
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
                            <a href="/panel/dealer/users/2/${authSalesInfo.id}" title="show all users under this sales" target="_blank">
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
                            <span class="input-group-text" id="basic-addon12">
                                <i class="fa fa-lock fa-lg"></i>
                            </span>
                        </div>
                        <input type="password" class="form-control" placeholder="Password" id="ipPassword4"
                               aria-label="Username" aria-describedby="basic-addon12">
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

<%@ include file="base_dealer.jsp"%>