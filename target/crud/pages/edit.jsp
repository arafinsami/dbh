<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" type="text/css" href="${path}/resources/css/bootstrap.min.css">
</head>
<body>

<div class="container">
    <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
        <a href="/" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
            <svg class="bi me-2" width="40" height="32">
                <use xlink:href="#bootstrap"/>
            </svg>
            <span class="fs-4"></span>
        </a>
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="<c:url value="/" /> " class="nav-link active" aria-current="page">Home</a>
            </li>
            <li class="nav-item"><a href="#" class="nav-link">Add new record</a></li>
        </ul>
    </header>
</div>

<div class="container">
    <div class="row">
        <form action="/edit" method="post">
            <input type="hidden" class="form-control" name="id" value="${employee.employeeId}">
            <div class="mb-3">
                <label for="name" class="form-label">Name</label>
                <input type="text" class="form-control" id="name" name="name" value="${employee.name}" placeholder="Enter Your Name">
            </div>

            <div class="mb-3">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" value="${employee.email}" placeholder="Enter Your Email">
            </div>

            <div class="mb-3">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password"  value="${employee.password}" placeholder="*************************">
            </div>

            <button type="submit" class="btn btn-primary">Submit</button>
        </form>
    </div>
</div>
<script src="${path}/resources/js/bootstrap.bundle.min.js"></script>
</body>

</html>
