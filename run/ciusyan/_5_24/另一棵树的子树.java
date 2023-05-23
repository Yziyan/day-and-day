package run.ciusyan._5_24;


import run.ciusyan.common.TreeNode;

import java.util.Arrays;

/**
 * https://leetcode.cn/problems/subtree-of-another-tree/
 */
public class 另一棵树的子树 {
    public static boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null || subRoot == null) return false;

        // 将两棵树序列化
        String rootStr = serializeTree(root);
        String sbRootStr = serializeTree(subRoot);

        return rootStr.contains(sbRootStr);
    }

    /** 将 root 序列化 */
    private static String serializeTree(TreeNode root) {
        // 这里是防止误判类似这样的情况： 12# 和 2#
        StringBuilder sb = new StringBuilder("#");

        // 前序遍历的方式
        serializeTree(root, sb);

        return sb.toString();
    }

    /** 将一棵树序列化（前序遍历的方式） */
    private static void serializeTree(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append("!").append("#");
            return;
        }

        sb.append(root.val).append("#");
        serializeTree(root.left, sb);
        serializeTree(root.right, sb);
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(4);
        root.right = new TreeNode(5);

        root.left.left = new TreeNode(1);
        root.left.right = new TreeNode(2);

        root.left.right.left = new TreeNode(0);


        TreeNode subRoot = new TreeNode(4);
        subRoot.left = new TreeNode(1);
        subRoot.right = new TreeNode(2);
        String rootStr = serializeTree(root);
        String sbRootStr = serializeTree(subRoot);
        System.out.println(rootStr);
        System.out.println(sbRootStr);
        System.out.println(rootStr.contains(sbRootStr));
        System.out.println(isSubtree(root, subRoot));
    }

}
