<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<rapid:override name="navigation">
    <div class="sidebar-sticky">
        <ul class="nav flex-column">
            <li class="nav-item">
                <a class="nav-link active" href="/panel/device/home">
                    <i class="fa fa-home fa-lg"></i>&nbsp;Home
                </a>
            </li>
            <c:if test="${permission > 100}">
                <li class="nav-item">
                    <a class="nav-link active" href="/panel/device/all">
                        <i class="fa fa-tasks fa-lg"></i>&nbsp;AllDevices
                    </a>
                </li>
            </c:if>
            <li class="nav-item">
                <a class="nav-link active" href="/panel/device/mcm">
                    <i class="fa fa-database fa-lg"></i>&nbsp;MCMDevices
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" href="/panel/device/pcp">
                    <i class="fa fa-bars fa-lg"></i>&nbsp;PCPDevices
                </a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/panel/signout2">
                    <i class="fa fa-sign-out fa-lg"></i>&nbsp;Sign out
                </a>
            </li>
        </ul>

    </div>
</rapid:override>

<%@ include file="base.jsp"%>