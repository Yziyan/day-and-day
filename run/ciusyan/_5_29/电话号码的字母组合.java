package run.ciusyan._5_29;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/letter-combinations-of-a-phone-number/
 */
public class 电话号码的字母组合 {

    /** 数字 -> 字母 */
    private static char[][] DIGITS_MAP = {
            {'a', 'b', 'c'}, {'d', 'e', 'f'},
            {'g', 'h', 'i'}, {'j', 'k', 'l'}, {'m', 'n', 'o'},
            {'p', 'q', 'r', 's'}, {'t', 'u', 'v'}, {'w', 'x', 'y', 'z'}
    };

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        if (digits == null) return result;

        char[] chars = digits.toCharArray();
        if (chars.length == 0) return result;

        // 用于记录轨迹
        char[] track = new char[chars.length];

        // 从第 1 层开始
        dfs(0, chars, track, result);

        return result;
    }

    /** 深度优先遍历求解组合 */
    private void dfs(int level, char[] chars, char[] track, List<String> results) {
        if (level == chars.length) {
            // 记录结果
            results.add(new String(track));

            return;
        }

        // 列举所有可能，挨层往下钻
        char[] emus = DIGITS_MAP[chars[level] - '2'];
        for (char c : emus) {

            // 记录轨迹，往下一层走
            track[level] = c;

            dfs(level + 1, chars, track, results);
        }
    }
}
