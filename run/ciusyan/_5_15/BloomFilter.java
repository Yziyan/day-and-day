package run.ciusyan._5_15;

/**
 * 布隆过滤器：
 *  是一种通过一组二进制向量、多个哈希函数实现的数据结构，
 *  它的空间利用率和查询的时间都远超于一般的算法，但最大的缺点是存在一定的误判率。
 *  这个误判率取决于二进制向量的位数和哈希函数的数量。
 *  可用于判断一个元素一定不存在或者可能存在的数据结构。
 *  常被用于：网页的黑名单系统、垃圾邮件过滤、爬虫的网址判重、解决缓存穿透等问题。
 *
 */
public class BloomFilter <V> {

    /** 具体的二进制向量，一个long占用8个字节，可存放64位的数据 */
    private long[] bits;

    /** 二进制向量的长度（一共有多少个二进制位） */
    private int bitSize;

    /** 哈希函数的个数 */
    private int hashSize;

    /**
     * 需要使用者告知一些信息
     * @param n：大致的数据规模
     * @param p：能接受的误判率
     */
    public BloomFilter(int n, double p) {
        if (n <= 0 || p >= 1 || p <= 0) return;

        // 这里面的一些公式，是科学家们研究出来的，仅做运用。
        double ln2 = Math.log(2);

        // 二进制向量的位数
        bitSize = (int) (-(n * Math.log(p)) / (ln2 * ln2));
        // 哈希函数的数量
        hashSize = (int) (bitSize * ln2 / n);

        // 初始化二进制向量
        bits = new long[(bitSize + Long.SIZE - 1) / Long.SIZE];
    }

    /** 添加元素 */
    public void put(V value) {
        valueCheck(value);

        // 获取 value 的哈希值
        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;

        // 有多少个哈希函数，就利用哈希值计算出多少索引
        for (int i = 1; i <= hashSize; i++) {
            int combineHash = (i * hash1) + hash2;

            // 保证不为负数
            if (combineHash < 0) combineHash = ~combineHash;

            // 计算出此次的索引
            int index = combineHash % bitSize;

            // 将二进制向量中，index 位置的值设置为 1
            set(index);
        }
    }

    /** 查看元素是否存在 */
    public boolean contains(V value) {
        valueCheck(value);

        // 获取值相关的哈希
        int hash1 = value.hashCode();
        int hash2 = hash1 >>> 16;

        // 有多少个位哈希，就需要判断多少个位置
        for (int i = 1; i <= hashSize; i++) {
            int combineHash = (i * hash1) + hash2;

            // 不能小于 0
            if (combineHash < 0) combineHash = ~combineHash;

            // 计算出二进制向量的索引
            int index = combineHash % bitSize;

            // 只要有一位是 0 ，那么就说明一定不存在
            if (!get(index)) return false;
        }

        // 来到这里说明存在，但可能会有一定的误判
        return true;
    }

    /** 将二进制向量中，index 位置设置为 1 */
    private void set(int index) {
        // 首先需要找出，index 位置，在long数组中，是第几个元素
        long value = bits[index / Long.SIZE];

        // 然后将这个 value 值的某一位设置为 1
        bits[index / Long.SIZE] = value | (1L << (index % Long.SIZE));
    }

    /** 判断二进制向量中， index 位置是否为 1 */
    private boolean get(int index) {
        // 首先获取 index 在 long数组中的值
        long value = bits[index / Long.SIZE];

        // 这样能够查询出，index 这一位置是否为 1
        return (value & (1L << (index % Long.SIZE))) != 0;
    }

    private void valueCheck(V value) {
        if (value == null) throw new IllegalArgumentException("值不能为null");
    }

    public static void main(String[] args) {

        BloomFilter<Integer> bf = new BloomFilter<>(1_00_000_000, 0.01);
        for (int i = 1; i <= 50; i++) {
            bf.put(i);
        }

        for (int i = 1; i <= 50; i++) {
            if (!bf.contains(i)) {
                // 来到这里面，说明大概率说明不存在
                System.out.println("能判断这些元素可能存在");
            }
        }

        for (int i = 51; i <= 60; i++) {
            if (bf.contains(i)) {
                // 来到这里，说明元素存在了
                System.out.println("能判断这些元素一定不存在");
            }
        }

        for (int i = 1; i <= 1_00_0000; i++) {
            bf.put(i);
        }

        int count = 0;
        for (int i = 1_00_0001; i <= 2_00_0000; i++) {
            if (bf.contains(i)) {
                count++;
            }
        }
        System.out.println(count);

    }
}
