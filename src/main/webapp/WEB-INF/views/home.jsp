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
  <link rel="stylesheet" href="/css/my.css">
  <link rel="alternate" type="application/rss+xml" title="NICHIJOU » FEED" href="/feed/rss.xml">
</head>
<body>
<c:forEach items="${articles}" var="article">
  <div class="item">
      <%--<div class="tags">${article.tags}</div>--%>
    <div class="tags">
      <c:forTokens items="${article.tags}" delims="," var="tag">
        <a href="xxx.html"><c:out value="${tag}"/></a>
      </c:forTokens>
    </div>
    <div class="articleTitle"><a href="/article/${article.name}.html">${article.title}</a></div>
  </div>
</c:forEach>
<jsp:include page="footer.jsp"/>
</body>
</html>
