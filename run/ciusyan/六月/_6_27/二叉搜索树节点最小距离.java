package run.ciusyan.六月._6_27;

import run.ciusyan.common.TreeNode;

/***
 * https://leetcode.cn/problems/minimum-distance-between-bst-nodes/
 */
public class 二叉搜索树节点最小距离 {

    /** 返回结果，默认是最大，要保证第一次计算能够应用 */
    private int minRes = Integer.MAX_VALUE;

    private TreeNode prev;

    /***
     * 中序遍历求解
     *  首先需要知道二叉搜索的一个性质：中序遍历是有序的。
     *      那么任意节点之间的最小差值，只可能存在于中序遍历中两个相邻的节点。
     *      所以第一种方案就是直接中序遍历。在中序遍历的过程中，尝试更新最小差值。
     *      遍历结束后，就能得到所有相邻节点间的最小值。
     *
     */
    public int minDiffInBST(TreeNode root) {
        if (root == null) return 0;
        // 中序遍历
        inorder(root);

        // 如果都没有更新过 minRes，说明节点的数量小于 0
        return minRes == Integer.MAX_VALUE ? 0 : minRes;
    }

    /** 中序遍历 */
    private void inorder(TreeNode root) {
        if (root == null) return;

        // 中序遍历左子树
        inorder(root.left);

        // 利用前驱节点计算差值
        if (prev != null) {
            // 如果有前驱节点，比较当前节点和前驱的差值要比 先前的小，就更新最小差值
            minRes = Math.min(minRes, root.val - prev.val);
        }

        prev = root;
        // 中序遍历右子树
        inorder(root.right);
    }


    /** 利用二叉树的递归套路求解 */
    public int minDiffInBST1(TreeNode root) {
        if (root == null) return 0;

        return getDiff(root);
    }

    /**
     * 也可以利用二叉树的递归套路求解：
     *  首先明确当前方法是获取 root 的最小差值。那么我们先计算出 左右 子树的 diff。
     *  那么当前 root 的 diff，有可能是他们之间的最小值。
     *      但是还需要将 root 分别与前驱和后继计算差值，因为是相邻的节点。
     *   那么最终答案就是：
     *      Min{ root-前驱, 后继-root, leftDiff, rightDiff}
     * 但是这样求解，需要会求解任意节点的前驱节点和后继节点。具体的看代码吧
     *
     */
    private int getDiff(TreeNode root) {
        if (root == null) return Integer.MAX_VALUE;

        // 获取左右子树的最小差值
        int leftDiff = getDiff(root.left);
        int rightDiff = getDiff(root.right);
        // 保留左右子树中最小的
        int diff = Math.min(leftDiff, rightDiff);

        // 获取前驱节点，用根节点 - 前驱 与 上面的最小值比较
        if (root.left != null) {
            TreeNode prec = root.left;
            // 前驱节点在左子树的最右边
            while (prec.right != null) {
                prec = prec.right;
            }

            diff = Math.min(diff, root.val - prec.val);
        }

        // 获取后继节点，用后继 - 根节点 与 上面的最小值比较
        if (root.right != null) {
            TreeNode succ = root.right;
            // 后继节点在右子树的最左边
            while (succ.left != null) {
                succ = succ.left;
            }

            diff = Math.min(diff, succ.val - root.val);
        }

        return diff;
    }
}
