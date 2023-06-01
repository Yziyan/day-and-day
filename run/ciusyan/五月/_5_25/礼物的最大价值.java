package run.ciusyan.五月._5_25;

/**
 * https://leetcode.cn/problems/li-wu-de-zui-da-jie-zhi-lcof/
 */
public class 礼物的最大价值 {
    public int maxValue(int[][] grid) {
        if (grid == null) return 0;
        if (grid.length == 0 || grid[0].length == 0) return 0;

        int colLen = grid[0].length;
        // dp(i) 代表以 [row][i] 件物品时，礼物的最大价值
        int[] dp = new int[colLen];

        for (int row = 0; row < grid.length; row++) {

            // 初始化第一个位置。
            dp[0] += grid[row][0];
            for (int col = 1; col < colLen; col++) {
                // 最大值来自于正上方 or 正左方
                dp[col] = Math.max(dp[col], dp[col - 1]) + grid[row][col];
            }
        }

        return dp[colLen - 1];
    }
}
