package run.ciusyan._5_1;

public class BubbleSort {

    /**
     * 冒泡排序是一种简单的排序算法，它通过比较任意两个相邻元素的大小，如果他们的顺序错了
     * 就将其交换。每一轮会将最大（最小）的元素，冒泡到首部或者尾部，从而实现排序。
     * 它的最坏和平均时间复杂度都是O(n ^ 2)，但他存在最好的时间复杂度，是O(n)，
     * 也就是当数组本身就有序的情况，只需要扫描一次即可
     * 空间复杂度是O(1)，属于原地排序、稳定排序
     */
    public static void sort(int[] nums) {
        if (nums == null || nums.length < 2) return;

        for (int end = nums.length - 1; end > 0 ; end--) {
            for (int begin = 1; begin <= end; begin++) {
                if (nums[begin] < nums[begin - 1]) {
                    // 说明后一个比前一个小，交换他们
                    int temp = nums[begin];
                    nums[begin] = nums[begin - 1];
                    nums[begin - 1] = temp;
                }
            }
        }
    }

    /*
    当某次扫描的时候，如果发现已经有序了。那么可以提前退出排序
     */
    public static void sort2(int[] nums) {
        if (nums == null || nums.length < 2) return;

        // 标识是否已经有序了
        boolean sorted = true;
        for (int end = nums.length - 1; end > 0; end--) {
            for (int begin = 1; begin <= end; begin++) {
                if (nums[begin] < nums[begin - 1]) {
                    int temp = nums[begin];
                    nums[begin] = nums[begin - 1];
                    nums[begin - 1] = temp;

                    // 来到这里，说明还是无序的，之后还需要扫描
                    sorted = false;
                }
            }

            // 如果已经有序了，那么别在
            if (sorted) break;
        }
    }

    /*
    如果已经部分有序了，我们也可以提前退出
     */

    public static void sort3(int[] nums) {
        if (nums == null || nums.length < 2) return;

        for (int end = nums.length - 1; end > 0; end--) {

            // 已经有序的索引。默认扫描完一次全部有序
            int sortedIndex = 1;

            for (int begin = 1; begin <= end; begin++) {
                if (nums[begin] < nums[begin - 1]) {
                    int temp = nums[begin];
                    nums[begin] = nums[begin - 1];
                    nums[begin - 1] = temp;

                    // 只要从这里出去，说明后面的肯定已经排好序了，
                    //  所以之后只需要冒泡到这里就行了
                    sortedIndex = begin;
                }
            }

            // 只需要冒泡到 sortedIndex 就可以了
            end = sortedIndex;
        }
    }

}
