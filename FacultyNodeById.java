public class FacultyNodeById {
    private int key;
    private FacultyNodeById left;
    private FacultyNodeById middle;
    private FacultyNodeById right;
    private FacultyNodeById parent;
    private Boolean is_leaf=true;
    private FacultyWithPlayers facultyWithPlayers;
    private FacultyNodeByScore placeInTree;

    public FacultyNodeById(int key, FacultyNodeById left, FacultyNodeById middle, FacultyWithPlayers facultyWithPlayers) {
        this.key=key;
        this.left=left;
        this.middle=middle;
        this.facultyWithPlayers=facultyWithPlayers;
    }


    public FacultyNodeById(){
        this(0,null,null,new FacultyWithPlayers());

    }

    public void setFacultyWithPlayers(FacultyWithPlayers element){

        for(int i=0;i<element.getPlayers().length;i++){
            this.facultyWithPlayers.getPlayers()[i] = element.getPlayers()[i];
        }
        this.facultyWithPlayers.setFaculty(element.getFaculty());
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


    public FacultyNodeById getLeft() {
        return left;
    }

    public void setLeft(FacultyNodeById left) {
        this.left = left;
    }

    public FacultyNodeById getMiddle() {
        return middle;
    }

    public void setMiddle(FacultyNodeById middle) {
        this.middle = middle;
    }

    public FacultyNodeById getRight() {
        return right;
    }

    public void setRight(FacultyNodeById right) {
        this.right = right;
    }

    public FacultyNodeById getParent() {
        return parent;
    }

    public void setParent(FacultyNodeById parent) {
        this.parent = parent;
    }

    public Boolean getIs_leaf() {
        return is_leaf;
    }

    public void setIs_leaf(Boolean is_leaf) {
        this.is_leaf = is_leaf;
    }

    public void setPlaceInTree(FacultyNodeByScore node){
        this.placeInTree=node;
    }
    public FacultyNodeByScore getPlaceInTree(){
        return this.placeInTree;
    }

}



