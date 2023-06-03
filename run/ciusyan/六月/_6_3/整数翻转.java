package run.ciusyan.六月._6_3;


/**
 * https://leetcode.cn/problems/reverse-integer/
 */
public class 整数翻转 {
    public int reverse(int x) {
        if (x >= 0 && x < 10) return x;

        int res = 0;

        boolean neg = x < 0;
        x = neg ? -x : x;
        while (x > 0) {

            int last = x % 10;
            int temp = res * 10 + last;

            // 如果推不回去，说明值已经溢出了，返回 0 即可
            if ((temp - last) / 10 != res) return 0;

            // 能来到这里说明没溢出，往后计算
            res = temp;

            // 往后移一位
            x /= 10;
        }

        return neg ? -res : res;
    }
}
