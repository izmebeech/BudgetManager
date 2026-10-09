import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Repository<T> {

    private final ArrayList<T> items;

    public Repository() {
        items = new ArrayList<>();
    }
    public void add(T item){     //add object of whatever current type to the collection
        items.add(item);
    }

    public List<T> findAll() {
        return items;
    }
    public List <T> findWhere(Predicate<T> condition){     //when called, will give back a List<T>. So if were calling
        return items.stream()                       //transactions, it will become List<transaction>findWhere
                .filter(condition)                  // predicate answers y/n, true/false,
                .toList();                  // stream takes items in list and lets us process them individually
                                        //filter(condition) uses predicate to clear out false objects from stream
                                        //toList makes a new list with the filtered results
    }
}