/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Activity;
import Entities.Course;
import Services.ActivityService;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ADMIN
 */
public class List_Activity_UserController implements Initializable {

    @FXML
    private VBox vboxdrawer;
    @FXML
    private ImageView imagechange;
    @FXML
    private Label UserName;
    @FXML
    private Button btn_Course1;
    @FXML
    private Pane pnl_abonnement;
    @FXML
    private Label CourseName;
    @FXML
    private TableView<Activity> TableView;
    @FXML
    private TableColumn<Activity, String> Name;
    @FXML
    private TableColumn<Activity, String> Deadline;
    @FXML
    private TextField txtSearch;
    private Path to;
    private Path from;
    private Path fromUpdated;
    private Path removePath;
    File file = null;
    int id_Course;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    
    void initData(Course c) {
        CourseName.setText(c.getName());
        UserName.setText(c.getName());
        id_Course = c.getId();
        showActivities();
    }

    public void showActivities() {
        ActivityService as = new ActivityService();
        ArrayList<Activity> al = as.getAvailableActivitiesListByIdCourse(id_Course);
        ObservableList<Activity> oL = FXCollections.observableArrayList(al);
        Name.setCellValueFactory(new PropertyValueFactory("name"));
        Deadline.setCellValueFactory(new PropertyValueFactory("deadline"));
        TableView.setItems(oL);
    }

    @FXML
    private void HomeAction(ActionEvent event) {
    }


    @FXML
    private void CourseAction(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Home_Course.fxml"));
            stage.setScene(new Scene(loader.load()));
            home_courseController controller = loader.getController();
            stage.show();
        } catch (IOException e) {
            System.err.println(String.format("Error: %s", e.getMessage()));
        }
    }

    @FXML
    private void ExamAction(ActionEvent event) throws IOException {
              Parent root = FXMLLoader.load(getClass().getResource("Front_ChargerExamen.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void CalendarAction(ActionEvent event) {
    }

    @FXML
    private void Co_StudyingAction(ActionEvent event) {
    }

    @FXML
    private void AccountAction(ActionEvent event) {
    }

    @FXML
    private void ClaimAction(ActionEvent event) {
    }

    @FXML
    private void DisconnectionAction(ActionEvent event) {
    }


    @FXML
    private void SearchAction(ActionEvent event) throws SQLException {
        if (txtSearch.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attention !");
            alert.setHeaderText(null);
            alert.setContentText("Le nom est vide saisir un nom ");
            alert.showAndWait();
        } else {
            List<Activity> al = new ArrayList();
            ActivityService as = new ActivityService();
            al = as.searchActivity(txtSearch.getText(), id_Course);
            ObservableList<Activity> oL = FXCollections.observableArrayList(al);
            TableView.setItems(oL);
            Name.setCellValueFactory(new PropertyValueFactory("name"));
            Deadline.setCellValueFactory(new PropertyValueFactory("deadline"));
        }
    }

    @FXML
    private void DetailsAction(ActionEvent event) {
    }

    @FXML
    private void ListWork_DoneAction(ActionEvent event) {
    }


    @FXML
    private void RefreshAction(ActionEvent event) {
        showActivities();
    }

    @FXML
    private void BackAction(ActionEvent event) {
        Node node = (Node) event.getSource();
        Stage oldStage = (Stage) node.getScene().getWindow();
        oldStage.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("List_Course.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            System.err.println(String.format("Error: %s", e.getMessage()));
        }
    }
    
}
