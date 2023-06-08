package run.ciusyan.六月._6_8;


import run.ciusyan.common.Asserts;

import java.util.HashMap;
import java.util.Map;

/**
 * 不存储值的 Trie
 */
public class TrieNoValue {

    public static void main(String[] args) {
        TrieNoValue trieNoValue = new TrieNoValue();

        Asserts.test(!trieNoValue.search("ciusyan"));
        trieNoValue.insert("ciusyan");
        Asserts.test(trieNoValue.search("ciusyan"));
        Asserts.test(!trieNoValue.startWith("zhi"));
        Asserts.test(trieNoValue.startWith("c"));
        trieNoValue.insert("志颜");
        Asserts.test(!trieNoValue.search("志颜1"));
        Asserts.test(trieNoValue.search("志颜"));
    }

    /** 不存储任何字符的根节点 */
    private Node root = new Node();

    /** 插入此 word */
    public void insert(String word) {
        keyCheck(word);
        // 遍历每一个字符，看看是否有对应的节点，如果有子节点就继续往下查找，如果没有那么就创建一个子节点
        Node curNode = root;
        for (char c : word.toCharArray()) {
            // 尝试获取子节点
            Node child = curNode.getChildren().get(c);
            if (child == null) {
                // 说明需要创建此节点，并且添加到树上
                child = new Node();
                curNode.children.put(c, child);
            }
            // 往下一层遍历
            curNode = child;
        }
        // 来到这里说明所有字符都上树了，将其结尾变成单词的标记
        curNode.word = true;
    }

    /** 搜索此 word 是否在 Tire 上 */
    public boolean search(String word) {
        Node node = node(word);

        // 如果有对应的节点，并且需要是单词，才代表有这个单词
        return node != null && node.word;
    }

    /** 查询此 Trie 上是否有此 prefix */
    public boolean startWith(String prefix) {
       return node(prefix) != null;
    }

    /** 根据 word 搜索节点 */
    private Node node(String word) {
        keyCheck(word);

        // 从根节点开始，遍历每一个字符
        Node curNode = root;
        for (char c : word.toCharArray()) {
            // 尝试获取此字符对应的节点
            Node node = curNode.getChildren().get(c);
            // 如果此字符都没有节点，那么说明此单词根本不可能在 Trie 上
            if (node == null) return null;
            curNode = node;
        }

        return curNode;
    }

    private void keyCheck(String word) {
        if (word == null || word.length() == 0) throw new IllegalArgumentException("word 不能为 null");
    }

    /** 字符的节点类 */
    private static class Node {
        /** 此节点的子节点 */
        Map<Character, Node> children;
        /** 此节点是否是某单词的结尾 */
        boolean word;

        /** 保证外界不拿到 null 的子节点 */
        Map<Character, Node> getChildren() {
            return children == null ? (children = new HashMap<>()) : children;
        }
    }
}
