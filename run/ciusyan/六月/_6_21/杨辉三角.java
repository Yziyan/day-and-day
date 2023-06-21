package run.ciusyan.六月._6_21;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/pascals-triangle/
 */
public class 杨辉三角 {

    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> results = new ArrayList<>();
        if (numRows <= 0) return results;

        // 先将第一行构建了
        results.add(List.of(1));
        // 再从第二行开始，一行一行的构建杨辉三角
        for (int row = 1; row < numRows; row++) {
            List<Integer> res = new ArrayList<>();

            // 将第一个数设置为 1
            res.add(1);
            for (int col = 1; col < row; col++) {
                // 取出上一行的值
                List<Integer> prevRow = results.get(row - 1);

                // 将左上角和正上方的数相加，得到当前位置的结果
                res.add(prevRow.get(col - 1) + prevRow.get(col));
            }
            // 将最后一个数也设置为 1
            res.add(row, 1);

            results.add(res);
        }

        return results;
    }
}
