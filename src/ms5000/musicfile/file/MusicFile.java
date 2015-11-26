package ms5000.musicfile.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;

import ms5000.musicfile.tag.MusicTag;
import ms5000.musicfile.tag.TagUtils;
import ms5000.musicfile.tag.MusicTag.TagState;
import ms5000.web.acousticid.result.Result;
import ms5000.web.acusticid.AcoustID;
import ms5000.web.acusticid.ChromaPrint;

public class MusicFile {
	
	
	private File file;
	private MusicTag tag;
	private MusicFileType fileType;
	private String originalFilePath;
	private String newFileName;
	private String newFilePath;
	private boolean newFileNameIsSet = false;
	private boolean newFilePathIsSet = false;
	private boolean possibleDuplicate = false;
	private final String PROPERTIES = "properties/musicLibrary.properties";
	private ChromaPrint chromaPrint;
	private String musicBrainz_recordingID;
	private String musicBrainz_artistID;
	private ArrayList<String> musicBrainz_feature_artistIDs = new ArrayList<String>();
		
	public MusicFile(String originalFilePath) throws CannotReadException,
			IOException, TagException, ReadOnlyFileException,
			InvalidAudioFrameException {
		this.originalFilePath = originalFilePath;
		this.file = new File(originalFilePath);
		this.fileType = determineFileType(originalFilePath);
		this.tag = TagUtils.generateTagFromFile(this.file);
		this.tag.setMusicFile(this);
		this.chromaPrint = AcoustID.chromaprint(this.file);
		
		// Querying the acoustid webservice
		Result result = AcoustID.lookup(this.chromaPrint);
		
		if(result != null) {
			musicBrainz_recordingID = result.getRecordings().get(0).getId();
			
			if(result.getRecordings().get(0).getArtists().size() > 1) {
				musicBrainz_artistID = result.getRecordings().get(0).getArtists().get(0).getId();
				
				for (int i = 1; i < result.getRecordings().get(0).getArtists().size(); i++) {
					musicBrainz_feature_artistIDs.add(result.getRecordings().get(0).getArtists().get(i).getId());
				}
			}
		}
		
		// Determining the state of the tag
		TagUtils.determineTagState(this.tag);
	}

	private MusicFileType determineFileType(String originalFilePath) {
		if (originalFilePath.contains("mp3")) {
			return MusicFileType.MP3;
		} else if (originalFilePath.contains("ogg")) {
			return MusicFileType.OGG;
		} else if (originalFilePath.contains("wma")) {
			return MusicFileType.WMA;
		} else if (originalFilePath.contains("flac")) {
			return MusicFileType.FLAC;
		} else if (originalFilePath.contains("m4a")) {
			return MusicFileType.MP4;
		}

		return null;
	}

	public void generateNewFileName() {

		if (!tag.getArtist().equals("") && tag.getArtist() != null
				&& !tag.getTitlename().equals("") && tag.getTitlename() != null) {
			this.newFileName = MusicFileUtils.generateNewFileName(this);
			this.newFileNameIsSet = true;
		} else {
			this.newFileNameIsSet = false;
		}
	}

	public void generateNewFilePath() throws IOException {
		MusicTag tag = this.getTag();

		if (!tag.getArtist().equals("") && !tag.getAlbum().equals("")) {
			if(getOrderingMode().equals("3") && !tag.getGenre().equals("") || getOrderingMode().equals("2") || getOrderingMode().equals("1")) {
				newFilePathIsSet = true;
				newFilePath = MusicFileUtils.makeFileDir(this);
			} else {
				newFilePathIsSet = false;
				newFilePath = "";
			}
		} else {
			newFilePathIsSet = false;
			newFilePath = "";
		}
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public MusicTag getTag() {
		return tag;
	}

	public void setTag(MusicTag tag) {
		this.tag = tag;
	}

	public MusicFileType getFileType() {
		return fileType;
	}

	public void setFileType(MusicFileType fileType) {
		this.fileType = fileType;
	}

	public String getOriginalFilePath() {
		return originalFilePath;
	}

	public void setOriginalFilePath(String originalFilePath) {
		this.originalFilePath = originalFilePath;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public String getNewFilePath() {
		return newFilePath;
	}

	public boolean isNewFileNameIsSet() {
		return newFileNameIsSet;
	}

	private String getOrderingMode() {
		// Reading the properties
		Properties properties = new Properties();
		BufferedInputStream stream;
		try {
			stream = new BufferedInputStream(new FileInputStream(PROPERTIES));
			properties.load(stream);
			stream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return properties.getProperty("ordering_mode");
	}

	public boolean isNewFilePathIsSet() {
		return newFilePathIsSet;
	}

	public ChromaPrint getChromaPrint() {
		return chromaPrint;
	}

	public void setChromaPrint(ChromaPrint chromaPrint) {
		this.chromaPrint = chromaPrint;
	}

	public boolean isPossibleDuplicate() {
		return possibleDuplicate;
	}

	public void setPossibleDuplicate(boolean possibleDuplicate) {
		this.possibleDuplicate = possibleDuplicate;
		
		if (possibleDuplicate) {
			this.getTag().setStatus(TagState.DUPLICATE);
		}
	}

	public String getMusicBrainz_recordingID() {
		return musicBrainz_recordingID;
	}

	public String getMusicBrainz_artistID() {
		return musicBrainz_artistID;
	}

	public ArrayList<String> getMusicBrainz_feature_artistIDs() {
		return musicBrainz_feature_artistIDs;
	}
	
}
