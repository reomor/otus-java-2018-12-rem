package rem.hw03;

import java.util.Collections;
import java.util.List;

public class Application {

    public static void main(String[] args) {
        List<String> myArrayList = new MyArrayList<>();
        List<String> myArrayListCopy = new MyArrayList<>();

        Collections.addAll(myArrayList, "uno", "duo", "tre", "quattro");
        Collections.copy(myArrayList, myArrayListCopy);
        Collections.sort(myArrayList, String::compareTo);
    }
}
