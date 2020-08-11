<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Создать событие</title>

        <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/4.1.1/css/bootstrap-multiselect.css" type="text/css"/>
        <link rel="stylesheet" href="${pageContext.request.contextPath} webjars/bootstrap/4.1.1/css/bootstrap.min.css">

        <script type="text/javascript" th:src="${pageContext.request.contextPath} webjars/bootstrap/4.1.1/js/bootstrap-multiselect.js"></script>
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
        <div style="text-align: center; padding-top: 5%;">
            <c:choose>
            <c:when test="${isCreate}">
                <form:form method="POST" action="${pageContext.request.contextPath}/createEvent" modelAttribute="eventForm">
                    <h2>Создать событие</h2>

                    <div style="text-align: center; padding-left: 30%; padding-right: 30%;">
                        <div style="padding: 5px;">
                            <form:input class="form-control" type="text" path="event_name" placeholder="Название события"
                                   autofocus="true"/>
                        </div>
                        <div style="padding: 5px;">
                            <form:input path="start_date" class="form-control" type="datetime-local" placeholder="Дата начала события"/>
                        </div>
                        <div style="padding: 5px;">
                            <form:input path="finish_date" class="form-control" type="datetime-local" placeholder="Дата окончания события"/>
                        </div>
                        <div style="padding: 5px;">
                            <form:input path="description" class="form-control" type="text" placeholder="Описание события"/>
                        </div>
                    </div>

                    <label>Участники: </label>
                    <form:select cssStyle="min-width: 20%;" path="users" id="example-multiple-selected" multiple="multiple">
                        <form:option value="NONE" label="..." />
                        <form:options items="${userList}" />
                    </form:select>

                    <br>
                    <button class="btn btn-warning" type="submit">Сохранить</button>
                </form:form>
            </c:when>

            <c:otherwise>
                <form:form method="POST" action="${pageContext.request.contextPath}/editEvent?event_id=${event_id}" modelAttribute="eventForm">
                    <h2>Редактировать событие</h2>

                    <div style="text-align: center; padding-left: 30%; padding-right: 30%;">
                        <div style="padding: 5px;">
                            <form:input class="form-control" type="text" path="event_name" placeholder="Название события"
                                        autofocus="true"/>
                        </div>
                        <div style="padding: 5px;">
                            <form:input path="start_date" class="form-control" type="datetime-local" placeholder="Дата начала события"/>
                        </div>
                        <div style="padding: 5px;">
                            <form:input path="finish_date" class="form-control" type="datetime-local" placeholder="Дата окончания события"/>
                        </div>
                        <div style="padding: 5px;">
                            <form:input path="description" class="form-control" type="text" placeholder="Описание события"/>
                        </div>
                    </div>
                    <br>
                    <button class="btn btn-warning" type="submit">Сохранить</button>
                </form:form>
            </c:otherwise>

            </c:choose>

            <p>${error}</p>
        </div>
    </body>
</html>