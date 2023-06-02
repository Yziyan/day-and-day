package run.ciusyan.六月._6_2;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/spiral-matrix/
 */
public class 螺旋矩阵 {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) return result;

        // 准备上下左右四个指针
        int top = 0, bottom = matrix.length - 1;
        int left = 0, right = matrix[0].length - 1;

        while (top <= bottom && left <= right) {
            // 从左上 -> 右上
            for (int i = left; i <= right; i++) {
                result.add(matrix[top][i]);
            }
            // 打印完成后，往中间靠
            top++;

            // 有可能 bottom 已经不合法了
            if (bottom < top) return result;

            // 从右上 -> 右下
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);

            }
            // 打印完成后，往中间靠
            right--;

            // 有可能 left 已经不合法了
            if (left > right) return result;

            // 从右下 -> 左下
            for (int i = right; i >= left; i--) {
                result.add(matrix[bottom][i]);
            }
            // 打印完成后，往中间靠
            bottom--;

            // 从左下 -> 左上
            for (int i = bottom; i >= top; i--) {
                result.add(matrix[i][left]);
            }
            // 打印完成后，往中间靠
            left++;
        }

        return result;
    }
}
