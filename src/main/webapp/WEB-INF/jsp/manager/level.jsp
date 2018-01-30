<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<rapid:override name="title">
    Level
</rapid:override>
<rapid:override name="css_js">
    <script type="application/javascript" src="Resource/js/manager/level.js"></script>
</rapid:override>


<rapid:override name="content">

    <div class="row" style="padding: 0 10px 0 10px">
        <div style="width: 100%; background-color: #0815a8; height: 3px"></div>
    </div>
    <div class="row" style="padding: 0 10px 0 10px">
        <div class="col-2" style="border-right: 1px solid #2c343c; background-color: #ffffff; padding: 10px">
            <div>
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-warning ba-strong">
                            ${levelDistributionInfo.level5}
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >Level5</span>
                </div>
            </div>
        </div>
        <div class="col-2" style="border-right: 1px solid #2c343c; background-color: #ffffff; padding: 10px">
            <div>
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-success ba-strong">
                            ${levelDistributionInfo.level4}
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >Level4</span>
                </div>
            </div>
        </div>
        <div class="col-2" style="border-right: 1px solid #2c343c; background-color: #ffffff; padding: 10px" >
            <div>
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-danger ba-strong">
                            ${levelDistributionInfo.level3}
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >Level3</span>
                </div>
            </div>
        </div>
        <div class="col-2" style="border-right: 1px solid #2c343c; background-color: #ffffff; padding: 10px">
            <div>
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-primary ba-strong">
                            ${levelDistributionInfo.level2}
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >Level2</span>
                </div>
            </div>
        </div>
        <div class="col-2" style="border-right: 1px solid #2c343c; background-color: #ffffff; padding: 10px">
            <div>
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-dark ba-strong">
                            ${levelDistributionInfo.level1}
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >Level1</span>
                </div>
            </div>
        </div>
        <div class="col-2" style="background-color: #ffffff; padding: 10px">
            <div>
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-secondary ba-strong">
                            ${levelDistributionInfo.level0}
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >Limited</span>
                </div>
            </div>
        </div>

    </div>




    <div class="row" style="padding: 10px 10px 0 10px">
        <div style="width: 100%; background-color: #0815a8; height: 3px"></div>
        <div class="col-5" style="padding: 10px; background-color: white">
            <span class="text-center text-muted" style="padding: 10px">The expires date of every level in year:</span>
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

        <div id="chartLevel5" style="width: 100%; height: 200px; padding: 0 20px 0 20px; background-color: white"></div>

        <div style="background-color: #ffffff; padding: 10px 20px 0 20px; width: 100%; overflow: scroll">
            <table class="table table-sm table-hover table-striped table-dark" id="tbLevel5" style="overflow: scroll">
                <thead >
                    <tr>
                        <th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th>
                        <th>7</th><th>8</th><th>9</th><th>10</th><th>11</th><th>12</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                    </tr>
                </tbody>
            </table>
        </div>


        <div id="chartLevel4" style="width: 100%; height: 200px; padding: 0 20px 0 20px; background-color: white"></div>

        <div style="background-color: #ffffff; padding: 10px 20px 0 20px; width: 100%; overflow: scroll">
            <table class="table table-sm table-hover table-striped table-dark" id="tbLevel4" style="overflow: scroll">
                <thead >
                    <tr>
                        <th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th>
                        <th>7</th><th>8</th><th>9</th><th>10</th><th>11</th><th>12</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                    </tr>
                </tbody>
            </table>
        </div>


        <div id="chartLevel3" style="width: 100%; height: 200px; padding: 0 20px 0 20px; background-color: white"></div>

        <div style="background-color: #ffffff; padding: 10px 20px 0 20px; width: 100%; overflow: scroll">
            <table class="table table-sm table-hover table-striped table-dark" id="tbLevel3" style="overflow: scroll">
                <thead >
                    <tr>
                        <th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th>
                        <th>7</th><th>8</th><th>9</th><th>10</th><th>11</th><th>12</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                    </tr>
                </tbody>
            </table>
        </div>


        <div id="chartLevel2" style="width: 100%; height: 200px; padding: 0 20px 0 20px; background-color: white"></div>

        <div style="background-color: #ffffff; padding: 10px 20px 0 20px; width: 100%; overflow: scroll">
            <table class="table table-sm table-hover table-striped table-dark" id="tbLevel2" style="overflow: scroll">
                <thead >
                    <tr>
                        <th>1</th><th>2</th><th>3</th><th>4</th><th>5</th><th>6</th>
                        <th>7</th><th>8</th><th>9</th><th>10</th><th>11</th><th>12</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                        <td>0</td><td>0</td>
                    </tr>
                </tbody>
            </table>
        </div>

    </div>


</rapid:override>
<%@ include file="base_manager.jsp"%>