package run.ciusyan._5_3;

/**
 * 基于QU + Rank + 路径分裂的实现：
 * 上一种实现，相当于对路径上的每一个节点，都需要进行压缩的操作，虽然确实将树更加扁平化了，
 * 但是可能使find的操作，更加耗时，那么，我们适当调节一下，让路径上的每一个节点，都指向它的祖父节点。
 * 那么，只需要跳两步，然而也能使树更加的扁平
 */
public class UnionFInd_QU_R_PS {

    private int[] parents;
    private int[] ranks;

    public UnionFInd_QU_R_PS(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("容量必须大于0");

        parents = new int[capacity];
        ranks = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            parents[i] = i;
            ranks[i] = 1;
        }
    }

    /**
     * 这个实现，需要让路径上的每一个节点，都指向它的祖父节点，也就是parent的parent
     * 只需要跳2步，那么根本不需要使用递归了
     */
    public int find(int v) {
        if (v < 0 || v >= parents.length) throw new IllegalArgumentException("参数不合理");

        while (v != parents[v]) {
            int p = parents[v];
            // 将自己指向祖父节点
            parents[v] = parents[p];
            // 让父节点也做此操作
            v = p;
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
            parents[p2] = p1;
            ranks[p1] += 1;
        }
    }
}
