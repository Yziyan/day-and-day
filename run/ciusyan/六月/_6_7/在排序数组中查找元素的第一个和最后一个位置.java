package run.ciusyan.六月._6_7;

/**
 * https://leetcode.cn/problems/find-first-and-last-position-of-element-in-sorted-array/
 */
public class 在排序数组中查找元素的第一个和最后一个位置 {
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length < 2) return new int[] {-1, -1};

        // 找出第一个 target 所在的索引
        int first = search(nums, 0, nums.length, target);

        // 说明序列中不存在 target
        if (first == nums.length || nums[first] != target) return new int[] {-1, -1};

        // 后面的索引是 target + 1 的索引位置 - 1
        return new int[] {first, search(nums, 0, nums.length, target + 1) - 1};
    }

    /** 在 [begin, end) 中搜索 target 的值，而且是返回第一个（如果有） */
    private int search(int[] nums, int begin, int end, int target) {

        while (begin < end) {
            // 计算中间位置
            int mid = (begin + end) >> 1;

            if (nums[mid] >= target) {
                // 找到相同的值也往中间挪动
                // 说明中值比 target 大，需要往前移动
                end = mid;
            } else {
                // 说明中值比 target 小，需要往后挪动
                begin = mid + 1;
            }
        }

        // 来到这里肯定是 begin = end 等于 插入位置了
        return begin;
    }
}
