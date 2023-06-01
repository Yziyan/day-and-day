package run.ciusyan.五月._5_15;


/**
 * https://leetcode.cn/problems/merge-sorted-array/
 *
 */
public class 合并两个有序数组 {

    /**
     * 多指针、扫描方向
     *  做数组题的时候，可以试试不同的扫描方向、比较不同的值
     *  如果需要一遍扫描就得出结果，可能需要使用到多指针
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (nums1 == null || nums2 == null) return;
        if (m < 0 || n == 0) return;

        // 准备两个指针，指向两个数组实际的最后一个元素
        int i1 = m - 1, i2 = n - 1;
        int ai = nums1.length - 1;

        // 当后面的数组合并完后，就可以直接退出合并了
        while (i2 >= 0) {
            if (i1 >= 0 && nums1[i1] > nums2[i2]) {
                nums1[ai--] = nums1[i1--];
            } else {
                // 来到这里，要么是前面数组已经合并完成了，要么是后面数组的元素要大
                nums1[ai--] = nums2[i2--];
            }
        }
    }
}
