package io.github.prurite.darkchessfx.game;

import io.github.prurite.darkchessfx.game.PerformGame.PlayerInGame;
import io.github.prurite.darkchessfx.game.UserData.Player;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import io.github.prurite.darkchessfx.game.PerformGame.Game;
import javafx.scene.layout.*;

import javax.security.auth.callback.UnsupportedCallbackException;

public class ClickOnChessController implements Initializable {
    private Game game;
    private ArrayList<Player> players;
    private Button buttons[][] = new Button[4][8];
    private int lastx, lasty;
    private PlayerInGame[] p;
    private int cur;
    //GridPane grid = new GridPane();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //grid = new GridPane();
        players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));

        game = new Game();
        p = game.startGame(players.get(0), players.get(1));
        ImageView img = new ImageView(new Image("file:src/main/resources/img/Unknown.png"));
        img.setFitHeight(79);
        img.setFitWidth(79);
        //Background bk = new Background(new BackgroundImage(new Image("file:src/main/resources/img/Unknown.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(79, 79, false, false, true, false)));

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                buttons[i][j] = new Button();
                //buttons[i][j].setPrefHeight(79);
                buttons[i][j].setMinHeight(79);
                buttons[i][j].setMinWidth(79);
                //grid.add(img, j, i);
                //buttons[i][j].setPrefWidth(79);

                //buttons[i][j].setBackground(bk);
                buttons[i][j].setText("");
                buttons[i][j].setDefaultButton(false);
                final int x = i;
                final int y = j;
                buttons[i][j].setOnAction(e -> {
                    lastx = x; lasty = y;
                    game.clickOnChess(p[cur], x, y);

                    //buttons[x][y].setBackground(new Background(new BackgroundImage(new Image("file:src/main/resources/img/" + game.qryPiece(x, y)), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(79, 79, false, false, true, false))));

                    //buttons[lastx][lasty].setBackground(new Background(new BackgroundImage(new Image("file:src/main/resources/img/" + game.qryPiece(lastx, lasty)), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(79, 79, false, false, true, false))));

                });
            }
        }
    }


}
