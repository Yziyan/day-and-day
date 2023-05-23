package run.ciusyan._5_24;

import run.ciusyan.common.TreeNode;

import javax.xml.parsers.SAXParser;

/**
 * https://leetcode.cn/problems/serialize-and-deserialize-binary-tree/
 */
public class 二叉树的序列化与反序列化 {

    /** 用于解析时的索引 */
    private int idx;

    /** 分隔符 */
    private static String SEPARATOR = "#";
    /** 表示 null 节点 */
    private static String NULL = "@";

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serialize(root, sb);

        return sb.toString();
    }

    /** 使用前序遍历的方式序列化 */
    private void serialize(TreeNode root, StringBuilder sb) {
        if (root == null) {
            sb.append(NULL).append(SEPARATOR);

            return;
        }

        // 拼接节点
        sb.append(root.val).append(SEPARATOR);
        serialize(root.left, sb);
        serialize(root.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null) return null;

        // 切分出所有的节点，然后进行序列化
        return deserialize(data.split(SEPARATOR));
    }

    /** 解析也要使用前序遍历的方式 */
    private TreeNode deserialize(String[] split) {
        if (NULL.contains(split[idx])) return null;

        // 构建节点
        TreeNode root = new TreeNode(Integer.parseInt(split[idx]));
        // 索引往后移
        idx++;
        // 构建左子树
        root.left = deserialize(split);
        // 索引继续往后移动
        idx++;
        // 构建右子树
        root.right = deserialize(split);

        return root;
    }
}
