package models;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javax.swing.*;
import java.net.URL;

/**
 * Created by michal on 16/03/14.
 */
public abstract class Fish {
    Image image;
    private String colour;

    public Fish(final String colour){
        this.image = loadImage(colour);
        this.colour = colour;
    }

    public static Image loadImage(String colour){
        return new Image("res\\"+colour+"_fish.png",true);
    }

    public String toString(){
        return this.getClass().toString();
    }
    public Image getImage(){return image;}

    public String getColour() {
        return colour;
    }

    abstract public boolean addFish(Fish fish);
    abstract public boolean removeFish(Fish fish);
}
