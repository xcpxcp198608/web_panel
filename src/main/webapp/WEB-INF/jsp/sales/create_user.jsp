<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<rapid:override name="title">
    Create
</rapid:override>
<rapid:override name="css_js">
    <script type="application/javascript" src="Resource/js/sales/create_user.js"></script>
</rapid:override>

<rapid:override name="navigation">
    <ul>
        <li>
            <span class="glyphicon glyphicon-home" aria-hidden="true"></span>
            <a href="/panel/sales/">My</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
            <a href="/panel/sales/users">Customers</a>
        </li>
        <li>
            <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
            <a href="/panel/signout">SignOut</a>
        </li>
    </ul>
</rapid:override>

<rapid:override name="content">
    <div>
        <div style="width: 50%; margin: auto">
            <h3 style="width: 100%; text-align: center">Create an new user</h3>
            <%--<div class="input-group">--%>
                <%--<select  style="height: 34px;" id="seCategory" name="category">--%>
                    <%--<option value="">Choose Plan</option>--%>
                    <%--<option value="B1">B1</option>--%>
                    <%--<option value="P1">P1</option>--%>
                    <%--<option value="P2">P2</option>--%>
                <%--</select>--%>
            <%--</div>--%>
            <div style="margin-top: 3px">
                <table class="table table-bordered table-condensed" id="tbCategory">
                    <tbody>
                        <tr style="background-color: #566778">
                            <td>Plan</td>
                            <td>FirstPay</td>
                            <td>MonthPay</td>
                            <td>Deposit</td>
                            <td>Expires</td>
                            <td>Bonus</td>
                            <td>Total</td>
                        </tr>
                        <tr>
                            <td>
                                <select  style="width: 100%; height: 100%; background-color: rgba(0,0,0,0); border: none; text-align: center"
                                         id="seCategory" name="category">
                                    <option value="">Choose</option>
                                    <option value="B1">B1</option>
                                    <option value="P1">P1</option>
                                    <option value="P2">P2</option>
                                </select>
                            </td>
                            <td style="color: red">0</td>
                            <td style="color: darkblue;">0</td>
                            <td>0</td>
                            <td>0</td>
                            <td>0</td>
                            <td>0</td>
                        </tr>
                    </tbody>
                </table>
            </div>

            <div class="input-group">
                    <span class="input-group-addon" id="basic-addon3">
                      <span class="glyphicon glyphicon-barcode" aria-hidden="true"></span>
                    </span>
                <input id="ipMac" type="text" class="form-control" placeholder="mac"
                       aria-describedby="basic-addon3" name="mac" maxlength="17">
            </div>
            <br/>
            <div class="input-group">
                    <span class="input-group-addon" id="basic-addon4">
                      <span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>
                    </span>
                <input id="ipFirstName" type="text" class="form-control" placeholder="First Name"
                       aria-describedby="basic-addon4" name="firstName">
            </div>
            <br/>
            <div class="input-group">
                    <span class="input-group-addon" id="basic-addon5">
                      <span class="glyphicon glyphicon-bookmark" aria-hidden="true"></span>
                    </span>
                <input id="ipLastName" type="text" class="form-control" placeholder="Last Name"
                       aria-describedby="basic-addon5" name="lastName">
            </div>
            <br/>
            <div class="input-group">
                    <span class="input-group-addon" id="basic-addon6">
                      <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
                    </span>
                <input id="ipEmail" type="email" class="form-control" placeholder="Email"
                       aria-describedby="basic-addon6" name="email">
            </div>
            <br/>

            <div class="input-group">
                    <span class="input-group-addon" id="basic-addon7">
                      <span class="glyphicon glyphicon-phone" aria-hidden="true"></span>
                    </span>
                <input id="ipPhone" type="number" class="form-control" placeholder="Phone"
                       aria-describedby="basic-addon7" name="phone">
            </div>
            <br/>


            <div class="input-group">
                <label class="radio-inline">Choose Payment Method：&nbsp;</label>
                <label class="radio-inline">
                    <input type="radio" name="payMethod" id="rdCash" value="0" checked> Cash
                </label>
                <label class="radio-inline">
                    <input type="radio" name="payMethod" id="rdCreditCard" value="1"> Credit Card
                </label>
            </div>
            <br/>

            <div id="dCardInfo" style="display: none">
                <div class="input-group">
                        <span class="input-group-addon" id="basic-addon8">
                          <span class="glyphicon glyphicon-credit-card" aria-hidden="true"></span>
                        </span>
                    <input id="ipCardNumber" type="number" class="form-control" placeholder="CreditCardNumber(16)"
                           aria-describedby="basic-addon8" name="cardNumber" maxlength="16" minlength="16">
                </div>
                <br/>

                <div>
                    <div style="width: 48%; display: block; float: left;">
                        <div class="input-group" >
                        <span class="input-group-addon" id="basic-addon9">
                          <span class="glyphicon glyphicon-calendar" aria-hidden="true"></span>
                        </span>
                            <input id="ipExpirationDate" type="number" class="form-control" placeholder="ExpirationDate(mmyy)"
                                   aria-describedby="basic-addon9" name="expirationDate" maxlength="4" minlength="4">
                        </div>
                    </div>

                    <div style="width: 48%; display: block; float: right;">
                        <div class="input-group" >
                            <span class="input-group-addon" id="basic-addon10">
                              <span class="glyphicon glyphicon-lock" aria-hidden="true"></span>
                            </span>
                            <input id="ipSecurityKey" type="number" class="form-control" placeholder="SecurtyKey(3)"
                                   aria-describedby="basic-addon10" name="securityKey" maxlength="3" minlength="3">
                        </div>
                    </div>
                </div>
                <br style="clear: both"/>
                <br/>
            </div>

            <button id="btSubmitCreate" type="submit" class="btn btn-primary" style="width: 100%; margin: auto; clear: both">
                Create
            </button>
            <h4 id="errorMessage" style="color: red; width: 100%; text-align: center"></h4>
        </div>

    </div>


</rapid:override>

<rapid:override name="details">

</rapid:override>
<%@ include file="../base.jsp"%>