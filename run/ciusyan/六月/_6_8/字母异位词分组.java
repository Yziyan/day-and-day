package run.ciusyan.六月._6_8;

import java.util.*;

/**
 * https://leetcode.cn/problems/group-anagrams/
 */
public class 字母异位词分组 {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String >> results = new ArrayList<>();
        if (strs == null || strs.length == 0) return results;

        // 准备一个 Map 用于记录 <str, [str, str1, str2]>
        Map<String, List<String>> map = new HashMap<>();

        for (String str : strs) {
            char[] chars = str.toCharArray();
            // 对字符串进行排序
            Arrays.sort(chars);

            // 再转换成 Map 的 key
            String key = String.valueOf(chars);
            List<String> list = map.get(key);
            // 说明以前不存在这个key
            if (list == null) list = new ArrayList<>();
            // 来到这里说明有 value 了，将此次的字符串也添加进去
            // 注意这里要放入的是原字符串
            list.add(str);
            map.put(key, list);
        }

        // 将 Map 中所有的值添加到结果中
        results.addAll(map.values());

        return results;
    }
}
