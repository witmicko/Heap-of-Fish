package app;

import javafx.event.EventHandler;
import javafx.scene.input.*;
import models.Fish;

/**
 * Created by michal on 20/04/2014.
 */
public class LocalVarView extends FishImageView {
    private FishImageView localVar;

    public LocalVarView(Fish fish) {
        super(fish);
    }

    public LocalVarView(Fish fish, double x, double y) {
        super(fish, x, y);
    }
    public LocalVarView(LocalVarView another){
        super(another);
    }

    public void setLocalVar(FishImageView fishImageView) {
        this.localVar = fishImageView;
    }


    public void setHandlers() {
        LocalVarView view = this;
        view.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("asasd");
                Dragboard db = view.startDragAndDrop(TransferMode.LINK);
                ClipboardContent content = new ClipboardContent();
                content.putString("");
                db.setContent(content);
                event.consume();
            }
        });
        view.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.LINK);
                event.consume();
            }
        });
    }
}
