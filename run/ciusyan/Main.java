package run.ciusyan;

import run.ciusyan._5_1.BubbleSort;
import run.ciusyan._5_2.InsertSort;
import run.ciusyan._5_2.MergeSort;
import run.ciusyan._5_2.QuickSort;
import run.ciusyan._5_2.SelectionSort;
import run.ciusyan._5_3.CountingSort;

public class Main {
    public static void main(String[] args) {
        int[] array = {10, 20, 31, 41, 98, 65, 21, 53, 8, 33, 87, 43, 29};
        CountingSort.sort(array);
        for (int i : array) {
            System.out.print(i + "_");
        }
    }
}
