package lt.viko.rrabeckas.saugumas.ketvirtas;


import java.util.Random;
import java.util.Scanner;

public class MyApp {
    Scanner sc = new Scanner(System.in);
    PrintHelper printer = new PrintHelper();
    FileService fileService = new FileService();

    public void run() {
        while (true) {
            printer.printLoginOrRegister();
            executeLoginChoice(getChoice());
        }
    }

    private void executeLoginChoice(String choice) {
        if (choice.equals("1")) {
            printer.printInputUsername();

            String username = sc.nextLine();
            System.out.println("Vartotojas:" + username);

            String password = getPassword();
            System.out.println("Slaptažodis:" + password);

            saveUserToFileAndEncrypt(username, password);

            System.out.println(getUserStringFromFileAndDecrypt());

        } else if (choice.equals("2")) {
            printer.printString("Iveskite varda");
            String username = sc.nextLine();
            printer.printString(username);
            printer.printString("Iveskite slaptazodi");
            String password = sc.nextLine();
            printer.printString(password);
            if (checkLogin(username, password)) {
                printer.printPasswordControlMenu();
                executePasswordManagerChoice(getChoice());
            } else {
                printer.printString("Failed login, check psw or user");
            }
        } else {
            System.out.println("Neteisingas pasirinkimas, bandykite dar kart");
        }
    }

    private void executePasswordManagerChoice(String choice) {
        while(true){
            if(choice.equals("1")){
                printer.printString("Įveskite slaptažodžio pavadinimą:");
                String name = sc.nextLine();
                printer.printString("Įveskite slaptažodį: ");
                String password = sc.nextLine();
                printer.printString("Įveskite programą kurios slpt.: ");
                String app = sc.nextLine();
                printer.printString("Įveskite komentarą: ");
                String comment = sc.nextLine();
                Password pass = new Password(name,password,app,comment);
                fileService.savePasswordToCsv(pass);
                break;
            }else if(choice.equals("2")){
                printer.printString("Koki rasti? Iveskite pavadinima: ");
                String name = sc.nextLine();
                fileService.updatePasswordByName(name);
                System.out.println("Slaptažodis atnaujintas.");
                return;
            }else if(choice.equals("3")){
                printer.printString("Kokį ištrinti? Iveskite pavadinima: ");
                String name = sc.nextLine();
                fileService.deletePasswordByName(name);
                System.out.println("Slaptažodis ištrintas.");
                return;
            }else{
                printer.printString("Neteisingas pasirinkimas, iveskite teisinga: ");
                choice=getChoice();
            }
        }
    }

    private boolean checkLogin(String username, String password) {
        if ((username + " " + password).equals(fileService.getUserStringFromFileAndDecrypt())) {
            printer.printString("Sekmingai prisijungta");
            return true;
        } else {
            printer.printString("Nesėkmingai prisijungta");
            return false;
        }
    }

    private String getUserStringFromFileAndDecrypt() {
        return fileService.getUserStringFromFileAndDecrypt();
    }

    private void saveUserToFileAndEncrypt(String username, String password) {
        fileService.saveUserAndEncrypt(username, password);
    }

    private String getPassword() {
        String choice = "";
        while (checkPasswordChoice(choice)) {
            printer.printPasswordMenu();
            choice = getChoice();
        }
        return executePasswordChoice(choice);
    }

    private boolean checkPasswordChoice(String choice) {
        return !choice.equals("1") && !choice.equals("2");
    }

    private String executePasswordChoice(String choice) {
        if (choice.equals("1")) {
            return generatePassword();
        } else {
            printer.printInputPassword();
            return sc.nextLine();
        }
    }

    private String generatePassword() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    private String getChoice() {
        return sc.nextLine();
    }
}
