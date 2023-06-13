package run.ciusyan.六月._6_13;

/**
 * https://leetcode.cn/problems/merge-sorted-array/
 */
public class 合并两个有序数组 {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (nums1 == null || nums2 == null ||nums1.length == 0) return;

        // 准备几个指针
        int ai = nums1.length - 1; // 每一次放置元素的指针
        int n1i = m - 1, n2i = n -1; // 用于每次比较左右的两个指针

        // 要是第二个数组都合并完成了，就没必要合并了
        while (n2i >= 0) {
            if (n1i >= 0 && nums1[n1i] > nums2[n2i]) {
                // 说明前面数组的值要大，将前面的数组的值放入指定位置
                nums1[ai--] = nums1[n1i--];
            } else {
                // 等于放在这里是因为想要让第二个数组的元素快速的放置完成。
                //  因为后面越早放置完毕，就越早退出循环。
                nums1[ai--] = nums2[n2i--];
            }
        }
    }
}
