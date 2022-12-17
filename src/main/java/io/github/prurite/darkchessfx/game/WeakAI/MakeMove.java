package io.github.prurite.darkchessfx.game.WeakAI;

import io.github.prurite.darkchessfx.game.PerformGame.*;

import java.util.ArrayList;

import static java.lang.Integer.valueOf;

public class MakeMove {
    private Side mySide;

    public MakeMove(Side side) {
        mySide = side;
    }

    public Side getMySide() {
        return mySide;
    }

    public void setMySide(Side mySide) {
        this.mySide = mySide;
    }

    static int WIDTH = 4;
    static int HEIGHT = 8;
    static int[][] d = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    private boolean isChess(Piece x) { return x.getType() != Chess.Empty && x.getType() != Chess.Unknown; }
    public Move makeMove(Piece[][] chessboard) {
        ArrayList<Move> res = new ArrayList<>();
        ArrayList<Move> valid = new ArrayList<>();
        int mx = 0;
        for(int x = 0; x < WIDTH; ++x)
            for(int y=0; y<HEIGHT; ++y) {
                if(chessboard[x][y].getType() == Chess.Unknown) valid.add(new Move(x, y, -1, -1));
                if(!isChess(chessboard[x][y])) continue;
                if(chessboard[x][y].getSide() != mySide) continue;

                if(chessboard[x][y].getType() != Chess.Cannon) {
                    for(int u=0; u<4; ++u) {
                        int a, b;
                        a = x + d[u][0];
                        b = y + d[u][1];
                        // check if valid move
                        if(a < 0 || a >= WIDTH || b < 0 || b >= HEIGHT) continue;
                        if(chessboard[a][b].getType() == Chess.Empty) {
                            valid.add(new Move(x, y, a, b));
                        }
                        // check if valid capture
                        if(!isChess(chessboard[a][b])) continue;
                        if(chessboard[a][b].getSide() == mySide) continue;
                        if(chessboard[a][b].getType().CompareTo(chessboard[x][y].getType()) > 0) continue;
                        valid.add(new Move(x,y,a,b));
                        // check if optimized and update
                        if(chessboard[a][b].getType().getScore() > mx) { mx = chessboard[a][b].getType().getScore(); res.clear(); }
                        if(chessboard[a][b].getType().getScore() == mx) res.add(new Move(x, y, a, b));
                    }
                }
                else {
                    for(int u=0; u<4; ++u) {
                        int cnt = 0;
                        for(int l=1; ; ++l) {
                            int a, b;
                            a = x + d[u][0] * l;
                            b = y + d[u][1] * l;
                            // check if valid
                            if(a < 0 || a >= WIDTH || b < 0 || b >= HEIGHT) break;
                            if(chessboard[a][b].getType() != Chess.Empty) ++cnt;
                            if(cnt == 2) {
                                valid.add(new Move(x, y, a, b));
                                if(isChess(chessboard[a][b]) && chessboard[a][b].getSide() != mySide) {
                                    if(chessboard[a][b].getType().getScore() > mx) { mx = chessboard[a][b].getType().getScore(); res.clear(); }
                                    if(chessboard[a][b].getType().getScore() == mx) res.add(new Move(x, y, a, b));
                                }
                                break;
                            }
                        }
                    }
                }
            }

        if(res.size() == 0) return valid.get((int)(Math.random() * valid.size()));
        return res.get((int)(Math.random() * res.size()));
    }
}