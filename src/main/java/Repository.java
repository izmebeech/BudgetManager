import java.util.ArrayList;

public class Repository<T> {
    private final ArrayList<T> items;

    public Repository() {
        items = new ArrayList<>();
    }

    public void add(T item){     //add object of whatever current type to the collection
        items.add(item);
    }
}