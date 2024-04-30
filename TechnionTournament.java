import java.util.ArrayList;

public class TechnionTournament implements Tournament{
    FacultiesTreeById FacultiesTreeWithId;
    FacultiesTreeByScore FacultiesTreeWithScores;
    PlayersTreeByGoals PlayersWithScores;
    PlayersTreeById PlayersWithIds;

    TechnionTournament(){
    }

    @Override
    public void init() {
        FacultyNodeById rootFacultyById=new FacultyNodeById();
        FacultyNodeByScore rootFacultyByScore=new FacultyNodeByScore();
        PlayerNodeById rootPlayerById=new PlayerNodeById();
        PlayerNodeByGoals rootPlayerByGoals=new PlayerNodeByGoals();


        this.FacultiesTreeWithId = new FacultiesTreeById(rootFacultyById);
        this.FacultiesTreeWithScores =new FacultiesTreeByScore(rootFacultyByScore);
        this.PlayersWithScores=new PlayersTreeByGoals(rootPlayerByGoals);
        this.PlayersWithIds=new PlayersTreeById(rootPlayerById);


    }

    @Override
    public void addFacultyToTournament(Faculty faculty) {
        FacultyNodeById FacultyIdNode = new FacultyNodeById();
        FacultyNodeByScore FacultyScoreNode = new FacultyNodeByScore();
        FacultyIdNode.getFacultyWithPlayers().setFaculty(faculty);
        FacultyScoreNode.getFacultyWithPlayers().setFaculty(faculty);
        FacultyIdNode.setKey(faculty.getId());
        FacultyIdNode.setPlaceInTree(FacultyScoreNode);
        this.FacultiesTreeWithId.Insert(FacultyIdNode);
        this.FacultiesTreeWithScores.Insert(FacultyScoreNode);
        this.FacultiesTreeWithScores.connect(FacultyScoreNode);
    }

    @Override
    public void removeFacultyFromTournament(int faculty_id) {
        FacultyNodeById root = this.FacultiesTreeWithId.root;
        FacultyNodeById found = this.FacultiesTreeWithId.Search(root,faculty_id);
        FacultyNodeByScore searching = found.getPlaceInTree();
        this.FacultiesTreeWithId.Delete(found);
        this.FacultiesTreeWithScores.disconnect(searching);
        this.FacultiesTreeWithScores.Delete(searching);
    }

    @Override
    public void addPlayerToFaculty(int faculty_id,Player player) {
        PlayerNodeById PlayerIDNode = new PlayerNodeById();
        PlayerNodeByGoals PlayerScoreNode = new PlayerNodeByGoals();
        PlayerIDNode.setPlayer(player);
        PlayerScoreNode.setPlayer(player);
        PlayerIDNode.setKey(player.getId());
        this.PlayersWithIds.Insert(PlayerIDNode);
        this.PlayersWithScores.Insert(PlayerScoreNode);
        this.PlayersWithScores.connect(PlayerScoreNode);
        PlayerIDNode.setPlyerWithScore(PlayerScoreNode);

        FacultyNodeById root=this.FacultiesTreeWithId.root;

        FacultyNodeById rightfaculty=this.FacultiesTreeWithId.Search(root,faculty_id);
        rightfaculty.getFacultyWithPlayers().addPlayer(PlayerScoreNode);
    }

    @Override
    public void removePlayerFromFaculty(int faculty_id, int player_id) {
        FacultyNodeById myRoot=this.FacultiesTreeWithId.root;
        FacultyNodeById myFaculty=this.FacultiesTreeWithId.Search(myRoot,faculty_id);
        PlayerNodeById rootPlayer=this.PlayersWithIds.root;
        PlayerNodeById myPlayer=this.PlayersWithIds.Search(rootPlayer,player_id);
        myFaculty.getFacultyWithPlayers().RemovePlayer(myPlayer.getPlayerWithScore());
        //this.PlayersWithIds.Delete(myPlayer);
       // TODO: WHAT ABOUT FREE AGENT
    }

    public void fillGoals(ArrayList<Integer> goals,FacultyNodeById myfaculty){

        //iterate over all players
        for(int i=0;i<myfaculty.getFacultyWithPlayers().getPlayersNum();i++){
            int numGoals=0;
            //count the num of goals for each player
            for(int goal=0;goal<goals.size();goal++){
                if(myfaculty.getFacultyWithPlayers().getPlayers()[i].getPlayer().getId()==goals.get(goal)){
                    numGoals++;
                }
            }
            //TODO:REMOVE THEN ADD IT BACK TO THE TREE
            //TODO:do set to the pointer to the tree of the player with score another time beacause we removed it
            PlayerNodeByGoals current_player = myfaculty.getFacultyWithPlayers().getPlayers()[i];
            int currentScore=current_player.getKey();
            this.PlayersWithScores.disconnect(current_player);
            this.PlayersWithScores.Delete(current_player);
            current_player.setKey(numGoals+currentScore);
            this.PlayersWithScores.Insert(current_player);
            this.PlayersWithScores.connect(current_player);
            myfaculty.getFacultyWithPlayers().getPlayers()[i]=current_player;
            PlayerNodeById root=this.PlayersWithIds.root;
            PlayerNodeById player=this.PlayersWithIds.Search(root,myfaculty.getFacultyWithPlayers().getPlayers()[i].getPlayer().getId());
            player.setPlyerWithScore(myfaculty.getFacultyWithPlayers().getPlayers()[i]);

        }
    }

