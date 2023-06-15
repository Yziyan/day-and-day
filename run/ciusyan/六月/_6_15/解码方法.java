package run.ciusyan.六月._6_15;

/**
 * https://leetcode.cn/problems/decode-ways/
 */
public class 解码方法 {

    public static void main(String[] args) {
        System.out.println(numDecodings1("111111111111111111111111111111111111111111111"));
    }

    /** 动态规划 */
    public static int numDecodings(String s) {
        if (s == null) return 0;
        char[] chars = s.toCharArray();
        int len = chars.length;
        if (len == 0) return 0;
        // dp(i) 代表以第 i 个字符结尾，能够解码的数量
        int[] dp = new int[len + 1];
        // 代表 "" ->解码出-> ""
        dp[0]  = 1;

        // i 代表第几个字符
        for (int i = 1; i <= len; i++) {
            // 如果是 '0' 打头，那么没必要计算这一个字符了
            if (chars[i - 1] != '0') {
                // 说明此字符可以单独解码算单独的一种情况，
                dp[i] = dp[i - 1];
            }

            // 尝试使用 2 位数来解码，首先要使这两个字符合法
            if (i < 2 || chars[i - 2] == '0') continue;

            // 再将这 2 个字符转换成数字
            int num = (chars[i - 2] - '0') * 10 + chars[i - 1] - '0';
            if (num <= 26) {
                // 说明可以解码
                dp[i] += dp[i - 2];
            }
        }

        return dp[len];
    }

        /** 类似 DFS 的思想递归，如果情况非常多可能会超时 */
    public static int numDecodings1(String s) {
        if (s == null) return 0;
        char[] chars = s.toCharArray();
        if (chars.length == 0) return 0;

        // 从第 0 层开始搜索
        return dfs(0, chars);
    }

    /**  从 level 层开始 DFS */
    private static int dfs(int level, char[] chars) {
        // 说明搜索出一个结果了，返回一个结果
        if (level == chars.length) return 1;

        // 列出所有可能的情况，
        //  如果 level 层是 0 字符打头，没必要往下面解码了，因为当初的选择不能搜索出一个解
        if (chars[level] == '0') return 0;

        // 来到这里说明当前字符是 1~9，有两种情况
        //  情况一：至少可以往下钻一层
        int way = dfs(level + 1, chars);

        //  情况二：可以往下面砖两层
        //  首先下两层要合法，不合法就直接返回第一种情况的答案了
        if (level + 1 == chars.length) return way;

        // 计算往下钻 2 层对应的数字，不能大于 26，要不然也不能往下钻 2 层
        int num = (chars[level] - '0') * 10 + chars[level + 1] - '0';
        if (num <= 26) {
            // 说明往下钻 2 层也合法，那么需要将 2 种情况的总路径加起来
            way += dfs(level + 2, chars);
        }

        return way;
    }
}
