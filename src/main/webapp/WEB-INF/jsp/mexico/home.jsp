<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<rapid:override name="title">
    Home
</rapid:override>
<rapid:override name="css_js">
    <script type="application/javascript" src="Resource/js/mexico/home.js"></script>
</rapid:override>


<rapid:override name="content">

    <input type="hidden" id="ldC" value="${commissionCategoryInfo.ldCommission}">
    <input type="hidden" id="ldmC" value="${commissionCategoryInfo.ldeCommission}">
    <input type="hidden" id="dealerC" value="${commissionCategoryInfo.dealerCommission}">
    <div class="row">
        <div class="col-3" style="padding: 0 0 0 10px">
            <div style="width: 100%; background-color: #1b740a; height: 3px"></div>
            <div style="background-color: #ffffff; padding: 10px">
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-danger ba-strong" id="totalCount">
                            0
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >TOTAL COUNT</span>
                </div>
            </div>
        </div>
        <div class="col-3" style="padding: 0 5px 0 10px">
            <div style="width: 100%; background-color: #1b740a; height: 3px"></div>
            <div style="background-color: #ffffff; padding: 10px">
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-warning ba-strong" id="totalLdCommission">
                            0
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >LD</span>
                </div>
            </div>
        </div>
        <div class="col-3" style="padding: 0 10px 0 5px">
            <div style="width: 100%; background-color: #1b740a; height: 3px"></div>
            <div style="background-color: #ffffff; padding: 10px">
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-warning ba-strong" id="totalLdmCommission">
                            0
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >LDM</span>
                </div>
            </div>
        </div>
        <div class="col-3" style="padding: 0 10px 0 0">
            <div style="width: 100%; background-color: #1b740a; height: 3px"></div>
            <div style="background-color: #ffffff; padding: 10px">
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-warning ba-strong" id="totalDealersCommission">
                            0
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >DEALER</span>
                </div>
            </div>
        </div>
    </div>


    <div class="row" style="padding: 10px 10px 0 10px">
        <div style="width: 100%; background-color: #0815a8; height: 3px"></div>
        <div class="col-5" style="padding: 10px; background-color: white">
            <span class="text-center text-muted" style="padding: 10px">All active customers in current month:</span>
        </div>
        <div class="col-4 text-left text-darks" style="background-color: white; padding: 10px">
            <span><span id="aYear">2018</span>-<span id="aMonth">01</span></span>
        </div>
        <div class="col-3 text-right" style="padding: 10px; background-color: white">
            <a id="btPreviousMonth" data-toggle="tooltip" title="previous month">
                <span class="badge badge-info text-center">Previous</span>
            </a>
            <a id="btNextMonth" data-toggle="tooltip" title="next month">
                <span class="badge badge-info text-center">Next</span>
            </a>
        </div>
    </div>

    <div class="row" style="padding: 0 10px">
        <div style="width: 100%; padding: 10px; background-color: white; overflow: scroll">
            <table class="table table-sm table-hover table-striped" id="tbUsers">
                <thead style="background-color: #769abb;">
                <tr>
                    <th>Item</th>
                    <th>ClientKey</th>
                    <th>Mac</th>
                    <th>Name</th>
                    <th>ActivateTime</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${authRentUserInfoList}" var="authRentUserInfo" varStatus="status">
                    <tr>
                        <td>${status.index+1}</td>
                        <td>${authRentUserInfo.clientKey}</td>
                        <td>${authRentUserInfo.mac}</td>
                        <td>${authRentUserInfo.firstName}&nbsp;${authRentUserInfo.lastName}</td>
                        <td>${fn:substring(authRentUserInfo.activateTime, 0, 19)}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</rapid:override>
<%@ include file="base_mexico.jsp"%>