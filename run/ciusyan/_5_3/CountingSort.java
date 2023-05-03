package run.ciusyan._5_3;


/**
 * 计数排序：是一种非比较排序算法，它的核心思想是，统计序列中每个元素出现的次数，
 * 利用次数还原出序列的顺序。它的时间复杂度是O(n + k)，n是序列的长度，k 是序列的最大值。
 */
public class CountingSort {

    /**
     * 添加偏移量的实现
     */
    public static void sort(int[] nums) {
        if (nums == null || nums.length < 2) return;

        // 找出最大最小值
        int max = nums[0], min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > max) {
                max = nums[i];
            }

            if (nums[i] < min) {
                min = nums[i];
            }
        }

        // 构建counts 数组
        int[] counts = new int[max - min + 1];
        for (int i = 0; i < nums.length; i++) {
            // nums[i] - min 作为索引，偏移 min 大小
            counts[nums[i] - min]++;
        }

        // 重构counts数组，将 nums[i] 元素的次数，累加到后面
        //  就类似：原本 4 出现了 3次，5 出现了 3 次，
        //  但是 5 该从哪里开始排呢？当然是将 4 排完了，才能 5 嘛
        //      所以 3 + 3 = 4，说明最后一个 5 应该在第 6 个位置，在数组中就是 6 - 1
        //      那我们从后往前排，就能够确定每一个出现的位置了
        for (int i = 1; i < counts.length; i++) {
            // 将前面出现的次数，累加到后面
            counts[i] += counts[i - 1];
        }

        // 利用counts数组，构建出 newNums数组
        int[] newNums = new int[nums.length];

        // 这里需要从后往前排哟
        for (int i = newNums.length - 1; i >= 0; i--) {
            // 因为nums中的第 i 个元素，前面被累加了 counts[nums[i] - min] 多个次数，
            // 那么，counts[nums[i] - min] - 1，就是 nums[i] 在 newNums中的索引
            newNums[--counts[nums[i] - min]] = nums[i];
        }

        // 利用 newNums 重构 nums
        for (int i = 0; i < nums.length; i++) {
            nums[i] = newNums[i];
        }
    }

    /**
     * 最简单的实现，只适用于非负整数
     */
    public static void sort1(int[] nums) {
        if (nums == null || nums.length < 2) return;

        // 找出最大值
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > max) {
                max = nums[i];
            }
        }

        // 统计出现的次数
        int[] counts = new int[max + 1];
        for (int i = 0; i < nums.length; i++) {
            // index = 序列值，value = 次数
            counts[nums[i]]++;
        }

        // 用于记录填到哪一个位置了
        int cur = 0;

        // 还原数组次序
        for (int i = 0; i < counts.length; i++) {
            while (counts[i]-- > 0) {
                // 说明此数字还有计数
                nums[cur++] = i;
            }
        }
    }

}
