package run.ciusyan._5_2;

/**
 * 归并排序：它是一种利用分治思想的排序算法，它的核心过程有两个，拆分和合并：
 *  拆分：不断的将序列均分成两个序列，直至每个序列只有一个元素。
 *  合并：然后将相邻的两个序列按照顺序合并到一起，直至只有一个序列。
 *  其中拆分可以使用递归很方便的实现，设计一个递归函数，对一个范围进行归并排序，在内部进行递归的拆分。
 *  合并可以将左子序列备份，然后使用多指针，挨个比较左右子序列中正操作的元素，直至左子序列已经全部放入合适的位置
 */
public class MergeSort {

    /** 用于备份左子序列 */
    private static int[] leftArray;

    public static void sort(int[] nums) {
        if (nums == null || nums.length < 2) return;

        // 弄一个最大的子序列，之后就不用频繁创建了
        leftArray = new int[nums.length >> 1];

        // 对 [0, nums.length) 进行归并排序
        sort(nums, 0, nums.length);
    }

    /**
     * 对nums中 [begin, end) 范围进行归并排序
     */
    private static void sort(int[] nums, int begin, int end) {
        // 至少要有两个元素，才进行递归
        if (end - begin < 2) return;

        // 均分成两个序列
        int mid = (begin + end) >> 1;
        // 对 [begin, mid) 进行归并排序
        sort(nums, begin, mid);
        // 对 [mid, end) 进行归并排序
        sort(nums, mid, end);

        // 合并[begin, mid) 和 [mid, end) 两个序列
        merge(nums, begin, mid, end);
    }

    /**
     * 合并 [begin, mid) 和 [mid, end) 两个序列
     */
    private static void merge(int[] nums, int begin, int mid, int end) {
        // li：左子序列开始位置、le：左子序列结束位置
        int li = 0, le = mid - begin;
        // 同上
        int ri = mid, re = end;
        // 正在合并的nums中下一个填入位置
        int cur = begin;

        // 备份左子序列
        for (int i = 0; i < le; i++) {
            // 注意，这里需要将 begin + i 开始拷贝
            leftArray[i] = nums[begin + i];
        }

        // 当左子序列合并完成，右子序列就不用处理了
        while (li < le) {
            if (ri < re && leftArray[li] > nums[ri]) {
                // 来到这里，说明右边的小，拷右边，记得拷完要挪动指针
                nums[cur++] = nums[ri++];
            } else {
                // leftArray[li] <= nums[ri] || 右子序列已经合并完了，直接将左子序列拷贝过来就行了
                nums[cur++] = leftArray[li++];
            }
        }
    }

}
