package strategies;

public interface HashFunction<K> {
	int hashCode(K key);
	}