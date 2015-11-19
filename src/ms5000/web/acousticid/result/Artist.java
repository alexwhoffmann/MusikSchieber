package ms5000.web.acousticid.result;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Artist {

	@SerializedName("joinphrase")
	@Expose
	public String joinphrase;
	
	@SerializedName("id")
	@Expose
	public String id;
	@SerializedName("name")
	@Expose
	public String name;
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}

}