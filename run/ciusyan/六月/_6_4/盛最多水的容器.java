package run.ciusyan.六月._6_4;

/**
 * https://leetcode.cn/problems/container-with-most-water/
 */
public class 盛最多水的容器 {
    public int maxArea(int[] height) {
        if (height == null || height.length < 2) return 0;

        // 最大面积
        int maxArea = 0;

        // 准备前后两个指针
        int left = 0, right = height.length - 1;

        while (left < right) {

            // 用较小的作为高度
            int h = Math.min(height[left], height[right]);

            // 尝试得出新的面积：底 * 高
            maxArea = Math.max(maxArea, (right - left) * h);

            // 但是可以跳过一些不必要的比较
            // 如果左边比现在的 h 还矮，或者相等，都说明之后计算出来的面积不可能变大了
            while (left < right && height[left] <= h) left++;

            // 右边同理可得，
            while (left < right && height[right] <= h) right--;

            // 但是都是和 h 比较，所以上面两个循环，每次只会进入一个
        }

        return maxArea;
    }
}
