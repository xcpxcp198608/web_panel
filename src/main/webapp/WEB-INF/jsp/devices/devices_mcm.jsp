<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<rapid:override name="title">
    MLM
</rapid:override>
<rapid:override name="css_js">
    <script type="application/javascript" src="Resource/js/devices/devices_mcm.js"></script>
</rapid:override>


<rapid:override name="content">

    <div class="row" style="padding: 0 10px 0 10px">
        <div style="width: 100%; background-color: #ffffff; padding: 10px">
            <nav>
                <div class="nav nav-tabs nav-dark" id="nav-tab" role="tablist">
                    <a class="nav-item nav-link active" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="true">MLM Devices</a>
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

                    <div class="row" style="margin-top: 5px">
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

                    <table class="table table-sm table-hover table-striped" id="tbMCMDevices" style="margin-top: 5px">
                        <thead style="background-color: #566778;">
                        <tr>
                            <th>Item</th>
                            <th>Mac</th>
                            <th>SoldTo</th>
                            <th>CheckOutBy</th>
                            <th>CheckInTime</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${deviceMLMInfoList}" var="deviceInfo" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${deviceInfo.mac}</td>
                                <td>${deviceInfo.username}</td>
                                <td>${deviceInfo.checkInUser}</td>
                                <td>
                                    <fmt:formatDate value="${deviceInfo.checkInTime}" pattern="yyyy-MM-dd HH:mm:ss" />
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
                    <h6 class="modal-title" >OutGoing</h6>
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
                        <input type="text" class="form-control" placeholder="mac" id="ipCheckInMac"
                               name="mac" aria-label="mac" aria-describedby="basic-addon113" maxlength="17">
                    </div>

                    <div class="input-group input-group-sm mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon124">
                                <i class="fa fa-user fa-lg"></i>
                            </span>
                        </div>
                        <input style="display:none" type="text" name="fakeusernameremembered"/>
                        <input style="display:none" type="password" name="fakepasswordremembered"/>
                        <input type="text" class="form-control" placeholder="customer name" id="ipUsername"
                               aria-label="username" aria-describedby="basic-addon124" maxlength="17">
                    </div>

                    <div class="input-group input-group-sm mb-3">
                        <div class="input-group-prepend">
                            <span class="input-group-text" id="basic-addon125">
                                <i class="fa fa-lock fa-lg"></i>
                            </span>
                        </div>
                        <input type="password" class="form-control" placeholder="Double confirm with your authorization code" id="ipCheckInCode"
                               aria-label="username" aria-describedby="basic-addon124" maxlength="17">
                    </div>
                </div>
                <div class="modal-footer">
                    <span id="errorCheckIn" class="badge badge-danger"></span>
                    <button type="button" class="btn btn-sm btn-primary" id="btSubmitCheckIn">Confirm</button>
                </div>
            </div>
        </div>
    </div>

</rapid:override>

<%@ include file="base_devices.jsp"%>