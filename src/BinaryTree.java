import java.util.List;

class BinaryTree {
    Node root;

    BinaryTree(){
        root = new Node(0,0);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node addTreeNode(List<Integer> list, int i, int n)
    {
            Node curNode = new Node();
            if(i < list.size())
            {

                curNode.setIndex(i);
                curNode.setValue(list.get(i));
                if (i * 2 + 2 < n) {
                    curNode.setLeft(addTreeNode(list, i*2+1,n));
                    curNode.setRight(addTreeNode(list, i*2+2,n));
                }
                /*else{
                    curNode.setLeft(new Node(0,0));
                    curNode.setRight(new Node(0,0));
                }*/
            }
            return curNode;

    }
}

class Node{
    int value;
    int index;
    Node left;
    Node right;
    Node(int value, int index){
        this.value = value;
        this.index = index;
        right = null;
        left = null;
    }
    Node(){
        this.value = 0;
        this.index = 0;
        right = null;
        left = null;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getLeft() {
        return left;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node getRight() {
        return right;
    }

    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}