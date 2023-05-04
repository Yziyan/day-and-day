package run.ciusyan._5_4;

import java.util.*;

/**
 * 偏向使用领接表来实现的图
 * @param <V>：顶点存储的值
 * @param <E>：边存储的值（权值）
 */
public class ListGraph<V, E> {

    /** 存储图中所有的顶点 */
    private Map<V, Vertex<V, E>> vertices = new HashMap<>();

    /** 存储图中所有的边 */
    private Set<Edge<V, E>> edges = new HashSet<>();

    /** 添加一个顶点 */
    public void addVertex(V v) {
        if (vertices.containsKey(v)) return;

        // 没有才添加这个顶点
        vertices.put(v, new Vertex<>(v));
    }

    /** 删除一个顶点 */
    public void removeVertex(V v) {
        // 直接尝试删除这个顶点
        Vertex<V, E> vertex = vertices.remove(v);
        if (vertex == null) return;

        // 来到这里，顶点确实删除成功了，但是我们还得删除顶点相关的边
        // 这里不能直接遍历他的出边集合，将它的出边和入边删掉，
        //  因为调用 removeEdge 的时候，可能需要访问到 Vertex 这个顶点，
        //  相当于在访问它的同时又要删除它，可能删不掉、而且不安全。
        // 那么，我们只能使用迭代器去安全的删除它

        // 这里是删除顶点的出边集合
        for (Iterator<Edge<V, E>> iterator = vertex.outEdges.iterator(); iterator.hasNext();) {
            Edge<V, E> edge = iterator.next();
            edges.remove(edge);
            // 将此起点对应终点的入边也删除
            edge.to.inEdges.remove(edge);
            // 在迭代器中删除自己，就回去原集合中删除遍历到的这条边
            iterator.remove();
        }

        // 这里是删除顶点的入边集合
        for (Iterator<Edge<V, E>> iterator = vertex.inEdges.iterator(); iterator.hasNext();) {
            Edge<V, E> edge = iterator.next();
            edges.remove(edge);
            // 将此终点对应的起点的出边也删除
            edge.from.outEdges.remove(edge);
            // 安全的删除这条边
            iterator.remove();
        }
    }

    /** 添加一条无权边 */
    public void addEdge(V fromV, V toV) {
        addEdge(fromV, toV, null);
    }

    /**
     * 添加一条有权边
     * @param fromV：起点
     * @param toV：终点
     * @param weight：权值
     */
    public void addEdge(V fromV, V toV, E weight) {
        // 先判断有没有起点和终点，如果没有，就创建
        Vertex<V, E> fromVertex = vertices.get(fromV);
        if (fromVertex == null) {
            fromVertex = new Vertex<>(fromV);
            vertices.put(fromV, fromVertex);
        }
        Vertex<V, E> toVertex = vertices.get(toV);
        if (toVertex == null) {
            toVertex = new Vertex<>(toV);
            vertices.put(toV, toVertex);
        }

        // 查看边已经存在了，构建一条新的边，根据起点和终点，就能确定这条边是否已经存在了
        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);
        // 直接去删除这条边，如果删除成功了，说明以前存在这条边，
        // 将这条边删除干净
        if (edges.remove(edge)) {
            // 说明以前存在这条边，将出边删除
            fromVertex.outEdges.remove(edge);
            toVertex.inEdges.remove(edge);
        }

        // 来到这里，肯定是不存在这条边了，更新权值，将其加入边中
        edge.weight = weight;

        edges.add(edge);
        fromVertex.outEdges.add(edge);
        toVertex.inEdges.add(edge);
    }

    /** 删除一条边 */
    public void removeEdge(V fromV, V toV) {
        // 要是这两个顶点有一个不存在，就没必要去删除了
        Vertex<V, E> fromVertex = vertices.get(fromV);
        if (fromVertex == null) return;
        Vertex<V, E> toVertex =  vertices.get(toV);
        if (toVertex == null) return;

        // 根据起点和终点构建边
        Edge<V, E> edge = new Edge<>(fromVertex, toVertex);

        // 尝试删除这条边
        if (edges.remove(edge)) {
            // 说明这条边以前存在，将其有关系的全部删除
            fromVertex.outEdges.remove(edge);
            toVertex.inEdges.remove(edge);
        }
    }

    /** 顶点的数量 */
    public int verticesSize() {
        return vertices.size();
    }

    /** 边的数量 */
    public int edgeSize() {
        return edges.size();
    }

    /** 顶点的抽象 */
    private static class Vertex<V, E> {
        // 存储的值
        V value;
        // 此顶点的入边集合
        Set<Edge<V, E>> inEdges = new HashSet<>();
        // 此顶点的出边集合
        Set<Edge<V, E>> outEdges = new HashSet<>();

        public Vertex(V value) {
            this.value = value;
        }

        /** value 相等，就认为是同一个顶点 */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Vertex<V, E> vertex = (Vertex<V, E>) obj;
            return Objects.equals(this.value, vertex.value);
        }

        /** 对应的哈希code方法 */
        @Override
        public int hashCode() {
            return Objects.hash(value);
        }
    }

    /** 边的抽象 */
    private static class Edge<V, E> {
        // 边的起点
        Vertex<V, E> from;
        // 边的终点
        Vertex<V, E> to;
        // 边的权值
        E weight;

        public Edge(Vertex<V, E> from, Vertex<V, E> to) {
            this(to, from, null);
        }

        public Edge(Vertex<V, E> from, Vertex<V, E> to, E weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        /** 只要一条边的起点和终点相等，就认为是一条边 */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Edge<V, E> edge = (Edge<V, E>) obj;

            // 这里使用了顶点的 equals 方法，那么我们也应该去重写
            return Objects.equals(this.from, edge.from) && Objects.equals(this.to, edge.to);
        }

        /** 那么对应的，应该重写 hashCode */
        @Override
        public int hashCode() {
            return Objects.hash(from, to);
        }
    }
}
