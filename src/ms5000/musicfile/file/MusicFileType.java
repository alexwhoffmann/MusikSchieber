package ms5000.musicfile.file;

import java.util.ArrayList;

public enum MusicFileType {
	MP3("mp3"),
	MP4("m4a"),
	OGG("ogg"),
	FLAC("flac"),
	WMA("wma");
	
	private String fileExtension;
	
	private MusicFileType(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	
	public static String getFileExtension(MusicFileType type) {
		return type.fileExtension;
	}
	
	public static String[] getExtensionValues() {
		ArrayList<String> list = new ArrayList<String>();
		String[] returnList = new String[0];
		
		for(MusicFileType type : MusicFileType.values()) {
			list.add("*." + MusicFileType.getFileExtension(type));
		}
		
		return list.toArray(returnList);
	}
}
