package run.ciusyan.六月._6_10;


/**
 * https://leetcode.cn/problems/powx-n/
 */
public class Pow {
    /**
     * 高级的二进制写法
     *  这个的原理是：将次方通过二进制来表示，比如：10 的二进制表示是 1010
     *  那么 x^10 = x^(1010) = x^(0 * 2^0 + 1 * 2^1 + 0 * 2^2 + 1 * 2^3) = x^(2^1) * x^(2^3)
     *  所以当对应二进制位为 1 时，就能够对结果有贡献。
     */
    public double myPow(double x, int n) {
        if (n == 0 || x == 1) return 1;
        if (n == 1) return x;
        if (n == -1) return 1 / x;

        double res = 1;
        // 将 t 初始为 x^1
        double t = x;
        // 但需要确保 n 是正次，但是如果是 int 的最小值，那么需要额外处理
        int pow = Math.abs(n == Integer.MIN_VALUE ? n + 1 : n);

        while (pow != 0) {
            if ((pow & 1) == 1) {
                // 如果 二进制位是 1，说明对结果有贡献
                res *= t;
            }
            // 挨次变成 x^1 -> x^2 -> x^4 -> x^8 -> x^16 ...
            t *= t;
            // 算一次就向后移动一位
            pow >>= 1;
        }

        // 如果是系统最小值，那么算了完了之后，还需要 * x^1
        if (n == Integer.MIN_VALUE) {
            res *= x;
        }

        // 如果是负次幂，那么需要将结果倒一下。比如（x^-10 = 1/x^10）
        return n < 0 ? 1/res : res;
    }


    /** 递归写法 */
    public double myPow1(double x, int n) {
        if (n == 0) return 1;
        if (n == 1) return x;
        if (n == -1) return 1/x;

        // 先求出一半次方
        double half = myPow1(x, n >> 1);
        // 如果是偶次方，那么直接就是 res = half * half (x^8 = x^4 * x^4)
        //  如果是奇次方，那么还需要乘以 x： res = half * half * x (x^9 = x^4 * x^4 * x)
        half *= half;

        // 上面是 n 为正次方的情况，那么如果是负次方呢？
        //  偶次：和正次的结果一样 （x^-8 = x^-4 * x^-4）
        //  奇次：还需要 * x^-1 （x^-9 = x^-4 * x^-4 * x^-1）
        //  但是如果是在写代码时，因为是向下取整，那么 -9 / 2 = -5
        //      那么相当于多除了一次方，所以需要乘回来，比如： x^-9 = x^-5 * x^-5 * x
        //      所以代码就可以统一成：如果是偶次方就直接用 half * half，如果是奇次方还需要乘 x，

        // 如果二进制最后一位是 1，那么说明是奇数，也就是 (n & 1)  == 1
        return (n & 1)  == 1 ? half * x : half;
    }
}
