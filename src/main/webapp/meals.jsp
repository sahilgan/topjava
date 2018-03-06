<%--
  Created by IntelliJ IDEA.
  User: lancer
  Date: 06.03.18
  Time: 19:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<%@ page import="ru.javawebinar.topjava.model.MealWithExceed" %>

<%--
private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;
--%>
<html>
<head>
    <title>Meals</title>
</head>
<body>
    <h2>Meals</h2>
    <table style="width:60%">
        <tr>
            <th>Описание</th>
            <th>Время приема</th>
            <th>Число калорий</th>
        </tr>
    <c:forEach items="${meal_list}" var="me">
        <tr>
            <c:choose>
                <c:when test="${me.getExceed()}">
                    <c:set var="red" scope="page" value="red"/>
                </c:when>
                <c:otherwise>
                    <c:set var="red" scope="page" value="white"/>
                </c:otherwise>
            </c:choose>

            <td bgcolor="${red}">${me.getDescription()}</td>
            <td bgcolor="${red}">
                <javatime:format value="${me.getDateTime()}" pattern="yyyy-MM-dd HH:MM" var="reprDate"/>
                ${reprDate}
            </td>
            <td bgcolor="${red}">${me.getCalories()}</td>
        </tr>
    </c:forEach>
    </table>
</body>
</html>
