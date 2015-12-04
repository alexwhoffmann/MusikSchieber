package ms5000.musicfile.file;

import java.util.ArrayList;

/**
 * Enumeration capturing the different music file types
 */
public enum MusicFileType {
	
	/**
	 * Mp3 type
	 */
	MP3("mp3"),
	
	/**
	 * M4A type
	 */
	M4A("m4a"),
	
	/**
	 * AAC type
	 */
	AAC("aac"),
	
	/**
	 * Ogg type
	 */
	OGG("ogg"),
	
	/**
	 * flac type
	 */
	FLAC("flac"),
	
	/**
	 * wma type
	 */
	WMA("wma");
	
	/**
	 * Set up the file extension enum type
	 */
	private String fileExtension;
	
	private MusicFileType(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	
	/**
	 * Returns the file extension as string 
	 * 
	 * @param type the type to which the file extension will be returned
	 * @return the file extension as string
	 */
	public static String getFileExtension(MusicFileType type) {
		return type.fileExtension;
	}
	
	/**
	 * Method to return a array containing all the file extensions
	 * 
	 * @return array containing all the file extensions
	 */
	public static String[] getExtensionValues() {
		ArrayList<String> list = new ArrayList<String>();
		String[] returnList = new String[0];
		
		for(MusicFileType type : MusicFileType.values()) {
			list.add("*." + MusicFileType.getFileExtension(type));
		}
		
		return list.toArray(returnList);
	}
}
