package ms5000.properties;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

import ms5000.properties.gui.GuiProperties;
import ms5000.properties.importprop.ImportProperties;
import ms5000.properties.library.LibraryProperties;
import ms5000.properties.library.OrderingProperty;
import ms5000.properties.playlist.PlayListProperties;
import ms5000.properties.web.WebProperties;

public class PropertiesUtils {
	/**
	 * Property instance
	 */
	private static Properties properties;

	public static String getProperty(WebProperties webProperty) {
		return readProperty(PropertyType.HTTP.returnName(), webProperty.returnName());
	}
	
	public static String getProperty(PlayListProperties playListProperty) {
		return readProperty(PropertyType.PLAYLIST.returnName(), playListProperty.returnName());
	}
	
	public static String getProperty(ImportProperties importProperty) {
		return readProperty(PropertyType.IMPORT.returnName(), importProperty.returnName());
	}

	public static String getProperty(GuiProperties guiProperty) {
		return readProperty(PropertyType.GUI.returnName(), guiProperty.returnName());
	}

	public static OrderingProperty getOrderingProperty() {
		String propertyValue = readProperty(PropertyType.LIBRARY.returnName(),
				LibraryProperties.ORDERINGMODE.returnName());
		return OrderingProperty.getOrderingCode(propertyValue);
	}

	public static String getProperty(LibraryProperties libraryProperty) {
		return readProperty(PropertyType.LIBRARY.returnName(), libraryProperty.returnName());
	}

	public static void setProperty(WebProperties webProperty, String value) {
		writeProperty(PropertyType.HTTP.returnName(), webProperty.returnName(), value);
	}

	public static void setProperty(LibraryProperties libraryProperty, String value) {
		writeProperty(PropertyType.LIBRARY.returnName(), libraryProperty.returnName(), value);
	}
	
	public static void setProperty(ImportProperties importProperty, String value) {
		writeProperty(PropertyType.IMPORT.returnName(), importProperty.returnName(), value);
	}
	
	public static void setProperty(PlayListProperties playlistProperty, String value) {
		writeProperty(PropertyType.PLAYLIST.returnName(), playlistProperty.returnName(), value);
	}

	public static void setOrderingProperty(OrderingProperty orderingProperty) {
		writeProperty(PropertyType.LIBRARY.returnName(), LibraryProperties.ORDERINGMODE.returnName(),
				orderingProperty.returnValue());
	}

	public static void setProperty(GuiProperties guiProperty, String value) {
		writeProperty(PropertyType.GUI.returnName(), guiProperty.returnName(), value);
	}

	private static String readProperty(String propertyType, String propertyName) {

		properties = new Properties();
		// Reading the properties
		BufferedInputStream stream;
		try {
			stream = new BufferedInputStream(new FileInputStream(propertyType));
			properties.load(stream);
			stream.close();

			// Reading the last Dir which was Imported1
			String result = properties.getProperty(propertyName);
			return result;
		} catch (FileNotFoundException e) {
			// Maybe emergency property
			return "";
		} catch (IOException e) {
			return "";
		}
	}

	private static void writeProperty(String propertyType, String propertyName, String value) {
		ArrayList<String[]> list = new ArrayList<String[]>();
		BufferedReader br = null;
		PrintWriter printWriter = null;

		try {

			br = new BufferedReader(new FileReader(new File(propertyType)));
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.startsWith("#")) {
					list.add(line.split("="));
				}
			}

			for (String[] entry : list) {
				if (entry[0].equals(propertyName)) {
					if (value.contains("\\")) {
						value = value.replace("\\", "\\\\");
					}

					if (value.contains(":")) {
						value = value.replaceFirst(":", "\\:");
					}

					entry[1] = value;
				}
			}

			printWriter = new PrintWriter(new File(propertyType));

			for (String[] entry : list) {
				printWriter.println(entry[0] + "=" + entry[1]);
			}
			printWriter.close();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (printWriter != null) {
				printWriter.close();
			}
		}
	}
	
	public static ProfileProperties getProfile() {
		ProfileProperties profile = ProfileProperties.getProfile();
		
		readMusicLibraryProperties(profile);
		readImportProperties(profile);
		readPlayListProperties(profile);
		
		return profile;
	}
	
	public static void saveProfile() {
		ProfileProperties profile = ProfileProperties.getProfile();
		
		setProperty(LibraryProperties.FILEPATH, profile.getPathToMusicLibrary());
		setOrderingProperty(profile.getOrderingMode());
		
		setProperty(ImportProperties.KEEPFILES, "" + profile.isKeepOriginalFiles());
		setProperty(ImportProperties.JUSTTAGFILES, "" + profile.isJustTagFiles());
		
		setProperty(PlayListProperties.PLAYLISTEXPORT, "" + profile.isPlayListExport());
		setProperty(PlayListProperties.PLAYLISTEXPORTDIR, profile.getPlayListExportDir());
	}
	

	private static void readPlayListProperties(ProfileProperties profile) {
		try {
			profile.setPlayListExport(Boolean.parseBoolean(getProperty(PlayListProperties.PLAYLISTEXPORT)));
			
		} catch (NumberFormatException e) {
			profile.setPlayListExport(true);
		}
		
		profile.setPlayListExportDir(getProperty(PlayListProperties.PLAYLISTEXPORTDIR));
	}

	private static void readImportProperties(ProfileProperties profile) {
		try {
			profile.setKeepOriginalFiles(Boolean.parseBoolean(getProperty(ImportProperties.KEEPFILES)));
			profile.setJustTagFiles(Boolean.parseBoolean(getProperty(ImportProperties.JUSTTAGFILES)));
		} catch (NumberFormatException e) {
			profile.setKeepOriginalFiles(true);
			profile.setJustTagFiles(false);
		}
	}

	private static void readMusicLibraryProperties(ProfileProperties profile) {
		profile.setOrderingMode(PropertiesUtils.getOrderingProperty());
		profile.setPathToMusicLibrary(PropertiesUtils.getProperty(LibraryProperties.FILEPATH));
	}
}
