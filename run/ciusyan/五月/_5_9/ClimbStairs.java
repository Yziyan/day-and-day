package run.ciusyan.五月._5_9;

/**
 * 爬楼梯：
 *  其实这个题目，和斐波那契数列基本一样
 */
public class ClimbStairs {

    /**
     * 使用两个变量记录
     *  通过上一种方法的分析，和斐波那契数列的求解差不多
     *  那么优化也是一样的
     */
    public int climbStairs(int n) {
        if (n <= 2) return n;

        int first = 1, second = 2;
        for (int i = 3; i <= n; i++) {
            second = second + first;
            first = second - first;
        }

        return second;
    }

    /**
     * 递归方法：
     *  1、明确这个方法的含义
     *      求解有 n 阶台阶时，有多少种爬法
     *  2、原问题和子问题的关系
     *      可以先爬 1 阶，那么剩下的 n - 1阶台阶，有多少种爬法，就有多少种爬法
     *      也可以先爬 2 阶，那么剩下的 n - 2阶台阶，有多少种爬法，就有多少种爬法
     *      那么最终 n 阶台阶，就只有这两种方法，要么先爬 1 阶，要么先爬 2 阶
     *  3、递归基
     *      当 n = 1 时，只有 1 种爬法
     *      当 n = 2 时，有 2 种爬法
     *
     */
    public int climbStairs1(int n) {
        if (n <= 2) return n;

        return climbStairs1(n - 1) + climbStairs1(n - 2);
    }
}
