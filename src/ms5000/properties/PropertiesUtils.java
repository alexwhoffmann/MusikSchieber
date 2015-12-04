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
import ms5000.properties.icons.IconProperties;
import ms5000.properties.importprop.ImportProperties;
import ms5000.properties.library.LibraryProperties;
import ms5000.properties.library.OrderingProperty;
import ms5000.properties.playlist.PlayListProperties;
import ms5000.properties.web.WebProperties;

/**
 * Helper class to read and write the property files
 */
public class PropertiesUtils {
	
	/**
	 * Property profile instance
	 */
	private static Properties properties;
	
	
	/**
	 * Method to return the property value of the received icon property
	 * 
	 * @param iconProperty the web property which the user wants to be returned
	 * @return the value of the icon property
	 */
	public static String getProperty(IconProperties iconProperty) {
		return readProperty(PropertyType.ICON.returnName(), iconProperty.returnName());
	}
	
	/**
	 * Method to return the property value of the received web property
	 * 
	 * @param webProperty the web property which the user wants to be returned
	 * @return the value of the web property
	 */
	public static String getProperty(WebProperties webProperty) {
		return readProperty(PropertyType.HTTP.returnName(), webProperty.returnName());
	}
	
	/**
	 * Method to return the property value of the received playlist property
	 * 
	 * @param webProperty the playlist property which the user wants to be returned
	 * @return the value of the playlist property
	 */
	public static String getProperty(PlayListProperties playListProperty) {
		return readProperty(PropertyType.PLAYLIST.returnName(), playListProperty.returnName());
	}
	
	/**
	 * Method to return the property value of the received import property
	 * 
	 * @param webProperty the import property which the user wants to be returned
	 * @return the value of the import property
	 */
	public static String getProperty(ImportProperties importProperty) {
		return readProperty(PropertyType.IMPORT.returnName(), importProperty.returnName());
	}
	
	/**
	 * Method to return the property value of the received gui property
	 * 
	 * @param webProperty the gui property which the user wants to be returned
	 * @return the value of the gui property
	 */
	public static String getProperty(GuiProperties guiProperty) {
		return readProperty(PropertyType.GUI.returnName(), guiProperty.returnName());
	}

	/**
	 * Method to return the property value of the received library property
	 * 
	 * @param webProperty the library property which the user wants to be returned
	 * @return the value of the library property
	 */
	public static String getProperty(LibraryProperties libraryProperty) {
		return readProperty(PropertyType.LIBRARY.returnName(), libraryProperty.returnName());
	}

	/**
	 * Method to return the property value of the stored ordering property
	 * 
	 * @return the stored ordering property
	 */
	public static OrderingProperty getOrderingProperty() {
		String propertyValue = readProperty(PropertyType.LIBRARY.returnName(),
				LibraryProperties.ORDERINGMODE.returnName());
		return OrderingProperty.getOrderingCode(propertyValue);
	}

	/**
	 * Method to set the property to the received value
	 * 
	 * @param webProperty Web property that's going to be changed
	 * @param value value to which the property is going to be changed
	 */
	public static void setProperty(WebProperties webProperty, String value) {
		writeProperty(PropertyType.HTTP.returnName(), webProperty.returnName(), value);
	}
	
	/**
	 * Method to set the property to the received value
	 * 
	 * @param libraryProperty Library property that's going to be changed
	 * @param value value to which the property is going to be changed
	 */
	public static void setProperty(LibraryProperties libraryProperty, String value) {
		writeProperty(PropertyType.LIBRARY.returnName(), libraryProperty.returnName(), value);
	}
	
	/**
	 * Method to set the property to the received value
	 * 
	 * @param importProperty Import property that's going to be changed
	 * @param value value to which the property is going to be changed
	 */
	public static void setProperty(ImportProperties importProperty, String value) {
		writeProperty(PropertyType.IMPORT.returnName(), importProperty.returnName(), value);
	}
	
	/**
	 * Method to set the property to the received value
	 * 
	 * @param playlistProperty Playlist property that's going to be changed
	 * @param value value to which the property is going to be changed
	 */
	public static void setProperty(PlayListProperties playlistProperty, String value) {
		writeProperty(PropertyType.PLAYLIST.returnName(), playlistProperty.returnName(), value);
	}
	
