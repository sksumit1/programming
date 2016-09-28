package com.sumit.programs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Definition for a binary tree node.
 **/
class TreeNode {
     int val;
     TreeNode left;
     TreeNode right;
     TreeNode(int x) { val = x; }
}

public class PreOrderInorderTreeReconstruct{
	
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder == null || preorder.length == 0 || inorder == null || inorder.length == 0) {
            return null;
        }
        List<Integer> prelist = new ArrayList<Integer>();
        for(int i = 0; i< preorder.length;i++) {
            prelist.add(preorder[i]);
        }
        return findSubTreeRoot(prelist,inorder,0,inorder.length-1);
    }
    
    public TreeNode findSubTreeRoot(List<Integer>preorder, int[] inorder, int startIndex, int endIndex) {
        TreeNode node = null;
        Iterator<Integer> it = preorder.iterator();
        Integer nodeValue = null;
        int index = -1;
        while(it.hasNext()) {
            Integer temp = it.next();
            index = index(temp,inorder,startIndex,endIndex);
            if(index != -1) {
                nodeValue = temp;
                break;
            } else {
                index = -1;
            }
        }
        if(nodeValue != null) {
            node = new TreeNode(nodeValue);
            preorder.remove(nodeValue);
            node.left = findSubTreeRoot(preorder,inorder,startIndex,index-1);
            node.right = findSubTreeRoot(preorder,inorder,index+1,endIndex);
        }
        return node;
    }
    
    private int index(int search,int[] inorder, int startIndex, int endIndex) {
        for(int i = startIndex; i <= endIndex; i++) {
            if(search == inorder[i]) {
                return i;
            }
        }
        return -1;
    }
}
