package run.ciusyan.六月._6_25;

import run.ciusyan.common.TreeNode;

/**
 * https://leetcode.cn/problems/balanced-binary-tree/
 */
public class 平衡二叉树 {

    /**
     * 也是可以使用二叉树的递归套路求解。
     * 当能收集到左右子树信息的时候，怎么样才代表 root 平衡呢？
     *      1. root 的左子树平衡
     *      2. root 的右子树平衡
     *      3. root 的左右子树高度差不大于 1
     * 那么根据上述可能性，要收集什么信息呢？对其可能性求全集后，需要收集：
     *      1. 树是否平衡
     *      2. 树的高度
     * 那么就可以构建 Info 类了。有了 Info，再根据收集到 root 左右子树的信息和所有可能性，
     * 构建出当前 root 的 Info。
     *
     */
    public boolean isBalanced(TreeNode root) {
        return getInfo(root).isBalanced;
    }

    private Info getInfo(TreeNode root) {
        // 因为这里不返回 null，在上游就不需要判断 null 了
        if (root == null) return new Info(true, 0);

        // 收集左右子树的 Info
        Info leftInfo = getInfo(root.left);
        Info rightInfo = getInfo(root.right);

        // 为当前 root 构建这俩信息
        //  默认是平衡的，默认就只有自己的高度
        boolean isBalanced = true;
        int height = 1;

        // 当前树的高度是： 1 + 左右子树的最高高度
        height += Math.max(leftInfo.height, rightInfo.height);

        // 说明左右子树有一个不平衡
        if (!leftInfo.isBalanced || !rightInfo.isBalanced) isBalanced = false;

        // 说明左右子树的高度大于 1，也不平衡
        if (Math.abs(leftInfo.height - rightInfo.height) > 1) isBalanced = false;

        return new Info(isBalanced, height);
    }

    private static class Info {
        /** 是否是平衡的 */
        boolean isBalanced;
        /** 节点高度 */
        int height;

        public Info(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }

    /**
     * 其实也是二叉树的递归套路
     * 但是这个题的两个信息，可以合并一下，我们可以只利用一个高度，就可以代表 root 是否平衡的。
     * 如果左右子树是不平衡的，那么记录它的真实高度也无效，
     *      所以可以随便记录一个错误的高度，比如我们这里用 -1 表示 root 是不平衡的。
     * 那么递归函数中，也是先收集左右子树的高度。
     *      若左右子树的高度有一个是不合法的 -1；
     *      或者左右子树的高度合法，但是它们的高度差大于 1 了。
     * 那么说明当前 root 也是不平衡的，返回 -1 作为自己的高度。
     * 所以只有一个信息了，就不需要额外的 Info 了，直接使用递归函数的返回值即可。
     *
     */
    public boolean isBalanced1(TreeNode root) {
        return height(root) >= 0;
    }

    /***
     * 返回 root 这棵树的高度
     * @return -1 代表不平衡，其余正常维护
     */
    private int height(TreeNode root) {
        if (root == null) return 0;

        // 先收集左右子树的高度
        int leftH = height(root.left);
        int rightH = height(root.right);

        // 如果左右子树的高度有一个是 -1，说明没必要记录真实的高度了，
        // 或者如果左右子树的高度差都大于 1 了，也没必要记录真实的高度了
        //  都返回 -1，代表不平衡
        if (leftH == -1 || rightH == -1 || Math.abs(leftH - rightH) > 1)  return -1;

        // 否则说明此时树还是平衡的，记录真实的高度
        return Math.max(leftH, rightH) + 1;
    }
}
