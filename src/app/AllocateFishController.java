package app;

import com.sun.javafx.scene.control.skin.ListViewSkin;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Rectangle;
import models.BlueFish;
import models.Fish;
import models.RedFish;
import models.YellowFish;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class AllocateFishController implements Initializable {
    private MainApp app;
    @FXML ListView<Rectangle> handlePool;
    @FXML ListView<Rectangle> objectPool;
    @FXML AnchorPane allocateFishPane;


    @FXML
    public void newFish(MouseEvent event) {
        switch (((Button) event.getSource()).getId()) {
            case "red":
                app.heap.addElement(new RedFish());
                break;
            case "blue":
                app.heap.addElement(new BlueFish());
                break;
            case "yellow":
                app.heap.addElement(new YellowFish());
                break;
        }

        List<Fish> handlePoolList = app.heap.getHandlePoolList();
        handlePool.setItems(handleLists(handlePoolList));

        List<Fish> objectPoolList = app.heap.getObjectPoolList();
        objectPool.setItems(handleLists(objectPoolList));
        drawLines(handlePoolList, objectPoolList);
    }

    private void drawLines(List<Fish> handlePoolList, List<Fish> objectPoolList) {
        for (Fish fishHnd : handlePoolList) {
            for (Fish fishObj : objectPoolList) {
                if (fishHnd == fishObj && fishObj != null) {
                    int index = handlePoolList.indexOf(fishHnd);
                    Rectangle rectangle = handlePool.getItems().get(index);
                    Point2D p1 = rectangle.localToScene(handlePool.getWidth(),
                                                      -(handlePool.getBoundsInParent().getMinY() + 2));

                    Circle c1 = new Circle(p1.getX(), p1.getY(), 1);

                    allocateFishPane.getChildren().add(c1);

                    index = objectPoolList.indexOf(fishObj);
                    Rectangle rectangle1 = objectPool.getItems().get(index);
                    Point2D p2 = rectangle1.localToScene(0,
                                                        -(objectPool.getBoundsInParent().getMinY() + 2));
                    Circle c2 = new Circle(p2.getX(), p2.getY(), 1);
                    allocateFishPane.getChildren().add(c2);
                    Line newLine = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                    allocateFishPane.getChildren().add(newLine);
                }
            }
        }
    }

    private ObservableList<Rectangle> handleLists(List<Fish> list) {
        List<Rectangle> rectangles = new ArrayList<>();
        double cellWidth = handlePool.getWidth() - 3;
        Rectangle r = null;
        for (Fish f : list) {
            if (f != null) {
                switch (f.getColour()) {
                    case "red":
                        r = new Rectangle(cellWidth, 5, Color.RED);
                        break;
                    case "blue":
                        r = new Rectangle(cellWidth, 5, Color.BLUE);
                        break;
                    case "yellow":
                        r = new Rectangle(cellWidth, 5, Color.YELLOW);
                        break;
                }
                r.setStroke(Color.BLACK);
                r.setStrokeWidth(1);
                rectangles.add(r);
            }
        }

        ObservableList<Rectangle> observableList = FXCollections.observableList(rectangles);
        return observableList;

    }

    public void setApp(MainApp app) {
        this.app = app;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initializing the alloc. controller");
    }
}
