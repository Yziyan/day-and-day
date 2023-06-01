package run.ciusyan.五月._5_9;

/**
 * 斐波那契数列：是学习递归调用，经典的题目。我们下面将逐步优化
 */
public class Fibonacci {

    /**
     * 递归实现：递归通常不是最优解，通常是用很简单的方式求解出解
     * 它一般的套路是：
     *  1、明确递归的问题是什么
     *      求解前 n 个数构成的斐波那契数量的值
     *  2、找出原问题和子问题的关系
     *      根据递推式，只需要分别求解出前 n-1、n-2 个数构成的斐波那契数数列的和
     *  3、找出递归基（递归终止条件）
     *      当 n = 1、n = 2 时，fib = 1
     *
     */
    public static int fib1(int n) {
        if (n <= 2) return 1;

        return fib1(n - 1) + fib1(n - 2);
    }

    /**
     * 采用数组记忆化每一项的结果
     *  上面的实现，复杂度非常高，其原因之一就是有太多的重复计算了
     *  那么，我们求解出每一项的时候，就将它记录下来，那么之后再使用到它的时候，就不用再算一次了
     */
    public static int fib2(int n) {
        // 用于记忆的数组， nums[n] = fib(n)
        int[] nums = new int[n + 1];
        // 初始化 nums 数组
        nums[1] = nums[2] = 1;

        return fib2(n, nums);
    }

    private static int fib2(int n, int[] nums) {
        if (nums[n] == 0) {
            // 说明还没计算出来，
            nums[n] = fib2(n - 1, nums) + fib2(n - 2, nums);
        }

        // 到这里，肯定是计算出来了
        return nums[n];
    }

    /**
     * 去除递归（自底向上）
     *  上面虽然使用数组记忆化求解了，但是还是递归调用，也就是自顶向下的求解
     *  我们能不能自底向上，先求出 n - 1 和 n - 2 的值，然后用它递推出 n 的值呢？
     */
    public static int fib3(int n) {
        if (n <= 2) return 1;

        // 用于记忆化的数组
        int[] nums = new int[n + 1];
        // 初始化
        nums[1] = nums[2] = 1;

        // 迭代求解
        for (int i = 3; i <= n; i++) {
            nums[i] = nums[i - 1] + nums[i - 2];
        }

        return nums[n];
    }

    /**
     * 滚动数组
     *  上面的方式，确实能求出每一项的值，但是其实没必要开辟 n + 1 这么长的数组
     *  因为计算每一项的值，都只用到了前两项，那么我们可以使用一个滚动数组来求解
     */
    public static int fib4(int n) {
        if (n <= 2) return 1;

        // 准备一个用于滚动的数组
        int[] nums = new int[2];
        // 初始化
        nums[0] = nums[1] = 1;

        // 迭代，用 n - 1 和 n - 2 项求出 n
        for (int i = 3; i <= n; i++) {
            // (i & 1) <=> (i % 2)
            nums[i & 1] = nums[1] + nums[0];
        }

        return nums[n & 1];
    }

    /**
     * 使用两个常量
     *  前面使用滚动数组记录了 n-1 和 n-2 项的值，本质上就是交替的记录了 nums[0] 和 nums[1]
     *  但其实就只需要使用两个常量交替的记录即可
     */
    public static int fib(int n) {
        if (n <= 2) return 1;

        // 使用两个常量来记录
        int first = 1, second = 1;

        // 迭代递推
        for (int i = 3; i <= n; i++) {
            // 先推出第 n 项，然后赋值给第二个数 second
            second = first + second;
            // 再记录 n-1项，复制给第一个数 first
            first = second - first;
        }

        // 那么最后的解就是第二个数 second
        return second;
    }

    public static void main(String[] args) {
        int n = 45;
        System.out.println(fib4(n));
        System.out.println(fib3(n));
        System.out.println(fib(n));
    }
}
