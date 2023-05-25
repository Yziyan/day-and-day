package run.ciusyan._5_22;

import java.util.Stack;

/**
 * https://leetcode.cn/problems/daily-temperatures/
 */
public class 每日温度 {


    /**
     * 类似动态规划求解
     *
     */
    public int[] dailyTemperatures(int[] temperatures) {
        if (temperatures == null) return null;
        int length = temperatures.length;
        if (length < 1) return new int[0];

        // 结果
        int[] results = new int[length];

        // 从后往前计算，因为肯定没有温度比最后一天高了
        for (int i = length - 2; i >= 0; i--) {
            // 后面天的索引，默认后一天
            int backDay = i + 1;

            // 说明当前温度很高，得往后看
            while (temperatures[i] >= temperatures[backDay]) {
                // 往后面加，说明之后的天，都没有比自己温度还高的了
                if (results[backDay] == 0) break;

                // 说明后面还有比 backDay 还高的温度
                backDay += results[backDay];
            }

            // 来到这里说明已经找到后面的最高温度了
            if (temperatures[backDay] > temperatures[i]) {
                // 来到这里说明后面的最高温度比自己还高

                results[i] = backDay - i;
            }
        }

        return results;
    }

    /**
     * 单调栈求解
     *
     */
    public int[] dailyTemperatures1(int[] temperatures) {
        if (temperatures == null) return null;
        int length = temperatures.length;
        if (length < 1) return new int[0];

        // 装结果
        int[] results = new int[length];
        // 准备一个单调栈（栈存放索引）
        Stack<Integer> stack = new Stack<>();

        // 遍历求解
        for (int i = 0; i < length; i++) {

            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                // 说明当前温度比之前的温度高，升温了，需要记录距离之前的多少天
                Integer previousDay = stack.pop();
                results[previousDay] = i - previousDay;
            }

            // 说明没有人的温度比自己低了，可以入栈了
            stack.push(i);
        }

        return results;
    }
}
