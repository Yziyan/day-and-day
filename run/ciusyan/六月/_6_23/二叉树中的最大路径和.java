package run.ciusyan.六月._6_23;

import run.ciusyan.common.TreeNode;

/**
 * https://leetcode.cn/problems/binary-tree-maximum-path-sum/
 */
public class 二叉树中的最大路径和 {
    public int maxPathSum(TreeNode root) {
        if (root == null) return 0;

        // 获取根节点路径信息中的 最大路径和
        return getPathInfo(root).maxPath;
    }

    /** 获取 root 节点的路径信息 */
    private PathInfo getPathInfo(TreeNode root) {
        if (root == null) return null;

        // 先获取左右子树的路径信息
        PathInfo leftInfo = getPathInfo(root.left);
        PathInfo rightInfo = getPathInfo(root.right);

        // 先计算从根节点出发的最大路径和，有三种可能
        //  1、只含 root 2、包含左子树 3、包含右子树
        int maxPathFromRoot = root.val;
        // 再计算整颗树的最大路径和，有四种可能
        //  1、只含 root 2、只在左子树 3、只在右子树 4、根 + (左 or 右) 5、根节点 + 左 + 右
        int maxPath = root.val;

        if (leftInfo != null) {
            // 尝试添加上左子树的最大路径和
            maxPathFromRoot = Math.max(maxPathFromRoot, leftInfo.maxPathFromRoot + root.val);

            // 只在右子树
            maxPath = Math.max(maxPath, leftInfo.maxPath);
        }

        if (rightInfo != null) {
            // 尝试添加上右子树的最大路径和
            maxPathFromRoot = Math.max(maxPathFromRoot, rightInfo.maxPathFromRoot + root.val);

            // 只在左子树
            maxPath = Math.max(maxPath, rightInfo.maxPath);
        }

        // maxPathFromRoot 是根节点加上 (左 or 右)
        maxPath = Math.max(maxPath, maxPathFromRoot);

        // 最后一种情况是：根节点 + 左 + 右
        if (leftInfo != null && rightInfo != null &&
                // 左右都要大于 0，才有必要进来比较
                leftInfo.maxPathFromRoot > 0 && rightInfo.maxPathFromRoot > 0) {
            // 左 + 右 + 根
            int mayMaxSum = leftInfo.maxPathFromRoot + rightInfo.maxPathFromRoot + root.val;
            maxPath = Math.max(maxPath, mayMaxSum);
        }

        // 构建路径信息
        return new PathInfo(maxPathFromRoot, maxPath);
    }

    /** 节点的路径信息 */
    private static class PathInfo {
        /** 该节点的路径和，但是必须要从根节点出发 */
        int maxPathFromRoot;
        /** 该节点最大路径和 */
        int maxPath;
        PathInfo(int maxPathFromRoot, int maxPath) {
            this.maxPathFromRoot = maxPathFromRoot;
            this.maxPath = maxPath;
        }
    }
}
