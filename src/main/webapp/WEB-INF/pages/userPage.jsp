<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Teomant
  Date: 05.08.2018
  Time: 23:31
  To change this template use File | Settings | File Templates.
--%>
<%--@elvariable id="user" type="org.teomant.entity.UserEntity"--%>
<%--@elvariable id="authority" type="org.teomant.entity.AuthoritiesEntity"--%>
<%--@elvariable id="file" type="org.teomant.entity.UserFileEntity"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${user.username}`s page</title>
</head>
<body>
    username: ${user.username} <br/>
    user`s id: ${user.id} <br/>
    user`s auth:
    <c:forEach items="${user.authorities}" var="authority" varStatus="status">
        ${authority.authority}<c:if test="${not status.last}">, </c:if>
    </c:forEach>
    <br/>
    user`s files:
    <c:forEach items="${fileIds}" var="fileId" varStatus="status">
        <img src="/user/image?id=${fileId}"/>
    </c:forEach>
    <br/>
    <form:form method="POST" action="/uploadFile" enctype="multipart/form-data">
        <table>
            <tr>
                <td>Select a file to upload</td>
                <td><input type="file" name="file" placeholder=""/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Submit" /></td>
            </tr>
        </table>
    </form:form>
</body>
</html>
