package run.ciusyan.五月._5_3;

/**
 * 在基于QU操作下实现的并查集，它两个操作的平均时间复杂度都是O(logn)，但是，集合可能会被退化成链表，
 * 需要O(n)的时间复杂度。这是因为我们以前合并的时候，无脑的将 v1 的老大变成了 v2的老大，
 * 那么，我们为了防止这种事情发生，我们可以稍微聪明一点，使用基于Size的优化：
 * 在合并的时候，将元素较少的集合，合并到元素较多的集合上。
 */
public class UnionFInd_QU_S {

    /** 记录每一个元素的父节点 */
    private int[] parents;
    /** 记录每一个集合的元素数量 */
    private int[] sizes;

    public UnionFInd_QU_S(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("容量必须大于0");

        parents = new int[capacity];
        sizes = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            // 默认将每一个元素都初始化为一个集合
            parents[i] = i;

            // 每个集合初始化大小为 1
            sizes[i] = 1;
        }
    }

    /**
     * 查找还是和QU一样，一路往上查找，直至根节点就是自己
     */
    public int find(int v) {
        if (v < 0 || v >= parents.length) throw new IllegalArgumentException("参数不合法");

        while (v != parents[v]) {
            v = parents[v];
        }

        return v;
    }

    /**
     * 之前合并的时候太笨了，无脑的将v1所在的集合嫁接到了v2上
     * 我们这里，采用基于Size的方式优化一下：将元素较少的集合，嫁接到元素较多的集合上面
     */
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;

        // 根据集合元素，决定将谁嫁接到谁上面
        if (sizes[p1] > sizes[p2]) {
            // 说明 p2 的元素少，将 p2 嫁接到 p1 上面
            parents[p2] = p1;
            // 别忘了更新size
            sizes[p2] += sizes[p1];
        } else {
            // 说明 p1 的元素少，相等放哪都一样
            // 将 p1 嫁接到 p2上
            parents[p1] = p2;
            // 别忘记更新size
            sizes[p2] += sizes[p1];
        }
    }

    public boolean isSame(int v1, int v2) {
        return find(v1) == find(v2);
    }
}
