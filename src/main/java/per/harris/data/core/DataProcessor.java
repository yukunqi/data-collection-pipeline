package per.harris.data.core;


public interface DataProcessor<T,R> {
    R processData(T input);
}
