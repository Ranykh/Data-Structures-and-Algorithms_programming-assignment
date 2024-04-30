public class PlayerNodeByGoals {
    private Player player;
    private int key;
    private PlayerNodeByGoals left;
    private PlayerNodeByGoals middle;
    private PlayerNodeByGoals right;
    private PlayerNodeByGoals parent;
    private Boolean is_leaf=true;
    private boolean isAgent;
    private PlayerNodeByGoals next;
    private PlayerNodeByGoals previous;



    public PlayerNodeByGoals(int key, PlayerNodeByGoals left, PlayerNodeByGoals middle, Player player) {
        this.key=key;
        this.left=left;
        this.middle = middle;
        is_leaf=true;
        this.player=new Player(0,null);
    }
    public PlayerNodeByGoals(){
        this(0,null,null,null);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }


    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }


    public PlayerNodeByGoals getLeft() {
        return left;
    }

    public void setLeft(PlayerNodeByGoals left) {
        this.left = left;
    }

    public PlayerNodeByGoals getMiddle() {
        return middle;
    }

    public void setMiddle(PlayerNodeByGoals middle) {
        this.middle = middle;
    }

    public PlayerNodeByGoals getRight() {
        return right;
    }

    public void setRight(PlayerNodeByGoals right) {
        this.right = right;
    }

    public PlayerNodeByGoals getParent() {
        return parent;
    }

    public void setParent(PlayerNodeByGoals parent) {
        this.parent = parent;
    }

    public Boolean getIs_leaf() {
        return is_leaf;
    }

    public void setIs_leaf(Boolean is_leaf) {
        this.is_leaf = is_leaf;
    }

    public PlayerNodeByGoals getNext() {
        return next;
    }

    public void setNext(PlayerNodeByGoals next) {
        this.next = next;
    }

    public PlayerNodeByGoals getPrevious() {
        return previous;
    }

    public void setPrevious(PlayerNodeByGoals previous) {
        this.previous = previous;
    }
}








