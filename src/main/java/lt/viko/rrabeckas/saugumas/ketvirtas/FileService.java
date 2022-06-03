package lt.viko.rrabeckas.saugumas.ketvirtas;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileService {

    public void saveUserAndEncrypt(String username, String password) {
        try {
            FileWriter myWriter = new FileWriter("user.txt");
            String data = AES.encrypt(username + " " + password, "secret");
            myWriter.write(data);
            myWriter.close();
            System.out.println("Successfully wrote to the file.: " + data);
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String getUserStringFromFileAndDecrypt() {

        String data = "";
        try {
            File myObj = new File("user.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                System.out.println("readed from file, encrypted: " + data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

        }
        return AES.decrypt(data, "secret");
    }

    public void savePasswordToCsv(Password pass) {
        List<Password> passwords = getAllPasswords();
        passwords.add(pass);
        System.out.println("Password added: " + pass);
        saveAllPasswords(passwords);
    }

    private void saveAllPasswords(List<Password> passwords) {
        final char CSV_SEPARATOR = ','; // it could be a comma or a semi colon

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("passwords.csv"))) {
            passwords.forEach(psw -> {
                try {
                    writer.append(psw.getName()).append(CSV_SEPARATOR)
                            .append(AES.encrypt(psw.getPassword(), "secret")).append(CSV_SEPARATOR)
                            .append(psw.getApp()).append(CSV_SEPARATOR)
                            .append(psw.getComment()).append(System.lineSeparator());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private List<Password> getAllPasswords() {
        List<Password> passwords = new ArrayList<>();
        Path pathToFile = Paths.get("passwords.csv");

        // create an instance of BufferedReader
        // using try with resource, Java 7 feature to close resources
        try (BufferedReader br = Files.newBufferedReader(pathToFile,
                StandardCharsets.US_ASCII)) {

            // read the first line from the text file
            String line = br.readLine();

            // loop until all lines are read
            while (line != null) {

                // use string.split to load a string array with the values from
                // each line of
                // the file, using a comma as the delimiter
                String[] attributes = line.split(",");

                Password password = createPassword(attributes);

                // adding password into ArrayList
                passwords.add(password);

                // read next line before looping
                // if end of file reached, line would be null
                line = br.readLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return passwords;
    }

    private static Password createPassword(String[] metadata) {
        String name = metadata[0];
        String password = AES.decrypt(metadata[1], "secret");
        String app = metadata[2];
        String comment = metadata[2];

        // create and return password of this metadata
        return new Password(name, password, app, comment);
    }

    public void updatePasswordByName(String name) {
        List<Password> passwords = getAllPasswords();
        for (Password p : passwords) {
            if (p.getName().equals(name)) {
                updateAtributes(p);
            }
        }
        saveAllPasswords(passwords);
    }

    private void updateAtributes(Password p) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Rastas slaptažodis: " + p);
        System.out.println("Iveskite nauja slaptažodį: ");
        p.setPassword(sc.nextLine());
        System.out.println("Naujas slaptažodis: "+p);
    }

    public void deletePasswordByName(String name) {
        List<Password> passwords = getAllPasswords();
        for (Password p : passwords) {
            if (p.getName().equals(name)) {
                passwords.remove(p);
            }
        }
        saveAllPasswords(passwords);
    }
}
