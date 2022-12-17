package io.github.prurite.darkchessfx.game.PerformGame;

public enum MoveChessMessage {
    MovePausing("You can't move chess when game is pausing"),
    NotCurrentTurnPlayer("It is not your turn."),
    FirstClickError("Please click on a chess of your own color or a faced-down chess."),
    NeedSecondClick("Now click where you'd like to move this chess to"),
    CannonToEmpty("Cannon can only move unless it is a capturing action."),
    CannonDiagonal("Cannon can only move to square on the same row or same column."),
    CannonNotOneIntermediate("Cannon can only move over exactly one intermediate."),
    ChessToSamePosition("Chess should be moved to a different position"),
    MoveNotAdjacent("You can only move this piece one square up, down, left, or right."),
    CaptureHighRank("You can't capture piece with higher rank."),
    CaptureSameSide("You can't capture piece with same side."),
    CaptureUnknown("You can't capture a faced-down chess with this piece.");

    private String info;
    private MoveChessMessage(String info) { this.info = info; }
    public String getInfo() { return info; }
}
