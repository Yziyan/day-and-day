package run.ciusyan._5_28;

import run.ciusyan.common.TreeNode;

/**
 * https://leetcode.cn/problems/recover-binary-search-tree/
 */
public class 恢复二叉搜索树 {

    /** 第一个逆序节点 */
    private TreeNode first;

    /** 第二个逆序节点 */
    private TreeNode second;

    /** 每次遍历的前一个节点 */
    private TreeNode prev;

    public void recoverTree(TreeNode root) {
        if (root == null) return;

        // 中序遍历找出 first 和 second 两个逆序节点
        inorder(root);

        // 交换两个逆序节点的值
        int temp = first.val;
        first.val = second.val;
        second.val = temp;
    }


    /** Morris 中序遍历 */
    private void morris(TreeNode root) {
        if (root == null) return;

        // 用于遍历的节点
        TreeNode node = root;
        // node 为 null 时，说明遍历完了
        while (node != null) {

            // 当前节点左子节点不为空时
            if (node.left != null) {
                // 找到当前节点的前驱节点

                TreeNode prec = node.left;

                // 但是需要注意，有可能当前节点的右子节点已经串起来了
                while (prec.right != null && prev.right != node) {
                    // 一路向右，直到不能往右走
                    prec = prec.right;
                }

                // 查看之前有没有已经串起来
                if (prec.right == null) {
                    // 说明之前没有串起来
                    // 找到了前驱节点，将它的右边串起自己
                    prec.right = node;
                    // 然后将节点往左走
                    node = node.left;
                } else {
                    // 说明之前已经串起来了，并且是从前驱刚回来
                    // 访问当前节点
                    visit(node);
                    // 访问完还原二叉树
                    prec.right = null;

                    // 然后节点往右走
                    node = node.right;
                }
            } else {
                // 说明不能往左走了，直接访问当前节点
                visit(node);
                // 访问完成然后往右走
                node = node.right;
            }
        }

    }

    /** 访问 node 节点 */
    private void visit(TreeNode node) {
        if (prev != null && prev.val > node.val) {
            // 说明出现了逆序节点

            second = node;
            // 如果first 都不为null了，说明此时遇到的事第二个逆序节点
            if (first != null) return;
            first = prev;
        }

        // 保留当前节点
        prev = node;
    }

    /** 中序遍历 */
    private void inorder(TreeNode root) {
        if (root == null) return;

        // 中序遍历左子树
        inorder(root.left);
        // 访问当前节点
        visit(root);
        // 中序遍历右子树
        inorder(root.right);
    }
}
