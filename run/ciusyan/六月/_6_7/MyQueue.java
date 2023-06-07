package run.ciusyan.六月._6_7;


/**
 * 循环队列
 */
public class MyQueue <E> {

    /** 指向队头的索引 */
    private int front;

    /** 底层存放元素的数组 */
    private E[] elements;

    /** 队列中的元素个数 */
    private int size;

    /** 可以支持扩容，但是先写一个不扩容的吧 */
    public MyQueue(int capacity) {
        elements = (E[]) new Object[capacity];
    }

    /** 入队 */
    public void enQueue(E ele) {
        // 1、判断是否满了
        if (size == elements.length) throw new IllegalArgumentException("队列已经满了");
        // 2、将元素放置在合理的队尾位置，越界后需要回到数组的前面
        elements[(front + size) % elements.length] = ele;
        size++;
    }

    /** 出队 */
    public E deQueue() {
        // 1、判断是否为空
        if (size == 0) throw new IllegalArgumentException("队列为空");

        // 说明队列中有元素，可以将其出队
        E oldEle = elements[front];
        // 先释放队头的空间
        elements[front] = null;
        // 将队头往后挪动，越界后需要回到数组的前面
        front = (front + 1) % elements.length;
        size--;

        return oldEle;
    }

    public static void main(String[] args) {
        MyQueue<Integer> queue = new MyQueue<>(3);
        queue.enQueue(1);
        queue.enQueue(2);
        queue.enQueue(3);
        System.out.println(queue.deQueue()); // 1
        System.out.println(queue.deQueue()); // 2
        queue.enQueue(4);
        queue.enQueue(5);
        System.out.println(queue.deQueue()); // 3
        System.out.println(queue.deQueue()); // 4
        queue.enQueue(6);
        queue.enQueue(7);
        System.out.println(queue.deQueue()); // 5
        System.out.println(queue.deQueue()); // 6
        System.out.println(queue.deQueue()); // 7

        System.out.println(queue.deQueue()); // Exception

    }

}
