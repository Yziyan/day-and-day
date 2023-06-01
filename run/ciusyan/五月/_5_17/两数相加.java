package run.ciusyan.五月._5_17;

import run.ciusyan.common.ListNode;

/**
 * https://leetcode.cn/problems/add-two-numbers/
 */
public class 两数相加 {

    /**
     * 若最终需要返回一条链表：
     *  看看能不能直接重新构建一条，而构建链表的时候，
     *  一般用一个虚拟头结点 + tail 节点，可以省去很多判断，tail 用于串接后面的节点。
     *
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        // 新建一条链表
        ListNode dummyHead = new ListNode();
        ListNode tail = dummyHead;

        // 进位值
        int carry = 0;

        // 只要一条链表还不为空，那就继续相加
        while (l1 != null || l2 != null) {
            // 取出 v1 的值，并让 l1 往下走
            int v1 = 0;
            if (l1 != null) {
                v1 = l1.val;
                l1 = l1.next;

            }

            // 取出 v2 的值，并让 l2 往下走
            int v2 = 0;
            if (l2 != null) {
                v2 = l2.val;
                l2 = l2.next;
            }

            // 相加，别忘了要加进位
            int val = v1 + v2 + carry;
            if (val < 10) {
                // 说明需要清空进位值，万一以前是 1 呢
                carry = 0;
            } else {
                // 说明需要更新进位值，而进位值只可能是 1
                carry = 1;

                // 只要个位数
                val %= 10;
            }

            // 来到这里，可以构建并串起新节点了
            tail = tail.next = new ListNode(val);
        }
        // 但是在退出循环后，还需要看看进位是否有值，
        if (carry != 0) {
            // 说明还需要串一个节点
            tail.next = new ListNode(carry);
        }

        return dummyHead.next;
    }
}
