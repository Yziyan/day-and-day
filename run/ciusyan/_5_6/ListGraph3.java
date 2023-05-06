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
