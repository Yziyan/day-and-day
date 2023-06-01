package run.ciusyan.五月._5_9;

import java.util.ArrayList;
import java.util.List;

/**
 * N皇后问题：
 * https://leetcode.cn/problems/n-queens/submissions/
 *  这是一道著名的可以利用DFS种的一些思想的题目，主要是利用到了回溯和剪枝
 *      回溯：如果一条路径不能往下走了，那么就会回溯到上一个路口，然后重新寻找一条路径
 *      剪枝：提前排除一些下一步可走，但是最终不可能到达的路径
 */
public class NQueens {

    public List<List<String>> solveNQueens(int n) {
        List<List<String>> result = new ArrayList<>();
        if (n < 1) return result;

        // 使用数组记录可行的路径 cols[3] = 6; 代表 第三行 第六列 有一个皇后
        int[] cols = new int[n];
        place(0, cols, result);

        return result;
    }

    /**
     * 从第 row 行，开始放置皇后
     */
    private void place(int row, int[] cols, List<List<String>> result) {
        // 说明每一行都摆好了
        if (row == cols.length) {
            // 记录结果
            record(result, cols);

            return;
        }

        // 在第 row 行，逐一查看能否放置皇后
        for (int col = 0; col < cols.length; col++) {
            if (!isPlaced(row, col, cols)) continue;

            // 来到这里，说明 (row, col) 坐标位置可以防止皇后
            cols[row] = col;

            // 在此行放置完成后，往下面一行继续放置
            place(row + 1, cols, result);
        }

    }

    /**
     * 检查 (row, col) 是否能够放置皇后（这就是剪枝的操作）
     */
    private boolean isPlaced(int row, int col, int[] cols) {
        // 如何判断这个坐标是否能放皇后呢？
        //  其实就是看相同的 row、col、还有这个坐标的斜线上。是否已经有皇后了
        //  而我们是逐行放置皇后，所以 相同的 row 不需要检查了

        for (int i = 0; i < row; i++) {
            // 这里说明前面的 i 行中，已经有放置到这一列的了
            if (cols[i] == col) return false;

            // 判断是否在一条斜线上，本质上就是看 A (i, cols[i]) B (row, col) 两点构成的直线斜率是否为 +-1
            //  如果是，那么就说明是在45°角上，则不能放置
            //  k = (col - cols[i]) / (row - i) = +-1 => col - cols[i] = +-(row - i)
            //  又因为 row - i，肯定不会是负数 => row - i = +-(col - cols[i])
            if (row - i == Math.abs(col - cols[i])) return false;
        }

        return true;
    }

    /**
     * 记录结果
     */
    private void record(List<List<String>> result, int[] cols) {
        List<String> oneRes = new ArrayList<>();
        // 利用 cols ，给结果赋值
        for (int row = 0; row < cols.length; row++) {
            // 每一行的皇后情况
            StringBuilder sb = new StringBuilder();

            // 遍历每一列
            for (int col = 0; col < cols.length; col++) {
                if (cols[row] == col) {
                    // 代表 row 行 col 列有一个皇后
                    sb.append("Q");
                } else {
                    // 代表这一位置没有皇后
                    sb.append(".");
                }
            }

            oneRes.add(sb.toString());
        }

        result.add(oneRes);
    }

    public static void main(String[] args) {
        NQueens nQueens = new NQueens();
        final List<List<String>> lists = nQueens.solveNQueens(4);
        System.out.println(lists);
    }
}
