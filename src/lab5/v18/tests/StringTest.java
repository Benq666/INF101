package lab5.v18.tests;

import static org.junit.jupiter.api.Assertions.*;

import lab5.v18.util.IGenerator;
import lab5.v18.util.generators.StringGenerator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;


public class StringTest {
	private final IGenerator<String> strGen = new StringGenerator();
	private final int N = 100000;

	public void transitiveProperty(String s1, String s2, String s3) {
		if(s1.equals(s2) && s2.equals(s3)) {
			assertEquals(s1, s3); // transitivitet
		}
	}

	public void reflexiveProperty(String s1, String s2) {
		assertEquals(s1, s2); // refleksivitet
	}

	public void symmetricProperty(String s1, String s2) {
		if(s1.equals(s2)) {
			assertEquals(s2, s1); // symmetri
		}
	}

	public void transitivePropertyHash(String s1, String s2, String s3) {
		if(s1.equals(s2) && s2.equals(s3)) {
			assertEquals(s1.hashCode(), s3.hashCode());
		}
	}

	public void reflexivePropertyHash(String s1, String s2) {
		assertEquals(s1.hashCode(), s2.hashCode());
	}

	public void symmetricPropertyHash(String s1, String s2) {
		if(s1.equals(s2)) {
			assertEquals(s2.hashCode(), s1.hashCode());
		}
	}

	@Test
	public void stringTest1() {
		assertEquals("foo", "FOO".toLowerCase());
	}
	
	@Test
	public void stringTest2() {
		for(int i = 0; i < N; i++) {
			String s = strGen.generate();
			assertEquals(s + s, s.concat(s));
		}
	}

	@Test
	public void transitiveTest1() {
		for (int i = 0; i < N; i++) {
			String randString = strGen.generate();
			transitiveProperty(randString, randString, randString);
		}
	}

	@Test
	public void transitiveTest2() {
		for (int i = 0; i < N; i++) {
			List<String> threeEqual = new ArrayList<>(strGen.generateEquals(3));
			transitiveProperty(threeEqual.get(0), threeEqual.get(1), threeEqual.get(2));
		}
	}

	@Test
	public void reflexiveTest1() {
		for (int i = 0; i < N; i++) {
			String randString = strGen.generate();
			reflexiveProperty(randString, randString);
		}
	}

	@Test
	public void reflexiveTest2() {
		for (int i = 0; i < N; i++) {
			List<String> twoEqual = new ArrayList<>(strGen.generateEquals(2));
			reflexiveProperty(twoEqual.get(0), twoEqual.get(1));
		}
	}

	@Test
	public void symmetricTest1() {
		for (int i = 0; i < N; i++) {
			String randString = strGen.generate();
			symmetricProperty(randString, randString);
		}
	}

	@Test
	public void symmetricTest2() {
		for (int i = 0; i < N; i++) {
			List<String> twoEqual = new ArrayList<>(strGen.generateEquals(2));
			symmetricProperty(twoEqual.get(0), twoEqual.get(1));
		}
	}

	@Test
	public void transitiveTestHash() {
		for (int i = 0; i < N; i++) {
			String randString = strGen.generate();
			List<String> threeEqual = new ArrayList<>(strGen.generateEquals(3));

			transitivePropertyHash(randString, randString, randString);
			transitivePropertyHash(threeEqual.get(0), threeEqual.get(1), threeEqual.get(2));
		}
	}

	@Test
	public void reflexiveTestHash() {
		for (int i = 0; i < N; i++) {
			String randString = strGen.generate();
			List<String> twoEqual = new ArrayList<>(strGen.generateEquals(3));

			reflexivePropertyHash(randString, randString);
			reflexivePropertyHash(twoEqual.get(0), twoEqual.get(1));
		}
	}

	@Test
	public void symmetricTestHash() {
		for (int i = 0; i < N; i++) {
			String randString = strGen.generate();
			List<String> twoEqual = new ArrayList<>(strGen.generateEquals(3));

			symmetricPropertyHash(randString, randString);
			symmetricPropertyHash(twoEqual.get(0), twoEqual.get(1));
		}
	}
}