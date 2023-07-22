package run.ciusyan._七月._7_22;

public class 单链表实现队列 {

    private static class Node<V> {
        V v;
        Node next;

        public Node(V v) {
            this.v = v;
        }
    }

    public static class MyQueue<V> {
        Node<V> head;
        Node<V> last;
        int size;

        public boolean isEmpty() {
            return size == 0;
        }

        public int size() {
            return size;
        }

        public void push(V v) {
            Node<V> node = new Node<>(v);
            if (last == null) {
                // 说明是第一次添加
                head = node;
                last = node;
            } else {
                // 不是第一次插入，采用尾插法
                last.next = node;
                last = node;
            }
            size++;
        }

        public V poll() {
            V v = null;

            if (head != null) {
                v = head.v;
                head = head.next;
                size--;
            }

            // 如果 head 走到了 null，说明 last 也得到 null
            if (head == null) last = null;

            return v;
        }

        public V peek() {
            return head == null ? null : head.v;
        }

    }


}
