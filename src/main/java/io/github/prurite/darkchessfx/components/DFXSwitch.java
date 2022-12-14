package io.github.prurite.darkchessfx.components;

import io.github.prurite.darkchessfx.DarkchessFXResourcesLoader;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class DFXSwitch extends StackPane {
    private BooleanProperty state = new SimpleBooleanProperty();

    public DFXSwitch() {
        getStylesheets().add(DarkchessFXResourcesLoader.loadURL("css/DFXSwitch.css").toExternalForm());
        Button button = new Button();
        button.getStyleClass().add("DFXSwitchButton");
        Rectangle back = new Rectangle();
        back.getStyleClass().add("DFXSwitchBack");
        Circle buttonCircle = new Circle();
        buttonCircle.getStyleClass().add("DFXSwitchButtonCircle");

        button.setShape(buttonCircle);
        back.widthProperty().bind(button.widthProperty().multiply(2));
        back.heightProperty().bind(button.heightProperty().multiply(0.8));
        back.arcHeightProperty().bind(back.heightProperty());
        back.arcWidthProperty().bind(back.heightProperty());
        buttonCircle.radiusProperty().bind(button.heightProperty());
        this.maxWidthProperty().bind(back.widthProperty());
        setAlignment(button, Pos.CENTER_LEFT);

        getChildren().addAll(back, button);
        state.set(false);

        EventHandler<Event> click = new EventHandler<Event>() {
            @Override
            public void handle(Event e) {
                if (state.get()) {
                    button.setStyle("-fx-background-color: -dark-gray-color;");
                    back.setStyle("-fx-fill: -switch-off-color;");
                    setAlignment(button, Pos.CENTER_LEFT);
                    state.set(false);
                } else {
                    button.setStyle("-fx-background-color: -theme-color;");
                    back.setStyle("-fx-fill: -switch-on-color 50%;");
                    setAlignment(button, Pos.CENTER_RIGHT);
                    state.set(true);
                }
            }
        };

        button.setFocusTraversable(false);
        setOnMouseClicked(click);
        button.setOnMouseClicked(click);
    }

    public BooleanProperty getState() {
        return state;
    }
}