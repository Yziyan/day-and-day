package run.ciusyan._5_25;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.cn/problems/longest-substring-without-repeating-characters/
 */
public class 无重复字符的最长子串 {

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("pwwkew"));
    }

    public static int lengthOfLongestSubstring(String s) {
        if (s == null) return 0;
        char[] chars = s.toCharArray();
        if (chars.length == 0) return 0;

        // 使用HashMap映射索引，<字符, 最后一个出现的位置>
        Map<Character, Integer> map = new HashMap<>();

        // 无重复子串最大长度
        int max = 1;
        // 记录起始位置
        int begin = -1;
        for (int i = 0; i < chars.length; i++) {
            Integer preIndex = map.getOrDefault(chars[i], -1);
            if (begin < preIndex) {
                // 说明这个字符在 begin 前出现的（也可能是在哨兵位置出现的 -1）
                begin = preIndex;
            } else {
                // 说明这个字符在之前没出现过，可以尝试计算最大长度
                // or 这个字符出现在 begin 之前
                max = Math.max(max, i - begin);
            }

            // 记录当前字符最后一次出现的位置
            map.put(chars[i], i);
        }

        return max;
    }
}
