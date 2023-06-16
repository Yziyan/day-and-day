package run.ciusyan.六月._6_16;

import run.ciusyan.common.TreeNode;

/**
 * https://leetcode.cn/problems/validate-binary-search-tree/
 */
public class 验证二叉搜索树 {

    /** 方法一：递归比较当前节点和左右子树的大小。 */
    public boolean isValidBST(TreeNode root) {
        if (root == null) return true;

        // 从左子树开始去搜索
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    /** 必须要保证当前节点的值是这样的顺序：lower < root.val < upper */
    private boolean isValidBST(TreeNode root, long lower, long upper) {
        if (root == null) return true;

        // 说明当前的值乱序了
        if (root.val <= lower || root.val >= upper) return false;

        // 验证左右子树必须也要满足，
        //  验证左子树时，val 是最大的值，
        //  验证右子树时，val 是最小的值
        return isValidBST(root.left, lower, root.val) && isValidBST(root.right, root.val, upper);
    }

    /** 每一次的前驱节点 */
    private TreeNode prev;

    /** 方法二：中序遍历二叉搜索树是有序的 */
    public boolean isValidBST1(TreeNode root) {
        if (root == null) return true;

        // 从左子树开始去搜索
        return inorder(root);
    }

    /** 中序遍历验证 */
    private boolean inorder(TreeNode node) {
        if (node == null) return true;

        // 中序遍历左子树失败了，直接返回
        if (!inorder(node.left)) return false;

        // 说明逆序了
        if (prev != null && prev.val >= node.val) return false;
        prev = node;

        // 返回中序遍历右子树的结果
        return inorder(node.right);
    }
}
