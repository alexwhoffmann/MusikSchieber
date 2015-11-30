package ms5000.web.acusticid;

/**
 * This class holds the chromaprint and the duration of a music file
 */
public class ChromaPrint {
	/**
	 * The chromaprint
	 */
	final String chromaprint;
	
	/**
	 * The duration
	 */
	final String duration;
	
	/**
	 * Instantiates a new chromaprint object
	 * 
	 * @param chromaprint the chroma print
	 * @param duration the duration
	 */
	public ChromaPrint(String chromaprint, String duration) {
		this.duration = duration;
		this.chromaprint = chromaprint;
	}
	
	/**
	 * Returns the chromaprint
	 * 
	 * @return the chromaprint as string
	 */
	public String getChromaprint() {
		return chromaprint;
	}
	
	/**
	 * Returns the duration of a music file
	 * 
	 * @return the duration as string
	 */
	public String getDuration() {
		return duration;
	}
}
