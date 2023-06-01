package run.ciusyan.五月._5_3;

/**
 * 基数排序：基数排序也是一种非比较的排序算法，它的核心操作是依次对序列的每一个数的每一位基数进行排序，
 * 并且是从低位到高位，个位 -> 十位 -> ... 对每一位基数一般使用计数排序，因为计数排序非常适用于对一定范围内
 * 的整数进行排序。
 */
public class RadixSort {

    public static void sort(int[] nums) {
        if (nums == null || nums.length < 2) return;

        // 找到最大值，才能知道要对多少基数进行排序
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > max) {
                max = nums[i];
            }
        }

        // 对每一位基数进行计数排序
        for (int i = 1; i < max; i *= 10) {
            countingSort(nums, i);
        }
    }

    /** 对 divider 作为基数进行计数排序 */
    private static void countingSort(int[] nums, int divider) {

        // 就不用找最大最小值了，反正也才 [0, 9]
        int[] counts = new int[10];

        // 构建counts数组
        for (int i = 0; i < nums.length; i++) {
            counts[nums[i] / divider % 10]++;
        }

        // 重构counts数组，将前面出现过的累加到后面
        for (int i = 1; i < counts.length; i++) {
            counts[i] += counts[i - 1];
        }

        // 从后往前，构建出新数组
        int[] newNums = new int[nums.length];
        for (int i = newNums.length - 1; i >= 0; i--) {
            newNums[--counts[nums[i] / divider % 10]] = nums[i];
        }

        // 重构nums数组
        for (int i = 0; i < nums.length; i++) {
            nums[i] = newNums[i];
        }
    }


}
