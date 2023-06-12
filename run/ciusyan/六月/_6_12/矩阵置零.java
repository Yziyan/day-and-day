package run.ciusyan.六月._6_12;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/set-matrix-zeroes/
 */
public class 矩阵置零 {
    public void setZeroes(int[][] matrix) {
        if (matrix == null || matrix.length == 0) return;

        // 准备两个用于记录第一行、第一列是否有 0 的变量，
        //  防止一会使用第一行和第一列记录 0 会冲突
        boolean rowZero = false;
        boolean colZero = false;

        // 遍历先记录，第一行是否有 0，
        for (int col = 0; col < matrix[0].length; col++) {
            if (matrix[0][col] == 0) {
                rowZero = true;
                break;
            }
        }

        // 遍历记录，第一列是否有 0
        for (int row = 0; row < matrix.length; row++) {
            if (matrix[row][0] == 0) {
                colZero = true;
                break;
            }
        }

        for (int row = 1; row < matrix.length; row++) {
            for (int col = 0; col < matrix[0].length; col++) {
                // 只要 (row, col) 是 0，那么就将 (row, 0) 和 (0, col) 设置为 0
                if (matrix[row][col] == 0) {
                    matrix[row][0] = 0;
                    matrix[0][col] = 0;
                }
            }
        }

        // 再检查第一行和第一列，是否需要设置为 0
        for (int col = 1; col < matrix[0].length; col++) {
            if (matrix[0][col] != 0) continue;

            // 来到这里，说明要将 col 列的设为 0
            for (int row = 1; row < matrix.length; row++) {
                matrix[row][col] = 0;
            }
        }

        // 将这两个循环分开写，可以跳过很多没必要的循环。
        for (int row = 1; row < matrix.length; row++) {
            if (matrix[row][0] != 0) continue;
            // 来到这里，说明需将 row 行的设为 0
            for (int col = 1; col < matrix[0].length; col++) {
                matrix[row][col] = 0;
            }
        }

        // 说明需要将第一行的设置为 0
        if (rowZero) Arrays.fill(matrix[0], 0);

        // 说明需要将第一列的设置为 0
        if (colZero) {
            for (int row = 0; row < matrix.length; row++) matrix[row][0] = 0;
        }
    }
}
