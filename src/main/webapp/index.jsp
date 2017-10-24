<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%-- 
    Document   : testDataSource
    Created on : 18-okt-2017, 11:14:27
    Author     : hwkei
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Welkom bij Applikaasie 3.0 beta</h1>
    <sql:query var="result_customer" dataSource="jdbc/applikaasie3">
        SELECT * FROM customer
    </sql:query>
    <!-- Let op quotes rond naam order!!! -->
   
        <h3>Customer tabel</h3>
        <table border="1">
            <!-- column headers -->
            <tr>
                <c:forEach var="columnName" items="${result_customer.columnNames}">
                    <th><c:out value="${columnName}"/></th>
                </c:forEach>
            </tr>
            <!-- column data -->
            <c:forEach var="row" items="${result_customer.rowsByIndex}">
                <tr>
                <c:forEach var="column" items="${row}">
                    <td><c:out value="${column}"/></td>
                </c:forEach>
                </tr>
            </c:forEach>
        </table>
 
    </body>
</html>