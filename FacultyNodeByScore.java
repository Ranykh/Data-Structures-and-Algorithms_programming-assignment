public class FacultyNodeByScore {

    private int key;
    private FacultyNodeByScore left;
    private FacultyNodeByScore middle;
    private FacultyNodeByScore right;
    private FacultyNodeByScore parent;
    private Boolean is_leaf=true;
    private FacultyWithPlayers facultyWithPlayers;
    private FacultyNodeByScore next;
    private FacultyNodeByScore previous;

    public FacultyNodeByScore(int key, FacultyNodeByScore left, FacultyNodeByScore middle, FacultyWithPlayers facultyWithPlayers) {
        this.key=key;
        this.left=left;
        this.middle=middle;
        this.facultyWithPlayers=new FacultyWithPlayers();
    }
    public FacultyNodeByScore(){
        this(0,null,null,null);
    }

    public void setFacultyWithPlayers(FacultyWithPlayers element){

        for(int i=0;i<element.getPlayers().length;i++){
            this.facultyWithPlayers.getPlayers()[i] = element.getPlayers()[i];
        }
        this.facultyWithPlayers.setFaculty(element.getFaculty()) ;
    }
    public FacultyWithPlayers getFacultyWithPlayers(){
        return this.facultyWithPlayers;
    }
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }


    public FacultyNodeByScore getLeft() {
        return left;
    }

    public void setLeft(FacultyNodeByScore left) {
        this.left = left;
    }

    public FacultyNodeByScore getMiddle() {
        return middle;
    }

    public void setMiddle(FacultyNodeByScore middle) {
        this.middle = middle;
    }

    public FacultyNodeByScore getRight() {
        return right;
    }

    public void setRight(FacultyNodeByScore right) {
        this.right = right;
    }

    public FacultyNodeByScore getParent() {
        return parent;
    }

    public void setParent(FacultyNodeByScore parent) {
        this.parent = parent;
    }

    public Boolean getIs_leaf() {
        return is_leaf;
    }

    public void setIs_leaf(Boolean is_leaf) {
        this.is_leaf = is_leaf;
    }


    public FacultyNodeByScore getNext() {
        return next;
    }

    public void setNext(FacultyNodeByScore next) {
        this.next = next;
    }

    public FacultyNodeByScore getPrevious() {
        return previous;
    }

    public void setPrevious(FacultyNodeByScore previous) {
        this.previous = previous;
    }
}

