package run.ciusyan.五月._5_3;

import java.util.ArrayList;
import java.util.List;

/**
 * 希尔排序：是一种基于比较的排序，它的核心思想是，将序列依次分为很多列，然后对每一列进行排序，
 * 直至序列被变成一列且完成排序。依次分为的一组列，被称为步长序列。通常使用插入排序对每一步长序列进行排序。
 * 所以也可以说希尔排序是对插入排序的优化，因为对每一步长序列拍完序后，序列的逆序对在减少。
 * 那么使用插入排序是一种不错的选择。希尔排序的时间复杂度取决于使用的步长序列，目前最优的步长序列可以到达O(n^1.3)左右
 */
public class ShellSort {

    public static void sort(int[] nums) {
        if (nums == null || nums.length < 2) return;

        // 获取步长序列

        List<Integer> steps = getStep(nums);

        // 对每一步长序列进行插入排序
        for (Integer step : steps) {
            sort(nums, step);
        }
    }

    /**
     * 对 step 列，进行插入排序
     */
    private static void sort(int[] nums, int step) {

        // 对每一列进行排序
        for (int col = 0; col < step; col++) {

            // 插入排序
            // 0, 1, 2, 3 ..
            // col, col + step, col + 2step ...
            // 默认已经摸一张牌了
            for (int begin = col + step; begin < nums.length; begin += step) {
                // 当前摸的牌
                int current = begin;
                int newEle = nums[current];

                // 腾出位置
                while (current > col && nums[current - step] > newEle) {
                    // 说明前一个元素是比新摸的牌大的，往后挪一个位置
                    nums[current] = nums[current -= step];
                }

                // 插入到对应的位置，
                nums[current] = newEle;
            }
        }
    }

    private static List<Integer> getStep(int[] nums) {
        List<Integer> list = new ArrayList<>();
        int step = nums.length;
        while ((step >>= 1) > 0) {
            list.add(step);
        }

        return list;
    }

}
