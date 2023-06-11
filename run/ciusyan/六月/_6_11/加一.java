package run.ciusyan.六月._6_11;

/**
 * https://leetcode.cn/problems/plus-one/
 */
public class 加一 {
    public int[] plusOne(int[] digits) {
        if (digits == null || digits.length == 0) return digits;

        for (int i =  digits.length - 1; i >= 0; i--) {

            // 说明不需要进位，加一返回即可。
            if (digits[i] != 9) {
                digits[i]++;

                // 多半是需要从这里返回的
                return digits;
            }

            // 说明加一后，会进位
            digits[i] = 0;
        }

        // 如果没有在前面就退出，那么说明进位进到第一位来了，比如给的数字是 999
        int[] res = new int[digits.length + 1];

        // 那么返回的数字应该是 1000
        res[0] = 1;

        return res;
    }
}
