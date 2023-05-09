package run.ciusyan._5_9;

import java.util.ArrayList;
import java.util.List;

/**
 * 汉罗塔
 */
public class Hanota {
    public void hanota(List<Integer> A, List<Integer> B, List<Integer> C) {
        if (A == null || B == null || C == null) return;
        // 有多少根柱子
        int n = A.size();

        move(A, B, C, n);
    }

    /**
     * 从 src 上，将第 n 个盘子，挪动到 dist 上面
     */
    private void move(List<Integer> src, List<Integer> central, List<Integer> dist, int n) {
        if (n == 1) {
            // 只有一个盘子（删除源柱 并且 添加到目标柱子）
            dist.add(src.remove(0));
            return;
        }

        // 将 n - 1个盘子，从A 挪动到 B
        move(src, dist, central, n - 1);
        // 将最下面的盘子从A挪动到C的最下面
        dist.add(0, src.remove(0));
        // 将 n - 1 个盘子，从 B 挪动到 C
        move(central, src, dist, n - 1);
    }
}
