package ms5000.web.httputil;

public class ResponseFactory {
	public static Response getInstance(WebService service) {
		return new Standard_Response();
	}
}
