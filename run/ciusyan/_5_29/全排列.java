package run.ciusyan._5_29;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode.cn/problems/permutations/
 */
public class 全排列 {

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) return result;

        // 从第 0 层开始搜索【nums 可以充当轨迹和去重】
        dfs(0, nums, result);

        return result;
    }

    /** 深度优先搜索 */
    private static void dfs(int level, int[] nums, List<List<Integer>> results) {
        if (level == nums.length) {
            // 记录结果
            List<Integer> result = new ArrayList<>();
            for (int num : nums) {
                result.add(num);
            }

            results.add(result);

            return;
        }


        // 列举所有可能，从 level 层开始
        for (int i = level; i < nums.length; i++) {
            // 将 level 与 i 位置交换
            swap(nums, level, i);

            // 往下一层搜索，轨迹不需要记录了，因为已经交换了，交换到 level 位置的就是此次的轨迹
            dfs(level + 1, nums, results);

            // 但是当调用完成后，需要还原现场
            swap(nums, i, level);
        }
    }

    /** 交换 i1 和 i2 索引位置的值 */
    private static void swap(int[] nums, int i1, int i2) {
        int temp = nums[i1];
        nums[i1] = nums[i2];
        nums[i2] = temp;
    }

    public static List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) return result;

        // 用于记录轨迹和查看是否已经访问过了
        List<Integer> track = new ArrayList<>();

        // 从第 0 层开始搜索
        dfs2(0, nums, track, result);

        return result;
    }

    private static void dfs2(int level, int[] nums, List<Integer> track, List<List<Integer>> results) {
        if (level == nums.length) {
            // 记录结果，但是注意这里需要深拷贝一份，要不然最终引用的地址的元素会被清空
            results.add(new ArrayList<>(track));

            return;
        }

        for (int num : nums) {
            if (track.contains(num)) continue;

            // 记录轨迹
            track.add(num);
            dfs2(level + 1, nums, track, results);
            // 还原现场，【删除最后一个元素】
            track.remove(track.size() - 1);
        }
    }

    public static List<List<Integer>> permute1(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length == 0) return result;

        // 用于记录轨迹
        int[] track = new int[nums.length];

        // 用于记录是否已经访问了
        boolean[] visited = new boolean[nums.length];

        // 从第 0 层开始排列
        dfs1(0, nums, track, visited, result);

        return result;
    }

    private static void dfs1(int level, int[] nums, int[] track, boolean[] visited, List<List<Integer>> results) {
        if (level == nums.length) {
            // 记录结果
            List<Integer> result = new ArrayList<>();
            for (int i : track) {
                result.add(i);
            }

            results.add(result);

            return;
        }

        // 列举所有可能，往下一层搜索
        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) continue;

            // 来到这里说明没有访问过此数字，记录轨迹
            track[level] = nums[i];
            visited[i] = true;
            // 往下钻
            dfs1(level + 1, nums, track, visited, results);
            // 需要还原现场
            visited[i] = false;
        }
    }

    public static void main(String[] args) {
        List<List<Integer>> permute = permute(new int[]{1, 2, 3});
        System.out.println(permute);
    }
}
