package leetcode;

import leetcode.support.ListNode;
import leetcode.support.TreeLinkNode;
import leetcode.support.TreeNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/10/30 13:52
 */
public class Solution4 {
    // 请实现一个函数用来匹配包括'.'和'*'的正则表达式。模式中的字符'.'表示任意一个字符，而'*'表示它前面的字符可以出现任意次（包含0次）。
    // 在本题中，匹配是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a" "a*a" "ab*ac*a"匹配，但是与"aa.a"和"ab*a"均不匹配
    // char：65 -122
    // 思路：2个指针分别指向字符串和pattern
    // 如果不同，则查看pattern后面是否是*，不是* 则直接false；
    // 如果相同，查看pattern后面，如果不同，则重复上面对比；如果是“.”，则str+1,pattern+1；
    // 如果是*，则查看*后面元素 相同元素的长度，然后查看str该元素的数量，如果小于等于str数量则匹配成功；遇到不同的元素停止
    public boolean match(char[] str, char[] pattern)
    {
        for (int i = 0; i < pattern.length; i++) {
            if (str[i] == pattern[i]){
                continue;
            }

        }
        return true;
    }

    @Test
    public void testMatch(){
        char c = 'A';
        System.out.println("A".charAt(0)=='A');
        System.out.println("z".charAt(0)+0);
    }

    LinkedHashMap<Character,Boolean> map = new LinkedHashMap<>();
    //Insert one char from stringstream
    public void Insert(char ch)
    {
        if (map.containsKey(ch)){
            map.put(ch,false);
        }else {
            map.put(ch,true);
        }
    }
    //return the first appearence once char in current stringstream
    public char FirstAppearingOnce()
    {
        for (Map.Entry<Character, Boolean> entry: map.entrySet()){
            if (entry.getValue()){
                return entry.getKey();
            }
        }
        return '#';
    }

    // 给一个链表，若其中包含环，请找出该链表的环的入口结点，否则，输出null
    // 思路：差步法，一个走2步一个走1步，如果相遇则有环
    // 1：差步伐求有环与否，如果有环则肯定相遇，在环内
    // 2：1个指针不动，另一个指针继续走，相遇则走了一圈，获取环的长度
    // 2：1个指针走过环的长度，然后另一个指针开走，相差正好一圈，再次相遇则实在圈起始位置
    public ListNode EntryNodeOfLoop(ListNode pHead)
    {
        boolean hasCircle = false;
        ListNode l = pHead;
        ListNode r = pHead;
        while (r!=null&&r.next!=null){
            l = l.next;
            r = r.next.next;
            if (l==r){
                hasCircle = true;
                break;
            }
        }
        if (!hasCircle){
            return null;
        }
        int n =1;
        r = r.next;
        while (l!=r){
            r = r.next;
            n++;
        }

        l=pHead;
        r=pHead;
        for (int i = 0; i < n; i++) {
            r = r.next;
        }
        while (l!=r){
            l = l.next;
            r = r.next;
        }
        return l;
    }


