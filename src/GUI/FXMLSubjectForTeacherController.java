package GUI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import CalendarA.FullCalendarView;
import Services.SubjectService;
import Services.TeacherService;
import Entities.Subjectt;
import Entities.Teacher;
import Entities.User;
import Entities.classe;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.YearMonth;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Mrad
 */
public class FXMLSubjectForTeacherController implements Initializable {

    @FXML
    private VBox vboxdrawer;
    @FXML
    private ImageView imagechange;
    @FXML
    private Label UserName;
    @FXML
    private TextField tfname;
    @FXML
    private ComboBox<String> cbclasse;
    @FXML
    private Button btnupdate;

    @FXML
    private TextField tfrech;
    @FXML
    private Button btnrech;
    @FXML
    private TableView<Subjectt> tvid;
    @FXML
    private TableColumn<Subjectt, String> tvname;
    @FXML
    private TableColumn<Subjectt, String> tvclasse;
    @FXML
    private Button btnconsultercours;

    ObservableList<Integer> ids = FXCollections.observableArrayList();

    TeacherService ts = new TeacherService();
    User current_user = LoginController.CurrentUser;
    @FXML
    private Label lblteacher;
    @FXML
    private Button btnconsulterexamen;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showSubjects();

        btnupdate.setDisable(true);
        classefxController cs = new classefxController();
        ObservableList<classe> elements = cs.getclasseList();
        ObservableList<String> strings = FXCollections.observableArrayList();
        for (classe c : elements) {
            strings.add(c.getName());
            ids.add(c.getId());
        }
        cbclasse.getItems().addAll(strings);
        System.out.println(ids);
        lblteacher.setText(current_user.getName() + " " + current_user.getLast_name());

// bech t3abi el combo box b liste ta les classes 
// label bech ta5ou current user name + lastname
    }

    private void showSubjects() {
        SubjectService ss = new SubjectService();
        tvid.setItems(ss.getSubjects());
        tvname.setCellValueFactory(new PropertyValueFactory("id_Subject"));
// bech nakhou el nom de classe bel id ta classe w bech n7oto f table view 
        tvclasse.setCellValueFactory(new PropertyValueFactory("nomClasse"));
    }

    @FXML
    private void HomeAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("TeacherHome.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void ClassAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("EnseignantClasse.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void CourseAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLSubjectForTeacher.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

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
    private void AccountAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLTeacherModify.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void ClaimAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ComplaintFront.fxml"));
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
    private void update(ActionEvent event) {
        SubjectService ss = new SubjectService();
        classefxController cs = new classefxController();
        Subjectt s = tvid.getSelectionModel().getSelectedItem();
        s.setId(ss.getIdSubject(s));
        System.out.println(ss.getIdSubject(s));
        s.setId_Subject(tfname.getText());
        classe c = cs.getClasseParID(ids.get(cbclasse.getSelectionModel().getSelectedIndex()));
        System.out.println(c.toString());
        s.setId_class(c.getId());
        s.setId_teacher(current_user.getId());
        s.setNomTeacher(current_user.getName() + " " + current_user.getLast_name());
        s.setNomClasse(c.getName());
        s.setLast_updated_by(1);
        s.setLast_update_date(java.sql.Date.valueOf(java.time.LocalDate.now()));
        ss.update(s);
        showSubjects();
    }

    private void remove(ActionEvent event) {
        Subjectt s = tvid.getSelectionModel().getSelectedItem();
        SubjectService ss = new SubjectService();
        s.setId(ss.getIdSubject(s));
        s.setArchived_by(2);
        s.setArchived_date(java.sql.Date.valueOf(java.time.LocalDate.now()));
        ss.remove(s);
        showSubjects();
    }

    private void add(ActionEvent event) {
        Subjectt s = new Subjectt();
        SubjectService ss = new SubjectService();
        if (!tfname.getText().matches("[A-Za-z-*\\s]+")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Nom de Sp??cialit?? n'est pas valide!");
            alert.showAndWait();
        } else {
            classefxController cs = new classefxController();
            classe c = cs.getClasseParID(ids.get(cbclasse.getSelectionModel().getSelectedIndex()));
            s.setNomClasse(c.getName());
            System.out.println(s.getNomClasse() + c.getName());
            s.setId_Subject(tfname.getText());
            s.setId_class(c.getId());
            s.setId_teacher(current_user.getId());
            s.setNomTeacher(current_user.getName() + " " + current_user.getLast_name());
// methode bech ta3tiha esm el classe w traja3lek el id mta3ha : lazem khedma ta aziz            
//            s.setId_class(cbclasse.getSelectionModel().getSelectedItem());

// 5edma hedhi lazemha el authetification w set id teacher bech ta5ou id ta teacher authentif??
//            s.setId_teacher("el user el authentifi??");
            s.setCreated_by(3);
            s.setCreated_date(java.sql.Date.valueOf(java.time.LocalDate.now()));
            ss.addSubject(s);
            System.out.println("1");
            s.setId(ss.getIdSubject(s));
            System.out.println("2");
            showSubjects();
        }
    }

    @FXML
    private void recherche(ActionEvent event) {
        SubjectService ss = new SubjectService();
        ObservableList<Subjectt> s = ss.recherche(tfrech.getText());
        int i = -1;
        int row = -1;
        if (s == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Aucun R??sultat Trouv??", ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.show();
        } else {
            for (Subjectt sub : s) {
                Teacher t = ts.getTeacherParSonId(sub.getId_teacher());
                classefxController cs = new classefxController();
                classe c = cs.getClasseParID(sub.getId_class());
                System.out.println(sub.getId_class());
                sub.setNomTeacher(t.getName() + " " + t.getLastname());
                sub.setNomClasse(c.getName());
            }
            tvid.setItems(s);
            tvname.setCellValueFactory(new PropertyValueFactory("id_Subject"));
// bech nakhou el nom de classe bel id ta classe w bech n7oto f table view 
            tvclasse.setCellValueFactory(new PropertyValueFactory("nomClasse"));
        }
    }

    @FXML
    private void deselect(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE) {
            tvid.getSelectionModel().clearSelection();
            tfname.clear();
            btnupdate.setDisable(true);
        }
    }

    @FXML
    private void select(MouseEvent event) {
        Subjectt s = tvid.getSelectionModel().getSelectedItem();
        tfname.setText(s.getId_Subject());
        btnupdate.setDisable(false);
    }

    @FXML
    private void AddAction(ActionEvent event) throws SQLException {
        if (tvid.getSelectionModel().getSelectedItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attention !");
            alert.setHeaderText(null);
            alert.setContentText("selectionnez une mati??re ");
            alert.showAndWait();
        } else {
            Subjectt c = tvid.getSelectionModel().getSelectedItem();
            SubjectService fs = new SubjectService();
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Home_Course.fxml"));
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Liste des courses");
                home_courseController controller = loader.getController();
                controller.initData(c);
                stage.show();
            } catch (IOException e) {
                System.err.println(String.format("Error: %s", e.getMessage()));
            }
        }
    }

    @FXML
    private void AddExamenAction(ActionEvent event) throws SQLException {
        if (tvid.getSelectionModel().getSelectedItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attention !");
            alert.setHeaderText(null);
            alert.setContentText("selectionnez une mati??re ");
            alert.showAndWait();
        } else {
            Subjectt c = tvid.getSelectionModel().getSelectedItem();
            System.out.println(c.getId());
            SubjectService fs = new SubjectService();
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Home_Exam.fxml"));
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Liste des courses");
                home_ExamController controller = loader.getController();
                controller.initData(c);
                stage.show();
            } catch (IOException e) {
                System.err.println(String.format("Error: %s", e.getMessage()));
            }
        }
    }

    @FXML
    private void chargerCalendarHandle(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FullCalendar.fxml"));
        Parent root = loader.load();

        FullCalendarController controller = loader.getController();
        VBox f = new FullCalendarView(YearMonth.now()).getView();
        controller.calendarPane.getChildren().add(f);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
