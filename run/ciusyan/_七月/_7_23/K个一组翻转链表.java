package run.ciusyan._七月._7_23;

import run.ciusyan.common.ListNode;
import run.ciusyan.六月._6_25.平衡二叉树;

/**
 * https://leetcode.cn/problems/reverse-nodes-in-k-group/
 */
public class K个一组翻转链表 {
    public static ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null) return head;

        // 先拿到第一轮的 head
        ListNode start = head;
        ListNode end = kEnd(start, k);
        if (end == null) return head;

        // 第一组的头结点，就是最后要返回的节点
        head = end;
        // 翻转
        reverse(start, end);

        // 上一组的结尾节点
        ListNode lastEnd = start;

        // 只要 end 不为 null，就重复做刚刚的事情
        while (lastEnd.next != null) {
            // 从下一组开始
            start = lastEnd.next;
            end = kEnd(start, k);
            if (end == null) return head;

            reverse(start, end);
            // 需要将上一组的与这一组的连接点修复
            lastEnd.next = end;
            lastEnd = start;
        }

        return head;
    }

    /** 数 k 个元素，返回第 k 个节点 */
    private static ListNode kEnd(ListNode head, int k) {
        while (--k != 0 && head != null) {
            head = head.next;
        }

        return head;
    }

    /** 翻转 [start, end) 的节点，并且将start所在组的末尾指向下一组的开头 */
    private static void reverse(ListNode start, ListNode end) {
        // 往下走一个
        end = end.next;

        ListNode prev = null;
        ListNode next = null;

        // 需要保留 start，在遍历完之后需要使用
        ListNode cur = start;

        // 当翻转到 end 的时候，就别翻转了
        while (cur != end) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }

        // 将翻转后的尾结点，指向末尾节点，
        start.next = end;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        head.next.next = new ListNode(3);
        head.next.next.next = new ListNode(4);
        head.next.next.next.next = new ListNode(5);

        ListNode listNode = reverseKGroup(head, 2);

        while (listNode != null) {
            System.out.print(listNode.val + " ");
            listNode = listNode.next;
        }
    }

}
