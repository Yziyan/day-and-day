package run.ciusyan.五月._5_23;


import java.util.Stack;

/**
 * https://leetcode.cn/problems/verify-preorder-serialization-of-a-binary-tree/
 */
public class 验证二叉树的前序序列化 {

    /**
     * 出度法
     */
    public boolean isValidSerialization(String preorder) {
        if (preorder == null) return false;

        String[] split = preorder.split(",");
        if (split.length == 0) return false;
        int outs = 1;
        for (String s : split) {
            // 当做访问完成上一条路径
            outs--;

            // 出度不能小于 0
            if (outs < 0) return false;

            if (!s.equals("#")) {
                outs += 2;
            }
        }

        return outs == 0;
    }

    /**
     * 方法一：栈
     *
     */
    public boolean isValidSerialization1(String preorder) {
        if (preorder == null) return false;

        String[] split = preorder.split(",");
        if (split.length == 0) return false;

        // 准备一个栈，因为一会还需要检查一开始加入的元素
        Stack<String> stack = new Stack<>();
        for (String s : split) {
            if (stack.isEmpty() || !s.equals("#") || !"#".equals(stack.peek())) {
                // 说明栈顶元素不是 #，添加完直接返回即可
                stack.push(s);
                continue;
            }
            // 来到这里说明栈顶为#
            if (!isNULL(stack, s)) return false;
            stack.push(s);
        }

        return stack.size() == 1 && stack.peek().equals("#");
    }

    private boolean isNULL(Stack<String> stack, String s) {
        if (!"#".equals(s)) return true;
        // 说明新加入的符号也为 #
        // 连续弹出两个
        stack.pop();

        int size  = stack.size() - 1;
        // 如果只能弹出一个，说明有问题
        if (size < 0) return false;
        stack.pop();

        if (size != 0) {
            // 说明还有元素，可以递归检查
            return isNULL(stack, stack.peek());
        }

        return true;
    }

}
