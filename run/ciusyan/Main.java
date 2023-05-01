package run.ciusyan;

import run.ciusyan.排序算法.BubbleSort;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] array = {10, 20, 31, 41, 98, 65, 21, 53, 8, 33, 87, 43, 29};
        BubbleSort.sort3(array);
        for (int i : array) {
            System.out.print(i + "_");
        }
    }
}
