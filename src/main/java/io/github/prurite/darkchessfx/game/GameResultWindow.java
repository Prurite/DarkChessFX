package io.github.prurite.darkchessfx.game;

import io.github.prurite.darkchessfx.game.PerformGame.PlayerInGame;
import io.github.prurite.darkchessfx.game.PerformGame.Side;
import io.github.prurite.darkchessfx.model.Player;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameResultWindow extends Application {

    public static void showResult(PlayerInGame p) {
        Stage stage = new Stage();
        Text text = new Text("");
        if(p == null) {
            text.setText("Draw");
        } else {
            text.setText("the winner is " + p.getPlayer().getName());
        }
        text.setFont(new Font(20));
        stage.setScene(new Scene(new VBox(text), 400, 100));
        stage.show();
    }

    public void start(Stage stage) {
        showResult(new PlayerInGame(new Player("a"), Side.BLACK));
    }
}
