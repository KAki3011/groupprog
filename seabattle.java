import java.util.*;
import java.io.*;

class Gamestate{
  private int p1state[][];    // player1の盤面情報
  private int p2state[][];    // player2の盤面情報
  private int p1ships;          // player1の艦の数
  private int p2ships;          // player2の艦の数
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
  public int getDistnace(){
    return distance;
  }
  public String getWay(){
    return way;
  }
}

public class seabattle{
  public static void main(String args[]){
    int player; // 0:player1
    int p1s, p2s, i, j, e = 0, f = 0, f2 = 0, i2, j2;
    int[][] test;
    Move move = new Move();
    Gamestate gamestate = new Gamestate();
    //-----------------------------------------
    //CUI
    Scanner scan;
    String str;
    System.out.println("艦の配置");
    System.out.println("戦艦を配置する座標を入力してください(二つの数字を空白で区切って入力)");
    scan = new Scanner(System.in);
    str = scan.next();
    i = Integer.parseInt(str);
    str = scan.next();
    j = Integer.parseInt(str);
    gamestate.setPosition(0, i, j);
    System.out.println("巡洋艦を配置する座標を入力してください");
    str = scan.next();
    i = Integer.parseInt(str);
    str = scan.next();
    j = Integer.parseInt(str);
    gamestate.setPosition(0, i, j);
    System.out.println("潜水艦を配置する座標を入力してください");
    str = scan.next();
    i = Integer.parseInt(str);
    str = scan.next();
    j = Integer.parseInt(str);
    gamestate.setPosition(0, i, j);
    //敵艦の配置
    gamestate.setPosition(1, 1, 1);
    gamestate.setPosition(1, 0, 3);
    gamestate.setPosition(1, 3, 2);
    //一方的に攻撃、移動
    p1s = gamestate.getships(0);
    p2s = gamestate.getships(1);
    while(p1s != 0 && p2s != 0){
      while(e != 1){
        System.out.println("------------------");
        gamestate.printstate(2);
        System.out.println("攻撃か移動か選択してください 0:攻撃　1:移動");
        str = scan.next();
        f = Integer.parseInt(str);
        while(e != 1){
          if(f == 0){
            System.out.println("攻撃する座標を入力してください");
            str = scan.next();
            i = Integer.parseInt(str);
            str = scan.next();
            j = Integer.parseInt(str);
            f2 = gamestate.setAttack(0, i, j);
            if(f2 == 2){
              System.out.println("Hit");
              e = 1;
            }else if(f2 == 1){
              System.out.println("Splashes");
              e = 1;
            }else if(f2 == 0){
              System.out.println("Miss");
              e = 1;
            }else{
              System.out.println("攻撃できません");
              e = 0;
            }
          }else{
            System.out.println("移動もとの座標と移動先の座標を入力してください");
            str = scan.next();
            i = Integer.parseInt(str);
            str = scan.next();
            j = Integer.parseInt(str);
            str = scan.next();
            i2 = Integer.parseInt(str);
            str = scan.next();
            j2 = Integer.parseInt(str);
            move = gamestate.setMove(0, i, j, i2, j2);
            if(move.getDistnace() > -1){
              System.out.println(move.getWay()+"へ"+move.getDistnace()+"マス移動");
              e = 1;
            }else{
              System.out.println("移動できません");
              e = 0;
            }
          }
        }
      }
      e = 0;
      p1s = gamestate.getships(0);
      p2s = gamestate.getships(1);
    }
    if(p2s == 0){
      System.out.println("You win");
    }else{
      System.out.println("You lose");
    }

    //----------------------------------------
    return;
  }
}
