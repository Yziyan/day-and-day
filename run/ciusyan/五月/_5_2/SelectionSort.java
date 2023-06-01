package run.ciusyan.五月._5_2;


/**
 * 选择排序：是一种简单的排序，
 */
public class SelectionSort {

    /**
     * 如果遇到一个比末尾（头部）还大（小）的元素，交换它们的值，进入下一次循环。
     */
    public static void sort(int[] nums) {
        if (nums == null || nums.length < 2) return;

        for (int end = nums.length - 1; end > 0; end--) {
            for (int begin = 0; begin < end; begin++) {
                if (nums[begin] <= nums[end]) continue;
                // 来到这里说明需要交换他们
                int temp = nums[end];
                nums[end] = nums[begin];
                nums[begin] = temp;
            }
        }
    }

    /**
     * 上面的实现，只要发现一个最大值，就需要交换末尾的值，
     * 但其实可以找出每一轮中最大（最小）的元素位置，最后再交换它们的值
     * 就可以避免每一轮都有 O(n) 次交换了
     */
    public static void sort1(int[] nums) {
        if (nums == null || nums.length < 2) return;

        for (int end = nums.length - 1; end > 0; end--) {
            // 用于找出最大值的索引，默认是0号位置
            int maxIndex = 0;
            for (int begin = 1; begin < end; begin++) {
                if (nums[begin] >= nums[maxIndex]) {
                    // 来到这里，说明找到一个比之前还大的数，= 是为了保证稳定性
                    maxIndex = begin;
                }
            }

            // 来到这里，找出了最大的值，将其与末尾交换
            int temp = nums[end];
            nums[end] = nums[maxIndex];
            nums[maxIndex] = temp;
        }
    }

}
