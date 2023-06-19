package run.ciusyan.六月._6_19;

import run.ciusyan.common.TreeNode;

/**
 * https://leetcode.cn/problems/convert-sorted-array-to-binary-search-tree/
 */
public class 将有序数组转换为二叉搜索树 {
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) return null;

        // 构建 [0, nums.length) 内的二叉搜索树
        return buildTree(nums, 0, nums.length);
    }

    /** 构建 [begin, end) 范围内的二叉搜索树 */
    private TreeNode buildTree(int[] nums, int begin, int end) {
        // 如果只有
        if (end - begin < 1) return null;

        // 计算出中间的索引
        int mid = (begin + end) >> 1;
        TreeNode root = new TreeNode(nums[mid]);
        // 构建左子树
        root.left = buildTree(nums, begin, mid);
        // 构建右子树
        root.right = buildTree(nums, mid + 1, end);

        return root;
    }

}
