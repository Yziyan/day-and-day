package run.ciusyan.五月._5_10;

/**
 * 最大上升子序列：
 * https://leetcode.cn/problems/longest-increasing-subsequence/
 */
public class LIS {


    /**
     * 牌顶法
     *
     */
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length < 1) return 0;

        // 用于存放牌堆
        int[] tops = new int[nums.length];

        int len = 0;

        for (int num : nums) {
            // 将每一张牌，放置到合适的位置
            int begin = 0;
            int end = len;

            // 二分搜索出合适的位置
            while (begin < end) {
                int mid = (begin + end) >> 1;

                if (tops[mid] >= num) {
                    // 说明这张牌应该放到 [begin, mid)
                    end = mid;
                } else {
                    // 说明这张牌应该放到 [mid+1, end)
                    begin = mid + 1;
                }
            }

            // 将牌放到此牌堆
            tops[begin] = num;

            // 如果是最后一个牌堆，说明需要一个新的牌堆了
            if (begin == len) {
                len++;
            }
        }

        return len;
    }

    /**
     * 动态规划
     */
    public int lengthOfLIS1(int[] nums) {
        if (nums == null || nums.length < 1) return 0;

        // dp(i) 代表，以 nums[i] 结尾元素的最长上升子序列长度
        int[] dp = new int[nums.length];

        // 初始化：只有一个元素时，dp(0) = 1，代表以 nums[0] 结尾元素的LIS
        dp[0] = 1;

        // 转移方程：
        //  如果 nums[i] 比i前面某一j位置的元素大，说明 dp(i) = Max{dp(j) + 1, dp(i)}
        //  否则直接看 j 的下一个位置

        // 默认最大值是1
        int max = dp[0];
        for (int i = 1; i < nums.length; i++) {
            // 有可能前面的每一项和自己都不满足最长上升子序列，所以设置一个默认值
            dp[i] = 1;

            for (int j = 0; j < i; j++) {
                // 说明前面的元素大些，等于也不行（严格上升）
                if (nums[j] >= nums[i]) continue;

                // 来到这里，说明有参考价值
                //  dp(i) 可能在前面某一项赋值过了，
                //  dp(j) + 1 代表以 j 结尾的LIS，找到一个更长的了
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }

            max = Math.max(max, dp[i]);
        }

        return max;
    }

}
