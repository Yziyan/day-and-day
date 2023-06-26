package run.ciusyan.六月._6_26;

import run.ciusyan.common.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.cn/problems/check-completeness-of-a-binary-tree/
 */
public class 二叉树的完全性检验 {

    /***
     * 可利用层序遍历求解。（默认你已经了解了完全二叉树的性质）
     * 首先要了解标准的层序遍历套路，这非常重要！！！这里只做简述：
     *      1. 准备一个队列，并将根节点入队
     *      2. 当队列不为空时，开始层序遍历
     *      3. 弹出队头元素，并且访问（做一些操作）
     *      4. 若队头元素的左右子树不为 null，也将其加入到队列中排队，等待下一层遍历。
     * 上面是最基本的层序遍历讨论，如果将其改造一下，即可求解此题了。
     * 因为我们是一层层的遍历，当弹出的队头元素只有右子树，但是没有左子树，
     *      那么说明不满足完全二叉树左对齐的性质了，直接返回 false。
     * 完全二叉树的最后一层是叶子节点，那何时会出现第一个叶子节点呢？
     *      只要遇到第一个度不为 2 的节点并且还必须要左对齐，
     *      也就是要么左右子树都没有，要么只有左子树。
     * 所以可以用一个变量作为是否是叶子节点的开关，
     *      当开关被打开后，之后遍历到的每一个节点，都必须是叶子节点。
     *
     */
    public boolean isCompleteTree(TreeNode root) {
        if (root == null) return true;

        // 准备一个队列进行，用于层序遍历
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        // 标记弹出的队头元素是否为叶子节点，默认不是叶子
        boolean leaf = false;
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();

            // 如果需要时 leaf，但是又不是叶子节点，说明不满足完全二叉树
            if (leaf && (node.left != null || node.right != null)) return false;

            // 左右子树不为 null，将其也加入到队列中
            if (node.left != null) {
                // 标准层序遍历的套路
                queue.offer(node.left);
            } else if (node.right != null) {
                // 说明有左子树，没有右子树，不满足左对齐
                return false;
            }

            if (node.right != null) {
                // 标准层序遍历的套路
                queue.offer(node.right);
            } else {
                // 到达这里有两种可能： left == null or right == null
                //  说明之后遇到的节点在左对齐的基础上，还必须是叶子节点。
                leaf = true;
            }
        }

        // 遍历结束都没有返回，说明肯定是完全二叉树了
        return true;
    }
}
