/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import IServices.ITeacher;
import Entities.Teacher;
import Utils.DataBaseConnection;
import java.sql.Connection;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/**
 *
 * @author Mrad
 */
public class TeacherService implements ITeacher {
    static Connection con ;
    ObservableList<Teacher> os = FXCollections.observableArrayList();
    public TeacherService(){
        con = DataBaseConnection.getInstance().getConnection();
    }
    
    public void addTeacher(Teacher t) {
    try {
            String requete = "INSERT INTO `user`(`name`, `last_name`, `role`, `cin`, `email`, `password`, `phone_number`, `birth_date`,`depId`, `subjects`, `created_by`, `created_date`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(requete);
            pst.setString(3, "teacher");
            pst.setString(1, t.getName());
            pst.setString(2, t.getLastname());
            pst.setInt(4, (int) t.getCin());
            pst.setString(5, t.getEmail());
            pst.setString(6, t.getPassword());
            pst.setInt(7, t.getPhone_number());
            pst.setDate(8, (Date) t.getBirth_date());
            pst.setInt(9, t.getDepId());
            pst.setString(10, t.getSubject());
            pst.setInt(11, t.getCreated_by());
            pst.setDate(12, t.getCreated_date());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    @Override
    public int getIdTeacher(Teacher t) {
        int id=0;
        try {
                String query="SELECT * FROM `user` WHERE name=? and last_name=?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, t.getName());
                ps.setString(2, t.getLastname());
                ResultSet rs;
                rs = ps.executeQuery();
                while(rs.next()) {
                    id=rs.getInt("id");
                }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }
    
    @Override
    public void remove(Teacher t) {
        try {
            String query="UPDATE `user` SET `archived_by`=?,`archived_date`=?,`status`=? WHERE id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, t.getArchived_by());
            ps.setDate(2, t.getArchived_date());
            ps.setString(3, "archived");
            ps.setInt(4, t.getId());
            ps.executeUpdate();
            System.out.println("removed succesfully");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void update(Teacher t) {
        try {
            String query="UPDATE `user` SET `name`=?,`last_name`=?,`cin`=?,`email`=?,`password`=?,`phone_number`=?,`birth_date`=?,`depId`=?,`subjects`=?,`last_updated_by`=?,`last_updated_date`=? WHERE id=?";
            PreparedStatement pst = con.prepareStatement(query);
            pst.setString(1, t.getName());
            pst.setString(2, t.getLastname());
            pst.setInt(3, (int) t.getCin());
            pst.setString(4, t.getEmail());
            pst.setString(5, t.getPassword());
            pst.setInt(6, t.getPhone_number());
            pst.setDate(7, (Date) t.getBirth_date());
            pst.setInt(8, t.getDepId());
            pst.setString(9, t.getSubject());
            pst.setInt(10, t.getLast_updated_by());
            pst.setDate(11, t.getLast_update_date());
            pst.setInt(12, t.getId());
            pst.executeUpdate();
            System.out.println("dep updated succesfully");
        } catch (SQLException ex) {
            Logger.getLogger(TeacherService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Teacher getTeacher(String str) 
        
    { 
        Teacher t = new Teacher();
        boolean check = false;
        try {
            String query = "select * from user where name=? or last_name=? or email=? or cin=? or phone_number=? or birth_date=? and role='Teacher'";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, str);
            ps.setString(2, str);
            System.out.println("abay");
            if (str.matches("[0-9]+")) {
                ps.setInt(4, Integer.parseInt(str));
            }
            else {
                ps.setNull(4, java.sql.Types.INTEGER);
            }
            System.out.println("test");
            ps.setString(3, str);
            if (str.matches("[0-9]+")) {
                ps.setInt(5, Integer.parseInt(str));
            }
            else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            if (isValidDate(str)) {
                ps.setDate(6, Date.valueOf(str));
            }
            else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(!"archived".equals(rs.getString("status"))) {
                    check = true;
                    t.setId(rs.getInt("id"));
                    t.setName(rs.getString("name"));
                    t.setLastname(rs.getString("last_name"));
                    t.setCin(rs.getLong("cin"));
                    t.setPhone_number(rs.getInt("phone_number"));
                    t.setEmail(rs.getString("email"));
                    t.setPassword(rs.getString("password"));
                    t.setBirth_date(rs.getDate("birth_date"));
                    t.setDepId(rs.getInt("depId"));
                    t.setSubject(rs.getString("subjects"));
                    t.setCreated_by(rs.getInt("created_by"));
                    t.setCreated_date(rs.getDate("created_date"));
                    t.setLast_update_date(rs.getDate("last_updated_date"));
                    t.setLast_updated_by(rs.getInt("last_updated_by"));
                    t.setArchived_by(rs.getInt("archived_by"));
                    t.setArchived_date(rs.getDate("archived_date"));
                    System.out.println(t.toString());
                }
            }
        } catch (SQLException ex) { 
            ex.printStackTrace();
        }
        if (check == true) {
                return t;
            }
            else
                return null;
    }
    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
    public List<Teacher> getTeachers()  
    { 
        List<Teacher> ld = new ArrayList();
        try {
            String query = "select * from user where role='Teacher'";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(!"archived".equals(rs.getString("status"))) {
                    Teacher t = new Teacher();
                    DepartmentService dc = new DepartmentService();
                    t.setId(rs.getInt("id"));
                    t.setName(rs.getString("name"));
                    t.setLastname(rs.getString("last_name"));
                    t.setCin(rs.getLong("cin"));
                    t.setPhone_number(rs.getInt("phone_number"));
                    t.setEmail(rs.getString("email"));
                    t.setPassword(rs.getString("password"));
                    t.setBirth_date(rs.getDate("birth_date"));
                
                    t.setDepId(rs.getInt("depId"));
                    t.setSubject(rs.getString("subjects"));
                    t.setCreated_by(rs.getInt("created_by"));
                    t.setCreated_date(rs.getDate("created_date"));
                    t.setLast_update_date(rs.getDate("last_updated_date"));
                    t.setLast_updated_by(rs.getInt("last_updated_by"));
                    t.setArchived_by(rs.getInt("archived_by"));
                    t.setArchived_date(rs.getDate("archived_date"));
                    ld.add(t);}
            } 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return ld;
    }

    @Override
    public ObservableList<Teacher> getTeachersObs() {
        try {
            String query = "select * from user where role='Teacher'";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(!"archived".equals(rs.getString("status"))) {
                    Teacher t = new Teacher();
                    t.setId(rs.getInt("id"));
                    t.setName(rs.getString("name"));
                    t.setLastname(rs.getString("last_name"));
                    t.setCin(rs.getLong("cin"));
                    t.setPhone_number(rs.getInt("phone_number"));
                    t.setEmail(rs.getString("email"));
                    t.setPassword(rs.getString("password"));
                    t.setBirth_date(rs.getDate("birth_date"));
                    t.setDepId(rs.getInt("depId"));
                    t.setSubject(rs.getString("subjects"));
                    t.setCreated_by(rs.getInt("created_by"));
                    t.setCreated_date(rs.getDate("created_date"));
                    t.setLast_update_date(rs.getDate("last_updated_date"));
                    t.setLast_updated_by(rs.getInt("last_updated_by"));
                    t.setArchived_by(rs.getInt("archived_by"));
                    t.setArchived_date(rs.getDate("archived_date"));
                    os.addAll(t);
                }
            } 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return os;
    }
    public ObservableList<Teacher> getTeacherSelonId(int id) {
        try {
            String query = "select * from user where depId=? and role='Teacher'";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(!"archived".equals(rs.getString("status"))) {
                    Teacher t = new Teacher();
                    t.setId(rs.getInt("id"));
                    t.setName(rs.getString("name"));
                    t.setLastname(rs.getString("last_name"));
                    t.setCin(rs.getLong("cin"));
                    t.setPhone_number(rs.getInt("phone_number"));
                    t.setEmail(rs.getString("email"));
                    t.setPassword(rs.getString("password"));
                    t.setBirth_date(rs.getDate("birth_date"));
                    t.setDepId(rs.getInt("depId"));
                    t.setSubject(rs.getString("subjects"));
                    t.setCreated_by(rs.getInt("created_by"));
                    t.setCreated_date(rs.getDate("created_date"));
                    t.setLast_update_date(rs.getDate("last_updated_date"));
                    t.setLast_updated_by(rs.getInt("last_updated_by"));
                    t.setArchived_by(rs.getInt("archived_by"));
                    t.setArchived_date(rs.getDate("archived_date"));
                    os.addAll(t);
                }
            } 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return os;
    }
    @Override
    public Boolean exist(int id) {
        Boolean check=false;
        try {
            String query="select * from user where role='Teacher'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                if(!"archived".equals(rs.getString("status"))) {
                    if (rs.getInt("id")==id) {
                        check=true;
                    }
                }
            }   
        } 
        catch (SQLException ex) {
                ex.printStackTrace();
        }   
        return check;
    }

    @Override
    public ObservableList<Teacher> getHistoriqueTeachers() {
        try {
            String query = "select * from user where role='Teacher'";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                    Teacher t = new Teacher();
                    t.setId(rs.getInt("id"));
                    t.setName(rs.getString("name"));
                    t.setLastname(rs.getString("last_name"));
                    t.setCin(rs.getLong("cin"));
                    t.setPhone_number(rs.getInt("phone_number"));
                    t.setEmail(rs.getString("email"));
                    t.setPassword(rs.getString("password"));
                    t.setBirth_date(rs.getDate("birth_date"));
                    t.setDepId(rs.getInt("depId"));
                    t.setSubject(rs.getString("subjects"));
                    t.setCreated_by(rs.getInt("created_by"));
                    t.setCreated_date(rs.getDate("created_date"));
                    t.setLast_update_date(rs.getDate("last_updated_date"));
                    t.setLast_updated_by(rs.getInt("last_updated_by"));
                    t.setArchived_by(rs.getInt("archived_by"));
                    t.setArchived_date(rs.getDate("archived_date"));
                    os.addAll(t);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return os;
    }

    public int nbEnseignantselonDepart(int j) {
        int i=-1;
        try {
            String query="select count(*) from user where depId=? and (status is null or status != 'archived') and role='Teacher'";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, j);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                i=rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return i;
    }
    public Teacher EnseigantAvecPlusCours(String str) {
        Teacher t = new Teacher();
        try {    
            String query="select * from user where department like '%' ? '%' and role='Teacher'and (status is null or status != 'archived') ORDER by subjects ASC LIMIT 1";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, str);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                t.setId(rs.getInt("id"));
                t.setName(rs.getString("name"));
                t.setLastname(rs.getString("last_name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;        
    }
    public Teacher getTeacherParSonId(int id) {
        Teacher t = new Teacher();
        try {    
            String query="select * from user where id=? and role='Teacher'";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(!"archived".equals(rs.getString("status"))) {
                    t.setId(rs.getInt("id"));
                    t.setName(rs.getString("name"));
                    t.setLastname(rs.getString("last_name"));
                    t.setCin(rs.getLong("cin"));
                    t.setPhone_number(rs.getInt("phone_number"));
                    t.setEmail(rs.getString("email"));
                    t.setPassword(rs.getString("password"));
                    t.setDepId(rs.getInt("depId"));
                    t.setBirth_date(rs.getDate("birth_date"));
                    t.setSubject(rs.getString("subjects"));
                    t.setCreated_by(rs.getInt("created_by"));
                    t.setCreated_date(rs.getDate("created_date"));
                    t.setLast_update_date(rs.getDate("last_updated_date"));
                    t.setLast_updated_by(rs.getInt("last_updated_by"));
                    t.setArchived_by(rs.getInt("archived_by"));
                    t.setArchived_date(rs.getDate("archived_date"));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(TeacherService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return t;  
    }
    public ObservableList<Teacher> recherche(String st) {
        try {
            String query = "select * from user where name like '%"+st+"%' or last_name like '%"+st+"%' or cin like '%"+st+"%' or phone_number like '%"+st+"%' or email like '%"+st+"%' or birth_date like '%"+st+"%' or department like '%"+st+"%' and role='Teacher'";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if(!"archived".equals(rs.getString("status"))) {
                    Teacher t = new Teacher();
                    t.setId(rs.getInt("id"));
                    t.setName(rs.getString("name"));
                    t.setLastname(rs.getString("last_name"));
                    t.setCin(rs.getLong("cin"));
                    t.setPhone_number(rs.getInt("phone_number"));
                    t.setEmail(rs.getString("email"));
                    t.setPassword(rs.getString("password"));
                    t.setBirth_date(rs.getDate("birth_date"));
                    t.setDepId(rs.getInt("depId"));
                    t.setSubject(rs.getString("subjects"));
                    t.setCreated_by(rs.getInt("created_by"));
                    t.setCreated_date(rs.getDate("created_date"));
                    t.setLast_update_date(rs.getDate("last_updated_date"));
                    t.setLast_updated_by(rs.getInt("last_updated_by"));
                    t.setArchived_by(rs.getInt("archived_by"));
                    t.setArchived_date(rs.getDate("archived_date"));
                    os.addAll(t);
                }
            } 
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return os;
    }
}

