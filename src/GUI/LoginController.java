/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Entities.Student;
import Entities.Teacher;
import Entities.User;
import Services.UserService;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Sabrina
 */
public class LoginController implements Initializable {

    @FXML
    private PasswordField tfpassword;
    @FXML
    private Button login;
    @FXML
    private Hyperlink forgotpwd;
    @FXML
    private TextField tfusername;

    public static User CurrentUser = new User();
    public static Student CurrentStudent = new Student();
    public static Teacher CurrentTeacher = new Teacher();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void Authentification(ActionEvent event) {
        try {
            UserService us = new UserService();
            User usr = new User();
            Student std = new Student();

            usr.setEmail(tfusername.getText());
            usr.setPassword(tfpassword.getText());
            boolean verify = us.Authentifier(usr.getEmail(), usr.getPassword());
            boolean verifyAd = false;

            if (verify) {
                try {
                    CurrentUser = us.AssignCurrentUser(usr.getEmail(), usr.getPassword());
                    CurrentStudent = us.AssignCurrentStudent(usr.getEmail(), usr.getPassword());
                    CurrentTeacher = us.AssignCurrentTeahcer(usr.getEmail(), usr.getPassword());
                    Stage primaryStage;

                    if ("Admin".equals(CurrentUser.getRole())) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminHome.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
//                        stage.initModality(Modality.APPLICATION_MODAL);
//                        stage.initStyle(StageStyle.UNDECORATED);
//                        stage.setTitle("Host an event");
                        stage.setScene(new Scene(root1));
                        stage.show();

                    } else if ("Student".equals(CurrentStudent.getRole())) {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("StudentHome.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root1));
                        stage.show();

                    } else if (("Teacher".equals(CurrentTeacher.getRole()))){
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLSubjectForTeacher.fxml"));
                        Parent root1 = (Parent) fxmlLoader.load();
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root1));
                        stage.show();
                    }

                    Stage CurrentStage = (Stage) login.getScene().getWindow();
                    CurrentStage.close();

                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Incorrect Username or Password " + usr.getEmail() + "!");
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void reinit(ActionEvent event) {
    }

}
