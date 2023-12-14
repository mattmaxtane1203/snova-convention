package view;

import java.util.List;

import controller.NavigationController;
import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class AdminFansPage {
    
    Scene mainScene;
    
    BorderPane fansPane;
    
    VBox listContainer;
    
    Label title, response;
    
    Button adminHomeBtn, deleteBtn;
    
    List<User> fanList;
    ObservableList<User> fanOL;
    
    TableView<User> tableView;
    TableColumn<User, Integer> userIDCol;
    TableColumn<User, String> usernameCol;
    TableColumn<User, String> emailCol;
    TableColumn<User, String> passwordCol;
    TableColumn<User, Void> deleteCol;

    private void init(Stage stage, User currentAdmin) {
        tableView = new TableView<>();
        
        userIDCol = new TableColumn<>("UserID");
        usernameCol = new TableColumn<>("Username");
        emailCol = new TableColumn<>("Email");
        passwordCol = new TableColumn<>("Password");
        deleteCol = new TableColumn<>("Delete");
        
        userIDCol.setCellValueFactory(new PropertyValueFactory<>("UserID"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("Username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
        passwordCol.setCellValueFactory(new PropertyValueFactory<>("Password"));
        
        deleteCol.setCellFactory(col -> new TableCell<User, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction((ActionEvent event) -> {
                    User user = getTableView().getItems().get(getIndex());
                    String res = UserController.deleteFan(user.getUserID());

                    if (!res.equals("User successfully deleted")) {
                        response.setText(res);
                    }
                    
                    new AdminFansPage(stage, currentAdmin);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        tableView.getColumns().addAll(userIDCol, usernameCol, emailCol, passwordCol, deleteCol);

        fanList = UserController.getAllUserInRole("Fan");
        fanOL = FXCollections.observableArrayList(fanList);
        tableView.setItems(fanOL);

        fansPane = new BorderPane();

        listContainer = new VBox();

        title = new Label("View All Fans");

        adminHomeBtn = new Button("Back to Home");

        mainScene = new Scene(fansPane, 600, 600);
    }

    private void layouting() {
        listContainer.getChildren().addAll(title, tableView, adminHomeBtn);
        listContainer.setAlignment(Pos.CENTER);

        fansPane.setCenter(listContainer);
    }

    private void styling() {

    }

    private void actions(Stage stage, User currentAdmin) {
    	NavigationController.navigateAdminHomePage(adminHomeBtn, stage, currentAdmin);
    }

    public AdminFansPage(Stage stage, User currentAdmin) {
        init(stage, currentAdmin);
        styling();
        layouting();
        actions(stage, currentAdmin);
        stage.setScene(mainScene);
    }
}
