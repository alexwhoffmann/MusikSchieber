package ms5000.gui.mainframe.top.eventhandler;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import ms5000.audio.player.AudioPlayer;
import ms5000.audio.player.PlayerStatus;

public class Slider_ValueChange_Listener implements InvalidationListener{
	private Slider slider;
	private MediaPlayer player;
	
	public Slider_ValueChange_Listener(Slider slider) {
		this.slider = slider;
	}
	
	@Override
	public void invalidated(Observable arg0) {
		player = AudioPlayer.getInstance().getMediaPlayer();
		
		if (player != null) {
			if ((AudioPlayer.getInstance().getProgressOld() + AudioPlayer.getInstance().getDifference() + 0.5) < slider
					.getValue() || (AudioPlayer.getInstance().getProgressOld() + AudioPlayer.getInstance().getDifference() 
							- 0.5) > slider.getValue()) {
				
				Duration newPosition = player.getTotalDuration().multiply(slider.getValue()/100);
				player.seek(newPosition);
				
				
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
			slider.setValue(0);
			slider.setDisable(true);
		}
		
	}

}
