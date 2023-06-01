package run.ciusyan.五月._5_26;

/**
 * https://leetcode.cn/problems/best-time-to-buy-and-sell-stock/
 */
public class 买卖股票的最佳时机 {


    /**
     * 动态规划
     *  任意两天之间的利润，等于它们之间相邻元素的和，可以转换为动态规划求解
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) return 0;

        // 表示以第 i 天卖出的最大价值
        int dp = prices[1] - prices[0];
        // 最大利润
        int max = Math.max(dp, 0);

        for (int i = 2; i < prices.length; i++) {
            // 先计算出今天的利润
            int todayProfile = prices[i] - prices[i - 1];

            if (dp <= 0) {
                // 说明之前的每一天都不挣钱，抛弃掉，从今天开始定一个小目标，整他一个亿
                dp = todayProfile;

            } else {
                // 说明之前挣钱了
                dp += todayProfile;
            }

            // 有可能有更大的利润产生
            max = Math.max(max, dp);
        }

        return max;
    }

    /**
     * 直接求解
     *
     */
    public int maxProfit1(int[] prices) {
        if (prices == null || prices.length == 0) return 0;

        // 最大价值
        int max = 0;
        // 前面的最小值
        int min = prices[0];

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > min) {
                // 说明可以尝试计算价值
                max = Math.max(max, prices[i] - min);
            } else {
                // 说明不可能产生利润
                min = prices[i];
            }
        }

        return max;
    }

}
