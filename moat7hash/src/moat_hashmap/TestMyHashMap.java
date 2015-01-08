package moat_hashmap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestMyHashMap {

	private MyHashMap<String, Integer> m;
	
	@Before
	public void setUp() throws Exception {
		m = new MyHashMap<String, Integer>();
	}
	
	@Test
	public void testResize() {
		m = new MyHashMap<String, Integer>(4,0.75f);
		String key = "Hello world!";
		int value = 1;
		m.put(key, value);
		assertEquals(1, m.size());
		m.put(key, 2);
		String newKey = "new key";
		int newValue = 123;
		m.put(newKey, newValue);
		assertEquals(2, m.size());
		String keyThree = "three";
		int valueThree = 3;
		String keyFour = "four";
		int valueFour = 4;
		// trigger resize
		m.put(keyThree, valueThree);
		// and verify functionality
		assertTrue(m.containsKey(keyThree));
		assertNotNull(m.get(keyThree));
		assertTrue(m.containsValue(valueThree));
		assertEquals(3, m.get(keyThree).intValue());
		assertEquals(3, m.size());
		m.put(keyFour, valueFour);
		assertTrue(m.containsKey(keyFour));
		assertTrue(m.containsValue(valueFour));
		assertEquals(4, m.size());
		assertNotNull(m.get(keyFour));
		assertEquals(4, m.get(keyFour).intValue());
	}
	
	@Test
	public void testClear() {
		String key = "Hello world!";
		int value = 1;
		m.put(key, value);
		assertEquals(1, m.size());
		m.put(key, 2);
		String newKey = "new key";
		int newValue = 123;
		m.put(newKey, newValue);
		assertEquals(2, m.size());
		m.clear();
		assertTrue(m.isEmpty());
		Integer vv = m.get(key);
		assertNull(vv);
		assertFalse(m.containsKey(key));
		vv = m.get(newKey);
		assertNull(vv);
		assertFalse(m.containsKey(newKey));
	}

	@Test
	public void testContainsKey() {
		String key = "Hello world!";
		int value = 1;
		assertTrue(m.isEmpty());
		m.put(key, value);
		assertTrue(m.containsKey(key));
		assertFalse(m.isEmpty());
		assertEquals(1, m.size());
		int v = m.get(key);
		assertEquals(value, v);
		String notThere = "wahoo";
		Integer vv = m.get(notThere);
		assertNull(vv);
		assertEquals(1, m.size());
		assertFalse(m.containsKey(notThere));
	}

	@Test
	public void testContainsValue() {
		assertTrue(m.isEmpty());
		String key = "Hello world!";
		int value = 1;
		assertFalse(m.containsValue(value));
		m.put(key, value);
		assertEquals(1, m.size());
		assertTrue(m.containsValue(value));
		int valueTwo = 2;
		m.put(key, valueTwo);
		assertFalse(m.containsValue(value));
		assertTrue(m.containsValue(valueTwo));
		String newKey = "new key";
		int newValue = 123;
		m.put(newKey, newValue);
		assertEquals(2, m.size());
		assertTrue(m.containsValue(newValue));
	}

	@Test
	public void testGet() {
		String key = "Hello world!";
		int value = 1;
		assertTrue(m.isEmpty());
		m.put(key, value);
		assertTrue(m.containsKey(key));
		assertFalse(m.isEmpty());
		assertEquals(1, m.size());
		int v = m.get(key);
		assertEquals(value, v);
		String notThere = "wahoo";
		Integer vv = m.get(notThere);
		assertNull(vv);
		assertEquals(1, m.size());
	}

	@Test
	public void testIsEmpty() {
		String key = "Hello world!";
		int value = 1;
		assertTrue(m.isEmpty());
		m.put(key, value);
		assertFalse(m.isEmpty());
	}

	@Test
	public void testPut() {
		assertTrue(m.isEmpty());
		String key = "Hello world!";
		int value = 1;
		Integer v = m.put(key, value);
		assertTrue(m.containsKey(key));
		assertEquals(1, m.size());
		assertNull(v);
		v = m.put(key, 2);
		assertTrue(m.containsKey(key));
		assertNotNull(v);
		assertEquals(value, v.intValue());
		assertEquals(1, m.size());
		String newKey = "new key";
		int newValue = 123;
		v = m.put(newKey, newValue);
		assertTrue(m.containsKey(newKey));
		assertNull(v);
		assertEquals(2, m.size());
	}

	@Test
	public void testRemove() {
		String key = "Hello world!";
		int value = 1;
		assertTrue(m.isEmpty());
		m.put(key, value);
		assertTrue(m.containsKey(key));
		assertFalse(m.isEmpty());
		int v = m.remove(key);
		assertTrue(m.isEmpty());
		assertEquals(value, v);
		Integer vv = m.remove(key);
		assertNull(vv);
		assertTrue(m.isEmpty());
	}

}
