package run.ciusyan.五月._5_30;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/permutations-ii/
 */
public class 全排列II {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) return result;

        // 从第 0 层开始搜索
        dfs(0, nums, result);

        return result;
    }

    /** 搜索第 level 层 */
    private void dfs(int level, int[] nums, List<List<Integer>> results) {
        if (level == nums.length) {
            // 记录结果
            List<Integer> result = new ArrayList<>();
            for (int num : nums) {
                result.add(num);
            }

            results.add(result);

            return;
        }

        for (int i = level; i < nums.length; i++) {
            if (isRepeat(nums, level, i)) continue;

            // 交换后面的元素
            swap(nums, level, i);

            // 往下面钻
            dfs(level + 1, nums, results);

            // 还原现场
            swap(nums, i, level);
        }
    }

    /** 交换 i1 和 i2 位置的元素 */
    private void swap(int[] nums, int i1, int i2) {
        int temp = nums[i1];
        nums[i1] = nums[i2];
        nums[i2] = temp;
    }

    /** 检查 willSwap 内是否有重复的元素 */
    private boolean isRepeat(int[] nums, int level, int willSwap) {
        for (int i = level; i < willSwap; i++) {
            if (nums[willSwap] == nums[i]) return true;
        }

        return false;
    }

}
