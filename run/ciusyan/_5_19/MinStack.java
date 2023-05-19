package run.ciusyan._5_19;

import java.util.Stack;

/**
 * https://leetcode.cn/problems/min-stack/
 *  使用链表实现
 *
 */
public class MinStack {

    /** 创建一个dummyHead节点，采用头插法维护链表来模拟栈 */
    private Node first;

    public MinStack() {
        first = new Node();
        // 表示之后的第一个节点一定会是最小的。
        first.minVal = Integer.MAX_VALUE;
    }

    public void push(int val) {
        int minVal = first.minVal;
        if (val < first.minVal) {
            // 说明比最小值还小
            minVal = val;
        }

        // 插入链表头部
        first = new Node(val, minVal, first);
    }

    public void pop() {
        // 删除 first 节点即可
        first = first.next;
    }

    public int top() {
        return first.val;
    }

    public int getMin() {
        return first.minVal;
    }

    private static class Node {
        /** 当前节点的值 */
        int val;
        /** 当前所有节点中的最小值 */
        int minVal;
        /** 后继节点 */
        Node next;

        public Node() {}

        public Node(int val, int minVal, Node next) {
            this.val = val;
            this.minVal = minVal;
            this.next = next;
        }
    }

}

/**
 *  使用两个栈实现
 */
class MinStack1 {

    /** 正常栈 */
    private Stack<Integer> stack;

    /** 维护最小值 */
    private Stack<Integer> minStack;

    public MinStack1() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    public void push(int val) {
        // 放入正常栈
        stack.push(val);

        if (minStack.isEmpty()) {
            // 说明是第一个元素
            minStack.push(val);
        } else {
            // 查看栈顶元素
            Integer minVal = minStack.peek();
            if (val < minVal) {
                // 说明现在的值要小
                minVal = val;
            }

            // 放入最小值
            minStack.push(minVal);
        }
    }

    public void pop() {
        stack.pop();
        minStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
