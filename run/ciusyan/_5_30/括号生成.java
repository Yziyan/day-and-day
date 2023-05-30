package run.ciusyan._5_30;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/generate-parentheses/
 */
public class 括号生成 {

    private static char LEFT = '(';
    private static char RIGHT = ')';

    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        if (n == 0) return result;

        char[] track = new char[n << 1];

        // 从第 0 层搜索，左右括号都剩余 n 个
        dfs(0, track, n, n, result);

        return result;
    }

    /***
     *
     * @param remainLeft：剩余的左括号数量
     * @param remainRight：剩余的右括号数量
     */
    private static void dfs(int level, char[] track, int remainLeft, int remainRight, List<String> results) {
        if (level == track.length) {
            // 说明有了 2n 个括号，记录结果
            results.add(new String(track));

            return;
        }

        // 列举所有可能

        // 要么能选择左边
        if (remainLeft > 0) {
            // 只要有左括号，就能选择左边
            track[level] = LEFT;
            // 然后往下钻
            dfs(level + 1, track, remainLeft - 1, remainRight, results);
        }

        // 要么能选择右边
        if (remainRight > remainLeft) {
            // 必须要小于左边的剩余括号数时，才能选择右边
            track[level] = RIGHT;
            // 往下钻
            dfs(level + 1, track, remainLeft, remainRight - 1, results);
        }

    }

    public static void main(String[] args) {
        System.out.println(generateParenthesis(3));
    }

}
