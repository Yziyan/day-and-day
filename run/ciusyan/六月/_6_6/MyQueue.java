package run.ciusyan.六月._6_6;

import java.util.Stack;

/**
 * https://leetcode.cn/problems/implement-queue-using-stacks/
 */
public class MyQueue {
    /** 用于模拟入队 */
    private Stack<Integer> inStack;

    /** 用于模拟出队 */
    private Stack<Integer> outStack;

    public MyQueue() {
        inStack = new Stack<>();
        outStack = new Stack<>();
    }

    public void push(int x) {
        // 直接将元素放入 入栈即可
        inStack.push(x);
    }

    public int pop() {
        // 查看出栈是否有元素
        checkOutStack();

        // 然后弹出栈顶元素
        return outStack.pop();
    }

    /** 检查出栈是否为空，如果为空那么就将入栈的元素全部弹到出栈中去 */
    private void checkOutStack() {
        if (!outStack.isEmpty()) return;

        // 说明为空，尝试将入栈的所有元素弹到 出栈中
        while (!inStack.isEmpty()) {
            outStack.push(inStack.pop());
        }
    }

    public int peek() {
        // 查看出栈是否有元素
        checkOutStack();

        // 然后查看栈顶元素
        return outStack.peek();
    }

    public boolean empty() {
        // 如果两个栈都为空，那么说明此 队列 为空
        return inStack.isEmpty() && outStack.isEmpty();
    }
}
