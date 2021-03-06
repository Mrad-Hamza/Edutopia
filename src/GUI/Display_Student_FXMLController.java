/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import CalendarA.FullCalendarView;
import Entities.Student;
import Entities.User;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import Services.StudentService;
import java.io.IOException;
import java.time.YearMonth;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableColumn.CellEditEvent;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author rayen
 */
public class Display_Student_FXMLController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    StudentService ps;
    @FXML
    private StackPane StackPane;
    @FXML
    private StackPane StackPane1;
    @FXML
    private Button email;
    @FXML
    private VBox vboxdrawer;
    @FXML
    private ImageView imagechange;
    @FXML
    private Label UserName;
    @FXML
    private Button btn_Course1;
    private Label mainmenu_txt;
    @FXML
    private ImageView mainmenu_icon;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ps = new StudentService();

        // role table view
        JFXTreeTableColumn<User, String> role = new JFXTreeTableColumn<>("Role");
        role.setPrefWidth(150);
        role.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                return new SimpleStringProperty(param.getValue().getValue().getRole());
            }
        });

        //making new editable role text field
        role.setCellFactory((TreeTableColumn<User, String> param) -> {
            return new GenericEditableTreeTableCell<>(
                    new TextFieldEditorBuilder());
        });
        //setting the new value for editable role text field
        role.setOnEditCommit((CellEditEvent<User, String> t) -> {
            int id = t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getId();
            String newValue = t.getNewValue();

            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition()
                            .getRow())
                    .getValue().setRole(t.getNewValue());
            ps.editStudent(id, "role", newValue);
        });

        // name table view
        JFXTreeTableColumn<User, String> name = new JFXTreeTableColumn<>("Pr??nom");
        name.setPrefWidth(150);
        name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                return new SimpleStringProperty(param.getValue().getValue().getName());
            }
        });
        //making new editable name text field
        name.setCellFactory((TreeTableColumn<User, String> param) -> {
            return new GenericEditableTreeTableCell<>(
                    new TextFieldEditorBuilder());
        });
        //setting the new value for editable name text field
        name.setOnEditCommit((CellEditEvent<User, String> t) -> {
            int id = t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getId();
            String newValue = t.getNewValue();

            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition()
                            .getRow())
                    .getValue().setName(t.getNewValue());
            ps.editStudent(id, "name", newValue);
        });

        // last_name table view
        JFXTreeTableColumn<User, String> last_name = new JFXTreeTableColumn<>("Nom");
        last_name.setPrefWidth(150);
        last_name.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                return new SimpleStringProperty(param.getValue().getValue().getLast_name());
            }
        });
        //making new editable last_name text field
        last_name.setCellFactory((TreeTableColumn<User, String> param) -> {
            return new GenericEditableTreeTableCell<>(
                    new TextFieldEditorBuilder());
        });
        //setting the new value for editable last_name text field
        last_name.setOnEditCommit((CellEditEvent<User, String> t) -> {
            int id = t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getId();
            String newValue = t.getNewValue();

            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition()
                            .getRow())
                    .getValue().setLast_name(t.getNewValue());
            ps.editStudent(id, "last_name", newValue);
        });

        // cin table view
        JFXTreeTableColumn<User, String> cin = new JFXTreeTableColumn<>("CIN");
        cin.setPrefWidth(150);
        cin.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                return new SimpleStringProperty(Integer.toString(param.getValue().getValue().getCin()));
            }
        });
        //making new editable cin text field
        cin.setCellFactory((TreeTableColumn<User, String> param) -> {
            return new GenericEditableTreeTableCell<>(
                    new TextFieldEditorBuilder());
        });
        //setting the new value for editable cin text field
        cin.setOnEditCommit((CellEditEvent<User, String> t) -> {
            int id = t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getId();
            String newValue = t.getNewValue();

            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition()
                            .getRow())
                    .getValue().setCin(Integer.parseInt(t.getNewValue()));
            ps.editStudent(id, "cin", newValue);
        });

        // email table view
        JFXTreeTableColumn<User, String> email = new JFXTreeTableColumn<>("E-Mail");
        email.setPrefWidth(150);
        email.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                return new SimpleStringProperty(param.getValue().getValue().getEmail());
            }
        });//making new editable email text field
        email.setCellFactory((TreeTableColumn<User, String> param) -> {
            return new GenericEditableTreeTableCell<>(
                    new TextFieldEditorBuilder());
        });
        //setting the new value for editable email text field
        email.setOnEditCommit((CellEditEvent<User, String> t) -> {
            int id = t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getId();
            String newValue = t.getNewValue();

            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition()
                            .getRow())
                    .getValue().setEmail(t.getNewValue());
            ps.editStudent(id, "email", newValue);
        });

        // phone_number table view
        JFXTreeTableColumn<User, String> phone_number = new JFXTreeTableColumn<>("Num??ro de T??lephone");
        phone_number.setPrefWidth(150);
        phone_number.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                return new SimpleStringProperty(Integer.toString(param.getValue().getValue().getPhone_number()));
            }
        });
        //making new editable phone num text field
        phone_number.setCellFactory((TreeTableColumn<User, String> param) -> {
            return new GenericEditableTreeTableCell<>(
                    new TextFieldEditorBuilder());
        });

        //setting the new value for editable phone num text field
        phone_number.setOnEditCommit((CellEditEvent<User, String> t) -> {
            int id = t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getId();
            String newValue = t.getNewValue();

            t.getTreeTableView()
                    .getTreeItem(t.getTreeTablePosition()
                            .getRow())
                    .getValue().setPhone_number(Integer.parseInt(t.getNewValue()));
            ps.editStudent(id, "phone_number", newValue);
        });

        //  birth_date table view
        JFXTreeTableColumn<User, String> birth_date = new JFXTreeTableColumn<>("Date de naissance");
        birth_date.setPrefWidth(150);
        birth_date.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String> param) {
                return new SimpleStringProperty(String.valueOf(param.getValue().getValue().getBirth_date()));
            }
        });

        //making new editable brith date text field
        birth_date.setCellFactory((TreeTableColumn<User, String> param) -> {
            return new GenericEditableTreeTableCell<>(
                    new TextFieldEditorBuilder());
        });
        //setting the new value for editable birth date text field
        birth_date.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
            @Override
            public void handle(CellEditEvent<User, String> t) {
                try {
                    int id = t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue().getId();
                    String newValue = t.getNewValue();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
                    java.util.Date date = sdf.parse(t.getNewValue().toString());
                    java.sql.Date sqlDate = new java.sql.Date(date.getTime());

                    t.getTreeTableView()
                            .getTreeItem(t.getTreeTablePosition()
                                    .getRow())
                            .getValue().setBirth_date(sqlDate);
                    ps.editStudent(id, "birth_date", newValue);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }
        });

        List<Student> myLst;
        myLst = ps.listStudent();
        ObservableList<User> students = FXCollections.observableArrayList();

        myLst.forEach(p -> students.add(p));
        JFXTreeTableView<User> treeview = new JFXTreeTableView<>();
        final TreeItem<User> root = new RecursiveTreeItem<User>(students, RecursiveTreeObject::getChildren);
        treeview.getColumns().setAll(name, last_name, role, cin, email, phone_number, birth_date);
        treeview.setRoot(root);
        treeview.setShowRoot(false);
        treeview.setEditable(true);

        //declarer la button supprimer
        JFXButton DltBtn = new JFXButton("Supprimer");
        DltBtn.setLayoutY(410D);
        DltBtn.setTextFill(Paint.valueOf("#FFFFFF"));
        DltBtn.setStyle("-fx-background-color: #0367b9");
        DltBtn.setRipplerFill(Paint.valueOf("#FFFFFF"));
        DltBtn.setButtonType(JFXButton.ButtonType.RAISED);
        DltBtn.setOnAction(new EventHandler<ActionEvent>() {

            //eventHandler de la button supprimer
            @Override
            public void handle(ActionEvent event) {
                Dialog confirmation = new Dialog();
                GridPane grid2 = new GridPane();
                Label l1 = new Label("Supprimer ??tudiant?");
                grid2.add(l1, 2, 2);
                confirmation.setTitle("Confirmation de suppression!");
                confirmation.getDialogPane().setContent(grid2);
                ButtonType Confi = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
                ButtonType Ann = new ButtonType("Non", ButtonBar.ButtonData.OK_DONE);
                confirmation.getDialogPane().getButtonTypes().add(Confi);
                confirmation.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
                confirmation.setResultConverter(new Callback<ButtonType, User>() {
                    @Override
                    public User call(ButtonType param) {
                        if (param == Confi) {
                            User p = treeview.getSelectionModel().getSelectedItem().getValue();
                            ps.deleteStudent((Student) p);
                            Button cancelButton = (Button) confirmation.getDialogPane().lookupButton(ButtonType.CANCEL);
                            cancelButton.fire();
                            initialize(url, rb);
                        }
                        return null;
                    }
                });
                confirmation.showAndWait();
            }
        });

        //affichage dans AnchorPane en passant la r??sultat de tableview et la button de suppression
        StackPane.getChildren().addAll(DltBtn);
        StackPane1.getChildren().addAll(treeview);

    }

    private void on_return_button(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Add_Student_FXML.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void on_email_button(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SendMail.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

  
    @FXML
    private void HomeAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AdminHome.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void UserAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UserAddPicker.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void DepartmentAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDepartment.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void ClassAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AdminClasse.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void CourseAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLSubjectForAdmin.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void CalendarAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FullCalendar.fxml"));
        Parent root = loader.load();
        FullCalendarController controller = loader.getController();
        VBox f = new FullCalendarView(YearMonth.now()).getView();
        controller.calendarPane.getChildren().add(f);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void Co_StudyingAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("List_CoStudying.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void AccountAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLAdminModify.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void ClaimAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ComplaintAdd.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void DisconnectionAction(ActionEvent event) {
        Dialog confirmation = new Dialog();
        GridPane grid2 = new GridPane();
        Label l1 = new Label("D??cnnecter ?");
        grid2.add(l1, 2, 2);
        confirmation.setTitle("Confirmation");
        confirmation.getDialogPane().setContent(grid2);
        ButtonType Confi = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType Ann = new ButtonType("Non", ButtonBar.ButtonData.OK_DONE);
        confirmation.getDialogPane().getButtonTypes().add(Confi);
        confirmation.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        confirmation.setResultConverter(new Callback<ButtonType, User>() {
            @Override
            public User call(ButtonType param) {
                if (param == Confi) {
                    Parent root = null;
                    try {
                        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                    } catch (IOException ex) {
                        Logger.getLogger(Add_Student_FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Scene scene = new Scene(root);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
                }

                return null;
            }
        });
        confirmation.showAndWait();
    }

    @FXML
    private void mm_hover(MouseEvent event) {
        mainmenu_icon.setCursor(Cursor.HAND);
    }

    @FXML
    private void main_menu_clicked(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("UserAddPicker.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

}
