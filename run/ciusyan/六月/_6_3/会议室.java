package run.ciusyan.六月._6_3;

import java.util.Arrays;
import java.util.PriorityQueue;

public class 会议室 {

    /**
     * 会议室 1
     * @param intervals：会议间隔
     * @return 能否参加所有会议
     */
    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return true;

        // 按照会议开始的时间对所有会议进行排序
        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);

        for (int i = 1; i < intervals.length; i++) {

            // 说明下一场会议开始的时候，上一场会议都还没结束
            if (intervals[i][0] < intervals[i - 1][1]) return false;
        }

        return true;
    }

    /**
     * 会议室 2
     * @param intervals：会议间隔
     * @return 最少需要几间会议室
     */
    public static int minMeetingRooms1(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;

        // 按照会议的开始时间排序
        Arrays.sort(intervals, (o1, o2) -> o1[0] - o2[0]);

        // 准备一个小顶堆，用于维护会议结束时间的相对顺序，堆顶的代表最早结束的会议
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        // 将第一场会议的结束时间入堆
        minHeap.offer(intervals[0][1]);

        for (int i = 1; i < intervals.length; i++) {
            // 不断与堆顶元素比较，若最早结束的会议比当前会议的时间还小，说明可以复用会议室
            if (minHeap.peek() <= intervals[i][0]) {
                // 代表复用会议室
                minHeap.poll();
            }

            // 到达这里说明需要将会议的结束时间加入堆中了
            minHeap.offer(intervals[i][1]);
        }

        // 最终堆里有多少会议，就需要多少间会议室
        return minHeap.size();
    }


    public static int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) return 0;

        // 准备两个数组，用于记录结束时间和开始时间
        int[] begins = new int[intervals.length];
        int[] ends = new int[intervals.length];
        for (int i = 0; i < intervals.length; i++) {
            begins[i] = intervals[i][0];
            ends[i] = intervals[i][1];
        }

        // 对开始和结束时间进行排序
        Arrays.sort(begins);
        Arrays.sort(ends);

        // 需要的会议室 & 最早结束会议的索引
        int room = 1, endIndex = 0;

        for (int i = 1; i < intervals.length; i++) {
            // 比较开始时间和最早结束会议的时间
            if (begins[i] < ends[endIndex]) {
                // 说明最早结束的会议还没结束，需要单独开一间会议室
                room++;
            } else {
                // 说明可以复用会议室了，将最早结束的时间往后移
                endIndex++;
            }
        }

        return room;
    }
}
