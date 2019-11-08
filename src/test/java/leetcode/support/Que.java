package leetcode.support;

import java.util.Stack;

/**
 * TODO 哥们，写点东西吧
 *
 * @auther jeffrey
 * @email 545030930@qq.com
 * @date 2019/9/17 16:46
 */
public class Que{
    // 只使用pop和push
    // 2 个栈完成队列-- 栈是先进先出，队列是先进后出
    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();
    public void push(int node) {//左推
        // stack1和stack2
        while (stack2.size()!=0){// 放的时候只在1里面放
            // 如果不等于0，则全部压倒1里面
            stack1.push(stack2.pop());
        }
        stack1.push(node);
    }

    public int pop() {//取的时候只在2里面取
        // stack1和stack2
        while (stack1.size()!=0){//
            // 如果不等于0，则全部压倒1里面
            stack2.push(stack1.pop());
        }
        return stack2.pop();
    }

}