package run.ciusyan.六月._6_7;

import java.util.Stack;

/**
 * https://leetcode.cn/problems/valid-parentheses/
 */
public class 有效的括号 {
    public boolean isValid(String s) {
        if (s == null) return false;
        char[] chars = s.toCharArray();
        if (chars.length == 0) return false;

        // 准备一个栈，用于记录左括号
        Stack<Character> stack = new Stack<>();

        // 遍历字符
        for (char c : chars) {

            // 如果字符是左括号，那么将其入栈，
            // 但为了之后与右括号匹配方便，其实可以直接入对应的右括号
            if (c == '(' || c == '[' || c == '{') {
                // 将其入栈
                stack.push(c == '(' ? ')' : c == '[' ? ']' : '}' );
            } else {
                // 说明是右括号，
                //  1、如果栈直接为空，直接加右括号，那么说明肯定不合法
                //  2、说明栈里有左括号，但是与当前右括号不匹配，那么都直接返回
                if (stack.isEmpty() || stack.pop() != c) return false;
            }
        }

        // 来到这里，还需要查看栈里面是否还有括号，有就说明不匹配
        return stack.isEmpty();
    }
}
