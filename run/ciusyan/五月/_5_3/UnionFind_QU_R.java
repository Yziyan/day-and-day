package run.ciusyan.五月._5_3;

/**
 * 基于Quick Union、基于Rank的优化，虽然我们使用基于Size的优化，已经很大程度限制了树高了，但有的时候，
 * 元素较少的集合可能更高，将它合并到更矮的集合，其实也会使高度增长。所以，我们又搞出了一种，可能更科学的优化方式：
 * 基于Rank的优化，也就是将树高较矮的集合，合并到较高的集合上去。
 */
public class UnionFind_QU_R {

    /** 每一个集合的根节点 */
    private int[] parents;
    /** 每一个集合的高度 */
    private int[] ranks;

    public UnionFind_QU_R(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("容量必须大于0");

        parents = new int[capacity];
        ranks = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            // 每一个元素默认一个集合
            parents[i] = i;
            // 每一个集合的高度默认为1
            ranks[i] = 1;
        }
    }

    /** 还是一路往上找，直到根节点是自己 */
    public int find(int v) {
        if (v < 0 || v >= parents.length) throw new IllegalArgumentException("参数不合法");

        while (v != parents[v]) {
            v = parents[v];
        }

        return v;
    }

    /**
     * 比基于Size更聪明，我们这里是将较矮的集合嫁接到较高的集合上去，
     * 那么树高肯定就还是较高的那个集合，不会使整体增高
     * 只有当两个集合一样高的时候，那么将谁嫁接到谁，都是一样的效果，但是会使树长高
     */
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;

        // 将较矮的集合嫁接到较高的上面
        if (ranks[p1] > ranks[p2]) {
            // 说明 p2 较矮，将它嫁接到p1
            parents[p2] = p1;
        } else if (ranks[p1] < ranks[p2]){
            // 说明 p1 较矮，将它嫁接到p2
            parents[p1] = p2;
        } else {
            // 说明两个数一样高，将谁嫁接到谁都一样
            parents[p1] = p2;
            // 但是这里需要更新对应的树高，树会长高 1
            ranks[p2] += 1;
        }
    }

    public boolean isSame(int v1, int v2) {
        return find(v1) == find(v2);
    }
}
