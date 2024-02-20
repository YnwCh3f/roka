package com.example.roka;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class HelloController {

    @FXML private Pane pn;
    @FXML private Label lbLoves;
    @FXML private Label lbRoka;

    private ImageView[][] ivTomb = new ImageView[16][32];
    private String[] tomb = {"dark", "dead", "fox", "home", "target", "tree"};
    private int[][]t = new int[16][32];
    private Image[] icon = new Image[5];

    private final int DARK = 0;
    private final int DEAD = 1;
    private final int FOX = 2;
    private final int HOME = 3;
    private final int TARGET = 4;
    private final int TREE = 5;



    public void initialize(){
        for (int i = 0; i < 5; i++){
            icon[i] = getKep(tomb[i]);
        }
        for (int i = 0; i < 16; i++){ for (int j = 0; j < 32; j++) {
                ivTomb[i][j] = new ImageView(icon[DARK]);
                ivTomb[i][j].setTranslateX(48*j+10);
                ivTomb[i][j].setTranslateY(48*i+10);
                int ii = i; int jj = j;
                ivTomb[i][j].setOnMouseEntered(e -> setAround(ii, jj, icon[HOME]));
                ivTomb[i][j].setOnMouseExited(e -> setAround(ii, jj, icon[DARK]));
                pn.getChildren().add(ivTomb[i][j]);
            }
        }
    }

    private Image getKep(String s){
        return new Image(getClass().getResourceAsStream(s+".png"));
    }

    private void setAround(int i, int j, Image im){
        for (int ii = i-2; ii <= i+2; ii++){
            for (int jj = j-1; jj <= j+1; jj++){
                try{
                    ivTomb[ii][jj].setImage(im);
                }catch (Exception e) {continue;}
            }
        }

        for (int ii = i-1; ii <= i+1; ii++){
            for (int jj = j-2; jj <= j+2; jj++){
                try{
                    ivTomb[ii][jj].setImage(im);
                }catch (Exception e) {continue;}
            }
        }
    }

    private void generalErdo(){
        for (int i = 0; i < 16; i++){ for (int j = 0; j < 32; j++) {
                if (Math.random() < 0.1)t[i][j] = FOX;
                else t[i][j] = TREE;
            }
        }
    }

}