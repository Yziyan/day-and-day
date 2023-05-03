package run.ciusyan._5_3;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 实现一个通用版并查集：使用 QuickUnion + Rank + 路径减半
 * 这里面使用 HashMap 表示多个集合，使用链表，将每一集合的元素串起来
 */
public class UnionFind<V> {

    /** 表示多个集合 */
    private Map<V, Node<V>> sets;

    public UnionFind() {
        // 初始化集合
        this.sets = new HashMap<>();
    }

    // 将 v 初始化成一个单独的集合
    public void makeSet(V v) {
        if (sets.containsKey(v)) return;

        sets.put(v, new Node<>(v));
    }

    /**
     * 查找 v 所属的集合，（返回 v 的根节点）
     * 但是我们的 value 是存储在节点内部的，所以我们实现一个查找节点的方法。
     */
    public V find(V v) {
        Node<V> node = findNode(v);

        return node == null ? null : node.value;
    }

    /**
     * 根据 v 返回对应的根节点
     * 这里采用路径减半的方式实现
     */
    private Node<V> findNode(V v) {
        Node<V> node = sets.get(v);
        if (node == null) return null;

        // 来到这里，节点肯定存在了，沿着父节点一路往上找，直至父节点就是自己
        while (!Objects.equals(node, node.parent)) {
            // 将自己指向祖父节点
            node.parent = node.parent.parent;

            // 然后让祖父节点也做此操作
            node = node.parent;
        }

        return node;
    }

    /**
     * 将 v1 和 v2 合并成一个集合
     * 这里采用基于 Rank 的优化
     */
    public void union(V v1, V v2) {
        Node<V> p1 = findNode(v1);
        Node<V> p2 = findNode(v2);

        // 说明可能有节点都还没有加入并查集中，不可以合并
        if (p1 == null || p2 == null) return;

        // 这里是说明它们本身就在一个集合中了，没必要合并了
        if (Objects.equals(p1.value, p2.value)) return;

        if (p1.rank > p2.rank) {
            // 说明 p2 要矮，将它嫁接到 p1 上面
            p2.parent = p1;
        } else if (p1.rank < p2.rank) {
            // 说明 p1 要矮，将它嫁接到 p2 上面
            p1.parent = p2;
        } else {
            // 说明一样高，嫁接谁都一样
            p1.parent = p2;
            // 但是需要更新对应的树高
            p2.rank++;
        }
    }

    public boolean isSame(V v1, V v2) {
        Node<V> p1 = findNode(v1);
        Node<V> p2 = findNode(v2);
        if (p1 == null || p2 == null) return false;

        return p1.value == p2.value;
    }

    /**
     * 内部节点类，同一集合，使用链表将元素串起来
     */
    private static class Node<V> {
        // 父节点，默认就指向自己
        Node<V> parent = this;
        // 集合的高度
        int rank;
        // 节点的值
        V value;

        public Node(V value) {
            this.value = value;
        }
    }
}
