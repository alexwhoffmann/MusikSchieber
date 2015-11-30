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
public class Results {

	@SerializedName("status")
	@Expose
	public String status;
	@SerializedName("results")
	@Expose
	public List<Result> results = new ArrayList<Result>();
	
	public String getStatus() {
		return status;
	}
	public List<Result> getResults() {
		return results;
	}

}