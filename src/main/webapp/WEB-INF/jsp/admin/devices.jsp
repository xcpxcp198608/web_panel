<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<rapid:override name="title">
    Devices
</rapid:override>

<rapid:override name="css_js">
    <script type="text/javascript" src="Resource/js/admin/devices.js"></script>
</rapid:override>

<rapid:override name="content">

    <div class="row" style="padding: 0 10px 0 10px;">
        <div style="width: 100%; background-color: #0815a8; height: 3px"></div>
        <div style="width: 100%; background-color: #ffffff;">
            <span class="text-center text-muted" style="padding: 10px">
                <abbr>Choose device(s) to update to selected rep:</abbr>
            </span>
            <span class="badge badge-success text-center" id="totalDevices"
                  data-toggle="tooltip" title="total check in devices!">
                    ${fn:length(deviceRentInfoList)}
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
                    <a id="btCheckIn" data-toggle="tooltip" title="check in new device">
                        <span class="badge badge-primary text-center">
                            <i class="fa fa-plus fa-lg"></i>&nbsp;Check In
                        </span>
                    </a>
                </div>
                <div class="col-1">
                    <a id="btUpdate" data-toggle="tooltip" title="update device binding to special sales">
                        <span class="badge badge-warning text-center">
                            <i class="fa fa-pencil fa-lg"></i>&nbsp;Update
                        </span>
                    </a>
                </div>
            </div>
        </div>

        <div style="width: 100%; padding: 10px; background-color: white; overflow: scroll">
            <table class="table table-sm table-hover table-striped" id="tbDevices">
                <thead style="background-color: #769abb;">
                <tr>
                    <th>#</th>
                    <th>Id</th>
                    <th>Mac</th>
                    <th>Sales</th>
                    <th>CheckInTime</th>
                    <th>Rented</th>
                    <th>RentedTime</th>
                    <th>Checked</th>
                    <th>CheckNumber</th>
                    <th>CheckedTime</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${deviceRentInfoList}" var="deviceRentInfo" varStatus="status">
                    <tr>
                        <td><input type="checkbox" name="cbDevice" value="${deviceRentInfo.mac}"
                                   currentRow="${status.index}"></td>
                        <td>${status.index+1}</td>
                        <td>${deviceRentInfo.mac}</td>
                        <td>
                            <c:if test="${deviceRentInfo.salesName eq 'pcp'}">
                                <span class="text-muted">${deviceRentInfo.salesName}</span>
                            </c:if>
                            <c:if test="${deviceRentInfo.salesName ne 'pcp'}">
                                <span class="text-warning">${deviceRentInfo.salesName}</span>
                            </c:if>

                        </td>
                        <td>${deviceRentInfo.createTime}</td>
                        <td>
                            <c:if test="${deviceRentInfo.rented == true}">
                                <span class="text-success"><i class="fa fa-check-circle"></i></span>
                            </c:if>
                            <c:if test="${deviceRentInfo.rented == false}">
                                <span class="text-secondary"><i class="fa fa-times-circle"></i></span>
                            </c:if>
                        </td>
                        <td>${deviceRentInfo.rentTime}</td>
                        <td>
                            <c:if test="${deviceRentInfo.checked == true}">
                                <span class="text-success"><i class="fa fa-check-circle"></i></span>
                            </c:if>
                            <c:if test="${deviceRentInfo.checked == false}">
                                <span class="text-secondary"><i class="fa fa-times-circle"></i></span>
                            </c:if>
                        </td>
                        <td>${deviceRentInfo.checkNumber}</td>
                        <td>${deviceRentInfo.checkTime}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</rapid:override>

<rapid:override name="modal">
    <div class="modal fade" id="modalCheckIn" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h6 class="modal-title" id="exampleModalLongTitle">Check in</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="input-group input-group-sm mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon1">
                                <i class="fa fa-shield fa-lg"></i>
                            </span>
                        </div>
                        <input type="text" class="form-control" placeholder="Mac" id="ipMac"
                               aria-label="mac" aria-describedby="basic-addon1" maxlength="17">
                    </div>

                    <div class="input-group input-group-sm mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon9">
                                <i class="fa fa-user fa-lg"></i>
                            </span>
                        </div>
                        <select class="custom-select" id="ipSalesId">
                            <option value="0">Choose Sales</option>
                            <c:forEach items="${authSalesInfoList}" var="authSalesInfo">
                                <option value="${authSalesInfo.id}">
                                        ${authSalesInfo.username}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <span id="errorCheckIn" class="badge badge-danger"></span>
                    <button type="button" class="btn btn-sm btn-primary" id="btSubmitCheckIn">Check In</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="modalUpdate" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h6 class="modal-title">Update sales</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div>
                        <span>this will deduct $</span>
                        <span id="spAmount" class="text-danger"></span>
                        <span>from rep credit card</span>
                    </div><br/>
                    <div class="input-group input-group-sm mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon12">
                                <i class="fa fa-bullhorn fa-lg"></i>
                            </span>
                        </div>
                        <select class="custom-select" id="ipUpdateSalesId">
                            <option value="0">Choose Rep</option>
                            <c:forEach items="${authSalesInfoList}" var="authSalesInfo">
                                <option value="${authSalesInfo.id}">
                                        ${authSalesInfo.username}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="input-group input-group-sm mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon16">
                                <i class="fa fa-lock fa-lg"></i>
                            </span>
                        </div>
                        <input style="display:none" type="text" name="fakeusernameremembered"/>
                        <input style="display:none" type="password" name="fakepasswordremembered"/>
                        <input type="password" class="form-control" placeholder="password" id="ipAdminPassword"
                               value="">
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

