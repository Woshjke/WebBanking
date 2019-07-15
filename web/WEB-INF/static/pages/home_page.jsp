<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta charset="utf-8">
    <script src="https://code.jquery.com/jquery-3.4.1.js"
            integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
            crossorigin="anonymous"></script>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/WEB-INF/resources/css/homepage.css">
    <title>Homepage</title>
</head>

<body>

<!-- todo починить ссылку на homepage-->

<div>
    <nav class="navbar navbar-light navbar-expand-md navigation-clean-button">
        <div class="container"><a class="navbar-brand" href="#">Мой банк</a>
            <button class="navbar-toggler" data-toggle="collapse" data-target="#navcol-1">
                <span class="sr-only">Toggle navigation</span>
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse"
                 id="navcol-1">
                <ul class="nav navbar-nav mr-auto">
                    <li class="nav-item" role="presentation"><a class="nav-link active" href="#">Счета</a></li>
                    <li class="nav-item" role="presentation"><a class="nav-link" href="#">Second Item</a></li>
                    <li class="dropdown"><a class="dropdown-toggle nav-link dropdown-toggle" data-toggle="dropdown"
                                            aria-expanded="false" href="#">Dropdown </a>
                        <div class="dropdown-menu" role="menu"><a class="dropdown-item" role="presentation" href="#">First
                            Item</a><a class="dropdown-item" role="presentation" href="#">Second Item</a><a
                                class="dropdown-item" role="presentation" href="#">Third Item</a></div>
                    </li>
                </ul>
                <span class="navbar-text actions"> <a href="#" class="login">Log In</a></span></div>
        </div>
    </nav>
</div>
<div>
    <div class="container">
        <div class="row">
            <div class="col-md-6">
                <div style="margin-top:10px;">
                    <button class="btn btn-primary" type="button">Button</button>
                </div>
                <div style="margin-top:10px;">
                    <button class="btn btn-primary" type="button">Button</button>
                </div>
            </div>
            <div class="col-md-6"></div>
        </div>
    </div>
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
