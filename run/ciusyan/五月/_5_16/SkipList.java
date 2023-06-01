package run.ciusyan.五月._5_16;

import java.util.Comparator;
import java.util.Objects;

/**
 * 跳表：
 *  在有序链表的基础上增加了跳跃的功能，使得增加删除搜索的平均复杂度都是O(logn)
 */
public class SkipList<K, V> {

    public static void main(String[] args) {
        // 创建一个跳表实例，使用自然顺序比较键
        SkipList<Integer, String> skipList = new SkipList<>();

        // 测试插入和获取
        skipList.put(1, "A");
        skipList.put(2, "B");
        skipList.put(3, "C");

        assertEqual(skipList.get(2), "B", "Value for key 2");
        assertEqual(skipList.get(4), null, "Value for key 4");

        // 测试更新
        skipList.put(2, "D");
        assertEqual(skipList.get(2), "D", "Updated value for key 2");

        // 测试删除
        skipList.remove(2);
        assertEqual(skipList.get(2), null, "Value for key 2 after removal");
    }

    private static void assertEqual(Object actual, Object expected, String message) {
        if (!Objects.equals(actual, expected)) {
            System.err.println("Error: " + message);
            System.err.println("Expected: " + expected);
            System.err.println("Actual: " + actual);
        }
    }

    /** 跳表中元素的数量 */
    private int size;

    /** 可传入一个比较器 */
    private Comparator<K> comparator;

    /** 一个虚拟头结点 */
    private Node<K, V> first;

    /** 用于标识现在实际有多少层 */
    private int level;

    /** 最大层数 */
    private static final int MAX_LEVEL = 2 << 4;

    /** 增加层数的概率 */
    private static final double P = 0.25;

    public SkipList() {
        this(null);
    }

    public SkipList(Comparator<K> comparator) {
        this.comparator = comparator;

        // 初始化虚拟头节点，最高有 32 层
        first = new Node<>();
        first.nexts = new Node[MAX_LEVEL];
    }

    /** 获取元素 */
    public V get(K key) {
        keyCheck(key);

        // 从虚拟头节点开始搜索
        Node<K, V> curNode = first;
        // 从实际层数中，最高的一层开始搜索，
        for (int i = level - 1; i >= 0; i--) {
            // 但是我们可以记录一下 cmp 的值
            int cmp = -1;

            // 先从这一层不断的往后钻、
            //  直到走到最后 or 找到一个比自己大的
            while (curNode.nexts[i] != null && (cmp = compare(key, curNode.nexts[i].key)) > 0) {
                curNode = curNode.nexts[i];
            }

            // 说明找到了对应的节点。
            if (cmp == 0) return curNode.nexts[i].value;

            // 来到这里，说明 level 会--，到下一层去
        }

        return null;
    }

    /** 放入、如果是更新，返回更新钱的值 */
    public V put(K key, V value) {
        keyCheck(key);

        // 添加需要找到对应的节点，
        //  将它所有的前驱节点的后继节点都变成它
        //  所它的后继都变成前驱的后继
        //  所以我们在寻找的过程中，还需要记录下它所有的前驱节点，最多有 level 个前驱
        Node<K, V>[] prevs = new Node[level];
        Node<K, V> curNode = first; // 从虚拟头结点开始

        for (int i = level - 1; i >= 0; i--) {
            // 记录比较的结果
            int cmp = -1;

            while (curNode.nexts[i] != null && (cmp = compare(key, curNode.nexts[i].key)) > 0) {
                // 说明可以往后走
                curNode = curNode.nexts[i];
            }

            if (cmp == 0) {
                // 说明更新旧值即可
                V oldV = curNode.nexts[i].value;
                curNode.nexts[i].key = key;
                curNode.nexts[i].value = value;

                return oldV;
            }

            // 来到这里，说明需要向下一层查找了，记录它在这一层的前驱节点
            prevs[i] = curNode;
        }

        // 来到这里，肯定是要增加元素的
        size++;

        // 随机获取层数
        int newLevel = randomLevel();
        // 构建新节点
        Node<K, V> newNode = new Node<>(key, value, newLevel);

        // 将其插入到合适的位置，也就是维护好节点的关系
        for (int i = 0; i < newLevel; i++) {
            if (i >= level) {
                // 说明已经大于目前的层数了，那么需要维护虚拟头结点
                first.nexts[i] = newNode;
            } else {
                // 说明需要维护以前节点的前驱和新节点的后继
                newNode.nexts[i] = prevs[i].nexts[i];
                prevs[i].nexts[i] = newNode;
            }
        }

        // 增加完之后，层数可能会变高
        level = Math.max(level, newLevel);

        return null;
    }

    /** 删除，需要返回被删除的值 */
    public V remove(K key) {
        keyCheck(key);

        // 删除节点也很类似，需要维护所有前驱节点的情况
        Node<K, V>[] prevs = new Node[level];
        Node<K, V> curNode = first; // 从虚拟头结点开始寻找

        // 但是也可能根本没有这个元素，所以需要再扫描的时候，记录一下
        boolean exist = false;
        
        for (int i = level - 1; i >= 0; i--) {

            int cmp = -1;
            while (curNode.nexts[i] != null && (cmp = compare(key, curNode.nexts[i].key)) > 0) {
                curNode = curNode.nexts[i];
            }
            
            // 如果存在，还需要往下面查找，不能直接 break 
            //  因为上面都还有联系，下面的层级肯定也有联系。
            if (cmp == 0) exist = true;
            
            // 到达这里，需要将所有前驱节点
            prevs[i] = curNode;
        }

        // 到达这里，说明根本不存在，不用删除
        if (!exist) return null;
        
        // 需要删除，删除谁呢？
        //  其实是用于遍历节点最下面一层。
        Node<K, V> removeNode = curNode.nexts[0];
        size--;
        
        // 维护节点关系，被删除节点有多少层，那就需要维护多少次
        for (int i = 0; i < removeNode.nexts.length; i++) {
            prevs[i].nexts[i] = removeNode.nexts[i];
        }

        // 但是删除后，需要查看，删除的是否是最高的，是的话，需要维护虚拟头结点的关系
        int validateLevel = level;
        while (--validateLevel > 0 && first.nexts[validateLevel] == null) {
            // 来到这里说明，还没到最底层，
            // 并且，在有效层数内，虚拟头结点指向都被删除了，说明删除的时最高的节点，
            //  需要将层数减少 1
            level--;
        }

        return removeNode.value;
    }

    public int size() {
        return size;
    }

    /** 随机获取层数 */
    private int randomLevel() {
        int newLevel = 1;

        // 按照一定的概率增加层数，但是不能超过最大层数
        while (Math.random() < P && newLevel < MAX_LEVEL) {
            newLevel++;
        }

        return newLevel;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /** 比较两个元素的大小 */
    private int compare(K k1, K k2) {
        return comparator != null ? comparator.compare(k1, k2) : ((Comparable) k1).compareTo(k2);
    }

    /** 检查key */
    private void keyCheck(K k) {
        if (k == null) throw new IllegalArgumentException("不支持 Key 为 null");
    }

    /** 链表的节点 */
    private static class Node<K, V> {
        K key;
        V value;
        /** 后继节点的数组，可能有很多层 */
        Node<K, V>[] nexts;

        public Node() {}

        public Node(K key, V value, int level) {
            this.key = key;
            this.value = value;

            // 需要创建 level 层
            nexts = new Node[level];
        }
    }
}
