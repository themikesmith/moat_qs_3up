package moat_hashmap;



public class MyHashMap<K,V> {
	
	public static final int DEFAULT_CAPACITY = 128;
	public static final float DEFAULT_LOAD = 0.75f;
	private float loadFactor;
	private int capacity;
	private int size;
	// handle collisions with chaining, see mapentry class
	private MapEntry<K,V>[] table;
	
	public MyHashMap() {
		create(DEFAULT_CAPACITY, DEFAULT_LOAD);
	}
	
	public MyHashMap(int capacity, float loadFactor) {
		create(capacity, loadFactor);
	}
	
	private void create(int capacity, float loadFactor) {
		this.capacity = capacity;
		this.loadFactor = loadFactor;
		clear();
	}

	/**
	 * Clears the map.
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		table = new MapEntry[capacity];
		size = 0;
	}

	/**
	 * Checks if this map contains the given key
	 * @param key
	 * @return true if contains key, false otherwise
	 */
	public boolean containsKey(Object key) {
		return getEntry(key) != null;
	}

	/**
	 * Checks if this map contains the given value
	 * @param value
	 * @return true if contains value, false otherwise
	 */
	public boolean containsValue(Object value) {
		for(int i = 0; i < table.length; i++) { // loop through all buckets...
			MapEntry<K,V> e = table[i];
			while(e != null) { // ...and all entries in each bucket
				if(e.getValue().equals(value)) {
					return true;
				}
				e = e.getNext();
			}
		}
		return false;
	}

	/**
	 * Gets and returns the value associated with the given key
	 * @param key
	 * @return the value, or null if key isn't in the map
	 */
	public V get(Object key) {
		MapEntry<K,V> e = getEntry(key);
		if(e == null) { // we haven't found the value, or the list was empty
			return null;
		}
		else {
			return e.getValue();
		}
	}

	/**
	 * Returns true if empty, false otherwise
	 * @return
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Similar to java.util.Map.put, puts a new value at the entry for key
	 * overwriting the existing key entry if it's there.
	 * @param key
	 * @param value
	 * @return the old value if the key was there before, else null
	 */
	public V put(K key, V value) {
		resizeIfNecessary();
		V oldValue = null;
		MapEntry<K,V> newEntry = new MapEntry<K, V>(key, value);
		int i = bucketIndexOf(key);
		MapEntry<K,V> e = table[i], prev = null;
		while(e != null) { // iterate over linked list
			if(e.getKey().equals(key)) { 
				break; // break if we find the value
			}
			prev = e;
			e = e.getNext();
		}
		if(table[i] == null) { // start of linked list
			table[i] = newEntry;
			size++;
		}
		else if(e == null) { // didn't find the key in the bucket
			assert(prev != null); // prev will never be null since we know we have >=1 element
			prev.setNext(newEntry);
			size++;
		}
		else { // found the key in the bucket already, replace
			oldValue = e.getValue();
			e.setValue(value);
		}
		return oldValue;
	}

	/**
	 * Similar to java.util.Map.remove, removes the entry associated with 
	 * the given key, and returns the old value, if it was present.
	 * @param key
	 * @return the old value, or null if key wasn't there.
	 */
	public V remove(Object key) {
		int i = bucketIndexOf(key);
		MapEntry<K,V> e = getEntry(key,i);
		if(e == null) { // we haven't found the value, or the list was empty
			return null;
		}
		// else we remove it
		if(e.getPrev() == null) {
			// first in a bucket. move the next element to the table
			table[i] = e.getNext();
		}
		else { // link prev to next
			e.getPrev().setNext(e.getNext());
		}
		// and return
		size--;
		return e.getValue();
	}
	
	/**
	 * Gets the map entry given the key
	 * @param key
	 * @return the entry or null if not found
	 */
	private MapEntry<K,V> getEntry(Object key) {
		int i = bucketIndexOf(key);
		MapEntry<K,V> e = table[i];
		while(e != null) { // iterate over linked list
			if(e.getKey().equals(key)) {
				return e; // stop if we find the value
			}
			e = e.getNext();
		}
		return null;
	}
	
	/**
	 * Gets the map entry given the key
	 * @param key the key
	 * @param i the bucket index
	 * @return the entry or null if not found
	 */
	private MapEntry<K,V> getEntry(Object key, int i) {
		MapEntry<K,V> e = table[i];
		while(e != null) { // iterate over linked list
			if(e.getKey().equals(key)) {
				return e; // stop if we find the value
			}
			e = e.getNext();
		}
		return null;
	}
	
	/**
	 * Gets the bucket index of the given key
	 * @param key
	 * @return the index where it would be
	 */
	private int bucketIndexOf(Object key) {
		int h = key.hashCode() % table.length;
		if(h < 0) {
			h += table.length;
		}
		return h;
	}
	
	/**
	 * Resizes the underlying array table to the given new capacity.
	 * @param newCapacity
	 */
	private void resize(int newCapacity) {
		if(this.capacity >= newCapacity) {
			return; // do nothing
		} // else make table larger, and rehash everything
		MapEntry<K,V>[] oldTable = table;
		create(newCapacity, this.loadFactor);
		for(int i = 0; i < oldTable.length; i++) {
			MapEntry<K,V> e = oldTable[i];
			while(e != null) {
				put(e.getKey(), e.getValue());
				e = e.getNext();
			}
		}
	}
	
	/**
	 * Checks the load factor - (size+1) vs capacity
	 * @return true if too large, false if within acceptable range
	 */
	private boolean checkLoad() {
		return (new Float(size+1) / new Float(capacity)) > this.loadFactor;
	}
	
	/**
	 * Gets capacity for resizing.
	 *  - as written, capacity squared
	 * @return new capacity
	 */
	private int getNewCapacity() {
		return (int) Math.pow(capacity, 2);
	}
	
	/**
	 * Resizes if necessary.
	 */
	private void resizeIfNecessary() {
		if(checkLoad()) {
			resize(getNewCapacity());
		}
	}

	/**
	 * Gets the number of entries in the map
	 * @return the number of entries
	 */
	public int size() {
		return size;
	}

}
