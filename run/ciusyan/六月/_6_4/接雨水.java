package run.ciusyan.六月._6_4;


/**
 * https://leetcode.cn/problems/trapping-rain-water/
 */
public class 接雨水 {

    public int trap(int[] height) {
        if (height == null || height.length < 3) return 0;

        int water = 0;
        // 用于指向左右两边
        int left = 1, right = height.length - 1;
        // 用于两边最大值中的较矮的值
        int lowerMax = 0;

        while (left < right) {
            // 记录较低的一边，并且将较矮的哪一边往中间靠
            int lower = height[left] > height[right] ? height[right--] : height[left++];
            // 谁往中间移动了，谁就是两边较矮的值
            lowerMax = Math.max(lowerMax, lower);

            water += lowerMax - lower;
        }

        return water;
    }

    public int trap2(int[] height) {
        if (height == null || height.length < 3) return 0;

        int water = 0;
        // 用于记录左右两边的最大值索引
        int leftMax = 0, rightMax = height.length - 1;
        // 表示左右两边的指针
        int left = 1, right = height.length - 2;

        while (left <= right) {

            if (height[leftMax] < height[rightMax]) {
                // 说明左边的较低，计算左边的柱子

                if (height[left] >= height[leftMax]) {
                    // 说明需要更新左边最大值索引了
                    leftMax = left;
                } else {
                    // 来到这里说明可以装水
                    water += height[leftMax] - height[left];
                }

                // 然后往中间靠
                left++;
            } else {
                // 否则以右边的为参考

                if (height[right] >= height[rightMax]) {
                    // 说明需要更新右边的最大索引了
                    rightMax = right;
                } else {
                    water += height[rightMax] - height[right];
                }

                // 然后往中间靠
                right--;
            }
        }

        return water;
    }

    public int trap1(int[] height) {
        if (height == null || height.length < 3) return 0;

        // 最终能接入的雨水
        int water = 0;

        // 求出每根柱子右边最高的柱子
        int[] rightDp = new int[height.length];
        for (int i = height.length - 2; i >= 1; i--) {
            rightDp[i] = Math.max(rightDp[i + 1], height[i + 1]);
        }

        // 每根柱子左边最高的柱子
        int leftDp = 0;

        // 第一根柱子和最后一根柱子肯定接不了雨水，所以不用计算
        for (int i = 1; i < height.length - 1; i++) {
            // 先计算出左边最高的柱子
            leftDp = Math.max(leftDp, height[i - 1]);

            // 获取一个最小的高
            int minH = Math.min(leftDp, rightDp[i]);

            // 然后看这棵柱子能否接水
            if (height[i] < minH) {
                water += minH - height[i];
            }
        }

        return water;
    }
}
