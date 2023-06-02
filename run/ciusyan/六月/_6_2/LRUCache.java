package run.ciusyan.六月._6_2;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    /** 准备虚拟头结点 */
    private Node first;
    /** 准备虚拟尾结点 */
    private Node last;

    /** 用于缓存数据 */
    private Map<Integer, Node> cache;

    /** 缓存的元素数量 */
    private int size;

    private int maxCapacity;

    public LRUCache(int capacity) {
        first = new Node();
        last = new Node();

        first.next = last;
        last.prev = first;

        maxCapacity = capacity;

        cache = new HashMap<>();
    }

    public int get(int key) {
        Node node = cache.get(key);
        if (node == null) return -1;

        // 删除此节点，
        remove(node);
        // 插入链表头部
        insertFirst(node);

        return node.value;
    }

    /** 头插法，插入链表头部 */
    private void insertFirst(Node node) {
        // 接后面的线
        first.next.prev = node;
        node.next = first.next;

        // 接前面的线
        first.next = node;
        node.prev = first;
    }

    /** 删除 node 节点 */
    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public void put(int key, int value) {
        Node node = cache.get(key);
        if (node == null) {

            if (size == maxCapacity) {
                // 说明已经满了，需要删除最后一个元素，别忘了缓存也要清除
                cache.remove(last.prev.key);
                remove(last.prev);
            } else {
                size++;
            }

            // 说明以前不存在，新建节点
            node = new Node(key, value);
            // 插入
            cache.put(key, node);
        } else {
            // 说明以前存在，更新值
            node.value = value;

            // 删除节点
            remove(node);
        }

        // 放入链表头部
        insertFirst(node);
    }

    private static class Node {
        int key;
        int value;
        Node prev;
        Node next;

        public Node() { }

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        LRUCache lRUCache = new LRUCache(2);
        lRUCache.put(1, 1); // 缓存是 {1=1}
        lRUCache.put(2, 2); // 缓存是 {1=1, 2=2}
        System.out.println(lRUCache.get(1));    // 返回 1
        lRUCache.put(3, 3); // 该操作会使得关键字 2 作废，缓存是 {1=1, 3=3}
        System.out.println(lRUCache.get(2));    // 返回 -1 (未找到)
        lRUCache.put(4, 4); // 该操作会使得关键字 1 作废，缓存是 {4=4, 3=3}
        System.out.println(lRUCache.get(1));    // 返回 -1 (未找到)
        System.out.println(lRUCache.get(3));    // 返回 3
        System.out.println(lRUCache.get(4));    // 返回 4

    }

}
