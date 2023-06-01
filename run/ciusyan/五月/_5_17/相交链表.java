package run.ciusyan.五月._5_17;

import run.ciusyan.common.ListNode;

/**
 * https://leetcode.cn/problems/intersection-of-two-linked-lists/
 */
public class 相交链表 {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;

        // 用两个节点，用于遍历 A 和 B
        ListNode curA = headA;
        ListNode curB = headB;

        // 这里不能看 val 是否相等，只能看他们是否是同一个节点。
        while (curA != curB) {
            // 看看 A 遍历完没有，遍历完了就接着 B 遍历
            curA = curA == null ? headB : curA.next;
            // 看看 B 遍历完没有，遍历完了就接着 A 遍历
            curB = curB == null ? headA : curB.next;

            // 即使是最坏情况，两条链表走到最后，它们走的长度都是一样的，那么最终都会走到 null
            //  所以也会退出循环
        }

        return curA;
    }
}
