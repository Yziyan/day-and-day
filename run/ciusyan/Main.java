package run.ciusyan;

import run.ciusyan._5_1.BubbleSort;
import run.ciusyan._5_2.InsertSort;
import run.ciusyan._5_2.MergeSort;
import run.ciusyan._5_2.QuickSort;
import run.ciusyan._5_2.SelectionSort;
import run.ciusyan._5_3.CountingSort;
import run.ciusyan._5_3.RadixSort;
import run.ciusyan._5_3.ShellSort;
import run.ciusyan._5_4.ListGraph;

public class Main {
    static void test() {
        int[] array = {10, 20, 31, 41, 98, 65, 21, 53, 8, 33, 87, 43, 29};
        RadixSort.sort(array);
        for (int i : array) {
            System.out.print(i + "_");
        }
    }

    public static void main(String[] args) {
        // 构建一幅图
        ListGraph<String, Integer> graph = new ListGraph<>();
        graph.addEdge("v1", "v0", 9);
        graph.addEdge("v1", "v2", 3);
        graph.addEdge("v2", "v0", 2);
        graph.addEdge("v2", "v3", 5);
        graph.addEdge("v3", "v4", 1);
        graph.addEdge("v0", "v4", 6);
    }
}
