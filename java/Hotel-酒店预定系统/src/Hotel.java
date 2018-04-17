//酒店类
public class Hotel {
    Room[][] rooms;
    //1.1.2层普通间101-110 201-210
    //3.3.4单人间
    //5.标准间
  Hotel(){
        rooms = new Room[5][10];
      for(int i=0;i<rooms.length;i++)
          for(int j=0;j<rooms[i].length;j++){
              if(i==0 ||i==1){
                  rooms[i][j]=new Room(((i+1)*100+j+1)+"","普通间",false);
              }
              if(i==2||i ==3){
                  rooms[i][j]=new Room(((i+1)*100+j+1)+"","单人间",false);
              }
                  else{
                      rooms[i][j]=new Room(((i+1)*100+j+1)+"","标准间",false);  
                  }
      }
  }
  //打印房间信息
  public void print(){
      for(int i=0;i<rooms.length;i++){
          for(int j=0;j<rooms[i].length;j++){
              System.out.print(rooms[i][j] + " ");
          }
          System.out.println();
      }
  }
  //提供一个预定房间的方法
  public void order(String no){
      for(int i=0;i<rooms.length;i++){
          for(int j=0;j<rooms[i].length;j++){
              if(rooms[i][j].getNo().equals(no)){
                  rooms[i][j].setIsuse(true);
                  return ;
              }
          }
      }
  }
//退房
  public void checkout(String no){
      for(int i=0;i<rooms.length;i++){
          for(int j=0;j<rooms[i].length;j++){
              if(rooms[i][j].getNo().equals(no)){
                  rooms[i][j].setIsuse(false);
                  return ;
              }
          }
      }
  }
}