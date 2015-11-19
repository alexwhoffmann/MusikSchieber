package ms5000.web.httputil;

public interface Response {
	String getResponse();
	void setResponse(String response);
	int getResponseCode();
	void setResponseCode(int responseCode);
}
