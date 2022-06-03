package lt.viko.rrabeckas.saugumas.ketvirtas;

public class PrintHelper {
    public void printLoginOrRegister(){
        System.out.println("---");
        System.out.println("Spausdinamas prisijungimo menu:");
        System.out.println("1. Registruotis");
        System.out.println("2. Prisijungti");
        System.out.println("---");
    }

    public void printInputUsername() {
        System.out.println("---");
        System.out.println("Įveskite prisijungimo vardą:");
        System.out.println("---");
    }

    public void printPasswordMenu() {
        System.out.println("---");
        System.out.println("Kaip įvesite slaptažodį:");
        System.out.println("1. Sugeneruoti");
        System.out.println("2. Įvesti");
        System.out.println("---");
    }

    public void printInputPassword() {
        System.out.println("---");
        System.out.println("Įveskite slaptažodį:");
        System.out.println("---");
    }

    public void printString(String string) {
        System.out.println(string);
    }

    public void printPasswordControlMenu() {
        System.out.println("---");
        System.out.println("Slaptažodžių valdymo menu:");
        System.out.println("1. Sukurti naują");
        System.out.println("2. Rasti esamą ir atnaujinti slaptažodį");
        System.out.println("3. Rasti esamą ir išstrinti");
        System.out.println("---");
    }
}
