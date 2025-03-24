package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author yucheng_xiang
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed = false;

        // 设置视角 - 这样我们只需要实现向上移动的逻辑
        board.setViewingPerspective(side);

        // 处理每一列
        for (int col = 0; col < board.size(); col++) {
            // 从上到下处理每一列，将所有方块上移
            changed |= tiltColumn(col);
        }

        // 恢复原始视角
        board.setViewingPerspective(Side.NORTH);

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /**
     * 处理单个列的上移操作
     * @param col 要处理的列
     * @return 如果列发生了改变，返回true
     */
    private boolean tiltColumn(int col) {
        boolean changed = false;
        boolean[] merged = new boolean[board.size()]; // 跟踪方块是否已合并

        // 从倒数第二行开始向上处理（最顶行不需要移动）
        for (int row = board.size() - 2; row >= 0; row--) {
            // 跳过空格子
            if (board.tile(col, row) == null) {
                continue;
            }

            // 获取当前方块
            Tile tile = board.tile(col, row);
            int value = tile.value();

            // 找到方块应该移动到的位置
            int targetRow = row;

            // 尝试往上移动
            for (int r = row + 1; r < board.size(); r++) {
                if (board.tile(col, r) == null) {
                    // 空格子，可以移动
                    targetRow = r;
                } else if (!merged[r] && board.tile(col, r).value() == value) {
                    // 找到相同值的方块且未被合并过，可以合并
                    targetRow = r;
                    merged[r] = true; // 标记为已合并，防止连续合并
                    break;
                } else {
                    // 遇到不能合并的非空格子，停止向上移动
                    break;
                }
            }

            // 如果找到新位置，移动方块
            if (targetRow != row) {
                boolean mergeHappened = (board.tile(col, targetRow) != null);

                // 移动方块，返回是否成功移动
                boolean moveSucceeded = board.move(col, targetRow, tile);

                // 如果移动成功并且发生了合并，更新分数
                if (moveSucceeded && mergeHappened) {
                    // 合并后的方块值是原始值的两倍
                    score += value * 2;
                }

                changed = true;
            }
        }

        return changed;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                if (b.tile(i, j) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                if (b.tile(i, j) == null){
                    continue;
                }

                if (b.tile(i, j).value() == MAX_PIECE) {
                    return true;
                }
            }

        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        // First check if there are any empty spaces
        if (emptySpaceExists(b)) {
            return true;
        }

        // Check all adjacent tiles for possible merges
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                int currentValue = b.tile(i, j).value();

                // Check right neighbor
                if (j < b.size() - 1 && currentValue == b.tile(i, j + 1).value()) {
                    return true;
                }
                // Check bottom neighbor
                if (i > 0 && currentValue == b.tile(i - 1, j).value()) {
                    return true;
                }
            }
        }

        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model's string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
