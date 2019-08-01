<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>

<head>
    <meta charset="utf-8">
    <script src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto+Slab:300,400|Roboto:300,400,700">
    <title>Home page</title>
</head>

<style>
    <%@include file="/WEB-INF/resources/css/home_page.css"%>
</style>


<body>
<div>
    <nav class="navbar navbar-light navbar-expand-md navigation-clean-button" style="background-color:rgb(255,255,255);">
        <div class="container">
            <a class="navbar-brand" href="#" style="color:rgb(0,0,0);">Web Banking</a>
            <button class="navbar-toggler" data-toggle="collapse" data-target="#navcol-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navcol-1">
                <ul class="nav navbar-nav mr-auto">
                    <c:if test="${!userRoles.contains('ROLE_ANONYMOUS')}">
                        <li class="dropdown">
                            <a class="dropdown-toggle nav-link dropdown-toggle" data-toggle="dropdown" aria-expanded="false" href="#" style="color:rgb(0,0,0);">
                                Bank Operations
                            </a>
                            <div class="dropdown-menu" role="menu">
                                <a class="dropdown-item"  href="#">Do Payment to organisation</a>
                                <a class="dropdown-item"  href="#">Do Transaction to bank account</a>
                            </div>
                        </li>
                    </c:if>
                    <c:if test="${userRoles.contains('ROLE_ADMIN')}">
                        <li class="nav-item" role="presentation">
                            <a class="nav-link active" href="#" style="color:rgb(0,0,0);">Admin page</a>
                        </li>
                    </c:if>
                    <img class="rounded-circle border border-primary" src="data:image/jpg;base64,${myImage}"
                         width="60" height="60" >
                </ul>
                <span class="navbar-text actions">
                    <c:if test="${!userRoles.contains('ROLE_ANONYMOUS')}">
                        <a class="btn btn-light action-button" role="button" href="#">Logout</a>
                    </c:if>
                     <c:if test="${userRoles.contains('ROLE_ANONYMOUS')}">
                         <a class="btn btn-light action-button" role="button" href="#">Login</a>
                     </c:if>
                </span>
            </div>
        </div>
    </nav>
</div>
<div>
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <div class="card" style="border-color: white;">
                    <div class="card-body">
                        <h4 class="card-title">Title</h4>
                        <h6 class="text-muted card-subtitle mb-2">Subtitle</h6>
                        <p class="card-text">Text</p><a class="card-link" href="#">Link</a>
                        <a class="card-link" href="#">Link</a>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="card" style="width:580px;padding-left:385px;border: none">
                    <div class="card-body">
                        <h4 class="card-title">Currency Rate</h4>
                        <h6 class="text-muted card-subtitle mb-2">Currency's:</h6>
                        <select name="currencySelect" class="form-control">
                                <option value="USD">USD</option>
                                <option value="EUR">EUR</option>
                        </select>
                        <button class="btn btn-primary" type="button" style="margin-top:16px;">Show rate</button>
                        <div style="padding-bottom: 20px"></div>
                        <p class="card-text">Rate will be here</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div class="footer-basic fixed-bottom">
    <footer>
        <ul class="list-inline">
            <li class="list-inline-item"><a href="#">Home</a></li>
            <li class="list-inline-item"><a href="#">Services</a></li>
            <li class="list-inline-item"><a href="#">About</a></li>
            <li class="list-inline-item"><a href="#">Terms</a></li>
            <li class="list-inline-item"><a href="#">Privacy Policy</a></li>
        </ul>
        <p class="copyright">SaM Solutions &nbsp;© 2019</p>
    </footer>
</div>

<%--Bootstrap JS classes--%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>

</html>
