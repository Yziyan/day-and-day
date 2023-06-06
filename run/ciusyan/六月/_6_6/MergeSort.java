package run.ciusyan.六月._6_6;


/**
 * 归并排序
 */
public class MergeSort {

    /** 将左子序列备份 */
    private int[] leftSubArr;

    public void sort(int[] nums) {
        if (nums == null || nums.length < 2) return;

        // 初始化左子序列，初始化一个最大的做子序列，稍后就不用重复申请内存了
        leftSubArr = new int[nums.length >> 1];

        // 对整个序列进行归并排序
        sort(nums, 0, nums.length);
    }

    /** 对 [begin, end) 的元素进行归并排序 */
    private void sort(int[] nums, int begin, int end) {
        // 当序列只有一个元素时，就可以退出递归了
        if (end - begin < 2) return;

        // 将序列均分成两个序列，分别进行归并排序
        int mid = (begin + end) >> 1;
        sort(nums, begin, mid);
        sort(nums, mid, end);

        // 然后对两个子序列进行合并
        merge(nums, begin, mid, end);
    }

    /** 将 [begin, mid) 和 [mid, end) 两个序列合并起来 */
    private void merge(int[] nums, int begin, int mid, int end) {
        // 准备一些指针，用于扫描比较左右子序列，

        // 但是为了能复用左子序列，将左边的起始和结束索引统一减去 begin
        int li = 0, le = mid - begin;
        int ri = mid, re = end;
        int ai = begin;

        // 备份左子序列
        for (int i = 0; i < mid; i++) {
            // 注意这里的索引需要添加上偏移量
            leftSubArr[i] = nums[begin + i];
        }

        // 循环比较
        // 如果做子序列填完了，那么其余的就不用填了，或者已经填完了
        while (li < le) {

            // 需要比较左子序列和右子序列的元素，但是需要保证右子序列不越界
            if (ri < re && nums[ri] < leftSubArr[li]) {
                nums[ai++] = nums[ri++];
            } else {
                // 要么说明右边已经全部填到左边去了，要么说明左边的本身就要小
                nums[ai++] = leftSubArr[ri++];
            }
        }
    }
}
