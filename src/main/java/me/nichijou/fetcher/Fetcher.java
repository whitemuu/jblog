package me.nichijou.fetcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.nichijou.pojo.SourceFileInfo;
import me.nichijou.service.FetchService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by nichijou on 8/25/17.
 */
public class Fetcher {

	@Autowired
	private FetchService fetchService;

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Test
	public void doGet() throws Exception {
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet("https://api.github.com/repos/whitemuu/blog");
		CloseableHttpResponse response = null;
		try {
			response = client.execute(httpGet);
			if (response.getStatusLine().getStatusCode() == 200) {
				String body = EntityUtils.toString(response.getEntity(), "utf-8");
				JSONObject jsonObject = new JSONObject(body);
				System.out.println(jsonObject.getString("pushed_at"));
				Date pushDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX").parse(jsonObject.getString("pushed_at"));
				Date now = new Date();
				System.out.println(pushDate);
				System.out.println(now);

				List<SourceFileInfo> sourceFileInfos = null;
				Map<String, String> articles = null;
//				for (SourceFileInfo sourceFileInfo : sourceFileInfos) {
//					String name = sourceFileInfo.getName();
//					String oldSha;
//					if ((oldSha = articles.get(name)) != null) {
//						articles.remove(name);
//						if (oldSha == sourceFileInfo.getSha()) {
//							sourceFileInfos.remove(sourceFileInfo);
//						}
//					}
//				}
//				addOrUpdata(sourceFileInfos);
//				removeArticles(articles);

/*				this.MAPPER.setPropertyNamingStrategy(
						PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
				SourceFileInfo[] sourceFiles = MAPPER.readValue(body, SourceFileInfo[].class);
				for (SourceFileInfo sourceFile : sourceFiles) {
					System.out.println(sourceFile);
				}*/

//				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(content.getBytes())));
//				SourceFileInfo sourceFileInfo = new SourceFileInfo();
			}
		} finally {
			if (response != null)
				response.close();
			client.close();
		}
	}
}
