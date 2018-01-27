<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<rapid:override name="title">
    Home
</rapid:override>
<rapid:override name="css_js">
    <script type="application/javascript" src="Resource/js/dealer/home.js"></script>
</rapid:override>


<rapid:override name="content">

    <div class="row" style="padding-top: 10px">
        <div class="col-3" style="padding: 0 0 0 10px">
            <div style="width: 100%; background-color: #c11021; height: 3px"></div>
            <div style="background-color: #ffffff; padding: 10px">
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-success ba-strong" id="totalCommission">
                            ${totalCommission}
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >total commission</span>
                </div>
            </div>
        </div>
        <div class="col-3" style="padding: 0 5px 0 10px">
            <div style="width: 100%; background-color: #c11021; height: 3px"></div>
            <div style="background-color: #ffffff; padding: 10px">
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-warning ba-strong" id="totalVolume">
                            ${totalVolume}
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >total volume</span>
                </div>
            </div>
        </div>
        <div class="col-3" style="padding: 0 10px 0 5px">
            <div style="width: 100%; background-color: #c11021; height: 3px"></div>
            <div style="background-color: #ffffff; padding: 10px">
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-success ba-strong" id="currentMonthCommission">
                            0
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >month commission</span>
                </div>
            </div>
        </div>
        <div class="col-3" style="padding: 0 10px 0 0">
            <div style="width: 100%; background-color: #c11021; height: 3px"></div>
            <div style="background-color: #ffffff; padding: 10px">
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-warning ba-strong" id="currentMonthVolume">
                            0
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >month volume</span>
                </div>
            </div>
        </div>
    </div>


    <div class="row" style="padding: 10px 10px 0 10px">
        <div style="width: 100%; background-color: #1b740a; height: 3px"></div>
        <div class="col-5" style="padding: 10px; background-color: white">
            <span class="text-center text-muted" style="padding: 10px">The detail of commission and volume:</span>
        </div>
        <div class="col-4 text-left text-darks" style="background-color: white; padding: 10px">
            <span><span id="aYear">2018</span>-<span id="aMonth">01</span></span>
        </div>
        <div class="col-3 text-right" style="padding: 10px; background-color: white">
            <a id="btPreviousMonth" data-toggle="tooltip" title="press this show previous month info!">
                <span class="badge badge-info text-center">Previous</span>
            </a>
            <a id="btNextMonth" data-toggle="tooltip" title="press this next month info!">
                <span class="badge badge-info text-center">Next</span>
            </a>
        </div>
    </div>


    <div class="row" style="padding: 0 10px 0 10px">
        <div style="background-color: #ffffff; padding: 0 10px 0 10px; width: 100%; overflow: scroll">
            <table class="table table-sm table-hover table-striped table-dark" id="tbCommission">
                <thead>
                <tr id="thCommission"><td>#</td><td>Total</td></tr>
                </thead>
                <tbody>
                <tr><td>commission</td><td>0</td></tr>
                </tbody>
            </table>
        </div>
    </div>

    <div class="row" style="padding: 0 10px 0 10px">
        <div style="background-color: #ffffff; width: 100%">
            <div id="chartCommission" class="d-flex" style="width: 100%; height: 200px"></div>
        </div>
    </div>

    <div class="row" style="padding: 0 10px 0 10px">
        <div style="background-color: #ffffff; padding: 0 10px 0 10px; width: 100%; overflow: scroll">
            <table class="table table-sm table-hover table-striped table-dark" id="tbVolume">
                <thead>
                <tr id="thVolume"><td>#</td><td>Total</td></tr>
                </thead>
                <tbody>
                <tr><td>volume</td><td>0</td></tr>
                <tr><td>B1</td><td>0</td></tr>
                <tr><td>P1</td><td>0</td></tr>
                <tr><td>P2</td><td>0</td></tr>
                </tbody>
            </table>
        </div>
    </div>

    <div class="row" style="padding: 0 10px 0 10px">
        <div style="background-color: #ffffff; width: 100%">
            <div id="chartVolume" class="d-flex" style="width: 100%; height: 200px"></div>
        </div>
    </div>



</rapid:override>
<%@ include file="base_dealer.jsp"%>