<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<rapid:override name="title">
    Create
</rapid:override>
<rapid:override name="css_js">
    <script type="application/javascript" src="Resource/js/sales/create_user.js"></script>
</rapid:override>


<rapid:override name="content">

    <div class="row" style="padding: 0 10px 0 10px">
        <div style="width: 100%; background-color: #c11021; height: 3px"></div>
        <div class="col-12" style="background-color: #ffffff; padding: 10px 10px 0 10px">
            <div>
                <span class="text-muted">Step1.choose the plan:</span>
                <table class="table table-sm table-hover table-striped table-dark" id="tbCategory">
                    <thead>
                    <tr><td>#</td><td>Category</td><td>Price</td><td>Deposit</td><td>Expires</td>
                        <td>Bonus</td><td>Activate</td><td>MonthPay</td><td>FirstPay</td><td>Description</td>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${commissionCategoryInfoList}" var="commissionCategoryInfo">
                        <tr>
                            <td><input type="radio" name="categoryRadio" value="${commissionCategoryInfo.category}"></td>
                            <td>${commissionCategoryInfo.category}</td>
                            <td>${commissionCategoryInfo.price}</td>
                            <td>${commissionCategoryInfo.deposit}</td>
                            <td>${commissionCategoryInfo.expires}</td>
                            <td>${commissionCategoryInfo.bonus}</td>
                            <td>${commissionCategoryInfo.activatePay}</td>
                            <td>${commissionCategoryInfo.monthPay}</td>
                            <td>${commissionCategoryInfo.firstPay}</td>
                            <td>${commissionCategoryInfo.description}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>


    <div class="row" style="padding: 10px 10px 0 10px">
        <div style="width: 100%; background-color: #0815a8; height: 3px"></div>
        <div style="width: 100%; background-color: #ffffff; padding: 10px">
            <span class="text-muted">Step2.choose payment method:</span>
            <div>
                <label class="radio-inline">
                    <input type="radio" name="payMethod" checked value="0">
                    <span class="badge badge-secondary">cash</span>
                </label>&nbsp;
                <label class="radio-inline">
                    <input type="radio" name="payMethod" value="1">
                    <span class="badge badge-secondary">credit card</span>
                </label>
            </div>
        </div>

    </div>


    <div class="row" style="padding: 10px 10px 0 10px">
        <div style="width: 100%; background-color: #1b740a; height: 3px"></div>
        <div style="width: 100%; background-color: #ffffff; padding: 10px">
            <span class="text-muted">Step3.fill in information</span>
        </div>
    </div>

    <div class="row" style="padding: 0 10px 0 10px">
        <div class="col-12" style="background-color: white;  : 0 10px 10px 10px">
            <div class="input-group input-group-sm mb-3">
                <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon11">
                            <i class="fa fa-user fa-lg"></i>
                        </span>
                </div>
                <input type="text" class="form-control" placeholder="mac" id="ipMac"
                       aria-label="mac" aria-describedby="basic-addon11" maxlength="17">
            </div>
            <div class="input-group input-group-sm mb-3">
                <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon13">
                            <i class="fa fa-child fa-lg"></i>
                        </span>
                </div>
                <input type="text" class="form-control" placeholder="first name" id="ipFirstName"
                       aria-label="first name" aria-describedby="basic-addon13">
            </div>
            <div class="input-group input-group-sm mb-3">
                <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon14">
                            <i class="fa fa-flag fa-lg"></i>
                        </span>
                </div>
                <input type="text" class="form-control" placeholder="last name" id="ipLastName"
                       aria-label="last name" aria-describedby="basic-addon14">
            </div>
            <div class="input-group input-group-sm mb-3">
                <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon15">
                            <i class="fa fa-envelope-o fa-lg"></i>
                        </span>
                </div>
                <input type="email" class="form-control" placeholder="email address" id="ipEmail"
                       aria-label="email address" aria-describedby="basic-addon15">
            </div>
            <div class="input-group input-group-sm mb-3">
                <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon16">
                            <i class="fa fa-phone fa-lg"></i>
                        </span>
                </div>
                <input type="number" class="form-control" placeholder="phone number" id="ipPhone"
                       aria-label="phone number" aria-describedby="basic-addon16">
            </div>
            <div class="input-group input-group-sm mb-3">
                <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon160">
                            <i class="fa fa-globe fa-lg"></i>
                        </span>
                </div>
                <input type="number" class="form-control" placeholder="post code" id="ipPostCode"
                       aria-label="postCode" aria-describedby="basic-addon160">
            </div>
            <div class="input-group input-group-sm mb-3">
                <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon161">
                            <i class="fa fa-university fa-lg"></i>
                        </span>
                </div>
                <input type="text" class="form-control" placeholder="post address" id="ipPostAddress"
                       aria-label="post address" aria-describedby="basic-addon161">
            </div>

            <div class="input-group input-group-sm mb-3">
                <label class="radio-inline">
                    <input type="radio" name="express" checked value="0">
                    <span class="badge badge-secondary">pick up</span>
                </label>&nbsp;
                <label class="radio-inline">
                    <input type="radio" name="express" value="1">
                    <span class="badge badge-secondary">express</span>
                </label>
            </div>



            <div style="display: none" id="dCardInfo">
                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon17">
                            <i class="fa fa-credit-card fa-lg"></i>
                        </span>
                    </div>
                    <input type="number" class="form-control" placeholder="credit card number" id="ipCardNumber"
                           aria-label="Username" aria-describedby="basic-addon17">
                </div>
                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon18">
                            <i class="fa fa-calendar fa-lg"></i>
                        </span>
                    </div>
                    <input type="number" class="form-control" placeholder="expires date(mmyy)" id="ipExpirationDate"
                           aria-label="Username" aria-describedby="basic-addon18">
                </div>
                <div class="input-group input-group-sm mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="basic-addon19">
                            <i class="fa fa-exclamation-triangle fa-lg"></i>
                        </span>
                    </div>
                    <input type="number" class="form-control" placeholder="security key" id="ipSecurityKey"
                           aria-label="Username" aria-describedby="basic-addon19">
                </div>
            </div>


            <div>
                <div class="input-group input-group-sm mb-3">
                    <button type="button" class="btn btn-sm btn-primary" id="btSubmitCreate">Create</button>
                    <span id="errorCreate" class="badge badge-danger text-center"
                          style="font-size: 16px"></span>
                </div>
            </div>

        </div>
    </div>


</rapid:override>
<%@ include file="base_sales.jsp"%>