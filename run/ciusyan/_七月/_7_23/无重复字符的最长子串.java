package run.ciusyan._七月._7_23;

import java.util.HashMap;
import java.util.Map;

public class 无重复字符的最长子串 {
    public int lengthOfLongestSubstring(String s) {
        if (s == null) return 0;

        char[] chars = s.toCharArray();
        if (chars.length == 0) return 0;

        // <字符, 最后一次出现的位置>
        Map<Character, Integer> map = new HashMap<>();
        int max = 1;
        // 记录起始位置
        int begin = -1;
        for (int i = 0; i < chars.length; i++) {
            Integer endIdx = map.getOrDefault(chars[i], -1);
            if (begin < endIdx) {
                // 说明以前出现过这个字符了，并且在开始的范围内
                begin = endIdx;
            } else {
                // 说明以前没有出现过，尝试更新最大距离
                max = Math.max(max, i - begin);
            }
            // 更新该字符最后出现的位置
            map.put(chars[i], i);
        }

        return max;
    }

}
