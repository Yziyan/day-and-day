package run.ciusyan._5_10;

import java.util.Arrays;

/**
 * 零钱兑换：
 * https://leetcode.cn/problems/coin-change/
 */
public class CoinChange {

    /**
     * 动态规划：
     *
     */
    public int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length < 1) return -1;

        // dp(i) 代表找零 i 分硬币，所需要的最少硬币数
        int[] dp = new int[amount + 1];

        // 初始化：dp[0] = 0

        // 挨次求出找零 1、2、3... 分需要最少的硬币
        // 从而推导出 amount 分硬币需要的最少硬币数
        for (int i = 1; i <= amount; i++) {

            int min = Integer.MAX_VALUE;
            // 尝试使用每一面值找零
            for (int coin : coins) {
                // 说明此面值太大了
                if (coin > i) continue;

                // 来到这里，查看 i 分硬币，用此面值找零后的钱，
                //  是否能够筹够、是否比用上一面值找零，硬币个数还少
                //  如果 dp[i - coin] = -1，说明没办法筹够，
                //  如果 dp[i - coin] >= min，说明使用之前的零钱，会更省硬币个数
                if (dp[i - coin] < 0 || dp[i - coin] >= min) continue;
                min = dp[i - coin];
            }

            if (min == Integer.MAX_VALUE) {
                // 说明没有硬币可以找零 i 分硬币
                dp[i] = -1;
            } else {
                dp[i] = min + 1;
            }
        }

        return dp[amount];
    }


}
