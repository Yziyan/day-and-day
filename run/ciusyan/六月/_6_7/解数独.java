package run.ciusyan.六月._6_7;

/**
 * https://leetcode.cn/problems/sudoku-solver/
 */
public class 解数独 {

    /** 先初始化行、列、格子数字 */
    private boolean[][] rows = new boolean[9][10];
    private boolean[][] cols = new boolean[9][10];
    private boolean[][] area = new boolean[9][10];

    public void solveSudoku(char[][] board) {
        if (board == null || board.length == 0) return;
        // 先初始化几个数组
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == '.') continue;

                // 格子号
                int ar = 3 * (row / 3) + (col / 3);
                // 将其字符转换成数字
                int num = board[row][col] - '0';

                // 记录每一个字符
                rows[row][num] = true;
                cols[col][num] = true;
                area[ar][num] = true;
            }
        }

        // 去解决数独，从 (0, 0) 开始
        solve(board, 0, 0);
    }

    /** 从第 (row, col) 开始去解决数独 */
    private boolean solve(char[][]board, int row, int col) {
        // 说明求解到了第九行，已经填完了最后一个格子，可以返回了
        if (row == 9) return true;

        // 当离开了 (row, col) 下一次该去哪里？
        //  下一次去的行：如果当前列已经到第八列了，那么说明要去下一行了，否则还在本行
        //  下一次去的列：如果当前列已经到第八列了，说明要换新的一行了，回到第一列，否则去到下一列
        int nextRow = col == 8 ? row + 1 : row;
        int nextCol = col == 8 ? 0 : col + 1;

        // 说明这里有数字了，不用填写，直接去看下一个格子需不需要填写
        if (board[row][col] != '.') return solve(board, nextRow, nextCol);

        // 说明此处需要填写，尝试使用 1 ~ 9 的数字填写
        // 算出属于哪个小格子
        int ar = 3 * (row / 3) + (col / 3);
        // 所有能选择的可能
        for (int num = 1; num <= 9; num++) {
            // 先查看能否填写此数字
            if (rows[row][num] || cols[col][num] || area[ar][num]) continue;

            // 来到这里说明可以填写此数字
            board[row][col] = (char) (num + '0');

            // 并且要更新 rows、cols、area
            rows[row][num] = true;
            cols[col][num] = true;
            area[ar][num] = true;

            // 然后往下面开始填写
            if (solve(board, nextRow, nextCol)) return true;

            // 如果上面 solve 成功了，说明不回来到这里，
            //  来到这里说明当时选择的这个数字不合适，需要还原现场。
            board[row][col] = '.';
            rows[row][num] = false;
            cols[col][num] = false;
            area[ar][num] = false;
        }

        return false;
    }
}
