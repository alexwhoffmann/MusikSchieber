package ms5000.musicfile.tag;

import java.io.File;
import java.io.IOException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.datatype.Artwork;

import ms5000.musicfile.file.MusicFile;
import ms5000.musicfile.file.MusicFileType;
import ms5000.musicfile.tag.MusicTag.TagState;
import ms5000.properties.PropertiesUtils;
import ms5000.properties.library.OrderingProperty;

public class TagUtils {


	/**
	 * Reads the tag from the music file and stores them in a tag object
	 * 
	 * @param musicFile
	 *            the music file from which the tag will be read
	 * @return the corresponding tag object
	 * 
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 */
	public static MusicTag generateTagFromFile(File musicFile)
			throws CannotReadException, IOException, TagException, ReadOnlyFileException, InvalidAudioFrameException {
		MusicTag musicTag = new MusicTag();
		AudioFile f = AudioFileIO.read(musicFile);
		Tag tag = f.getTag();

		musicTag.setAlbum(tag.getFirst(FieldKey.ALBUM));
		musicTag.setArtist(tag.getFirst(FieldKey.ARTIST));
		musicTag.setAlbumArtist(tag.getFirst(FieldKey.ALBUM_ARTIST));
		musicTag.setTitlename(tag.getFirst(FieldKey.TITLE));
		musicTag.setComposer(tag.getFirst(FieldKey.COMPOSER));
		musicTag.setGenre(tag.getFirst(FieldKey.GENRE));
		musicTag.setYear(stringToInt(tag.getFirst(FieldKey.YEAR)));
		musicTag.setTitlenumber(stringToInt(tag.getFirst(FieldKey.TRACK)));
		musicTag.setTotal_titles(stringToInt(tag.getFirst(FieldKey.TRACK_TOTAL)));

		if (tag.getFirst(FieldKey.DISC_TOTAL).equals("") && tag.getFirst(FieldKey.DISC_NO).equals("")) {
			musicTag.setTotal_discs(1);
			musicTag.setDisc_number(1);
		} else if (tag.getFirst(FieldKey.DISC_TOTAL).equals("") && !tag.getFirst(FieldKey.DISC_NO).equals("")) {
			musicTag.setTotal_discs(stringToInt(tag.getFirst(FieldKey.DISC_NO)));
			musicTag.setDisc_number(stringToInt(tag.getFirst(FieldKey.DISC_NO)));
		} else {
			musicTag.setDisc_number(stringToInt(tag.getFirst(FieldKey.DISC_NO)));
			musicTag.setTotal_discs(stringToInt(tag.getFirst(FieldKey.DISC_TOTAL)));
		}

		musicTag.setComment(tag.getFirst(FieldKey.COMMENT));
		musicTag.setArtwork(tag.getFirstArtwork());

		return musicTag;
	}

	/**
	 * Writes the received tag to the music file
	 * 
	 * @param musicFile
	 *            the music file which will be changed
	 * @param musicTag
	 *            the music tag
	 * 
	 * @throws CannotReadException
	 * @throws IOException
	 * @throws TagException
	 * @throws ReadOnlyFileException
	 * @throws InvalidAudioFrameException
	 * @throws CannotWriteException
	 */
	public static void commitTagToFile(MusicFile musicFile, MusicTag musicTag) throws CannotReadException, IOException,
			TagException, ReadOnlyFileException, InvalidAudioFrameException, CannotWriteException {
		AudioFile f = AudioFileIO.read(musicFile.getFile());
		Tag tag = f.getTag();

		tag.setField(FieldKey.ALBUM, musicTag.getAlbum());
		tag.setField(FieldKey.ARTIST, musicTag.getArtist());
		tag.setField(FieldKey.ALBUM_ARTIST, musicTag.getAlbumArtist());
		tag.setField(FieldKey.TITLE, musicTag.getTitlename());
		tag.setField(FieldKey.COMPOSER, musicTag.getComposer());

		if (musicFile.getFileType() == MusicFileType.MP4) {
			tag.setField(FieldKey.GROUPING, musicTag.getGenre());
		}

		tag.setField(FieldKey.GENRE, musicTag.getGenre());
		tag.setField(FieldKey.YEAR, intToString(musicTag.getYear()));
		tag.setField(FieldKey.TRACK, intToString(musicTag.getTitlenumber()));
		tag.setField(FieldKey.TRACK_TOTAL, intToString(musicTag.getTotal_titles()));
		tag.setField(FieldKey.DISC_NO, intToString(musicTag.getDisc_number()));
		tag.setField(FieldKey.DISC_TOTAL, intToString(musicTag.getTotal_discs()));
		tag.setField(FieldKey.COMMENT, musicTag.getComment());

		Artwork artwork = musicTag.getArtwork();
		if (artwork != null) {
			tag.setField(musicTag.getArtwork());
		}

		// Commiting Changes to File
		f.commit();
	}

	/**
	 * Converts the received string to an integer
	 * 
	 * @param number
	 *            the number as string
	 * @return the corresponding integer
	 */
	public static int stringToInt(String number) {
		try {
			return Integer.parseInt(number);
		} catch (NumberFormatException e) {
			return 0;
		}
	}

	/**
	 * Converts the received integer to a string
	 * 
	 * @param number
	 *            the integer which will be converted
	 * @return the converted number as string
	 */
	public static String intToString(int number) {
		if (number == 0) {
			return "0";
		} else {
			return "" + number;
		}
	}

	/**
	 * Method which determines the state of the received tag and stores it in
	 * the tag
	 * 
	 * @param tag:
	 *            for which the state will be determined
	 */
	public static void determineTagState(MusicTag tag) {
		// Critical
		String artist = tag.getArtist();
		String album = tag.getAlbum();
		String titlename = tag.getTitlename();
		String genre = tag.getGenre();
		// Non-critical
		int titleNumber = tag.getTitlenumber();
		int totalTitleNumber = tag.getTotal_titles();
		OrderingProperty orderingMode = PropertiesUtils.getOrderingProperty();
		// Weak infos
		int year = tag.getYear();
		String albumArtist = tag.getAlbumArtist();

		if (tag.getMusicFile().isPossibleDuplicate()) {
			tag.setStatus(TagState.DUPLICATE);
		}

		if (orderingMode == OrderingProperty.GAA) {
			if (artist.equals("") || album.equals("") || genre.equals("") || titlename.equals("")) {
				tag.setStatus(TagState.MISSINGCRITICAL);
				return;
			} else if (titleNumber == 0 || totalTitleNumber == 0) {
				tag.setStatus(TagState.MISSINGNONCRITICAL);
				return;
			} else if (year == 0 || albumArtist.equals("")) {
				tag.setStatus(TagState.MISSINGWEAKINFOS);
				return;
			} else {
				tag.setStatus(TagState.COMPLETE);
				return;
			}
		} else {
			if (artist.equals("") || album.equals("") || titlename.equals("")) {
				tag.setStatus(TagState.MISSINGCRITICAL);
				return;
			} else if (titleNumber == 0 || totalTitleNumber == 0 || genre.equals("")) {
				tag.setStatus(TagState.MISSINGNONCRITICAL);
				return;
			} else if (year == 0 || albumArtist.equals("")) {
				tag.setStatus(TagState.MISSINGWEAKINFOS);
				return;
			} else {
				tag.setStatus(TagState.COMPLETE);
				return;
			}
		}

	}
}
