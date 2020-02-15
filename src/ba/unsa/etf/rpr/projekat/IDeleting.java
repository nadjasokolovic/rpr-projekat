package ba.unsa.etf.rpr.projekat;

public interface IDeleting {
    void deleteActivityUser(int userId);
    void deleteActivity(int activityId);
    void deleteUser(int userId);
    void deletePerson(int personId);
    void deleteTraining(int id);
    void deleteObjectDiscipline(int id);
    void deleteObjectRate(int idObjekta);
    void deleteObject(int id);
    void deleteDisciplineForObject(int objectId, int disciplineId);

}
