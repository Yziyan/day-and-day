package run.ciusyan.六月._6_24;

import run.ciusyan.common.TreeNode;

/**
 * https://leetcode.cn/problems/validate-binary-search-tree/
 */
public class 验证二叉搜索树 {
    /** 使用二叉树的递归套路求解 */
    public boolean isValidBST(TreeNode root) {
        // 如果是 空树，也认为是 BST
        return root == null || getInfo(root).isVBst;
    }

    /** 获取 root 节点的 Info */
    private Info getInfo(TreeNode root) {
        if (root == null) return null;

        // 先收集左右子树的信息
        Info leftInfo = getInfo(root.left);
        Info rightInfo = getInfo(root.right);

        // 再利用左右子树上报的信息构建当前 root 节点的信息
        //  默认 root 是 BST，最大最小值是当前节点自己的值
        boolean isVBst = true;
        int max = root.val, min = root.val;

        if (leftInfo != null) {
            // 如果在左子树收集上了信息，尝试推大最大值和最小值
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);

            // 左子树不是 BST，那么 root 肯定也不是了
            if (!leftInfo.isVBst) isVBst = false;
        }

        if (rightInfo != null) {
            // 如果在右子树收集上了信息，尝试推大最大值和最小值
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);

            // 右子树不是 BST，那么 root 肯定也不是了
            if (!rightInfo.isVBst) isVBst = false;
        }

        // 如果左右子树是 BST，但是不满足 BST 的大小关系
        if (leftInfo != null && leftInfo.max >= root.val) {
            // 左子树必须比 root 小
            isVBst = false;
        }

        if (rightInfo != null && rightInfo.min <= root.val) {
            // 右子树必须比 root 大
            isVBst = false;
        }

        return new Info(isVBst, max, min);
    }

    /** 每一个节点的信息 */
    private static class Info {
        boolean isVBst;
        int max;
        int min;

        Info(boolean isVBst, int max, int min) {
            this.isVBst = isVBst;
            this.max = max;
            this.min = min;
        }
    }
}
