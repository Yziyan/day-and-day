package run.ciusyan.七月;


import run.ciusyan.common.TreeNode;

public class 验证二叉搜索树 {

    public boolean isValidBST(TreeNode root) {

        return root == null || getInfo(root).isBst;
    }

    private Info getInfo(TreeNode root) {
        if (root == null) return null;

        Info leftInfo = getInfo(root.left);
        Info rightInfo = getInfo(root.right);

        int min = root.val;
        int max = root.val;
        boolean isBst = true;
        if (leftInfo != null) {
            min = Math.min(min, leftInfo.min);
            max = Math.max(max, leftInfo.max);
            if (!leftInfo.isBst) isBst = false;
        }

        if (rightInfo != null) {
            min = Math.min(min, rightInfo.min);
            max = Math.max(max, rightInfo.max);
            if (!rightInfo.isBst) isBst = false;
        }

        if (leftInfo != null && leftInfo.max >= root.val) isBst = false;
        if (rightInfo != null && rightInfo.min <= root.val) isBst = false;

        return new Info(isBst, min, max);
    }

    private static class Info {
        boolean isBst;
        int min;
        int max;

        public Info(boolean isBst, int min, int max) {
            this.isBst = isBst;
            this.min = min;
            this.max = max;
        }
    }

}
