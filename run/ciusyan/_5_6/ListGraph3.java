package run.ciusyan._5_6;

import run.ciusyan._5_5.ListGraph2;

import java.util.*;

/**
 * 此处的继承也是毫无意义的
 */
public class ListGraph3 <V, E> extends ListGraph2<V, E> {

    public ListGraph3(WeightManager weightManager) {
        super(weightManager);
    }

    /**
     * Bellman-Ford 也是一种基于贪心策略的用于求解单源最短路径的算法，它的核心操作就是对每一条边都进行 n - 1 次
     * 松弛操作，就能得到所有顶点的最短路径，它可以支持负权边，但不支持负权环。
     * 基于这一点，可以利用它来判断，是否带有负权环等问题。
     */
    public Map<V, PathInfo<V, E>> shortPathBellmenFord(V src) {
        Map<V, PathInfo<V, E>> result = new HashMap<>();

        // 但是需要进行初始化操作，我们目前能直接求出来的最短路径，
        // 可以使用 源点 -> 源点，我们只需要使用它的权值即可，不需要真正创建一条路径
        result.put(src, new PathInfo<>(weightManager.zero()));

        // 对所有边 进行 v - 1 次松弛操作
        int count = vertices.size() - 1;
        for (int i = 0; i < count; i++) {
            for (Edge<V, E> edge : edges) {
                // 尝试获取从源点到这条边的最短路径
                PathInfo<V, E> minPath = result.get(edge.to.value);

                // 说明此条边还不能被拉起来，松弛不动
                if (minPath == null) continue;

                relaxForBellmanFord(edge, minPath, result);
            }
        }

        // 可以检测是否有负权环，只需要再进行一次松弛操作，如果能成功，说明有负权环
        for (Edge<V, E> edge : edges) {
            final PathInfo<V, E> minPath = result.get(edge.to.value);
            if (minPath == null) continue;

            if (relaxForBellmanFord(edge, minPath, result)) {
                throw new RuntimeException("存在负权环");
            }
        }

        // 删除源点到源点的信息
        result.remove(src);

        return result;
    }

    /**
     * 松弛操作
     * @param edge：松弛的边
     * @param minPath：此前的最短路径
     * @param result：最终结果
     */
    private boolean relaxForBellmanFord(Edge<V,E> edge,
                                     PathInfo<V,E> minPath, Map<V, PathInfo<V,E>> result) {

        // 首先计算出新权值
        E newWeight = weightManager.add(minPath.weight,edge.weight);

        // 获取旧的路径信息
        PathInfo<V, E> oldPath = result.get(edge.to.value);

        // 和旧权值比较，当然可能以前也没有路可走
        // 说明新的权值还要大一些
        if (oldPath != null && weightManager.compare(oldPath.weight, newWeight) <= 0) return false;

        // 来到这里，说明找到新的路径了
        if (oldPath == null) {
            // 说明以前没有路径
            oldPath = new PathInfo<>(newWeight);
            result.put(edge.to.value, oldPath);
        } else {
            // 说明以前有路，更新权值和删除旧的路径
            oldPath.edgeInfos.clear();
            oldPath.weight = newWeight;
        }

        // 设置新的路径，先设置最短路径
        oldPath.edgeInfos.addAll(minPath.edgeInfos);
        // 再加上这条边
        oldPath.edgeInfos.add(edge.info());

        return true;
    }

