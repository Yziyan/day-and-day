package run.ciusyan.六月._6_6;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/longest-common-prefix/
 */
public class 最长公共前缀 {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        // 然后默认是第一个字符那么长
        String result = strs[0];

        // 从第二个字符串开始比较
        for (int i = 1; i < strs.length; i++) {

            // 拿到当前比较的字符串
            char[] chars = strs[i].toCharArray();

            // 拿到当前结果的前缀
            char[] prefix = result.toCharArray();
            int end = 0;
            while (end < Math.min(chars.length, prefix.length)) {
                // 挨个字符比较，若不相等了，直接 break
                if (chars[end] != prefix[end]) break;

                // 将 end ++
                end++;
            }

            // 若某次比较没有前缀，那么说明所有字符没有公共前缀
            if (end == 0) return "";

            result = new String(chars, 0, end);
        }

        return result;
    }

}
