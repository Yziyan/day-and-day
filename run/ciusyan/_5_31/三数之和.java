package run.ciusyan._5_31;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.cn/problems/3sum/
 */
public class 三数之和 {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        if (nums == null || nums.length < 3) return results;

        // 对数组排序
        Arrays.sort(nums);

        // 最后一个索引
        int last = nums.length - 1;
        for (int i = 0; i <= last; i++) {

            // 如果当前元素都 大于 0 了，后面肯定没有值了
            if (nums[i] > 0) break;

            // 在循环前，需要检查此元素是否和前一个元素相等，相等的话可以直接跳过，因为之前全部比较过了
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            // 每一轮循环，都有三个指针
            int left = i + 1, right = last;

            // 0 - nums[i]
            int delta = -nums[i];

            while (left < right) {
                int lrSum = nums[left] + nums[right];

                if (lrSum == delta) {
                    // 说明这是一个结果
                    results.add(List.of(nums[i], nums[left], nums[right]));

                    // 防止出现重复的结果，将左右两边相同的值跳过
                    while (left < right && nums[right] == nums[--right]);
                    while (left < right && nums[left] == nums[++left]);


                    continue;
                }

                // 哪边大，就将哪边往中间移动
                if (lrSum > delta) {
                    // 说明右边太大了，
                    right--;
                } else {
                    // 说明左边太小了，
                    left++;
                }

            }
        }

        return results;
    }
}