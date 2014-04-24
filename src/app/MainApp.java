package app;

import gc.Heap;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import models.Fish;
import models.RedFish;
import models.YellowFish;
import sun.applet.Main;

import java.io.BufferedReader;
import java.util.ArrayList;

public class MainApp extends Application {
    Scene scene;
    Heap heap;
    AllocateFishController allocController;
    AssignReferencesController assignRefController;
    GarbageCollectController garbCollController;
    MainController mainController;

    @Override
    public void start(Stage primaryStage) throws Exception {
        heap = new Heap(70);

        FXMLLoader mainFxmlLoader = new FXMLLoader(MainApp.class.getResource("/res/sample.fxml"));
        Parent root = (Parent) mainFxmlLoader.load();

        allocController = (AllocateFishController) mainFxmlLoader.getNamespace().get("allocateFishTabController");
        allocController.setApp(this);

        assignRefController = (AssignReferencesController) mainFxmlLoader.getNamespace().get("assignRefsTabController");
        assignRefController.setApp(this);

        garbCollController = (GarbageCollectController) mainFxmlLoader.getNamespace().get("garbageCollectTabController");
        garbCollController.setApp(this);

        mainController = (MainController) mainFxmlLoader.getNamespace().get("controller");
        mainController.setApp(this);

        allocController.redraw(new ArrayList<Fish>());
        //customized css stylesheet
        scene = new Scene(root, 600, 400);
        String stylesheet = getClass().getResource("/res/stylesheet.css").toExternalForm();
        scene.getStylesheets().add(stylesheet);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
