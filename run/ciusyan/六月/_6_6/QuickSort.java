package run.ciusyan.六月._6_6;

/**
 * 快速排序
 */
public class QuickSort {

    public void sort(int[] nums) {
        if (nums == null || nums.length < 2) return;

        // 对整个序列进行快速排序


    }

    /** 对 [begin, end) 的元素进行快速排序 */
    private void sort(int[] nums, int begin, int end) {
        // 说明只有一个元素了，退出递归
        if (end - begin < 2) return;

        // 构建轴点元素，并返回轴点元素的索引
        int pivotIndex = buildPivot(nums, begin, end);

        // 利用轴点将序列一分为二，也进行快速排序
        sort(nums, begin, pivotIndex);
        // 因为轴点位置已经放置了，那么需要从轴点后一个位置开始快排
        sort(nums, pivotIndex + 1, end);
    }

    /** 对 [begin, end) 范围构建轴点元素，并且返回轴点元素的索引 */
    private int buildPivot(int[] nums, int begin, int end) {
        // 因为不包含 end 位置，所以往前移动一位
        end--;

        // 随机选择一个轴点 [0, 1) -> [0, end - begin) -> [begin, end)
        int random = (int) (Math.random() * (end - begin) + begin);

        // 先保存所选轴点的值
        int pivot = nums[random];
        // 需要将其交换到序列的起始位置
        nums[random] = nums[begin];
        nums[begin] = pivot;

        // 让轴点元素合法（大的放轴点右边，小的放轴点左边）
        while (begin < end) {
            // 1、先从后往前扫描
            // 此处的 while 是为了能够使下面代码块的被逻辑交替执行
            while (begin < end) {
                // 先从后往前扫描
                if (nums[end] > pivot) {
                    // 说明此元素本身就比轴点大，并且还在轴点元素的后面，前面就确定了一个元素，那么往后挪动
                    end--;
                } else {
                    // 到达这里说明需要将此元素交换到前面去，
                    nums[begin++] = nums[end];

                    // 此处的 break 是需要转换扫描方向了，改成从后往前扫描
                    break;
                }
            }

            // 2、再从前往后扫描
            // 此 while 与上面同理
            while (begin < end) {
                if (nums[begin] < pivot) {
                    // 说明此元素本身就比轴点小，并且还在轴点元素的前面
                    begin++;
                } else {
                    // 到达这里说明需要将此元素交换到后面去，后面就确定了一个元素，那么往前挪动
                    nums[end--] = nums[begin];

                    // break 与上面同理
                    break;
                }
            }
        }

        // 到达这里说明 begin == end == pivotIndex，将轴点放置在合适的位置
        nums[begin] = pivot;

        return begin;
    }

}
