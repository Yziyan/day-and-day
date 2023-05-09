package run.ciusyan._5_9;

/**
 * 最大子数组之和：
 * https://leetcode.cn/problems/maximum-subarray/
 */
public class MaxSubArray {


    /**
     * 使用动态规划求解：
     *
     */
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length < 1) return 0;

        // dp(i) 表示前 i 项中的最大子数组之和

        // 初始化
        int dp = nums[0];

        int max = dp;

        for (int i = 1; i < nums.length; i++) {
            if (dp > 0) {
                // 说明之前的前 i-1 项最大子数组和对这一项有贡献
                dp += nums[i];
            } else {
                // 说明对之前的没贡献，最大就是自己这一项
                dp = nums[i];
            }

            // 找到最大的一项
            max = Math.max(max, dp);
        }

        return max;
    }

    /**
     * 分治的方法：
     *  一个序列的最大子序列之和，只可能在三个地方
     *      1、在左子序列
     *      2、在右子序列
     *      3、同时包含左和右
     *
     */
    public int maxSubArray2(int[] nums) {
        if (nums == null || nums.length < 1) return 0;

        return maxSubArray2(nums, 0, nums.length);
    }

    /**
     * 计算出 nums 中， [begin, end) 范围内的最大子数组之和
     *  [begin, end)
     */
    private int maxSubArray2(int[] nums, int begin, int end) {
        // 范围内只有一个元素
        if (end - begin < 2) return nums[begin];

        // 中间索引
        int mid = (begin + end) >> 1;
        // 一个序列的最大子序列之和，只可能在三个地方
        //  1、在左子序列
        int leftMaxSum = maxSubArray2(nums, begin, mid);
        // 2、在右子序列
        int rightMaxSum = maxSubArray2(nums, mid, end);

        // 3、在中间地带

        // 往左边加，求出能加到左边的最大值
        int addedLeftSum = nums[mid - 1];
        int addedLeftMax = addedLeftSum;
        for (int i = mid - 2; i >= begin; i--) {
            addedLeftSum += nums[i];
            addedLeftMax = Math.max(addedLeftMax, addedLeftSum);
        }

        // 往右边加，求出能加到右边的最大值
        int addedRightSum = nums[mid];
        int addedRightMax = addedRightSum;
        for (int i = mid + 1; i < end; i++) {
            addedRightSum += nums[i];
            addedRightMax = Math.max(addedRightMax, addedRightSum);
        }

        // 中间的最大值
        int centralMaxSum = addedLeftMax + addedRightMax;

        // 所有可能中的最大值，就是结果
        return Math.max(centralMaxSum, Math.max(leftMaxSum, rightMaxSum));
    }

    /**
     * 暴力出奇迹
     *  使用暴力枚法，列举出所有可能的结果
     *  从中挑选一个最大的
     */
    public int maxSubArray1(int[] nums) {
        if (nums == null || nums.length < 1) return 0;

        int max = Integer.MIN_VALUE;

        // 列出所有可能
        for (int begin = 0; begin < nums.length; begin++) {
            // [begin, end] 这个区间子数组的和
            int sum = 0;

            for (int end = begin; end < nums.length; end++) {
                // 子序列和
                sum += nums[end];

                // 最大子序列和
                max = Math.max(max, sum);
            }

        }

        return max;
    }
}
