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
<title>{nichijou}·HOME</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/home.css">
<link rel="alternate" type="application/rss+xml" title="NICHIJOU » FEED" href="${pageContext.request.contextPath}/feed/rss.xml">
<script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
            (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
        m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');
    ga('create', 'UA-60584744-3', 'auto');
    ga('send', 'pageview');
</script>
<body>
<div id="filterbar"></div>
<div id="items">
  <c:forEach items="${articles}" var="article">
    <div class="item">
        <%--<div class="tags">${article.tags}</div>--%>
      <div class="tags">
        <c:forTokens items="${article.tags}" delims="," var="tag">
          <span><c:out value="${tag}"/></span>
        </c:forTokens>
      </div>
      <div class="articleTitle"><a href="/article/${article.name}.html">${article.title}</a></div>
    </div>
  </c:forEach>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>
