package ms5000.audio.player;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import ms5000.gui.mainframe.top.BoderPane_TOP_CENTER;
import ms5000.gui.mainframe.top.VBox_StatusSlider;
import ms5000.gui.mainframe.top.VBox_VolumeSlider;
import ms5000.musicfile.file.MusicFile;

public class AudioPlayer {
	private MediaPlayer mediaPlayer;
	private static AudioPlayer audioPlayer = new AudioPlayer();
	
	private AudioPlayer() {
	}
	
	public static AudioPlayer getInstance() {
		return audioPlayer;
	}
	
	
	public void setMedia(MusicFile file) {
		Media media = new Media(file.getFile().toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		
		mediaPlayer.volumeProperty().bind(VBox_VolumeSlider.getVolumeSlider().sliderValueProperty().divide(100));
		mediaPlayer.currentTimeProperty().addListener(InitChangeListener());
	}
	
	public MediaPlayer getMediaPlayer() {
		return this.mediaPlayer;
	}
	
	public void pause() {
		if(mediaPlayer != null) {
			mediaPlayer.pause();
		}
	}
	
	public void play() {
		if(mediaPlayer != null) {
			VBox_StatusSlider.getSlider().getSlider().setDisable(false);
			mediaPlayer.play();
		}
	}
	
	public void stop() {
		if(mediaPlayer != null) {
			VBox_StatusSlider.getSlider().getSlider().setValue(0);
			VBox_StatusSlider.getSlider().getSlider().setDisable(true);
			mediaPlayer.stop();
		}
	}
	
	private ChangeListener<Duration> InitChangeListener() {
		return new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
				BoderPane_TOP_CENTER.getTimeToGO().setText(formatTimeToGo(newValue));
				BoderPane_TOP_CENTER.getTimeLeft().setText("-" + formatTimeLeft(newValue));
				
				Double progress = (newValue.toMillis() / mediaPlayer.getTotalDuration().toMillis())*100;
				System.out.println(progress);
				VBox_StatusSlider.getSlider().getSlider().setValue(progress);
			}
			};
	}
	
	private String formatTimeToGo(Duration duration) {
		double millis = duration.toMillis();
		
		double second = (millis / 1000) % 60;
		double minute = (millis / (1000 * 60)) % 60;

		return formatTime(round(minute,0),  round(second,0));
	}
	
	private String formatTimeLeft(Duration duration) {
		Duration timeLeft = mediaPlayer.getTotalDuration().subtract(duration); 
		double millis = timeLeft.toMillis();
		
		double second = (millis / 1000) % 60;
		double minute = (millis / (1000 * 60)) % 60;

		return formatTime(round(minute,0),  round(second,0));
	}
	
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.FLOOR);
	    return bd.doubleValue();
	}
	
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
}	
