<%@ page import="models.User" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <tr>
        <th>First Name</th>
        <th>Last Name</th>
        <th>Age</th>
    </tr>

    <%
        List<User> users = (List<User>) request.getAttribute("usersForJsp");
        for (int i = 0; i < users.size(); i++) {
            System.out.println(users.size());
    %>
    <tr>
        <td><%=users.get(i).getName()%></>
        <td><%=users.get(i).getSurname()%></>
        <td><%=users.get(i).getAge()%></>
    </tr>
    <%}%>
</table>
</body>
</html>
