package run.ciusyan.五月._5_2;

/**
 * 堆排序，可以看做是对选择排序的一种优化，核心是使用堆来优化查找最值，如果使用最大堆。
 * 将待排序序列原地入堆以后，最大值默认就是在堆顶，那么直接将堆顶和堆尾交换，然后进行下滤操作
 * 其中的难点是如何将序列入堆，有两种方式：
 *  1、自上而下的上滤：从上往下，挨个进行上滤操作，与它的祖先节点比较，放置到合适的位置。
 *  2、自下而上的下滤：从下网上，挨个进行下滤操作，用它与较大的祖孙节点比较，放置到合适的位置。
 *  选择第二种的性能要高一些，因为更多的节点走了更短的路径。
 *
 */
public class HeapSort {

    /** 堆里的元素数量 */
    private int heapSize;

    public void sort(int[] nums) {
        if (nums == null || nums.length < 2) return;

        heapSize = nums.length;

        // 原地入堆
        // 采用自下而上的下滤，从最后一个非叶子结点开始
        for (int i = (heapSize >> 1) - 1; i >= 0; i--) {
            siftDown(i, nums);
        }

        // 然后将堆顶与堆尾交换，再维护堆的性质
        //      堆里只剩一个元素就不用交换了
        while (heapSize > 1) {
            // 相当于每一轮排好一个，拍好后堆的元素就减少一个
            heapSize--;

            // 堆顶元素
            int temp = nums[0];
            nums[0] = nums[heapSize];
            // 堆尾元素
            nums[heapSize] = temp;

            // 下滤（维护堆的性质）
            siftDown(0, nums);
        }
    }

    /**
     * 下滤操作
     */
    private void siftDown(int index, int[] nums) {
        int half = heapSize >> 1;

        // 先保存下滤元素
        int element = nums[index];
        // 非叶子结点才有有必要下滤
        while (index < half) {
            // 找出较大的子节点，默认是左子节点
            int childIndex = (index << 1) + 1;
            int child = nums[childIndex];

            // 但是有可能是右子节点
            int rightIndex = childIndex + 1;
            if (rightIndex < heapSize && nums[rightIndex] > child) {
                // 来到这里说明右子节点大
                childIndex = rightIndex;
                child = nums[rightIndex];
            }

            // 与较大的子节点比较，说明已经找到了合适的位置
            if (element >= child) break;

            // 来到这里，说明之后还需要往下尝试，先将路径记录了
            nums[index] = child;
            index = childIndex;
        }

        // 最后要给下滤的位置赋值
        nums[index] = element;
    }

    public static void main(String[] args) {
        int[] array = {10, 20, 31, 41, 98, 65, 21, 53, 8, 33, 87, 43, 29};
        HeapSort heapSort = new HeapSort();
        heapSort.sort(array);
        for (int i : array) {
            System.out.print(i + "_");
        }
    }
}
