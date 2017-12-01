import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TheJava {
    public static Map<String, List<Integer>>foo() {
        List<Integer> list = IntStream.range(0, 1000).boxed().collect(Collectors.toList());
        Function<Integer, String> key = i -> "" + i % 100;
        Map<String, List<Integer>> map = new HashMap<>();
        list.forEach(
                i -> {
                    map.putIfAbsent(key.apply(i), new ArrayList<>());
                    map.get(key.apply(i)).add(i);
                }
        );
        return map;
    }
}
