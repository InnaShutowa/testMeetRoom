<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Список событий</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/webjars/bootstrap/4.1.1/css/bootstrap.min.css">

    <script th:src="@{/jquery/jquery.min.js}"></script>
    <script th:src="@{/popper/popper.min.js}"></script>
    <script th:src="@{/bootstrap/js/bootstrap.min.js}"></script>
</head>

<header class="mt-2 mb-2" role="banner">
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <a class="navbar-brand" href="#">MeetRoom</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link" href="/eventsList">Список событий <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/createEvent">Добавить событие</a>
                </li>
                <c:choose>
                    <c:when test="${isAdmin}">
                        <li class="nav-item">
                            <a class="nav-link" href="/createUser">Добавить пользователя</a>
                        </li>
                    </c:when>
                </c:choose>
            </ul>
            <ul class="form-inline my-2 my-lg-0">
                <li class="nav-item">
                    <a class="nav-link" href="/personalArea">${login}</a>
                </li>
                <li class="nav-item">
                    <form action="${pageContext.request.contextPath}/logOut" method="GET">
                        <button class="btn btn-outline-info"> Выйти </button>
                    </form>
                </li>
            </ul>
        </div>
    </nav>
</header>
<body style="text-align: center">
    <h1> Подробная информация о событии</h1>
    <h2>"${eventName}"</h2>
    <p style="font-style: italic">Начало события: ${date_start} - ${date_finish}</p>
    <p>${description}</p>

    <h2>Участники события:</h2>
    <c:forEach  items="${users}" var ="user">
        <br> ${user}
    </c:forEach>

    <p>
        <form action="${pageContext.request.contextPath}/editEvent?event_id=${event_id}" method="GET">
            <input type="hidden" name="event_id" value=${event_id} />
            <button class="btn btn-warning">Редактировать</button>
        </form>
    </p>
<div>

</div>
</body>

</html>