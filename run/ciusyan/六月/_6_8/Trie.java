package run.ciusyan.六月._6_8;


import run.ciusyan.common.Asserts;

import java.util.HashMap;
import java.util.Map;

/**
 * 前缀树的简易实现，可以存储值
 */
public class Trie <V> {

    public static void main(String[] args) {
        Trie<Integer> trie = new Trie<>();
        trie.add("zyan", 1);
        trie.add("ciusyan", 2);
        trie.add("cherlin", 3);
        trie.add("zhiyan", 4);
        trie.add("志颜", 5);
        Asserts.test(trie.size == 5);
        Asserts.test(trie.startWith("c"));
        Asserts.test(trie.startWith("ci"));
        Asserts.test(trie.startWith("志"));
        Asserts.test(!trie.startWith("jo"));
        Asserts.test(trie.get("志颜") == 5);
        Asserts.test(trie.remove("zyan") == 1);
        Asserts.test(trie.remove("cherlin") == 3);
        Asserts.test(trie.size == 3);
        Asserts.test(trie.startWith("志"));
        Asserts.test(!trie.startWith("zy"));
    }

    /** Trie 的根节点，不存储任何节点 */
    private Node<V> root = new Node<>();
    /** 存储了多少个元素 */
    private int size;

    /** 根据 word 获取 value */
    public V get(String word) {
        Node<V> node = node(word);

        // 要想根据 word 获取到值，那么首先 node 需要存在，其次是此 word 必须代表是一个单词
        return node != null && node.word ? node.value : null;
    }

    /** 插入单词 */
    public V add(String word, V value) {
        keyCheck(word);

        // 从根节点开始
        Node<V> curNode = root;
        // 遍历每一个字符

        for (char c : word.toCharArray()) {
            // 先尝试取出此字符对应的节点
            Node<V> childNode = curNode.getChildren().get(c);
            if (childNode == null) {
                // 说明此字符在 Trie 中还没有，建立节点，
                childNode = new Node<>();
                childNode.parent = curNode;
                childNode.c = c;
                // 并将其上树
                curNode.children.put(c, childNode);
            }
            // 然后将节点往下查找
            curNode = childNode;
        }

        // 遍历结束后，查看 curNode 是否是单词，如果是那么覆盖掉原来的值，返回旧的值
        V oldValue = curNode.value;
        curNode.value = value;
        if (curNode.word) return oldValue;

        // 到这里了说明以前不是单词，现在要变成一个单词了，并且存储的元素数量要++
        curNode.word = true;
        size++;

        return null;
    }

    /** 从 Trie 上删除 word */
    public V remove(String word) {
        // 先获取要删除的节点
        Node<V> removedNode = node(word);
        // 说明没有这个节点或者这个节点不是一个单词的结尾，那么没必要删除
        if (removedNode == null || !removedNode.word) return null;

        // 来到这里肯定要被删除了
        V oldValue = removedNode.value;
        removedNode.value = null;
        size--;

        if (removedNode.children != null && !removedNode.children.isEmpty()) {
            // 说明此字符不是结尾的字符，那么直接删除单词标记即可
            removedNode.word = false;

            return oldValue;
        }

        // 来到这里说明删除的字符是结尾字符，那么不断的往上面遍历，移除路径上的节点
        Node<V> parent;
        while ((parent = removedNode.parent) != null) {
            // 删除自己在父节点中的引用
            parent.children.remove(removedNode.c);

            //  但删除一个节点，就得看看它是否是单词，或者还有其他子节点，是的话就截止删除了
            if (parent.word || !parent.children.isEmpty()) break;

            // 否则继续往上走
            removedNode = parent;
        }

        return oldValue;
    }

    /** 查询此 Trie 是否拥有此 prefix */
    public boolean startWith(String prefix) {
        // 如果能根据此前缀查询到节点，说明有此前缀
        return node(prefix) != null;
    }

    /** 根据 word 获取节点 */
    private Node<V> node(String word) {
        keyCheck(word);

        // 从根节点开始去查找对应的节点
        Node<V> curNode = root;
        for (char c : word.toCharArray()) {
            // 从所有的子节点中查找子节点
            curNode = curNode.getChildren().get(c);

            // 如果节点都没有，那么别谈能获取到单词了
            if (curNode == null) return null;
        }

        // 来到这里说明此单词所有字符都在 Trie 上
        return curNode;
    }

    private void keyCheck(String key) {
        if (key == null || key.length() == 0) {
            throw new IllegalArgumentException("Key 不能为 null");
        }
    }

    /** 节点类 */
    private static class Node<V> {
        /** 此节点的字符 */
        Character c;
        /** 此节点的父节点 */
        Node<V> parent;
        /** 此节点的子节点 */
        Map<Character, Node<V>> children;
        /** 存储的值，只有完整的单词上才能存储 value */
        V value;
        /** 是否是一个完整的单词 */
        boolean word;

        /** 返回 Children */
        public Map<Character, Node<V>> getChildren() {
            // 返回不为空的 Map
            return children == null ? (children = new HashMap<>()) : children;
        }
    }
}
