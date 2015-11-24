package ms5000.gui.mainframe.top.sliderbar;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;

public class SliderBar extends StackPane {

        private Slider slider = new Slider();

        private ProgressBar progressBar = new ProgressBar();
        
        public SliderBar(double init) {
            getChildren().addAll(progressBar, slider);
            bindValues();
            slider.setValue(init);
        }
        private void bindValues(){
            progressBar.progressProperty().bind(slider.valueProperty().divide(100));
        }

        public DoubleProperty sliderValueProperty() {
            return slider.valueProperty();
        }

        public boolean isTheValueChanging() {
            return slider.isValueChanging();
        }
        
        public void setProgressBarWidth() {
        	progressBar.setPrefWidth(slider.getWidth());
        }
        
        public Slider getSlider() {
        	return slider;
        }
   }