    // 题目描述
    //在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5
    // 思路：当前节点，下个节点 不同继续，相同内循环；然后重新相接
    public ListNode deleteDuplication(ListNode pHead) {
        if (pHead == null || pHead.next == null) {
            return pHead;
        }
        // 自荐临时节点
        ListNode head = new ListNode(0);// 暂定第一个
        head.next = pHead;
        // 当前，下一个,下下一个
        ListNode last = head;//
        ListNode current = pHead;
        ListNode next = pHead.next;
        while (current != null) {// 下一个值和下下一个值比较
            if (next != null) {
                boolean circle = false;
                while (current.val == next.val) {
                    circle = true;
                    current = next;
                    next = next.next;
                    if (next==null){// 可能遍历到最后一个空值
                        break;
                    }
                }
                // 循环之后 next就是 最后一个重复值
                // more是下一个值不知道重复不重复

                if (!circle) {// 没有环才连接
                    last.next = current;// 接到后面
                    last = current;
                }else {
                    last.next = null;// 接到后面
                }
                current = next;// 下一个循环
                if (next==null){
                    break;
                }
                next = next.next;
            }else {// 最后一个单独的可能无法处理到
                last.next = current;
                break;
            }
        }

        return head.next;
    }
    @Test
    public void testDeleteDuplication(){
        ListNode pHead = new ListNode(1);
        ListNode node1 = new ListNode(1);
        ListNode node11 = new ListNode(1);
        ListNode node2 = new ListNode(1);
        ListNode node3 = new ListNode(2);
        ListNode node33 = new ListNode(4);
        ListNode node4 = new ListNode(5);
        ListNode node5 = new ListNode(5);

        pHead.next = node1;
        node1.next = node11;
        /*node11.next = node2;
        node2.next = node3;*/
        /*node3.next = node33;
        node33.next = node4;
        node4.next = node5;*/
        ListNode listNode = deleteDuplication(pHead);
        while (listNode!=null){
            System.out.println(listNode.val);
            listNode = listNode.next;
        }
    }
    // 题目描述
    // 给定一个二叉树和其中的一个结点，请找出中序遍历顺序的下一个结点 并且返回。注意，树中的结点不仅包含左右子结点，同时包含指向父结点的指针。
    // 思路：左中右--如果当前节点有右节点，则下个节点一定是右节点的最左子节点
    // 如果当前节点没有右节点：：
        // 如果当前节点是父节点的左节点，则下一节点就是父节点
        // 如果做当前节是父节点的右节点，则下一节点就是父节点的第一个右父节点
    public TreeLinkNode GetNext(TreeLinkNode pNode)// 这只是其中的一个节点，请找出下一个节点
    {
        // 如果右节点非空
        if (pNode.right!=null){
            TreeLinkNode left = pNode.left;
            while (left!=null){
                left = left.left;
            }
            return left;
        }

        // 如果右节点空
        // 如果是左节点
        if (pNode == pNode.next.left){
            return pNode.next;
        }
        // 如果是右节点
        if (pNode.next!=null){
            TreeLinkNode father = pNode.next;
            TreeLinkNode grand = father.next;
            while (grand!=null&&grand.right==father){
                father = grand;
                grand = grand.next;
            }
            return grand;
        }
        return null;
    }
    @Test
    public void testGetNext(){
        TreeLinkNode node0 = new TreeLinkNode(0);
        TreeLinkNode node1 = new TreeLinkNode(1);
        TreeLinkNode node2 = new TreeLinkNode(2);
        TreeLinkNode node3 = new TreeLinkNode(3);
        TreeLinkNode node4 = new TreeLinkNode(4);
        TreeLinkNode node5 = new TreeLinkNode(5);
        TreeLinkNode node6 = new TreeLinkNode(6);

        node0.left = node1;
        node0.right = node2;
        node1.left = node3;
        node1.right = node4;
        node1.next = node0;
        node2.left = node5;
        node2.right = node6;
        node2.next = node0;
    }

