package leetcode;

import leetcode.support.ListNode;
import leetcode.support.RandomListNode;
import leetcode.support.TreeNode;
import org.junit.Test;

import java.util.*;

/**
 * 算法题
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/9/17 16:45
 */
public class Solution2 {
    // 输入一个链表，反转链表后，输出新链表的表头。
    // 使用栈只能保存 数值，而不是listNode，否则会保存链表后的所有数据！！很大
    public ListNode ReverseList(ListNode head) {
        // 放到栈里面
        Stack<Integer> stack = new Stack<>();
        while (true){
            stack.push(head.val);
            if (head.next!=null){
                head = head.next;
            }else {
                break;
            }
        }
        int newHeadVal = stack.pop();
        ListNode head1 = new ListNode(newHeadVal);
        ListNode node = head1;
        while (!stack.empty()){
            node.next = new ListNode(stack.pop());
            node = node.next;
        }
        return head1;
    }
    // 使用List
    public ListNode ReverseList1(ListNode head) {
        // 放到List里面
        List<Integer> list = new ArrayList<>();
        while (true){
            list.add(head.val);
            head = head.next;
            if (head==null){
                break;
            }
        }
        // 取出来
        ListNode head1 = new ListNode(list.get(list.size()-1));
        ListNode node = head1;
        for (int i = list.size(); i >1; i--) {
            node.next = new ListNode(list.get(i-1));
            node = node.next;
        }
        return head1;
    }

    // 交换位置
    public ListNode ReverseList2(ListNode head) {
        if (head==null||head.next==null){
            return head;
        }
        ListNode pre = null;// 存放倒序后的节点
        ListNode next = null;// 存放当前节点下一个节点
        while (head!=null){
            next = head.next;// 记录当前节点的下一个节点位置：保存原链表后面的元素
            head.next = pre;// 让当前节点指向前一个节点位置，完成反转：将head抽离出来，放到pre前面
            pre = head;// 重新赋值pre为头部的head
            head = next;// 当前节点往右走 ：head赋值为保存的链原表
        }

        return pre;
    }
    @Test
    public void testReverseList(){
        ListNode head = new ListNode(0);
        head.next = new ListNode(1);
        head.next.next = new ListNode(2);
        //head.next.next.next = new ListNode(3);
        ListNode newHead = ReverseList2(head);
        System.out.println(newHead.val);
    }
    //题目描述
    //输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。
    public ListNode Merge(ListNode list1,ListNode list2) {
        if (list1==null){
            return list2;
        }
        if (list2==null){
            return list1;
        }
        ListNode head;
        if(list1.val<list2.val){
            head = list1;
            list1 = list1.next;
        }else {
            head = list2;
            list2 = list2.next;
        }
        ListNode current = head;
        while (true){
            if (list1==null){
                current .next =  list2;
                break;
            }
            if (list2==null){
                current .next =  list1;
                break;
            }
            if (list1.val<list2.val){
                ListNode next = list1.next;
                list1.next = null;
                current.next = list1;
                list1 = next;
            }else {
                ListNode next = list2.next;
                list2.next = null;
                current.next = list2;
                list2 = next;
            }
            current = current.next;
        }
        return head;
    }

    //输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
    // 不是这个意思，比较的是值
    public boolean HasSubtree(TreeNode root1, TreeNode root2){
        if (root1==null||root2==null){
            return false;
        }
        if (root1==root2){
            return true;
        }
        if (root1.left!=null){
            if (HasSubtree(root1.left,root2)){
                return true;
            }
        }
        if (root1.right!=null){
            if (HasSubtree(root1.right,root2)){
                return true;
            }
        }
        return false;
    }

