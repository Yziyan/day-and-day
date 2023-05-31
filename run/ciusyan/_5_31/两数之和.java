package run.ciusyan._5_31;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.cn/problems/two-sum/
 */
public class 两数之和 {
    public int[] twoSum(int[] nums, int target) {
        if (nums == null || nums.length < 2) return new int[0];

        // 用于记录遍历的索引 <值, 索引>
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {

            // 尝试获取前面的索引，
            Integer prev = map.get(target - nums[i]);

            // 如果获取到了说明找到了 nums[i] + nums[prev] = target
            if (prev != null) return new int[] {prev, i};

            // 来到这里说明没找到，将当前值存储在 map 中，供之后使用
            map.put(nums[i], i);
        }

        return new int[0];
    }
}
