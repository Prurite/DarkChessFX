package io.github.prurite.darkchessfx;

import fr.brouillard.oss.cssfx.CSSFX;
import io.github.prurite.darkchessfx.controllers.DFXController;
import io.github.prurite.darkchessfx.controllers.GameBoardController;
import io.github.prurite.darkchessfx.controllers.GamePageController;
import io.github.prurite.darkchessfx.controllers.StartGamePageController;
import io.github.prurite.darkchessfx.game.PerformGame.Game;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    private Stage primaryStage;

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;
        CSSFX.start();
        Pane root = loadFXML("HomePage");
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("DarkChessFX");
        primaryStage.show();

        // TODO: Add zooming support
        // fix aspect ratio and handle zoom
        /*
        final double aspectRadio = scene.getWidth() / scene.getHeight();
        DoubleBinding heightRestriction = primaryStage.widthProperty().divide(aspectRadio).add(28);
        primaryStage.minHeightProperty().bind(heightRestriction);
        primaryStage.maxHeightProperty().bind(heightRestriction);
        */
        // letterbox(scene, root);
    }

    public Pane loadFXML(String filename) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(DarkchessFXResourcesLoader.loadURL("fxml/" + filename + ".fxml"));
            Pane pane = loader.load();
            ((DFXController)loader.getController()).setApp(this);
//            System.out.println("Loading " + filename + ".fxml");
            return pane;
        } catch (IOException e) {
//            System.err.println("Failed to load " + filename + ".fxml");
            e.printStackTrace();
            return null;
        }
    }

    public void changePage(String newPage)
    {
//        System.out.println("Changing page to " + newPage);
        Pane root = loadFXML(newPage);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }

    public void startGameFinish(Game game) {
        try {
            game.startGame();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(DarkchessFXResourcesLoader.loadURL("fxml/GamePage.fxml"));
            loader.setControllerFactory(c -> new GamePageController(game, this));
            Pane root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
//          System.err.println("Failed to load GamePage.fxml");
            e.printStackTrace();
        }
    }

    public void loadGameFinish(Game game) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(DarkchessFXResourcesLoader.loadURL("fxml/StartGamePage.fxml"));
            loader.setControllerFactory(c -> new StartGamePageController(game, this));
            Pane root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
        } catch (IOException e) {
//          System.err.println("Failed to load StartGamePage.fxml");
            e.printStackTrace();
        }
    }

    public Pane loadGameBoard(Game game, GameBoardController controller) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(DarkchessFXResourcesLoader.loadURL("fxml/GameBoard.fxml"));
            loader.setControllerFactory(c -> new GameBoardController(game, this));
            controller = loader.getController();
            return loader.load();
        } catch (IOException e) {
//            System.err.println("Failed to load " + filename + ".fxml");
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        launch();
    }

    // Zooming functions

    private void letterbox(final Scene scene, final Pane contentPane) {
        final double initWidth = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double ratio = initWidth / initHeight;

        SceneSizeChangeListener sizeListener = new SceneSizeChangeListener(scene, ratio, initHeight, initWidth, contentPane);
        scene.widthProperty().addListener(sizeListener);
        scene.heightProperty().addListener(sizeListener);
    }

    private static class SceneSizeChangeListener implements ChangeListener<Number> {
        private final Scene scene;
        private final double ratio;
        private final double initHeight;
        private final double initWidth;
        private final Pane contentPane;

        public SceneSizeChangeListener(Scene scene, double ratio, double initHeight, double initWidth, Pane contentPane) {
            this.scene = scene;
            this.ratio = ratio;
            this.initHeight = initHeight;
            this.initWidth = initWidth;
            this.contentPane = contentPane;
        }

        @Override
        public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
            final double newWidth = scene.getWidth();
            final double newHeight = scene.getHeight();

            double scaleFactor =
                    newWidth / newHeight > ratio
                            ? newHeight / initHeight
                            : newWidth / initWidth;

            if (scaleFactor >= 1) {
                Scale scale = new Scale(scaleFactor, scaleFactor);
                scale.setPivotX(0);
                scale.setPivotY(0);
                scene.getRoot().getTransforms().setAll(scale);

                contentPane.setPrefWidth(newWidth / scaleFactor);
                contentPane.setPrefHeight(newHeight / scaleFactor);
            } else {
                contentPane.setPrefWidth(Math.max(initWidth, newWidth));
                contentPane.setPrefHeight(Math.max(initHeight, newHeight));
            }
        }
    }
}