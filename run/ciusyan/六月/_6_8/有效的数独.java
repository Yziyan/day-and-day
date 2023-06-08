package run.ciusyan.六月._6_8;

/**
 * https://leetcode.cn/problems/valid-sudoku/
 */
public class 有效的数独 {
    public boolean isValidSudoku(char[][] board) {
        if (board == null || board.length == 0) return false;
        // 准备三个数组，分别用于记录 行、列、九宫格

        // 比如 rows[2][9] -> 第 2 行有 9 这个数字了
        //  比如 area[3][1] -> 第 4 个九宫格中有 1 这个数字了
        boolean[][] rows = new boolean[9][10];
        boolean[][] cols = new boolean[9][10];
        boolean[][] area = new boolean[9][10];

        for (int row = 0; row < board.length; row++) {

            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] == '.') continue;

                // 此次到达了第几个格子？
                int ar = 3 * (row / 3) + (col / 3);

                // 将其字符转换成数字
                int num = board[row][col] - '0';

                // 判断每一个数字，所在行、所在列、所在九宫格是否有此数字，有了就说明无效
                if (rows[row][num]) return false;
                if (cols[col][num]) return false;
                if (area[ar][num]) return false;

                // 说明第一次有，那么将其设置为 true
                rows[row][num] = true;
                cols[col][num] = true;
                area[ar][num] = true;
            }
        }

        return true;
    }

}
