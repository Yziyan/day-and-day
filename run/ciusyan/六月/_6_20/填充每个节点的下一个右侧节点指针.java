package run.ciusyan.六月._6_20;


/**
 * https://leetcode.cn/problems/populating-next-right-pointers-in-each-node/
 */
public class 填充每个节点的下一个右侧节点指针 {
    public Node connect(Node root) {
        if (root == null) return null;

        // 准备一个队列，用于层序遍历
        //  如果使用 JDK 的队列，那么需要 O(n) 的空间复杂度
        // Queue<Node> queue = new LinkedList<>();

        // 所以这里使用自己写的队列，
        //  相当于在题目给定的 Node 基础上改造的，所以不占用额外的空间
        MyQueue queue = new MyQueue();
        queue.offer(root);

        while (!queue.isEmpty()) {
            // 获取当前队列中的元素，就是这一层的所有节点
            int size = queue.size();

            Node prev = null;
            // 遍历当前层所有的节点
            for (int i = 0; i < size; i++) {
                Node poll = queue.poll();

                // 如果前驱节点有值，将其指向当前节点
                if (prev != null) prev.next = poll;

                // 左右不为空，将左右入队
                if (poll.left != null) queue.offer(poll.left);
                if (poll.right != null) queue.offer(poll.right);

                prev = poll;
            }
        }

        return root;
    }

    /** 直接使用题目给定的 Node，将其改造成队列 */
    private static class MyQueue {
        /** 头结点 */
        Node front;
        /** 尾结点 */
        Node rear;
        int size;

        /** 入队 */
        public void offer(Node node) {
            size++;
            if (front == null) {
                // 说明是第一次添加节点
                front = node;
                rear = node;
            } else {
                // 来到这里，需要将节点串起来
                rear.next = node;
                rear = node;
            }
        }

        /** 出队 */
        public Node poll() {
            size--;
            // 先取出旧的头结点
            Node oldFront = front;
            front = front.next;
            // 出去的时候，记得将节点引用清除
            oldFront.next = null;

            return oldFront;
        }

        public int size() {
            return size;
        }
        public boolean isEmpty() {
            return size == 0;
        }
    }
}

/** 题目给定的 Node */
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}

    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
}