    // 题目描述
    //请实现一个函数，用来判断一颗二叉树是不是对称的。注意，如果一个二叉树同此二叉树的镜像是同样的，定义其为对称的。
    // 注意 是根节点对称！！
    // 思路1 左子树：左根右---右子树：右根左，然后对比是否对称
    // 思路2  2条线：
    boolean isSymmetrical(TreeNode pRoot)
    {
        if (pRoot==null){
            return true;
        }
        if(pRoot.left==null&&pRoot.right==null){
            return true;
        }else if(pRoot.left!=null&&pRoot.right!=null) {
            return  isSymmetrical(pRoot.left,pRoot.right);
        }else {
            return false;
        }
    }
    boolean isSymmetrical(TreeNode left,TreeNode right){
        if (left.val!=right.val){
            return false;
        }
        if (left.left==null&&right.right==null&&left.right==null&&right.left==null){// 如果都是空，直接返回true
            return true;
        }else if (left.left==null&&right.right==null&&left.right!=null&&right.left!=null){//如果左右为空，判断中间
            return isSymmetrical(left.right,right.left);
        }else if (left.right==null&&right.left==null&&left.left!=null&&right.right!=null){// 中间空值，判断两边
            return isSymmetrical(left.left,right.right);
        }else if (left.right!=null&&right.left!=null&&left.left!=null&&right.right!=null){// 都不是空值
            boolean b = isSymmetrical(left.right,right.left);
            if (b){
                return isSymmetrical(left.left,right.right);
            }else {
                return false;
            }
        }else {
            return false;
        }
    }
    //题目描述
    //请实现一个函数按照之字形打印二叉树，即第一行按照从左到右的顺序打印，第二层按照从右至左的顺序打印，第三行按照从左到右的顺序打印，其他行以此类推。
    // 思路：每一行都放入list里面，最终放到总list里面
    public ArrayList<ArrayList<Integer>> Print(TreeNode pRoot) {
        if (pRoot!=null){
            ArrayList<TreeNode> nodes = new ArrayList<>(1);
            nodes.add(pRoot);
            print(nodes);
        }
        return result;
    }
    ArrayList<ArrayList<Integer>> result = new ArrayList<>();
    boolean left = true;
    public void print(ArrayList<TreeNode> list) {
        ArrayList<Integer> integerListNode = new ArrayList<>();
        ArrayList<TreeNode> treeNodeArrayList = new ArrayList<>();
        // 左右
        for (int i = 0; i < list.size(); i++) {
            TreeNode node;
            if (left){// 从左开始放入值
                node = list.get(i);
                // 放入值
                integerListNode.add(node.val);
            }else {// 从右
                node = list.get(list.size()-i-1);
                // 放入值
                integerListNode.add(node.val);
            }
            node = list.get(i);// 不能换顺序
            // 每次都从左放入子值，不能换顺序
            if (node.left!=null){
                treeNodeArrayList.add(node.left);
            }
            if (node.right!=null){
                treeNodeArrayList.add(node.right);
            }
        }

        if (!integerListNode.isEmpty()){
            result.add(integerListNode);
        }
        if (!treeNodeArrayList.isEmpty()){
            left = !left;
            print(treeNodeArrayList);
        }
    }

    // 题目描述
    //请实现两个函数，分别用来序列化和反序列化二叉树
    //二叉树的序列化是指：把一棵二叉树按照某种遍历方式的结果以某种格式保存为字符串，从而使得内存中建立起来的二叉树可以持久保存。
    // 序列化可以基于先序、中序、后序、层序的二叉树遍历方式来进行修改，序列化的结果是一个字符串，
    // 序列化时通过 某种符号表示空节点（#），以 ！ 表示一个结点值的结束（value!）。
    //二叉树的反序列化是指：根据某种遍历顺序得到的序列化字符串结果str，重构二叉树。

    // 先序  根左右
    StringBuilder sb = new StringBuilder();
    String Serialize(TreeNode root) {
        Serialize1(root);
        return sb.toString();
    }
    void Serialize1(TreeNode root) {
        if(root!=null){
            sb.append(root.val);
            sb.append("!");// 结束
            Serialize1(root.left);
            Serialize1(root.right);
        }else {
            sb.append("#");// 结束
            sb.append("!");// 结束
        }

    }
    TreeNode Deserialize(String str) {
        String[] nodesVal = str.split("!");
        if (nodesVal[0].equals("#")){
            return null;
        }
        return Deserialize1(nodesVal);
    }
    int index = -1;
    TreeNode Deserialize1(String[] nodesVal) {
        index++;
        if (index==nodesVal.length){
            return null;
        }
        if (!nodesVal[index].equals("#")){// 不是null则对该节点进行设置
            TreeNode treeNode = new TreeNode(Integer.parseInt(nodesVal[index]));
            // 设置本节点的数值后，设置子节点信息
            treeNode.left = Deserialize1(nodesVal);
            treeNode.right = Deserialize1(nodesVal);
            // 设置完这个节点下的所有子节点后返回，供该节点的父节点使用
            return treeNode;
        }else {// 只有null才返回
            return null;
        }
    }
    @Test
    public void testSerialize(){
        TreeNode root  = new TreeNode(0);
        TreeNode root1  = new TreeNode(1);
        TreeNode root2  = new TreeNode(2);
        TreeNode root3  = new TreeNode(3);
        TreeNode root4  = new TreeNode(4);
        TreeNode root5  = new TreeNode(5);
        root.left = root1;
        root.right = root2;
        root1.left = root3;
        root3.right = root4;
        root2.right = root5;
        String s = Serialize(root);
        System.out.println(s);
        TreeNode node = Deserialize(s);
        sb.setLength(0);
        System.out.println(Serialize(node));
    }

