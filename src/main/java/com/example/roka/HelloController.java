package com.example.roka;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
    private Image[] icon = new Image[6];

    private final int DARK = 0;
    private final int DEAD = 1;
    private final int FOX = 2;
    private final int HOME = 3;
    private final int TARGET = 4;
    private final int TREE = 5;

    private int eI = -1;
    private int eJ = -1;

    private AnimationTimer timer = null;
    private long most = 0;
    private long tt = 0;

    public void initialize(){
        for (int i = 0; i < 6; i++){
            icon[i] = getKep(tomb[i]);
        }
        for (int i = 0; i < 16; i++){ for (int j = 0; j < 32; j++) {
                ivTomb[i][j] = new ImageView(icon[DARK]);
                ivTomb[i][j].setTranslateX(48*j+10);
                ivTomb[i][j].setTranslateY(48*i+10);
                int ii = i; int jj = j;
                ivTomb[i][j].setOnMouseEntered(e -> setAround(ii, jj));
                ivTomb[i][j].setOnMouseExited(e -> setAround(ii, jj, icon[DARK]));
                ivTomb[i][j].setOnMouseClicked(e -> loves(ii,jj));
                pn.getChildren().add(ivTomb[i][j]);
            }
        }
        generalErdo();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                most = now;
                if (now > tt){
                    elbujik();
                }
                if (roka == 0) vege();
            }
        };
        timer.start();
    }

    private Image getKep(String s){
        return new Image(getClass().getResourceAsStream(s+".png"));
    }

    private void setAround(int i, int j){
        if (i != eI || j != eJ) {
            for (int ii = i - 2; ii <= i + 2; ii++) {
                for (int jj = j - 1; jj <= j + 1; jj++) {
                    try {
                        ivTomb[ii][jj].setImage(icon[t[ii][jj]]);
                    } catch (Exception e) {
                        continue;
                    }
                }
            }

            for (int ii = i - 1; ii <= i + 1; ii++) {
                for (int jj = j - 2; jj <= j + 2; jj++) {
                    try {
                        ivTomb[ii][jj].setImage(icon[t[ii][jj]]);
                    } catch (Exception e) {
                        continue;
                    }
                }
            }
            eI = i; eJ = j;
            tt = most + 500000000;
        }
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

    private int roka = 0;
    private int rokaMax = 0;
    private int loves = 0;
    private int talalat = 0;

    private void generalErdo(){
        roka = 0;
        loves = 0;
        talalat = 0;
        for (int i = 0; i < 16; i++){ for (int j = 0; j < 32; j++) {
                if (Math.random() < 0.1){t[i][j] = FOX; roka++;}
                else t[i][j] = TREE;
            }
        }
        rokaMax = roka;
        lbRoka.setText(roka + " / " + rokaMax + " róka");
        lbLoves.setText(loves + " lövés" + " / " + talalat + " találat");
    }

    private void elbujik(){
        for (int ii = -2; ii <= 2; ii++){
            for (int jj = -2; jj <= 2; jj++){
                int i = ii + eI; int j = jj + eJ;
                if (i >= 0 && i <= 15 && j >= 0 && j <= 31 && !(Math.abs(jj) == 2) && t[i][j] == FOX){
                    ivTomb[i][j].setImage(icon[HOME]); t[i][j] = HOME; roka--;
                }
            }
        }
        lbRoka.setText(roka + " / " + rokaMax + " róka");
    }

    private void loves(int i, int j){
        loves++;
        if (t[i][j] == FOX){
            ivTomb[i][j].setImage(icon[DEAD]); roka--;
            talalat++;
        }
        lbRoka.setText(roka + " / " + rokaMax + " róka");
        lbLoves.setText(loves + " lövés" + " / " + talalat + " találat");
    }

    private void vege(){
        felfed();
        Alert uzenet = new Alert(Alert.AlertType.NONE);
        uzenet.setHeaderText(null);
        uzenet.setTitle("Game Over!");
        String txt = String.format("%d lövésből %d talált, ami %d%%\n", loves, talalat, talalat*100/loves);
        txt += String.format("%d rókából %d lett lelőve, ami %d%%", rokaMax, talalat, talalat*100/rokaMax);
        uzenet.setContentText(txt);
        uzenet.getButtonTypes().removeAll();
        uzenet.getButtonTypes().add(new ButtonType("Újra"));
        uzenet.setOnCloseRequest(e -> generalErdo());
        uzenet.show();
        timer.stop();
    }

    private void felfed(){
        for (int i = 0; i < 16; i++){ for (int j = 0; j < 32; j++) {
            ivTomb[i][j].setImage(icon[t[i][j]]);
        }

        }
    }
}