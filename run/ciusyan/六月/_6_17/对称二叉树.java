package run.ciusyan.六月._6_17;

import run.ciusyan.common.TreeNode;

/**
 * https://leetcode.cn/problems/symmetric-tree/
 */
public class 对称二叉树 {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) return true;

        // 从左子树和右子树开始比较即可
        return isSymmetric(root.left, root.right);
    }

    /** 判断 left 和 right 的值是否相等 */
    private boolean isSymmetric(TreeNode left, TreeNode right) {
        // 说明到达树的末尾了
        if (left == null && right == null) return true;

        if (left != null && right != null) {
            // 对称要满足：1、左右节点的值相等 2、左子树的左子节点要等于右子树的右子节点 3、左子树的右子节点要等于右子树的左子节点
            return left.val == right.val && isSymmetric(left.left, right.right) && isSymmetric(left.right, right.left);
        }

        // 到这里说明有一边是 null 的，肯定不对称了。
        return false;
    }
}
