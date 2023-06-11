package run.ciusyan.六月._6_11;

/**
 * https://leetcode.cn/problems/unique-paths/
 */
public class 不同路径 {

    /**
     * 数学组合法
     *  总共需要往右走 m - 1 步，往下走 n - 1 步，总共就需要 m - 1 + n -1 = m + n - 2 步。
     *  那么前所有的走法，就是从 m + n - 2 中选择往下走的步数，或者选择往右走的步数
     */
    public int uniquePaths(int m, int n) {
        if (m < 1 || n < 1) return 0;

        // 防止溢出
        long res = 1;
        if (m < n) { // 用小的作为 n，大的作为 m，就可以少算几次
            int temp = m;
            m = n;
            n = temp;
        }

        for (int above = m, below = 1; below < n; above++, below++) {
            // 这个本质上是在算： m! / n! * (m - n)!
            //  但是我们可以将上面 m! 和 下面 (m - n)! 约分，那么就可以少算一些
            res = res * above / below;
        }

        return (int) res;
    }

    /**
     * 和上面一样的方法，但是上面那种算过大的数字，可能会导致过早的 乘法 溢出，
     *  虽然 Leetcode 的数字不大，我们可以将分子分母提前约分
     *  比如 算 1001 * 1000 / 100 * 99，我们可以先将 1000 和 100 约分了再计算，
     *  变成：1001 * 10 / 99，就没有必要先计算出，1000 * 10001 后再除以 100 * 99
     *  那么就减少了溢出的可能性。
     */
    public static int uniquePaths1(int m, int n) {
        if (m < 1 || n < 1) return 0;

        // 防止溢出
        long res = 1;
        if (m < n) { // 用小的作为 n，大的作为 m，就可以少算几次
            int temp = m;
            m = n;
            n = temp;
        }

        for (int above = m, below = 1; below < n; above++, below++) {
            // 先找出 above 和 below 的最大公约数
            int gcd = gcd(above, below);

            // 在计算结果前，先约分，但是注意别修改了 above 和 below 本身的值
            int newAbove = above / gcd;
            int newBelow = below / gcd;

            res = res * newAbove / newBelow;
        }

        return (int) res;
    }

    /** 利用辗转相除法，求解最大公约数 */
    private static int gcd(int m, int n) {
        // m % n == 0 时，代表 m 能整除 n，所以最大公约数是 n，
        //  那么为什么要返回 m 呢？因为 m 和 n 辗转了。
        return n == 0 ? m : gcd(n, m % n);
    }

    /** 动态规划 */
    public int uniquePaths2(int m, int n) {
        if (m < 1 || n < 1) return 0;

        if (m < n) { // 用小的作为 n，大的作为 m，就可以少算几次
            int temp = m;
            m = n;
            n = temp;
        }

        // dp(i, j) 代表到达第 (i, j) 拥有的次数，
        //  但是每次计算只用到了上面和左边的状态，所以用一维数组即可
        int[] dp = new int[n];

        // 代表第一行，因为没法从上面走下来，所以只能有一种走法
        for (int col = 0; col < n; col++) dp[col] = 1;

        for (int row = 1; row < m; row++) {
            for (int col = 1; col < n; col++) {
                // 走到 (row, col) 只能往上面或者下面过来
                dp[col] = dp[col] + dp[col - 1];
            }
        }

        return dp[n - 1];
    }

    public static void main(String[] args) {
        System.out.println(uniquePaths1(3, 7));
    }

}
