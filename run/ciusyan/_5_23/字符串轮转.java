package run.ciusyan._5_23;

/**
 * https://leetcode.cn/problems/string-rotation-lcci/
 */
public class 字符串轮转 {
    public boolean isFlipedString(String s1, String s2) {
        if (s1 == null || s2 == null) return true;
        if (s1.length() != s2.length()) return false;

        // 将其中一个字符串拼接在一起
        String s3 = s1 + s1;

        // 然后看 s3 是否包含 s2
        return s3.contains(s2);
    }
}
