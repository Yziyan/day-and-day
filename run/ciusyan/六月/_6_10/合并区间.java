package run.ciusyan.六月._6_10;

import java.util.Arrays;
import java.util.Map;

/**
 * https://leetcode.cn/problems/merge-intervals/
 */
public class 合并区间 {
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return intervals;

        // 先根据区间的起始位置排序
        Arrays.sort(intervals, ((o1, o2) -> o1[0] - o2[0]));

        // [start, end] 默认是第一个区间
        int begin = intervals[0][0];
        int end = intervals[0][1];
        // 用于记录最终有几个区间
        int size = 0;
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] > end) {
                // 说明需要新开一个区间，记录上一个区间最远到达了哪里
                // 可以再原区间上覆盖，size 代表现在有几个区间了
                intervals[size][0] = begin;
                // 覆盖完了，别忘了区间数要 + 1，
                intervals[size++][1] = end;

                // 然后重新开一个新的区间
                begin = intervals[i][0];
            }

            // 否则就尝试将区间结尾推大，
            end = Math.max(end, intervals[i][1]);
        }
        // 因为是找到一个新区间后才记录上一个区间的，所以还需要补录最后一个区间
        intervals[size][0] = begin;
        intervals[size++][1] = end;

        // 因为复用以前的区间，那么直接从区间上拷贝一个区间即可
        return Arrays.copyOf(intervals, size);
    }
}
