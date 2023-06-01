package run.ciusyan.五月._5_24;


/**
 * https://leetcode.cn/problems/reverse-words-in-a-string/
 */
public class 反转字符串中的单词 {
    public static String reverseWords(String s) {
        if (s == null) return null;
        char[] chars = s.toCharArray();
        if (chars.length == 0) return "";

        // 先去除字符串多余的空格
        int valid = removeSpace(chars);

        // 再对整体逆序
        reverse(chars, 0, valid);
        System.out.println(new String(chars, 0, valid));

        // 最后将每一个单词逆序
        int i = 1, begin = 0;
        while (i < valid) {
            // 遇到一个空格的时候才进行逆序
            if (chars[i] == ' ') {
                reverse(chars, begin, i);
                begin = i + 1;
            }

            i++;
        }
        // 因为最后一个单词遇不到空格了，在循环里面是不会进行翻转的
        //  所以需要将最后一个单词翻转
        reverse(chars, begin, valid);

        return new String(chars, 0, valid);
    }

    /** 翻转 chars 数组中 [begin, end) 范围的元素 */
    private static void reverse(char[] chars, int begin, int end) {

        // 因为不包含 end 位置
        end--;

        while (begin < end) {
            char s = chars[begin];
            chars[begin++] = chars[end];
            chars[end--] = s;
        }
    }

    /** 去除字符串中多余的空格 返回有效字符的截止索引*/
    private static int removeSpace(char[] chars) {

        // 现在应该放的位置
        int i = 0;
        // 正在遍历的位置
        int cur = 0;
        // 记录之前是否已经出现过空格了（默认有一个哨兵位置已经出现过空格了）
        boolean space = true;

        while (cur < chars.length) {
            if (space && chars[cur] == ' ') {
                // 说明之前已经出现过空格了，然后现在拿到的字符也是空格
                //  直接遍历下一个即可
                cur++;
                continue;
            }

            // 如果当前字符不是空格，记得记录 space 的状态
            space = chars[cur] == ' ';

            // 来到这里，说明不管现在是不是空格，都要将其设置到 i 位置
            chars[i++] = chars[cur++];
        }

        if (space) {
            // 说明最后有空格
            i--;
        }

        System.out.println(new String(chars, 0, i));

        return i;
    }

    public static void main(String[] args) {
        String s = " a      good   example      ";
        System.out.println(reverseWords(s));
    }

}