    // 比较值
    // 比较相同根节点的方法
    public boolean desTree(TreeNode tree1, TreeNode tree2){
        if (tree2==null){
            return true;
        }
        if (tree1==null){
            return false;
        }
        if (tree1.val!=tree2.val){
            return false;
        }
        // 比较 左节点和右节点
        return desTree(tree1.left,tree2.left)&&desTree(tree1.right,tree2.right);
    };
    public boolean HasSubtree2(TreeNode root1, TreeNode root2){
        // 找到相同的节点，利用上述方法进行比较
        if (root1==null||root1==null){
            return false;
        }
        // 遍历 root1的节点
        if (desTree(root1,root2)){
            return true;
        }
        if (root1.left!=null){
            if (desTree(root1.left,root2)){
                return true;
            }
        }
        if (root1.right!=null){
            if (desTree(root1.right,root2)){
                return true;
            }
        }
        return false;
    }
    @Test
    public void testHasSubtree(){
        TreeNode root1 = new TreeNode(0);
        TreeNode root2 = new TreeNode(5);
        root1.left = new TreeNode(1);
        root1.right = new TreeNode(2);
        System.out.println(HasSubtree(root1,root2));
    }

    //操作给定的二叉树，将其变换为源二叉树的镜像。-- 递归，将每个
    public void Mirror(TreeNode root) {
        if (root==null){
            return;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;
        root.left = right;
        root.right = left;
        if (left!=null){
            Mirror(left);
        }
        if (right!=null){
            Mirror(right);
        }
    }
    //输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，
    // 例如，如果输入如下4 X 4矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
    // 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
    // 则依次打印出数字1, 2, 3, 4, 8, 12, 16, 15, 14, 13, 9, 5, 6, 7, 11, 10
    // 每4个方向为1组-- 每次打印一圈，然后缩小矩阵--可以将标准坐标系  向右向上为正：（）
    int changeNum =0;
    public ArrayList<Integer> printMatrix(int [][] matrix) {
        ArrayList<Integer> list = new ArrayList<>();
        if (matrix.length==0||matrix[0].length==0){
            return list;
        }
        int bot = 0;
        int top = matrix[0].length-1;
        int lef = 0;
        int rig = matrix.length-1;

        while (bot<=top&&lef<=rig){//
            // 先向上
            for (int i = bot; i <= top; i++) {
                list.add(matrix[lef][i]);
            }
            // 向右
            for (int i = lef+1; i <= rig; i++) {
                list.add(matrix[i][top]);
            }
            // 向下--因为返回，可能一列，需要判断
            if (lef<rig){
                for (int i = top-1; i >= bot; i--) {
                    list.add(matrix[rig][i]);
                }
            }
            // 向左，也可能一行，需要判断;另外，第一个需要排除
            if (bot<top){
                for (int i = rig-1; i >= lef+1 ; i--) {
                    list.add(matrix[i][bot]);
                }
            }
            // 缩放矩形
            bot++;
            top--;
            lef++;
            rig--;
        }
        return list;
    }

    @Test
    public void printMatrix(){
        int [][] ma= {{1,2,3,4},{5,6,7,8},{9,10,11,12},{13,14,15,16}};
        System.out.println(printMatrix(ma));
    }

    //定义栈的数据结构，请在该类型中实现一个能够得到栈中所含最小元素的min函数（时间复杂度应为O（1））。
    // 思路，每次放入的时候都将最小值一起放入,第一个当前值，第二个最小值
    public Stack<String> stack = new Stack<>();
    public void push(int node) {
        if (stack.empty()){
            stack.push(node+"-"+node);
        }else {//对比
            String value = stack.peek();
            int min = Integer.parseInt(value.split("-")[1]);
            if (node<min){// 如果更小
                stack.push(node+"-"+node);
            }else {// 否则原值
                stack.push(node+"-"+min);
            }
        }
    }
    // 每次取出 都取出最小值和当前值
    public void pop() {
        stack.pop();
    }
    // 返回当前值
    public int top() {
        return Integer.parseInt(stack.peek().split("-")[0]);
    }
    // 返回最小值
    public int min() {
        return Integer.parseInt(stack.peek().split("-")[1]);
    }
    //输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否可能为该栈的弹出顺序。假设压入栈的所有数字均不相等。
    // 例如序列1,2,3,4,5是某栈的压入顺序，序列4,5,3,2,1是该压栈序列对应的一个弹出序列，但4,3,5,1,2就不可能是该压栈序列的弹出序列。（注意：这两个序列的长度是相等的）
    public boolean IsPopOrder(int [] pushA,int [] popA) {
        // 思路，直接压栈，然后判断栈取出的第一个元素是否相等，相等则取出，然后继续对比，；不相同则继续放元素，知道最后一个元素，然后看stack是否被取空
        Stack<Integer> stack = new Stack<>();
        int index = 0;
        // 对比第一个和第二个，如果相同就干掉并对比
        for (int i = 0; i < pushA.length; i++) {
            stack.push(pushA[i]);
            while (!stack.empty()&&stack.peek()==popA[index]){
                stack.pop();
                index++;
            }
        }
        return stack.empty();
    }

    @Test
    public void IsPopOrder(){
        int[] arr = {1,2,3,4,5};
        int[] arr2 = {4,3,5,1,2};
        System.out.println(IsPopOrder(arr,arr2));
    }


    //从上往下打印出二叉树的每个节点，同层节点从左至右打印。
    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        ArrayList<Integer> listNodes = new ArrayList<>();
        List<TreeNode> listNodes1 = new ArrayList<>();
        List<TreeNode> listNodes2 = new ArrayList<>();
        listNodes1.add(root);
        // 先打记号？，然后再进行处理？
        while (true){
            if (!listNodes1.isEmpty()){
                for (TreeNode node:listNodes1) {
                    listNodes.add(node.val);
                    if (node.left!=null){
                        listNodes2.add(node.left);
                    }
                    if (node.right!=null){
                        listNodes2.add(node.right);
                    }
                }
                listNodes1.clear();
            }
            if (!listNodes2.isEmpty()){// 遍历后清空
                for (TreeNode node:listNodes2) {
                    listNodes.add(node.val);
                    if (node.left!=null){
                        listNodes1.add(node.left);
                    }
                    if (node.right!=null){
                        listNodes1.add(node.right);
                    }
                }
                listNodes2.clear();
            }
            if (listNodes1.isEmpty()&&listNodes2.isEmpty()){
                break;
            }
        }
        return listNodes;
    }
    // 使用queue，每次都加入同一级的元素，然后取出其子元素放在尾部，就会按顺序处理
    // queue 性质
    // 容量不够或队列为空时不会抛异常：offer（队尾添加元素）、peek（访问队头元素）、poll（访问队头元素并移除）
    // 容量不够或队列为空时抛异常：add（队尾添加元素）、element（访问队列元素）、remove（访问队头元素并移除）
    public ArrayList<Integer> PrintFromTopToBottom2(TreeNode root) {
        ArrayList<Integer> result = new ArrayList<>();
        if (root==null){
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()){// 空值中断
            TreeNode tmp = queue.poll();
            result.add(tmp.val);
            if (tmp.left!=null) queue.offer(tmp.left);
            if (tmp.right!=null) queue.offer(tmp.right);
        }
        return result;
    }
    @Test
    public void testPrintFromTopToBottom(){
        TreeNode root = new TreeNode(0);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(3);
        TreeNode node4 = new TreeNode(4);
        TreeNode node5 = new TreeNode(5);
        root.left = node1;
        root.right = node2;
        node1.left = node3;
        node1.right = node4;
        node2.left = node5;
        System.out.println(PrintFromTopToBottom(root));;
    }

