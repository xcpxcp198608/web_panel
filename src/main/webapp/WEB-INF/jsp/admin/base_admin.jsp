<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<rapid:override name="navigation">
    <div class="sidebar-sticky">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link active" href="/panel/admin/">
                    <i class="fa fa-home fa-lg"></i>&nbsp;Home
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/admin/dealer">
                    <i class="fa fa-briefcase fa-lg"></i>&nbsp;Dealer
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/admin/sales">
                    <i class="fa fa-bullhorn fa-lg"></i>&nbsp;Reps
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/admin/devices">
                    <i class="fa fa-tasks fa-lg"></i>&nbsp;Devices
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/admin/users">
                    <i class="fa fa-users fa-lg"></i>&nbsp;Customers
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/admin/distribution">
                    <i class="fa fa-globe fa-lg"></i>&nbsp;Geographic
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/admin/commission">
                    <i class="fa fa-bar-chart fa-lg"></i>&nbsp;Commission
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/admin/transactions">
                    <i class="fa fa-building-o fa-lg"></i>&nbsp;Transactions
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/signout">
                    <i class="fa fa-sign-out fa-lg"></i>&nbsp;Sign out
                </a>
            </li>
        </ul>

    </div>
</rapid:override>

<%@ include file="../base.jsp"%>