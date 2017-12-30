seabattle.javaの概要

<class Gamestate>
player1:自分が操作
player2:敵が操作
//フィールド
private int p1state[][];    // player1の盤面情報
private int p2state[][];    // player2の盤面情報
/* 盤面の情報は以下のようにする
         戦艦　    3(数字は耐久値としても扱う)
         巡洋艦　  2
         潜水艦    1
         攻撃可能  -1
         移動可能  -2
         攻撃かつ移動可能　-3
         何もなし  0                  */
private int p1ships;          // player1の艦の数
private int p2ships;          // player2の艦の数
private int p1position[];   // player1の艦の場所
private int p2position[];   // player2の艦の場所

//publicメソッド
public int[][] getstate(int p)
      0:player1の盤面情報を返す(返り値はint[5][5])
      1:player2の盤面情報を返す(返り値はint[5][5])

public int getships(int p)
      0:player1の残りの艦数を返す
      1:player2の残りの艦数を返す

public void setPosition(int p, int i, int j)
      p = 0 でplayer1の盤面情報に位置(i, j (int[i][j]))に艦の情報を追加
      p = 1 でplayer2の同様のことをする
      3:戦艦　2:巡洋艦　1:潜水艦の順に追加(値は耐久値も兼ねる)

public int setAttack(int p, int i, int j)
      p = 0 でplayer2へ指定された位置(i, j)に攻撃する.
      p = 1 でplayer1へ同様のことをする
      返り値は 2:hit(敵艦のいずれかの艦の耐久値を1へらした)
              1:splashes(水しぶき、攻撃した位置の上下左右斜めに敵艦がいた)
              0:miss(攻撃をはずした)
              -1:攻撃可能な位置ではなかった

public Move setMove(int p, int i1, int j1, int i2, int j2)
      p = 0 でplayer1の位置(i1, j1)の艦を位置(i2, j2)へ移動
      p = 1 でplayer2で同様のことをする
      返り値は Move(メソッドは後述)
              フィールドに String way, int distance を持ち way は艦の移動方向(東西南北(今のところ))
              を表し。　distanceは移動したマスを表す。distance = -1の移動不可であったことを示す

public void printstate(int p)
      p = 0 player1state[][] を表示
      p = 1 player2state[][] を表示
      p = 2 両方表示

//privateメソッド
private void setAttackrange()
      艦の攻撃範囲をstateに設定

private void setMovementrange()
      艦の移動範囲をstateに設定

private int isSplashes()
        指定された位置の周りに艦がいるか返す(0より大きい値が返ってくると艦がいる)

private void reset()
       stateをリセット


<class Move>
//フィールド
String way //方向
int distance //移動したマス

//publicメソッド
public void setDistnace(int d)
    distanceにdを代入

public void setWay(String s)
    wayにsを代入

public void set(String s, int d)
    wayにs, distanceにdを代入

public int getDistnace()
    distanceを返す

public String getWay()
    wayを返す


//main
seabattle.javaをコンパイルすると、相手の手番がなく、一方的に攻撃、移動ができるゲームができます
動作の確認に使用してください
windowsでコンパイルするときは javac -encoding UTF-8 seabattle.java としてください
