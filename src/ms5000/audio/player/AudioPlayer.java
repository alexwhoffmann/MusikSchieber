package ms5000.audio.player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.MessageFormat;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import ms5000.gui.mainframe.Main_Frame;
import ms5000.musicfile.file.MusicFile;
import ms5000.properties.PropertiesUtils;

/**
 * This class implements the main functionality of the player in the top of the main frame.
 * It is built up to be a singleton, so there can be only one instance of it
 */
public class AudioPlayer {
	
	/**
	 * Instance of the media player
	 */
	private MediaPlayer mediaPlayer;
	
	/**
	 * Double to capture the difference between to time steps
	 */
	private double difference = 0.0;
	
	/**
	 * Double to capture the time in milliseconds between the time gaps
	 */
	private double progressOld = 0.0;
	
	/**
	 * The status of the player
	 */
	private PlayerStatus status;
	
	/**
	 * Instance of the the ChangeListener that updates the slider in the top
	 */
	private ChangeListener<Duration> changeListener;
	
	/**
	 * the currently playing music file
	 */
	private MusicFile currMusicFile;
	
	/**
	 * Instance of the player itself
	 */
	private static AudioPlayer audioPlayer = new AudioPlayer();
	
	/**
	 * Private constructor, so that it is impossible to instantiate 
	 * the player from outside the class 
	 */
	private AudioPlayer() {
	}
	
	/**
	 * Method to return the instance of the player
	 * 
	 * @return the instance of the player
	 */
	public static AudioPlayer getInstance() {
		return audioPlayer;
	}
	
	/**
	 * Method that sets the current audio file
	 * 
	 * @param file the audio file which is to be played
	 */
	public void setMedia(MusicFile file) {
		this.currMusicFile = file;

		if (file == null) {
			// Setting the player status
			this.setStatus(PlayerStatus.NOMEDIAFILE);
			mediaPlayer = null;
			
		} else {
			Media media = new Media(file.getFile().toURI().toString());
			mediaPlayer = new MediaPlayer(media);
			
			
			// Getting and setting the instance of the Change Listener
			changeListener = InitChangeListener();
			mediaPlayer.currentTimeProperty().addListener(changeListener);
			
			// Binding the volume property to the slider in the right hand side of
			// the player
			mediaPlayer.volumeProperty().bind(
					Main_Frame.gethBox_Right().getVolumeSlider().getVolumeSlider().sliderValueProperty().divide(100));
				
			// Setting the player status
			this.setStatus(PlayerStatus.READY);
		}
	}
	
	/**
	 * Method to return the media player (mostly for time operations)
	 * 
	 * @return the current instance of the media player
	 */
	public MediaPlayer getMediaPlayer() {
		return this.mediaPlayer;
	}

	/**
	 * Method to pause the player
	 */
	public void pause() {
		if (mediaPlayer != null) {
			// Pausing the player
			mediaPlayer.pause();

			// Setting the player status
			this.setStatus(PlayerStatus.PAUSED);
		}
	}

	/**
	 * Method to start the player
	 */
	public void play() {
		if (mediaPlayer != null) {
			// Setting up the status text
			String currentlyPlaying = MessageFormat.format(PropertiesUtils.getString("top.section.text.audioplayer.playing"),
					currMusicFile.getTag().getArtist(), currMusicFile.getTag().getTitlename());
			Main_Frame.getBorderPaneTopCenter().getStatusSlider().setStatusText(currentlyPlaying);

			// Enabling the slider which shows the progress of the song
			Main_Frame.getBorderPaneTopCenter().getStatusSlider().getSlider().getSlider().setDisable(false);

			// Starting the player
			mediaPlayer.play();
			
			// Setting the status
			this.setStatus(PlayerStatus.PLAYING);
		}
	}
	
	/**
	 * Method to stop the player
	 */
	public void stop() {
		if (mediaPlayer != null) {
			//
			Main_Frame.getBorderPaneTopCenter().resetStatusFields();

			Main_Frame.getBorderPaneTopCenter().getStatusSlider().getSlider().getSlider().setValue(0);
			Main_Frame.getBorderPaneTopCenter().getStatusSlider().getSlider().getSlider().setDisable(true);

			mediaPlayer.stop();
			this.setStatus(PlayerStatus.READY);
		}
	}
	
