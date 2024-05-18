<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
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
        <c:forEach var="employee" items="${employees.content}">
            <tr>
                <th scope="row">${employee.id}</th>
                <td>${employee.name}</td>
                <td>${employee.email}</td>
                <td><a href="${path}/details/<c:out value='${employee.id}'/>">Details</a>&nbsp;
                    <a href="${path}/edit/<c:out value='${employee.id}'/>">Edit</a>&nbsp;
                <a href="${path}/delete/<c:out value='${employee.id}'/>">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <c:if test="${totalPages > 0}">
        <nav aria-label="Page navigation example">
            <ul class="pagination">
                <c:choose>
                    <c:when test="${employees.number == 0}">
                        <li class="page-item disabled">
                            <a class="page-link" href="#">Previous</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                            <a class="page-link" href="?page=${employees.number - 1}&size=${employees.size}">Previous</a>
                        </li>
                    </c:otherwise>
                </c:choose>

                <c:forEach var="i" begin="0" end="${totalPages - 1}">
                    <c:choose>
                        <c:when test="${i == currentPage}">
                            <li class="page-item active">
                                <a class="page-link" href="#">${i + 1}</a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="page-item">
                                <a class="page-link" href="?page=${i}&size=${employees.size}">${i + 1}</a>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <c:choose>
                    <c:when test="${employees.number + 1 == totalPages}">
                        <li class="page-item disabled">
                            <a class="page-link" href="#">Next</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="page-item">
                            <a class="page-link" href="?page=${employees.number + 1}&size=${employees.size}">Next</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
    </c:if>
</div>
<script src="${path}/resources/js/bootstrap.bundle.min.js"></script>
</body>

</html>
