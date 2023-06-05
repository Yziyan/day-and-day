package run.ciusyan.六月._6_5;

/**
 * https://leetcode.cn/problems/string-to-integer-atoi/
 */
public class 字符串转换整数 {
    public static int myAtoi(String s) {
       if (s == null) return 0;
       char[] chars = s.toCharArray();

       // 合法的起始索引
       int index = 0;

       // 1、处理前导空格
        while (index < chars.length && chars[index] == ' ') index++;

        // 有可能全是空格，或者根本没有字符
        if (index == chars.length) return 0;

        // 2、处理符号
        boolean neg; // 如果是负号，将其记录下来
        if ((neg = chars[index] == '-') || chars[index] == '+') index++;

        //  返回结果
        int res = 0;
        // 用于判断是否溢出
        int division = Integer.MIN_VALUE / 10;
        int mod = Integer.MIN_VALUE % 10;

        // 3、处理数字
        while (index < chars.length) {
            // 取出当前字符
            char curChar = chars[index];
            // 要保证当前字符是数字
            if (curChar < '0' || curChar > '9') break;

            // 将当前字符变为负数
            int curNum = '0' - curChar;

            // 但是需要判断是否溢出了，将计算后的值，若倒推不回去，则说明肯定溢出了
            if (res < division || (res == division && curNum <= mod))
                return neg ? Integer.MIN_VALUE : Integer.MAX_VALUE;

            // 保存当前结果
            res = res * 10 + curNum;
            // 往下走
            index++;
        }

        return neg ? res : -res;
    }

    public static void main(String[] args) {
        System.out.println(myAtoi("2147483648"));
    }

}
