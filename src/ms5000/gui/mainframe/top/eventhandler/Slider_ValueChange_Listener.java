package ms5000.gui.mainframe.top.eventhandler;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.Slider;
import javafx.scene.media.MediaPlayer;
import ms5000.audio.player.AudioPlayer;

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
			System.out.println("Hello");
		} else {
			slider.setValue(0);
			slider.setDisable(true);
		}
		
	}

}
