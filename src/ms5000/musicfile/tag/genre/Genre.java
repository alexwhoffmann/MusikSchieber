package ms5000.musicfile.tag.genre;

/**
 * This enumeration is used to decode the genre received by some id3v2 tag
 */
public enum Genre {
	BLUES("(0)", "Blues"),
	CLASSICROCK("(1)", "Classic Rock"),
	COUNTRY("(2)", "Country"),
	DANCE("(3)", "Dance"),
	DISCO("(4)", "Disco"),
	FUNK("(5)", "Funk"),
	GRUNGE("(6)", "Grunge"),
	HIPHOP("(7)", "Hip-Hop"),
	JAZZ("(8)", "Jazz"),
	METAL("(9)", "Metal"),
	NEWAGE("(10)", "New Age"),
	OLDIES("(11)", "Oldies"),
	OTHER("(12)", "Other"),
	POP("(13)", "Pop"),
	RANDB("(14)", "R&B"),
	RAP("(15)", "Rap"),
	REGGAE("(16)", "Reggae"),
	ROCK("(17)", "Rock"),
	TECHNO("(18)", "Techon"),
	INDUSTRIAL("(19)", "Industrial"),
	ALTERNATIVE("(20)", "Alternative"),
	SKA("(21)", "Ska"),
	DEATHMETAL("(22)", "Death Metal"),
	PRANKS("(23)", "Pranks"),
	SOUNDTRACK("(24)", "Soundtrack"),
	EUROTECHNO("(25)", "Euro-Techno"),
	AMBIENT("(26)", "Ambient"),
	TRIPHOP("(27)", "Trip-Hop"),
	VOCAL("(28)", "Vocal"),
	JAZZANDFUNK("(29)", "Jazz+Funk"),
	FUSION("(30)", "Fusion"),
	TRANCE("(31)", "Trance"),
	CLASSICAL("(32)", "Classical"),
	INSTRUMENTAL("(33)", "Instrumental"),
	ACID("(34)", "House"),
	HOUSE("(35)", "House"),
	GAME("(36)", "Game"),
	SOUNDCLIP("(37)", "Sound Clip"),
	GOSPEL("(38)", "Gospel"),
	NOISE("(39)", "Noise"),
	ALTERNROCK("(40)", "AlternRock"),
	BASS("(41)", "Bass"),
	SOUL("(42)", "Souk"),
	PUNK("(43)", "Punk"),
	SPACE("(44)", "Space"),
	MEDITATIVE("(45)", "Meditative"),
	INSTRUMENTALPOP("(46)", "Instrumental Pop"),
	INSTRUMENTALROCK("(47)", "Instrumental Rock"),
	ETHNIC("(48)", "Ethnic"),
	GOTHIC("(49)", "Gothic"),
	DARKWAVE("(50)", "Darkwave"),
	TECHNOINDUSTRIAL("(51)", "Techon-Industrial"),
	ELECTRONIC("(52)", "Electronic"),
	POPFOLK("(53)", "Pop-Folk"),
	EURODANCE("(54)", "Eurodance"),
	DREAM("(55)", "Dream"),
	SOUTHERNROCK("(56)", "Southern Rock"),
	COMEDY("(57)", "Comedy"),
	CULT("(58)", "Cult"),
	GANGSTA("(59)", "Gangsta"),
	TOP40("(60)", "Top 40"),
	CHRISTIANRAP("(61)", "Chirstian Rap"),
	POPFUNK("(62)", "Pop/Funk"),
	JUNGLE("(63)", "Jungle"),
	NATVEAMERICAN("(64)", "Native American"),
	CABARET("(65)", "Cabaret"),
	NEWWAVE("(66)", "New Wave"),
	PSYCHADELIC("(67)", "Psychadelic"),
	RAVE("(68)", "Rave"),
	SHOWTUNES("(69)", "Showtunes"),
	TRAILER("(70)", "Trailer"),
	LOFI("(71)", "Lo-Fi"),
	TRIBAL("(72)", "Tribal"),
	ACIDPUNK("(73)", "Acid Punk"),
	ACIDJAZZ("(74)", "Acid Jazz"),
	POLKA("(75)", "Polka"),
	RETRO("(76)", "Retro"),
	MUSICAL("(77)", "Musical"),
	ROCHANDROLL("(78)", "Rock & Roll"),
	HARDROCK("(79)", "Reggae");
	 
	/**
	 * The genre identifier
	 */
	String genreNumber;
	
	/**
	 * The genre name
	 */
	String genreName;

	/**
	 * Constructor
	 * 
	 * @param genreNumber The genre identifier
	 * @param genreName The genre name
	 */
	private Genre(String genreNumber, String genreName) {
		this.genreNumber = genreNumber;
		this.genreName = genreName;
	}
	
	/**
	 * Returns the genre identifier to the received genre
	 * 
	 * @param gen Genre to which the identifier will be returned
	 * 
	 * @return the genre identifier as string
	 */
	public static String getGenreNumber(Genre gen) {
		return gen.genreNumber;
	}
	
	/**
	 * Returns the genre name to the received genre
	 * 
	 * @param gen Genre to which the name will be returned
	 * 
	 * @return the genre name
	 */
	public static String getGenreName(Genre gen) {
		return gen.genreName;
	}
	
	/**
	 * Method to return the genre to the received genre
	 * 
	 * @param gen the received genre as string
	 * 
	 * @return the corresponding genre enumeration object
	 */
	public static Genre getGenre(String gen) {
		for (Genre genre : Genre.values()) {
			if (gen.equals(genre.genreNumber)) {
				return genre;
			}
		}

		return null;
	}

}
