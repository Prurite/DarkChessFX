package io.github.prurite.darkchessfx;

import fr.brouillard.oss.cssfx.CSSFX;
import io.github.prurite.darkchessfx.controllers.*;
import io.github.prurite.darkchessfx.game.PerformGame.Game;
import io.github.prurite.darkchessfx.model.PlayerInfoProcessor;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class App extends Application {
    private Stage primaryStage;
    private Scene scene;
    private String colorScheme;
    private PlayerInfoProcessor playerList;

    public Stage getPrimaryStage() {
        return primaryStage;
    }
    public Scene getScene() {
        return scene;
    }

    public PlayerInfoProcessor getPlayerList() {
        return playerList;
    }

    @Override
    public void start(Stage primaryStage) {
        playerList = new PlayerInfoProcessor();
        File f = new File("playerInfo.txt");
        try {
            f.createNewFile();
            playerList.readFromFile(f);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error reading player info file");
            alert.setContentText(e.toString());
            alert.showAndWait();
        }
        this.primaryStage = primaryStage;
        CSSFX.start();
        colorScheme = "LTCatBlue";
        Pane root = loadFXML("HomePage");
        scene = new Scene(root);
        scene.getStylesheets().add(DarkchessFXResourcesLoader.loadURL("css/" + colorScheme + ".css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("DarkChessFX");
        primaryStage.show();

        // letterbox(scene); // handle zooming
    }

    public String getColorScheme() {
        return colorScheme;
    }

    public void setColorScheme(String colorScheme) {
        try {
            scene.getStylesheets().clear();
            this.colorScheme = colorScheme;
            scene.getStylesheets().add(DarkchessFXResourcesLoader.loadURL("css/" + this.colorScheme + ".css").toExternalForm());
            System.out.println("Color scheme changed to " + colorScheme);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public Pane loadFXML(String filename) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(DarkchessFXResourcesLoader.loadURL("fxml/" + filename + ".fxml"));
            Pane pane = loader.load();
            ((DFXSimpleController)loader.getController()).setApp(this);
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
        scene.setRoot(root);
    }

    public void startGameFinish(Game game) {
        try {
            if (game.getGameConfig().lanPort == 0) {
                game.startGame();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(DarkchessFXResourcesLoader.loadURL("fxml/GamePage.fxml"));
                loader.setControllerFactory(c -> new GamePageController(game, this));
                Pane root = loader.load();
                scene.setRoot(root);
            } else {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(DarkchessFXResourcesLoader.loadURL("fxml/LANWaitPage.fxml"));
                loader.setControllerFactory(c -> new LANWaitPageController(game, this));
                Pane root = loader.load();
                scene.setRoot(root);
            }
        } catch (IOException e) {
//          System.err.println("Failed to load GamePage.fxml");
            e.printStackTrace();
        }
    }

    public void loadGameFinish(Game game) {
        try {
            FXMLLoader loader = new FXMLLoader();
            if (!game.getGameConfig().isReplay) {
                loader.setLocation(DarkchessFXResourcesLoader.loadURL("fxml/StartGamePage.fxml"));
                loader.setControllerFactory(c -> new StartGamePageSimpleController(game, this));
            } else {
                game.startGame();
                loader.setLocation(DarkchessFXResourcesLoader.loadURL("fxml/GamePage.fxml"));
                loader.setControllerFactory(c -> new GamePageController(game, this));
            }
            Pane root = loader.load();
            scene.setRoot(root);
        } catch (IOException e) {
//          System.err.println("Failed to load StartGamePage.fxml");
            e.printStackTrace();
        }
    }

    public FXMLLoader getGameBoardLoader(Game game) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DarkchessFXResourcesLoader.loadURL("fxml/GameBoard.fxml"));
        loader.setControllerFactory(c -> new GameBoardController(game, this));
        return loader;
    }

    public static void main(String[] args) {
        launch();
    }

    // Zooming functions

    // A function that listens the stage size change, locks its ratio and changes font-size accordingly
    private void letterbox(Scene scene) {
        final double initWidth = scene.getWidth();
        final double initHeight = scene.getHeight();
        final double initFontSize = 10;

        ChangeListener<Number> stageSizeListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double factor = Math.min(scene.getWidth() / initWidth, scene.getHeight() / initHeight);
                Scale scale = new Scale(factor, factor);
                scale.setPivotX(0);
                scale.setPivotY(0);
                scene.getRoot().getTransforms().setAll(scale);
                scene.getRoot().setStyle("-fx-font-size: " + initFontSize * factor + "px;");
            }
        };
        scene.widthProperty().addListener(stageSizeListener);
        scene.heightProperty().addListener(stageSizeListener);
    }
}