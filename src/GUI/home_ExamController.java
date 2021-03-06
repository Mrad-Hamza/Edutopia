package GUI;

import Entities.*;
import Alert.AlertDialog;
import CalendarA.FullCalendarView;
import Services.Exam_Service;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DateStringConverter;

public class home_ExamController implements Initializable {

    @FXML
    private VBox vboxdrawer;
    @FXML
    private ImageView imagechange;
    @FXML
    private Button btn_Questions;
    private Button btn_Course;
    @FXML
    private Pane pnl_abonnement;
    @FXML
    private TextField txt_Seach;
    @FXML
    private TableView<Exam> tabview;
    @FXML
    private TableColumn<Exam, String> col_type;
    @FXML
    private TableColumn<Exam, Date> col_dateDebut;
    @FXML
    private TableColumn<Exam, Date> col_dateFin;
    @FXML
    private TableColumn<Exam, Date> col_created_date;
    @FXML
    private TableColumn<Exam, Date> col_archived_date;
    private TableColumn<Exam, Integer> col_id_subject;
    @FXML
    private Button btn_ajout;
    private Button btnBack;
    @FXML
    private DatePicker datefin;
    @FXML
    private ComboBox<String> combo_type;
    @FXML
    private DatePicker datedebut;

    private Exam_Service service = new Exam_Service();
    private TableColumn<Exam, String> col_btnArchiver;
    @FXML
    private Label SubjectName;
    @FXML
    private Label UserName;
    private int id_Subject;
    public static int id_subjectstatic;
    @FXML
    private Button CourseAction;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        search();
        Modifier();
        try {
            refreche();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        combo_type.getItems().addAll("DS", "EXAM", "TEST");
        combo_type.getSelectionModel().select(0);
        tabview.setEditable(true);
        col_btnArchiver = new TableColumn("Archiver");
        javafx.util.Callback<TableColumn<Exam, String>, TableCell<Exam, String>> cellFactory = new Callback<TableColumn<Exam, String>, TableCell<Exam, String>>() {
            public TableCell call(final TableColumn<Exam, String> param) {
                final TableCell<Exam, String> cell = new TableCell<Exam, String>() {
                    final Button btn = new Button("Archiver");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Exam u = getTableView().getItems().get(getIndex());
                                try {
                                    if (u.getArchived_Date() == null) {
                                        service.Archiver(u.getId());
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Archivage ");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Archivage avec success ");
                                        alert.showAndWait();
                                    } else {
                                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                        alert.setTitle("Erreur ! ");
                                        alert.setHeaderText(null);
                                        alert.setContentText("Deja archiver");
                                        alert.showAndWait();
                                    }
                                } catch (SQLException ex) {
                                }
                                try {
                                    refreche();
                                } catch (SQLException ex) {
                                }
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        col_btnArchiver.setCellFactory(cellFactory);
        tabview.getColumns().add(col_btnArchiver);
    }

    void initData(Subjectt c) throws SQLException {
        id_Subject = c.getId();
        SubjectName.setText(c.getId_Subject());
        id_subjectstatic = id_Subject;
        show();

    }

    public void show() throws SQLException {
        col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        col_type.setCellFactory(TextFieldTableCell.<Exam>forTableColumn());
        col_dateDebut.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        col_dateDebut.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        col_dateFin.setCellValueFactory(new PropertyValueFactory<>("finish_date"));
        col_dateFin.setCellFactory(TextFieldTableCell.forTableColumn(new DateStringConverter()));
        col_created_date.setCellValueFactory(new PropertyValueFactory<>("created_Date"));
        col_archived_date.setCellValueFactory(new PropertyValueFactory<>("archived_Date"));
        tabview.setItems(service.AfficherListExamMatiere(id_Subject));
    }

    @FXML
    private void handleClicks(ActionEvent event) throws IOException {

        if (event.getSource() == btn_Course) {
            Node node = (Node) event.getSource();

            Stage stage = (Stage) node.getScene().getWindow();
            //stage.setMaximized(true);
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/GUI/FXMLSubjectForTeacher.fxml")));
            stage.setScene(scene);
            stage.show();
        }
    }

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

    @FXML
    private void ajouter_Exam(ActionEvent event) throws SQLException {
        float res1 = 0;
        float res2 = 0;
        LocalDate d = LocalDate.now();
        Date date = java.sql.Date.valueOf(d);
        if (datefin.getValue() != null && datedebut.getValue() != null) {
            Date dd = new java.sql.Date(new Date(datedebut.getEditor().getText()).getTime());
            Date df = new java.sql.Date(new Date(datefin.getEditor().getText()).getTime());
            long diff1 = df.getTime() - dd.getTime();
            long diff = dd.getTime() - date.getTime();
            res1 = (diff / (1000 * 60 * 60 * 24));
            res2 = (diff1 / (1000 * 60 * 60 * 24));
        }

        if (datefin.getValue() == null || datedebut.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur Date ! ");
            alert.setHeaderText(null);
            alert.setContentText("V??rifier votre champs !");
            alert.showAndWait();
        } else if ((res1 < 0)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur Date ! ");
            alert.setHeaderText(null);
            alert.setContentText("V??rifier votre champs !");
            alert.showAndWait();
        } else if ((res2 < 0)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Erreur Date ! ");
            alert.setHeaderText(null);
            alert.setContentText("V??rifier votre champs !");
            alert.showAndWait();
        } else {
            java.sql.Date dd = java.sql.Date.valueOf(datedebut.getValue());
            java.sql.Date df = java.sql.Date.valueOf(datefin.getValue());

            Exam e = new Exam(combo_type.getSelectionModel().getSelectedItem(), dd, df, id_Subject, (java.sql.Date) dd, (java.sql.Date) dd);
            service.Ajouter(e);
            refreche();
//            AlertDialog.showNotification("Ajout", "Ajouter avec success", AlertDialog.image_checked);
        }
    }

    public void refreche() throws SQLException {
        show();
    }

    public void search() {
        txt_Seach.setOnKeyReleased(e
                -> {
            if (txt_Seach.getText().equals("")) {

                try {
                    refreche();
                } catch (SQLException ex) {
                }

            } else {
                try {
                    col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
                    col_dateDebut.setCellValueFactory(new PropertyValueFactory<>("start_date"));
                    col_dateFin.setCellValueFactory(new PropertyValueFactory<>("finish_date"));
                    col_created_date.setCellValueFactory(new PropertyValueFactory<>("created_Date"));
                    col_archived_date.setCellValueFactory(new PropertyValueFactory<>("archived_Date"));
                    tabview.getItems().clear();
                    tabview.setItems(service.serach(txt_Seach.getText()));

                } catch (SQLException ex) {
                }
            }
        }
        );

    }

    public void Modifier() {

        col_type.setOnEditCommit((TableColumn.CellEditEvent<Exam, String> event) -> {
            TablePosition<Exam, String> pos = event.getTablePosition();
            String type = event.getNewValue();
            int row = pos.getRow();
            Exam ac = event.getTableView().getItems().get(row);
            ac.setType(type);
            try {
                service.Modifier(ac, ac.getId());
            } catch (Exception ex) {
            }
        });

        col_dateDebut.setOnEditCommit((TableColumn.CellEditEvent<Exam, Date> event) -> {
            TablePosition<Exam, Date> pos = event.getTablePosition();
            Date dated = event.getNewValue();
            java.sql.Date dd = new java.sql.Date(dated.getTime());
            int row = pos.getRow();
            Exam ex = event.getTableView().getItems().get(row);
            ex.setStart_date(dd);
            try {
                service.Modifier(ex, ex.getId());
            } catch (SQLException ex1) {
                Logger.getLogger(home_ExamController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        });
        col_dateFin.setOnEditCommit((TableColumn.CellEditEvent<Exam, Date> event) -> {
            TablePosition<Exam, Date> pos = event.getTablePosition();
            Date dated = event.getNewValue();
            java.sql.Date dd = new java.sql.Date(dated.getTime());
            int row = pos.getRow();
            Exam ab = event.getTableView().getItems().get(row);
            ab.setFinish_date(dd);
            try {
                service.Modifier(ab, ab.getId());
            } catch (SQLException ex1) {
                Logger.getLogger(home_ExamController.class.getName()).log(Level.SEVERE, null, ex1);
            }
        });
    }

    @FXML
    private void AddQuestionAction(ActionEvent event) throws SQLException {
        if (tabview.getSelectionModel().getSelectedItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Attention !");
            alert.setHeaderText(null);
            alert.setContentText("selectionnez un examen ");
            alert.showAndWait();
        } else {
            Exam c = tabview.getSelectionModel().getSelectedItem();
            System.out.println(c.getId());
            Exam_Service fs = new Exam_Service();
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Home_Question.fxml"));
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Liste des examen");
                home_QuestionController controller = loader.getController();
                controller.initData(c);
                stage.show();
            } catch (IOException e) {
                System.err.println(String.format("Error: %s", e.getMessage()));
            }
        }
    }

    @FXML
    private void handleNoteExamen(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();
        //stage.setMaximized(true);
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/GUI/StatExamenNote.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void HandlePassageExamen(ActionEvent event) throws IOException {

        Node node = (Node) event.getSource();

        Stage stage = (Stage) node.getScene().getWindow();
        //stage.setMaximized(true);
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/GUI/Home_StatPassagExamen.fxml")));
        stage.setScene(scene);
        stage.show();
    }

    private void backAction(ActionEvent event) throws IOException {
        if (event.getSource() == btnBack) {
            Node node = (Node) event.getSource();

            Stage stage = (Stage) node.getScene().getWindow();
            //stage.setMaximized(true);
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/GUI/FXMLSubjectForTeacher.fxml")));
            stage.setScene(scene);
            stage.show();

        }
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

    private void CourseAction(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLSubjectForTeacher.fxml"));
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
    private void backAction(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLSubjectForTeacher.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
