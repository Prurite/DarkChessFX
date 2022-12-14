package io.github.prurite.darkchessfx.components;

import io.github.prurite.darkchessfx.DarkchessFXResourcesLoader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class DFXSlider extends StackPane {
    Slider slider;

    public DFXSlider() {
        getStylesheets().add(DarkchessFXResourcesLoader.loadURL("css/DFXSlider.css").toExternalForm());

        slider = new Slider();
        // The rectangle which shows the progress
        Rectangle progressRec = new Rectangle();
        progressRec.getStyleClass().add("progressRec");
        // Bind both width and height to match the size of Slider
        progressRec.heightProperty().bind(slider.heightProperty().multiply(0.5));
        progressRec.widthProperty().bind(slider.widthProperty().add(slider.heightProperty().multiply(-0.5)));

        // Listen on value changes on the slider to update the progress (color)
        slider.valueProperty().addListener((ov, old_val, new_val) -> {
            // Using linear gradient we can fill two colors to show the progress
            double pos = (new_val.doubleValue()-slider.getMin())/(slider.getMax()-slider.getMin())*100;
            String style = String.format("-fx-fill: linear-gradient(to right, -theme-color %f%%, -light-gray-color %f%%);", pos, pos);
            // set the Style
            progressRec.setStyle(style);
        });
        getChildren().addAll(progressRec, slider);
        slider.translateYProperty().bind(slider.heightProperty().multiply(0.1));
    }

    public Slider getSlider() {
        return slider;
    }
}