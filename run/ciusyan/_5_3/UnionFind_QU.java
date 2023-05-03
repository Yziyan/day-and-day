package run.ciusyan._5_3;

/**
 * 并查集，基于QU（Quick Union）的实现：这种实现find、union两个操作都是O(logn)级别的操作
 * 但是可以通过一些优化手段，将其优化成O(a(n)) a(n) < 5的操作
 * 这里与QF不同的是，在union的时候，不是将根节点是p1的元素，全部嫁接到p2上面，
 * QU只是将 p1 这个元素嫁接到了 p2上面
 * 相当于是，p1帮这一个整体，合并到了p2帮，只是他们的老大p1，现在认p2当老大了，p1的弟子，还是p1管
 */
public class UnionFind_QU {

    private int[] parents;

    public UnionFind_QU(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("容量必须大于0");

        parents = new int[capacity];

        // 先将每一个元素单独一个集合
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
        }
    }

    /**
     * 查找v所属的集合，返回v的根节点
     *  现在v就不是直接跟随着某一帮主了，它的老大，可能也隶属于其他帮派，
     *  所以，v属于什么帮派，得看v的老大p属于什么帮派，直至v的老大是自己
     */
    public int find(int v) {
        if (v < 0 || v >= parents.length) throw new IllegalArgumentException("查询的元素非法");

        while (v != parents[v]) {
            v = parents[v];
        }

        // 来到这里，说明v的老大就是自己，那么就是这个集合的根节点
        return v;
    }

    /**
     * 将 v1 和 v2 合并成一个集合，
     * 其实就是将 v1 老大，变成 v2 的老大，其余的不变。
     */
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;

        // 来到这里，将 p1 -> 设置为 p2
        parents[p1] = p2;
    }

    public boolean isSame(int v1, int v2) {
        return find(v1) == find(v2);
    }
}
