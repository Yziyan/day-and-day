package run.ciusyan.五月._5_6;

import run.ciusyan.五月._5_5.ListGraph2;

import java.util.*;

/**
 * 此处的继承也是毫无意义的
 */
public class ListGraph3 <V, E> extends ListGraph2<V, E> {

    public ListGraph3(WeightManager weightManager) {
        super(weightManager);
    }

    /**
     * Floyd：是一种用于求解多元最短路径的算法，该算法也支持负权边，它的核心思想是：
     * 任意两个顶点的最短路径只有两种情况：
     * 1、直接从源点到终点
     * 2、从源点出发，经过若干顶点后，到达终点
     * @return <"A", <"B", PathInfo> ...> ...
     */
    public Map<V, Map<V, PathInfo<V, E>>> shortPathFloyd() {
        Map<V, Map<V, PathInfo<V, E>>> paths = new HashMap<>();
        // 初始化最短路径，先默认每一条边的起点到终点，就是这两个顶点间的最短路径
        for (Edge<V, E> edge : edges) {
            // 尝试获取起点的最短路径信息
            Map<V, PathInfo<V, E>> fromPath = paths.get(edge.from.value);

            if (fromPath == null) {
                // 说明此起点还未初始化
                fromPath = new HashMap<>();
                paths.put(edge.from.value, fromPath);
            }

            // 来到这里，肯定初始化了，但是还需要初始化它这条边终点的路径信息
            PathInfo<V, E> toPath = new PathInfo<>(edge.weight);
            toPath.edgeInfos.add(edge.info());
            fromPath.put(edge.to.value, toPath);
        }

        // 初始化完毕，三层循环、每一次尝试求解 v1 -> v3 的目标距离
        vertices.forEach((v2, vertex2) -> {
            vertices.forEach((v1, vertex1) -> {
                vertices.forEach((v3, vertex3) -> {
                    // 如果 v1、v2、v3 任意两个顶点是相等的，说明不用计算了
                    if (v1.equals(v2) || v2.equals(v3) || v1.equals(v3)) return;

                    // 获取 v1 -> v2 的最短路径信息
                    PathInfo<V, E> path12 = getPath(v1, v2, paths);
                    // 如果没有路，就直接返回
                    if (path12 == null) return;

                    // 获取 v2 -> v3 的最短路径信息
                    PathInfo<V, E> path23 = getPath(v2, v3, paths);
                    // 如果没有路，就直接返回
                    if (path23 == null) return;

                    // 计算出 v1->v2 + v2->v3（间接）
                    E weight123 = weightManager.add(path12.weight, path23.weight);

                    // 获取 v1 -> v3 的最短路径信息
                    PathInfo<V, E> path13 = getPath(v1, v3, paths);
                    // 说明 v1 -> v3 比间接到达还近，直接返回
                    if (path13 != null && weightManager.compare(weight123, path13.weight) >= 0) return;

                    // 到达这里，说明找到一条新路了
                    if (path13 == null) {
                        // 说明 v1 -> v3 还没有路
                        path13 = new PathInfo<>(weight123);
                        // 添加 v1 -> v3 的路径
                        paths.get(v1).put(v3, path13);
                    } else {
                        // 说明以前有路，但是不是最短的，清除以前的路径，更新路径长度
                        path13.edgeInfos.clear();
                        path13.weight = weight123;
                    }

                    // 来到这里了，说明要更新新路径了
                    // 先添加 v1 -> v2 的路径
                    path13.edgeInfos.addAll(path12.edgeInfos);
                    // 再添加 v2 -> v3 的路径
                    path13.edgeInfos.addAll(path23.edgeInfos);
                });
            });
        });

        return paths;
    }

    /** 获取 src -> dist 的最短路径 */
    private PathInfo<V, E> getPath(V src, V dist, Map<V, Map<V, PathInfo<V, E>>> paths) {
        Map<V, PathInfo<V, E>> srcPaths = paths.get(src);

        return srcPaths == null ? null : srcPaths.get(dist);
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
