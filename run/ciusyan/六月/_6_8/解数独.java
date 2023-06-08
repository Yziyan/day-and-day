package run.ciusyan.六月._6_8;

/**
 * https://leetcode.cn/problems/sudoku-solver/
 */
public class 解数独 {

    /** 先初始化行、列、桶数组 */
    private boolean[][] rows = new boolean[9][10];
    private boolean[][] cols = new boolean[9][10];
    private boolean[][] buckets = new boolean[9][10];

    public void solveSudoku(char[][] board) {
        if (board == null || board.length == 0) return;
        // 先初始化几个数组
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == '.') continue;

                // 当前属于哪一个桶号
                int curBucket = 3 * (row / 3) + (col / 3);
                // 将其数字字符转换成数字
                int num = board[row][col] - '0';

                // 记录在当前行、当前列、当前桶中出现了此字符
                rows[row][num] = true;
                cols[col][num] = true;
                buckets[curBucket][num] = true;
            }
        }

        // 去解决数独，从 (0, 0) 开始
        solve(board, 0, 0);
    }

    /** 尝试填写在 (row, col) 位置填写数字，返回 true 代表填写在此位置的字符合理 */
    private boolean solve(char[][]board, int row, int col) {
        // 说明求解到了第九行，已经填完了最后一个格子，可以返回了
        if (row == 9) return true;

        // 当离开了 (row, col) 要去下一个格子了，但是下一个格子在哪里？下一个格子也是行和列确定的
        //  下一次去的行：如果当前已经到第八列了，那么说明此行填完了，要去下一行了，否则还在本行
        //  下一次去的列：如果当前已经到第八列了，那么说明会到下一行，就需要回到第一列，否则去到下一列
        //      那么下一次要去的格子就是：(nextRow, nextCol)
        int nextRow = col == 8 ? row + 1 : row;
        int nextCol = col == 8 ? 0 : col + 1;

        // 说明这里有数字了，不用填写，直接尝试填写下一个格子
        if (board[row][col] != '.') return solve(board, nextRow, nextCol);

        // 说明此处需要填写，尝试使用 1 ~ 9 的数字填写

        // 先算出属于哪个桶
        int curBucket = 3 * (row / 3) + (col / 3);

        for (int num = 1; num <= 9; num++) { // 只能填 1~9 的数字
            // 用上面初始化的三个数组，查看能否填写此数字
            if (rows[row][num] || cols[col][num] || buckets[curBucket][num]) continue;

            // 来到这里说明可以填写此数字，将此数字转换成数字字符
            board[row][col] = (char) (num + '0');

            // 并且要更新 rows、cols、buckets，因为你在这个地方插入了新的 num
            rows[row][num] = true;
            cols[col][num] = true;
            buckets[curBucket][num] = true;


            // 然后往下面开始填写
            if (solve(board, nextRow, nextCol)) return true;
            // 如果上面 solve 成功了，则不会来到这里

            //  来到这里说明当时选择的这个数字不合适，需要先还原现场，然后重新用下一个数字尝试。
            board[row][col] = '.';
            rows[row][num] = false;
            cols[col][num] = false;
            buckets[curBucket][num] = false;
        }

        return false;
    }
}
