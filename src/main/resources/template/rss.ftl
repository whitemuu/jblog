<?xml version="1.0" encoding="UTF-8" ?>
<rss version="2.0">
  <channel>
	<title>{nichijou}</title>
	<link>https://www.robotgirl.me</link>
	<description>Quite a life</description>
    <#list articles as article>
	<item>
	  <title>${article.title}</title>
	  <link>http://www.robotgirl.me/article/${article.name}.html</link>
	  <description>${article.description}</description>
	</item>
    </#list>
  </channel>
</rss>
