PImage draw;
PImage erase;
PImage save;
int mode; //0 for draw 1 for erase

final int pWidth=40;

void setup(){
  frameRate(240);
  size(700, 750);
  background(0);
  textAlign(CENTER,CENTER);
  textSize(28);
  strokeWeight(1); 
  draw = loadImage("draw.png");
  erase = loadImage("erase.png");
  save = loadImage("save.png");
  stroke(0);
  line(0,50,700,50);
  noStroke();
  image(draw, 5, 5, pWidth, pWidth);
  image(erase, 60, 5, pWidth, pWidth);
  image(save, 125, 5, pWidth, pWidth);
  mode = 0;//draw by default
}

void draw(){
  fill(255);
  rect(0, 0 ,700,50);
  stroke(0);
  line(0,50,700,50);
  noStroke();
  image(draw, 5, 5, pWidth, pWidth);
  image(erase, 60, 5, pWidth, pWidth);
  image(save, 125, 5, pWidth, pWidth);
}

import java.io.*;
import java.nio.file.Files;
import java.nio.file.*;

void mousePressed(){
  //if within the bar at the top
  if (mouseY >= 5 && mouseY <= 45){
    
    //draw
    if(mouseX >= 0 && mouseX <= 50){
       mode = 0;
       System.out.println("PROCED DRAW");
    }
    
    //erase
    else if(mouseX >= 55 && mouseX <= 105){
      background(0);
      System.out.println("PROCED ERASE");
    }
    
    //save
    else if (mouseX >=120 && mouseX <= 170){
      PImage bound  =get(0, 51, 700, 699);
      
      
      
      //change file name here
      bound.resize(28,28);
      bound.save("test.png");
      
      ////try to move file
      //File file = new File("test.jpeg");
      //System.out.println(file.getAbsolutePath());
      //File parentFolder = new File(file.getParent());
      //try{
      //  Path temp = Files.move
      //  (Paths.get(file.getAbsolutePath()), 
      //  Paths.get(parentFolder.getPath() + "\\training_sets\\validation"));
      //}
      //catch(Exception e){
      //  e.printStackTrace();
      //}
      
      
      
      
      System.out.println("PROCED SAVE");
        
    }
    
    else{
      System.out.println("PROCED ON BAR");
    }
  }  
}

void mouseDragged(){
  //in draw area
  if (!(mouseY >= 5 && mouseY <= 45)){
    fill(0); //black
    if (mode == 0){
      fill(255);
    }
    ellipse(mouseX, mouseY,70, 70);
  }
}
