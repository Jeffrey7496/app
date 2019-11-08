package sort;

import org.junit.Test;

/**
 * 归并排序
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/10/7 21:25
 */
public class MergeSort {
    // 归并排序特点： 递归到最后一个元素（即分开所有元素），然后相邻的2个元素排序，之后合并成一个有序的单元数组，继续2个单元数组归并
    public void mergeSort(int[] arr,int low,int high){
        if (low==high){// 最后一个元素停止
            return;
        }
        int mid = (low+high)/2;
        mergeSort(arr,low,mid);
        mergeSort(arr,mid+1,high);
        merge(arr,low,mid,high);// 合并
    }

    private void merge(int[] arr, int low, int mid, int high) {
        // 辅助数组
        int[] temp = new int[arr.length];
        int p1 = low,// 指向前半部分数组元素
                p2 = mid+1,// 指向后半部分数组元素
                k=low;// 指向辅助数组
        while (p1<=mid&&p2<=high){
            temp[k++] = arr[p1]<arr[p2]?arr[p1++]:arr[p2++];// 循环设置最小值
        }
        // 可能一个数组单元遍历完后第二个还没有遍历完，这样的话就全部设置
        while (p1<=mid) temp[k++] = arr[p1++];
        while (p2<=high) temp[k++] = arr[p2++];
        // 临时数组复制回原数组
        for (int i = low ; i <= high; i++) {
            arr[i] = temp[i];
        }
    }
    @Test
    public  void test() {
        int[] a = { 49, 38, 65, 97, 76, 13, 27, 50 };
        mergeSort(a, 0, a.length-1);
        System.out.println("排好序的数组：");
        for (int e : a)
            System.out.print(e+" ");

    }
}
