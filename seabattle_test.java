package group;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.CardLayout;
import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
//computer
class ComAction {//computerの次の行動に関する座標を格納した配列を返す
  public int[] comset(){//初期セット
    int set[] = new int[6];
    set[0] = (int)(5 * (Math.random()));
    set[1] = (int)(5 * (Math.random()));
    set[2] = (int)(5 * (Math.random()));
    set[3] = (int)(5 * (Math.random()));
    set[4] = (int)(5 * (Math.random()));
    set[5] = (int)(5 * (Math.random()));
    for(;set[0]==set[2]&&set[1]==set[3];){
      set[2] = (int)(5 * (Math.random()));
      set[3] = (int)(5 * (Math.random()));
    }
    for(;(set[0]==set[4]&&set[1]==set[5])||(set[2]==set[4]&&set[3]==set[5]);){
      set[4] = (int)(5 * (Math.random()));
      set[5] = (int)(5 * (Math.random()));
    }
    return set;
  }

  public int[] com(int banmen[][], int banmen2[][]){
    double r = Math.random();//ランダム変数
    int data[] = new int[5];//返り値
    int i, j, k, l;
    int ban[][] = new int[5][5];//banmen[][]のコピー(comの盤面)
    int ban2[][] = new int[5][5];//banmen2[][]のコピー(playerの盤面)
    int gata[] = new int[50];//攻撃の座標を格納(x座標,y座標の順で)
    int jam2[] = new int[6];//playerの艦の座標の格納
    int jam[] = new int[6];//移動の際の前の座標の格納(x座標,y座標の順で)
    int aim[] = new int[18];//移動の際の後の座標の格納(x座標,y座標の順で)
    double at = 0.5;
    k = 0;
    l = 0;

    for(i=0;i<5;i++)
    for(j=0;j<5;j++)
    ban[i][j] = banmen[i][j];//2次元配列のコピー

    for(i=0;i<5;i++)
    for(j=0;j<5;j++)
    ban2[i][j] = banmen2[i][j];//2次元配列のコピー

    for(i=0;i<5;i++)//自分の艦の回りに敵がいる場合攻撃の確率を上げる
    for(j=0;j<5;j++)
    if(ban2[i][j]>0){
      jam2[k] = i;
      jam2[k+1] = j;
      k += 2;
    }
    for(i=0;i<k;i+=2)
    if(ban[jam2[i]][jam2[i+1]]==-1 || ban[jam2[i]][jam2[i+1]]==-3)
    at += 0.1;


    k = 0;
    l = 0;
    if(r<at){//攻撃
      data[0] = 0;

      for(i=0;i<5;i++){
        for(j=0;j<5;j++){
          if(ban[i][j]==-1 || ban[i][j]==-3){//攻撃座標の格納
            gata[k] = i;
            gata[k+1] = j;
            k = k + 2;
          }
        }
      }

      r = Math.random();//ランダム
      i = (int)(k * r);
      if(i%2!=0)i--;
      data[1] = gata[i];
      data[2] = gata[i+1];

    }else{//移動
      data[0] = 1;

      for(i=0;i<5;i++){
        for(j=0;j<5;j++){
          if(ban[i][j]>0){//移動前の座標の格納
            jam[l] = i;
            jam[l+1] = j;
            l = l + 2;
          }
        }
      }
      r = Math.random();//ランダム
      l = (int)(l * r);
      if(l%2!=0)l--;
      data[1] = jam[l];
      data[2] = jam[l+1];
      i = jam[l];
      j = jam[l+1];
      l = 0;

      //上下左右の座標の格納

      for(k=0;k<5;k++){
        if(ban[i][k]==-2||ban[i][k]==-3){
          aim[l] = i;
          aim[l+1] = k;
          l = l + 2;
        }
      }
      for(k=0;k<5;k++){
        if(ban[k][j]==-2||ban[k][j]==-3){
          aim[l] = k;
          aim[l+1] = j;
          l = l + 2;
        }
      }

      //上下左右の格納終了
      r = Math.random();//ランダム
      l = (int)(l * r);
      if(l%2!=0)l--;
      data[3] = aim[l];
      data[4] = aim[l+1];

    }
    return data;
  }
}