    // 给定一棵二叉搜索树，请找出其中的第k小的结点。例如， （5，3，7，2，4，6，8）    中，按结点数值大小顺序第三小结点的值为4。
    // 其实就是排序， 中序遍历-- 左根右
    int index1 = 1;
    TreeNode node = null;
    void KthNode1(TreeNode pRoot, int k)
    {
        if (node!=null){
            return;
        }
        if (pRoot==null){
            return;
        }else {
            KthNode1(pRoot.left,k);
            if(index1 ==k){// 只有经历了才
                node =pRoot;
            }
            index1++;
            KthNode1(pRoot.right,k);
        }
    }
    @Test
    public void testKthNode(){
        TreeNode node0 = new TreeNode(8);
        TreeNode node1 = new TreeNode(6);
        TreeNode node2 = new TreeNode(10);
        TreeNode node3 = new TreeNode(5);
        TreeNode node4 = new TreeNode(7);
        TreeNode node5 = new TreeNode(9);
        TreeNode node6 = new TreeNode(1);
        node0.left = node1;
        node0.right = node2;
        node1.left = node3;
        node1.right = node4;
        node2.left = node5;
        node2.right = node6;
        KthNode1(node0,1);
        System.out.println(node.val);
    }

    // 题目描述
    // 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。如果从数据流中读出偶数个数值，
    // 那么中位数就是所有数值排序之后中间两个数的平均值。我们使用Insert()方法读取数据流，使用GetMedian()方法获取当前读取数据的中位数。
    // 思路 ：插入的时候直接排序？--使用TreeMap/ 或者ArrayList-二分法插入都制定位置
    // 大顶堆、小顶堆--维持2遍个数相同
    @Test
    public void testInsert(){
        Insert(1);
        Insert(5);
        Insert(3);
        Insert(2);
        //Insert(7);
        Insert(0);
        System.out.println(list);
        System.out.println(GetMedian());
    }
    // 使用ArrayList-二分法
    ArrayList<Integer> list = new ArrayList<>();
    public void Insert(Integer num) {
        Insert(list,0,list.size()-1,num);
    }
    public void Insert(ArrayList<Integer> list,int start, int end,int value){
        if (list.isEmpty()){
            list.add(value);
            return;
        }
        int mid = start+(end-start)/2;
        int midVal = list.get(mid);
        int startVal = list.get(start);
        int endVal = list.get(end);
        if (midVal==value){
            list.add(mid,value);
        }else if (midVal>value){// 检查左边
            if(value<startVal){// 直接插入
                list.add(start,value);
            }else if (value>endVal){
                list.add(end+1,value);
            }else {// 二者之间
                Insert(list,start,mid,value);
            }
        }else {
            if (mid+1<list.size()){
                int nextMidVal = list.get(mid+1);
                if (value<nextMidVal){
                    list.add(mid+1,value);
                }else if (value>end){
                    list.add(value);
                }else {
                    Insert(list,mid+1,end,value);
                }
            }else {
                list.add(value);
            }
        }
    }

