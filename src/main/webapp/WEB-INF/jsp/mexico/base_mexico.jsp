<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<rapid:override name="navigation">
    <div class="sidebar-sticky">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link active" href="/panel/mexico/home">
                    <i class="fa fa-home fa-lg"></i>&nbsp;Home
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/mexico/customers">
                    <i class="fa fa-users fa-lg"></i>&nbsp;Customers
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/mexico/customers/create">
                    <i class="fa fa-plus-square fa-lg"></i>&nbsp;Create
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/mexico/commission">
                    <i class="fa fa-bar-chart fa-lg"></i>&nbsp;Commission
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/mexico/transactions">
                    <i class="fa fa-building-o fa-lg"></i>&nbsp;Transactions
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/signout3">
                    <i class="fa fa-sign-out fa-lg"></i>&nbsp;Sign out
                </a>
            </li>
        </ul>

    </div>
</rapid:override>

<%@ include file="base.jsp"%>