<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<rapid:override name="title">
    ALL
</rapid:override>
<rapid:override name="css_js">
    <script type="application/javascript" src="Resource/js/devices/devices_all.js"></script>
</rapid:override>


<rapid:override name="content">

    <div class="row" style="padding: 0 10px 0 10px">
        <div style="width: 100%; background-color: #ffffff; padding: 10px">
            <nav>
                <div class="nav nav-tabs nav-dark" id="nav-tab" role="tablist">
                    <a class="nav-item nav-link active" id="nav-home-tab" data-toggle="tab" href="#nav-home" role="tab" aria-controls="nav-home" aria-selected="true">All Devices</a>
                </div>
            </nav>
            <div class="tab-content" id="nav-tabContent" style="margin-top: 10px">
                <div class="tab-pane fade show active" id="nav-home" role="tabpanel" aria-labelledby="nav-home-tab">
                    <div class="row">
                        <div class="col-6">
                            <a id="btCheckIn" data-toggle="tooltip" title="check in new device">
                                <span class="badge badge-primary text-center" style="padding: 3px">
                                    <i class="fa fa-plus-square fa-lg"></i>&nbsp;CheckIn
                                </span>
                            </a>&nbsp;
                            <a id="btBathCheckIn" data-toggle="tooltip" title="check in new device">
                                <span class="badge badge-warning text-center" style="padding: 3px">
                                    <i class="fa fa-plus-square fa-lg"></i>&nbsp;BatchCheckIn
                                </span>
                            </a>
                        </div>
                    </div>

                    <table class="table table-sm table-hover table-dark" id="tbAllDevices" style="margin-top: 5px">
                        <thead>
                        <tr>
                            <th>Id</th>
                            <th>Mac</th>
                            <th>CreateTime</th>
                            <th>Status</th>
                            <th>ChangeTime</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${deviceAllInfoList}" var="deviceAllInfo" varStatus="status">
                            <tr>
                                <td>${deviceAllInfo.id}</td>
                                <td>${deviceAllInfo.mac}</td>
                                <td>
                                    <fmt:formatDate value="${deviceAllInfo.createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                                <td>
                                    <c:if test="${deviceAllInfo.status == 0}">
                                        <span class="text-muted">PENDING</span>
                                    </c:if>
                                    <c:if test="${deviceAllInfo.status == 1}">
                                        <span class="text-primary">MLM</span>
                                    </c:if>
                                    <c:if test="${deviceAllInfo.status == 2}">
                                         <span class="text-warning">PCP</span>
                                    </c:if>
                                    <c:if test="${deviceAllInfo.status == -1}">
                                         <span class="text-danger">CANCEL</span>
                                    </c:if>
                                </td>
                                <td>
                                    <fmt:formatDate value="${deviceAllInfo.checkInTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

</rapid:override>


<rapid:override name="modal">

    <div class="modal fade" id="modalCheckIn" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h6 class="modal-title" >CheckIn</h6>
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
                               name="mac" aria-label="mac" aria-describedby="basic-addon1" maxlength="17">
                    </div>
                </div>
                <div class="modal-footer">
                    <span id="errorCheckIn" class="badge badge-danger"></span>
                    <button type="button" class="btn btn-sm btn-primary" id="btSubmitCheckIn">Confirm</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="modalBathCheckIn" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h6 class="modal-title" >CheckIn</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="input-group input-group-sm mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon11">
                                <i class="fa fa-shield fa-lg"></i>
                            </span>
                        </div>
                        <input type="text" class="form-control" placeholder="start mac" id="ipStartMac"
                               name="mac" aria-label="mac" aria-describedby="basic-addon1" maxlength="17">
                    </div>

                    <div class="input-group input-group-sm mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon12">
                                <i class="fa fa-shield fa-lg"></i>
                            </span>
                        </div>
                        <input type="text" class="form-control" placeholder="end mac" id="ipEndMac"
                               name="mac" aria-label="mac" aria-describedby="basic-addon1" maxlength="17">
                    </div>
                </div>
                <div class="modal-footer">
                    <span id="errorBathCheckIn" class="badge badge-danger"></span>
                    <button type="button" class="btn btn-sm btn-primary" id="btSubmitBathCheckIn">Confirm</button>
                </div>
            </div>
        </div>
    </div>

</rapid:override>

<%@ include file="base_devices.jsp"%>