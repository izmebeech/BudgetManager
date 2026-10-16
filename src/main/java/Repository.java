import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class Repository<T> {

    private final ArrayList<T> items;

    public Repository() {
        items = new ArrayList<>();
    }
    public void add(T item){
        Objects.requireNonNull(item);  //if item is null-> NullPointerException before it reaches .add(item).
        items.add(item);            //add object of whatever current type to the collection
    }

    public List<T> findAll() {
        return List.copyOf(items);  //Callers can't add or remove items through the returned list.
                                    //Changes to the returned list won't alter the repository.

    }
    public List <T> findWhere(Predicate<T> condition){     //when called, will give back a List<T>. So if were calling
        return items.stream()                        //transactions, it will become List<transaction>findWhere
                .filter(condition)                  // predicate answers y/n, true/false,
                .toList();                  // stream takes items in list and lets us process them individually
                                        //filter(condition) uses predicate to clear out false objects from stream
                                        //toList makes a new list with the filtered results
    }
}