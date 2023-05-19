package run.ciusyan._5_19;

/**
 * https://leetcode.cn/problems/squares-of-a-sorted-array/
 */
public class 有序数组的平方 {
    public int[] sortedSquares(int[] nums) {
        if (nums == null || nums.length == 0) return nums;

        // 准备一个结果数组
        int[] res = new int[nums.length];

        // 准备两个指针，用于比较前面和后面的绝对值
        int left = 0, right = nums.length - 1;

        // 从后往前排
        for (int i = res.length - 1; i >= 0; i--) {
            // 默认左边的平方大
            int pow2 = nums[left] * nums[left];
            // 但是得看看后面的是不是要大一些
            if (Math.abs(nums[right]) > Math.abs(nums[left])) {
                // 说明后面的平方要大一些
                pow2 = nums[right] * nums[right];
                // 往中间移动
                right--;
            } else {
                left++;
            }

            res[i] = pow2;
        }

        return res;
    }
}
