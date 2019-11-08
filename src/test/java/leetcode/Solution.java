package leetcode;

import leetcode.support.ListNode;
import leetcode.support.Que;
import org.junit.Test;

import java.util.*;

/**
 * 力扣
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/9/2 11:06
 */
public class Solution {
    public static void main(String[] args) {
        int[] arr = {1,4,3,6,7,8,-1};
        System.out.println();
    }
    // 第一题
    public int[] twoSum(int[] nums, int target) {
        return force(nums,target);
    }
    // 暴力- 时间复杂度n方
    public int[] force(int[] nums, int target) {
        int[] result = new int[2];
        for(int i=0;i< nums.length-1;i++){
            for(int j=i+1;j<nums.length;j++){
                if(nums[i]+nums[j]==target){
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }

    // 时间复杂度，n ： HashMap查询时间复杂度O（1）
    // 2遍hash表，hashMap- 底层二叉树实现--不能有重复值
    public int[] hashSum(int[] nums, int target){
        int[] result = new int[2];
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i],i);// 没有重复值
        }
        for (int i :
                nums) {
            if (map.containsKey(target-i)&&target-i!=i){
                result[0] = map.get(i);
                result[1] = map.get(target-i);
                return result;
            }
        }
        return result;
    }
    // 时间复杂度，n ： HashMap查询时间复杂度O（1）(无hash冲突，有的话，则是logN-N：即红黑树或链表)
    // 1遍hash表，hashMap- 底层二叉树实现--可以有重复值
    public int[] hashSum2(int[] nums, int target){
        int[] result = new int[2];
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[] { map.get(complement), i };
            }
            map.put(nums[i], i);
        }
        return result;
    }

    public int[] hashSearch(int[] nums, int target){
        Map<Integer,Integer> map = new HashMap<>();
        for(int i=0;i< nums.length-1;i++){
            int another = target-nums[i];
            if(map.containsKey(another)){
                return new int[] {i,map.get(another)};
            }
        }
        return new int[]{};
    }

    @Test
    public void addTwoNumbers(){
        ListNode l1 = new ListNode(1);
        l1.next = new ListNode(8);
        //l1.next.next= new ListNode(3);
        ListNode l2 = new ListNode(0);
        /*l2.next = new ListNode(6);
        l2.next.next= new ListNode(4);*/
        System.out.println(addTwoNumbers(l1,l2));

    }

    // 第二题
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
       /* ListNode result = new ListNode(0);
        add(l1,l2,result,0);
        return result;*/
        // 直接使用循环
        ListNode p = l1;ListNode q = l2; ListNode tmp = new ListNode(0);int carry = 0;
        ListNode head = tmp;
        while((p!=null||q!=null)||carry!=0){
            int val1 = p==null?0:p.val;
            int val2 = q==null?0:q.val;
            int sum = val1+val2+carry;
            tmp.val = sum%10;
            carry = sum/10;
            // 终止条件
            p = p==null?null:p.next;
            q = q==null?null:q.next;
            if(p==null&&q==null&&carry==0){
                break;
            }
            tmp.next = new ListNode(0);
            tmp = tmp.next;

        }
        return head;

    }
    // 递归
    public void add(ListNode l1,ListNode l2,ListNode result,int plus){
        // 条件公式
        int val1 = l1==null?0:l1.val;
        int val2 = l2==null?0:l2.val;
        int sum = val1+val2 + plus;
        if(sum>9){// 进位
            result.val = sum-10;
            plus = 1;
        }else{
            result.val = sum;
            plus = 0;
        }
        // 终止条件
        if ((l1==null||l1.next==null)&&(l2==null||l2.next==null)&&plus==0){
            return;
        }
        // 重新赋值
        result.next = new ListNode(0);

        add(l1==null?null:l1.next,l2==null?null:l2.next,result.next,plus);
    }

    // 第三题：最长无重复子字符串长度
    @Test
    public void lengthOfLongestSubstring(){
        String s = "abcabcbb";
        System.out.println(lengthOfLongestSubstring7(s));;
    }
    // 暴力
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();
        int longest = 0;
        // 遍历所有子字符串，如果最长则替换
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j <= n; j++) {
                // 遍历每个字符串 判断是否唯一
                if (unique(s,i,j)){
                    // 对比长度
                    longest = (j-i)>longest?(j-i):longest;
                }
            }
        }
        return longest;
    }

    private boolean unique(String s,int start,int end){
        // 放入set，如果 重复返回false
        Set<Character> set = new HashSet<>();
        for (int i = start; i < end; i++) {
            if (set.contains(s.charAt(i))){
                return false;
            }
            set.add(s.charAt(i));
        }
        return true;
    }


    // 滑窗,如果有则滑动[i,j)
    public int lengthOfLongestSubstring2(String s) {
        int n = s.length();
        int longest = 0;
        Set<Character> set = new LinkedHashSet<>();
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j <=n; j++) {
                if (set.contains(s.charAt(j-1))){
                    set.clear();
                    break;
                }
                set.add(s.charAt(j-1));
                longest = Math.max(longest,set.size());
            }
        }
        return longest;
    }


    // 滑窗,如果有则滑动[i,j)
    public int lengthOfLongestSubstring3(String s) {
        int n = s.length();
        int longest = 0;
        Map<Character,Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j <=n; j++) {
                Character character = s.charAt(j-1);
                if (map.keySet().contains(character)){// 如果包含，则清空 set？
                    i = map.get(character);// 重置为
                    map.clear();
                    break;
                }
                // 如果不包含，则添加该字符
                map.put(s.charAt(j-1),j-1);
                longest = Math.max(longest,map.size());
            }
        }
        return longest;
    }
    public int lengthOfLongestSubstring5(String s) {
        int n = s.length();
        Map<Character,Integer> map = new HashMap<>();
        int ans = 0, i = 0, j = 0;
        while (i < n && j < n) {
            Character character = s.charAt(j);
            if(map.keySet().contains(character)){// 如果有则
                i = Math.max(map.get(character),i);// 因为 所有元素都不删除，所以可能会比当前i小
            }
            map.put(character,j+1);// 增加/替换元素-- +1的原因：为了下面减法：如果不＋1，在下面计算的时候会多一位
            ans = Math.max(ans,j -i+1);// +1的原因：当0的时候，会少一位
            j++;
        }
        return ans;
    }
    //  网上，单独记录左边界
    public int lengthOfLongestSubstring6(String s) {
        int n = s.length();
        int flag = 0;
        int ans = 0, i = 0;
        while (i < n) {
            Character character1 = s.charAt(flag);
            Character character2 = s.charAt(i);
            if (character1.equals(character2)){// 如果相同则移动flag
                flag++;
            }else {

            }
        }
        return ans;
    }

    public int lengthOfLongestSubstring7(String s) {
        int i = 0;
        int flag = 0;// 标记位置
        int length = 0;// 字符串长度
        int result = 0;
        while (i < s.length()) {
            // 标记
            int pos = s.indexOf(s.charAt(i),flag);//从 flag位置开始 s字符串中 当前相同字符的位置
            if (pos < i) {// 如果pos小于i
                if (length > result) {// 如果长度大于result，重新赋值result为length
                    result = length;
                }
                if (result >= s.length() - pos - 1) {// 如果结果大于剩余的长度，返回
                    return result;
                }
                length = i - pos - 1;// 长度 等于 当前i与pos位置之间的距离
                flag = pos + 1;//每次flag+1？
            }
            length++;
            i++;
        }
        return length;
    }

    @Test
    public void testFind(){
        int[][] arr = {{1,2,8,9},{2,4,9,12},{4,7,10,13},{6,8,11,15}};
        // arr[x][y], 将数组按照标准坐标模式进行处理（向右向上为正）--容易理解
        int right = arr.length;
        if (right==0){
            System.out.println(false);
            return;
        }
        int top = arr[0].length;
        System.out.println(find3(5,arr,0,0,right-1,top-1));
    }


    public boolean Find(int target, int [][] array) {
        for (int[] arr:array) {
            if (arr.length==0){continue;}
            if (arr.length==1){
                if (target==arr[0]){
                    return true;
                }
            }
            if (arr[0]<=target&&arr[arr.length-1]>=target){
                for (int value :arr) {
                    if (target==value){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean find1(int target, int [][] array){
        // 二维数组：arr[y][x]
        int y = array.length;
        if (y==0){
            return false;
        }
        int x = array[0].length;
        if (x==0){
            return false;
        }
        for (int i = 0,j=y-1; i < x&&j>=0;) {
            if (array[j][i] == target){
                return true;
            }

            if (array[j][i] > target){// 如果大于，则干掉下面一行
                j--;
            }else {// 如果小于 ，则干掉左边一列
                i++;
            }
        }
        return false;
    }

    // 十字分割法--每次排除1/4，剩下的3/4 分成3块 递归调用
    private boolean find3(int target, int [][] array,int left,int bottom,int right,int top){
        // 二维数组：arr[y][x]
        // 终止条件
        if (left==right&&bottom==top){
            if (array[left][bottom]==target){
                return true;
            }
            return false;
        }
        // 如果超出范围，不用继续进行
        if (target<array[left][bottom]||target>array[right][top]){
            return false;
        }
        // 如果剩余4个以内元素，直接进行查找--因为可能一直查找
        if (right-left<=1&&top-bottom<=1){
            if (array[left][bottom]==target||array[left][top]==target||array[right][bottom]==target||array[right][top]==target){
                return true;
            }
            return false;
        }
        // 取中间数据
        int midX= (left+right)/2;
        int midY = (top+bottom)/2;
        if (array[midX][midY] == target){
            return true;
        }

        if (array[midX][midY] > target){// 如果大于，则干掉右上，对左下查找

            boolean res = find3(target,array,left,bottom,midX,midY);
            if (res){
                return true;
            }
        }else {// 如果小于则干掉左下，对右上进行查找
            boolean res = find3(target,array,midX,midY,right,top);
            if (res){return true;}
        }
        // 查找左上与右下
        if (midX-1>=0){
            boolean res = find3(target,array,left,midY,midX-1,top);
            if (res){return true;}
        }
        if (midY-1>=0){
            boolean res = find3(target,array,midX,bottom,right,midY-1);
            if (res){return true;}
        }
        return false;
    }

    @Test
    public void replaceSpace(){
        System.out.println(replaceSpace(new StringBuffer("hello world")));
    }
    public String replaceSpace(StringBuffer str) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length(); i++) {
            Character character = str.charAt(i);
            if (character==' '){
                sb.append("%20");
            }else {
                sb.append(character);
            }
        }
        return sb.toString();
    }

    @Test
    public void printListFromTailToHead(){
        ListNode listNode = new ListNode(1);
        ListNode listNode1 = new ListNode(2);
        listNode.next = listNode1;

        System.out.println(printListFromTailToHead(listNode));;
    }
    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        ArrayList<Integer>  list = new ArrayList<>();
        // 每次都插到0位置，后面自动向后

        while (listNode!=null){
            list.add(0,listNode.val);
            listNode = listNode.next;
        }

        return list;
    }
    @Test
    public void testQueue(){
        Que que = new Que();
        que.push(1);
        que.push(2);
        que.push(3);
        que.push(4);

        for (int i = 0; i < 8; i++) {
            System.out.println(que.pop());
        }
    }

    @Test
    public void testMinNumberInRotateArray(){
        int[] arr = {6501,6828,6963,7036,7422,7674,8146,8468,8704,8717,9170,9359,9719,9895,9896,9913,9962,154,293,334,492,1323,1479,1539,1727,1870,1943,2383,2392,2996,3282,3812,3903,4465,4605,4665,4772,4828,5142,5437,5448,5668,5706,5725,6300,6335};
        System.out.println(minNumberInRotateArray(arr));
    }
    public int minNumberInRotateArray(int [] array) {
        //小于第一个数 的第一个元素
        if (array.length==0){
            return 0;
        }
        return binary(array,0,array.length-1);
    }

    public int binary(int[] arr,int begin,int end){
        if (end-begin==0){
            return arr[begin];
        }else if (end-begin==1){// 返回较小的
            return arr[begin]<arr[end]?arr[begin]:arr[end];
        }else {
            int low = arr[begin];
            int mid = arr[(begin+end)/2];
            int high = arr[end];
            if (low<mid){// 证明在右边
               return binary(arr,(begin+end)/2,end);
            }else if (low>mid){
                return binary(arr,begin,(begin+end)/2);
            }else {// 中位相同的话，无法判断，跟末尾比较
               if (begin>high){// 如果大于末尾
                   return binary(arr,(begin+end)/2,end);
               }else {// 中位、末位都相同，则完全不能判断，需要缩小范围
                   return binary(arr,begin+1,end);
               }
            }
        }
    }

    //题目描述
    //大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0）。
    //n<=39  0 1 1 2 3 5  -- 递归
    public int Fibonacci(int n) {
        // 终止条件
        if (n ==0){
            return 0;
        }
        if (n ==1){
            return 1;
        }
        return Fibonacci(n-1)+Fibonacci(n-2);
    }
    // 循环
    public int Fibonacci2(int n) {
        int a=0,b=1;

        // 循环
        // 0 1 1 2 3
        while (n>0){// 交换
            a = a+b;// a 递增 c = a+b
            b = a-b;// b = c -b = a
            n--;
        }
        return a;
    }
    @Test
    public void testFibonacci(){
        //System.out.println(Fibonacci(3));
        System.out.println(Fibonacci2(0));
    }

    //题目描述
    //一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）
    // 动态规划，如果只剩最后2级，则只有2种方式，
    public int JumpFloor(int target) {
        if (target==0){
            return 0;
        }
        if (target==1){
            return 1;
        }
        if (target==2){
            return 2;
        }
        return JumpFloor( target-1)+JumpFloor(target-2);
    }



    // 循环
    public int JumpFloorII2(int target) {
        int result = 1;
        while(target>1){
            target --;
            result = target*2;
        }
        return result;
    }

    public int NumberOf1(int n) {
        int count = 0;
        while (n>=1){
            if (n%2==1){
                count++;
            }
            n = n/2;
        }
        return count;
    }

    @Test
    public void testCount(){
        System.out.println(NumberOf1(4));
    }

    //输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
    public void reOrderArray(int [] array) {
        // 增加一个数组进行处理
        int[] arr1 = new int[array.length];
        int index1 = 0;
        int[] arr2 = new int[array.length];
        int index2 = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i]%2==1){// 如果是奇数
                arr1[index1] = array[i];
                index1++;
            }else {
                arr2[index2] = array[i];
                index2++;
            }
        }
        System.arraycopy(arr2,0,arr1,index1,index2);
        //array = arr1;// 重新赋值不行，只能改变元素
    }
    // 只用一个数组
    public void reOrderArray2(int [] array) {
        // 增加一个数组进行处理
        int index1 = 0;
        int[] arr2 = new int[array.length];
        int index2 = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i]%2==1){// 如果是奇数,直接挪动位置
                array[index1] = array[i];
                index1++;
            }else {
                arr2[index2] = array[i];
                index2++;
            }
        }
        // 移动arr2中的偶数赋值

        System.arraycopy(arr2,0,array,index1,index2);
        //array = arr1;// 重新赋值不行，只能改变元素
    }

    //  i j 夹击
    public void reOrderArray3(int [] array) {
        int length = array.length;
        int i= 0;
        while (i<length){// 遍历出第一个 偶数
            int j = i+1;
            if (array[i]%2==0){// 只有遇到偶数才进行处理
               while (array[j]%2==0){
                   if (j==length-1){
                       return;
                   }
                   j++;
               }
               // 此时j为奇数
                int count = j-i;
                int tmp = array[i];
                array[i] = array[j];// 交换奇数位置
                while (count>1){
                    array[i+count] = array[i+count-1];//数组后移
                    count--;
                }
                array[i+1] = tmp;
            }
            // 没有遇到偶数或者，遇到偶数但是处理奇数后，i 向后移一位
            i++;
        }
    }
    @Test
    public void testReOrderArray(){
        int [] array = {1,4,5,6,7,8};
        reOrderArray3(array);
        for (int i :
                array) {
            System.out.print(i+",");
        }
    }

    public ListNode FindKthToTail(ListNode head,int k) {
        ListNode current = head;
        ListNode listNode = null;
        // 差步
        int step = 1;
        while (true){
            if (step==k){
                listNode = head;
            }
            if (step!=k&&listNode!=null){
                // 直接下一个
                listNode = listNode.next;
            }
            step++;
            if (current.next==null){
                break;
            }
            current = current.next;
        }
        return listNode;
    }
    @Test
    public void testFindKthToTail(){
        ListNode head = new ListNode(0);
        head.next = new ListNode(1);
        head.next.next = new ListNode(2);

        System.out.println(FindKthToTail(head,1).val);
    }

}



