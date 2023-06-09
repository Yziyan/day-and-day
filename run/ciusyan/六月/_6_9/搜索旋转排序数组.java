package run.ciusyan.六月._6_9;

/**
 * https://leetcode.cn/problems/search-in-rotated-sorted-array/
 */
public class 搜索旋转排序数组 {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) return 0;

        // 对 [begin, end] 进行二分查找
        int begin = 0;
        int end = nums.length - 1;

        while (begin <= end) {
            // 先取出中间位置的索引
            int mid = (begin + end) >> 1;

            // 说明存在 target，返回索引
            if (nums[mid] == target) return mid;

            if (nums[mid] >= nums[begin]) {
                // 那么断点一定不在 [begin, mid)，说明 mid 前是有序的
                //  比如这个序列 [5 6 7 8 9 1 3]
                if (target >= nums[begin] && target < nums[mid]) { // 答案可能在 [begin, mid)
                    // 假设 target = 6，那么就会到这里 [5 6 7]
                    end = mid - 1;
                } else { // 说明答案可能在后半部分
                    // 假设 target = 9，那么就会到这里 [9 1 3]
                    begin = mid + 1;
                }
            } else {
                // 说明断点在 [begin, mid)，mid 的后半部分是有序的，与上面是对称的操作
                if (target > nums[mid] && target <= nums[end]) {
                    // 说明答案可能在后半部分
                    begin = mid + 1;
                } else {
                    // 说明答案可能在前半部分
                    end = mid - 1;
                }
            }
        }

        // 来到这里说明没有找到
        return -1;
    }
}
