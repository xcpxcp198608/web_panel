<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<rapid:override name="title">
    PCP
</rapid:override>
<rapid:override name="css_js">
    <script type="application/javascript" src="Resource/js/devices/devices_pcp.js"></script>
</rapid:override>


<rapid:override name="content">

    <div class="row" style="padding: 0 10px 0 10px">
        <div style="width: 100%; background-color: #ffffff; padding: 10px">
            <nav>
                <div class="nav nav-tabs nav-dark" id="nav-tab" role="tablist">
                    <a class="nav-item nav-link active" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="true">PCP Devices</a>
                </div>
            </nav>
            <div class="tab-content" id="nav-tabContent" style="margin-top: 10px">
                <div class="tab-pane fade show active" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
                    <div class="row">
                        <div class="col-6">
                            <a id="btCheckIn" data-toggle="tooltip" title="enable the device">
                                <span class="badge badge-primary text-center" style="padding: 3px">
                                    <i class="fa fa-plus-square fa-lg"></i>&nbsp;OutGoing
                                </span>
                            </a>&nbsp;
                        </div>
                    </div>

                    <table class="table table-sm table-hover table-striped" id="tbPCPDevices" style="margin-top: 5px">
                        <thead style="background-color: #566778;">
                        <tr>
                            <th>Id</th>
                            <th>Mac</th>
                            <th>Sales</th>
                            <th>CheckInTime</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${devicePCPInfoList}" var="devicePCPInfo" varStatus="status">
                            <tr>
                                <td>${devicePCPInfo.id}</td>
                                <td>${devicePCPInfo.mac}</td>
                                <td>${devicePCPInfo.salesName}</td>
                                <td>${devicePCPInfo.createTime}</td>
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
                    <h6 class="modal-title" >OutGoing</h6>
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


    <div class="modal fade" id="modalEnable" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h6 class="modal-title" >Enable</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="input-group input-group-sm mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon113">
                                <i class="fa fa-shield fa-lg"></i>
                            </span>
                        </div>
                        <input type="text" class="form-control" placeholder="start mac" id="ipEnableMac"
                               name="mac" aria-label="mac" aria-describedby="basic-addon113" maxlength="17">
                    </div>

                    <div class="input-group input-group-sm mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon124">
                                <i class="fa fa-user fa-lg"></i>
                            </span>
                        </div>
                        <input type="text" class="form-control" placeholder="username" id="ipUsername"
                               aria-label="username" aria-describedby="basic-addon124" maxlength="17">
                    </div>
                </div>
                <div class="modal-footer">
                    <span id="errorEnable" class="badge badge-danger"></span>
                    <button type="button" class="btn btn-sm btn-primary" id="btSubmitEnable">Confirm</button>
                </div>
            </div>
        </div>
    </div>

</rapid:override>

<%@ include file="base_devices.jsp"%>