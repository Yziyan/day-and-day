package run.ciusyan._5_12;


/**
 * 最长公共子数组：
 * https://leetcode.cn/problems/maximum-length-of-repeated-subarray/
 */
public class LCSubArray {

    /**
     * 动态规划：一维数组
     */
    public int findLength(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 1 || nums2 == null || nums2.length == 0) return 0;

        int[] rowNums = nums1;
        int[] colNums = nums2;
        // 取较短的为列数组
        if (colNums.length > rowNums.length) {
            colNums = nums1;
            rowNums = nums2;
        }

        // 因为每次只会用到右上角的值，所以使用一个一维数组即可
        int[] dp = new int[colNums.length + 1];

        // 最大长度
        int max = 0;
        for (int row = 1; row <= rowNums.length; row++) {

            // 从后往前计算，就可以直接拿到左上角的值了，左上角的值，其实就是 dp[col - 1]
            for (int col = colNums.length; col >= 1; col--) {
                // 说明以这两个字符结尾，没有公共子数组
                if (rowNums[row - 1] != colNums[col - 1]) {
                    // 因为是一维数组了，所以需要覆盖值
                    dp[col] = 0;
                } else {
                    // 到达这里，说明至少有最后一个元素的公共子数组
                    //  那么 + 左上角即可
                    dp[col] = dp[col - 1] + 1;

                    // 说明最大值有可能更新了
                    max = Math.max(max, dp[col]);

                }
            }
        }

        return max;
    }

    /**
     * 动态规划：二维数组
     */
    public int findLength1(int[] nums1, int[] nums2) {
        if (nums1 == null || nums1.length == 1 || nums2 == null || nums2.length == 0) return 0;

        int[] rowNums = nums1;
        int[] colNums = nums2;

        // 短的做为列数组
        if (colNums.length > rowNums.length) {
            colNums = nums1;
            rowNums = nums2;
        }

        // 定义状态：dp(i, j) 代表以 nums1[i - 1] 结尾的子数组与 nums2[j - 1] 结尾的最长重复子数组的长度
        int[][] dp = new int[rowNums.length + 1][colNums.length + 1];
        // 初始值是 dp(i, 0) = dp(0, j) = 0

        // 最大值
        int max = 0;
        for (int row = 1; row <= rowNums.length; row++) {

            for (int col = 1; col <= colNums.length; col++) {
                // 说明没有以 nums1[i - 1] 和 nums2[j - 1] 结尾的最长重复子数组
                if (rowNums[row - 1] != colNums[col - 1]) continue;
                // 说明两数组最后一个元素相等，那么可以根据左上角推出自己
                //  其实就是将两数组最后一个元素去掉后，再看以最后一个字符结尾的最大长度
                dp[row][col] = dp[row - 1][ col - 1] + 1;

                // 来到这里，才有可能更新最大的长度
                max = Math.max(max, dp[row][col]);
            }
        }

        return max;
    }

}
