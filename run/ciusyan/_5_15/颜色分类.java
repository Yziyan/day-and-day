package run.ciusyan._5_15;

/**
 * https://leetcode.cn/problems/sort-colors/
 */
public class 颜色分类 {

    /**
     * 多指针
     *  面对数组题，如果要求使用原地算法的，那么可能需要交换数组前后的值
     *  那么可以使用几个指针，分别用于指向前后，还有用于扫描。
     *
     */
    public void sortColors(int[] nums) {
        if (nums == null || nums.length == 0) return;

        // 准备三个指针
        int l = 0, r = nums.length - 1, ai = 0;

        while (ai <= r) {
            int num = nums[ai];
            if (num == 2) {
                // 需要交换 r 和 ai，然后只讲r往中间挪动，ai位置还不能挪，因为万一交换过来的也是 2
                nums[ai] = nums[r];
                nums[r--] = num;
            } else if (num == 1) {
                // 直接跳过
                ai++;
            } else {
                // 遇到的是 0，需要交换 l 和 ai，然后都往中间挪动
                nums[ai++] = nums[l];
                nums[l++] = num;
            }
        }
    }
}
