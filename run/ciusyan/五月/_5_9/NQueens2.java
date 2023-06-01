package run.ciusyan.五月._5_9;

/**
 * 只是单纯复习一遍，
 * https://leetcode.cn/problems/n-queens-ii/
 */
public class NQueens2 {

    /** 有多少种方案 */
    private int ways;

    public int totalNQueens(int n) {
        if (n < 1) return 0;

        // 用于记录路径
        // cols[3] = 6 代表 (3, 6) 这个坐标，能否放置皇后
        int[] cols = new int[n];
        // 从第0行开始放置皇后
        place(0, cols);

        return ways;
    }

    /** 在第 row 行放置皇后 */
    private void place(int row, int[] cols) {
        if (row == cols.length) {
            // 说明最后一行都放好了皇后，
            ways++;

            return;
        }

        // 在第 row 行，查看哪一列可以放置皇后
        for (int col = 0; col < cols.length; col++) {
            // 说明 (row, col) 这个位置不能放置皇后
            if (!isPlaced(row, col, cols)) continue;

            // 来到这里，说明可以放置
            cols[row] = col;

            // 往下一行放置
            place(row + 1, cols);
        }
    }

    /** 查看 (row, col) 这个坐标能否放置皇后 */
    private boolean isPlaced(int row, int col, int[] cols) {
        for (int i = 0; i < row; i++) {
            // 说明这一列，在前面的行已经放置过皇后了
            if (cols[i] == col) return false;

            // 说明这个位置的正对角线上有皇后，也不能放置
            if (row - i == Math.abs(col - cols[i])) return false;
        }

        return true;
    }
}
