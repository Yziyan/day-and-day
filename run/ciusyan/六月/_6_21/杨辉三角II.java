package run.ciusyan.六月._6_21;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * https://leetcode.cn/problems/pascals-triangle-ii/
 */
public class 杨辉三角II {
    public List<Integer> getRow(int rowIndex) {
        List<Integer> results = new LinkedList<>();
        if (rowIndex < 0) return results;

        // 先添加第一行
        results.add(1);

        // 然后从第二行开始
        for (int row = 1; row <= rowIndex; row++) {
            // 最后一个位置添加 1
            results.add(row, 1);
            // 防止覆盖，从后往前计算
            for (int col = row - 1; col >= 1; col--) {
                // 将 col 位置设置的左上角和正上方相加
                results.set(col, results.get(col) + results.get(col - 1));
            }
        }

        return results;
    }
}
