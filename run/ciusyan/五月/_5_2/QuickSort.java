package run.ciusyan.五月._5_2;


/**
 * 快速排序：快速排序也是一种基于分治思想的排序算法，它有两个核心操作：
 *  构建轴点：选择序列的第一个位置作为轴点，在序列中，大于轴点的放他右边，小于的放他左边
 *  拆分：利用轴点的位置，将序列拆分成两个子序列，直至序列只有一个元素，对其子序列也进行快速排序。
 *  其实快速排序的本质就是：将每一个元素都变成轴点元素
 */
public class QuickSort {

    public static void sort(int[] nums) {
        if (nums == null || nums.length < 2) return;

        sort(nums, 0, nums.length);
    }

    /**
     * 对 [begin, end) 范围进行快速排序
     */
    private static void sort(int[] nums, int begin, int end) {
        if (end - begin < 2) return;


        // 构建轴点元素
        int pivotIndex = pivotIndex(nums, begin, end);

        // 利用轴点，将序列拆分成两个子序列
        // [begin, pivot)
        sort(nums, begin, pivotIndex);

        // [pivot + 1, end)，因为轴点位置已经放置元素了，所以不用再排了
        sort(nums, pivotIndex + 1, end);
    }

    /**
     * 构建 [begin, end) 轴点元素
     */
    private static int pivotIndex(int[] nums, int begin, int end) {
        // 因为不包含 end
        end--;

        // 但是为了防止轴点两边的元素极度不均匀，随机选择一个元素，作为轴点
        // 从 [begin, end) 中随机挑一个
        //  begin + random() * (end - begin)
        // [0, 1) -> [0, end - begin) -> [begin, end)
        int random = (int) (begin + Math.random() * (end - begin));
        int pivot = nums[random];
        nums[random] = nums[begin];
        nums[begin] = pivot;

        while (begin < end) {

            // 此处的while，是为了重复执行下面的逻辑
            while (begin < end) {
                if (nums[end] > pivot) {
                    // 说明本身就在轴点后面
                    end--;
                } else {
                    // 说明此元素应该在轴点前面
                    nums[begin++] = nums[end];

                    // 此处的 break，是为了换一个方向执行
                    break;
                }
            }

            // 此处的 while，是为了重复执行下面的逻辑
            while (begin < end) {
                if (nums[begin] < pivot) {
                    // 说明该元素本身就在轴点的前面
                    begin++;
                } else {
                    // 说明此元素应该在轴点的后面
                    nums[end--] = nums[begin];

                    // 此处的 break，是为了换一个方向执行
                    break;
                }
            }
        }

        // 记得要设置轴点元素
        nums[begin] = pivot;

        // 来到这里，begin = end = pivotIndex
        return begin;
    }
}