//VC
class TotalFrame extends JFrame implements ActionListener{
  static Gamestate gs;
  static int start=3;
  static int flag = 0;
  static int move=0;
  static int movex=0;
  static int movey=0;
  static int[][] St = new int[5][5];
  static SeaButton[][] SB = new SeaButton[5][5];
  JPanel board = new JPanel();
  JPanel textboard = new JPanel();
  static JLabel txt = new JLabel("Textbox");
  static JLabel txt2 = new JLabel("Textbox");
  static JLabel txt3 = new JLabel("Textbox");
  static JLabel txt4 = new JLabel("Textbox");
  static JLabel txt5 = new JLabel("Textbox");
  //追加---------------------------------------------------
  JPanel cardpanel = new JPanel();
  JPanel gamepanel = new JPanel();
  JPanel titlep = new JPanel();
  JPanel thirdp = new JPanel();
  JLabel gname = new JLabel();
  JButton startb = new JButton("START");
  JButton restart = new JButton("REATART");
  JButton exit = new JButton("EXIT");
  CardLayout layout = new CardLayout();
  //-------------------------------------------------------

  public TotalFrame(Gamestate gs){
    this.gs=gs;
    this.setSize(500,700);
    board.setSize(500,500);
    //修正---------------------------
    this.add(cardpanel);
    cardpanel.setLayout(layout);
    cardpanel.setSize(500, 700);
    gamepanel.setLayout(new BorderLayout());
    gamepanel.add(board, BorderLayout.NORTH);
    gamepanel.add(textboard, BorderLayout.SOUTH);
    //--------------------------------
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    textboard.setLayout(new GridLayout(5,1));
    textboard.add(txt5);
    textboard.add(txt4);
    textboard.add(txt3);
    textboard.add(txt2);
    textboard.add(txt);
    board.setLayout(new GridLayout(5,5));
    int i=0;
    int j=0;
    while(i<5){
      j=0;
      while(j<5){
        SB[i][j]=new SeaButton(i, j);
        board.add(SB[i][j]);
        j++;
      }
      i++;
    }
    txt.setFont(new Font("Ariel", Font.BOLD,40));
    txt2.setFont(new Font("Ariel", Font.BOLD,40));
    txt3.setFont(new Font("Ariel", Font.BOLD,40));
    txt4.setFont(new Font("Ariel", Font.BOLD,40));
    txt5.setFont(new Font("Ariel", Font.BOLD,40));
    //追加----------------------------------------
    //titlep.setSize(500, 700);
    titlep.setLayout(null);
    gname.setText("海戦ゲーム");
    startb.setBounds(200, 500, 100, 50);
    gname.setBounds(0, 200, 500, 100);
    startb.addActionListener(this);
    startb.setActionCommand("start");
    titlep.add(startb);
    titlep.add(gname);

    //thirdp.setSize(500, 700);
    thirdp.setLayout(null);
    restart.setBounds(200, 300, 100, 50);
    restart.setActionCommand("restart");
    exit.setBounds(200, 400, 100, 50);
    exit.setActionCommand("exit");
    thirdp.add(restart);
    thirdp.add(exit);

    cardpanel.add(titlep, "title");
    cardpanel.add(gamepanel, "gamepanel");
    cardpanel.add(thirdp, "thirdp");

    //--------------------------------------------
    this.setVisible(true);
  }
  public static void setflag(int i){
    flag = i;
  }
  public static int getflag(){
    return flag;
  }

