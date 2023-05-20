package run.ciusyan._5_20;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * https://leetcode.cn/problems/sliding-window-maximum/
 */
public class 滑动窗口的最大值 {

    /**
     * 单调队列解法
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length < k) return new int[]{};

        // 窗口的总数量
        int winCount = nums.length - k + 1;
        int[] windowMaxes = new int[winCount];

        // 准备一个单调队列，用于保存相对的最大值（注：队列存储元素的索引）
        Deque<Integer> deque = new LinkedList<>();

        for (int i = 0; i < nums.length; i++) {

            while (!deque.isEmpty() && nums[i] >= nums[deque.peekLast()]) {
                // 从尾部一直往前弹，保证单调性
                deque.pollLast();
            }

            // 来到这里说明前面没有比 nums[i] 大的值了，将其入队
            deque.offerLast(i);

            // 计算窗口索引
            int winIdx = i - k + 1;

            // 说明窗口索引还不合法
            if (winIdx < 0) continue;

            // 获取现在队头最大的索引
            Integer maxIndex = deque.peek();
            if (maxIndex < winIdx) {
                // 说明现在的最大索引不合法了，将队头弹出
                deque.poll();
                maxIndex = deque.peek();
            }

            windowMaxes[winIdx] = nums[maxIndex];
        }

        return windowMaxes;
    }

    /**
     * 较为直接的解法
     *
     */
    public int[] maxSlidingWindow1(int[] nums, int k) {
        if (nums == null || nums.length < k) return new int[]{};

        // 窗口的总数量
        int winCount = nums.length - k + 1;
        int[] results = new int[winCount];

        // 求出第一个窗口的最大值索引
        int maxIndex = 0;
        for (int i = 1; i < k; i++) {
            if (nums[i] >= nums[maxIndex]) {
                // 说明后面的比前面的大
                maxIndex = i;
            }
        }

        results[0] = nums[maxIndex];

        // 往后面滑动每一个窗口，求之后窗口的最大值
        for (int i = 1; i < winCount; i++) {
            // 新滑入元素的索引
            int newIndex = i + k - 1;
            if (i > maxIndex) {
                maxIndex = i;
                // 说明最大元素的索引无效了，需要重新计算窗口的值
                for (int j = i + 1; j <= newIndex; j++) {
                    if (nums[j] >= nums[maxIndex]) {
                        maxIndex = j;
                    }
                }

                // 设置完最大值后跳到下一个
                results[i] = nums[maxIndex];
                continue;
            }

            // 来到这里说明以前的最大索引还有效
            if (nums[newIndex] >= nums[maxIndex]) {
                // 说明新加入的元素要大一些
                maxIndex = newIndex;
            }

            // 给窗口赋值
            results[i] = nums[maxIndex];
        }

        return results;
    }
}
