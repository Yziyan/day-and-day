package run.ciusyan.五月._5_3;

/**
 * 基于Quick Union + 基于Rank + 基于路径压缩实现的并查集
 * 之前虽然使用基于Rank实现的并查集，已经很大程度抑制树的增长了，但是呢，如果当元素多的时候，还是会使树变得很高
 * 那么会大大影响find的效率，特别是底层的节点。那么，我们为了之后查找变快一些。
 * 我们可以使用路径压缩的方式来优化一下find操作，我们以前不是需要从 v 顺着父节点一路往上查找吗？
 * 那么我们在查找的时候，就将该路径上所有的节点，最终都指向根节点。之后在查找这一路径上的节点，将会是O(1)的操作
 * 这样的思想，其实有点类似于延时删除的策略，比如redis，我们在get的时候，可能会将过期的key清除掉。
 * 再比如sql连接池，将过期的连接先不管他，等下一次用到它的时候，在换一个人来承接这个壳子。
 */
public class UnionFind_QU_R_PC {

    private int[] parents;
    private int[] ranks;

    public UnionFind_QU_R_PC(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("容量必须大于0");

        parents = new int[capacity];
        ranks = new int[capacity];

        for (int i = 0; i < capacity; i++) {
            parents[i] = i;
            ranks[i] = 1;
        }
    }

    /**
     * 在查询v的时候，需要根据父节点，一路往上查找，那我们在查找的时候，顺便更新一下路径上的根节点
     * 让它们直接指向根节点，之后查找他们，就很方便了
     */
    public int find(int v) {
        if (v < 0 || v >= parents.length) throw new IllegalArgumentException("参数不合法");

        // 还是直至它自己就是根节点的时候，退出递归
        if (v != parents[v]) {
            parents[v] = find(v);
        }

        // 最终路径上的值，都已经更新成根节点了，直接返回即可
        return parents[v];
    }

    /** 同之前的实现 */
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;

        if (ranks[p1] > ranks[p2]) {
            // 说明 p2 矮，将它嫁接到p1上面
            parents[p2] = p1;
        } else if (ranks[p1] < ranks[p2]) {
            // 说明 p1 矮，将它嫁接到p2上面
            parents[p1] = p2;
        } else {
            // 一样高，嫁接谁都一样
            parents[p1] = p2;
            // 但是需要更新对应的树高
            ranks[p2] += 1;
        }
    }

    public boolean isSame(int v1, int v2) {
        return find(v1) == find(v2);
    }
}