    public Double GetMedian() {
        int len = list.size();
        if (len==0){
            return 0.0;
        }
        if (len==1){
            return Double.valueOf(list.get(0));
        }
        if (len%2==0){
            return (Double.valueOf(list.get(len/2-1)+list.get(len/2)))/2;
        }else {
            return Double.valueOf(list.get(len/2));
        }
    }

    @Test
    public  void testHeapSort(){
        int [] arr = {1,4,5,7,8,4,5};
        heapSort(arr);
        for (int i :
                arr) {
            System.out.print(i);
        }
    }
    /**
     * 堆排序入口
     * 循环建堆、交换，每次都缩进一个单位
     * @param arr 数据
     */
    public void heapSort(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            buildHeap(arr,arr.length-i-1,arr.length-i-1);
            sortChange(arr,arr.length-i-1);
        }
    }
    /**
     * 堆排序，建堆，从最后一个节点处理；每次处理之后都将最大的值置于顶端
     * @param arr 需要排序的数组
     * @param currentIndex 当前节点--需要跟子节点进行对比
     * @param endIndex 当前数组需要建堆的数组的尾部
     * @return
     */
    public void buildHeap(int[] arr,int currentIndex,int endIndex){
        if (currentIndex==-1){
            return;
        }
        TreeNode root = new TreeNode(arr[currentIndex]);
        int leftIndex = 2*currentIndex+1; //左子节点
        int rightIndex = 2*currentIndex+2;// 右子节点
        int max = currentIndex;
        if (leftIndex<=endIndex){
            if (arr[max]<arr[leftIndex]){
                max = leftIndex;
            }
        }
        if (rightIndex<=endIndex){
            if (arr[max]<arr[rightIndex]){
                max = rightIndex;
            }
        }
        if (currentIndex!=max){
            // 交换
            int temp = arr[currentIndex];
            arr[currentIndex] = arr[max];
            arr[max] = temp;
        }
        // 进行前一个节点
        buildHeap(arr,currentIndex-1,endIndex);
    }

    /**
     * 堆排序：顶部与最后一个交换
     * @param arr
     * @param endIndex
     */
    public void sortChange(int[] arr,int endIndex){
        int temp = arr[0];
        arr[0] = arr[endIndex];
        arr[endIndex] = temp;
    }

    // 给定一个数组和滑动窗口的大小，找出所有滑动窗口里数值的最大值。例如，如果输入数组{2,3,4,2,6,2,5,1}及滑动窗口的大小3，
    // 那么一共存在6个滑动窗口，他们的最大值分别为{4,4,6,6,6,5}； 针对数组{2,3,4,2,6,2,5,1}的滑动窗口有以下6个：
    // {[2,3,4],2,6,2,5,1}， {2,[3,4,2],6,2,5,1}， {2,3,[4,2,6],2,5,1}， {2,3,4,[2,6,2],5,1}， {2,3,4,2,[6,2,5],1}， {2,3,4,2,6,[2,5,1]}。
    // 思路： 维护一个排序的队列，然后每次增加一个，记住角标，比最大值角标小的删除
    ArrayList<Integer> worm = new ArrayList<>();
    int secondIndex = Integer.MIN_VALUE;
    public ArrayList<Integer> maxInWindows(int [] num, int size)
    {
        for (int i = size-1; i < num.length; i++) {
            if (worm.size()==size){// 如果相同，则需要干掉第一个，然后第二大与

            }
        }
        return null;
    }

    // 请设计一个函数，用来判断在一个矩阵中是否存在一条包含某字符串所有字符的路径。路径可以从矩阵中的任意一个格子开始，
    // 每一步可以在矩阵中向左，向右，向上，向下移动一个格子。如果一条路径经过了矩阵中的某一个格子，则该路径不能再进入该格子。
    // 例如 a b c e s f c s a d e e   3 X 4矩阵中包含一条字符串"bcced"的路径，
    // abce
    // sfcs
    // adee
    // 但是矩阵中不包含"abcb"路径，因为字符串的第一个字符b占据了矩阵中的第一行第二个格子之后，路径不能再次进入该格子。
    public boolean hasPath(char[] matrix, int rows, int cols, char[] str)
    {
        return true;
    }
    // 题目描述
    //地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，每一次只能向左，右，上，下四个方向移动一格，
    // 但是不能进入行坐标和列坐标的数位之和大于k的格子。 例如，当k为18时，机器人能够进入方格（35,37），
    // 因为3+5+3+7 = 18。但是，它不能进入方格（35,38），因为3+5+3+8 = 19。请问该机器人能够达到多少个格子？
    // 5: 1-1,2,3,4-10,11,12,13-20,21,22-30,31-40
    // 5: 2-1,2,4-10,11,12-20,21-30
    // 如果完全包含：
    // 第一行：(k-0-1) + (k-1) + (k-2) +...+ (1);
    // 第二行：（k-1-1)+ (k-2) + (1);
    // 实际上







    // 回溯法
    // 例题： 从1，... ，n中取出k个数，要求不重复。
    // n=4, k=2,   1,2 --1,3 --1,4 --2,3-- 2,4 --3,4

    public  List<List<Integer>> result1 = new ArrayList<>();
    public  List<List<Integer>> combine(int n, int k) {
        List<Integer> list = new ArrayList<>();
        backtracking(n, k, 1, list);
        return result1;
    }

    public  void backtracking(int n, int k, int start,List<Integer> list) {
        if (k < 0) {
            return;
        } else if (k == 0) {
            result1.add(new ArrayList<>(list));
        } else {
            // 利用值传递
            for (int i = start; i <= n; i++) {
                list.add(i);                      // 添加元素
                backtracking(n, k - 1, i + 1, list);
                list.remove(list.size() - 1);       // 回退
            }
        }
    }
    @Test
    public void testCombine(){
        combine(4,2);
        System.out.println(result1);
    }
    // 给定一个数组{1，2，3}，得出所有的组合方式[],1,2,3,12,13,23,123。
    // 回溯法：从1 开始到 3，
    // 与上面相比，少了一个限定，所以 只要调用一次就加一个数据
    public ArrayList<ArrayList<Integer>> combine2(int[] nums){
        ArrayList<ArrayList<Integer>> result = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        backTracking2(result,list,0,nums);
        return result;
    }
    public void backTracking2(ArrayList<ArrayList<Integer>> result,ArrayList<Integer> list,int start,int[] nums){
        for (int i = start; i < nums.length; i++) {
            list.add(nums[i]);// 添加元素
            result.add(new ArrayList<>(list));// 直接加入-- 没有限制，直接加入
            backTracking2(result,list,i+1,nums);// 该起始点，进行下步添加
            list.remove(list.size()-1); // 每次添加完成后去添加的元素，方便进行下一轮
        }
    }
    @Test
    public void testCombine2(){
        int [] arr = {1,2,3,4};
        System.out.println(combine2(arr));
    }
    // 给一个字符串,你可以选择在一个字符或两个相邻字符之后拆分字符串,使字符串由仅一个字符或两个字符组成,输出所有可能的结果
    // 样例：
    // 给一个字符串"123"
    // 返回[["1","2","3"],["12","3"],["1","23"]]





    // 给你一根长度为n的绳子，请把绳子剪成m段（m、n都是整数，n>1并且m>1），每段绳子的长度记为k[0],k[1],...,k[m]。
    // 请问k[0]xk[1]x...xk[m]可能的最大乘积是多少？例如，当绳子的长度是8时，我们把它剪成长度分别为2、3、3的三段，此时得到的最大乘积是18。
    // 思路：越平均 越大--计算出
    public int cutRope(int target) {
        return 0;
    }

    private int n;
    private boolean flag = true;
    private Object lock = new Object();
    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            synchronized (lock){
                if (!flag){// 如果是偶数则等待
                    lock.wait();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                flag=!flag;
                lock.notifyAll();
            }
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            synchronized (lock){
                if (flag){// 如果是奇数，则等待
                    lock.wait();
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                flag=!flag;
                lock.notifyAll();
            }
        }
    }
















}
