package run.ciusyan.五月._5_3;

/**
 * 并查集实现QF(Quick Find)：find的时间复杂度为 O(1)，union的时间复杂度为O(n)
 * 它的实现很简单，就是合并的时候，将一个集合的根节点，全部变成另一个集合的根节点。
 */
public class UnionFind_QF {

    /** 使用数组来表示集合关系 */
    private int[] parents;

    public UnionFind_QF(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("容量必须大于0");

        parents = new int[capacity];
        // 默认每一个元素就是一个集合
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
        }
    }

    /**
     * 查看v所属的集合，返回 v 的根节点，为什么是 QuickFind 呢？
     * 因为相当于每一个元素，都直接指向根节点。当然很快呐。
     * 所以要是元素合法，那么直接可以从集合中返回它的根节点
     * 相当于每一个元素，直接隶属于帮主，那么要找帮主，当然直接返回即可呐！
     */
    public int find(int v) {
        // 当然要检查 v 的范围了
        if (v < 0 || v >= parents.length) throw new IllegalArgumentException("元素不合法");

        return parents[v];
    }

    /**
     * 将 v1 和 v2 合并成一个集合，
     * 我们这里同意将 v1 集合的所有元素的根节点，变成 v2
     * 相当于将 v1 这个帮派，直接合并到v2，从此江湖再无v1帮
     */
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        // 说明本身就在一个帮派了
        if (p1 == p2) return;

        // 来到这里，直接将 v1的所有元素的根节点变成 p2
        for (int i = 0; i < parents.length; i++) {
            if (parents[i] == p1) {
                // 说明以前是p1帮
                parents[i] = p2;
            }
        }
    }

    /** 查询v1和v2是否属于同一集合 */
    public boolean isSame(int v1, int v2) {
        return find(v1) == find(v2);
    }
}
