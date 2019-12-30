package ba.unsa.etf.rpr.projekat;

public class Validacija {
    public boolean validirajPassword(String newPassword) {
        //validan password mora imati barem 4 znaka
        if(newPassword.length() < 4) return false;

        return true;
    }

    public boolean validirajUsername(String newUsername) {
        //validan username mora imati barem 4 znaka
        if(newUsername.length() < 4) return false;
        //validan username moze sadrzavati samo velika i mala slova i cifre 0-9
        for(char c : newUsername.toCharArray())
            if(!Character.isLetter(c) && !Character.isDigit(c))
                return false;

        return true;
    }

    public boolean validirajImeIPrezime(String newName) {
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

    public boolean validirajOcjenu(int ocjena) {
        //ocjena objekta mora biti od 1 do 5
        if(ocjena < 1 || ocjena > 5)
            return false;

        return true;
    }

    public boolean validirajNazivObjekta(String naziv) {
        //mora imati barem 3 slova
        if(naziv.length() < 3) return false;
        //moze sadrzavati velika i mala slova, cifre i sve specijalne znakove
        return true;
    }

    public boolean validirajNazivLokacije(String lokacija) {
        //mora sadrzavati barem 3 slova
        if(lokacija.length() < 3)
            return false;
        //moze sadrzavati slova, cifre, '-', ' ' i '.'
        for(char c : lokacija.toCharArray()) {
            if(!Character.isLetter(c) && !Character.isDigit(c )&& c != '-' && c != ' ' && c != '.')
                return false;
        }
        return true;
    }

    public boolean validirajNazivDiscipline(String disciplina) {
        //mora sadrzavati barem 3 slova
        if(disciplina.length() < 3)
            return false;
        //moze sadrzavati slova, '-', ' '
        for(char c : disciplina.toCharArray()) {
            if(!Character.isLetter(c) && c != '-' && c != ' ' )
                return false;
        }
        return true;
    }

}
