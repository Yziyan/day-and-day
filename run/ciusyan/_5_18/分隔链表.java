package run.ciusyan._5_18;

import run.ciusyan.common.ListNode;

/**
 * https://leetcode.cn/problems/partition-list/
 */
public class 分隔链表 {
    public ListNode partition(ListNode head, int x) {
        if (head == null) return null;

        // 用于嫁接小于 x 的节点
        ListNode front = new ListNode();
        ListNode frontTail = front;

        // 用于嫁接大于等于 x 的节点
        ListNode back = new ListNode();
        ListNode backTail = back;

        // 遍历链表
        while (head != null) {
            if (head.val < x) {
                // 接前面
                frontTail = frontTail.next = head;
            } else {
                // 接后面
                backTail = backTail.next = head;
            }

            head = head.next;
        }
        // 但是需要将第二条链表的末尾，手动清空一下
        // 要不然遇到比 x 小的节点，还有条线不能够清楚
        backTail.next = null;

        // 将两条链表接起来
        frontTail.next = back.next;

        return front.next;
    }
}
