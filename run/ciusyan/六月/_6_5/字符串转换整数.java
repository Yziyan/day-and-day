package run.ciusyan.六月._6_5;

/**
 * https://leetcode.cn/problems/string-to-integer-atoi/
 */
public class 字符串转换整数 {
    public int myAtoi(String s) {
        if (s == null) return 0;
        char[] chars = s.toCharArray();
        if (chars.length == 0) return 0;

        // 得到合法的字符数组（去掉前导空格）
        int index = 0;

        // 跳过前导空格
        while (index < chars.length && chars[index] == ' ') index++;

        // 说明全是空格
        if (index == chars.length) return 0;

        // 判断第一个字符是否是负号
        boolean neg;
        if ((neg = chars[index] == '-') || chars[index] == '+') {
            // 说明第一个字符是符号
            index++;
        }

        // 准备一个结果
        int res = 0;
        // 遍历到的每一个 “数字”
        int cur;

        // 用于判断是否溢出
        int minDivision = Integer.MIN_VALUE / 10;
        int minMod = Integer.MIN_VALUE % 10;

        // 遍历每一个字符，只到合法长度就可以了
        while (index < chars.length) {

            // 需要判断当前字符是否是数字，
            if (chars[index] < '0' || chars[index] > '9') break;

            // 将当前字符转换成负数
            cur = '0' - chars[index++];

            // 说明之后会溢出，要么是比最小值/10还小，如果刚好等于最小值/10，那么还要看是否比最小值的个位数大
            if ((res < minDivision) || (res == minDivision && cur <= minMod))
                return neg ? Integer.MIN_VALUE : Integer.MAX_VALUE;

            // 计算出下一次的值
            res = res * 10 + cur;
        }

        // 来到这里说明 res 肯定是没有溢出的，但是需要检验原先是否是负数，
        //  如果是就直接返回，因为 res 被转换成负数相加的
        //  否则需要返回相反数

        return neg ? res : -res;
    }

}
