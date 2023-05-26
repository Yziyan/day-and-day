package run.ciusyan._5_26;

/**
 * https://leetcode.cn/problems/edit-distance/
 */
public class 编辑距离 {

    /**
     * 一维数组
     *
     */
    public int minDistance(String word1, String word2) {
        if (word1 == null || word2 == null) return 0;
        char[] chars1 = word1.toCharArray();
        char[] chars2 = word2.toCharArray();

        // 较短的做为列
        char[] rowChars = chars1;
        char[] colChars = chars2;
        if (colChars.length > rowChars.length) {
            colChars = chars1;
            rowChars = chars2;
        }

        // 可以将二维数组转换为一维的
        int[] dp = new int[colChars.length + 1];

        // 初始化第一行
        for (int col = 1; col <= colChars.length; col++) {
            dp[col] = col;
        }

        for (int row = 1; row <= rowChars.length; row++) {
            // 方便之后保存左上角的值
            int cur = dp[0];
            for (int col = 1; col <= colChars.length; col++) {
                // 拿到这一轮的左上角，保存下一轮的左上角
                int leftTop = cur;
                cur = dp[col];

                // 替换，这里就直接用右上角的值当做替换了
                if (rowChars[row - 1] != colChars[col - 1]) {
                    // 说明最后一个字符不相等，需要替换的操作
                    leftTop++;
                }

                // 删除 or 添加
                int removeOrInsert = Math.min(dp[col], dp[col - 1]) + 1;

                dp[col] = Math.min(leftTop, removeOrInsert);
            }

            // 初始化每一行的首字符： "xxx" -> ""
            dp[0] = row;
        }

        return dp[colChars.length];
    }


    /**
     * 二维数组
     *
     */
    public int minDistance1(String word1, String word2) {
        if (word1 == null ||word2 == null) return 0;
        char[] chars1 = word1.toCharArray();
        char[] chars2 = word2.toCharArray();

        // 较短的作为列数组
        char[] rowChars = chars1;
        char[] colChars = chars2;
        if (colChars.length > rowChars.length) {
            colChars = chars1;
            rowChars = chars2;
        }

        // dp(i, j) 代表以 s1[i] 结尾 和 s2[j] 结尾单词的最小编辑路径
        int[][] dp = new int[rowChars.length + 1][colChars.length + 1];
        // 初始化第一行 dp(0, j) "" -> "xxx"
        for (int col = 1; col <= colChars.length; col++) {
            dp[0][col] = col;
        }
        // 初始化第一列 dp(i, 0) "xxx" -> ""
        for (int row = 1; row <= rowChars.length; row++) {
            dp[row][0] = row;
        }

        // 推导之后的值
        for (int row = 1; row <= rowChars.length; row++) {

            for (int col = 1; col <= colChars.length; col++) {
                // 一行一行的计算
                // 如果采用删除的方式 dp(i, j) = dp(i - 1, j) + 1
                // 如果采用插入的方式 dp(i, j) = dp(i, j - 1) + 1
                // 如果采用替换的方式 dp(i, j) = dp(i - 1, j - 1) + s1[row] == s2[col] ? 0 : 1
                int insertOrRemove = Math.min(dp[row][col - 1], dp[row - 1][col]) + 1;
                int replace = dp[row - 1][col - 1];
                if (rowChars[row - 1] != colChars[col - 1]) {
                    // 说明最后一个字符不相等，需要替换
                    replace++;
                }

                // 三者中的最小值
                dp[row][col] = Math.min(insertOrRemove, replace);
            }
        }

        return dp[rowChars.length][colChars.length];
    }
}
