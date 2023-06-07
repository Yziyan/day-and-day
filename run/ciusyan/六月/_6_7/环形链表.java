package run.ciusyan.六月._6_7;

import run.ciusyan.common.ListNode;

public class 环形链表 {
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) return false;

        // 准备快慢指针
        ListNode slow = head;
        ListNode fast = head.next;

        // 当 fast 或者 fast.next 为 null 时，说明没有环
        while (fast != null && fast.next != null) {
            // 当 fast 追上 slow 的时候，说明有环
            if (slow == fast) return true;

            // 慢指针每次走一步、快指针每次走两步
            slow = slow.next;
            fast = fast.next.next;
        }

        return false;
    }
}
