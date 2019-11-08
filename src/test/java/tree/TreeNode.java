package tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * TODO 哥们，写点东西吧
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/9/11 17:30
 */
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }

    static List<TreeNode> list = new ArrayList<>();
    public static void main(String[] args) {
        TreeNode treeNode1 = new TreeNode(1);
        TreeNode treeNode2 = new TreeNode(2);
        TreeNode treeNode3 = new TreeNode(3);
        TreeNode treeNode4 = new TreeNode(4);
        TreeNode treeNode5 = new TreeNode(5);
        TreeNode treeNode6 = new TreeNode(6);
        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;
        treeNode2.left = treeNode4;
        treeNode2.right = treeNode5;
        treeNode5.right = treeNode6;
        // 前序：根左右
        // 遍历原则，一直遍历左节点，先放左节点，再放右节点
        qianXu(treeNode1);
        print();

        // 中序：左根右
        zhongXu(treeNode1);
        print();
        // 后序：左右根
        houXu(treeNode1);
        print();

        // 还原
        int[] pre = {1,2,4,7,3,5,6,8};
        int[] in = {4,7,2,1,5,3,8,6};
        qianXu(huanYuan(pre,in));
        print();
        zhongXu(huanYuan(pre,in));
        print();
    }
    // 前序:根左右：根在前面
    // 放入当前元素，然后先放左节点，再放右节点
    public static void qianXu(TreeNode treeNode){
        list.add(treeNode);
        if (treeNode.left!=null){
            qianXu(treeNode.left);
        }
        if (treeNode.right!=null){
            qianXu(treeNode.right);
        }
    }
    // 中序：左根右：根在中间
    // 先放入左节点，再放入当前节点，再让如右节点
    public static void zhongXu(TreeNode treeNode){
        if (treeNode.left!=null){
            zhongXu(treeNode.left);// 只要有左节点，就一直遍历
        }
        list.add(treeNode);// 没有左节点，放入根节点
        if (treeNode.right!=null){// 对右节点进行遍历
            zhongXu(treeNode.right);
        }
    }

    // 后序：左右根：根在后面-
    public static void houXu(TreeNode treeNode){
        if (treeNode.left!=null){
            houXu(treeNode.left);// 只要有左节点，就一直遍历
        }
        if (treeNode.right!=null){// 对右节点进行遍历
            houXu(treeNode.right);
        }
        list.add(treeNode);
    }


    public static void print(){
        for (TreeNode t :
                list) {
            System.out.print(t.val+"-");
        }
        System.out.println();
        list.clear();
    }

    public static TreeNode huanYuan(int [] pre,int [] in){
        if (pre==null||pre.length==0){// 如果没有了
            return null;
        }
        TreeNode root = new TreeNode(pre[0]);// 前序第一个就是根节点
        //然后根据根节点 分开中序数组，
        for (int i = 0;i<in.length;i++) {
            if (in[i]==pre[0]){// 如果中序的数值等于前序根节点值，则分开，左边是左半部分，右边是有半部分，进行切割
                //前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}
                // 前序：不包含本身，左开右闭；中序：不包含本身，左闭右开
                root.left = huanYuan(Arrays.copyOfRange(pre,1,i+1),Arrays.copyOfRange(in,0,i));
                // 前序：
                root.right = huanYuan(Arrays.copyOfRange(pre,i+1,pre.length),Arrays.copyOfRange(in,i+1,in.length));
                break;
            }
        }

        return root;//每次返回赋值的根节点
    }


    public TreeNode reConstructBinaryTree(int [] pre,int [] in) {
        if (pre==null||pre.length==0){// 如果没有了
            return null;
        }
        TreeNode root = new TreeNode(pre[0]);// 前序第一个就是根节点
        //然后根据根节点 分开中序数组，
        for (int i = 0;i<in.length;i++) {
            if (in[i]==pre[0]){// 如果中序的数值等于前序根节点值，则分开，左边是左半部分，右边是有半部分，进行切割
                //前序遍历序列{1,2,4,7,3,5,6,8}和中序遍历序列{4,7,2,1,5,3,8,6}
                // 前序：不包含本身，左开右闭；中序：不包含本身，左闭右开
                root.left = reConstructBinaryTree(Arrays.copyOfRange(pre,1,i+1),Arrays.copyOfRange(in,0,i));
                // 前序：
                root.right = reConstructBinaryTree(Arrays.copyOfRange(pre,i+1,pre.length),Arrays.copyOfRange(in,i+1,in.length));
                break;
            }
        }

        return root;//每次返回赋值的根节点
    }

}
