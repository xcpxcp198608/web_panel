<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<rapid:override name="title">
    Home
</rapid:override>
<rapid:override name="css_js">
    <link rel="stylesheet" href="Resource/css/users/home.css" />
    <script type="application/javascript" src="Resource/js/users/home.js"></script>
</rapid:override>

<rapid:override name="content_header">
    <div style="width: 100%; height: 34px;">
        <div style="width: 84%; display: block; float: left">
            <span style="height: 35px; line-height: 34px; font-size: 20px">Month information</span>
        </div>

        <div style="width: 8%; display: block; float: left; align-content: center;">
            <div style="margin: auto; height: 34px">
                <a style="display: block; float: left; height: 34px; line-height: 34px;
                    font-size: 20px" id="aYear">2017</a>
                <a style="display: block; float: left; height: 34px; line-height: 34px;
                    font-size: 20px">-</a>
                <a style="display: block; float: left; height: 34px; line-height: 34px;
                    font-size: 20px" id="aMonth">11</a>
            </div>
        </div>

        <div style="width: 8%; display: block; float: left">
            <div style="display: block; float: right; align-content: center; margin-top: 3px;">
                <button type="button" class="btn btn-default" aria-label="Left Align" id="btPreviousMonth"
                        title="previous month" disabled="disabled">
                    <span class="glyphicon glyphicon-menu-left" aria-hidden="true"></span>
                </button>
                <button type="button" class="btn btn-default" aria-label="Left Align" id="btNextMonth"
                        title="next month" disabled="disabled">
                    <span class="glyphicon glyphicon-menu-right" aria-hidden="true"></span>
                </button>
            </div>
        </div>
    </div>

</rapid:override>

<rapid:override name="content_body">

    <div id="chartYearVolume" style="width: 100%; height: 200px; background-color: #2c343c;
        box-shadow: 0 0 5px #0a148f; clear: both"></div>
    <div>
        <table id="tbYearVolume" class="table table-bordered table-hover table-striped table-condensed"
               style="box-shadow: 0 0 5px #000; overflow: scroll">
            <thead style="background-color: #566778;">
                <tr>
                    <th>YEAR</th><th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th>
                    <th>7</th><th>8</th><th>9</th><th>10</th><th>11</th><th>12</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>0</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td><td>0</td>
                    <td>0</td><td>0</td><td>0</td><td>0</td><td>0</td>
                </tr>
            </tbody>
        </table>
    </div>

    <div id="chartMonthVolume" style="width: 100%; height: 300px; background-color: #2c343c;
        box-shadow: 0 0 5px #0a148f; clear: both"></div>
    <div>
        <table id="tbMonthVolume" class="table table-bordered table-hover table-striped table-condensed"
               style="box-shadow: 0 0 5px #000;">
            <thead style="background-color: #566778;">
                <tr><th>MONTH</th></tr>
            </thead>
            <tbody>
                <td>0</td>
            </tbody>
        </table>
    </div>

    <div id="chartLevel5" style="width: 100%; height: 200px; background-color: #2c343c;
        box-shadow: 0 0 5px #0a148f; clear: both"></div> <br/>
    <div id="chartLevel4" style="width: 100%; height: 200px; background-color: #2c343c;
        box-shadow: 0 0 5px #0a148f; clear: both"></div> <br/>
    <div id="chartLevel3" style="width: 100%; height: 200px; background-color: #2c343c;
        box-shadow: 0 0 5px #0a148f; clear: both"></div> <br/>
    <div id="chartLevel2" style="width: 100%; height: 200px; background-color: #2c343c;
        box-shadow: 0 0 5px #0a148f; clear: both"></div> <br/>

</rapid:override>
<%@ include file="base_manger.jsp"%>