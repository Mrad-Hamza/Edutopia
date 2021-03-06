/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entities.Activity;
import IServices.IActivity;
import Utils.DataBaseConnection;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author ADMIN
 */
public class ActivityService implements IActivity {

    Connection conn;
    ObservableList<Activity> oL = FXCollections.observableArrayList();
    ArrayList<Activity> la = new ArrayList();

    public ActivityService() {
        conn = DataBaseConnection.getInstance().getConnection();
    }

    @Override
    public int getId(Activity act) {
        int id = 0;
        try {
            String query = "SELECT * FROM `activity` WHERE name=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, act.getName());
            ResultSet rs;
            rs = ps.executeQuery();
            while (rs.next()) {
                id = rs.getInt("id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return id;
    }

    @Override
    public void add(Activity activity) {
        try {
            String requete = "INSERT INTO activity (name,deadline,work_todo,id_course,status,created_by,ceated_date) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setString(1, activity.getName());
            pst.setDate(2, (Date) activity.getDeadline());
            pst.setString(3, activity.getWork_todo());
            pst.setInt(4, activity.getId_Course());
            pst.setString(5, activity.getStatus());
            pst.setInt(6, activity.getCreated_by());
            pst.setDate(7, (Date) activity.getCreated_date());
            pst.executeUpdate();
            System.out.println("Added succesfully");
        } catch (SQLException excep) {
            System.err.println(excep.getMessage());
        }

    }

    @Override
    public void remove(int id) {
        try {
            String req = "UPDATE `activity` SET `archived_by`=?,`archived_date`=?,`status`=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setString(1, "nour");
            ps.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
            ps.setString(3, "Archived");
            ps.setInt(4, id);
            ps.executeUpdate();
            System.out.println("removed succesfully");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void activate(int id) {
        try {
            String req = "UPDATE `activity` SET `archived_by`=?,`archived_date`=?,`status`=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(req);
            ps.setString(1, "sabrine");
            ps.setDate(2, java.sql.Date.valueOf(java.time.LocalDate.now()));
            ps.setString(3, "Available");
            ps.setInt(4, id);
            ps.executeUpdate();
            System.out.println("activated succesfully");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(int id, Activity new_activity) {
        try {
            String query = "UPDATE `activity` SET `name`=?,`deadline`=?,`work_todo`=?,`id_course`=?,`last_updated_by`=?,`last_updated_date`=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, new_activity.getName());
            ps.setDate(2, (Date) new_activity.getDeadline());
            ps.setString(3, new_activity.getWork_todo());
            ps.setInt(4, new_activity.getId_Course());
            ps.setInt(5, new_activity.getLast_updated_by());
            ps.setDate(6, (Date) new_activity.getLast_updated_Date());
            ps.setInt(7, id);
            ps.executeUpdate();
            System.out.println("Updated succesfully");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Activity details(int id) {
        Activity act = new Activity();
        boolean check = false;
        try {
            String query = "select * from activity where id= ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                check = true;
                act.setId(rs.getInt("id"));
                act.setName(rs.getString("name"));
                act.setDeadline(rs.getDate("deadline"));
                act.setStatus(rs.getString("status"));
                act.setId_Course(rs.getInt("id_course"));
                act.setCreated_by(rs.getInt("created_by"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        if (check == true) {
            return act;
        } else {
            return null;
        }
    }

    @Override
    public List<Activity> listAvailable() {

        List<Activity> acts = new ArrayList();
        try {
            String query = "select name,deadline,status from activity";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (!"Archived".equals(rs.getString("status"))) {
                    Activity act = new Activity();
                    act.setName(rs.getString("name"));
                    act.setDeadline(rs.getDate("deadline"));
                    act.setStatus(rs.getString("status"));
                    act.setId_Course(rs.getInt("id_course"));
                    act.setCreated_by(rs.getInt("created_by"));
                    acts.add(act);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return acts;
    }

    @Override
    public List<Activity> listArchived() {
        List<Activity> acts = new ArrayList();
        try {
            String query = "select name,deadline,status from activity";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (!"Available".equals(rs.getString("status"))) {
                    Activity act = new Activity();
                    act.setName(rs.getString("name"));
                    act.setDeadline(rs.getDate("deadline"));
                    act.setStatus(rs.getString("status"));
                    act.setId_Course(rs.getInt("id_course"));
                    act.setCreated_by(rs.getInt("created_by"));
                    acts.add(act);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return acts;
    }

    @Override
    public String display(List<Activity> acts) {
        String listActivities = "";
        for (Activity act : acts) {
            listActivities += "Activity{" + "name=" + act.getName() + ", deadline=" + act.getDeadline() + ", status=" + act.getStatus() + "\n";
        }
        return listActivities;
    }

    @Override
    public ObservableList<Activity> getActivities() {
        try {
            String query = "select id,name,deadline,status,id_course from activity";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (!"Archived".equals(rs.getString("status"))) {
                    Activity act = new Activity();
                    act.setId(rs.getInt("id"));
                    act.setName(rs.getString("name"));
                    act.setDeadline(rs.getDate("deadline"));
                    act.setStatus(rs.getString("status"));
                    act.setId_Course(rs.getInt("id_course"));
                    act.setCreated_by(rs.getInt("created_by"));
                    oL.addAll(act);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return oL;
    }

    public ArrayList<Activity> getActivitiesList() {
        try {
            String query = "select * from activity";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (!"Archived".equals(rs.getString("status"))) {
                    Activity act = new Activity();
                    act.setId(rs.getInt("id"));
                    act.setName(rs.getString("name"));
                    act.setDeadline(rs.getDate("deadline"));
                    act.setStatus(rs.getString("status"));
                    act.setId_Course(rs.getInt("id_course"));
                    act.setWork_todo(rs.getString("work_todo"));
                    act.setCreated_by(rs.getInt("created_by"));
                    la.add(act);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return la;
    }

    public ArrayList<Activity> getActivitiesListByIdCourse(String id_Course) {
        try {
            String query = "select * from activity where id_course=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, id_Course);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (!"Archived".equals(rs.getString("status"))) {
                    Activity act = new Activity();
                    act.setId(rs.getInt("id"));
                    act.setName(rs.getString("name"));
                    act.setDeadline(rs.getDate("deadline"));
                    act.setStatus(rs.getString("status"));
                    act.setId_Course(rs.getInt("id_course"));
                    act.setWork_todo(rs.getString("work_todo"));
                    act.setCreated_by(rs.getInt("created_by"));
                    la.add(act);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return la;
    }

    public ArrayList<Activity> getArchivedActivitiesListByIdCourse(int id_Course) {
        try {
            String query = "select * from activity where id_course=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id_Course);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (!"Available".equals(rs.getString("status"))) {
                    Activity act = new Activity();
                    act.setId(rs.getInt("id"));
                    act.setName(rs.getString("name"));
                    act.setDeadline(rs.getDate("deadline"));
                    act.setStatus(rs.getString("status"));
                    act.setId_Course(rs.getInt("id_course"));
                    act.setWork_todo(rs.getString("work_todo"));
                    la.add(act);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return la;
    }

    public ArrayList<Activity> getAvailableActivitiesListByIdCourse(int id_Course) {
        try {
            String query = "select * from activity where id_course=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id_Course);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (!"Archived".equals(rs.getString("status"))) {
                    Activity act = new Activity();
                    act.setId(rs.getInt("id"));
                    act.setName(rs.getString("name"));
                    act.setDeadline(rs.getDate("deadline"));
                    act.setStatus(rs.getString("status"));
                    act.setId_Course(rs.getInt("id_course"));
                    act.setWork_todo(rs.getString("work_todo"));
                    la.add(act);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return la;
    }

    public List<Activity> searchActivity(String txt, int id_Course) throws SQLException {

        List<Activity> listSearch = new ArrayList<>();
        try {
            String query = "select * from activity where name LIKE '%" + txt + "%'and status=? and id_course=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, "Available");
            ps.setInt(2, id_Course);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Activity act = FindActivityById(rs.getInt("id"));
                listSearch.add(act);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return listSearch;
    }

    public List<Activity> searchRemovedActivity(String txt, int id_Course) throws SQLException {

        List<Activity> listSearch = new ArrayList<>();
        try {
            String query = "select * from activity where name LIKE '%" + txt + "%'and status=? and id_course=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, "Archived");
            ps.setInt(2, id_Course);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Activity act = FindActivityById(rs.getInt("id"));
                listSearch.add(act);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return listSearch;
    }

    public Activity FindActivityById(int id) {

        Activity act = new Activity();
        try {
            String query = "select * from activity where id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                act.setId(rs.getInt("id"));
                act.setName(rs.getString("name"));
                act.setDeadline(rs.getDate("deadline"));
                act.setStatus(rs.getString("status"));
                act.setId_Course(rs.getInt("id_course"));
                act.setWork_todo(rs.getString("work_todo"));
                act.setCreated_by(rs.getInt("created_by"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return act;

    }

    @Override
    public List trierActivitiesID() {
        ArrayList<Activity> listActivity = new ArrayList<>();
        try {

            String query = "select * from activity ORDER BY id DESC";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            Activity act = new Activity();
            while (rs.next()) {
                act.setId(rs.getInt("id"));
                act.setName(rs.getString("name"));
                act.setDeadline(rs.getDate("deadline"));
                act.setStatus(rs.getString("status"));
                act.setId_Course(rs.getInt("id_course"));
                act.setWork_todo(rs.getString("work_todo"));
                act.setCreated_by(rs.getInt("created_by"));
                listActivity.add(act);

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return listActivity;
    }

}
