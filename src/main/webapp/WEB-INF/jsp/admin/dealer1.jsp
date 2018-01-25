<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<rapid:override name="title">
    Dealer
</rapid:override>

<rapid:override name="css_js">
    <script type="text/javascript" src="Resource/js/admin/dealer.js"></script>
</rapid:override>

<rapid:override name="content">

    <div class="row" style="padding-top: 20px">
        <div class="col-4" style="padding: 0 10px 0 10px">
            <div style="background-color: #ffffff; padding: 10px">
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-warning ba-strong">
                        ${fn:length(authDealerInfoList)}
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >current number of dealers</span>
                </div>
            </div>
        </div>
        <div class="col-4" style="padding: 0 10px 0 10px">
            <div style="background-color: #ffffff; padding: 10px">
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-warning ba-strong">
                            xxx
                    </span>
                    <span class="badge badge-success ba-strong">
                            0
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >the maximum sales volume</span>
                </div>
            </div>
        </div>
        <div class="col-4" style="padding: 0 10px 0 10px">
            <div style="background-color: #ffffff; padding: 10px">
                <div class="text-center" style="width: 100%">
                    <span class="badge badge-danger ba-strong">
                            0
                    </span>
                </div>
                <div class="text-center" style="width: 100%">
                    <span class="text-muted " >the total commission of current month</span>
                </div>
            </div>
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
        <div style="background-color: white; height: 100px; width: 100%; padding: 10px">
            fsdf
        </div>
    </div>



    <div class="row" style="padding: 20px 10px 0 10px;">
        <div style="width: 100%; background-color: #ffffff;">
            <span class="text-center text-muted" style="padding: 10px">
                <abbr>The detail information of all dealers:</abbr>
            </span>
        </div>
        <div style="width: 100%; padding: 10px; background-color: white">
            <table class="table table-sm table-hover table-striped table-dark" id="tb_month">
            <thead>
                <tr id="trSales">
                <th></th>
                <th>#</th>
                <th>Username</th>
                <th>Password</th>
                <th>Name</th>
                <th>SSN</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Time</th>
                <th>Users</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${authDealerInfoList}" var="authDealerInfo" varStatus="status">
                    <tr>
                        <td><input type="radio" name="update" value="${authDealerInfo.id}" currentRow="${status.index}"></td>
                        <td>${status.index+1}</td>
                        <td>${authDealerInfo.username}</td>
                        <td><input type="text" value="${authDealerInfo.password}" title="pwd" size="12"/></td>
                        <td>${authDealerInfo.firstName}&nbsp;${authDealerInfo.lastName}</td>
                        <td>
                        ${fn:substring(authDealerInfo.ssn, 0, 3)}-${fn:substring(authDealerInfo.ssn, 3, 5)}-${fn:substring(authDealerInfo.ssn, 5, 9)}
                        </td>
                        <td>${authDealerInfo.email}</td>
                        <td>${authDealerInfo.phone}</td>
                        <td>${fn:substring(authDealerInfo.createTime, 0, 19)}</td>
                        <td>
                        <a href="/panel/admin/users/1/${authDealerInfo.id}" title="show all users under this sales">
                        <span class="glyphicon glyphicon-eye-open" aria-hidden="true"></span>
                        </a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        </div>
    </div>
    <br/>



</rapid:override>
<%@ include file="base_admin.jsp"%>



    <%--<div style="width: 100%; height: 40px">--%>
        <%--<div style="width: 20%; display: block; float: left;">--%>
            <%--<button type="button" class="btn btn-default" id="btCreate" title="create a sales">--%>
                <%--<span class="glyphicon glyphicon-plus" aria-hidden="true"></span> Create--%>
            <%--</button>--%>

            <%--<button type="button" class="btn btn-default" id="btUpdate" title="update password which choose">--%>
                <%--<span class="glyphicon glyphicon-edit" aria-hidden="true"></span> Update--%>
            <%--</button>--%>
        <%--</div>--%>
        <%--<div style="width: 80%; display: block; float: left;">--%>
            <%--<div class="input-group">--%>
                <%--<span class="input-group-addon" id="basic-addon1">Search</span>--%>
                <%--<input type="text" class="form-control" placeholder="type in keyword"--%>
                       <%--aria-describedby="basic-addon1" id="ipSearch">--%>
            <%--</div>--%>
        <%--</div>--%>
    <%--</div>--%>


<rapid:override name="details">
    <div style="margin: 20px">
        <div id="div_create">
            <h3 style="width: 100%; text-align: center; color: whitesmoke">CREATE SALES ACCOUNT</h3>
            <br/>

            <div>
                <div style="width: 47%; height: 200px; display: block; float: left">
                    <div class="input-group">
                    <span class="input-group-addon" id="basic-addon3">
                      <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                    </span>
                        <input id="ipUsername" type="text" class="form-control" placeholder="Username"
                               aria-describedby="basic-addon3" name="username" maxlength="20">
                    </div>
                    <br/>
                    <div class="input-group">
                    <span class="input-group-addon" id="basic-addon4">
                      <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
                    </span>
                        <input id="ipPassword" type="password" class="form-control" placeholder="Password(length >= 6)"
                               aria-describedby="basic-addon4" name="password" maxlength="30">
                    </div>
                    <br/>
                    <div class="input-group">
                    <span class="input-group-addon" id="basic-addon5">
                      <span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>
                    </span>
                        <input id="ipFirstName" type="text" class="form-control" placeholder="FirstName"
                               aria-describedby="basic-addon5" name="firstName" maxlength="30">
                    </div>
                    <br/>
                    <div class="input-group">
                    <span class="input-group-addon" id="basic-addon6">
                      <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
                    </span>
                        <input id="ipEmail" type="email" class="form-control" placeholder="Email"
                               aria-describedby="basic-addon6" name="email" maxlength="50">
                    </div>
                    <br/>
                </div>

                <div style="width: 47%; height: 200px; display: block; float: right">
                    <div class="input-group">
                    <span class="input-group-addon" id="basic-addon7">
                      <span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
                    </span>
                        <input id="ipSSN" type="number" class="form-control" placeholder="SSN"
                               aria-describedby="basic-addon7" name="ssn" maxlength="9">
                    </div>
                    <br/>
                    <div class="input-group">
                    <span class="input-group-addon" id="basic-addon8">
                      <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
                    </span>
                        <input id="ipPassword1" type="password" class="form-control" placeholder="Password(length >= 6)"
                               aria-describedby="basic-addon8" name="password1" maxlength="30">
                    </div>
                    <br/>
                    <div class="input-group">
                    <span class="input-group-addon" id="basic-addon9">
                      <span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>
                    </span>
                        <input id="ipLastName" type="text" class="form-control" placeholder="LastName"
                               aria-describedby="basic-addon9" name="lastName" maxlength="30">
                    </div>
                    <br/>
                    <div class="input-group">
                    <span class="input-group-addon" id="basic-addon10">
                      <span class="glyphicon glyphicon-phone" aria-hidden="true"></span>
                    </span>
                        <input id="ipPhone" type="number" class="form-control" placeholder="Phone"
                               aria-describedby="basic-addon10" name="phone" maxlength="20">
                    </div>
                    <br/>
                </div>
            </div>

            <br style="clear: both"/>
            <div style="width: 70%; margin: 20px auto; clear: both">
                <button id="btSubmitCreate" type="submit" class="btn btn-primary" style="width: 100%; margin: auto">
                    Create
                </button>
            </div>
            <h4 id="errorMessage" style="color: red; width: 100%; text-align: center"></h4>
        </div>
    </div>

</rapid:override>

