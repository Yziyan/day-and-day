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

    /** 中序遍历 */
    private void inorder(TreeNode root) {
        if (root == null) return;

        // 中序遍历左子树
        inorder(root.left);

        if (prev != null && prev.val > root.val) {
            // 进来这里说明逆序了

            second = root;
            // 如果first都已经不为 null了，说明此次进来肯定是找到了第二个逆序的节点，
            //  可以退出递归了
            if (first != null) return;
            first = prev;
        }

        // 保留此节点
        prev = root;

        // 中序遍历右子树
        inorder(root.right);
    }
}