  public static int PP(int x,int y){
    return St[x][y];
  }
  public static void St(String s){
    String f=txt.getText();
    Stlog1(f);
    txt.setText(s);
  }
  public static void Stlog1(String s){
    String f=txt2.getText();
    Stlog2(f);
    txt2.setText(s);
  }
  public static void Stlog2(String s){
    String f=txt3.getText();
    Stlog3(f);
    txt3.setText(s);
  }
  public static void Stlog3(String s){
    String f=txt4.getText();
    Stlog4(f);
    txt4.setText(s);
  }
  public static void Stlog4(String s){
    txt5.setText(s);
  }
  public static void Repaint(){
    int i=0;
    int j=0;
    St=gs.getstate(0);
    while(i<5){
      while(j<5){
        SB[i][j].Awake();
        j++;
      }
      j=0;
      i++;
    }
  }
  public static int CheckMove() {
    return move;
  }
  public static int GetMovex() {
    return movex;
  }
  public static int GetMovey() {
    return movey;
  }
  public static void SetMove(int m) {
    move=m;
  }
  public static void SetMove_x(int x) {
    movex=x;
  }
  public static void SetMove_y(int y) {
    movey=y;
  }
  public static int GetStart() {
    return start;
  }
  public static void CountdownStart() {
    start--;

  }
  //追加----------------------------------------
  public void actionPerformed(ActionEvent e){
    String str = e.getActionCommand();
    if(str.equals("start")){
      layout.show(cardpanel, "gamepanel");
    }else if(str.equals("restart"));
  }
  //-------------------------------------------
}

class SeaButton extends JPanel implements ActionListener{
  JButton b=new JButton(" ");
  int x;
  int y;
  int q=0;
  ImageIcon icon;
  Move move;
  public SeaButton(int i, int j){
    b.setFont(new Font("Ariel", Font.BOLD,40));
    x=i;
    y=j;
    this.setLayout(new GridLayout(1,1));
    b.addActionListener(this);
    this.add(b);
  }
  public void actionPerformed(ActionEvent e){
    if(TotalFrame.getflag() > 0){
      TotalFrame.St("Enemy Turn");
    }else{
      TotalFrame.PP(x, y);
      //TotalFrame.St("("+String.valueOf(x)+","+String.valueOf(y)+")"+"was pushed.");
      //modelへの干渉->
      if(TotalFrame.gs.p1ships < 3 && TotalFrame.GetStart()>0){
        TotalFrame.gs.setPosition(0,x,y);
        TotalFrame.St("艦を配置しました");
        TotalFrame.CountdownStart();
        TotalFrame.setflag(1);
      }else if(TotalFrame.CheckMove()==1){
        if((TotalFrame.GetMovex()==x || TotalFrame.GetMovey()==y) && (TotalFrame.GetMovex()!=x || TotalFrame.GetMovey()!=y)){
          //TotalFrame.St("Move");
          move = TotalFrame.gs.setMove(0,TotalFrame.GetMovex(),TotalFrame.GetMovey(),x,y);
          TotalFrame.St(move.getWay()+"へ"+move.getDistance()+"マス移動");
          TotalFrame.setflag(1);
        }else{
          TotalFrame.St("Move straight");
        }
        TotalFrame.SetMove(0);
        //TotalFrame.setflag(1);
      }else{
        switch (TotalFrame.PP(x,y)){
          case 0:
          case -2:
          TotalFrame.St("Denied");
          break;
          case -1:
          case -3:
          TotalFrame.St("("+String.valueOf(x)+","+String.valueOf(y)+")"+"in Fire.");
          int i=TotalFrame.gs.setAttack(0,x,y);
          if(i==2){
            TotalFrame.St("Hit!");
          }else if(i==1){
            TotalFrame.St("Splash!");
          }else{
            TotalFrame.St("Miss!");
          }
          TotalFrame.setflag(1);
          break;
          default:
          TotalFrame.St("MoveMode");
          TotalFrame.SetMove(1);
          TotalFrame.SetMove_x(x);
          TotalFrame.SetMove_y(y);

        }
      }
      //TotalFrame.setflag(1);
      TotalFrame.Repaint();
    }
  }
  public void SetColor(Color x){
    b.setBackground(x);
  }
  public void Awake(){
    int i=TotalFrame.PP(x,y);
    String s= String.valueOf(i);
    b.setText(s);
    b.setIcon(null);
    switch (i){
      case -3:
      b.setBackground(Color.GREEN);
      break;
      case -2:
      b.setBackground(Color.YELLOW);
      break;
      case -1:
      b.setBackground(Color.RED);
      break;
      case 0:
      b.setBackground(null);
      break;
      case 1:
      b.setBackground(Color.CYAN);
      icon=new ImageIcon("./taraibune.png");
      b.setIcon(icon);
      break;
      case 2:
      b.setBackground(Color.CYAN);
      icon=new ImageIcon("./nanpasen.png");
      b.setIcon(icon);
      break;
      case 3:
      b.setBackground(Color.CYAN);
      icon=new ImageIcon("./fune.png");
      b.setIcon(icon);
      break;
    }
    //System.out.println(i);
    //GridFrameに押されたボタンの座標送信
  }
}

