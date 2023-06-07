package run.ciusyan.六月._6_7;

/**
 * https://leetcode.cn/problems/remove-duplicates-from-sorted-array/
 */
public class 删除有序数组中的重复项 {
    public int removeDuplicates(int[] nums) {
        if (nums == null || nums.length == 0) return 0;

        // 从第二个元素开始放置
        int plate = 1;

        for (int i = 1; i < nums.length; i++) {

            if (nums[i] != nums[i - 1]) {
                // 说明和前一个元素不相等，
                nums[plate++] = nums[i];
            }
        }

        return plate;
    }
}