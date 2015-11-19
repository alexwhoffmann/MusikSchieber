package ms5000.web.httputil;

public class Standard_Response implements Response{
	private String response;
	private int responseCode;
	
    public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
}
