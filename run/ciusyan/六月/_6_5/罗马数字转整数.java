package run.ciusyan.六月._6_5;

/**
 * https://leetcode.cn/problems/roman-to-integer/
 */
public class 罗马数字转整数 {
    public int romanToInt(String s) {
        if (s == null) return 0;
        char[] chars = s.toCharArray();
        if (chars.length == 0) return 0;

        // 准备一个映射的数组，先将每一个罗马数字映射成数字
        int[] maps = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            switch (chars[i]) {
                case 'I' : {
                    maps[i] = 1;

                    break;
                }
                case 'V' : {
                    maps[i] = 5;

                    break;
                }
                case 'X' : {
                    maps[i] = 10;

                    break;
                }
                case 'L' : {
                    maps[i] = 50;

                    break;
                }
                case 'C' : {
                    maps[i] = 100;

                    break;
                }
                case 'D' : {
                    maps[i] = 500;

                    break;
                }
                default: {
                    maps[i] = 1000;
                }
            }
        }

        // 再将 maps 的元素，转换成结果
        int res = maps[maps.length - 1];
        for (int i = maps.length - 2; i >= 0; i--) {
            // 说明前一个数要小，那么是减去当前的值
            // 否则加上当前值就行了
            res += maps[i] < maps[i + 1] ? -maps[i] : maps[i];
        }

        return res;
    }
}
