package run.ciusyan.六月._6_12;

/**
 * https://leetcode.cn/problems/sqrtx/
 */
public class x的平方根 {
    public int mySqrt(int x) {
        if (x == 0) return 0;
        if (x < 4) return 1;

        long res = 1;
        long begin = 1;
        long end = x;
        long mid = 0;
        while (begin <= end) {
            // 二分
            mid = (begin + end) >> 1;

            if (mid * mid <= x) {
                // 先记录这个为一个答案
                res = mid;
                // 然后向右移动
                begin = mid + 1;
            } else {
                // 太大了，答案肯定在左边
                end = mid - 1;
            }
        }

        return (int) res;
    }
}
