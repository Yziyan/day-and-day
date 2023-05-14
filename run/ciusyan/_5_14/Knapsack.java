package run.ciusyan._5_14;


/**
 * 0-1背包问题：
 *  有 n 件物品和一个最大承重为 W 的背包，每件物品的重量是 wi、价值是 vi
 *  在保证总重量补充搞过 W 的前提下，选择某件物品装进背包，背包的最大总价值是多少？
 *  注意：每件物品只有 1 件。
 */
public class Knapsack {


    public int select(int[] weights, int[] values, int capacity) {
        if (weights == null || weights.length == 0) return 0;
        if (values == null || values.length == 0) return 0;
        if (values.length != weights.length || capacity < 1) return 0;

        // 直接使用一维数组即可，因为一直都只用到了上一行的结果
        int[] dp = new int[weights.length + 1];

        for (int i = 1; i <= weights.length; i++) {

            // 需要逆序求解，因为用到上一行中的谁，是不确定的
            //  但是当第 i 件物品的重量比 j 还小时，说明 j-- 之后，也会比第 i 件物品的重量还小
            //  所以还保留之前的值即可。
            for (int j = capacity; j >= weights[i - 1]; j--) {
                // 能来到这里面，说明可以选择第 i 件物品
                //  值不值呢？
                //      dp[j]：代表不选这一件的最大价值，
                //      dp[j - weights[i - 1]] + values[i - 1]：代表选择这一件的价值
                dp[j] = Math.max(dp[j], dp[j - weights[i - 1]] + values[i - 1]);
            }
        }

        return dp[capacity];
    }

    /**
     * 动态规划：
     * @param weights：w[0] 是第一件物品的重量
     * @param values：v[0] 是第一件物品的价值
     * @param capacity：背包的容量
     * @return 最大价值
     */
    public int select1(int[] weights, int[] values, int capacity) {
        if (capacity <= 0 || weights == null || values == null) return 0;
        if (weights.length == 0 || values.length == 0 || weights.length != values.length) return 0;

        // dp(i, j) 表示最大承重为 j 时，有前 i 件物品的最大价值
        int[][] dp = new int[weights.length + 1][capacity + 1];
        // 对于每一件物品，有两种情况：
        //  1、选了会超重，不能选择，价值就是以前物品的重量
        //  2、能选择，但是需要看选了值不值。也就是虽然能选，但是也可能不会选择它

        for (int i = 1; i <= weights.length; i++) {
            for (int j = 1; j <= capacity; j++) {
                if (j < weights[i - 1]) {
                    // 说明当前剩余载重为 j，装不下最后一件物品了
                    // 那么只能看，剩余载重为 j 时，有 i - 1件物品的时候了
                    dp[i][j] = dp[i - 1][j];
                } else {
                    // 说明能装，那么值不值呢？
                    //  不装：最大价值为，载重量为j，选择前 i - 1 件物品的价值
                    //  装： 最大值价值为，载重量为 j - weights[i]，有前 i - 1件物品的的最大价值 + 当前物品的最大价值
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weights[i]] + values[i - 1]);
                }
            }
        }

        return dp[weights.length][capacity];
    }

}
