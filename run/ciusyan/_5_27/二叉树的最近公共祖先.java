package run.ciusyan._5_27;

import run.ciusyan.common.TreeNode;

/**
 * https://leetcode.cn/problems/lowest-common-ancestor-of-a-binary-tree/
 */
public class 二叉树的最近公共祖先 {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root == q || root == p) return root;

        // 有可能存在于左子树
        TreeNode left = lowestCommonAncestor(root.left, p, q);

        // 有可能存在与右子树
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        // 即存在于左子树，又存在于右子树，说明公共祖先是根节点
        //  因为存在根节点，说明到某一节点时，root == p || root == q 会有一个成立。
        if (left != null && right != null) return root;

        // 到这下面只会有一个为 null ，谁不为 null，祖先就是谁
        return left == null ? right : left;
    }
}
