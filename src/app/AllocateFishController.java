package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import models.BlueFish;
import models.Fish;
import models.RedFish;
import models.YellowFish;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialogs;


public class AllocateFishController implements Initializable {
    private MainApp app;
    @FXML ListView<Rectangle> handlePool;
    @FXML ListView<Rectangle> objectPool;
    @FXML AnchorPane allocateFishPane;

    @FXML
    public void newFish(MouseEvent event) {
        Fish newFish = null;
        switch (((Button) event.getSource()).getId()) {
            case "red":
                newFish = new RedFish();
                break;
            case "blue":
                newFish = new BlueFish();
                break;
            case "yellow":
                newFish = new YellowFish();
                break;
        }
        int fishSize = newFish.getClass().getDeclaredFields().length;
        boolean isObjectPoolFree = objectPool.getItems().size() < (objectPool.getHeight() / 5) - fishSize;
        if (isObjectPoolFree) {
            app.heap.addElement(newFish);
            List<Fish> handlePoolList = app.heap.getHandlePoolList();
            ObservableList<Rectangle> rects = handleLists(handlePoolList);
            handlePool.setItems(rects);
            List<Fish> objectPoolList = app.heap.getObjectPoolList();
            objectPool.setItems(handleLists(objectPoolList));

            drawLines2(handlePoolList, objectPoolList);
        } else {
            Dialogs.create()
                    .title("Looks like trouble Ted")
                    .masthead("Out of memory")
                    .message("No more objects can allocated in the heap")
                    .showError();
        }
    }

    private ObservableList<Rectangle> handleLists(List<Fish> list) {
        List<Rectangle> rectangles = new ArrayList<>();
        double cellWidth = handlePool.getWidth();

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
                r.setStrokeWidth(1.3);
                rectangles.add(r);
            }
        }
        return FXCollections.observableList(rectangles);
    }

    protected void drawLines2(List<Fish> handlePoolList,
                              List<Fish> objectPoolList) {
        List<Node> lines = new ArrayList<>();
        for (Node n : allocateFishPane.getChildren()) {
            if (n instanceof Line || n instanceof Circle) lines.add(n);
        }
        allocateFishPane.getChildren().removeAll(lines);

        for (Fish fishHnd : handlePoolList) {
            for (Fish fishObj : objectPoolList) {
                if ((fishHnd == fishObj) && (fishObj != null)) {
                    int index = handlePoolList.indexOf(fishHnd);
                    Rectangle rectangle = handlePool.getItems().get(index);
                    Point2D p1 = rectangle.localToScene(rectangle.getLayoutBounds().getMaxX(), -28);
                    Circle c1 = new Circle(p1.getX(), p1.getY(), 1);
                    allocateFishPane.getChildren().add(c1);

                    index = objectPoolList.indexOf(fishObj);
                    Rectangle rectangle1 = objectPool.getItems().get(index);
                    Point2D p2 = rectangle1.localToScene(rectangle1.getLayoutBounds().getMinX(), -28);
                    Circle c2 = new Circle(p2.getX(), p2.getY(), 1);

                    allocateFishPane.getChildren().add(c2);
                    Line newLine = new Line(p1.getX(), p1.getY(), p2.getX(), p2.getY());
                    allocateFishPane.getChildren().add(newLine);
                }
            }
        }
    }


    public void setApp(MainApp app) {
        this.app = app;
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("Initializing the alloc. controller");
    }
}
