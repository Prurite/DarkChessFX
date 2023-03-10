package io.github.prurite.darkchessfx.game.PerformGame;

public enum Chess {
    General(6, 30, 1), // 将
    Advisor(5, 10, 2), // 士
    Minister(4, 5, 2), // 相
    Chariot(3, 5, 2), // 车
    Horse(2, 5, 2), // 马
    Cannon(1, 5, 2), // 炮
    Soldier(0, 1, 5), // 兵
    Unknown(-1, 0, 0),
    Empty(-2, 0, 0);

    private int id;
    private int score;
    private int count;
    public int getId() { return id; }
    public int getScore() { return score; }
    public int getCount() { return count; }
    private Chess(int id,int score, int count) { this.id = id; this.score = score; this.count = count; }
    public int CompareTo(Chess c) {
        if(c.getId() < 0 || id < 0) return -2;
        if(c.getId() == 6 && id == 0) return 1;
        if(c.getId() == 0 && id == 6) return -1;
        int x = id - c.getId();
        return x > 0 ? 1 : (x < 0 ? -1 : 0);
    }
}