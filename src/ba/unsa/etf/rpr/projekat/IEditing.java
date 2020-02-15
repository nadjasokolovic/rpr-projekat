package ba.unsa.etf.rpr.projekat;

public interface IEditing {
    void editUser(User user);
    void editObject(Object object);
    void editProfil(String user, String newName, String newSurname, String newUsername, String newPassword);
    void updatePassword(String username, String newPassword);
}
