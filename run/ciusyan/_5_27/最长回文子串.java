package run.ciusyan._5_27;

/**
 * https://leetcode.cn/problems/longest-palindromic-substring/
 */
public class 最长回文子串 {

    /**
     * 扩展中心法
     *
     */
    public String longestPalindrome(String s) {
        if (s == null) return "";
        char[] chars = s.toCharArray();
        int n = chars.length;
        if (n < 2) return s;

        // 记录最大长度的起始位置
        int begin = 0;
        // 记录最大长度
        int maxLen = 1;

        int i = 0;
        while (i < n) {
            // 记录从哪开始扩展的
            int l = i - 1;
            // 从i开始找和自己相同的字符
            int r = i;

            while (++r < n && chars[i] == chars[r]);
            // 跳过相同的字符后，下一次的 i 就是现在的 r
            i = r;

            // 保证不越界的前提下，l往左边扩展、r往右边扩展
            while (l >= 0 && r < n && chars[l] == chars[r]) {
                l--;
                r++;
            }

            // 来到这里，说明已经不能往外扩展了
            int extendLen = r - l - 1;
            if (extendLen > maxLen) {
                maxLen = extendLen;
                begin = l + 1;
            }
        }

        return new String(chars, begin, maxLen);
    }

    /**
     * 动态规划（一维数组）
     *
     */
    public String longestPalindrome3(String s) {
        if (s == null) return "";
        char[] chars = s.toCharArray();
        int n = chars.length;
        if (n < 2) return s;

        // 记录最大长度的起始位置
        int begin = 0;
        // 记录最大长度
        int maxLen = 1;

        // 因为每次都只用到了右下角的值，可以变成一维数组
        boolean[] dp = new boolean[n];

        // 从倒数第二行开始计算
        for (int row = n - 2; row >= 0; row--) {

            // 代表对角线的值
            dp[row] = true;
            // 从后面往前计算，可以省略记录右下角的变量
            for (int col = n - 1; col > row; col--) {
                if (chars[row] != chars[col]) {
                    // 这里还是复用了之前的值，需要清空
                    dp[col] = false;
                    continue;
                }
                // 说明首尾字符相同，可能是回文串

                // 计算当前串的长度
                int curLen = col - row + 1;

                // 如果长度是2，那么肯定是回文串了，否则看右下角是否是回文串即可
                dp[col] = curLen <= 2 || dp[col - 1];

                // 如果是回文串，看看长度是否比最大的还长
                if (dp[col] && curLen > maxLen) {
                    maxLen = curLen;
                    begin = row;
                }
            }
        }

        return new String(chars, begin, maxLen);
    }

    /**
     * 动态规划（二维数组）
     *
     */
    public String longestPalindrome2(String s) {
        if (s == null) return "";
        char[] chars = s.toCharArray();
        int n = chars.length;
        if (n < 2) return s;

        // 记录最大长度的起始位置
        int begin = 0;
        // 记录最大长度
        int maxLen = 1;

        // dp(i, j) 表示从 s[i] 到 s[j] 是否为回文子串
        boolean[][] dp = new boolean[n][n];

        // 需要从下面往上计算，因为需要用到右下角的值
        for (int row = n - 2; row >= 0; row--) {

            // 对角线不用算了，肯定是长度为一的回文串
            dp[row][row] = true;
            for (int col = row + 1; col < n; col++) {
                // 判断首、尾是否字符是否相同
                if (chars[row] != chars[col]) continue;

                // 来到这里说明此时可能是回文串
                // 当前子串的长度
                int curLen = col - row + 1;

                // 如果长度是 2，那么说明是回文串，否则看右下角的值
                dp[row][col] = curLen <= 2 || dp[row + 1][col - 1];

                // 赋值过后，如果是回文串，并且长度比最大长度还长，说明需要更新结果
                if (dp[row][col] && curLen > maxLen) {
                    maxLen = curLen;
                    begin = row;
                }
            }
        }

        return new String(chars, begin, maxLen);
    }

    /**
     * 暴力法
     *
     */
    public String longestPalindrome1(String s) {
        if (s == null) return "";
        char[] chars = s.toCharArray();
        if (chars.length < 2) return s;

        // 最长的起始位置
        int begin = 0;
        // 最长的长度
        int maxLen = 1;

        for (int i = 0; i < chars.length; i++) {
            for (int j = i + 1; j < chars.length; j++) {
                if (!isPalindrome(chars, i, j)) continue;
                // 说明是回文子串，记录长度和出现的位置

                // 计算长度
                int curLen = j - i + 1;
                if (maxLen >= curLen) continue;

                // 来到这里说明比之前的长
                maxLen = curLen;
                begin = i;
            }
        }

        return new String(chars, begin, maxLen);
    }

    /**
     * 判断 [begin, end] 是不是回文子串
     *
     */
    private boolean isPalindrome(char[] chars, int begin, int end) {

        // 不断往中间逼近
        while (begin < end) {
            if (chars[begin] != chars[end]) return false;

            begin++;
            end--;
        }

        return true;
    }
}
