package Application.Interfaces;

import java.util.List;

public interface Model<T> {
    public List<T> getAll();
    public T store(T object);
    public T findOne(T object);
    public T update(T object);
    public boolean destroy(T object);
}
