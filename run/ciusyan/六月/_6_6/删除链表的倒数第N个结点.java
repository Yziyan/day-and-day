package run.ciusyan.六月._6_6;

import run.ciusyan.common.ListNode;

/**
 * https://leetcode.cn/problems/remove-nth-node-from-end-of-list/
 */
public class 删除链表的倒数第N个结点 {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null || n <= 0) return head;

        // 先统计链表的元素个数

        int count = 0;
        ListNode curNode = head;
        while (curNode != null) {
            count++;
            curNode = curNode.next;
        }

        // 再找到倒数的 n - 1 个节点
        // 说明没有倒数 n 个节点
        if ((count = count - n) < 0) return head;

        // 如果 count = 0，说明需要删除的是第一个节点
        if (count == 0) return head.next;

        // 将节点返回前面
        curNode = head;
        ListNode removeNode = curNode.next;
        while (--count > 0) {
            curNode = curNode.next;
            removeNode = curNode.next;
        }

        // 那么当前节点就是要被删除的节点
        curNode.next = removeNode.next;

        return head;
    }
}
