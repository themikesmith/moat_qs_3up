package moat_hashmap;

import java.util.Map.Entry;

public class MapEntry<K,V> implements Entry<K, V> {
	private K key;
	private V value;
	private MapEntry<K,V> next; // doubly linked list
	private MapEntry<K,V> prev; // doubly linked list
	
	public MapEntry(K k, V v) {
		key = k;
		value = v;
		prev = null;
		next = null;
	}

	@Override
	public K getKey() {
		return key;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof MapEntry) {
			@SuppressWarnings("rawtypes")
			MapEntry e = (MapEntry) o;
			return e.getKey().equals(this.key) && e.getValue().equals(this.value);
		}
		return false;
	}

	@Override
	public V getValue() {
		return value;
	}

	@Override
	public V setValue(V value) {
		V oldVal = this.value;
		this.value = value;
		return oldVal;
	}
	public MapEntry<K,V> getPrev() {
		return prev;
	}
	public MapEntry<K,V> getNext() {
		return next;
	}
	/**
	 * Sets this entry's next value to the given entry, so that
	 * it follows immediately after this entry in the list
	 * If the given entry is not null, assigns its prev value and its next value.
	 * (calling this with a null parameter will cut off any next entries linked)
	 * @param next
	 */
	public void setNext(MapEntry<K,V> next) {
		MapEntry<K,V> oldNext = this.next;
		this.next = next;
		if(next != null) {
			next.prev = this;
			if(oldNext != null) {
				next.prev = oldNext;
			}
		}
	}
	@Override
	public int hashCode() {
		return this.key.hashCode() ^ this.value.hashCode();
	}
}