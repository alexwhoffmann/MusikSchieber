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
public class Recording {

	@SerializedName("duration")
	@Expose
	public Integer duration;
	@SerializedName("title")
	@Expose
	public String title;
	@SerializedName("id")
	@Expose
	public String id;
	@SerializedName("artists")
	@Expose
	public List<Artist> artists = new ArrayList<Artist>();
	
	public Integer getDuration() {
		return duration;
	}

	public String getTitle() {
		return title;
	}
	public String getId() {
		return id;
	}
	public List<Artist> getArtists() {
		return artists;
	}

}