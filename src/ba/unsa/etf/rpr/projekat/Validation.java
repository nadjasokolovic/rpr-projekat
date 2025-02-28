package ba.unsa.etf.rpr.projekat;

public class Validation {

    private static<Tip> boolean validateLength(Tip length) {
        return length.equals(4);
    }

    public boolean validatePassword(String newPassword) {
        if(validateLength(newPassword.length()))
            return true;
        //validan password mora imati barem 4 znaka
        if(newPassword.length() < 4) return false;
        
        return true;
    }

    public boolean validateUsername(String newUsername) {
        //validan username mora imati barem 4 znaka
        if(newUsername.length() < 4) return false;
        //validan username moze sadrzavati samo velika i mala slova i cifre 0-9
        for(char c : newUsername.toCharArray())
            if(!Character.isLetter(c) && !Character.isDigit(c))
                return false;

        return true;
    }

    public boolean validateNameAndSurname(String newName) {
        //ne smije biti krace od 3 slova
        if(newName.length() < 3)
            return false;
        //ne smije pocinjati necim sto nije slovo
        if(!Character.isLetter(newName.charAt(0)))
            return false;
        //moze sadrzavati samo mala i velika slova, '-' i ' '
        for(char c : newName.toCharArray()) {
            if(!Character.isLetter(c) && c != '-' && c != ' ')
                return false;
        }
        return true;
    }

    public boolean validateGrade(String grade) {
        //ocjena objekta mora biti od 1 do 5
        if(grade.isEmpty())
            return false;
        if(Integer.parseInt(grade) < 1 || Integer.parseInt(grade) > 5)
            return false;

        return true;
    }

    public boolean validateNameOfObject(String name) {
        //mora imati barem 3 slova
        if(name.length() < 3) return false;
        //moze sadrzavati velika i mala slova, cifre i sve specijalne znakove
        return true;
    }

    public boolean validateLocation(String location) {
        //mora sadrzavati barem 3 slova
        if(location.length() < 3)
            return false;
        //moze sadrzavati slova, cifre, '-', ' ' i '.'
        for(char c : location.toCharArray()) {
            if(!Character.isLetter(c) && !Character.isDigit(c )&& c != '-' && c != ' ' && c != '.')
                return false;
        }
        return true;
    }

    public boolean validateDiscipline(String discipline) {
        //mora sadrzavati barem 3 slova
        if(discipline.length() < 3)
            return false;
        //moze sadrzavati slova, '-', ' '
        for(char c : discipline.toCharArray()) {
            if(!Character.isLetter(c) && c != '-' && c != ' ' )
                return false;
        }
        return true;
    }
}