//model
class Gamestate{
  private int p1state[][];    // player1の盤面情報
  private int p2state[][];    // player2の盤面情報
  int p1ships;          // player1の艦の数
  int p2ships;          // player2の艦の数
  private int p1position[];   // player1の艦の場所
  private int p2position[];   // player2の艦の場所
  // 艦のセットはオブジェクト生成時には行わない
  public Gamestate(){
    p1state = new int[5][5];
    p2state = new int[5][5];
    p1ships = 0;
    p2ships = 0;
    p1position = new int[6];
    p2position = new int[6];
    for(int i = 0; i < 6; i++){
      p1position[i] = -1;
      p2position[i] = -1;
    }
  }

  // CUIでの盤面の表示 引数はプレイヤー
  // p=0:プレイヤー p=1:CPU p=2:両者
  public void printstate(int p){
    if(p == 0){
      System.out.println("Player1");
      for(int i = 0; i < 5; i++){
        for(int j = 0; j < 5; j++){
          System.out.print(p1state[i][j]+" ");
        }
        System.out.println();
      }
      System.out.println("---------------------");
    }else if(p == 1){
      System.out.println("Player2(CPU)");
      for(int i = 0; i < 5; i++){
        for(int j = 0; j < 5; j++){
          System.out.print(p2state[i][j]+" ");
        }
        System.out.println();
      }
      System.out.println("---------------------");
    }else if(p == 2){
      System.out.println("Player1");
      for(int i = 0; i < 5; i++){
        for(int j = 0; j < 5; j++){
          System.out.print(p1state[i][j]+" ");
        }
        System.out.println();
      }
      System.out.println("---------------------");
      System.out.println("Player2(CPU)");
      for(int i = 0; i < 5; i++){
        for(int j = 0; j < 5; j++){
          System.out.print(p2state[i][j]+" ");
        }
        System.out.println();
      }
      System.out.println("---------------------");
    }
    return;
  }

