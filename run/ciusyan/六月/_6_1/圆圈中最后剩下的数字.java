package run.ciusyan.六月._6_1;


/**
 * https://leetcode.cn/problems/yuan-quan-zhong-zui-hou-sheng-xia-de-shu-zi-lcof/
 */
public class 圆圈中最后剩下的数字 {

    public int lastRemaining(int n, int m) {
        // 说明不合法
        if (n < 1 || m < 1) return -1;

        // 当只有一个人玩时，存活的人就是 0 号位置
        int dp = 0;
        // 玩 n - 1 次
        for (int i = 2; i <= n; i++) {
            // 根据玩游戏的规模，递推出下一规模的结果

            // dp 是上一次存活的结果，加上人数后可以得到下一规模的索引，但是防止越界，需要模上规模人数
            dp = (dp + m) % i;
        }

        return dp;
    }

    public int lastRemaining1(int n, int m) {
        // 说明不合法
        if (n < 1 || m < 1) return -1;

        // 如果只剩一个人玩，那么第一个位置的编号，就是存活的人
        if (n == 1) return 0;

        // 通过递推得出公式
        return (lastRemaining1(n - 1, m) + m) % n;
    }
}
