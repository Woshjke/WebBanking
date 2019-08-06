<link href="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!------ Include the above in your HEAD tag ---------->

<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<!DOCTYPE html>

<html lang="en">
<meta charset="utf-8">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>This is a Bootstrap example</title>

    <style>
        <%@include file="/WEB-INF/resources/css/registrate.css"%>
    </style>
</head>
<body>
<section>
    <div class="main main-raised" style="background: #d1e7e5;margin:0px 90px 0 90px">
        <div class="container">
            <div id="kv-avatar-errors-2" class="center-block" style="width:800px;display:none"></div>
            <form class="form form-vertical" action="/site/avatar-upload/2" method="post" enctype="multipart/form-data">
                <div class="row">
                    <br>
                    <div class="col-sm-4 text-center" >
                        <div class="kv-avatar" >
                            <div class="avatar-wrapper" style="margin: 40px auto 0 450px">

                                <img class="profile-pic" src=""/>
                                <div class="upload-button">
                                    <i class="fa fa-arrow-circle-up" aria-hidden="true"></i>
                                </div>
                                <input class="file-upload" type="file" accept="image/*"required/>
                            </div>


                        </div>
                        <div class="col-sm-8" style="margin: 0px 0px 0px 435px">
                        <span>Choose avatar<span class="kv-reqd">*</span></span>
                        </div>
                    </div>

                            <div class="col-sm-8" style="margin: 30px 20px 20px 160px">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <center><label for="email">Login<span class="kv-reqd">*</span></label></center>
                                            <input id="email"  type="text" class="form-control" name="Login" required>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                            <center><label for="pwd">Password<span class="kv-reqd">*</span></label></center>
                                            <input id="pwd" type="password" class="form-control" name="pwd" required>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                           <center> <label for="fname">First Name<span class="kv-reqd">*</span></label></center>
                                            <input id="fname" type="text" class="form-control" name="fname" required>
                                        </div>
                                    </div>
                                    <div class="col-sm-6">
                                        <div class="form-group">
                                           <center> <label for="lname">Last Name<span class="kv-reqd">*</span></label></center>
                                            <input id="lname" type="text" class="form-control" name="lname" required>
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="text-right">
                                        <center>
                                            <button type="submit" class="btn btn-primary">Submit</button>
                                        </center>
                                    </div>
                                </div>


                            </div>

                        </div>
            </form>

        </div>


    </div>
<br>
</section>

<script>
    var btnCust = '<button type="button" class="btn btn-secondary" title="Add picture tags" ' +
        'onclick="alert(\'Call your custom code here.\')">' +
        '<i class="glyphicon glyphicon-tag"></i>' +
        '</button>';
    $("#avatar-1").fileinput({
        overwriteInitial: true,
        maxFileSize: 1500,
        showClose: false,
        showCaption: false,
        browseLabel: '',
        removeLabel: '',
        browseIcon: '<i class="glyphicon glyphicon-folder-open"></i>',
        removeIcon: '<i class="glyphicon glyphicon-remove"></i>',
        removeTitle: 'Cancel or reset changes',
        elErrorContainer: '#kv-avatar-errors-1',
        msgErrorClass: 'alert alert-block alert-danger',
        defaultPreviewContent: '<img src="/samples/default-avatar-male.png" alt="Your Avatar">',
        layoutTemplates: {main2: '{preview} ' + btnCust + ' {remove} {browse}'},
        allowedFileExtensions: ["jpg", "png", "gif"]
    });
</script>
<!-- the fileinput plugin initialization -->
<script>
    $(document).ready(function () {

        var readURL = function (input) {
            if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('.profile-pic').attr('src', e.target.result);
                }

                reader.readAsDataURL(input.files[0]);
            }
        }

        $(".file-upload").on('change', function () {
            readURL(this);
        });

        $(".upload-button").on('click', function () {
            $(".file-upload").click();
        });
    });

</script>

</body>
</html>