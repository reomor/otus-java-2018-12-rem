package rem.hw01;

import org.apache.commons.text.StrSubstitutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Application {

    private static String makeApacheCommonsTextSubstitution(String templateString, Map<String, String> substitutes) {
        StrSubstitutor sub = new StrSubstitutor(substitutes);
        return sub.replace(templateString);
    }

    public static void main(String[] args) {
        String name = "";
        String message = "";

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Enter your name:");
            name = reader.readLine();
            System.out.println("Enter your message:");
            message = reader.readLine();
        } catch (IOException ignore) {
        }

        Map<String, String> substitutes = new HashMap<>();
        substitutes.put("name", name);
        substitutes.put("message", message);

        System.out.println(makeApacheCommonsTextSubstitution("User ${name} sends to world \"${message}\"", substitutes));
    }
}
