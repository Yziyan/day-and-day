package run.ciusyan._5_9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 加勒比海盗问题：
 *  可以利用贪心策略，求解出部分问题的最优解
 */
public class Private {

    /**
     * 基于贪心策略的解决思路：
     *  每次选择一个最优的解，尝试得到所有可能中的最优解
     */
    public int pirate(int[] weights, int capacity) {
        if (weights == null || weights.length < 1) return 0;
        // 将其重量排序
        Arrays.sort(weights);

        // 已载的重量，能载的件数
        int weight = 0, count = 0;

        // 若已载重量已经大于等于总容量了，就别载了
        for (int i = 0; i < weights.length && weight < capacity; i++) {
            int newWeight = weight + weights[i];
            if (newWeight > capacity) continue;

            // 来到这里说明至少还可以载重一件
            count++;
            weight = newWeight;
        }

        return count;
    }
}
