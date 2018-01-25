<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<rapid:override name="title">
    Home
</rapid:override>
<rapid:override name="css_js">
    <script type="application/javascript" src="Resource/js/world.js"></script>
    <script type="application/javascript" src="Resource/js/admin/distribution.js"></script>
    <script type="application/javascript" src="Resource/js/admin/home.js"></script>
</rapid:override>


<rapid:override name="content">

    <div class="row" style="padding: 10px" >
        <div style="background-color: #ffffff; width: 100%; padding: 10px">
            <span class="text-center text-muted"><abbr>The number of real time online:</abbr>
                <span class="badge badge-success text-center" id="sOnline"
                      data-toggle="tooltip" title="current online number!"></span>
            </span>
            <div id="chart_online" class="d-flex" style="width: 100%; height: 200px"></div>
        </div>
    </div>

    <div class="row" style="margin-top: 10px">
        <div class="col-5" style="padding-left: 10px; background-color: white">
            <span class="text-center text-muted" style="padding: 10px">The detail of sales volume:</span>
        </div>
        <div class="col-5 text-left text-darks" style="background-color: white">
            <span><span id="aYear">2018</span>-<span id="aMonth">01</span></span>
        </div>
        <div class="col-2 text-center" style="padding-right: 10px; background-color: white">
            <a id="btPreviousMonth" data-toggle="tooltip" title="press this show previous month info!">
                <span class="badge badge-info text-center">Previous</span>
            </a>
            <a id="btNextMonth" data-toggle="tooltip" title="press this next month info!">
                <span class="badge badge-info text-center">Next</span>
            </a>
        </div>
    </div>

    <div class="row" style="padding: 0 10px 0 10px">
        <div id="chart_month" style="width: 100%; height: 250px;"></div>
    </div>

    <div class="row" style="padding: 0 10px 0 10px">
        <div style="background-color: #ffffff; padding: 10px 20px 0 20px; width: 100%">
            <table class="table table-sm table-hover table-striped table-dark" id="tb_month" style="overflow: scroll">
            <thead>
                <tr id="trHead"><td>#</td><td>Total</td></tr>
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

    <div class="row" style="margin-top: 10px">
        <div class="row d-flex" style="background-color: #ffffff; padding: 10px; width: 100%">
            <div id="chart_distribution" style="width: 100%; height: 400px;"></div>
        </div>
    </div>

</rapid:override>
<%@ include file="base_admin.jsp"%>