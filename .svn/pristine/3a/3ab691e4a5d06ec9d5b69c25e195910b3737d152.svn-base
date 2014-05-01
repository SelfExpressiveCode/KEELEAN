package com.sec.framework.component.tree;

import java.util.List;

public class TreeNode {

    public TreeNode parent;

    public boolean visible;

    public int level;       //TODO Not changeable during runtime.

    public List<TreeNode> children() {
         return null;
    }

    public void expand() {
        //TODO hide something.
        List<TreeNode> nodes = children();
        for (TreeNode node : nodes) {
             node.expand();
        }
    }

    public void collapse() {

    }
}
