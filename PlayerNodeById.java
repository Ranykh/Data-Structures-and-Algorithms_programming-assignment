public class PlayerNodeById {
    private Player player;
    private int key;
    private PlayerNodeById left;
    private PlayerNodeById middle;
    private PlayerNodeById right;
    private PlayerNodeById parent;
    private Boolean is_leaf=true;
    private boolean isAgent;
    private PlayerNodeByGoals playerWithScore;


    public PlayerNodeById(int key, PlayerNodeById left, PlayerNodeById middle, Player player) {
        this.key=key;
        this.left=left;
        this.middle = middle;
        is_leaf=true;
        this.player=player;
    }
    public PlayerNodeById(){
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


    public PlayerNodeById getLeft() {
        return left;
    }

    public void setLeft(PlayerNodeById left) {
        this.left = left;
    }

    public PlayerNodeById getMiddle() {
        return middle;
    }

    public void setMiddle(PlayerNodeById middle) {
        this.middle = middle;
    }

    public PlayerNodeById getRight() {
        return right;
    }

    public void setRight(PlayerNodeById right) {
        this.right = right;
    }

    public PlayerNodeById getParent() {
        return parent;
    }

    public void setParent(PlayerNodeById parent) {
        this.parent = parent;
    }

    public Boolean getIs_leaf() {
        return is_leaf;
    }

    public void setIs_leaf(Boolean is_leaf) {
        this.is_leaf = is_leaf;
    }

    public void setPlyerWithScore(PlayerNodeByGoals p){
        this.playerWithScore=p;
    }
    public PlayerNodeByGoals getPlayerWithScore(){
        return this.playerWithScore;
    }
}
