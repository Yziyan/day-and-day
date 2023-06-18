package run.ciusyan.六月._6_18;


import run.ciusyan.common.TreeNode;

import java.util.*;

/**
 * https://leetcode.cn/problems/binary-tree-zigzag-level-order-traversal/
 */
public class 二叉树的锯齿形层序遍历 {
    public static List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> results = new ArrayList<>();
        if (root == null) return results;

        // 准备一个队列，用于层序遍历，（双端的可以从队尾操作）
        Deque<TreeNode> deque = new LinkedList<>();
        deque.offer(root);
        // 用于转换方向
        boolean reverse = false;

        while (!deque.isEmpty()) {
            // 队列里现在有的元素，刚好是一层的
            int size = deque.size();
            // 挨个弹出，添加到结果中
            List<Integer> res = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                // false：代表从后往前
                TreeNode node = reverse ? deque.pollLast() : deque.pollFirst();

                if (reverse) {
                    // 说明是现在是从队尾弹出的，需要添加到队头，
                    //  往队头添加时需要先加右边，让左边在最前面
                    if (node.right != null) deque.offerFirst(node.right);
                    if (node.left != null) deque.offerFirst(node.left);
                } else {
                    // 说明现在是从队头弹出的，添加在队尾
                    if (node.left != null) deque.offerLast(node.left);
                    if (node.right != null) deque.offerLast(node.right);
                }

                res.add(node.val);
            }
            // 添加结果，并且要转换方向
            results.add(res);
            reverse = !reverse;
        }

        return results;
    }

    public static void main(String[] args) {
        TreeNode node = new TreeNode(3);
        node.left = new TreeNode(9);
        node.right = new TreeNode(20);
        node.left.left = new TreeNode(10);
        node.left.right = new TreeNode(13);
        node.right.left = new TreeNode(15);
        node.right.right = new TreeNode(7);

        System.out.println(zigzagLevelOrder(node));

    }



}