    /**
     * 完善版Dijkstra算法：
     * 不仅包含源点到达每一个顶点的最短路径，还包含了路径信息，其实实现的思路还是一样的，
     * 只是换成了泛型参数而已。都是从源点开始，对它能到达的边不断的进行松弛操作。每一次松弛过后，选择一条最短的路径。
     *
     */
    public Map<V, PathInfo<V, E>> shortPathDijkstra(V src) {
        Map<V, PathInfo<V, E>> result = new HashMap<>();
        Vertex<V, E> srcVertex = vertices.get(src);
        if (srcVertex == null) return result;

        // 准备一个Map，用于记录中间结果
        Map<Vertex<V, E>, PathInfo<V, E>> paths = new HashMap<>();
        // 初始化源点的路径信息
        // 若知道边权值的零值，那么就不用先初始化了，只要我们保证在内部可以让源点的出边进行松弛操作就行了
        paths.put(srcVertex, new PathInfo<>(weightManager.zero()));

//        for (Edge<V, E> outEdge : srcVertex.outEdges) {
//            PathInfo<V, E> info = new PathInfo<>(outEdge.weight);
//            info.edgeInfos.add(outEdge.info());
//            paths.put(outEdge.to, info);
//        }

        while (!paths.isEmpty()) {
            // 获取最短路径，并且删除它
            Map.Entry<Vertex<V, E>, PathInfo<V, E>> minPath = getMinPath(paths);
            // 删除路径，获取信息，记录最短路径
            Vertex<V, E> distVertex = minPath.getKey();
            PathInfo<V, E> minPathInfo = paths.remove(distVertex);
            result.put(distVertex.value, minPathInfo);

            // 然后对此次最短路径的目标顶点的出边进行松弛操作。
            for (Edge<V, E> edge : distVertex.outEdges) {
                // 进来先看看这个点有没有已经被求出来了
                if (result.containsKey(edge.to.value)) continue;

                // 进行松弛操作
                relaxForDijkstra(edge, minPathInfo, paths);
            }
        }

        // 将源点 -> 源点 删除
        result.remove(src);

        return result;
    }

    /**
     * 松弛操作
     * @param edge：进行松弛的边
     * @param minPathInfo：此前最短路径的信息
     * @param paths：中间结果
     */
    public void relaxForDijkstra(Edge<V, E> edge,
                                 PathInfo<V, E> minPathInfo,
                                 Map<Vertex<V, E>, PathInfo<V, E>> paths) {

        // 计算出新权值
        E newWeight = weightManager.add(minPathInfo.weight, edge.weight);
        // 获取旧权值路径信息
        PathInfo<V, E> oldPathInfo = paths.get(edge.to);

        // 说明源点到此边终点的路径不需要更新
        if (oldPathInfo != null && weightManager.compare(newWeight, oldPathInfo.weight) >= 0) return;

        if (oldPathInfo == null) {
            // 说明以前没有路径
            oldPathInfo = new PathInfo<>(newWeight);
            paths.put(edge.to, oldPathInfo);
        } else {
            // 说明以前有路径，将旧路径清除掉，并且更新权值
            oldPathInfo.edgeInfos.clear();
            oldPathInfo.weight = newWeight;
        }

        // 更新路径信息，先添加当前的最短路径
        oldPathInfo.edgeInfos.addAll(minPathInfo.edgeInfos);
        // 再添加当前这条边
        oldPathInfo.edgeInfos.add(edge.info());
    }

    /** 获取此次的最小路径 */
    private Map.Entry<Vertex<V, E>, PathInfo<V, E>> getMinPath(Map<Vertex<V, E>, PathInfo<V, E>> paths) {
        Iterator<Map.Entry<Vertex<V, E>, PathInfo<V, E>>> it = paths.entrySet().iterator();
        Map.Entry<Vertex<V, E>, PathInfo<V, E>> minPath = it.next();

        while (it.hasNext()) {
            Map.Entry<Vertex<V, E>, PathInfo<V, E>> next = it.next();
            if (weightManager.compare(minPath.getValue().weight, next.getValue().weight) > 0) {
                minPath = next;
            }
        }

        return minPath;
    }

    public static class PathInfo <V, E> {
        /** 该路径的总权值 */
        protected E weight;

        /** 路径信息 */
        protected List<EdgeInfo<V, E>> edgeInfos;

        public PathInfo(E weight) {
            this.weight = weight;
        }
    }

}
