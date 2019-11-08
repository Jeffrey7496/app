package leetcode.support;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO 哥们，写点东西吧
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/9/17 16:46
 */
public class ListNode {
    public int val;
    public ListNode next;
    public ListNode(int x) { val = x; }

    @Override
    public String toString() {
        // 递归
        List<Integer> list = new ArrayList<>();
        add(list,this);
        return list.toString();
    }

    private void add(List list,ListNode listNode){
        list.add(listNode.val);
        if (listNode.next!=null){
            add(list,listNode.next);
        }
    }
}