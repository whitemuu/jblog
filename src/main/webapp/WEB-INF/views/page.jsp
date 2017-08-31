<%--
  Created by IntelliJ IDEA.
  User: nichijou
  Date: 8/25/17
  Time: 10:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>{N}·${article.title}</title>
  <link rel="stylesheet" type="text/css" href="/css/page.css"/>
</head>
<body>
<h1 id="title">${article.title}</h1>
${article.content}
<jsp:include page="footer.jsp"/>
</body>
</html>
