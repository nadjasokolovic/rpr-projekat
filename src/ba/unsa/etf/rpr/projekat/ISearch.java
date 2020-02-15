package ba.unsa.etf.rpr.projekat;

import java.util.ArrayList;
import java.util.Map;

public interface ISearch {
    int getIdForUsername(String username);
    String getPasswordForUsername(String username);
    ArrayList<User> getAllUsers();
    ArrayList<Object> getAllObjects();
    int getUserIdForPersonId(int id);
    int getActivityIdForUserId(int id);
    ArrayList<Discipline> getDisciplinesForObject(int objectId);
    int getNumberTerminsUsed(int userId);
    int getNumberOfTermins(int userId);
    ArrayList<String> getNotifications(int userId);
    ArrayList<Object> getObjectsForDiscipline(int disciplineId);
    ArrayList<Discipline> getAllDisciplines();
    ArrayList<Training> getTrainingsOnDay(String objectName, String dayOfWeek);
    Map<User, Training> getTrainingsForAllUsers(String day);
    String getTypeOfPerson(String username);
    ArrayList<Activity> getActivityForUser(String username);
}
