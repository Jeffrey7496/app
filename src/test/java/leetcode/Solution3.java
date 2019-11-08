package leetcode;

import leetcode.support.ListNode;
import leetcode.support.TreeNode;
import org.junit.Test;

import java.util.*;

/**
 * 算法题
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/10/7 21:44
 */
@SuppressWarnings("all")
public class Solution3 {
    // 题目描述
    //在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组,求出这个数组中的逆序对的总数P。并将P对1000000007取模的结果输出。 即输出P%1000000007
    //输入描述:
    //题目保证输入的数组中没有的相同的数字
    //数据范围：
    //	对于%50的数据,size<=10^4
    //	对于%75的数据,size<=10^5
    //	对于%100的数据,size<=2*10^5
    //示例1
    //输入
    //复制
    //1,2,3,4,5,6,7,0
    //输出
    //复制
    //7
    // 利用归并排序 的数组单元数据有序行进行 计算
    int total = 0;
    public int InversePairs(int [] array) {
        mergeSort(array, 0, array.length-1);
        return total;
    }
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
        int[] temp = new int[high-low+1];
        int p1 = low,// 指向前半部分数组元素
                p2 = mid+1,// 指向后半部分数组元素
                k=0;// 指向辅助数组
        while (p1<=mid&&p2<=high){
            if (arr[p1]<arr[p2]){// 如果左边单元小于右边，则不计算
                temp[k++] = arr[p1++];
            }else {
                temp[k++] = arr[p2++];// 如果左边一个元素大于右边某元素，则左边单元当前元素后面的元素都大于右边的这个元素
                total+= (mid-p1+1);
            }
        }
        // 可能一个数组单元遍历完后第二个还没有遍历完，这样的话就全部设置
        while (p1<=mid) temp[k++] = arr[p1++];
        while (p2<=high) temp[k++] = arr[p2++];
        // 临时数组复制回原数组
        for (int i = 0 ; i <= high-low; i++) {
            arr[low+i] = temp[i];
        }
    }
    @Test
    public void testInversePairs(){
        int[] arr = {1,2,3,4,5,6,7,0,2,3,4,5,6,7,0,2,3,4,5,6,7,0,2,3,4,5,6,7,0,2,3,4,5,6,7,0,2,3,4,5,6,7,0,2,3,4,5,6,7,0};
        System.out.println(InversePairs(arr));
        System.out.println("排好序的数组：");
        for (int e : arr)
            System.out.print(e+" ");
    }
    //输入两个链表，找出它们的第一个公共结点。
    // 思路 有公共点后面所有元素相同，比较长度后走几步，然后
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if (pHead1==null||pHead2==null){
            return null;
        }
        ListNode head1 = pHead1;
        ListNode head2 = pHead2;
        int step = 0;
        while (pHead1.next!=null&&pHead2.next!=null){
            pHead1 = pHead1.next;
            pHead2 = pHead2.next;
        }
        String flag;
        if (pHead1.next!=null){
            flag = "1";
        }else {
            flag = "2";
        }
        ListNode node = flag.equals("1")?pHead1:pHead2;
        while (node.next!=null){
            node = node.next;
            step++;
        }
        // 走步
        if (flag.equals("1")){
            while (step>0){
                head1 = head1.next;
                step--;
            }
        }else {
            while (step>0){
                head2 = head2.next;
                step--;
            }
        }
        // 对比
        while (head1!=head2){
            head1 = head1.next;
            head2 = head2.next;
        }
        return head1;
    }
    // 统计一个数字在排序数组中出现的次数
    // 二分查找 找到第一个和最后一个
    public int GetNumberOfK(int [] array , int k) {
        if (array.length==0){
            return -1;
        }
        int last = getLast(array,0,array.length,k);
        int first = getFirst(array,0,array.length,k);
        return last == -1?0:last-first+1;
    }
    public int getFirst(int[] arr, int begin,int end,int target){
        if (begin==end){
            if (arr[begin]==target){
                return begin;
            }
            return -1;
        }
        int mid = begin+(end-begin)/2;
        if (arr[mid]==target){// 找左边
            if (mid==0){
                return 0;
            }else {
                if (arr[mid-1]!=target){
                    return mid;
                }else {
                    return getFirst(arr,begin,mid,target);
                }
            }
        }else if (arr[mid]<target){// 在右边
            if (mid == arr.length-1){
                return -1;
            }
            return getFirst(arr,mid+1,end,target);
        }else {// 在左边
            if (mid == 0){
                return -1;
            }
            return  getFirst(arr,begin,mid-1,target);
        }
    }
    public int getLast(int[] arr, int begin,int end,int target){
        if (begin==end){
            if (arr[begin]==target){
                return begin;
            }
            return -1;
        }
        int mid = begin+(end-begin)/2;
        if (arr[mid]==target){// 找右边
            if (mid==arr.length-1){
                return arr.length-1;
            }else {
                if (arr[mid+1]!=target){
                    return mid;
                }else {
                    return getLast(arr,mid,end,target);
                }
            }
        }else if (arr[mid]<target){// 在右边
            if (mid == arr.length-1){
                return -1;
            }
            return getLast(arr,mid+1,end,target);
        }else {// 在左边
            if (mid == 0){
                return -1;
            }
            return  getLast(arr,begin,mid-1,target);
        }
    }

    @Test
    public void testGetNumberOfK(){
        int[] arr = {1,1,2,4,4,5,5,6,6,7,7,7,7,8};
        System.out.println(GetNumberOfK(arr , 3));
    }

    int current = 0;
    int max = 0;
    public int TreeDepth(TreeNode root) {
        current++;
        max = current>max?current:max;
        if (root.left!=null){
            TreeDepth(root.left);
        }
        if (root.right!=null){
            TreeDepth(root.right);
        }
        current --;
        return max;
    }
    public void TreeDepth(){

    }


    //输入一棵二叉树，判断该二叉树是否是平衡二叉树。-- 平衡二叉树的左右子树也是平衡二叉树，那么所谓平衡就是左右子树的高度差不超过1
    // 此方法思路：从最枝根节点开始计算--最左枝节点，从下往上依次增加1，计算出所有根节点的深度--如果某个根节点左右枝高度差大于1，则不满足
    public int depth(TreeNode root){
        if(root == null)return 0;// 如果当前节点空值则返回1-- 证明其父节点深度为0 --没有该子节点
        int left = depth(root.left);
        if (left==-1) return -1;
        int right = depth(root.right);
        if (right==-1) return -1;
        if (Math.abs(left-right)>1) return -1; // 如果绝对值大于1 则返回-1
        return 1+ (left>right?left:right); // 真正的计算--每次在最大的枝节点增加1
    }
    public boolean IsBalanced_Solution(TreeNode root) {
        return depth(root) != -1;
    }
    @Test
    public void testIsBalanced_Solution(){
        TreeNode node = new TreeNode(0);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        node.left = node1;
        node.right = node2;
        node1.left = node3;
        //node3.left = node4;
        System.out.println(IsBalanced_Solution(node));
    }

    public void FindNumsAppearOnce(int [] array,int num1[] , int num2[]) {
        Set<Integer> set = new HashSet<>();
        for (int i : array) {
            if (set.contains(i)){
                set.remove(i);
            }else {
                set.add(i);
            }
        }
        Integer [] a = new Integer[2];
        set.toArray(a);
        num1[0] = a[0];
        num2[0] = a[1];
    }

    // 小明很喜欢数学,有一天他在做数学作业时,要求计算出9~16的和,他马上就写出了正确答案是100。但是他并不满足于此,他在想究竟有多少种连续的正数序列的和为100(至少包括两个数)。没多久,他就得到另一组连续正数和为100的序列:18,19,20,21,22。现在把问题交给你,你能不能也很快的找出所有和为S的连续正数序列? Good Luck!
    // 思路：找到中间数，2边对称--除数如果是奇数，必须整除；如果是偶数，必须是x.5;-- 余数 是1/2 除数
    // 思路转化： 实际就是 直接除之后，2变找对应的数字
    // 最小连续整数的和 小于被除数 ---1,2,3,4---10
    public ArrayList<ArrayList<Integer>> FindContinuousSequence(int sum) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<>();
        // 先求边界
        int n = 2;
        while (true){
            if ((n*n+n)>2*sum){  // 最小连续整数的和 小于被除数 ---1,2,3,4---10   （1+n）/2 * n<=sum
                break;
            }
            ArrayList<Integer> list = new ArrayList<>();
            int x = sum/n; // 中间数
            if (n%2==1){// 奇数
                if (sum%n==0){// 必须整除
                    for (int i = -n/2; i <= n/2; i++) {
                        list.add(x+i);
                    }
                }
            }else {// 偶数
                if ((sum%n)*2==n){ // 余数必须 是1、2 除数
                    for (int i = -n/2+1; i <= n/2; i++) {
                        list.add(x+i);
                    }
                }
            }
            if (!list.isEmpty()){
                lists.add(0,list);
            }
            n++;
        }

        return lists;
    }

    // 输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S，如果有多对数字的和等于S，输出两个数的乘积最小的。

    // 遍历+ 二分法
    public ArrayList<Integer> FindNumbersWithSum(int [] array,int sum) {
        ArrayList<Integer> list = new ArrayList<>(2);
        int x ;
        for (int i = 0; i < array.length; i++) {
            x = array[i];
            if (array[array.length-1]<x){// 直接没有
                return list;
            }
            int j = bSearch(array,i+1,array.length-1,sum-x);
            if (j!=-1){
                list.add(x);
                list.add(array[j]);
                return list;
            }
        }
        return list;
    }
    private int bSearch(int[] arr,int start,int end,int target){
        if (start>arr.length||start>end){
            return -1;
        }
        int mid =start+(end-start)/2;
        if (arr[mid]==target){
            return mid;
        }else if (arr[mid]>target){
            return bSearch(arr,start,mid,target);
        }else {
            return bSearch(arr,mid+1,end,target);
        }
    }
    @Test
    public void testFindNumbersWithSum(){
        int[] arr = {1,2,3,4,5,6};
        System.out.println(FindNumbersWithSum2(arr,10));
    }

    // 夹逼法-- 2个指针，指向两头，然后一步一步向中间
    public ArrayList<Integer> FindNumbersWithSum2(int [] array,int sum) {
        ArrayList<Integer> list = new ArrayList<>(2);
        int low = 0;
        int high = array.length-1;
        if (high<2){
            return list;
        }
        while ((array[low]+array[high])>sum){
            high--;
        }
        while (high-low>0){
            if (array[high]+array[low]>sum){
                high--;
            }else if (array[high]+array[low]<sum){
                low++;
            }else {
                list.add(array[low]);
                list.add(array[high]);
                break;
            }
        }
        return list;
    }
    // 牛客最近来了一个新员工Fish，每天早晨总是会拿着一本英文杂志，写些句子在本子上。同事Cat对Fish写的内容颇感兴趣，
    // 有一天他向Fish借来翻看，但却读不懂它的意思。例如，“student. a am I”。后来才意识到，这家伙原来把句子单词的顺序翻转了，
    // 正确的句子应该是“I am a student.”。Cat对一一的翻转这些单词顺序可不在行，你能帮助他么？
    // 思路：array反转
    public String ReverseSentence(String str) {
        if (str==null||str.length()==0){
            return str;
        }
        String[] arr = str.split(" ");
        if (arr.length==0){
            return str;
        }
        reverse(arr);
        StringBuilder sb = new StringBuilder();
        for (String s : arr) {
            sb.append(s).append(" ");
        }

        return sb.substring(0,sb.length()-1);
    }

    public void reverse(String [] arr){
        if (arr.length<=1){
            return;
        }
        int low = 0;
        int high = arr.length-1;

        String temp ;
        while (high>low){
            temp = arr[low];
            arr[low] = arr[high];
            arr[high] = temp;
            low++;
            high--;
        }
    }
    @Test
    public void testRe(){
        String s = "I am a student.";
        System.out.println(ReverseSentence(s));
    }

    // 题目描述
    // LL今天心情特别好,因为他去买了一副扑克牌,发现里面居然有2个大王,2个小王(一副牌原本是54张^_^)...他随机从中抽出了5张牌,想测测自己的手气,
    // 看看能不能抽到顺子,如果抽到的话,他决定去买体育彩票,嘿嘿！！“红心A,黑桃3,小王,大王,方片5”,“Oh My God!”不是顺子.....LL不高兴了,他想了想,
    // 决定大\小 王可以看成任何数字,并且A看作1,J为11,Q为12,K为13。上面的5张牌就可以变成“1,2,3,4,5”(大小王分别看作2和4),“So Lucky!”。LL决定去买体育彩票啦。
    // 现在,要求你使用这幅牌模拟上面的过程,然后告诉我们LL的运气如何， 如果牌能组成顺子就输出true，否则就输出false。为了方便起见,你可以认为大小王是0。
    // 解析：除了大小王之外，都不相同
    public boolean isContinuous(int [] numbers) {
        if (numbers.length<5){
            return false;
        }
        // 直接放到set里面-- 排序
        TreeSet<Integer> set = new TreeSet<>();
        for (Integer i :
                numbers) {
            if (i!=0){
                if (set.contains(i)){
                    return false;
                }else {
                    set.add(i);
                }
            }
        }
        // 比较最大和最小
        if (set.last()-set.first()<5){
            return true;
        }else {
            return false;
        }
    }

    // 每年六一儿童节,牛客都会准备一些小礼物去看望孤儿院的小朋友,今年亦是如此。HF作为牛客的资深元老,自然也准备了一些小游戏。
    // 其中,有个游戏是这样的:首先,让小朋友们围成一个大圈。然后,他随机指定一个数m,让编号为0的小朋友开始报数。
    // 每次喊到m-1的那个小朋友要出列唱首歌,然后可以在礼品箱中任意的挑选礼物,并且不再回到圈中,
    // 从他的下一个小朋友开始,继续0...m-1报数....这样下去....直到剩下最后一个小朋友,可以不用表演,并且拿到牛客名贵的“名侦探柯南”典藏版(名额有限哦!!^_^)。
    // 请你试着想下,哪个小朋友会得到这份礼品呢？(注：小朋友的编号是从0到n-1)
    //
    //如果没有小朋友，请返回-1
    // 思路-- 小朋友编号0~n-1，数字编号--m的倍数,标记是否唱歌
    public int LastRemaining_Solution(int n, int m) {
        int[] arr = new int[n];
        int current = 0;// 统计当前喊的数
        int i = 0;// 当前学生编号
        int leave = 0; // 统计离开数量
        while (leave<n-1){
            if (arr[i]==-1){// 已经离开则继续
                i++;
                if (i==n){
                    i=0;
                }
                continue;
            }
            if ((current+1)%m==0){ // 离开
                arr[i] = -1;
                leave++;
                System.out.println("--"+i);
            }
            current++;
            i++;
            if (i==n){
                i=0;
            }
        }
        for (int j = 0; j < n; j++) {
            if (arr[j]!=-1){
                return j;
            }
        }
        return -1;
    }
    @Test
    public void testLastRemaining_Solution(){
        System.out.println(LastRemaining_Solution(5,3));
    }

    // 题目描述
    // 求1+2+3+...+n，要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
    // 思路：(n+n^2)/2
    public int Sum_Solution(int n) {// 设定每次返回的都是上一个 -- 返回条件怎么办？？
        // 放到
        // 1-n
        return Sum_Solution(n-1)+n;
    }
    // 写一个函数，求两个整数之和，要求在函数体内不得使用+、-、*、/四则运算符号。
    public int Add(int num1,int num2) {
        return 0;
    }

    // 题目描述
    //将一个字符串转换成一个整数，要求不能使用字符串转换整数的库函数。 数值为0或者字符串不是一个合法的数值则返回0
    // 通过char进行判断--然后乘以位数
    public int StrToInt(String str) {
        if (str.length()==0){
            return 0;
        }
        // 通过char进行判断--然后乘以位数
        for (int i = 0; i < str.length(); i++) {
            if (i==0&&str.charAt(i)==1){

            }
        }
        return 0;
    }
    public boolean duplicate(int numbers[],int length,int [] duplication) {
        Set<Integer> set = new HashSet<>();
        for (Integer i:numbers){
            if (set.contains(i)){
                duplication[0] = i;
                return true;
            }
            set.add(i);
        }
        set.add(-1);
        return false;
    }
    @Test
    public void testDup(){
        int numbers[] = {};
        int length = numbers.length;
        int [] duplication = new int[1];
        System.out.println(duplicate(numbers,length,duplication));
    }

    // 给定一个数组A[0,1,...,n-1],请构建一个数组B[0,1,...,n-1],其中B中的元素B[i]=A[0]*A[1]*...*A[i-1]*A[i+1]*...*A[n-1]。不能使用除法
    // 思路 将b元素分成2部分，c * d : c[0] = 1;c[1]=a[0];c[2]=a[0]*a[1]...c[i]=a[0]*..*a[i-1]=c[i-1]*a[i-1];  d[n-1]=1;d[n-2]=1*a[n-1]=d[n-1]*a[n-1]
    public int[] multiply(int[] A) {
        int[] c = new int[A.length];
        int[] d = new int[A.length];
        int[] b = new int[A.length];
        setC(0,A,c);
        setD(A.length-1,A,d);
        for (int i = 0; i < A.length; i++) {
            b[i] = c[i]*d[i];
        }
        return b;
    }
    public void setC(int i,int[] A,int []c){
        if (i==A.length){
            return;
        }else {
            if (i==0){
                c[i] = 1;
            }else {
                c[i] =c[i-1]*A[i-1];
            }
            i++;
            setC(i,A,c);
        }
    }
    public void setD(int i,int[] A,int []d){
        if (i==-1){
            return;
        }else {
            if (i==A.length-1){
                d[i] = 1;
            }else {
                d[i] =A[i+1]*d[i+1];
            }
            i--;
            setD(i,A,d);
        }
    }
    @Test
    public void testMultiply(){
        int[] a = {1,2,3,4,5};
        int[] arr = multiply(a);

    }
}
