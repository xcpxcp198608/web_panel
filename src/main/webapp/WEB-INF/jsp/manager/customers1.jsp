<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<rapid:override name="title">
    Customers
</rapid:override>

<rapid:override name="css_js">
    <script type="text/javascript" src="Resource/js/manager/customers1.js"></script>
    <script type="text/javascript" src="https://cdn.bootcss.com/printThis/1.12.2/printThis.min.js"></script>
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
                    ${fn:length(authRegisterUserInfoList)}
            </span>
        </div>

        <div style="width: 100%; padding: 10px; background-color: white">
            <div class="row">
                <div class="col-3">
                    <a><span class="badge badge-info text-center">search index</span></a>
                </div>
            </div>
            <div class="row">
                <div class="col-6">
                    <label class="radio-inline">
                        <input type="radio" name="searchRadio" checked value="0">
                        <span class="badge badge-secondary" data-toggle="tooltip"
                              title="search customer without index, match all column" >all</span>
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="searchRadio" value="1">
                        <span class="badge badge-secondary" data-toggle="tooltip"
                              title="search customer with index id">id</span>
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="searchRadio" value="2">
                        <span class="badge badge-secondary" data-toggle="tooltip"
                              title="search customer with index username">username</span>
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="searchRadio" value="3">
                        <span class="badge badge-secondary" data-toggle="tooltip"
                              title="search customer with index mac">mac</span>
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="searchRadio" value="4">
                        <span class="badge badge-secondary" data-toggle="tooltip"
                              title="search customer with index email">email</span>
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="searchRadio" value="5">
                        <span class="badge badge-secondary" data-toggle="tooltip"
                              title="search customer with index name">name</span>
                    </label>
                    <label class="radio-inline">
                        <input type="radio" name="searchRadio" value="6">
                        <span class="badge badge-secondary" data-toggle="tooltip"
                              title="search customer with index expires date">expiration date</span>
                    </label>
                </div>

                <div class="col-1">
                    <span class="badge badge-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
                              title="search customer with index level" id="level">level</span>&nbsp;
                    <div class="dropdown-menu" aria-labelledby="level">
                        <span class="dropdown-item" id="ipLevelAll">all</span>
                        <span class="dropdown-item" id="ipLevelFto">fto</span>
                        <span class="dropdown-item" id="ipLevel4">4</span>
                        <span class="dropdown-item" id="ipLevel3">3</span>
                        <span class="dropdown-item" id="ipLevel2">2</span>
                        <span class="dropdown-item" id="ipLevel1">1</span>
                        <span class="dropdown-item" id="ipLevel0">0</span>
                    </div>
                </div>

                <div class="col-1">
                    <span class="badge badge-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
                          title="search customer with index status" id="status">status</span>&nbsp;
                    <div class="dropdown-menu" aria-labelledby="status">
                        <span class="dropdown-item" id="ipStatusAll1">
                            all
                        </span>
                        <span class="dropdown-item" id="ipStatusActivate">
                            <i class="fa fa-check text-muted"></i>
                        </span>
                        <span class="dropdown-item" id="ipStatusDeactivate">
                            <i class="fa fa-close text-muted"></i>
                        </span>
                    </div>
                </div>

                <div class="col-1">
                    <span class="badge badge-secondary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"
                          title="search customer with index online" id="online">online</span>&nbsp;
                    <div class="dropdown-menu" aria-labelledby="online">
                        <span class="dropdown-item" id="ipStatusAll">all</span>
                        <span class="dropdown-item" id="ipStatusOnline">online</span>
                        <span class="dropdown-item" id="ipStatusOffline">offline</span>
                    </div>
                </div>
            </div>


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
                            <i class="fa fa-check"></i>&nbsp;Activate
                        </span>
                    </a>
                    <a id="btLimited" data-toggle="tooltip" title="limited the device">
                        <span class="badge badge-warning text-center" style="padding: 3px">
                            <i class="fa fa-lock"></i>&nbsp;Block
                        </span>
                    </a>
                    <a id="btDelete" data-toggle="tooltip" title="delete the device">
                        <span class="badge badge-danger text-center" style="padding: 3px">
                            <i class="fa fa-close"></i>&nbsp;Delete
                        </span>
                    </a>
                    <a id="btPrint" data-toggle="tooltip" title="print customer information that display in current">
                        <span class="badge badge-primary text-center" style="padding: 3px">
                            <i class="fa fa-print"></i>&nbsp;Print&PDF
                        </span>
                    </a>
                    <c:if test="${permission > 100}">
                        <a id="btExport" data-toggle="tooltip" title="export customer information to excel file">
                        <span class="badge badge-warning text-center" style="padding: 3px">
                            <i class="fa fa-share-square"></i>&nbsp;Export
                        </span>
                        </a>
                    </c:if>
                </div>
            </div>

            <c:if test="${permission > 100}">
                <div class="row" style="margin-top: 5px; height: 30px; font-size: 12px">
                    <div class="col-1">
                        <select id="seUpdateLevel" style="height: 30px;">
                            <option value="0">Level</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">fto</option>
                        </select>
                    </div>
                    <div class="col-2" style="height: 30px">
                        <input type="date" placeholder="Date(yyyy-mm-dd)" id="ipExpiresTime">
                    </div>
                    <div class="col-1">
                        <button type="button" class="btn btn-primary btn-sm" data-toggle="tooltip"
                                title="update customer level" id="btUpdateLevel">
                                <i class="fa fa-upload"></i>&nbsp;Update
                        </button>
                    </div>
                </div>
            </c:if>


            <div class="row" style="margin-top: 5px; height: 30px; font-size: 12px">
                <div class="col-12">
                    <nav aria-label="page" >
                        <ul class="pagination justify-content-end">
                            <li class="page-item"><a class="page-link" href="/panel/manager/users/${currentPage - 1}">Previous</a></li>
                            <c:forEach begin="1" end="${totalPage}" varStatus="status">
                                <li class="page-item"><a class="page-link" href="/panel/manager/users/${status.index}">${status.index}</a></li>
                            </c:forEach>
                            <li class="page-item"><a class="page-link" href="/panel/manager/users/${currentPage + 1}">Next</a></li>
                        </ul>
                    </nav>

                </div>
            </div>

        </div>

        <div style="width: 100%; padding: 10px; background-color: white; overflow: scroll" >
            <div id="dTable">
            <table class="table table-sm table-hover table-striped" id="tbUsers">
                <thead style="background-color: #566778;">
                <tr>
                    <th>#</th>
                    <th>ID</th>
                    <th>Username</th>
                    <th>Mac</th>
                    <th>Email</th>
                    <th>Name</th>
                    <th>Expiration</th>
                    <th>Level</th>
                    <th>Status</th>
                    <th>Online</th>
                    <th>More</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${authRegisterUserInfoList}" var="authRegisterUserInfo" varStatus="status">
                    <tr>
                        <td><input type="radio" name="rdUser" value="${authRegisterUserInfo.id}"
                                   currentRow="${status.index}" currentStatus="${authRegisterUserInfo.emailStatus}"></td>
                        <td>${authRegisterUserInfo.id}</td>
                        <td>${authRegisterUserInfo.username}</td>
                        <td>${authRegisterUserInfo.mac}</td>
                        <td>${authRegisterUserInfo.email}</td>
                        <td>${authRegisterUserInfo.firstName}&nbsp;${authRegisterUserInfo.lastName} </td>
                        <td>${authRegisterUserInfo.expiresTime}</td>
                        <td>
                            <c:if test="${authRegisterUserInfo.level == 0}">
                                <span style="color: #a01c34">
                                    <i class="fa fa-lock text-danger"></i>
                                </span>
                            </c:if>
                            <c:if test="${authRegisterUserInfo.level == 5}">
                                <span>fto</span>
                            </c:if>
                            <c:if test="${authRegisterUserInfo.level != 0 && authRegisterUserInfo.level != 5}">
                                <span>${authRegisterUserInfo.level}</span>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${authRegisterUserInfo.emailStatus == 1}">
                                <i class="fa fa-check text-muted" status="${authRegisterUserInfo.emailStatus}"></i>
                            </c:if>
                            <c:if test="${authRegisterUserInfo.emailStatus == 0}">
                                <i class="fa fa-close text-danger" status="${authRegisterUserInfo.emailStatus}"></i>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${authRegisterUserInfo.online == 'true'}">
                                <i class="fa fa-circle text-success"
                                   online="${authRegisterUserInfo.online}"></i>
                            </c:if>
                            <c:if test="${authRegisterUserInfo.online == 'false'}">
                                <i class="fa fa-circle-o text-muted"
                                   online="${authRegisterUserInfo.online}"></i>
                            </c:if>
                        </td>
                        <td>
                            <a  title="show details">
                                 <span class="text-info">
                                    <i class="fa fa-external-link-square"></i>
                                </span>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            </div>
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
                        <tr><td>Username</td><td></td></tr>
                        <tr><td>Password</td><td></td></tr>
                        <tr><td>Mac</td><td></td></tr>
                        <tr><td>FirstName</td><td></td></tr>
                        <tr><td>LastName</td><td></td></tr>
                        <tr><td>Email</td><td></td></tr>
                        <tr><td>Phone</td><td></td></tr>
                        <tr><td>CreateTime</td><td></td></tr>
                        <tr><td>ActivateTime</td><td></td></tr>
                        <tr><td>Level</td><td></td></tr>
                        <tr><td>ExpiresTime</td><td></td></tr>
                        <tr><td>Status</td><td></td></tr>
                        <tr><td>Model</td><td></td></tr>
                        <tr><td>ROM</td><td></td></tr>
                        <tr><td>UI</td><td></td></tr>
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

    <div class="modal fade" id="modalDelete" tabindex="-1" role="dialog"
         aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h6 class="modal-title" >Warning</h6>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    do you confirm to delete this customer?
                </div>
                <div class="modal-footer">
                    <span id="errorDelete" class="badge badge-danger"></span>
                    <button type="button" class="btn btn-sm btn-primary" id="btSubmitDelete">Delete</button>
                </div>
            </div>
        </div>
    </div>

</rapid:override>

<%@ include file="base_manager.jsp"%>

