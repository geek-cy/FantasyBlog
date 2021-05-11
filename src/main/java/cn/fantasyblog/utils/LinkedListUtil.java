package cn.fantasyblog.utils;

import cn.fantasyblog.entity.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Cy
 * @Date 2021-04-12 20:10
 */
public class LinkedListUtil {
    public static List<Comment> toCommentLinkedList(List<Comment> rootList, List<Comment> commentList){
        ArrayList<Comment> linkedList = new ArrayList<>();
        for(Comment parent : rootList){
            List<Comment> children = new ArrayList<>();
            findCommentChildren(children,parent,commentList);
            parent.setChildren(children);
            linkedList.add(parent);
        }
        return linkedList;
    }

    private static void findCommentChildren(List<Comment> children,Comment parent,List<Comment> commentList){
        for(Comment child:commentList){
            if(parent.getId().equals(child.getPid())){
                children.add(child);
                findCommentChildren(children, child, commentList);
            }
        }
    }
}