	/**
	 * Method to return an instance of the change listener
	 *  
	 * @return An instance of the change listener
	 */
	private ChangeListener<Duration> InitChangeListener() {
		return new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
				// Updating the time fields in the top of the frame
				Main_Frame.getBorderPaneTopCenter().setTimeFields(formatTimeToGo(newValue), "-" + formatTimeLeft(newValue));
				
				// Determining the time gap between the single steps
				progressOld = (oldValue.toMillis() / mediaPlayer.getTotalDuration().toMillis())*100;
				double progressNew = (newValue.toMillis() / mediaPlayer.getTotalDuration().toMillis())*100;
				difference = progressNew - progressOld;
				
				Main_Frame.getBorderPaneTopCenter().getStatusSlider().getSlider().getSlider().setValue(progressNew);
				
				if (newValue.greaterThanOrEqualTo(mediaPlayer.getTotalDuration())) {
					// Invoking the stop button in case the song is over
					Main_Frame.gethBox_Left().getBtnStop().fire();
				}
			}
			};
	}
	
	/**
	 * Method to format the time to go
	 * 
	 * @param duration the current position in the song
	 * @return the formated time
	 */
	private String formatTimeToGo(Duration duration) {
		double millis = duration.toMillis();
		
		// calculating seconds and minutes from the milliseconds
		double second = (millis / 1000) % 60;
		double minute = (millis / (1000 * 60)) % 60;

		// returning the rounded time
		return formatTime(round(minute,0),  round(second,0));
	}
	
	/**
	 * Method to format the time left on the song
	 * 
	 * @param duration the current position in the song
	 * @return the formated time
	 */
	private String formatTimeLeft(Duration duration) {
		// Calculating the time left on the song
		Duration timeLeft = mediaPlayer.getTotalDuration().subtract(duration); 
		double millis = timeLeft.toMillis();
		
		// calculating seconds and minutes from the milliseconds
		double second = (millis / 1000) % 60;
		double minute = (millis / (1000 * 60)) % 60;
		
		// returning the rounded time
		return formatTime(round(minute,0),  round(second,0));
	}
	
	/**
	 * Method to round the given value to "places" after the comma
	 * 
	 * @param value the value which is to be rounded
	 * @param places amount of places behind the comma
	 * @return the rounded value 
	 */
	private double round(double value, int places) {
		try {
			BigDecimal bd = new BigDecimal(value);
			bd = bd.setScale(places, RoundingMode.FLOOR);
			return bd.doubleValue();
		} catch (NumberFormatException e) {
			return 0.0;
		}
	}
	
	/**
	 * Method to format the time to look like 00:00
	 * 
	 * @param minute the amount of minutes
	 * @param seconds the amount of seconds
	 * 
	 * @return the formatted time as string
	 */
	private String formatTime(double minute, double seconds) {
		String time = "";
		
		if (seconds < 10.0) {
			time = ":0" + String.valueOf(seconds).substring(0, 1);
		} else {
			time = ":" + String.valueOf(seconds).substring(0, 2);
		}
		
		if (minute < 1.0) {
			time = "00" + time;
		} else if (minute >= 1.0 && minute < 10.00) {
			time = "0" + String.valueOf(minute).substring(0, 1) + time;
		} else {
			time = String.valueOf(minute).substring(0, 2) + time;
		}
		
		return time;
	}
	
	/**
	 * Method to return the difference between the time gaps
	 * 
	 * @return the difference between the time gaps
	 */
	public double getDifference() {
		return difference;
	}

	/**
	 * Method to return the time in the song before the slider was updated
	 * 
	 * @return the time before the slider gets updated
	 */
	public double getProgressOld() {
		return progressOld;
	}

	/**
	 * Method to return the current status of the player
	 * 
	 * @return the current status of the player
	 */
	public PlayerStatus getStatus() {
		return status;
	}

	/**
	 * Method to set the player status from another class
	 * 
	 * @param status the player status that will be set
	 */
	public void setStatus(PlayerStatus status) {
		this.status = status;
	}
	
	/**
	 * Method to disable the audio player internally and on the frame
	 */
	public void disablePlayer() {
		if (AudioPlayer.getInstance().getMediaPlayer() != null) {
			if (AudioPlayer.getInstance().getStatus() != PlayerStatus.READY) {
				AudioPlayer.getInstance().stop();
			}
		}
		Main_Frame.gethBox_Left().getBtnStop().setDisable(true);
		Main_Frame.gethBox_Left().getBtnStart().setDisable(true);
		
		Main_Frame.getBorderPaneTopCenter().resetStatusFields();
		AudioPlayer.getInstance().setMedia(null);
	}
	
	/**
	 * Method to enable the audio player internally and on the frame
	 */
	public void enablePlayer() {
		if (Main_Frame.gethBox_Left().getBtnStop().isDisable()) {
			Main_Frame.gethBox_Left().getBtnStop().setDisable(false);
		}
		
		Main_Frame.gethBox_Left().getBtnStart().setDisable(false);
		
		if (AudioPlayer.getInstance().getMediaPlayer() != null) {
			if (AudioPlayer.getInstance().getStatus() != PlayerStatus.READY) {
				AudioPlayer.getInstance().stop();
			}
		}
		
		Main_Frame.getBorderPaneTopCenter().resetStatusFields();
	}

	
}	
