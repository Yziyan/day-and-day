package run.ciusyan._5_24;

/**
 * https://leetcode.cn/problems/valid-anagram/
 */
public class 有效字母异位词 {
    public boolean isAnagram(String s, String t) {
        if (s == null || t == null) return false;
        char[] chars1 = s.toCharArray();
        char[] chars2 = t.toCharArray();
        if (chars1.length != chars2.length) return false;

        // 先计算 chars1 每个字母出现的次数
        //  由于都是小写字母，这里就使用 int[26] 数组表示即可
        int[] map = new int[26];

        for (char c : chars1) {
            map[c - 'a']++;
        }

        // 然后利用 map 查看 chars2
        for (char c : chars2) {
            map[c - 'a']--;

            // 如果有一个字母减到小于 0 了，说明不是异位词
            if (map[c - 'a'] < 0) return false;
        }

        return true;
    }
}