  // 艦の初期位置のセット
  // 戦艦、巡洋艦、潜水艦の順でセット
  /* 盤面の情報は以下のようにする
  戦艦　    3(数字は耐久値としても扱う)
  巡洋艦　  2
  潜水艦    1
  攻撃可能  -1
  移動可能  -2
  攻撃かつ移動可能　-3
  何もなし  0                  */
  public void setPosition(int p, int i, int j){
    if(p == 0){
      if(p1ships >= 3){
        System.out.println("これ以上艦の配置を行えません。");
      }else{
        if(p1ships == 0){
          // 戦艦のセット
          p1state[i][j] = 3;
          p1position[0] = i;
          p1position[1] = j;
          // 攻撃可能箇所のセット
          setAttackrange(this.p1state, i, j);
          // 移動可能範囲のセット
          setMovementrange(this.p1state, i, j);
          // 艦の数の更新
          p1ships += 1;
        }else if(p1ships == 1){
          p1state[i][j] = 2;
          p1position[2] = i;
          p1position[3] = j;
          // 攻撃可能箇所のセット
          setAttackrange(this.p1state, i, j);
          // 移動可能範囲のセット
          setMovementrange(this.p1state, i, j);
          // 艦の数の更新
          p1ships += 1;
        }else if(p1ships == 2){
          p1state[i][j] = 1;
          p1position[4] = i;
          p1position[5] = j;
          // 攻撃可能箇所のセット
          setAttackrange(this.p1state, i, j);
          // 移動可能範囲のセット
          setMovementrange(this.p1state, i, j);
          // 艦の数の更新
          p1ships += 1;
        }
      }
    }else if(p == 1){
      if(p2ships >= 3){
        System.out.println("これ以上艦の配置を行えません。");
      }else{
        if(p2ships == 0){
          // 戦艦のセット
          p2state[i][j] = 3;
          p2position[0] = i;
          p2position[1] = j;
          // 攻撃可能箇所のセット
          setAttackrange(this.p2state, i, j);
          // 移動可能範囲のセット
          setMovementrange(this.p2state, i, j);
          // 艦の数の更新
          p2ships += 1;
        }else if(p2ships == 1){
          p2state[i][j] = 2;
          p2position[2] = i;
          p2position[3] = j;
          // 攻撃可能箇所のセット
          setAttackrange(this.p2state, i, j);
          // 移動可能範囲のセット
          setMovementrange(this.p2state, i, j);
          // 艦の数の更新
          p2ships += 1;
        }else if(p2ships == 2){
          p2state[i][j] = 1;
          p2position[4] = i;
          p2position[5] = j;
          // 攻撃可能箇所のセット
          setAttackrange(this.p2state, i, j);
          // 移動可能範囲のセット
          setMovementrange(this.p2state, i, j);
          // 艦の数の更新
          p2ships += 1;
        }
      }
    }
    return;
  }

  // 攻撃
  // 返り値 2:hit 1:splashes 0:miss -1:攻撃不可
  public int setAttack(int p, int i, int j){
    if(p == 0){
      if(p1state[i][j] == -1 || p1state[i][j] == -3 || p1state[i][j] > 0){
        if(p2state[i][j] > 0){
          p2state[i][j] -= 1;
          if(p2state[i][j] == 0){
            p2ships -= 1;
            for(int k = 0; k < 6; k += 2){
              if((i == p2position[k]) && j == p2position[k+1]){
                p2position[k] = p2position[k+1] = -1;
              }
            }
            stateupdate(1);
          }
          return 2;
        }else{
          if(isSplashes(p2state, i, j) > 0){
            return 1;
          }else{
            return 0;
          }
        }
      }else{
        return -1;
      }
    }else if(p == 1){
      if(p2state[i][j] == -1 || p2state[i][j] == -3 || p2state[i][j] > 0){
        if(p1state[i][j] > 0){
          p1state[i][j] -= 1;
          if(p1state[i][j] == 0){
            p1ships -= 1;
            for(int k = 0; k < 6; k += 2){
              if((i == p1position[k]) && j == p1position[k+1]){
                p1position[k] = p1position[k+1] = -1;
              }
            }
            stateupdate(0);
          }
          return 2;
        }else{
          if(isSplashes(p1state, i, j) > 0){
            return 1;
          }else{
            return 0;
          }
        }
      }else{
        return -1;
      }
    }
    return -1;
  }

  // 艦の移動
  // 戻り値　move型
  public Move setMove(int p, int i1, int j1, int i2, int j2){
    Move move = new Move("移動不可", -1);
    if(p == 0){
      if((i1-i2) != 0 && (j1-j2) != 0){
        return move;
      }
      if(p1state[i1][j1] > 0 && (p1state[i2][j2] == -2 || p1state[i2][j2] == -3)){
        if(i2-i1 > 0){
          move.set("南", i2-i1);
        }else if(i1-i2 > 0){
          move.set("北", i1-i2);
        }else if(j2-j1 > 0){
          move.set("東", j2-j1);
        }else if(j1-j2 > 0){
          move.set("西", j1-j2);
        }
        p1state[i2][j2] = p1state[i1][j1];
        for(int k = 0; k < 6; k += 2){
          if(i1 == p1position[k] && j1 == p1position[k+1]){
            p1position[k] = i2;
            p1position[k+1] = j2;
          }
        }
        stateupdate(0);
      }
    }else if(p == 1){
      if((i1-i2) != 0 && (j1-j2) != 0){
        return move;
      }
      if(p2state[i1][j1] > 0 && (p2state[i2][j2] == -2 || p2state[i2][j2] == -3)){
        if(i2-i1 > 0){
          move.set("南", i2-i1);
        }else if(i1-i2 >= 0){
          move.set("北", i1-i2);
        }else if(j2-j1 > 0){
          move.set("東", j2-j1);
        }else if(j1-j2 > 0){
          move.set("西", j1-j2);
        }
        p2state[i2][j2] = p2state[i1][j1];
        for(int k = 0; k < 6; k += 2){
          if(i1 == p2position[k] && j1 == p2position[k+1]){
            p2position[k] = i2;
            p2position[k+1] = j2;
          }
        }
        stateupdate(1);
      }
    }
    return move;
  }

