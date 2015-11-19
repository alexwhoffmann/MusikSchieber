package ms5000.web.httputil;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HTTPUtil {
	private final static String PROPERTIES = "properties/http.properties";

	public static Response get(String url, WebService service) throws IOException {
		Properties properties = new Properties();
		BufferedInputStream stream = new BufferedInputStream(new FileInputStream(PROPERTIES));
		properties.load(stream);
		stream.close();

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(url);

		// In case AcoustID was called
		if (service == WebService.ACOUSTID) {
			httpGet.setHeader("User-Agent", properties.getProperty("useragent"));
		}

		CloseableHttpResponse httpResponse = httpclient.execute(httpGet);

		try {
			Response response = ResponseFactory.getInstance(service);
			HttpEntity httpEntity = httpResponse.getEntity();
			Standard_Response standard_response = (Standard_Response) response;
			standard_response.setResponse(EntityUtils.toString(httpEntity));
			standard_response.setResponseCode(httpResponse.getStatusLine().getStatusCode());
			
			return standard_response;
		} catch (ClientProtocolException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			httpResponse.close();
		}
	}

}
