<%--
  Created by IntelliJ IDEA.
  User: nichijou
  Date: 8/25/17
  Time: 10:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>${article.title}·{nichijou}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="/css/page.css"/>
<link rel="alternate" type="application/rss+xml" title="NICHIJOU » FEED" href="/feed/rss.xml">
<body>
<div id="article">
  <h1 id="title">${article.title}</h1>
  ${article.content}
  <div style="text-align:center;margin-top:50px;color:#999">~ END ~</div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
