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
        // 一行一行的构建杨辉三角
        for (int i = 1; i < numRows; i++) {
            List<Integer> res = new ArrayList<>();

            // 将第一个数设置为 1
            res.add(1);
            for (int j = 1; j < i; j++) {
                // 取出上一行的值
                List<Integer> prevRow = results.get(i - 1);

                // 将左上角和正上方的数相加，得到当前位置的结果
                res.add(prevRow.get(j - 1) + prevRow.get(j));
            }
            // 将最后一个数也设置为 1
            res.add(i, 1);

            results.add(res);
        }

        return results;
    }
}
