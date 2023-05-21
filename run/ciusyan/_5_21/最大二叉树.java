package run.ciusyan._5_21;

import run.ciusyan.common.TreeNode;

/**
 * https://leetcode.cn/problems/maximum-binary-tree/
 */
public class 最大二叉树 {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null || nums.length == 0) return null;

        return buildMaxTree(nums, 0, nums.length);
    }

    /**
     * 将[begin, end)范围内的元素，构建成二叉树
     */
    private TreeNode buildMaxTree(int[] nums, int begin, int end) {
        if (begin == end) return null;

        // 找出最大值索引（根节点）
        int maxIdx = begin;
        for(int i = begin + 1; i < end; i++) {
            if (nums[i] > nums[maxIdx]) {
                maxIdx = i;
            }
        }

        // 构建根节点
        TreeNode root = new TreeNode(nums[maxIdx]);
        // 构建左子树
        root.left = buildMaxTree(nums, begin, maxIdx);
        // 构建右子树
        root.right = buildMaxTree(nums, maxIdx + 1, end);

        return root;
    }
}
