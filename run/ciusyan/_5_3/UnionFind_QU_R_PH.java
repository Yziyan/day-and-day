package run.ciusyan._5_3;

/**
 * 基于QU + Rank + 路径减半的实现：
 * 这种实现和路径分裂差不多，都是觉得路径压缩的时候，要压缩整条路径，可能使find操作降速非常多
 * 路径减半也是想要中和一下两者，它是指每隔一个节点，就将它指向它的祖父节点。
 * 因为隔了一个节点，才操作，所以操作的节点就减少一半了，再加上只是指向它的祖父节点，只需要跳2步
 */
public class UnionFind_QU_R_PH {

    private int[] parents;
    private int[] ranks;

    public UnionFind_QU_R_PH(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("容量必须大于0");

        parents = new int[capacity];
        ranks = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            parents[i] = i;
            ranks[i] = 1;
        }
    }

    /**
     * 这里采用路径减半的方式来使树变得扁平化，
     * 我们每隔一个节点，就将它嫁接到它的祖父节点上面去，
     * 然后让他的祖父节点也做此操作，所以也没必要使用递归的写法了
     */
    public int find(int v) {
        if (v < 0 || v >= parents.length) throw new IllegalArgumentException("参数不合法");

        // 还是只要能往上走，那就不太能停
        while (v != parents[v]) {
            // 将自己嫁接到祖父节点上面 parent.parent
            parents[v] = parents[parents[v]];

            // 然后让祖父节点也做此操作，此时的祖父节点已经变成 parents[v]了
            v = parents[v];
        }

        return v;
    }

    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;

        if (ranks[p1] > ranks[p2]) {
            parents[p2] = p1;
        } else if (ranks[p1] < ranks[p2]) {
            parents[p1] = p2;
        } else {
            parents[p1] = p2;
            ranks[p2] += 1;
        }
    }
}
