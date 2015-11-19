package ms5000.web.httputil;

import java.util.ArrayList;
import java.util.List;

public class Results {
	private String status;
	private List<Result> results = new ArrayList<Result>();

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Result> getResults() {
		return results;
	}

	public void setResults(List<Result> results) {
		this.results = results;
	}
}
