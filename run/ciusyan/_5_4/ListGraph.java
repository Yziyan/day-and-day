package run.ciusyan._5_4;

import run.ciusyan._5_3.UnionFind;

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

    /**
     * DFS非递归实现2
     * 上一种实现中，我们采用了和BFS类似的做法，使用栈实现了非递归的DFS，
     * 但是其实还有一种非递归版本的实现，使用栈是毋庸置疑的，因为需要回溯，
     * 上一种实现的本质，其实也是：遇到一个节点就访问它，然后将根据它的出边，选择一条路径，添加到栈里面。
     * 但是在其中需要注意是否是已经访问过的顶点。
     */
    public void dfs(V begin) {
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return;

        // 准备第一个栈，用于回溯节点
        Stack<Vertex<V, E>> stack = new Stack<>();
        // 将起点入栈，并直接访问它
        stack.push(beginVertex);
        System.out.println(beginVertex.value);

        // 用于记录已访问的节点
        Set<Vertex<V, E>> visited = new HashSet<>();
        visited.add(beginVertex);

        while (!stack.isEmpty()) {
            // 将栈顶元素出栈
            Vertex<V, E> vertex = stack.pop();

            // 选择一条出边的一个终点，继续访问
            for (Edge<V, E> edge : vertex.outEdges) {
                // 说明这个终点已经被访问过了
                if (visited.contains(edge.to)) continue;
                // 遇到一个顶点，就直接访问
                System.out.println(edge.to.value);
                visited.add(edge.to);

                // 然后将起点和终点入栈，
                stack.push(edge.from);
                stack.push(edge.to);

                // 只需选择一条路径即可
                break;
            }
        }
    }

    /**
     * DFS非递归实现1
     * 我们以前在实现非递归的前序遍历的时候，有一种和层序遍历很类似的实现，就是将队列直接换成栈即可。
     * 因为我们要有一个回溯的过程。所以需要将访问过的顶点放入栈中，方便未来回溯
     */
    public void dfs2(V begin) {
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return;

        // 准备一个栈、用于回溯顶点
        Stack<Vertex<V, E>> stack = new Stack<>();
        // 将起点入栈
        stack.push(beginVertex);

        // 用于记录已访问的顶点
        Set<Vertex<V, E>> visited = new HashSet<>();

        while (!stack.isEmpty()) {
            // 将栈顶元素弹出
            Vertex<V, E> vertex = stack.pop();
            // 如果没有访问过，才访问（遇到一个顶点，就尝试访问）
            if (visited.contains(vertex)) continue;

            // 说明此节点未被访问过，访问，并置为已访问状态
            System.out.println(vertex.value);
            visited.add(vertex);

            // 然后往下面钻，将此顶点所有的出边的终点都加入栈中，稍后的栈顶，就是选择的一条路。
            for (Edge<V, E> edge : vertex.outEdges) {
                stack.push(edge.to);
            }
        }
    }

    /**
     * 深度优先遍历：深度优先遍历通过一个顶点，沿着一条路径，一直往下面钻，搜索能到达的顶点，
     * 直至不能往下搜索了，那么回溯到上节点，寻找其他的路径。直至所有顶点都被访问。
     * 这种需要不断往下钻并且需要回溯到上一层节点，可以使用递归实现。
     * 二叉树的前序遍历，就是一种DFS的实现。
     */
    public void dfs1(V begin) {
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return;

        dfs1(beginVertex, new HashSet<>());
    }

    /**
     * 递归函数、从 curVertex 开始，遇到一个节点就访问他，并将他置为已访问的顶点，
     * 然后往下钻，根据它的出边找到它未访问过的终点，将其进行递归调用
     */
    public void dfs1(Vertex<V, E> curVertex, Set<Vertex<V, E>> visited) {
        // 访问当前节点，并将它置为以访问
        System.out.println(curVertex.value);
        visited.add(curVertex);

        // 根据当前顶点的出边，找一条路径继续往下访问
        for (Edge<V, E> edge : curVertex.outEdges) {
            // 没访问过才往下钻
            if (!visited.contains(edge.to)) {
                dfs1(edge.to, visited);
            }
        }
    }

    /**
     * 广度优先遍历：从起点出发，逐层遍历，下一层的节点是当前层所有节点中，走一步就能到达的节点。
     * 直至所有顶点都被访问完毕。二叉树的层序遍历就是一种 BFS的实现。通常使用队列来完成BFS。
     */
    public void bfs(V begin) {
        Vertex<V, E> beginVertex = vertices.get(begin);
        if (beginVertex == null) return;

        // 准备一个队列，用于按顺序访问下一层的节点
        Queue<Vertex<V, E>> queue = new LinkedList<>();
        // 将起点入队
        queue.offer(beginVertex);

        // 用于记录已经被添加到队列中的顶点，只要被添加到队列中了，那么就会顺序的被访问，
        //  本质上就是记录已经被访问的顶点。
        Set<Vertex<V, E>> addedQueue = new HashSet<>();
        addedQueue.add(beginVertex);

        // 直至所有节点都访问完毕
        while (!queue.isEmpty()) {
            // 弹出队头，访问节点
            Vertex<V, E> vertex = queue.poll();
            System.out.println(vertex.value);

            // 并将此顶点出边的终点添加到队列中
            for (Edge<V, E> edge : vertex.outEdges) {
                // 没有加入过队列的顶点，才需要添加进去
                if (!addedQueue.contains(edge.to)) {
                    queue.offer(edge.to);

                    // 别忘记将它标记为已经添加到队列中了
                    addedQueue.add(edge.to);
                }
            }
        }
    }

    /**
     * 拓扑排序：拓扑是一种对有向无换图顶点的一种排序算法，它把每一个顶点抽象为一个活动，
     * 将有向边的起点称为前驱活动，终点称为后继活动。拓扑排序指的是，按照所有的前驱活动都在后继活动的前面执行，
     * 建成的一个序列。这一序列通常被用作解决编译顺序、任务调度等问题。
     * 它通常使用卡恩算法来实现，也就是从入度为0的节点开始，每一次都选择入度为0的节点，然后将其删除掉。
     * 继续选择入度为0的节点，直至所有节点都被加入序列中。
     *
     */
    public List<V> topological() {
        List<V> result = new ArrayList<>();

        // 准备一个队列，用于按顺序排队
        Queue<Vertex<V, E>> queue = new LinkedList<>();
        // 准备一个Map，用于记录顶点的入度
        Map<Vertex<V, E>, Integer> insMap = new HashMap<>();

        // 遍历所有的顶点，
        vertices.forEach((key, vertex) -> {
            int ins = vertex.inEdges.size();
            if (ins == 0) {
                // 将入度为0的顶点加入队列中排队
                queue.offer(vertex);
            } else {
                // 入度不为0的顶点放入map中
                insMap.put(vertex, ins);
            }
        });

        // 还有人在排队，就不能下班
        while (!queue.isEmpty()) {
            // 将队头的人添加到结果中
            Vertex<V, E> vertex = queue.poll();
            result.add(vertex.value);

            // 然后模拟将这个节点删除（对应的出边的终点入度 - 1）
            for (Edge<V, E> edge : vertex.outEdges) {
                Integer ins = insMap.get(edge.to) - 1;

                // 如果入度为 0 了，那就可以加入队列排队了
                if (ins == 0) {
                    queue.offer(edge.to);
                } else {
                    // 否则更新入度
                    insMap.put(edge.to, ins);
                }
            }
        }

        // 有可能有环，那么将没有拓扑排序的结果
        if (result.size() < vertices.size()) {
            throw new RuntimeException("该图存在环、不能进行Topu排序");
        }

        return result;
    }

    /**
     * 使用 Prim 算法求出的MST：
     * Prim算法是一种基于贪心思想和切分定理实现的用于求解最小生成树的算法。先来了解一下切分定理，
     * 将图中的顶点一分为二被称为一次切分，它们之间被切开的边叫做横切边，切分定理是在若干切分中，
     * 选择一条权值最小的边。Prim就是利用切分定理，将顶点分为已切分和未切分两类顶点，然后每次将
     * 这两类顶点一分为二，选择一条权值最小的横切边加入结果集，直至所有顶点都被加入已切分的顶点集中。
     */
    public Set<EdgeInfo<V, E>> mstPrim() {
        // 存放结果的集合
        Set<EdgeInfo<V, E>> result = new HashSet<>();

        // 随机获取一个顶点
        Iterator<Vertex<V, E>> it = vertices.values().iterator();
        if (!it.hasNext()) return result;
        Vertex<V, E> beginVertex = it.next();
        // 准备一个已切分的顶点集
        Set<Vertex<V, E>> cuts = new HashSet<>();
        cuts.add(beginVertex);

        // 将这个顶点的出边集和加入最小堆中，并且将它原地建堆
        // 这里要注意，需要边的权值，具备可比较性。我们这里先这样认为他拥有可比较性了
        PriorityQueue<Edge<V, E>> minHeap = new PriorityQueue<>(beginVertex.outEdges);

        // 如果堆里还有元素，或者已经切分完了，就可退出了
        int vertexSize = vertices.size();
        while (!minHeap.isEmpty() && cuts.size() < vertexSize) {
            // 删除堆顶元素
            Edge<V, E> minEdge = minHeap.poll();

            // 查看它的终点，有没有被切分过，切分了就直接跳过
            if (cuts.contains(minEdge.to)) continue;

            // 来到这里，将它加入结果集合中，并且置为已切分
            result.add(minEdge.info());
            cuts.add(minEdge.to);

            // 然后需要将它终点的出边作为起点入堆，进行下一轮循环
            minHeap.addAll(minEdge.to.outEdges);
        }

        return result;
    }

    /**
     * 使用 Kruskal 算法求出的MST：
     * Kruskal算法也是一种基于贪心策略的求解最小生成树的算法。它的核心是在所有边集中，在不使顶点间成环的情况下，
     * 每次选择一条最小权边加入结果集合中。这里可以使用最小堆，维护边的最小值。它的难点在于，如何判断将某边加入
     * 结果集中后，会不会使最小生成树出现环。一种简单的实现就是使用并查集。在每次添加结果前，先判断这条边的起点和
     * 终点是否已经在一个集合中了，如果已经在一个集合中了，添加这条边，就会出现环。那么只有当起点和终点不在一个集合中
     * 的情况下，才去添加这条边。并且要将起点和终点合并到一个集合。
     *
     */
    public Set<EdgeInfo<V, E>> mstKruskal() {
        Set<EdgeInfo<V, E>> result = new HashSet<>();

        // 使用并查集，将所有的边集，初始化成一个单独的集合
        UnionFind<Vertex<V, E>> unionFind = new UnionFind<>();
        unionFind.makeSet(vertices.values());

        // 将所有的边原地入堆，这里也暂时使用JDK的最小堆
        PriorityQueue<Edge<V, E>> minHeap = new PriorityQueue<>(edges);

        // 只要堆不为空，或者结果中已经有 n - 1条边了，就可以返回了
        int resEdgeSize = vertices.size() - 1;
        while (!minHeap.isEmpty() && result.size() < resEdgeSize) {
            // 删除堆顶元素
            Edge<V, E> minEdge = minHeap.poll();

            // 加入前先检查是否会成环（如果已经在一个集合中，在加入就会成环）
            if (unionFind.isSame(minEdge.from, minEdge.to)) continue;

            // 来到这里，肯定不会成环了。添加结果、并且将起点和终点合并为一个集合
            result.add(minEdge.info());
            unionFind.union(minEdge.from, minEdge.to);
        }

        return result;
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

        EdgeInfo<V, E> info() {
            return new EdgeInfo<>(from.value, to.value, weight);
        }

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

    /** 用于传递边的信息（给外界看的） */
    public static class EdgeInfo<V, E> {
        V from;
        V to;
        E weight;

        public EdgeInfo(V from, V to, E weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }
    }

}
