public class FacultiesTreeByScore {
    FacultyNodeByScore root;
    FacultyNodeByScore maximum;

    public FacultiesTreeByScore(FacultyNodeByScore root) {
        this.root = root;
        FacultyNodeByScore left = new FacultyNodeByScore();
        left.setKey(Integer.MIN_VALUE);
        root.setLeft(left);
        left.setParent(this.root);

        FacultyNodeByScore middle = new FacultyNodeByScore();
        middle.setKey(Integer.MAX_VALUE);
        root.setMiddle(middle);
        middle.setParent(this.root);

        root.setIs_leaf(false);
    }


    public void Update_Key(FacultyNodeByScore Node) {
        Node.setKey(Node.getLeft().getKey());
        Node.setFacultyWithPlayers(Node.getLeft().getFacultyWithPlayers());
        if (Node.getMiddle() != null) {
            Node.setKey(Node.getMiddle().getKey());
            Node.setFacultyWithPlayers(Node.getMiddle().getFacultyWithPlayers());
        }
        if (Node.getRight() != null) {
            Node.setKey(Node.getRight().getKey());
            Node.setFacultyWithPlayers(Node.getRight().getFacultyWithPlayers());
        }
    }


    public boolean TieBreaker(FacultyNodeByScore child1, FacultyNodeByScore child2) {

        if (child1.getFacultyWithPlayers().getFaculty().getId() < child2.getFacultyWithPlayers().getFaculty().getId()) {
            return true;
        }
        return false;
    }

    public void Set_Children(FacultyNodeByScore Node, FacultyNodeByScore left, FacultyNodeByScore middle, FacultyNodeByScore right) {
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


    public FacultyNodeByScore InsertAndSplit(FacultyNodeByScore parent, FacultyNodeByScore child) {
        FacultyNodeByScore left = parent.getLeft();
        FacultyNodeByScore middle = parent.getMiddle();
        FacultyNodeByScore right = parent.getRight();
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
        FacultyNodeByScore newNode = new FacultyNodeByScore();
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

    public void  setMaxScore(FacultyNodeByScore node) {
        if (maximum == null) {
            maximum = node;
        }
        else {

            if (node.getKey() > maximum.getKey()) {
                maximum = node;
            }
            if (node.getKey() == maximum.getKey()) {
                if (TieBreaker(node, maximum))
                    //node id value is smaller
                    maximum = node;
            }
        }

    }

    public FacultyNodeByScore getMaxScore(){
        return this.maximum;
    }

    public void Insert (FacultyNodeByScore node){
        if (this.root.getFacultyWithPlayers().getFaculty() == null) {
            this.root.getFacultyWithPlayers().setFaculty(node.getFacultyWithPlayers().getFaculty());
            this.root.getLeft().getFacultyWithPlayers().setFaculty(node.getFacultyWithPlayers().getFaculty());
            this.root.getMiddle().getFacultyWithPlayers().setFaculty(node.getFacultyWithPlayers().getFaculty());

        }

        setMaxScore(node);
        FacultyNodeByScore traverse = this.root;
        while (!traverse.getIs_leaf()) {
            if (node.getKey() < traverse.getLeft().getKey()) {
                traverse = traverse.getLeft();
            } else if (node.getKey() < traverse.getMiddle().getKey()) {
                traverse = traverse.getMiddle();
            } else {
                traverse = traverse.getRight();
            }
        }
        FacultyNodeByScore x = traverse.getParent();
        x.setIs_leaf(false);
        node= InsertAndSplit(x, node);
        while (x != this.root) {
            x = x.getParent();
            if (node != null) {
                node = InsertAndSplit(x,node);
            } else {
                Update_Key(x);
            }
        }
        if (node != null) {
            FacultyNodeByScore newRoot = new FacultyNodeByScore();
            Set_Children(newRoot, x, node, null);
            this.root = newRoot;
            this.root.setIs_leaf(false);
        }
    }
    public void connect(FacultyNodeByScore node){
        FacultyNodeByScore prevNode = Predecessor(node);
        FacultyNodeByScore nextNode = Successor(node);
        if(prevNode!= null){
            prevNode.setNext(node);
            node.setPrevious(prevNode);
        }
        if(nextNode != null){
            nextNode.setPrevious(node);
            node.setNext(nextNode);
        }

    }

    public FacultyNodeByScore Borrow_or_Merge (FacultyNodeByScore node){
        FacultyNodeByScore parent = node.getParent();
        if (node == parent.getLeft()) {
            FacultyNodeByScore middle = parent.getMiddle();
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
            FacultyNodeByScore left = parent.getLeft();
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
            FacultyNodeByScore middle = parent.getMiddle();
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


    public void Delete (FacultyNodeByScore node){
        FacultyNodeByScore parent = node.getParent();
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

    public FacultyNodeByScore Successor (FacultyNodeByScore leaf){
        FacultyNodeByScore parent = leaf.getParent();
        while ((leaf == parent.getRight()) || (parent.getRight() == null && leaf == parent.getMiddle())) {
            leaf = parent;
            parent = parent.getParent();
        }
        FacultyNodeByScore SuccessorNode=null;
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

    public FacultyNodeByScore Predecessor (FacultyNodeByScore leaf){
        FacultyNodeByScore parent = leaf.getParent();
        while (leaf == parent.getLeft()) {
            leaf = parent;
            parent = parent.getParent();
        }

        FacultyNodeByScore PredecessorNode=null;
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
    public void disconnect(FacultyNodeByScore node) {
        FacultyNodeByScore nextNode = Successor(node);

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
}










