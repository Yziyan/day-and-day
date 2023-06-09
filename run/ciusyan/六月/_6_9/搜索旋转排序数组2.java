package run.ciusyan.六月._6_9;


/**
 * https://leetcode.cn/problems/search-in-rotated-sorted-array-ii/
 */
public class 搜索旋转排序数组2 {
    public boolean search(int[] nums, int target) {
        if (nums == null || nums.length == 0) return false;

        int begin = 0;
        int end = nums.length - 1;

        while (begin <= end) {
            // 先取出中间索引
            int mid = (begin + end) >> 1;
            if (nums[mid] == target) return true;

            // 说明没有找到，需要尝试往左右两边找。
            if (nums[begin] == nums[end] && nums[begin] == nums[mid]) {
                // 但有可能 begin mid end 都是相等的数，比如 [2 3 3 2 2 2]

                // 跳到第一个可能有序的序列
                while (begin != mid && nums[begin] == nums[mid]) begin++;

                // 来到这里，要么是 begin 和 mid 都重合了，要么是到了第一个有序的序列了
                if (begin == mid) {
                    // 说明重合了，那么答案只可能在 mid 后面了。
                    begin = mid + 1;

                    continue;
                }
            }


            // 能来到这里，那么肯定说明 begin mid end 中至少有一个不相等
            if (nums[begin] != nums[mid]) {
                // 这里说明 end 和 mid 有可能相等，也可能不等
                if (nums[mid] > nums[begin]) {
                    // 说明 [begin, mid] 是有序序列
                    if (target >= nums[begin] && target < nums[mid]) {
                        // 说明答案可能在 [begin, mid) 间
                        end = mid - 1;
                    } else {
                        // 说明答案可能在 (mid, end]
                        begin = mid + 1;
                    }

                } else {
                    // 说明 [mid, end] 是有序序列
                    if (target > nums[mid] && target <= nums[end]) {
                        // 说明答案可能在 (mid, end]
                        begin = mid + 1;
                    } else {
                        // 说明答案可能在 [begin, mid)
                        end = mid - 1;
                    }
                }

            } else {
                // 说明 begin mid end 中至少有一个不相等，并且还说明 begin 和 mid 相等
                //  那么可以推出：mid 和 end 不相等，
                if (nums[mid] < nums[end]) {
                    // 说明后面是有序数组
                    if (target > nums[mid] && target <= nums[end]) {
                        // 说明答案可能在 (mid, end]
                        begin = mid + 1;
                    } else {
                        end = mid - 1;
                    }

                } else {
                    // 说明前面是有序数组
                    if (target >= nums[begin] && target < nums[mid]) {
                        // 说明答案可能在 [begin, mid) 间
                        end = mid - 1;
                    } else {
                        // 说明答案可能在 (mid, end]
                        begin = mid + 1;
                    }
                }
            }
        }

        return false;
    }
}
