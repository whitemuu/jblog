<%--
  Created by IntelliJ IDEA.
  User: nichijou
  Date: 8/25/17
  Time: 10:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>{nichijou}·HOME</title>
</head>
<body>
<c:forEach items="${articles}" var="article">
  <a href="/article/${article.name}.html">${article.title}</a>
  ${article.tags}<br/>
</c:forEach>
<jsp:include page="footer.jsp"/>
</body>
</html>
