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
<body>
<h1>Список событий</h1>

<div>
    <table class="table" border="1">
        <thead class="thead-light">
            <tr>
                <th>
                    <form action="${pageContext.request.contextPath}/eventsList?direction=${direction}&number=${number}" method="GET">
                        <input type="hidden" name="number" value=${number-1} />
                        <input type="hidden" name="direction" value="back" />
                        <button class="btn btn-outline-info"> Back </button>
                    </form>
                </th>
                <th>Понедельник<br>${days.get(0).day_date}</th>
                <th>Вторник<br>${days.get(1).day_date}</th>
                <th>Среда<br>${days.get(2).day_date}</th>
                <th>Четверг<br>${days.get(3).day_date}</th>
                <th>Пятница<br>${days.get(4).day_date}</th>
                <th>Суббота<br>${days.get(5).day_date}</th>
                <th>
                    <form action="${pageContext.request.contextPath}/eventsList?direction=${direction}&number=${number}" method="GET">
                        <input type="hidden" name="number" value=${number+1} />
                        <input type="hidden" name="direction" value="next" />
                        <button class="btn btn-outline-info"> Next </button>
                    </form>
                </th>
            </tr>
        </thead>
        <tbody>
            <c:forEach  begin="1" end="24" var ="hour">
                <tr >
                    <th scope="row">${hour}</th>
                    <c:forEach  begin="0" end="5" var ="day">
                        <c:choose>
                            <c:when test="${days.get(day).hours.stream().anyMatch(a->a.number==(hour))}">
                                <td class="table-danger" style="text-align: center">
                                    <a href="${pageContext.request.contextPath}/eventDetails/${days.get(day).hours.stream().filter(a->a.number==(hour)).findFirst().get().event_id}">
                                        "${days.get(day).hours.stream().filter(a->a.number==(hour)).findFirst().get().event_name}"
                                    </a>
                                    <br>
                                    ${days.get(day).hours.stream().filter(a->a.number==(hour)).findFirst().get().start_date}
                                    -
                                    ${days.get(day).hours.stream().filter(a->a.number==(hour)).findFirst().get().finish_date}
                                    <c:forEach  items="${days.get(day).hours.stream().filter(a->a.number==(hour)).findFirst().get().users}" var ="user">
                                        <br> ${user}
                                    </c:forEach>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <td></td>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <td></td>
                </tr>
            </c:forEach>
        </tbody>

    </table>
</div>
</body>
</html>