	/**
	 * Method to set the property to the received value
	 * 
	 * @param guiProperty GUI property that's going to be changed
	 * @param value value to which the property is going to be changed
	 */
	public static void setProperty(GuiProperties guiProperty, String value) {
		writeProperty(PropertyType.GUI.returnName(), guiProperty.returnName(), value);
	}
	
	/**
	 * Method to set the music library ordering property
	 * 
	 * @param orderingProperty the new ordering property of the music library
	 */
	public static void setOrderingProperty(OrderingProperty orderingProperty) {
		writeProperty(PropertyType.LIBRARY.returnName(), LibraryProperties.ORDERINGMODE.returnName(),
				orderingProperty.returnValue());
	}
	
	/**
	 * Method to read the properties files
	 * 
	 * @param propertyType Property file type which will be read
	 * @param propertyName Property which will be read
	 * 
	 * @return value of the read property
	 */
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

	/**
	 * Method to write the received property name in received property file to the received value
	 * 
	 * @param propertyType property file in which the property is going to be changed
	 * @param propertyName the property that is going to be changed
	 * @param value the new value of the property
	 */
	private static void writeProperty(String propertyType, String propertyName, String value) {
		ArrayList<String[]> list = new ArrayList<String[]>();
		ArrayList<String[]> newList = new ArrayList<String[]>();
		
		BufferedReader br = null;
		PrintWriter printWriter = null;
		
		
		try {
			br = new BufferedReader(new FileReader(new File(propertyType)));
			
			// Splitting up the property file and adding it to a list
			String line;
			while ((line = br.readLine()) != null) {
				if (!line.startsWith("#")) {
					list.add(line.split("="));
				}
			}
			
			for (String[] entry : list) {
				// Url and filepath configuration
				if (entry[0].equals(propertyName)) {
					if (value.contains("\\")) {
						value = value.replace("\\", "\\\\");
					}
					
					if (value.contains(":")) {
						value = value.replaceFirst(":", "\\:");
					}
					
					// Writing the new value to the array
					if (entry.length == 1) {
						// Case: no value was stored
						String[] newEntry = new String[2];
						
								
						// New entry object		
						newEntry[0] = propertyName;
						newEntry[1] = value;
						
						newList.add(newEntry);
					} else {
						entry[1] = value;
						newList.add(entry);
					}
				} else {
					if (entry.length == 1) {
						String[] newEntry = new String[2];
						newEntry[0] = entry[0];
						newEntry[1] = "";
						
						newList.add(newEntry);
					} else {
						newList.add(entry);
					}
				}
			}
			
			
			// Writing in the file
			printWriter = new PrintWriter(new File(propertyType));

			for (String[] entry : newList) {
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
	
	/**
	 * Method to generate the current profile
	 * 
	 * @return the profile object
	 */
	public static ProfileProperties getProfile() {
		ProfileProperties profile = ProfileProperties.getProfile();
		
		readMusicLibraryProperties(profile);
		readImportProperties(profile);
		readPlayListProperties(profile);
		
		return profile;
	}
	
	/**
	 * Method to write the current profile to the properties files
	 */
	public static void saveProfile() {
		ProfileProperties profile = ProfileProperties.getProfile();
		
		setProperty(LibraryProperties.FILEPATH, profile.getPathToMusicLibrary());
		setOrderingProperty(profile.getOrderingMode());
		
		setProperty(ImportProperties.KEEPFILES, "" + profile.isKeepOriginalFiles());
		setProperty(ImportProperties.JUSTTAGFILES, "" + profile.isJustTagFiles());
		
		setProperty(PlayListProperties.PLAYLISTEXPORT, "" + profile.isPlayListExport());
		setProperty(PlayListProperties.PLAYLISTEXPORTDIR, profile.getPlayListExportDir());
		setProperty(PlayListProperties.PLAYLISTHEADER,profile.getPlayListHeader());
	}
	
	/**
	 * Method to read the text and config properties of this program
	 * 
	 * @param propertyName the property name
	 * @return the property value
	 */
	public static String getString(String propertyName) {
		String propertyValue = "";
		
		if (propertyName.contains("top.section")) {
			if (propertyName.contains("config")) {
				propertyValue = readProperty(ConfigTextProperty.TOP_SECTION.getConfigProperty(),propertyName);
			} else if (propertyName.contains("text")){
				propertyValue = readProperty(ConfigTextProperty.TOP_SECTION.getTextProperty(),propertyName);
			}
		} else if (propertyName.contains("center.section")){
			if (propertyName.contains("config")) {
				propertyValue = readProperty(ConfigTextProperty.CENTER_SECTION.getConfigProperty(),propertyName);
			} else if (propertyName.contains("text")){
				propertyValue = readProperty(ConfigTextProperty.CENTER_SECTION.getTextProperty(),propertyName);
			}
		} else if (propertyName.contains("bottom.section")) {
			if (propertyName.contains("config")) {
				propertyValue = readProperty(ConfigTextProperty.BOTTOM_SECTION.getConfigProperty(),propertyName);
			} else if (propertyName.contains("text")){
				propertyValue = readProperty(ConfigTextProperty.BOTTOM_SECTION.getTextProperty(),propertyName);
			}
		} else if (propertyName.contains("profile.settings")) {
			if (propertyName.contains("config")) {
				propertyValue = readProperty(ConfigTextProperty.PROFILE_SETTINGS.getConfigProperty(),propertyName);
			} else if (propertyName.contains("text")){
				propertyValue = readProperty(ConfigTextProperty.PROFILE_SETTINGS.getTextProperty(),propertyName);
			}
		} else if (propertyName.contains("util.config")) {
			propertyValue = readProperty(ConfigTextProperty.UTIL.getConfigProperty(),propertyName);
		} else if (propertyName.contains("readdir.text")) {
			propertyValue = readProperty(ConfigTextProperty.READDIR_TASK.getTextProperty(),propertyName);
		} else if (propertyName.contains("importfiles.text")) {
			propertyValue = readProperty(ConfigTextProperty.IMPORTFILES_TASK.getTextProperty(),propertyName);
		}
		
		return propertyValue;
	}
	
	/**
	 * Returns the property value as a string array
	 * 
	 * @param propertyName the property name
	 * @return the property value as string
	 */
	public static String[] getArray(String propertyName) {
		String propertyValue;
		
		if (propertyName.contains("util.config")) {
			propertyValue = readProperty(ConfigTextProperty.UTIL.getConfigProperty(),propertyName);
			return propertyValue.split(",");
		} else {
			return null;
		}
	}
	
	/**
	 * Method to read and store the playlist properties in the profile object
	 * 
	 * @param profile the profile object
	 */
	private static void readPlayListProperties(ProfileProperties profile) {
		try {
			profile.setPlayListExport(Boolean.parseBoolean(getProperty(PlayListProperties.PLAYLISTEXPORT)));
		} catch (NumberFormatException e) {
			profile.setPlayListExport(true);
		}
		
		profile.setPlayListExportDir(getProperty(PlayListProperties.PLAYLISTEXPORTDIR));
		profile.setPlayListHeader(getProperty(PlayListProperties.PLAYLISTHEADER));
	}

	/**
	 * Method to read and store the import properties in the profile object
	 * 
	 * @param profile the profile object
	 */
	private static void readImportProperties(ProfileProperties profile) {
		try {
			profile.setKeepOriginalFiles(Boolean.parseBoolean(getProperty(ImportProperties.KEEPFILES)));
			profile.setJustTagFiles(Boolean.parseBoolean(getProperty(ImportProperties.JUSTTAGFILES)));
		} catch (NumberFormatException e) {
			profile.setKeepOriginalFiles(true);
			profile.setJustTagFiles(false);
		}
	}
	
	/**
	 * Method to read and store the music library properties in the profile object
	 * 
	 * @param profile the profile object
	 */
	private static void readMusicLibraryProperties(ProfileProperties profile) {
		profile.setOrderingMode(PropertiesUtils.getOrderingProperty());
		profile.setPathToMusicLibrary(PropertiesUtils.getProperty(LibraryProperties.FILEPATH));
	}
}