  //艦の残りを取得
  public int getships(int p){
    if(p == 0){
      return p1ships;
    }else if(p == 1){
      return p2ships;
    }
    return -1;
  }

  // stateを取得　0:player1
  public int[][] getstate(int p){
    if(p == 0){
      return p1state;
    }else if(p == 1){
      return p2state;
    }else{
      return new int[5][5];
    }
  }

  // 艦の攻撃範囲のセット
  private void setAttackrange(int[][] arr, int i, int j){
    //左上
    if(i > 0 && j > 0){
      if(arr[i-1][j-1] == 0 || arr[i-1][j-1] == -2){
        arr[i-1][j-1] -= 1;
      }
    }
    //上
    if(i > 0){
      if(arr[i-1][j] == 0 || arr[i-1][j] == -2){
        arr[i-1][j] -= 1;
      }
    }
    //右上
    if(i > 0 && j < 5-1){
      if(arr[i-1][j+1] == 0 || arr[i-1][j+1] == -2){
        arr[i-1][j+1] -= 1;
      }
    }
    //左
    if(j > 0){
      if(arr[i][j-1] == 0 || arr[i][j-1] == -2){
        arr[i][j-1] -= 1;
      }
    }
    //右
    if(j < 5-1){
      if(arr[i][j+1] == 0 || arr[i][j+1] == -2){
        arr[i][j+1] -= 1;
      }
    }
    //左下
    if(i < 5-1 && j > 0){
      if(arr[i+1][j-1] == 0 || arr[i+1][j-1] == -2){
        arr[i+1][j-1] -= 1;
      }
    }
    //下
    if(i < 5-1){
      if(arr[i+1][j] == 0 || arr[i+1][j] == -2){
        arr[i+1][j] -= 1;
      }
    }
    //右下
    if(i < 5-1 && j < 5-1){
      if(arr[i+1][j+1] == 0 || arr[i+1][j+1] == -2){
        arr[i+1][j+1] -= 1;
      }
    }
    return;
  }

  // 移動可能範囲のセット
  private void setMovementrange(int[][] arr, int i, int j){
    //上方向
    for(int k=1; i-k > -1; k++){
      if(arr[i-k][j] == 0 || arr[i-k][j] == -1){
        arr[i-k][j] -= 2;
      }
    }
    //左方向
    for(int k=1; j-k > -1; k++){
      if(arr[i][j-k] == 0 || arr[i][j-k] == -1){
        arr[i][j-k] -= 2;
      }
    }
    //右方向
    for(int k=1; j+k < 5; k++){
      if(arr[i][j+k] == 0 || arr[i][j+k] == -1){
        arr[i][j+k] -= 2;
      }
    }
    //下方向
    for(int k=1; i+k < 5; k++){
      if(arr[i+k][j] == 0 || arr[i+k][j] == -1){
        arr[i+k][j] -= 2;
      }
    }
    return;
  }