    @Override
    public void playGame(int faculty_id1, int faculty_id2, int winner,
                         ArrayList<Integer> faculty1_goals, ArrayList<Integer> faculty2_goals) {
        FacultyNodeById root1 = this.FacultiesTreeWithId.root;
        FacultyNodeById myFaculty1 = this.FacultiesTreeWithId.Search(root1,faculty_id1);

        FacultyNodeById root2 = this.FacultiesTreeWithId.root;
        FacultyNodeById myFaculty2 = this.FacultiesTreeWithId.Search(root2,faculty_id2);
        FacultyNodeByScore facultyWithScore1=myFaculty1.getPlaceInTree();
        FacultyNodeByScore facultyWithScore2=myFaculty2.getPlaceInTree();
        if(winner == 1){
            this.FacultiesTreeWithScores.disconnect(myFaculty1.getPlaceInTree());
            this.FacultiesTreeWithScores.Delete(myFaculty1.getPlaceInTree());
            facultyWithScore1.setKey(facultyWithScore1.getKey() + 3);
            this.FacultiesTreeWithScores.Insert(facultyWithScore1);
            this.FacultiesTreeWithScores.connect(facultyWithScore1);
            myFaculty1.setPlaceInTree(facultyWithScore1);
        }
        if(winner==2){
            this.FacultiesTreeWithScores.disconnect(myFaculty2.getPlaceInTree());
            this.FacultiesTreeWithScores.Delete(myFaculty2.getPlaceInTree());
            facultyWithScore2.setKey(facultyWithScore2.getKey() + 3);
            this.FacultiesTreeWithScores.Insert(facultyWithScore2);
            this.FacultiesTreeWithScores.connect(facultyWithScore2);
            myFaculty2.setPlaceInTree(facultyWithScore2);
        }
        if(winner==0){
            this.FacultiesTreeWithScores.disconnect(myFaculty1.getPlaceInTree());
            this.FacultiesTreeWithScores.Delete(myFaculty1.getPlaceInTree());
            facultyWithScore1.setKey(facultyWithScore1.getKey() + 1);
            this.FacultiesTreeWithScores.Insert(facultyWithScore1);
            this.FacultiesTreeWithScores.connect(facultyWithScore1);
            myFaculty1.setPlaceInTree(facultyWithScore1);

            this.FacultiesTreeWithScores.disconnect(myFaculty2.getPlaceInTree());
            this.FacultiesTreeWithScores.Delete(myFaculty2.getPlaceInTree());
            facultyWithScore2.setKey(facultyWithScore2.getKey() + 1);
            this.FacultiesTreeWithScores.Insert(facultyWithScore2);
            this.FacultiesTreeWithScores.connect(facultyWithScore2);
            myFaculty2.setPlaceInTree(facultyWithScore2);
        }
        fillGoals(faculty1_goals,myFaculty1);
        fillGoals(faculty2_goals,myFaculty2);


    }

    @Override
    public void getTopScorer(Player player) {
        PlayerNodeByGoals node=this.PlayersWithScores.getMaxScore();
        player.setId(node.getPlayer().getId());
        player.setName(node.getPlayer().getName());
    }

    @Override
    public void getTopScorerInFaculty(int faculty_id, Player player) {
        FacultyNodeById root = this.FacultiesTreeWithId.root;
        FacultyNodeById myFaculty = this.FacultiesTreeWithId.Search(root,faculty_id);
        Player myPlayer = myFaculty.getFacultyWithPlayers().getTopScorer();
        player.setId(myPlayer.getId());
        player.setName(myPlayer.getName());
    }

    @Override

    public void getTopKFaculties(ArrayList<Faculty> faculties, int k, boolean ascending) {

        if(ascending){
            int counter=1;
            FacultyNodeByScore current=this.FacultiesTreeWithScores.maximum;
            while(counter<k){
                current=current.getPrevious();
                counter++;
            }
            int counter2=0;
            while(counter2<k){
                faculties.add(current.getFacultyWithPlayers().getFaculty());
                current=current.getNext();
                counter2++;
            }
        }
        else{
            int counter=1;
            FacultyNodeByScore current=this.FacultiesTreeWithScores.maximum;
            faculties.add(current.getFacultyWithPlayers().getFaculty());
            while(counter<k){
                current=current.getPrevious();
                faculties.add(current.getFacultyWithPlayers().getFaculty());
                counter++;
            }
        }
    }

    @Override
    public void getTopKScorers(ArrayList<Player> players, int k, boolean ascending) {

        if(ascending){
            int counter=1;
            PlayerNodeByGoals current=this.PlayersWithScores.maximum;
            while(counter<k){
                current=current.getPrevious();
                counter++;
            }
            int counter2=0;
            while(counter2<k){
                players.add(current.getPlayer());
                current=current.getNext();
                counter2++;
            }

        }
        else{
            int counter=1;
            PlayerNodeByGoals current=this.PlayersWithScores.maximum;
            players.add(current.getPlayer());
            while(counter<k){
                current=current.getPrevious();
                players.add(current.getPlayer());
                counter++;
            }
        }

    }

    @Override
    public void getTheWinner(Faculty faculty) {
        FacultyNodeByScore node=this.FacultiesTreeWithScores.getMaxScore();
        faculty.setId(node.getFacultyWithPlayers().getFaculty().getId());
        faculty.setName(node.getFacultyWithPlayers().getFaculty().getName());
    }

    ///TODO - add below your own variables and methods
}
