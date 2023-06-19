package run.ciusyan.六月._6_19;

import run.ciusyan.common.ListNode;
import run.ciusyan.common.TreeNode;

/**
 * https://leetcode.cn/problems/convert-sorted-list-to-binary-search-tree/description/
 */
public class 有序链表转换二叉搜索树 {
    public TreeNode sortedListToBST1(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return new TreeNode(head.val);

        ListNode curNode = head;
        int count = 0;
        while (curNode != null) {
            count++;
            curNode = curNode.next;
        }

        return buildTree(head, 0, count);
    }

    /** 和 108 题一样的做法 */
    private TreeNode buildTree(ListNode head, int begin, int end) {
        if (end - begin < 1) return null;

        int mid = (begin + end) >> 1;

        // 查找中间节点
        ListNode middleNode = head;
        for (int i = 0; i < mid; i++) {
            middleNode = middleNode.next;
        }

        // 构建中间节点
        TreeNode root = new TreeNode(middleNode.val);
        // 再去构建左子树
        root.left = buildTree(head, begin, mid);
        // 构建右子树
        root.right = buildTree(head, mid + 1, end);

        return root;
    }

    /** 遍历的过程中修改链表的指针 */
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;
        if (head.next == null) return new TreeNode(head.val);

        // 获取中间节点的前驱节点
        ListNode midPrevNode = middlePrev(head);
        // 用 mid 的下一个作为根节点
        TreeNode root = new TreeNode(midPrevNode.next.val);

        // 递归的构建左右子树
        //  先构建右子树，就不用先记录右子树的头结点了，从 mid 节点的后一个节点开始
        root.right = sortedListToBST(midPrevNode.next.next);
        // 构建左子树，但是要先将中间节点置为 null
        midPrevNode.next = null;
        // 现在从头开始遍历时，只能到达中间节点的前驱节点
        root.left = sortedListToBST(head);

        return root;
    }

    /** 查找链表的前驱节点*/
    private ListNode middlePrev(ListNode head) {
        // 准备快慢指针
        ListNode slow = head;

        // 想要返回 中间节点的 prev 节点，初始时只需要将快指针多走一步即可
        ListNode fast = head.next.next;

        while (fast != null && fast.next != null) {
            // 慢指针每次走一步
            slow = slow.next;
            // 快指针每次走两步
            fast = fast.next.next;
        }

        // 当快指针走到末尾时，slow 就是中间的节点的 prev 节点
        return slow;
    }
}