  // 攻撃がミスか水しぶきか判定 2:上下左右に艦　1:斜めに艦　0:ミス
  private int isSplashes(int[][] arr, int i, int j){
    int v = 0;
    //左上
    if(i > 0 && j > 0){
      if(arr[i-1][j-1] > 0 ){
        v = 1;
      }
    }
    //上
    if(i > 0){
      if(arr[i-1][j] > 0){
        v = 2;
      }
    }
    //右上
    if(i > 0 && j < 5-1){
      if(arr[i-1][j+1] > 0){
        v = 1;
      }
    }
    //左
    if(j > 0){
      if(arr[i][j-1] > 0){
        v = 2;
      }
    }
    //右
    if(j < 5-1){
      if(arr[i][j+1] > 0){
        v = 2;
      }
    }
    //左下
    if(i < 5-1 && j > 0){
      if(arr[i+1][j-1] > 0){
        v = 1;
      }
    }
    //下
    if(i < 5-1){
      if(arr[i+1][j] > 0){
        v = 2;
      }
    }
    //右下
    if(i < 5-1 && j < 5-1){
      if(arr[i+1][j+1] > 0){
        v = 1;
      }
    }
    return v;
  }

  // 艦がいなくなった時の後処理
  private void stateupdate(int p){
    int b = 0, c = 0, s = 0;
    if(p == 0){
      // 耐久値を保存
      if(p1position[0] > -1){
        b = p1state[p1position[0]][p1position[1]];
      }
      if(p1position[2] > -1){
        c = p1state[p1position[2]][p1position[3]];
      }
      if(p1position[4] > -1){
        s = p1state[p1position[4]][p1position[5]];
      }
      // stateをreset
      reset(p1state);
      // 艦と各レンジのセット
      if(b > 0){
        p1state[p1position[0]][p1position[1]] = b;
      }
      if(c > 0){
        p1state[p1position[2]][p1position[3]] = c;
      }
      if(s > 0){
        p1state[p1position[4]][p1position[5]] = s;
      }
      printstate(0);
      for(int i = 0; i < 6; i += 2){
        if(p1position[i] > -1){
          setAttackrange(p1state, p1position[i], p1position[i+1]);
          setMovementrange(p1state, p1position[i], p1position[i+1]);
        }
      }
    }else if(p == 1){
      // 耐久値を保存
      if(p2position[0] > -1){
        b = p2state[p2position[0]][p2position[1]];
      }
      if(p2position[2] > -1){
        c = p2state[p2position[2]][p2position[3]];
      }
      if(p2position[4] > -1){
        s = p2state[p2position[4]][p2position[5]];
      }
      // stateをreset
      reset(p2state);
      // 艦と各レンジのセット
      if(b > 0){
        p2state[p2position[0]][p2position[1]] = b;
      }
      if(c > 0){
        p2state[p2position[2]][p2position[3]] = c;
      }
      if(s > 0){
        p2state[p2position[4]][p2position[5]] = s;
      }
      for(int i = 0; i < 6; i += 2){
        if(p2position[i] > -1){
          setAttackrange(p2state, p2position[i], p2position[i+1]);
          setMovementrange(p2state, p2position[i], p2position[i+1]);
        }
      }
    }else{

    }
    return;
  }

  private void reset(int[][] arr){
    for(int i = 0; i < 5; i++){
      for(int j = 0; j < 5; j++){
        arr[i][j] = 0;
      }
    }
    return;
  }
}

class Move{
  private String way;
  private int distance;

  public Move(){
    this(" ", 0);
  }
  public Move(String s){
    this(s, 0);
  }
  public Move(int d){
    this(" ", d);
  }
  public Move(String s, int d){
    way = s;
    distance = d;
  }

  public void setDistnace(int d){
    distance = d;
  }
  public void setWay(String s){
    way = s;
  }
  public void set(String s, int d){
    way = s;
    distance = d;
  }
  public int getDistance(){
    return distance;
  }
  public String getWay(){
    return way;
  }
}

