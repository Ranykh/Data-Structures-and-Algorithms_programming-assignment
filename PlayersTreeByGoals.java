public class PlayersTreeByGoals {
    PlayerNodeByGoals root;
    PlayerNodeByGoals maximum;


    public PlayersTreeByGoals(PlayerNodeByGoals root) {
        this.root=root;
        PlayerNodeByGoals left = new PlayerNodeByGoals();
        left.setKey(Integer.MIN_VALUE);
        root.setLeft(left);
        left.setParent(this.root);

        PlayerNodeByGoals middle = new PlayerNodeByGoals();
        middle.setKey(Integer.MAX_VALUE);
        root.setMiddle(middle);
        middle.setParent(this.root);

        root.setIs_leaf(false);
    }


    public void Update_Key(PlayerNodeByGoals Node) {
        Node.setKey(Node.getLeft().getKey());// deleted an object
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

    public boolean TieBreaker(PlayerNodeByGoals child1, PlayerNodeByGoals child2) {
        if (child1.getPlayer().getId() < child2.getPlayer().getId()) {
            return true;
        }
        return false;
    }

    public void Set_Children(PlayerNodeByGoals Node, PlayerNodeByGoals left, PlayerNodeByGoals middle, PlayerNodeByGoals right) {
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


    public PlayerNodeByGoals InsertAndSplit(PlayerNodeByGoals parent, PlayerNodeByGoals child) {

        PlayerNodeByGoals left = parent.getLeft();
        PlayerNodeByGoals middle = parent.getMiddle();
        PlayerNodeByGoals right = parent.getRight();
        if (right == null) {
            if (child.getKey() <= left.getKey()) {
                if (child.getKey() == left.getKey()) {
                    if (TieBreaker(child, left)) {
                        Set_Children(parent, left, child, middle);
                    } else {
                        Set_Children(parent, child, left, middle);
                    }
                } else {
                    Set_Children(parent, child, left, middle);
                }
            }
            //if they have the same number of goals, then compare there ID's a
            else if (child.getKey() <= middle.getKey()) {

                if (child.getKey() == middle.getKey()) {
                    if (TieBreaker(child, middle)) {
                        Set_Children(parent, left, middle, child);
                    } else {
                        Set_Children(parent, left, child, middle);
                    }
                } else {
                    Set_Children(parent, left, child, middle);
                }
            } else {
                Set_Children(parent, left, middle, child);
            }
            return null;
        }
        PlayerNodeByGoals newNode = new PlayerNodeByGoals();
        newNode.setIs_leaf(false);
        if (child.getKey() <= left.getKey()) {
            if (child.getKey() == left.getKey()) {
                if (TieBreaker(child, left)) {
                    Set_Children(parent, left, child, null);
                } else {
                    Set_Children(parent, child, left, null);
                }
                Set_Children(newNode, middle, right, null);
            } else {
                Set_Children(parent, child, left, null);
                Set_Children(newNode, middle, right, null);
            }
        } else if (child.getKey() <= middle.getKey()) {
            if (child.getKey() == middle.getKey()) {
                if (TieBreaker(child, middle)) {
                    Set_Children(parent, left, middle, null);
                    Set_Children(newNode, child, right, null);
                } else {
                    Set_Children(parent, left, child, null);
                    Set_Children(newNode, middle, right, null);
                }
            } else {
                Set_Children(parent, left, child, null);
                Set_Children(newNode, middle, right, null);
            }
        } else if (child.getKey() <= right.getKey()) {
            Set_Children(parent, left, middle, null);
            if (child.getKey() == right.getKey()) {
                if (TieBreaker(child, right)) {
                    Set_Children(newNode, right,child, null);
                } else {
                    Set_Children(newNode, child,right, null);
                }
            } else {
                Set_Children(newNode, child, right, null);
            }
        } else {
            Set_Children(parent, left, middle, null);
            Set_Children(newNode, right, child, null);
        }
        return newNode;
    }



    public void  setMaxScore(PlayerNodeByGoals node) {
        if(maximum==null){
            maximum=node;
        }
        else{
            if (node.getKey() > maximum.getKey()) {
                maximum = node;
            }
            if (node.getKey() == maximum.getKey()) {
                if (TieBreaker(node, maximum))
                    maximum = node;

            }
        }
    }

    public PlayerNodeByGoals getMaxScore(){
        return this.maximum;
    }


    public void connect(PlayerNodeByGoals node){
        PlayerNodeByGoals prevNode = Predecessor(node);
        PlayerNodeByGoals nextNode = Successor(node);
        if(prevNode!= null){
            prevNode.setNext(node);
            node.setPrevious(prevNode);
        }
        if(nextNode != null){
            nextNode.setPrevious(node);
            node.setNext(nextNode);
        }

    }

    public void Insert(PlayerNodeByGoals node) {
        if(this.root.getPlayer()==null){
            this.root.setPlayer(node.getPlayer());
            this.root.getLeft().setPlayer(node.getPlayer());
            this.root.getMiddle().setPlayer(node.getPlayer());
        }
        setMaxScore(node);
        PlayerNodeByGoals traverse=this.root;
        while (!traverse.getIs_leaf()) {
            if (node.getKey() < traverse.getLeft().getKey()) {
                traverse = traverse.getLeft();
            } else if (node.getKey() < traverse.getMiddle().getKey()) {
                traverse = traverse.getMiddle();
            } else {
                traverse = traverse.getRight();
            }
        }
        //this.root now points to the leaf where we will "try" to make object node, sibling of this.root
        PlayerNodeByGoals x = traverse.getParent(); // x points to the parent where we will try to "insert"
        x.setIs_leaf(false);
        //we know clearly x is not a leaf
        node = InsertAndSplit(x, node);
        // we give node pointer s.t if node==null then we inserted node object as a child of x, else node holds the pointer of new node that we created
        while (x != this.root) { // MAYBE A BUG!!!!!, because this.root points to the child of x, and we are
            x = x.getParent();
            if (node != null) {
                node = InsertAndSplit(x, node);
            } else {
                Update_Key(x);
            }

        }

        if (node != null) {
            PlayerNodeByGoals newRoot = new PlayerNodeByGoals();
            Set_Children(newRoot, x, node, null);
            this.root = newRoot;
            this.root.setIs_leaf(false);
        }
    }

    public PlayerNodeByGoals Borrow_or_Merge(PlayerNodeByGoals node) {
        PlayerNodeByGoals parent = node.getParent();
        if (node == parent.getLeft()) {
            PlayerNodeByGoals middle = parent.getMiddle();
            if (middle.getRight() != null) {
                Set_Children(node, node.getLeft(), middle.getLeft(), null);
                Set_Children(middle, middle.getMiddle(), middle.getRight(), null);
            } else {
                Set_Children(middle, node.getLeft(), middle.getLeft(), middle.getMiddle());
                parent.setLeft(null);
                Set_Children(parent, middle, parent.getRight(), null);
            }
            return parent;
        } else if (node == parent.getMiddle()) {
            PlayerNodeByGoals left = parent.getLeft();
            if (left.getRight() != null) {
                Set_Children(node, left.getRight(), node.getLeft(), null);
                Set_Children(left, left.getMiddle(), left.getRight(), null);
            } else {
                Set_Children(left, left.getLeft(), left.getMiddle(), node.getLeft());
                parent.setMiddle(null);
                Set_Children(parent, left, parent.getRight(), null);
            }
            return parent;
        } else {
            PlayerNodeByGoals middle = parent.getMiddle();
            if (middle.getRight() != null) {
                Set_Children(node, middle.getRight(), node.getLeft(), null);
                Set_Children(middle, middle.getLeft(), middle.getMiddle(), null);
            } else {
                Set_Children(middle, middle.getLeft(), middle.getMiddle(), node.getLeft());
                parent.setRight(null);
                Set_Children(parent, parent.getLeft(), middle, null);
            }

        }
        return parent;
    }


    public void Delete(PlayerNodeByGoals node){
        PlayerNodeByGoals parent = node.getParent();
        if (node == parent.getLeft()) {
            Set_Children(parent, parent.getMiddle(), parent.getRight(), null);
        } else if (node == parent.getMiddle()) {
            Set_Children(parent, parent.getLeft(), parent.getRight(), null);
        } else {
            Set_Children(parent, parent.getLeft(), parent.getMiddle(), null);
        }

        while (parent != null) {
            //CHECK FOR THE OTHER TREES
            if (parent.getMiddle() == null) {
                if (parent != this.root) {
                    parent = Borrow_or_Merge(parent);
                } else {
                    this.root = parent.getLeft();
                    parent.getLeft().setParent(null);
                    return;
                }
            } else {
                Update_Key(parent);
                parent = parent.getParent();
            }

        }

    }


    public void disconnect(PlayerNodeByGoals node) {
        PlayerNodeByGoals nextNode = Successor(node);

        if (nextNode != null) {
            if (node.getPrevious() != null) {
                nextNode.setPrevious(node.getPrevious());
                node.getPrevious().setNext(nextNode);
            }
            else{
                nextNode.setPrevious(null);
            }
        }
        else {
            if (node.getPrevious() != null) {
                node.getPrevious().setNext(null);
            }
        }
    }

    public PlayerNodeByGoals Successor (PlayerNodeByGoals leaf){
        PlayerNodeByGoals parent = leaf.getParent();
        while ((leaf == parent.getRight()) || (parent.getRight() == null && leaf == parent.getMiddle())) {
            leaf = parent;
            parent = parent.getParent();
        }
        PlayerNodeByGoals SuccessorNode=null;
        if (leaf == parent.getLeft()) {
            SuccessorNode = parent.getMiddle();
        } if(leaf==parent.getMiddle()){
            SuccessorNode = parent.getRight();
        }
        if(SuccessorNode!=null){
            while (!SuccessorNode.getIs_leaf()) {
                SuccessorNode = SuccessorNode.getLeft();
            }
            if (SuccessorNode.getKey() < Integer.MAX_VALUE) {
                return SuccessorNode;
            }
        }

        return null;
    }

    public PlayerNodeByGoals Predecessor (PlayerNodeByGoals leaf){
        PlayerNodeByGoals parent = leaf.getParent();
        while (leaf == parent.getLeft()) {
            leaf = parent;
            parent = parent.getParent();
        }

        PlayerNodeByGoals PredecessorNode=null;
        if (leaf == parent.getMiddle()) {
            PredecessorNode = parent.getLeft();
        } if(leaf==parent.getRight()){
            PredecessorNode = parent.getMiddle();
        }
        if(PredecessorNode!=null){
            while (!PredecessorNode.getIs_leaf()) {
                if (PredecessorNode.getRight() != null) {
                    PredecessorNode = PredecessorNode.getRight();
                } else {
                    PredecessorNode = PredecessorNode.getMiddle();
                }
            }
            if (PredecessorNode.getKey() > Integer.MIN_VALUE) {
                return PredecessorNode;
            }
        }
        return null;
    }

//
//    public PlayerNodeByGoals Search(int NumOfGoals){
//        if(this.root.getIs_leaf()){
//            if(this.root.getKey() == NumOfGoals){
//                return this.root;
//            }
//
//        }
//    }








}
