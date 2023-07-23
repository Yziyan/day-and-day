package run.ciusyan._七月._7_22;

public class 双向链表实现双端队列 {

    private static class DoubleListNode {
        int value;
        DoubleListNode prev;
        DoubleListNode next;

        public DoubleListNode(int value) {
            this.value = value;
        }
    }

    public static class MyDeque {
        int size;
        DoubleListNode head;
        DoubleListNode tail;

        /** 从左边 Push */
        public void LPush(int v) {
            DoubleListNode node = new DoubleListNode(v);
            if (tail == null) {
                head = node;
                tail = node;
            } else {
                node.next = head;
                head = node;
            }

            size++;
        }

        /** 从右边 Push */
        public void RPush(int v) {
            DoubleListNode node = new DoubleListNode(v);
            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
            size++;
        }

        /** 从左边出队 */
        public int LPoll() {
            int res = 0;
            if (head != null) {
                res = head.value;
                head = head.next;

                size--;
            }

            if (head == null) {
                tail = null;
            }

            return res;
        }

        /** 从右边出队 */
        public int RPoll() {
            int res = 0;
            if (tail != null) {
                res = tail.value;
                tail = tail.prev;
                size--;
            }
            if (tail == null) {
                head = null;
            }

            return res;
        }
    }

    public static void main(String[] args) {
        MyDeque deque = new MyDeque();

        // Test LPush, RPush, Size, and IsEmpty on an empty deque
        deque.LPush(10);
        deque.RPush(20);
        deque.LPush(30);

        // Test LPoll
        int value1 = deque.LPoll();
        if (value1 == 30) {
            System.out.println("LPoll test passed!");
        } else {
            System.out.println("LPoll test failed!");
        }

        int value2 = deque.RPoll();
        if (value2 == 20) {
            System.out.println("RPoll test passed!");
        } else {
            System.out.println("RPoll test failed!");
        }

        deque.LPoll();
        int value3 = deque.LPoll(); // LPoll the last element

        if (value3 == 0) {
            System.out.println("LPoll on an empty deque test passed!");
        } else {
            System.out.println("LPoll on an empty deque test failed!");
        }

        // Test RPoll on an empty deque
        int value4 = deque.RPoll();
        if (value4 == 0) {
            System.out.println("RPoll on an empty deque test passed!");
        } else {
            System.out.println("RPoll on an empty deque test failed!");
        }
    }

}
