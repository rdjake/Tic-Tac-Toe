import java.awt.Graphics;
import javax.swing.JFrame;
import java.lang.System;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Arrays;
PImage line_gor, line_vert, x, o, line_diag1, line_diag2, winer, xw, ow, white;
int Ox = 155, Oy = 305, even = 1, player, sum,i,j, n = 958,m = 9, num, max, max_i, arr;
int[] poles = {0,0,0,0,0,0,0,0,0};
char[][] data, buf;
char[] trash;  
int[] gain, keys = {7,8,9,4,5,6,1,2,3};
boolean result = false, kek = false;
FileReader reader;
void setup()
  {
    size(450,450);
    background(255);
    line_gor = loadImage("line_gor.png");
    line_vert = loadImage("line_vert.png");
    line_diag1 = loadImage("line_diag1.png");
    line_diag2 = loadImage("line_diag2.png");
    xw = loadImage("x_win.png");
    ow = loadImage("o_win.png");
    white = loadImage("new.png");
    x = loadImage("x.png");
    o = loadImage("o.png");
    image(line_vert,138,0);
    image(line_vert,290,0);
    image(line_gor,0,130);
    image(line_gor,0,283);
    
    data();
  }
 
 void data(){
   n = 958;
   gain = new int[m];
    data = new char[n][10];
    buf = new char[n][10];
    trash = new char[2];
    try{
      reader = new FileReader("data/data.txt");
      for(i=0;i<n;i++)  {reader.read(data[i]); reader.read(trash);}
      
      reader.close();  
    } catch(IOException ie) {PImage dead = loadImage("dead.png"); image(dead,0,0);}
 }

void draw(){}

void keyPressed() {
  if(!result) game(key);
  else { 
    if(!kek){
      image(winer,0,0); kek = true;
      }
    else {
      image(white,0,0);
      image(line_vert,138,0);
      image(line_vert,290,0);
      image(line_gor,0,130);
      image(line_gor,0,283);
      for(i = 0; i < 9; i++) poles[i] = 0;
      result = false;
      kek = false;
      even = 1;
      data();
      }
  }
}
void game(int key)
{
  num = key-49;
  for(i=0;i<m;i++) if(keys[i] == num+1) arr = i;
  if(key < 49 || key > 57 || poles[arr] > 0) return;
  //System.out.print(arr);
  poles[arr] = 1; 
  //player = 1;
  drawing(key-48, o, "Your");
  compare(1);
  if(!result){
    AI(arr);
    //player = 2;
    compare(2);
  }
  
}
void AI(int num)
{
  for(i=0,j=0;i<n;i++) if(data[i][num] == 'o'){ buf[j] = data[i]; j++;}
  n = j;
  data = new char[n][10];
  for(i=0;i<n;i++) data[i] = buf[i]; 
  
  for(i = 0; i < m; i++){
    if(poles[i] == 0) gain[i] = 0;
    else {gain[i] = -1000; continue;}
    for(j = 0; j < n; j++) {
      if(data[j][9] == 'p' &&  data[j][i]  == 'x') gain[i]+=2;
      if(data[j][9] == 'n' &&  data[j][i]  == 'x') gain[i]--;
    }
    
    }
  max = gain[0];
  max_i = 0;
  for(i=0;i<m;i++){
    if(gain[i] > max) {max = gain[i]; max_i = i; }
    System.out.print(i+":"+gain[i]+" ");
  }
  System.out.print("\nMax: "+max_i+":"+gain[max_i]+"\n");
  poles[max_i] = 2;
  gain[max_i] = -1000;
   for(i=0;i<m;i++) System.out.print(i+":"+gain[i]+" ");

  drawing(keys[max_i], x, "\nAI");
  //m--;
}

void compare(int player)
{
  even++;
  System.out.print("Step: "+even+"\nPoles:");
  for(i=0;i<m;i++) System.out.print(poles[i]+" ");
  System.out.print("\n");
   if(even > 4){
    if(even%2 == 0) winer = ow;
    else winer = xw;
    sum = 3*player;
    if(poles[2]*poles[4]*poles[6] != 0 && poles[2]+poles[4]+poles[6] == sum) {image(line_diag2,0,0); result = true;}
    if(poles[0]*poles[4]*poles[8] != 0 && poles[0]+poles[4]+poles[8] == sum) {image(line_diag1,0,0);result = true;}
    if(poles[1]*poles[4]*poles[7] != 0 && poles[1]+poles[4]+poles[7] == sum) {image(line_vert,220,0);result = true;}
    if(poles[3]*poles[4]*poles[5] != 0 && poles[3]+poles[4]+poles[5] == sum) {image(line_gor,0,210);result = true;}
    if(poles[0]*poles[1]*poles[2] != 0 && poles[0]+poles[1]+poles[2] == sum) {image(line_gor,0,55);result = true;}
    if(poles[6]*poles[7]*poles[8] != 0 && poles[6]+poles[7]+poles[8] == sum) {image(line_gor,0,355);result = true;}
    if(poles[0]*poles[3]*poles[6] != 0 && poles[0]+poles[3]+poles[6] == sum) {image(line_vert,65,0);result = true;}
    if(poles[2]*poles[5]*poles[8] != 0 && poles[2]+poles[5]+poles[8] == sum) {image(line_vert,365,0);result = true;}
    if(!result && even>9) {PImage dead = loadImage("dead.png"); image(dead,0,0); result=true; kek=true;}
    
  }
}
void drawing(int number, PImage img, String who)
{
  
  System.out.print(who+"'s choice: "+number+"\n");
  switch(number) {
  case 1:
    image(img,0,Oy);
    break;
  case 2:
    image(img,Ox,Oy);
    break;
  case 3:
    image(img,Oy,Oy);
    break;
  case 4:
    image(img,0,Ox);
    break;
  case 5:
    image(img,Ox,Ox);
    break;
  case 6:
    image(img,Oy,Ox);
    break;
  case 7:
    image(img,0,0);
    break;
  case 8:
    image(img,Ox,0);
    break;
  case 9:
    image(img,Oy,0);
    break;
  }
}
