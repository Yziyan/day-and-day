package run.ciusyan.五月._5_18;

import run.ciusyan.common.ListNode;

/**
 * https://leetcode.cn/problems/palindrome-linked-list/
 */
public class 回文链表 {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;

        // 找到链表的中心节点
        ListNode central = findCentral(head);
        // 从中心节点的下一个开始，反转链表
        ListNode reverse = reverse(central.next);
        // 将前面和反转的半段链表挨个比较

        while (reverse != null) {
            // 直到reverse为空
            if (reverse .val != head.val) return false;

            reverse = reverse.next;
            head = head.next;
        }

        return true;
    }

    /** 利用快慢指针寻找中心节点 */
    private ListNode findCentral(ListNode head) {
        if (head == null || head.next == null) return head;

        // 准备快慢指针
        ListNode slow = head;
        ListNode fast = head.next;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    /** 采用头插法 翻转链表 */
    private ListNode reverse(ListNode head) {
        if (head == null || head.next == null) return head;

        // 采用头插法翻转链表
        ListNode newHead = null;
        while (head != null) {
            ListNode temp = head.next;
            head.next = newHead;
            newHead = head;
            head = temp;
        }

        return newHead;
    }
}
