package run.ciusyan.五月._5_5;

import run.ciusyan.五月._5_3.UnionFind;
import run.ciusyan.五月._5_4.ListGraph;

import java.util.*;


/**
 * 此处的继承，完全是接上一天图的实现，并无其他含义
 * 而且修改掉了很多访问权限，也不重要，因为只是想记录这一天
 *
 */
public class ListGraph2 <V, E> extends ListGraph<V, E> {

    /** 每一幅图都拥有自己的权值管理器 */
    protected WeightManager<E> weightManager;

    /** 每一幅图都拥有自己的边比较器 */
    protected Comparator<Edge<V, E>> edgeComparator = new Comparator<>() {
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
        MinHeap<Edge<V, E>> minHeap = new MinHeap<>(beginVertex.outEdges, edgeComparator);

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
        MinHeap<Edge<V, E>> minHeap = new MinHeap<>(edges, edgeComparator);
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

    /**
     * Dijkstra实现的单元最短路径算法：
     * Dijkstra 是一种基于贪心策略的一种求解单源最短路径的算法，它的核心是不断对能到达的边进行松弛操作。
     * 从源点开始，不断的对每一条边进行松弛操作，其实就是更新源点到能到达顶点间的距离，每一次松弛操作的意义在于，尝试找出
     * 更短的路径，然后每一次松弛完成后，选择所有路径中长度最短的一条路径，这条路径就是源点到对应终点的最短路径。
     * 它适用于没有负权边的图（有向、无向、无权都可以）
     * @param src：源点
     * @return ：源点到其他的点的最短路径长度。如：<B， 40>
     */
    public Map<V, E> shortPathDijkstra1(V src) {
        Map<V, E> result = new HashMap<>();

        // 获取源点
        Vertex<V, E> srcVertex = vertices.get(src);
        if (srcVertex == null) return result;

        // 记录中间结果
        Map<Vertex<V, E>, E> paths = new HashMap<>();
        // 初始化源点到达能到达点的路径长度
        for (Edge<V, E> edge : srcVertex.outEdges) {
            paths.put(edge.to, edge.weight);
        }

        // 只要 paths 还有顶点没有求出最短路径，那就继续求
        while (!paths.isEmpty()) {
            // 删除并且获取此次最短路径的顶点
            Map.Entry<Vertex<V, E>, E> minPath = getMinPath(paths);
            // 删除它
            Vertex<V, E> distVertex = minPath.getKey();
            E minWeight = paths.remove(distVertex);
            // 记录结果
            result.put(distVertex.value, minWeight);

            // 将该顶点能到达的边进行松弛操作
            for (Edge<V, E> outEdge : distVertex.outEdges) {
                // 但是得先看看这个顶点是否已经计算出来了。
                if (result.containsKey(outEdge.to.value)) continue;

                // 当前最短路径 + 这条出边
                E newWeight = weightManager.add(minWeight, outEdge.weight);
                // 以前的权值
                E oldWeight = paths.get(outEdge.to);

                if (oldWeight != null && weightManager.compare(newWeight, oldWeight) >= 0) continue;
                // 来到这这里说明新的权值要小

                paths.put(outEdge.to, newWeight);
            }

        }

        // 将源点 -> 源点的路径删除掉
        result.remove(src);

        return result;
    }

    /**
     * 获取现有结果中的最小路径
     * @param paths：所有路径
     * @return 将 key, value 一起返回
     */
    private Map.Entry<Vertex<V, E>, E> getMinPath(Map<Vertex<V, E>, E> paths) {
        Iterator<Map.Entry<Vertex<V, E>, E>> it = paths.entrySet().iterator();
        // 默认获取到的第一个键值对就是最小值（前面会判断，所以这里可以放心的取）
        Map.Entry<Vertex<V, E>, E> minPath = it.next();

        while (it.hasNext()) {
            Map.Entry<Vertex<V, E>, E> next = it.next();

            if (weightManager.compare(next.getValue(), minPath.getValue()) < 0) {
                // 说明比最小值还小
                minPath = next;
            }
        }

        return minPath;
    }

    /** 边权值的管理器 */
    public interface WeightManager<E> {
        /** 比较两个边权值的大小 */
        int compare(E w1, E w2);

        /** 将两条边的权值相加 */
        E add(E w1, E w2);

        /** 权值的 零 值 */
        E zero();
    }
}
