package run.ciusyan.五月._5_16;

/**
 * https://leetcode.cn/problems/sub-sort-lcci/
 */
public class 部分排序 {
    public int[] subSort(int[] array) {
        if (array == null || array.length < 2) return new int[] {-1, -1};

        // 从前往后找到最后一个逆序对，就是最后一个乱序的
        int lastIdx = -1;
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] >= max) {
                max = array[i];
            } else {
                lastIdx = i;
            }
        }

        // 从后往前找到最前面一个逆序对，就是第一个乱序的
        int first = -1;
        int min = array[array.length - 1];
        for (int i = array.length - 2; i >= 0; i--) {
            if (array[i] <= min) {
                min = array[i];
            } else {
                first = i;
            }
        }

        return new int[] {first, lastIdx};
    }
}
