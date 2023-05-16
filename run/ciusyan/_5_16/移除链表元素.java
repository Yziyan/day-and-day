package run.ciusyan._5_16;

import run.ciusyan.common.ListNode;

/**
 * https://leetcode.cn/problems/remove-linked-list-elements/
 */
public class 移除链表元素 {
    public ListNode removeElements(ListNode head, int val) {
        if (head == null) return null;

        // 接一条新的链表出来，可以搞一个虚拟头结点
        ListNode dummyHead = new ListNode();
        dummyHead.next = head;
        ListNode tail = dummyHead;

        while (head != null) {
            if (head.val != val) {
                // 说明需要保留这个节点，将其接入新链表中
                tail = tail.next = head;
            }

            // 遍历链表
            head = head.next;
        }
        // 但是到达这里，需要将 tail 后面的节点置为 null
        tail.next = null;

        return dummyHead.next;
    }
}
