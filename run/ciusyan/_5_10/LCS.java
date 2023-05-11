package run.ciusyan._5_10;

/**
 * 最长公共子序列：
 * https://leetcode.cn/problems/longest-common-subsequence/
 *
 */
public class LCS {

    /**
     * 动态规划：一维数组
     */
    public int longestCommonSubsequence(String text1, String text2) {
        if (text1 == null || text2 == null) return 0;
        char[] chars1 = text1.toCharArray();
        if (chars1.length == 0) return 0;
        char[] chars2 = text2.toCharArray();
        if (chars2.length == 0) return 0;

        char[] rowChars = chars1;
        char[] colChars = chars2;

        // 让列变更少，那么每一行计算就更快
        if (colChars.length > rowChars.length) {
            colChars = chars1;
            rowChars = chars2;
        }

        // 每次计算都只用到了上一行和自己这一行，那么完全可以使用一维数组
        int[] dp = new int[colChars.length + 1];

        for (int row = 1; row <= rowChars.length; row++) {

            int cur = dp[0];
            for (int col = 1; col <= colChars.length; col++) {
                // 先保存左上角的值
                int leftTop = cur;
                cur = dp[col];

                if (rowChars[row - 1] == colChars[col - 1]) {
                    // 说明和右上角有关系
                    dp[col] = leftTop + 1;
                } else {
                    // 说明答案在正上方或正左方
                    dp[col] = Math.max(dp[col], dp[col - 1]);
                }
            }
        }

        return dp[colChars.length];
    }

    /**
     * 动态规划：二维数组
     */
    public int longestCommonSubsequence1(String text1, String text2) {
        if (text1 == null || text2 == null) return 0;
        char[] chars1 = text1.toCharArray();
        if (chars1.length == 0) return 0;
        char[] chars2 = text2.toCharArray();
        if (chars2.length == 0) return 0;

        char[] rowChars = chars1;
        char[] colChars = chars2;

        if (colChars.length > rowChars.length) {
            // 说明有更多的列，交换将列变少
            colChars = chars1;
            rowChars = chars2;
        }

        // dp(i, j) 指的是，row[i] 字符结尾的序列，和col[j] 字符结尾的序列的LCS
        int[][] dp = new int[rowChars.length + 1][colChars.length + 1];

        // 初始化
        // dp(0, 0) = dp(i, 0) = dp(o, j) = 0

        for (int row = 1; row <= rowChars.length; row++) {
            for (int col = 1; col <= colChars.length; col++) {

                if (rowChars[row - 1] == colChars[col - 1]) {
                    // 说明序列的最后一个字符相等，那么是右上角的方格值 + 1
                    dp[row][col] = dp[row - 1][col - 1] + 1;
                } else {
                    // 说明最后一个字符不相等，那么最长的可能再两个地方
                    //  正上方：列数组多一个字母和以前的行比较
                    //  正左边：行数组多一个字母和以前的列比较
                    dp[row][col] = Math.max(dp[row - 1][col], dp[row][col - 1]);
                }

            }
        }

        return dp[rowChars.length][colChars.length];
    }
}
