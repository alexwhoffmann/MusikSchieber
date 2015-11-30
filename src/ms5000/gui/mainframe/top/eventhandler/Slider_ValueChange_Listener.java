package ms5000.gui.mainframe.top.eventhandler;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import ms5000.audio.player.AudioPlayer;
import ms5000.audio.player.PlayerStatus;

/**
 * This class implements the invalidation listener of the progress slider bar
 */
public class Slider_ValueChange_Listener implements InvalidationListener{
	/**
	 * Instance of the slider
	 */
	private Slider slider;
	
	/**
	 * Instance of the media player
	 */
	private MediaPlayer player;
	
	/**
	 * Constructs the invalidation listener
	 *
	 * @param slider the slider
	 */
	public Slider_ValueChange_Listener(Slider slider) {
		this.slider = slider;
	}
	
	@Override
	public void invalidated(Observable arg0) {
		player = AudioPlayer.getInstance().getMediaPlayer();
		
		// Pausing, Changing the position in the tune and playingi it again
		if (player != null) {
			if ((AudioPlayer.getInstance().getProgressOld() + AudioPlayer.getInstance().getDifference() + 0.5) < slider
					.getValue() || (AudioPlayer.getInstance().getProgressOld() + AudioPlayer.getInstance().getDifference() 
							- 0.5) > slider.getValue()) {
				
				// Determining the new position in the tune
				Duration newPosition = player.getTotalDuration().multiply(slider.getValue()/100);
				player.seek(newPosition);
				
				// Returing the song in case it was playing before
				if (AudioPlayer.getInstance().getStatus() == PlayerStatus.PLAYING) {
					player.pause();
					
					try {
						Thread.sleep(50);
						player.play();
					} catch (InterruptedException e) {
						player.play();
					}
				}
				
			}
		} else {
			// Case: no tune is playing
			slider.setValue(0);
			slider.setDisable(true);
		}
		
	}

}
