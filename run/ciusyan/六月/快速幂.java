package run.ciusyan.六月;


/**
 * https://leetcode.cn/problems/powx-n/
 */
public class 快速幂 {
    public static double myPow(double x, int n) {
        if (x == 0 || x == 1) return x;

        if (n == 0) return 1.0;
        if (n == 1) return x;
        if (n == -1) return 1/x;


        // 除以二

        // 分治
        double half = myPow(x, n >> 1);
        half *= half;

        // 因为会向下取整，所以如果是负数，本身就会多除 x^-1 次方，所以也是直接乘以 x^1 次方
        return (n & 1) == 1 ? half * x : half;
    }

    public static void main(String[] args) {
        System.out.println(myPow(2, -11));
    }

}
