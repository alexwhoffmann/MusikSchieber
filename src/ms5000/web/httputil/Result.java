package ms5000.web.httputil;

import java.util.ArrayList;
import java.util.List;

public class Result {
	private String id;
	private List<Recording> recordings = new ArrayList<Recording>();
	private String score;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<Recording> getRecordings() {
		return recordings;
	}
	public void setRecordings(List<Recording> recordings) {
		this.recordings = recordings;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
}
