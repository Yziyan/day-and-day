package run.ciusyan._七月._7_22;

public class 单链表实现栈 {

    private static class Node<V> {
        V v;
        Node<V> next;

        public Node(V v) {
            this.v = v;
        }
    }

    public static class MyStack<V> {
        int size;
        Node<V> head;

        public int size() { return size; }

        public boolean isEmpty() { return size == 0; }

        public void push(V v) {
            Node<V> node = new Node<>(v);

            if (head != null) {
                // 采用头插法实现
                node.next = head;
            }
            head = node;

            size++;
        }

        public V pop() {
            V v = null;

            if (head != null) {
                v = head.v;
                head = head.next;
                size--;
            }

            return v;
        }

        public V peek() {
            return head == null ? null : head.v;
        }
    }

}
