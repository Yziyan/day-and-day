package run.ciusyan._5_2;

/**
 * 插入排序：它的核心思想是将序列分成已排序和待排序2个序列，每一轮从待排序的序列中，
 * 选取一个元素，将其插入到已排序序列的合适位置，直至待排序序列为空。这个过程有点像摸牌的过程。
 * 它是一种自适应排序，它的时间复杂度取决于逆序对的数量，如果序列没有逆序对，那么它的时间复杂度是O(n)
 * 属于基于比较的、稳定的、原地的排序算法。
 */
public class InsertSort {

    /**
     * 上一种方式，是一张一张的挪动，从而找出插入的位置，
     * 那么，核心就是要找出插入的位置，而且是在一个有序序列中查找合适的位置，
     * 那么，我们能不能使用二分查找，先找出位置，然后统一挪动元素，再插入呢？
     *
     */
    public static void sort(int[] nums) {
        if (nums == null || nums.length < 2) return;

        for (int begin = 1; begin < nums.length; begin++) {
            // 先拿到新摸的牌
            int element = nums[begin];

            // 找出插入的位置
            int insertIndex = search(begin, nums);

            // 挨个腾出位置
            for (int i = begin; i > insertIndex ; i--) {
                nums[i] = nums[i - 1];
            }

            // 插入值
            nums[insertIndex] = element;
        }

    }

    /**
     * 找出 [0, index) 有序序列中 nums[index] 应该存在的位置
     */
    private static int search(int index, int[] nums) {
        // 前面的序列是有序的
        int begin = 0;
        int end = index;

        while (begin < end) {
            int mid = (begin + end) >> 1;

            if (nums[mid] <= nums[index]) {
                // 等于放在这里，为了保证稳定
                //      要不然搜索到相等的的值的时候，可能会返回不同的位置

                // 说明位置在 [mid + 1, end)
                begin = mid + 1;
            } else {
                // 说明位置在 [begin, mid)
                end = mid;
            }
        }

        return begin;
    }

    /**
     * 优化，交换牌，改为挪动牌的顺序
     */
    public static void sort2(int[] nums) {
        if (nums == null || nums.length < 2) return;

        for (int begin = 1; begin < nums.length;  begin++) {
            int cur = begin;
            // 当前摸的一张牌
            int element = nums[cur];

            while (cur > 0 && nums[cur - 1] > element) {
                // 说明前一张牌，比当前这张牌大，往后腾一个位置
                // 前一个往后挪动
                nums[cur] = nums[--cur];
            }

            // 来到这里，说明找到了合适的位置，将其插入
            nums[cur] = element;
        }
    }


    /**
     * 模拟摸牌的过程，最简单实现（交换牌）
     */
    public static void sort1(int[] nums) {
        if (nums == null || nums.length < 2) return;

        // 默认已经摸了一张牌了
        for (int begin = 1; begin < nums.length; begin++) {
            int cur = begin;

            // 将cur这张牌，插入到合适的位置
            while (cur > 0 && nums[cur - 1] > nums[cur]) {
                // 来到这里说明前面那张牌要大一些
                int temp = nums[cur];
                nums[cur] = nums[cur - 1];
                nums[cur - 1] = temp;

                // 再与前面的牌比较
                cur--;
            }
        }
    }



}
