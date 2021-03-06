/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;
import Entities.Forum;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author ADMIN
 */
public interface IForum {
    public int getId(Forum forum); 
    public ObservableList<Forum> getForum();
    public ObservableList<Forum> getForumByIdCourse(int id_course);
    public ObservableList<Forum> getArchivedForumByIdCourse(int id_course);
    public void add(Forum forum);
    public void remove(int id);
    public void activate(int id);
    public void update(int id, Forum new_forum);
    public Forum details(int id);
    public List<Forum> listAvailable();
    public List<Forum> listArchived();
    public String display(List<Forum> acts);
    
}
