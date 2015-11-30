
package ms5000.web.acousticid.result;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Generated class to capture the result of the acoustid web service
 */
@Generated("org.jsonschema2pojo")
public class Result {

	@SerializedName("score")
	@Expose
	public Double score;
	@SerializedName("id")
	@Expose
	public String id;
	@SerializedName("recordings")
	@Expose
	public List<Recording> recordings = new ArrayList<Recording>();
	
	public Double getScore() {
		return score;
	}
	public String getId() {
		return id;
	}
	public List<Recording> getRecordings() {
		return recordings;
	}

}