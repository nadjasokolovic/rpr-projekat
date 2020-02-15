package ba.unsa.etf.rpr.projekat;

public interface IAdding {
    void addUser(User user);
    void addObject(Object object);
    void addDisciplineForObject(int id, String name);
    void addObjectRate(int objectId, int rate);
    void addNotification(int personId, String notification);
    void addTrainings(String username);
}
