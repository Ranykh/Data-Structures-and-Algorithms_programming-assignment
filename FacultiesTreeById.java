public class FacultiesTreeById {
    FacultyNodeById root;


    public FacultiesTreeById(FacultyNodeById root) {
        this.root=root;
        FacultyNodeById left = new FacultyNodeById();
        left.setKey(Integer.MIN_VALUE);
        root.setLeft(left);
        left.setParent(this.root);


        FacultyNodeById middle = new FacultyNodeById();
        middle.setKey(Integer.MAX_VALUE);
        root.setMiddle(middle);
        middle.setParent(this.root);
        root.setIs_leaf(false);

    }



    public void Update_Key(FacultyNodeById Node){
        Node.setKey(Node.getLeft().getKey());
        Node.setFacultyWithPlayers(Node.getLeft().getFacultyWithPlayers());

        if (Node.getMiddle()!= null) {
            Node.setKey(Node.getMiddle().getKey());
            Node.setFacultyWithPlayers(Node.getMiddle().getFacultyWithPlayers());
        }
        if (Node.getRight()!= null) {
            Node.setKey(Node.getRight().getKey());
            Node.setFacultyWithPlayers(Node.getRight().getFacultyWithPlayers());
        }
    }



    public void Set_Children(FacultyNodeById Node, FacultyNodeById left, FacultyNodeById middle, FacultyNodeById right){
        Node.setLeft(left);
        Node.setMiddle(middle);
        Node.setRight(right);
        left.setParent(Node);
        if(middle!=null){
            middle.setParent(Node);
        }
        if(right!=null){
            right.setParent(Node);
        }
        Update_Key(Node);
    }


    public FacultyNodeById InsertAndSplit(FacultyNodeById parent, FacultyNodeById child) {
        FacultyNodeById left = parent.getLeft();
        FacultyNodeById middle = parent.getMiddle();
        FacultyNodeById right = parent.getRight();
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
        FacultyNodeById newNode = new FacultyNodeById();
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

    public void Insert(FacultyNodeById node) {
        if(this.root.getFacultyWithPlayers().getFaculty()==null){
            this.root.getFacultyWithPlayers().setFaculty(node.getFacultyWithPlayers().getFaculty());
            this.root.getLeft().getFacultyWithPlayers().setFaculty(node.getFacultyWithPlayers().getFaculty());
            this.root.getMiddle().getFacultyWithPlayers().setFaculty(node.getFacultyWithPlayers().getFaculty());
        }


        //node.setKey(node.getFacultyWithPlayers().getFaculty().getId());
        //TODO:ADD TO THE LINKED LIST
        FacultyNodeById traverse=this.root;
        //TODO:CHECK WHY THE INFINITY ISNT THERE ANYMORE
        while (!traverse.getIs_leaf()) {
            if (node.getKey() < traverse.getLeft().getKey()) {
                traverse = traverse.getLeft();
            } else if (node.getKey() < traverse.getMiddle().getKey()) {
                traverse = traverse.getMiddle();
            }
            else{
                traverse = traverse.getRight();
            }
        }
        FacultyNodeById x = traverse.getParent();

        x.setIs_leaf(false);
        //TODO:DELETE THE NODE FROM THE LINKED LIST IF ITS EVEN THERE
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
            FacultyNodeById newRoot = new FacultyNodeById();
            newRoot.setIs_leaf(false);
            Set_Children(newRoot, x, node, null);
            this.root = newRoot;
        }
    }

    public FacultyNodeById Borrow_or_Merge(FacultyNodeById node) {
        FacultyNodeById parent = node.getParent();
        if (node == parent.getLeft()) {
            FacultyNodeById middle = parent.getMiddle();
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
            FacultyNodeById left=parent.getLeft();
            if(left.getRight()!=null){
                Set_Children(node,left.getRight(),node.getLeft(),null);
                Set_Children(left,left.getLeft(),left.getMiddle(),null);
            }
            else{
                Set_Children(left,left.getLeft(),left.getMiddle(),node.getLeft());
                parent.setMiddle(null);
                Set_Children(parent,left,parent.getRight(),null);
            }
            return parent;
        }
        else{
            FacultyNodeById middle=parent.getMiddle();
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


    public void Delete(FacultyNodeById node){
        //TODO:SEARCH THE NODE AND DELETE IT
        FacultyNodeById parent = node.getParent();
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

    public FacultyNodeById  Search(FacultyNodeById root,int Id){

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