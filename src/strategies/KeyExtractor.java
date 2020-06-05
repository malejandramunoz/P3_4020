package strategies;

public interface KeyExtractor<K, V> {

	K getKey(V value);
}