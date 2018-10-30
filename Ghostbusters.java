import java.util.Random;
import java.util.Scanner;
public class Ghostbusters extends GameBoard
{
    public static void main(String[] args)
    {
        GameBoard game = new Ghostbusters();
        game.initializeGame();
    }

    public void setupGame(){
        GameBoard game =  GameBoard.getGame();
        int numRows= game.getNumRows();
        int numCols= game.getNumCols();
        Random generator = new Random();

        int ghostBusterRow = generator.nextInt(numRows);
        int ghostBusterCol = generator.nextInt(numCols);
        int slimerRow = generator.nextInt(numRows);
        int slimerCol = generator.nextInt(numCols);
        int portalCol=generator.nextInt(numCols);
        int portalRow=generator.nextInt(numRows);
        int ghostTrapRow = generator.nextInt(numRows);
        int ghostTrapCol = generator.nextInt(numCols);
        
        int MAX_PTLS=2;

        Room roomForGhostBuster= game.getRoom( ghostBusterRow, ghostBusterCol );
        roomForGhostBuster.showPlayerEnters();

        if (ghostBusterRow==slimerRow && ghostBusterCol==slimerCol) {
            slimerRow = generator.nextInt(numRows);
            slimerCol = generator.nextInt(numCols);
            // remove this after testing!!

        }
        Room roomForSlimer = game.getRoom( slimerRow, slimerCol );
        roomForSlimer.addSlimer();
        roomForSlimer.showSlimer();
        boolean positionFound=false;
        Room roomForPortal = game.getRoom(portalRow,portalCol);
        for(int i=0;i<2;i++){
        
        while (positionFound==false){
            if (roomForPortal.containsSlimer()||roomForPortal.containsGhostbuster()
            ||roomForPortal.containsPortal()){ 
                portalCol=generator.nextInt(numCols);
                portalRow=generator.nextInt(numRows);
            } else{
                positionFound =true;
                roomForPortal.addPortal();
            } 
            
        }
        roomForPortal.addPortal();
        roomForPortal.showPortal();
      }
        
        

      Room roomForGhostTrap = game.getRoom(ghostTrapRow,ghostTrapCol);
      boolean goodPosition = false;
       for(int i=0;i<=4;i++){
        while (goodPosition==false){
         if (roomForGhostTrap.containsSlimer() || roomForGhostTrap.containsPortal()||
        roomForGhostTrap.containsGhostbuster()||roomForGhostTrap.containsGhostTrap()){
            ghostTrapRow = generator.nextInt(numRows);
            ghostTrapCol = generator.nextInt(numCols);
            
            }else {
             goodPosition=true;
             roomForGhostTrap.addGhostTrap();
             roomForGhostTrap.showGhostTrap();
            }
        }
        roomForGhostTrap.addGhostTrap();
        roomForGhostTrap.showGhostTrap();
      }
    }
    public boolean handleMove(String direction ){
        GameBoard game =  GameBoard.getGame();
        int playerCol;
        int playerRow;
        
        playerCol= game.getPlayerCol();
        playerRow= game.getPlayerRow();
        Room roomForPlayer= game.getRoom(playerRow,playerCol);
        roomForPlayer.showPlayerExits();
        int maxRows= game.getNumRows();
        int maxCols= game.getNumCols();
        if (direction.equals("UP")){
            playerRow--;
            playerRow = (playerRow + maxRows) % maxRows;
            game.setPlayerRow(playerRow);
        } 
        else if (direction.equals ("DOWN"))
        {
            playerRow++;
            playerRow = (playerRow + maxRows) % maxRows;
            game.setPlayerRow(playerRow);
        }
        else if (direction.equals ("LEFT"))
        {
            playerCol++;
            playerCol = (playerCol + maxCols) % maxCols;
            game.setPlayerCol(playerCol);
        }
        else if (direction.equals("RIGHT")) 
        {
            playerCol--;
            playerCol = (playerCol + maxCols) % maxCols;
            game.setPlayerCol(playerCol);
        }
        game.checkForGhostTrap();
        game.checkForLoss();
        return true;
    }  

    public void handleFire(String direction){
        GameBoard game =  GameBoard.getGame();
        game.showAllRooms();
        int playerCol;
        int playerRow;
        
        playerCol= game.getPlayerCol();
        playerRow= game.getPlayerRow();
        Room roomForPlayer= game.getRoom(playerRow,playerCol);
        int maxRows= game.getNumRows();
        int maxCols= game.getNumCols();
        if (direction.equals("UP")){
            playerRow--;
            playerRow = (playerRow + maxRows) % maxRows;
        } 
        else if (direction.equals ("DOWN"))
        {
            playerRow++;
            playerRow = (playerRow + maxRows) % maxRows;
        }
        else if (direction.equals ("LEFT"))
        {
            playerCol++;
            playerCol = (playerCol + maxCols) % maxCols;
        }
        else if (direction.equals("RIGHT")) 
        {
            playerCol--;
            playerCol = (playerCol + maxCols) % maxCols;
        }
        if (roomForPlayer.containsSlimer()){
          roomForPlayer.showPlayerSlimed();
          int score = game.getScore();
          int newScore = score + game.numPointsForWin();
          game.updateMessage( "congratulations you have Won");
          
        }else{
           roomForPlayer.showPlayerMissed(); 
        }
    }

    public boolean checkForLoss(){
      GameBoard game =  GameBoard.getGame();
      Room currentRoomForPlayer= game.getRoom(getPlayerRow(),getPlayerCol());
      int PlayerCol= game.getPlayerCol();
      int PlayerRow= game.getPlayerRow();
      if (currentRoomForPlayer.containsSlimer()){
          currentRoomForPlayer.showPlayerSlimed();
          return true;
        }else if(currentRoomForPlayer.containsPortal()){
            currentRoomForPlayer.showPlayerInPortal();
            return true;
        }else{
           return false;
        }
    }

    public void checkForGhostTrap(){
       GameBoard game =  GameBoard.getGame();
       Room currentRoomForPlayer= game.getRoom(getPlayerRow(),getPlayerCol());
       int PlayerCol= game.getPlayerCol();
       int PlayerRow= game.getPlayerRow();
       int score = game.getScore();
       if (currentRoomForPlayer.containsGhostTrap()){
          int newScore = score + game.numPointsForGhostTrap();
          game.updateMessage( "congratulations you have found a ghostTrap");
          game.updateScore(newScore);
          currentRoomForPlayer.removeGhostTrap();
        }
    }

}
