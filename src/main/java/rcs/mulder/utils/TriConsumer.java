package rcs.mulder.utils;

@FunctionalInterface
public interface TriConsumer<T, U, V> {
  
  void accept(T t, U u, V v);
}
