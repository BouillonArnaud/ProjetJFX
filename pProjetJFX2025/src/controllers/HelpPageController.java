package controllers;

import javafx.stage.Stage;
import model.HelpPageModel;
import views.HelpPageView;

public class HelpPageController {
    private final HelpPageModel model;
    private final HelpPageView view;

    public HelpPageController(Stage stage) {
        this.model = new HelpPageModel();
        this.view = new HelpPageView(model, stage);
    }
}