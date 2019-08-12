<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-dark bg-dark navbar-expand-lg fixed-top">
    <a href="/user/user_page" class="navbar-brand">Web Banking</a>
    <button class="navbar-toggler" data-toggle="collapse" data-target="#navbarCollapse">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarCollapse">
        <ul class="navbar-nav ml-auto">
            <c:if test="${myImage != null}">
                <img class="rounded-circle border border-primary" style="margin-right: 10px; margin-top: 3px"
                     src="data:image/jpg;base64,${myImage}"
                     width="36"
                     height="36"/>
            </c:if>
            <a href="${pageContext.request.contextPath}/user/addOrganisation" class="nav-link">Add Organisation</a>
            <a href="${pageContext.request.contextPath}/user/showTransactionsHistory" class="nav-link">
                Show Transactions history
            </a>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false">
                    Bank operations
                </a>
                <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/user/payment">
                        Do Payment to organisation
                    </a>
                    <a class="dropdown-item" href="${pageContext.request.contextPath}/user/transaction">
                        Do Transaction to bank account
                    </a>
                </div>
            </li>
            <a href="${pageContext.request.contextPath}/user/addBankAccount" class="nav-link">Add Bank Account</a>
            <a href="${pageContext.request.contextPath}/process_logout" class="nav-link">Logout</a>
            <c:if test="${userRoles.contains('ROLE_ADMIN')}">
                <a href="${pageContext.request.contextPath}/admin/admin_page" class="nav-link">Admin page</a>
            </c:if>

        </ul>
    </div>
</nav>