package run.ciusyan.六月._6_6;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/letter-combinations-of-a-phone-number/
 */
public class 电话号码的字母组合 {

    /** 电话映射表 */
    private static char[][] PHONE = {
            {'a', 'b', 'c'}, {'d', 'e', 'f'},
            {'g', 'h', 'i'}, {'j', 'k', 'l'}, {'m', 'n', 'o'},
            {'p', 'q', 'r', 's'}, {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}
    };

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null) return result;
        char[] chars = digits.toCharArray();
        if (chars.length == 0) return result;

        char[] track = new char[chars.length];

        dfs(0, chars, track, result);

        return result;
    }

    /** 深度优先遍历 */
    private void dfs(int level, char[] chars, char[] track, List<String> result) {
        if (level == chars.length) {
            // 记录一个结果
            result.add(new String(track));

            return;
        }

        // 列举所有可能 （因为 0 和 1 没有字符对应）
        char[] all = PHONE[chars[level] - '2'];

        for (char c : all) {
            // 记录轨迹
            track[level] = c;
            // 往下面钻
            dfs(level + 1, chars, track, result);
        }
    }
}
