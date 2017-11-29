<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<rapid:override name="title">
    Home
</rapid:override>
<rapid:override name="css_js">
    <link rel="stylesheet" href="Resource/css/admin/home.css" />
    <script type="application/javascript" src="Resource/js/world.js"></script>
    <script type="application/javascript" src="Resource/js/admin/distribution.js"></script>
    <script type="application/javascript" src="Resource/js/admin/home.js"></script>
</rapid:override>

<rapid:override name="navigation">
    <ul>
        <li>
            <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
            <a href="/panel/admin/">Home</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-th-list " aria-hidden="true"></span>
            <a href="/panel/admin/sales">Sales</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
            <a href="/panel/admin/users">Customers</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-usd" aria-hidden="true"></span>
            <a href="/panel/admin/commission">Commission</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
            <a href="/panel/signout">SignOut</a>
        </li>
    </ul>
</rapid:override>

<rapid:override name="content">

    <div>
        <div id="chart_distribution" style="width: 100%; height: 500px; background-color: #2c343c; box-shadow: 0 0 5px #000"></div>
    </div>

    <div style="margin-top: 5px">
        <div id="chart_online" style="width: 100%; height: 250px; background-color: #2c343c; box-shadow: 0 0 5px #000"></div>
    </div>

    <div style="position: relative; margin-top: 5px">
        <div style="width: 100%; height: 34px;">
            <div style="width: 80%; display: block; float: left">
                <span style="height: 35px; line-height: 34px; font-size: 20px">Month rental numbers</span>
            </div>

            <div style="width: 10%; display: block; float: left; align-content: center;">
                <div style="margin: auto; height: 34px">
                    <a style="display: block; float: left; height: 34px; line-height: 34px;
                    font-size: 20px" id="aYear">2017</a>
                    <a style="display: block; float: left; height: 34px; line-height: 34px;
                    font-size: 20px">-</a>
                    <a style="display: block; float: left; height: 34px; line-height: 34px;
                    font-size: 20px" id="aMonth">11</a>
                </div>
            </div>

            <div style="width: 10%; display: block; float: left">
                <div style="display: block; float: right; align-content: center; margin-top: 3px;">
                    <button type="button" class="btn btn-default" aria-label="Left Align" id="btPreviousMonth"
                        disabled="disabled" title="previous month">
                        <span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span>
                    </button>
                    <button type="button" class="btn btn-default" aria-label="Left Align" id="btNextMonth"
                            disabled="disabled" title="next month">
                        <span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>
                    </button>
                </div>
            </div>
        </div>

        <div id="chart_month" style="width: 100%; height: 250px; background-color: #2c343c;box-shadow: 0 0 5px #0a148f; clear: both">

        </div>
        <div style="box-shadow: 0 0 5px #030629;">
            <table class="table table-bordered table-hover table-striped table-condensed" id="tb_month">
                <thead style="background-color: #566778">
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

        <div class="progress" id="homeLoading" style="display: none; width: 30%;
            position: absolute; top:100px; left: 35%">
            <div class="progress-bar progress-bar-danger progress-bar-striped active"
                 role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100"
                 style="width: 100%">
            </div>
        </div>
    </div>

</rapid:override>
<%@ include file="../base.jsp"%>