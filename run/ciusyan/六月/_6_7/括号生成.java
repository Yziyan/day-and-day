package run.ciusyan.六月._6_7;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/generate-parentheses/
 */
public class 括号生成 {

    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        if (n < 1) return result;

        // 准备一个轨迹，用于记录结果，合法的话，会有 2n 个括号
        char[] track = new char[n << 1];
        // 从第 0 层开始搜索，左右括号初始剩余 n 个
        dfs(0, track, n, n, result);

        return result;
    }

    /** 深度优先搜索第 level 层，来到这一层时，还剩余的左右括号分别是 leftRemain 和 rightRemain */
    private void dfs(int level, char[] track, int leftRemain, int rightRemain, List<String> results) {
        if (level == track.length) {
            // 说明有一个结果了，记录并返回
            results.add(new String(track));

            return;
        }

        // 列举所有可能，记录结果并且往下一层搜索
        // 1、选择左括号
        //  只要还有左括号，其实就能选择
        if (leftRemain > 0) {
            // 记录左括号
            track[level] = '(';

            // 往下一层搜索，左括号少了一个
            dfs(level + 1, track, leftRemain - 1, rightRemain, results);
        }

        // 2、选择右括号
        //  右括号必须大于左括号，才能选择
        if (rightRemain > leftRemain) {
            // 记录右括号
            track[level] = ')';

            // 往下一层搜索，右括号少了一个
            dfs(level + 1, track, leftRemain, rightRemain - 1, results);
        }
    }

}
