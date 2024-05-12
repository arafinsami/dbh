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
            <li class="nav-item"><a href="<c:url value="/save" />" class="nav-link">Add new record</a></li>
        </ul>
    </header>
</div>

<div class="container">
    <table class="table">
        <thead>
        <tr>
            <th scope="col">ID</th>
            <th scope="col">Name</th>
            <th scope="col">Email</th>
            <th scope="col">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="employee" items="${employees}">
            <tr>
                <th scope="row">${employee.employeeId}</th>
                <td>${employee.name}</td>
                <td>${employee.email}</td>
                <td><a href="${path}/details?id=<c:out value='${employee.employeeId}'/>">Details</a>&nbsp;
                    <a href="${path}/edit?id=<c:out value='${employee.employeeId}'/>">Edit</a>&nbsp;
                <a href="${path}/delete?id=<c:out value='${employee.employeeId}'/>">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <nav aria-label="Page navigation example">
        <ul class="pagination">
            <c:if test="${currentPage > 1}">
                <li class="page-item">
                    <a class="page-link" href="${path}/?page=${currentPage - 1}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                        <span class="sr-only">Previous</span>
                    </a>
                </li>
            </c:if>
            <c:forEach var="i" begin="1" end="${totalPages}">
                <li class="page-item ${i eq currentPage ? 'active' : ''}">
                    <a class="page-link" href="${path}/?page=${i}">${i}</a>
                </li>
            </c:forEach>
            <c:if test="${currentPage < totalPages}">
                <li class="page-item">
                    <a class="page-link" href="${path}/?page=${currentPage + 1}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                        <span class="sr-only">Next</span>
                    </a>
                </li>
            </c:if>
        </ul>
    </nav>

</div>
<script src="${path}/resources/js/bootstrap.bundle.min.js"></script>
</body>

</html>
