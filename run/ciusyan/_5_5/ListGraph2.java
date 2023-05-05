package run.ciusyan._5_5;

import run.ciusyan._5_3.UnionFind;
import run.ciusyan._5_4.ListGraph;

import java.util.*;


/**
 * 此处的继承，完全是接上一天图的实现，并无其他含义
 * 而且修改掉了很多访问权限，也不重要，因为只是想记录这一天
 *
 */
public class ListGraph2 <V, E> extends ListGraph<V, E> {

    /** 每一幅图都拥有自己的权值管理器 */
    private WeightManager weightManager;

    /** 每一幅图都拥有自己的边比较器 */
    private Comparator<Edge<V, E>> weightComparator = new Comparator<>() {
        @Override
        public int compare(Edge<V, E> o1, Edge<V, E> o2) {
            return weightManager.compare(o1.weight, o2.weight);
        }
    };

    public ListGraph2(WeightManager weightManager) {
        this.weightManager = weightManager;
    }

    /**
     * 昨天的实现中，我们侧重于prim算法了，使用的最小堆是JDK的优先队列，
     * 但是它不同时支持原地建堆和自定义比较规则两个操作。
     * 那使用我们自己写的堆，将会是一个不错的选择。
     */
    @Override
    public Set<EdgeInfo<V, E>> mstPrim() {
        Set<EdgeInfo<V, E>> result = new HashSet<>();

        // 随机获取一个顶点
        final Iterator<Vertex<V, E>> it = vertices.values().iterator();
        if (!it.hasNext()) return result;
        Vertex<V, E> beginVertex = it.next();

        // 准备一个已切分的集合
        Set<Vertex<V, E>> cuts = new HashSet<>();
        cuts.add(beginVertex);

        // 准备一个最小堆，用于不断获取权值最小边
        MinHeap<Edge<V, E>> minHeap = new MinHeap<>(beginVertex.outEdges, weightComparator);

        // 堆为空、或者已经切完了
        int vertexSize = vertices.size();
        while (!minHeap.isEmpty() && cuts.size() < vertexSize) {
            // 删除堆顶
            Edge<V, E> edge = minHeap.remove();
            // 添加到结果中，但是要先查看这条边的终点是否已经被切分了
            if (cuts.contains(edge.to)) continue;
            result.add(edge.info());

            // 然后将这条边的终点作为起点，继续进行切分操作
            minHeap.addAll(edge.to.outEdges);
        }

        return result;
    }

    /**
     * 同Prim一样，使用自己的最小堆来实现
     */
    @Override
    public Set<EdgeInfo<V, E>> mstKruskal() {
        Set<EdgeInfo<V, E>> result = new HashSet<>();

        // 准备一个最小堆，用于每一次获取最小权边（直接将所有边入堆）
        MinHeap<Edge<V, E>> minHeap = new MinHeap<>(edges, weightComparator);
        // 准备一个并查集，用于判断是否有环
        UnionFind<Vertex<V, E>> unionFind = new UnionFind<>();
        // 将所有顶点初始化为一个单独的集合
        unionFind.makeSet(vertices.values());

        // 堆为空，或者已经有 n - 1条边了
        int resEdgeSize = vertices.size() - 1;
        while (!minHeap.isEmpty() && result.size() < resEdgeSize) {
            // 删除最小边
            Edge<V, E> minEdge = minHeap.remove();

            // 判断起点和终点是否在同一集合
            if (unionFind.isSame(minEdge.from, minEdge.to)) continue;

            // 来到这里，说明加入这条边，不会出现环
            result.add(minEdge.info());

            // 然后将这条边的起点和终点合并到一个集合
            unionFind.union(minEdge.from, minEdge.to);
        }

        return result;
    }

    /** 边权值的管理器 */
    public interface WeightManager<E> {
        /** 比较两个边权值的大小 */
        int compare(E w1, E w2);
    }
}
