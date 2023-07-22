package run.ciusyan._七月._7_22;

import java.util.List;

public class 链表翻转 {

    private static class Node {
        int value;
        Node next;

        public Node(int value) {
            this.value = value;
        }
    }

    /** 翻转单向链表 */
    public static Node reverseLinkedList(Node head) {
        if (head == null || head.next == null) return head;

        // 前后两个节点
        Node prev = null;
        Node next = null;

        while (head != null) {
            // 先记录后面的节点
            next = head.next;
            head.next = prev;
            prev = head;
            // 最后将 head 调整到后面的节点去
            head = next;
        }

        return prev;
    }

    private static class DoubleNode {
        int value;
        DoubleNode prev;
        DoubleNode next;
        public DoubleNode(int value) {
            this.value = value;
        }
    }

    /** 翻转双向链表 */
    public static DoubleNode reverseLinkedList(DoubleNode head) {
        if (head == null) return head;

        DoubleNode prev = null;
        DoubleNode next = null;

        while (head != null) {
            next = head.next;
            head.next = prev;
            head.prev = next;
            prev = head;
            head = next;
        }

        return prev;
    }

    public static void printLinkedList(DoubleNode head) {
        while (head != null) {
            System.out.print(head.value + " ");
            head = head.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Create a doubly linked list: 1 <-> 2 <-> 3 <-> 4
        DoubleNode head = new DoubleNode(1);
        DoubleNode node2 = new DoubleNode(2);
        DoubleNode node3 = new DoubleNode(3);
        DoubleNode node4 = new DoubleNode(4);

        head.next = node2;
        node2.prev = head;
        node2.next = node3;
        node3.prev = node2;
        node3.next = node4;
        node4.prev = node3;

        System.out.println("Original linked list:");
        printLinkedList(head);

        // Reverse the linked list
        DoubleNode reversedHead = reverseLinkedList(head);

        System.out.println("Reversed linked list:");
        printLinkedList(reversedHead);
    }

}
