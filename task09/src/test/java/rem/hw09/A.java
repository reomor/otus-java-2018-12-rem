package rem.hw09;

import java.util.*;

public class A extends AA {
    private int i;
    private Integer I;
    private String string = "123";
    private double[] array = {1.2, 1.3};
    private Set<Integer> set = new HashSet<>();
    private Queue<Boolean> queue = new PriorityQueue<>();
    private Map<String, String> map = new HashMap<>();
    private Map<Integer, Integer> mapI = new HashMap<>();
    private char c = 'a';
    public A() {
        super(1);
        set.add(1);
        set.add(2);
        set.add(3);
        queue.add(true);
        map.put("1key", "1value");
        map.put("2key", "2value");
        mapI.put(1, 1);
        mapI.put(2, 22);
    }
}
