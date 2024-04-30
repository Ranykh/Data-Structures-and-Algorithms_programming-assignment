import java.util.ArrayList;

public class FacultyWithPlayers {
    private Faculty faculty;
    private PlayerNodeByGoals[] players=new PlayerNodeByGoals[11];
    private int PlayersNum;


    public FacultyWithPlayers(Faculty faculty,PlayerNodeByGoals player1,PlayerNodeByGoals player2){
        this.faculty = faculty;
        players[0]=player1;
        players[1]=player2;
        this.PlayersNum=2;
    }
    public FacultyWithPlayers(){

        this(null,null,null);

        this.PlayersNum=0;

    }



    public PlayerNodeByGoals[] getPlayers() {
        return players;
    }

    public void setPlayers(PlayerNodeByGoals[] players) {
        this.players = players;
    }


    public Faculty getFaculty() {
        return faculty;
    }
    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public int getPlayersNum() {
        return PlayersNum;
    }

    public void setPlayersNum(int playersNum) {
        this.PlayersNum = playersNum;
    }

    public void addPlayer(PlayerNodeByGoals player){
        this.PlayersNum = this.PlayersNum + 1;
        players[this.PlayersNum-1]=player;
        this.setPlayersNum(PlayersNum);
    }
    public void RemovePlayer(PlayerNodeByGoals player) {
        int index=0;
        int lastnotnull=0;
        for (int i = 0; i < this.getPlayersNum(); i++) {
            if(this.players[i].getPlayer().getId() == player.getPlayer().getId()) {
                //  this.players[i] = null;
                index=i;
            }
//            if(players[i]==null){
//                lastnotnull=i;
//                break;
//            }
        }
        for (int i = index; i+1 < getPlayersNum(); i++) {
            this.players[i]=players[i+1];
        }
        players[getPlayersNum()-1]=null;
        this.setPlayersNum(getPlayersNum()-1);
    }
    public Player getTopScorer(){
        Player player;

        int maxScore=0;
        for(int i=0;i<PlayersNum;i++){
            if(players[i].getKey()>maxScore){
                maxScore=players[i].getKey();
            }
        }
        int minId;
        Player[] playersWithTopScore=new Player[11];
        int j=0;
        for(int i=0;i<PlayersNum;i++){
            if(players[i].getKey()==maxScore){
                playersWithTopScore[j]=players[i].getPlayer();
                j++;
            }
        }
        minId=playersWithTopScore[0].getId();
        player=playersWithTopScore[0];
        for(int i=0;i<j;i++){
            if(minId>playersWithTopScore[i].getId()){
                minId=playersWithTopScore[i].getId();
                player=playersWithTopScore[i];
            }
        }
       return player;
    }

    public void fillGoals(ArrayList<Integer> goals){

        //iterate over all players
        for(int i=0;i<PlayersNum;i++){
            int numGoals=0;
            //count the num of goals for each player
            for(int goal=0;goal<goals.size();goal++){
                if(players[i].getPlayer().getId()==goals.get(goal)){
                    numGoals++;
                }
            }
            //TODO:REMOVE THEN ADD IT BACK TO THE TREE
            PlayerNodeByGoals current_player = players[i];
            current_player.setKey(numGoals);
        }
    }

}
