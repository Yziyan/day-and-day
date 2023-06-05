package run.ciusyan.六月._6_5;

/**
 * https://leetcode.cn/problems/integer-to-roman/
 */
public class 整数转罗马数字 {
    public String intToRoman(int num) {

        // 准备数字映射表
        String[][] roman = {
                {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"}, // 个位数
                {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"}, // 十位数
                {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"}, // 百位数
                {"", "M", "MM", "MMM"} // 千位数，因为题目最大直到 3000，所以只要可以表示三千即可
        };

        // 用于拼接结果，分别拼接千位、百位、十位、个位
        StringBuilder sb = new StringBuilder().
                append(roman[3][num / 1000 % 10]).append(roman[2][num / 100 % 10]).
                append(roman[1][num / 10 % 10]).append(roman[0][num % 10]);

        return sb.toString();
    }
}
