<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<rapid:override name="title">
    Customers
</rapid:override>

<rapid:override name="css_js">
    <script type="text/javascript" src="Resource/js/sales/customers.js"></script>
</rapid:override>

<rapid:override name="content">

    <div class="row" style="padding: 10px 10px 0 10px;">
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
        </div>

        <div style="width: 100%; padding: 0 10px 0 10px; background-color: white">
            <table class="table table-sm table-hover table-striped" id="tbUsers">
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Item</th>
                        <th>ClientKey</th>
                        <th>Mac</th>
                        <th>Name</th>
                        <th>ActivateTime</th>
                        <th>Plan</th>
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
                        <td>${authRentUserInfo.category}</td>
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
                                    <i class="fa fa-circle fa-lg" online="${authRentUserInfo.online}"></i>
                                </span>
                            </c:if>
                            <c:if test="${authRentUserInfo.online == 'false'}">
                                <span class="text-secondary">
                                    <i class="fa fa-circle-o fa-lg" online="${authRentUserInfo.online}"></i>
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

</rapid:override>

<%@ include file="base_sales.jsp"%>

