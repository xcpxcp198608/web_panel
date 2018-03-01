<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<rapid:override name="title">
    Customers
</rapid:override>

<rapid:override name="css_js">
    <script type="text/javascript" src="Resource/js/mexico/customers.js"></script>
</rapid:override>

<rapid:override name="content">

    <div class="row" style="padding: 0 10px 0 10px;">
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
                    <a id="btCreate" data-toggle="tooltip" title="activate the device">
                        <span class="badge badge-primary text-center" style="padding: 3px">
                            <i class="fa fa-plus-square fa-lg"></i>&nbsp;Create
                        </span>
                    </a>
                    <a id="btActivate" data-toggle="tooltip" title="activate the device">
                        <span class="badge badge-success text-center" style="padding: 3px">
                            <i class="fa fa-check fa-lg"></i>&nbsp;Activate
                        </span>
                    </a>
                    <a id="btLimited" data-toggle="tooltip" title="manually block the device">
                        <span class="badge badge-warning text-center" style="padding: 3px">
                            <i class="fa fa-lock fa-lg"></i>&nbsp;Block
                        </span>
                    </a>
                    <div style="display: none">
                    <a id="btCanceled" data-toggle="tooltip" title="Reset the device">
                        <span class="badge badge-danger text-center" style="padding: 3px">
                            <i class="fa fa-close fa-lg"></i>&nbsp;Cancel
                        </span>
                    </a>
                    <a id="btCashActivate" data-toggle="tooltip" title="To manually activate the device when pay by cash">
                        <span class="badge badge-primary text-center" style="padding: 3px">
                            <i class="fa fa-money fa-lg"></i>&nbsp;Cash Activation
                        </span>
                    </a>
                    </div>
                </div>
            </div>
        </div>

        <div style="width: 100%; padding: 10px; background-color: white; overflow: scroll">
            <table class="table table-sm table-hover table-striped" id="tbUsers">
                <thead style="background-color: #769abb;">
                    <tr>
                        <th>#</th>
                        <th>Item</th>
                        <th>ClientKey</th>
                        <th>Mac</th>
                        <th>Name</th>
                        <th>ActivateTime</th>
                        <th>Status</th>
                        <th>Online</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${authRentUserInfoList}" var="authRentUserInfo" varStatus="status">
                    <tr>
                        <td><input type="radio" name="rdUser" value="${authRentUserInfo.clientKey}"
                                   currentRow="${status.index}" currentStatus="${authRentUserInfo.status}"></td>
                        <td>${status.index+1}</td>
                        <td>${authRentUserInfo.clientKey}</td>
                        <td>${authRentUserInfo.mac}</td>
                        <td>${authRentUserInfo.firstName}&nbsp;${authRentUserInfo.lastName}</td>
                        <td>${fn:substring(authRentUserInfo.activateTime, 0, 19)}</td>
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
                                    <i class="fa fa-circle" online="${authRentUserInfo.online}"></i>
                                </span>
                            </c:if>
                            <c:if test="${authRentUserInfo.online == 'false'}">
                                <span class="text-secondary">
                                    <i class="fa fa-circle-o" online="${authRentUserInfo.online}"></i>
                                </span>
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
                    <h6 class="modal-title" id="exampleModalLongTitle3">Create User</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="input-group input-group-sm mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon1">
                                <i class="fa fa-user fa-lg"></i>
                            </span>
                        </div>
                        <input type="text" class="form-control" placeholder="mac" id="ipMac"
                               aria-label="mac" name="mac" aria-describedby="basic-addon1" maxlength="17">
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
                            <span class="input-group-text" id="basic-addon4">
                                <i class="fa fa-flag fa-lg"></i>
                            </span>
                        </div>
                        <input type="text" class="form-control" placeholder="LastName" id="ipLastName"
                               aria-label="Username" aria-describedby="basic-addon4">
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
                            <span class="input-group-text" id="basic-addon6">
                                <i class="fa fa-phone fa-lg"></i>
                            </span>
                        </div>
                        <input type="number" class="form-control" placeholder="Phone" id="ipPhone"
                               aria-label="Username" aria-describedby="basic-addon6">
                    </div>
                </div>
                <div class="modal-footer">
                    <span id="errorCreate" class="badge badge-danger"></span>
                    <button type="button" class="btn btn-sm btn-primary" id="btSubmitCreate">Create</button>
                </div>
            </div>
        </div>
    </div>


    <div class="modal fade" id="modalCashActivate" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h6 class="modal-title" id="modalCashActivateTitle">Cash Activate</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Confirm receive the activation pay<br/><br/>
                    <div class="input-group input-group-sm mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon234">
                                <i class="fa fa-lock"></i>
                            </span>
                        </div>
                        <input style="display:none" type="text" name="fakeusernameremembered"/>
                        <input style="display:none" type="password" name="fakepasswordremembered"/>
                        <input type="password" class="form-control" placeholder="password" id="ipAdminPassword"
                               value="">
                    </div>
                </div>
                <div class="modal-footer">
                    <span id="errorCashActivate" class="badge badge-danger"></span>
                    <button type="button" class="btn btn-sm btn-primary" id="btSubmitCashActivate">Activate</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="modalCancel" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h6 class="modal-title" id="modalCancelTitle">Cancel</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    Confirm cancel this customer?
                </div>
                <div class="modal-footer">
                    <span id="errorCancel" class="badge badge-danger"></span>
                    <button type="button" class="btn btn-sm btn-primary" id="btSubmitCancel">Confirm</button>
                </div>
            </div>
        </div>
    </div>

</rapid:override>

<%@ include file="base_mexico.jsp"%>

