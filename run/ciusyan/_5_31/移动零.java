package run.ciusyan._5_31;

/**
 * https://leetcode.cn/problems/move-zeroes/
 */
public class 移动零 {
    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length < 2) return;
        
        // 遍历和放置的位置
        for (int i = 0, plate = 0; i < nums.length; i++) {
            // 遇到零 i++ 即可
            if (nums[i] == 0) continue;

            // 在这里说明不是 0，如果 plate != cur，说明可以放置元素
            if (plate != i) {
                //  将此元素放到 plate 位置，
                nums[plate] = nums[i];
                nums[i] = 0;
            }

            // 然后 plate 往后移动
            plate++;
        }
    }
}