    // 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是则输出Yes,否则输出No。假设输入的数组的任意两个数字都互不相同。
    // 二叉搜索树：例如，左边斗笔右边小：放入一个元素，与根比较，如果较小则放到左子节点，如果较大则放到又子节点
    // 特点，最后一位是根节点，比他小的是左树，比他大的是右树，如果出现不一致则不是搜索树
    // 每个切分之后，必须左边的全比根节点小，右边的全比根节点大
    public boolean VerifySquenceOfBST(int [] sequence) {
        return isRight(sequence,0,sequence.length-1);
    }
    @Test
    public void VerifySquenceOfBST(){
        int[] se = {1,2,4};
        System.out.println( VerifySquenceOfBST(se));;
    }
    public boolean isRight(int [] sequence,int start,int end){
        if (end-start<=1){
            return true;
        }
        // 是否分割
        boolean flag = false;
        for (int i = start ;i <end ; i++) {
            if (flag){// 界限后处理
                if (sequence[i]<sequence[end]){
                    return false;
                }
            }
            if (!flag&&sequence[i]>sequence[end]){// 遇界限
                flag = true;
            }
        }

        return isRight(sequence,start,end-1);
    }

    //输入一颗二叉树的根节点和一个整数，打印出二叉树中结点值的和为输入整数的所有路径。
    // 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。(注意: 在返回值的list中，数组长度大的数组靠前)

