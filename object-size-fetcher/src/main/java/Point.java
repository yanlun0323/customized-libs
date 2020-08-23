public class Point {

    /**
     * 数据类型	所占空间（byte）
     * byte　　　　	1
     * short	2
     * int	4
     * long	8
     * float	4
     * double	8
     * char　　	2
     * boolean	1
     * <p>
     * 对象类型	内存布局构成
     * 一般非数组对象	8个字节对象头(mark) + 4/8字节对象指针 + 数据区 + padding内存对齐(按照8的倍数对齐)
     * 数组对象    	8个字节对象头(mark) + 4/8字节对象指针 + 4字节数组长度 + 数据区 + padding内存对齐(按照8的倍数对齐)
     */
    private int x;
    private int y;

    public static void main(String[] args) {
        // 总字节数：2int(4 * 2) + 8字节对象头（mark） + 4字节对象指针 = 8 + 8 + 4 = 20
        // 因为Java在对对象内存分配时都是以8的整数倍来分，因此大于20byte的最接近8的整数倍的是24，因此此对象的大小为24byte
        System.out.println(ObjectSizeFetcher.sizeOf(new Point()));
    }
}