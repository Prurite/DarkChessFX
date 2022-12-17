package io.github.prurite.darkchessfx.game;
import io.github.prurite.darkchessfx.game.PerformGame.Game;
import io.github.prurite.darkchessfx.game.PerformGame.Move;
import io.github.prurite.darkchessfx.game.PerformGame.PlayerInGame;
import io.github.prurite.darkchessfx.game.UserData.Player;

import io.github.prurite.darkchessfx.game.WeakAI.MakeMove;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {
    Game game;
    ArrayList<Player> players;
    PlayerInGame[] p;
    int cur;
    int lastx, lasty;

    static private void UPD(Button[][] buttons, Game game, int x,int y) {
        buttons[x][y].setBackground(new Background(new BackgroundImage(new Image("file:src/main/resources/img/" + game.qryPiece(x, y)), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(79, 79, false, false, true, false))));
    }
    private void startLocalGame(Stage stage) {
        game = new Game();
        /*PlayerInGame[] tmp =*/ p = game.startGame(players.get(0), players.get(1));
        //p = new PlayerInGame[2];
        //System.arraycopy(tmp, 0, p, 0, 2);
        cur = 0;

        GridPane grid = new GridPane();
        Button buttons[][] = new Button[4][8];
        Background bk = new Background(new BackgroundImage(new Image("file:src/main/resources/img/Unknown.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(79, 79, false, false, true, false)));
        Label label = new Label("let's play!");
        Label playeris = new Label("player " + game.qryCur().getPlayer().getName() + " is playing");
        Label youAre = new Label("you are" + p[cur].getPlayer().getName());
        Label explainSide = new Label("");
        Label score = new Label("score is " + p[0].getScore() + " : " + p[1].getScore());
        for(int i=0; i<4; ++i)
            for(int j=0; j<8; ++j) {
                buttons[i][j] = new Button();
                buttons[i][j].setMinWidth(79);
                buttons[i][j].setMinHeight(79);
                buttons[i][j].setBackground(bk);
                grid.add(buttons[i][j], j, i);
                final int x = i, y = j;
                buttons[i][j].setOnAction(e -> {
                    boolean firstmove = game.firstMoveFlag;
                    label.setText(game.clickOnChess(p[cur], x, y));
                    UPD(buttons, game, x, y);
                    UPD(buttons, game, lastx, lasty);
                    lastx = x; lasty = y;
                    playeris.setText("player " + game.qryCur().getPlayer().getName() + " is playing");
                    if(firstmove == true) {
                        explainSide.setText(p[0].getPlayer().getName() + " is " + p[0].getSide() + ", " + p[1].getPlayer().getName() + " is " + p[1].getSide());
                    }

                    if(game.checkEndGame()) {
                        game.endGame();
                    }

                    // set cur as qryCur, this is for local game only
                    PlayerInGame t = game.qryCur();
                    youAre.setText("you are" + t.getPlayer().getName());

                    score.setText("score is " + p[0].getScore() + " : " + p[1].getScore());


                });
            }
        Button changePlayer = new Button("Change Player");
        changePlayer.setOnAction(e -> {
            cur = 1 - cur;
            youAre.setText("you are" + p[cur].getPlayer().getName());
        });

        VBox root = new VBox(grid, changePlayer, label, playeris, youAre, explainSide, score);
        Scene scene = new Scene(root, 800, 1500);
        stage.setScene(scene);
        stage.show();

    }
    private void startAIGame(Stage stage) {
        game = new Game();
        /*PlayerInGame[] tmp =*/ p = game.startGame(players.get(0), players.get(1));
        //p = new PlayerInGame[2];
        //System.arraycopy(tmp, 0, p, 0, 2);
        cur = 0; // you are p[0]
        MakeMove AI = new MakeMove(p[cur^1].getSide());

        GridPane grid = new GridPane();
        Button buttons[][] = new Button[4][8];
        Background bk = new Background(new BackgroundImage(new Image("file:src/main/resources/img/Unknown.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(79, 79, false, false, true, false)));
        Label label = new Label("let's play!");
        Label playeris = new Label("player " + game.qryCur().getPlayer().getName() + " is playing");
        Label youAre = new Label("you are" + p[cur].getPlayer().getName());
        Label explainSide = new Label("");
        Label score = new Label("score is " + p[0].getScore() + " : " + p[1].getScore());
        Label eaten = new Label("");
        //Label debug = new Label("");
        for(int i=0; i<4; ++i)
            for(int j=0; j<8; ++j) {
                buttons[i][j] = new Button();
                buttons[i][j].setMinWidth(79);
                buttons[i][j].setMinHeight(79);
                buttons[i][j].setBackground(bk);
                grid.add(buttons[i][j], j, i);
                int x = i, y = j;


                buttons[i][j].setOnAction(e -> {
                    boolean firstmove = game.firstMoveFlag;
                    label.setText(game.clickOnChess(p[cur], x, y));
                    UPD(buttons, game, x, y);
                    UPD(buttons, game, lastx, lasty);
                    lastx = x; lasty = y;
                    playeris.setText("player " + game.qryCur().getPlayer().getName() + " is playing");
                    if(firstmove == false) {
                        explainSide.setText(p[0].getPlayer().getName() + " is " + p[0].getSide() + ", " + p[1].getPlayer().getName() + " is " + p[1].getSide());
                        AI.setMySide(p[cur^1].getSide());
                    }

                    //debug.setText(game.debugChessboard());

                    if(game.checkEndGame()) {
                        game.endGame();
                    }
                    assert(cur == 0);
                    if(!p[cur].equals(game.qryCur())) {
                        //label.setText(p[cur].getPlayer().getName() + game.qryCur().getPlayer().getName());
                        Move tmp = AI.makeMove(game.getChessboard());
                        //label.setText(tmp.getCurx() + " " + tmp.getCury() + " " + tmp.getNewx() + " " + tmp.getNewy());
                        int x1 = tmp.getCurx(), y1 = tmp.getCury();
                        game.clickOnChess(p[cur^1], x1, y1);
                        int x2 = tmp.getNewx(), y2 = tmp.getNewy();
                        if(y2 != -1) {
                            game.clickOnChess(p[cur^1], x2, y2);
                            UPD(buttons, game, x2, y2);
                        }
                        UPD(buttons, game, x1, y1);
                        assert(game.qryCur().equals(p[cur]));


                        //debug.setText(game.debugChessboard());
                    }
                    playeris.setText("player " + game.qryCur().getPlayer().getName() + " is playing");
                    score.setText("score is " + p[0].getScore() + " : " + p[1].getScore());
                    eaten.setText(game.getEatenPieces().toString());
                });
            }
        Button changePlayer = new Button("Change Player");
        changePlayer.setOnAction(e -> {
            cur = 1 - cur;
            youAre.setText("you are" + p[cur].getPlayer().getName());
        });

        Button withdraw = new Button ("Withdraw");
        withdraw.setOnAction(e -> {
            Move t = game.withdraw();
            label.setText("withdraw " + t.getCurx() + " " + t.getCury() + " " + t.getNewx() + " " + t.getNewy());
            playeris.setText("player " + game.qryCur().getPlayer().getName() + " is playing");
            score.setText("score is " + p[0].getScore() + " : " + p[1].getScore());
            eaten.setText(game.getEatenPieces().toString());
            //assert t.getCury() != -1 && t.getCurx() != -1;
            UPD(buttons, game, t.getCurx(), t.getCury());
            if(t.getNewx() != -1) UPD(buttons, game, t.getNewx(), t.getNewy());

        });

        VBox root = new VBox(grid, changePlayer, withdraw, label, playeris, youAre, explainSide, score/*, debug*/, eaten);
        Scene scene = new Scene(root, 800, 1500);
        stage.setScene(scene);
        stage.show();

    }
    private void createPlayers() {
        players = new ArrayList<>();
        players.add(new Player("Player1"));
        players.add(new Player("Player2"));
    }


    @Override
    public void start(Stage stage) throws IOException {
        createPlayers();
        //startLocalGame(stage);
        startAIGame(stage);



//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}