    ArrayList<ArrayList<Integer>> result = new ArrayList<>();
    ArrayList<Integer> list = new ArrayList<>();
    // 这个是计算路径中 只要相同就算这个
    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root,int target) {
        int total = 0;// 计算total不如减法计算最终等于0
        if (root!=null){
            list.add(root.val);// 直接添加
            // 计算总数
            for (int i : list) {
                total+=i;
            }
            if (total==target&&root.left==null&&root.right==null){// 如果相同就返回
                // 赋值
                ArrayList<Integer> list1 = new ArrayList<>(list);
                result.add(list1);
            }
            int currentNum = list.size();// 缓存当前节点的位置
            if (root.left!=null){//麻烦，实际是就是下面的删除当前元素
                FindPath(root.left,target);// 对左节点进行处理
                // 删除后加的节点，以便从原位置开始,并恢复原总数
                deleteElements(list,currentNum);
            }
            if (root.right!=null){
                FindPath(root.right,target);
                // 删除后加的节点，以便从原位置开始
                deleteElements(list,currentNum);
            }
        }
        return result;
    }
    // 保留从0-currentNum位置的节点
    private void deleteElements(ArrayList<Integer> list, int currentNum) {
        for (int i = 0; i < list.size(); i++) {
            if (i>=currentNum){
                list.remove(list.get(i));
            }
        }
    }
    // 每次计算完之后就删掉当前元素
    public ArrayList<ArrayList<Integer>> FindPath2(TreeNode root,int target) {
        if(root == null)return result;
        list.add(root.val);
        target -= root.val;// 值传递。原来的target数不会变化
        // 返回的是完整路径！
        if(target == 0 && root.left == null && root.right == null)
            result.add(new ArrayList<Integer>(list));
        FindPath2(root.left, target);
        FindPath2(root.right, target);
        list.remove(list.size()-1);
        return result;
    }
    @Test
    public void testFindPath(){
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(12);
        root.left.left = new TreeNode(7);
        System.out.println(FindPath(root,22));
    }

    // 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针指向任意一个节点），
    // 返回结果为复制后复杂链表的head。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）

    // 本体就是 对该链表进行克隆

    // 思路：
    // 因为每个结点除了指向它下一个结点外，还随机指向一个任意结点。所以我们分为三步来做。

    // 1.复制每个结点，放在原结点的后面，复制后结点的next指向=原结点的指向
    // 2.复制任意指向的结点，例如A指向C，则A'指向C'
    // 3.链表拆分，偶数位的结点是原结点，奇数位的结点是新结点。
    public RandomListNode Clone(RandomListNode pHead){
        RandomListNode currentNode = pHead;
        RandomListNode newHead = null;
        // 1.
        while (currentNode!=null){// 从第二个开始
            // 复制-新建相同节点
            RandomListNode newNode = new RandomListNode(currentNode.label);
            RandomListNode tmp = currentNode.next;
            currentNode.next = newNode;// 将新节点接到原节点后面
            newNode.next = tmp;
            currentNode = tmp;// 继续
        }
        // 2.
        currentNode = pHead;
        while (currentNode!=null){
            currentNode.next.random = currentNode.random==null?null:currentNode.random.next;
            currentNode = currentNode.next.next;
        }
        // 3
        currentNode = pHead;
        newHead = pHead.next;
        while (currentNode!=null){
            if(currentNode.next.next!=null){
                currentNode.next.next = currentNode.next.next.next.next;
            }else {
                break;
            }
            currentNode = currentNode.next.next;
        }
        return newHead;
    }

    //输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向。
    // 就是中序排序

    // 每次返回右节点，指向本节点，然后再输入某节点

    // 不对~~
    public TreeNode Convert(TreeNode pRootOfTree){
        TreeNode rightNode;
        TreeNode left = pRootOfTree.left;
        TreeNode right = pRootOfTree.right;
        if (left==null&&right == null){// 若果左右节点都是null，直接返回本身--左右是什么呢
            rightNode = pRootOfTree;
        }else if (left==null){// 如果左节是null，右节点非null，调整顺序
            rightNode = Convert(right);
            pRootOfTree.right = rightNode;
            rightNode.left = pRootOfTree;
        }else if ((right==null)){
            left.right = pRootOfTree;
            pRootOfTree.left = left;
            rightNode = pRootOfTree;
        }else { // 都非空
            rightNode = Convert(right);
            TreeNode leftNode = Convert(left);
            leftNode.right = pRootOfTree;
            pRootOfTree.left = leftNode;
            pRootOfTree.right = rightNode;
            rightNode.left= pRootOfTree;
        }
        return rightNode;
    }

    // 利用中序排序后重新指针
    public TreeNode Convert1(TreeNode pRootOfTree) {// 每次返回
        List<TreeNode> list = new ArrayList<>();
        Convert1(pRootOfTree,list);
        if (list.size()==0){
            return null;
        }
        TreeNode head = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i).val+"-");
            list.get(i).left=(i-1)==-1?null:list.get(i-1);
            list.get(i).right = (i+1)==list.size()?null:list.get(i+1);
        }
        return head;
    }

    public void Convert1(TreeNode pRootOfTree, List<TreeNode> list) {// 每次返回
        if (pRootOfTree==null) return;
        Convert1(pRootOfTree.left,list);
        list.add(pRootOfTree);// 添加本元素
        Convert1(pRootOfTree.right,list);
    }
    @Test
    public void Convert1(){
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(6);
        root.right = new TreeNode(14);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(8);
        root.right.left = new TreeNode(12);
        root.right.right = new TreeNode(16);
        TreeNode treeNode = Convert(root);
        System.out.println(treeNode);
    }

    // 输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
    // 思路：先排序，然后遍历abcd,abdc,acbd,acdb,,adbc,adcb,bacb...
    // f(a,b,c) = a f(b,c)+ b f(a,c)+ c f(a,b)
    ArrayList<String> stringList = new ArrayList<>();
    StringBuilder sb = new StringBuilder();
    ArrayList<Character>  characterList= new ArrayList<>();
    public void Permutation() {
        if (characterList.size()==0){
            stringList.add(sb.toString());
        }else {
            for (int i = 0; i < characterList.size(); i++) {
                Character s = characterList.remove(i);// 直接取出此值
                sb.append(s);
                Permutation();
                // 放回去
                characterList.add(i,s);
                // 取出来
                sb.deleteCharAt(sb.length()-1);
            }
        }
    }
    @Test
    public void testPermutation(){
        String str = "abdc";
        for (int i = 0; i < str.length(); i++) {
            characterList.add(str.charAt(i));
        }
        Permutation();

        Collections.sort(stringList);
        System.out.println(stringList);
    }

    // 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。
    // 由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。


    //输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        ArrayList<Integer> list= new ArrayList<>(k);
        if (input.length<=k){
            Arrays.sort(input);
            for (int i = 0; i < input.length; i++) {
                list.add(input[i]);
            }
        }else {
            for (int i = 0; i < k; i++) {
                list.add(input[i]);
            }
            Collections.sort(list);
            for (int i = k; i < input.length; i++) {
                if (input[i]<list.get(k-1)){// 如果比最大的小
                    changeElement(list,input[i]);
                }
            }
        }
        return list;
    }

    private void changeElement(ArrayList<Integer> list, int num) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i)>num){
                list.add(i,num);// 添加该元素
                list.remove(list.size()-1);// 挤出末尾元素
                break;
            }
        }
    }

    @Test
    public void testGetLeastNumbers_Solution(){
        int [] arr = {4,5,1,6,2,7,3,8};
        System.out.println(GetLeastNumbers_Solution(arr,4));
    }

    // HZ偶尔会拿些专业问题来忽悠那些非计算机专业的同学。今天测试组开完会后,他又发话了:
    // 在古老的一维模式识别中,常常需要计算连续子向量的最大和,当向量全为正数的时候,问题很好解决。但是,如果向量中包含负数,是否应该包含某个负数,并期望旁边的正数会弥补它呢？
    // 例如:{6,-3,-2,7,-15,1,2,2},连续子向量的最大和为8(从第0个开始,到第3个为止)。给一个数组，返回它的最大连续子序列的和，你会不会被他忽悠住？(子向量的长度至少是1)

    // 暴力法
    public int FindGreatestSumOfSubArray(int[] array) {
        int count = Integer.MIN_VALUE;
        int lastCount = 0;
        if (array==null||array.length==0){
            return count;
        }
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                lastCount += array[j];
                count = lastCount>count?lastCount:count;
            }
            lastCount = 0;
        }
        return count;
    }
    // 这题目应该是最基础的动态规划的题目：最大子数组的和一定是由当前元素和之前最大连续子数组的和叠加在一起形成的，
    // 因此需要遍历n个元素，看看当前元素和其之前的最大连续子数组的和能够创造新的最大值。
    // 个人解释： 创建一个数组，保存历史创建的最大值，然后逐步走位
    public int FindGreatestSumOfSubArray2(int[] array) {
        int len = array.length;
        int[] history = new int[len];
        history[0] = array[0];
        int max = array[0];
        for (int i = 1; i < len; i++) {// 从1开始
            int newMax = history[i-1]+array[i];
            // 走过的最大值与当前值结合，如果能创建更大的值，则替换（历史中存在最大值），否则重新从当前值开始（因为如果还要前面的值，则会更小）
            history[i] = newMax>array[i]?newMax:array[i];
            // 替换最大值
            max = history[i]>max?history[i]:max;
        }
        return max;
    }


    @Test
    public void FindGreatestSumOfSubArray(){
        int[] arr = {1,-2,3,10,-4,7,2,-5};
        System.out.println(FindGreatestSumOfSubArray(arr));
    }

    // 求出1~13的整数中1出现的次数,并算出100~1300的整数中1出现的次数？为此他特别数了一下1~13中包含1的数字有1、10、11、12、13因此共出现6次,但是对于后面问题他就没辙了。
    // ACMer希望你们帮帮他,并把问题更加普遍化,可以很快的求出任意非负整数区间中1出现的次数（从1 到 n 中1出现的次数）。
    // 思路 ：个位数，逢10出现一次，十分位，逢100出现10次，百分数，逢1000出现100次...注意观察，每多一位就增加总数的1/10n个1；但是注意不完整部分--可以使用减法处理
    public int NumberOf1Between1AndN_Solution(int n) {
        // 判断位数
        int wei = 1;//个位、十位。。。
        int total = 0;
        int current;// 当前位之后的值  比如2345，百分位的值 345
        while (true){
            total+= n/(wei*10)*wei;// 每次都是加1/10n；
            // 减去多余部分，如果当前位是0 则减去wei，如果是0 则减去(wei - 后面的数)

            // 取模获取当前位之后的值
            current= n%(10*wei);
            if(current<wei){// 如果是2045，不需要增加
                // 不变化
            }else if( current<2*wei){// 如果是2145，则需要加上 45
                total = total+current-wei;
            }else {// 其他，直接加上 2245 ,加上100
                total+=wei;
            }
            wei *=10;// 每次涨一位
            if (wei*10>n){// 如果超过了就停止。
                break;
            }
        }
        return total;
    }
    @Test
    public void testNumberOf1Between1AndN_Solution(){
        System.out.println(NumberOf1Between1AndN_Solution(13));
    }

    //输入一个正整数数组，把数组里所有数字拼接起来排成一个数，打印能拼接出的所有数字中最小的一个。例如输入数组{3，32，321}，则打印出这三个数字能排成的最小数字为321323。

    // 比较的时候将2个字符串组合，返回小的顺序
    public String PrintMinNumber(int [] numbers) {
        List<String> list = new ArrayList<>();
        for (int i : numbers) {
            list.add(i+"");
        }
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
               if (Long.parseLong(o1+o2)<Long.parseLong(o2+o1)){
                   return -1;
               }
               return 1;
            }
        });
        StringBuilder sb = new StringBuilder();
        for (String s :
                list) {
            sb.append(s);
        }
        return sb.toString();
    }
    @Test
    public void testPrintMinNumber(){
        int[] arr = {321,32,3};
        System.out.println(PrintMinNumber(arr));;
    }


    //把只包含质因子2、3和5的数称作丑数（Ugly Number）。例如6、8都是丑数，但14不是，因为它包含质因子7。 习惯上我们把1当做是第一个丑数。求按从小到大的顺序的第N个丑数。
    // 思路，相当于 2^x3^y5^z，需要保存
    // 将所有丑数放到数组中，然后在 存储 2、3、5对应的数组的脚标，
    // 如果乘2后最小，则2的脚标+1（相当于丑数数组向前移动了一位），
    // 比如从数组 0位开始， 1x2 = 2 < 1x3 =3 < 1 x5=5，则2对应的脚标+1=2，2对应丑数是2
    // 1 x3< 2x2 < 1x5, 所以 3对应脚标+1 =1，3对应丑数也是2
    // 2x2<1x5<2x3 ,所以 2对应脚标+1 =2， 2对应丑数是3
    // 1x5 < 2x3 <4x2 ，所以 5对应脚标+1 = 1 ，对应丑数是2
    // 这样就会把所有最小的数一次遍历到，分别乘2、3、4，就能最终达到目的
    public int GetUglyNumber_Solution(int index) {
        if(index <= 0)return 0;
        int [] res = new int[index];
        int p2 = 0,p3=0,p5=0;
        res[0] = 1;
        for (int i = 1; i < index; i++) {
            res[i] = Math.min(Math.min(res[p2]*2,res[p3]*3),res[p5]*5);
            if (res[i]==res[p2]*2){
                p2++;
            }
            if (res[i]==res[p3]*3){
                p3++;
            }
            if (res[i]==res[p5]*5){
                p5++;
            }
        }
        return res[index-1];
    }
    @Test
    public void testGetUglyNumber_Solution(){
        System.out.println(GetUglyNumber_Solution(8));
    }
    // 在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置, 如果没有则返回 -1（需要区分大小写）.
    //
    public int FirstNotRepeatingChar(String str) {
        Map<Character,Integer> map = new LinkedHashMap<>();// 存储顺序
        for (int i = 0; i < str.length(); i++) {
            if (map.get(str.charAt(i))==null){
                map.put(str.charAt(i),i);
            }else {
                map.put(str.charAt(i),-1);
            }
        }
        for (Map.Entry<Character,Integer> entry:map.entrySet()
             ) {
            if (entry.getValue()!=-1){
                return entry.getValue();
            }
        }
        return -1;
    }

}
