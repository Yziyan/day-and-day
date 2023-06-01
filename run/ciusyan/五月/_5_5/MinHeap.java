package run.ciusyan.五月._5_5;


import java.util.*;
import java.util.stream.Collectors;

/**
 * 最小堆：
 *
 */
public class MinHeap<E> {

    /** 底层使用数组实现 */
    private E[] elements;

    private int size;

    private Comparator<E> comparator;

    private static final int DEFAULT_CAPACITY = 10;

    public MinHeap() {
        this(null);
    }

    static int[] topK(int[] datas, int k) {
        if (datas == null || datas.length <= k) return datas;

        // 准备一个最小堆
        MinHeap<Integer> minHeap = new MinHeap<>();
        for (int data : datas) {
            if (minHeap.size < k) {
                // 直接将其入堆
                minHeap.add(data);
            } else if (minHeap.get() < data) {
                // 说明已经有 k 个元素了，并且最小值比自己小，
                // 放入堆顶。
                // 取代堆顶元素，（排除掉最小值）
                minHeap.replace(data);            }
        }

        // 最终堆里面的元素，就是所需要的 Top K
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = minHeap.remove();
        }

        return res;
    }

    public static void main(String[] args) {
        int[] datas = {4, 1, 5, 7, 2, 8, 6, 2, 5, 3, 13, 9, 10, 40, 13, 24, 100, 654, 24
        , 29, 543, 28, 975, 43, 76, 29, 45};

        System.out.println(Arrays.toString(topK(datas, 5)));
    }

    public MinHeap(Comparator<E> comparator) {
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        this.comparator = comparator;
    }

    public MinHeap(E[] elements, Comparator<E> comparator) {
        this(Arrays.stream(elements).collect(Collectors.toList()), comparator);
    }

    public MinHeap(Collection<E> collection, Comparator<E> comparator) {
        this.comparator = comparator;

        size = comparator == null ? 0 : collection.size();

        if (size == 0) {
            this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        } else {
            int capacity = Math.max(size, DEFAULT_CAPACITY);
            this.elements = (E[]) new Object[capacity];

            int i = 0;
            for (E e : collection) {
                this.elements[i++] = e;
            }

            // 原地建堆
            heapify();
        }

    }

    /**
     * 原地建堆：有两种方式
     * 1、自上而下的上滤：从上往下，将每一元素不断与它的祖父节点进行比较，直至放到合适的位置。
     * 2、自下而上的下滤：从小往上，将每一个元素不断与它较小的祖孙节点进行比较，直至放到合适的位置。
     * 一般采用自下而上的下滤这种操作，因为会有更多的节点，走更短的路径，效率更高。
     */
    private void heapify() {
        // 这里采用自下而上的下滤，从最后一个叶子节点开始
        for (int i = (size >> 1) - 1; i >= 0; i--) {
            siftDown(i);
        }
    }

    /**
     * 下滤操作：
     * 将某一下滤节点，不断的与它较小的子节点比较，如果子节点比自己还小，那么交换它的位置，直至找到合适的位置。
     *
     */
    private void siftDown(int index) {
        int half = size >> 1;

        // 将下滤节点备份
        E element = elements[index];

        // 非叶子节点才能进行下滤（half是第一个非叶子节点）
        while (index < half) {

            // 找到自己的较小的子节点（默认是左子节点）
            int childIndex = (index << 1) + 1;
            E child = elements[childIndex];

            int rightIndex = childIndex + 1;
            if (rightIndex < size && compare(child, elements[rightIndex]) > 0) {
                // 说明右边小
                childIndex = rightIndex;
                child = elements[rightIndex];
            }

            // 将下滤节点与较小的子节点比较
            if (compare(element, child) <= 0) break;

            // 来到这里，说明子节点更小，将子节点上移
            elements[index] = child;
            index = childIndex;
        }

        // 最后要将下滤节点放到合适的位置
        elements[index] = element;
    }

    /** 删除堆顶元素 */
    public E remove() {
        if (size <= 0) return null;

        // 将堆尾放到堆顶，然后进行下滤
        E old = elements[0];
        elements[0] = elements[--size];
        elements[size] = null; // 删除最后一个元素
        siftDown(0);

        return old;
    }

    /** 取代堆顶元素 */
    public E replace(E element) {
        if (element == null) return null;

        E old = elements[0];
        elements[0] = element;
        if (size == 0) {
            // 说明以前没有元素
            size++;
        } else {
            // 说明以前有元素
            siftDown(0);
        }

        return old;
    }

    /** 获取堆顶元素（最小值） */
    public E get() {
        if (size <= 0) return null;

        return elements[0];
    }

    /** 添加元素到堆尾 */
    public void add(E element) {
        if (element == null) return;
        // 添加新的元素，可能需要扩容
        ensureCapacity(size + 1);

        // 然后将元素添加到末尾后
        elements[size] = element;
        // 对堆尾元素进行上滤操作，需要将 size++
        siftUp(size++);
    }

    /** 批量添加 */
    public void addAll(Collection<E> collection) {
        if (collection == null) return;

        collection.forEach(this::add);
    }

    /**
     * 上滤操作：
     * 将上滤节点不断的与祖父节点比较，若父节点比自己还大，那么交换它们，直至放入合适的位置
     */
    private void siftUp(int index) {
        // 备份上滤节点
        E element = elements[index];

        // 不断进行上滤操作，有父节点，才进行上滤操作
        while (index > 0) {
            // 取出父节点索引
            int parentIndex = (index - 1) >> 1;
            E parent = elements[parentIndex];

            // 将自己与父节点比较
            if (compare(element, parent) >= 0) break;

            // 来到这里说明父节点要大，将父节点下移
            elements[index] = parent;
            index = parentIndex;
        }

        // 最后别忘记将上滤节点放置合适位置
        elements[index] = element;
    }

    /**
     * 扩容操作
     * @param capacity：至少需要的容量
     */
    private void ensureCapacity(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;

        // 来到这里，说明需要扩容（为原来的1.5倍）
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        E[] newElements = (E[]) new Object[newCapacity];

        // 元素拷贝
        for (int i = 0; i < size; i++) {
            newElements[i] = elements[i];
        }

        elements = newElements;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** 清空所有元素 */
    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }

        size = 0;
    }

    /**
     * 比较 e1 和 e2 的大小
     */
    private int compare(E e1, E e2) {
        return comparator != null ? comparator.compare(e1, e2) : ((Comparable)e1).compareTo(e2);
    }
}
