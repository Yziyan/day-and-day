package run.ciusyan._5_21;


import run.ciusyan.common.TreeNode;

import java.util.Arrays;
import java.util.Map;
import java.util.Stack;

/**
 * 和上一道题意一样，但返回值不一样，
 *  要求返回每一个元素的父节点，没有就返回 -1
 */
public class 单调栈 {

    public static void main(String[] args) {
        int[] parents = getMaximumBinaryTreeParent(new int[]{3, 2, 1, 6, 0, 5});
        System.out.println(Arrays.toString(parents));
    }

    /**
     * 其实目的就是分别求解出某元素左、右两边第一个比自己大的数字
     *  那么那个元素的根节点就是 Min {左边第一个大于自己的数, 右边第一个大于自己的数}
     *  那么该如何求解呢？其实可以使用单调栈来求解
     *
     */
    public static int[] getMaximumBinaryTreeParent(int[] nums) {
        if (nums == null || nums.length == 0) return null;

        int length = nums.length;
        // 准备两个数组，分别用于承接每一个元素左、右两边第一个大于自己的数
        int[] leftFMTs = new int[length];
        int[] rightFMTs = new int[length];
        // 初始化
        for (int i = 0; i < length; i++) {
            leftFMTs[i] = -1;
            rightFMTs[i] = -1;
        }

        // 准备一个数组，用于返回parents
        int[] parents = new int[length];

        // 准备一个单调栈（装的是索引）
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < length; i++) {

            // 保证栈的单调
            while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
                // 被弹出去，说明被弹出元素右边第一个比自己大的元素就是当前元素
                rightFMTs[stack.pop()] = i;
            }


            if (!stack.isEmpty()) {
                // 说明左边有人大于自己
                leftFMTs[i] = stack.peek();
            }

            // 到达这里说明可以入栈了
            stack.push(i);
        }

        System.out.println(Arrays.toString(leftFMTs));
        System.out.println(Arrays.toString(rightFMTs));

        // 第 i 个元素的 parent = min{leftFMTs[i], rightFMTs[i]}
        for (int i = 0; i < length; i++) {
            if (leftFMTs[i] == -1 && rightFMTs[i] == -1) {
                // 说明没有父节点
                parents[i] = -1;
                continue;
            }

            if (leftFMTs[i] == -1) {
                // 父节点在右边
                parents[i] = rightFMTs[i];
                continue;
            }

            if (rightFMTs[i] == -1) {
                // 父节点在左边

                parents[i] = leftFMTs[i];
                continue;
            }

            // 来到这里说明左右都有值，那么选较小值，注意比较需要用元素来比较，
            parents[i] = nums[leftFMTs[i]] > nums[rightFMTs[i]] ? rightFMTs[i] : leftFMTs[i];
        }

        return parents;
    }
}
