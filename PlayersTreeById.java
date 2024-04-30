public class PlayersTreeById {
    PlayerNodeById root;

    public PlayersTreeById(PlayerNodeById root) {
        this.root=root;
        PlayerNodeById left = new PlayerNodeById();
        left.setKey(Integer.MIN_VALUE);
        root.setLeft(left);
        left.setParent(this.root);

        PlayerNodeById middle = new PlayerNodeById();
        middle.setKey(Integer.MAX_VALUE);
        root.setMiddle(middle);
        middle.setParent(this.root);

        root.setIs_leaf(false);
    }


    public void Update_Key(PlayerNodeById Node) {
        Node.setKey(Node.getLeft().getKey());
        Node.setPlayer(Node.getLeft().getPlayer());
        if (Node.getMiddle() != null) {
            Node.setKey(Node.getMiddle().getKey());
            Node.setPlayer(Node.getMiddle().getPlayer());
        }
        if (Node.getRight() != null) {
            Node.setKey(Node.getRight().getKey());
            Node.setPlayer(Node.getRight().getPlayer());
        }
    }


    public void Set_Children(PlayerNodeById Node, PlayerNodeById left, PlayerNodeById middle, PlayerNodeById right) {
        Node.setLeft(left);
        Node.setMiddle(middle);
        Node.setRight(right);
        left.setParent(Node);
        if (middle != null) {
            middle.setParent(Node);
        }
        if (right != null) {

            right.setParent(Node);
        }
        Update_Key(Node);
    }


    public PlayerNodeById InsertAndSplit(PlayerNodeById parent, PlayerNodeById child) {
        PlayerNodeById left = parent.getLeft();
        PlayerNodeById middle = parent.getMiddle();
        PlayerNodeById right = parent.getRight();
        if (right == null) {
            if (child.getKey() < left.getKey()) {

                Set_Children(parent, child, left, middle);
            }

            //if they have the same number of goals, then compare there ID's a
            else if (child.getKey() < middle.getKey()) {
                Set_Children(parent, left, child, middle);
            } else {
                Set_Children(parent, left, middle, child);
            }
            return null;
        }
        PlayerNodeById newNode = new PlayerNodeById();
        newNode.setIs_leaf(false);
        if (child.getKey() < left.getKey()) {
            Set_Children(parent,child,left,null);
            Set_Children(newNode,middle,right,null);
        }
        else if (child.getKey() < middle.getKey()) {
            Set_Children(parent, left, child, null);
            Set_Children(newNode, middle, right, null);
        }
        else if (child.getKey() < right.getKey()) {
            Set_Children(parent, left, middle, null);
            Set_Children(newNode, child, right, null);
        }
        else {
            Set_Children(parent, left, middle, null);
            Set_Children(newNode, right, child, null);
        }
        return newNode;
    }

    public void Insert(PlayerNodeById node) {

        if(this.root.getPlayer()==null){
            this.root.setPlayer(node.getPlayer());
            this.root.getLeft().setPlayer(node.getPlayer());
            this.root.getMiddle().setPlayer(node.getPlayer());
        }

        PlayerNodeById traverse = this.root;

        while (!traverse.getIs_leaf()) {
            if (node.getKey() < traverse.getLeft().getKey()) {
                traverse = traverse.getLeft();
            } else if (node.getKey() < traverse.getMiddle().getKey()) {
                traverse = traverse.getMiddle();
            } else {
                traverse = traverse.getRight();
            }
        }

        PlayerNodeById x = traverse.getParent();
        x.setIs_leaf(false);
        node = InsertAndSplit(x,node);
        while (x != this.root) {
            x = x.getParent();
            if (node != null) {
                node = InsertAndSplit(x, node);
            } else {
                Update_Key(x);
            }
        }

        if (node != null) {
            PlayerNodeById newRoot = new PlayerNodeById();
            Set_Children(newRoot, x, node, null);
            this.root = newRoot;
            this.root.setIs_leaf(false);
        }
    }

    public PlayerNodeById Borrow_or_Merge(PlayerNodeById node) {
        PlayerNodeById parent = node.getParent();
        if (node == parent.getLeft()) {
            PlayerNodeById middle = parent.getMiddle();
            if (middle.getRight() != null) {
                Set_Children(node, node.getLeft(), middle.getLeft(), null);
                Set_Children(middle, middle.getMiddle(), middle.getRight(), null);
            } else {
                Set_Children(middle,node.getLeft(),middle.getLeft(),middle.getMiddle());
                parent.setLeft(null);
                Set_Children(parent,middle,parent.getRight(),null);
            }
            return parent;
        }
        else if(node==parent.getMiddle()){
            PlayerNodeById left=parent.getLeft();
            if(left.getRight()!=null){
                Set_Children(node,left.getRight(),node.getLeft(),null);
                Set_Children(left,left.getMiddle(),left.getRight(),null);
            }
            else{
                Set_Children(left,left.getLeft(),left.getMiddle(),node.getLeft());
                parent.setMiddle(null);
                Set_Children(parent,left,parent.getRight(),null);
            }
            return parent;
        }
        else{
            PlayerNodeById middle=parent.getMiddle();
            if(middle.getRight()!=null) {
                Set_Children(node, middle.getRight(), node.getLeft(), null);
                Set_Children(middle, middle.getLeft(), middle.getMiddle(), null);
            }
            else{
                Set_Children(middle, middle.getLeft(), middle.getMiddle(), node.getLeft());
                parent.setRight(null);
                Set_Children(parent, parent.getLeft(), middle, null);
            }

        }
        return parent;
    }


    public void Delete(PlayerNodeById node){
        PlayerNodeById parent = node.getParent();
        if(node == parent.getLeft()){
            Set_Children(parent, parent.getMiddle(), parent.getRight(), null);
        }
        else if (node == parent.getMiddle()){
            Set_Children(parent, parent.getLeft(), parent.getRight(), null);
        }
        else {
            Set_Children(parent, parent.getLeft(),parent.getMiddle(), null);
        }
        while(parent != null){
            if(parent.getMiddle() == null){
                if(parent != this.root){
                    parent = Borrow_or_Merge(parent);
                }
                else{
                    this.root = parent.getLeft();
                    parent.getLeft().setParent(null);
                    return;
                }
            }
            else {
                Update_Key(parent);
                parent = parent.getParent();
            }

        }
    }
    public PlayerNodeById  Search(PlayerNodeById  root,int Id){

        if(root.getIs_leaf()){
            if(root.getKey() == Id){
                return root;
            }
            else {
                return null;
            }
        }
        if(Id <= root.getLeft().getKey()){
            return Search(root.getLeft(),Id);
        }
        else if ( Id <= root.getMiddle().getKey()){
            return Search(root.getMiddle(), Id);
        }
        return Search(root.getRight(),Id);
    }

}