//main
public class seabattle_test{
  public static void main(String args[]){
    int turn = 0; // 奇数:player1　偶数:player2
    int p1s, p2s, i, j, e = 0, f = 0, f2 = 0, i2, j2;
    int[][] test;
    Move move = new Move();
    Gamestate gamestate = new Gamestate();
    TotalFrame totalframe = new TotalFrame(gamestate);
    ComAction comaction = new ComAction();
    int data[] = new int[6];
    int data2[] = new int[5];
    double r = Math.random();
    System.out.println(r);
    //-----------------------------------------
    //GUI
    totalframe.St("艦を配置してください。");
    totalframe.St("戦艦を配置してください。");
    while(true){
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e1) {
        // TODO 自動生成された catch ブロック
        e1.printStackTrace();
      }
      if(totalframe.getflag() == 1){

        break;
      }
    }
    totalframe.St("巡洋艦を配置してください。");
    totalframe.setflag(0);
    while(true){
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e1) {
        // TODO 自動生成された catch ブロック
        e1.printStackTrace();
      }
      if(totalframe.getflag() == 1){
        break;
      }
    }
    totalframe.St("潜水艦を配置してください。");
    totalframe.setflag(0);
    while(true){
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e1) {
        // TODO 自動生成された catch ブロック
        e1.printStackTrace();
      }
      if(totalframe.getflag() == 1){
        break;
      }
    }
    //敵艦の配置
    data = comaction.comset();
    gamestate.setPosition(1, data[0], data[1]);
    gamestate.setPosition(1, data[2], data[3]);
    gamestate.setPosition(1, data[4], data[5]);

    p1s = gamestate.getships(0);
    p2s = gamestate.getships(1);
    if(r > 0.5){
      System.out.println("自分の番からです " + turn);
      turn++;
    }

    while(p1s != 0 && p2s != 0){
      System.out.println("ターン　"+turn);
      gamestate.printstate(2);
      if(turn % 2 == 1){
        totalframe.setflag(0);
        while(totalframe.getflag() != 1){
          totalframe.St("あなたの番です。");
          totalframe.setflag(0);
          while(true){
            try {
              Thread.sleep(500);
            } catch (InterruptedException e1) {
              // TODO 自動生成された catch ブロック
              e1.printStackTrace();
            }
            if(totalframe.getflag() == 1){
              //System.out.println(" ");
              break;
            }
          }
        }
      }else{
        try {
          Thread.sleep(1500);
          totalframe.St("相手プレイヤーの番です。");
        } catch (InterruptedException e1) {
          // TODO 自動生成された catch ブロック
          e1.printStackTrace();
        }
        data2 = comaction.com(gamestate.getstate(1), gamestate.getstate(0));
        if(data2[0] == 0){
          gamestate.setAttack(1, data2[1], data2[2]);
          try {
            Thread.sleep(1500);
            totalframe.St("CPUは"+"("+data2[1]+","+data2[2]+")へ攻撃");
          } catch (InterruptedException e1) {
            // TODO 自動生成された catch ブロック
            e1.printStackTrace();
          }
        }else{
          move = gamestate.setMove(1, data2[1], data2[2], data2[3], data2[4]);
          try {
            Thread.sleep(1500);
            totalframe.St(move.getWay()+"へ"+move.getDistance()+"マス移動");
          } catch (InterruptedException e1) {
            // TODO 自動生成された catch ブロック
            e1.printStackTrace();
          }
        }
      }
      p1s = gamestate.getships(0);
      p2s = gamestate.getships(1);
      turn++;
      totalframe.Repaint();
    }
    if(p2s == 0){
      totalframe.St("You win");
    }else{
      totalframe.St("You lose");
    }

    return;
  }
}

/*
254, 284行目他　TotalFrameにゲーム開始を判断する変数start、関数Getstart, CountdownStartを導入。
艦の数で判断していると、自機の数が減ると無限にたらい船が蘇ったため。

290, 293行目他　MoveModeになった時、Moveが失敗したときは相手にターンを回さないよう、setflagの位置を修正。
同時に、違う列だけでなく自身のマスに移動しようとしたときもMove straightと表示しターンをやり直すようにした。

1014行目他　
try {
Thread.sleep(1000);
} catch (InterruptedException e1) {
e1.printStackTrace();
}
これにより休止を入れ、ループを1秒ごとに回す。ループ文の表示を削除。

eclipseでやったのでpackageついてたり行ずれてたりするよ。
*/
