package ca.ulaval.ticketmaster.dao.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PairTest {

    @Test
    public void pairTest() {
	Pair<Integer, Integer> pair = new Pair<Integer, Integer>(1, 1);
	assertEquals(pair.getLeft(), new Integer(1));
	assertEquals(pair.getRight(), new Integer(1));
    }

    @Test
    public void hashCodeTest() {
	Pair<Integer, Integer> pair = new Pair<Integer, Integer>(1, 1);
	assertNotNull(pair.hashCode());
    }

    @Test
    public void equalsTest() {
	Pair<Integer, Integer> pair = new Pair<Integer, Integer>(1, 1);
	Pair<Integer, Integer> pair2 = new Pair<Integer, Integer>(1, 11);
	Pair<Integer, Integer> pairnull = new Pair<Integer, Integer>(0, 0);
	pairnull = null;
	assertFalse(pair.equals(pairnull));
	assertFalse(pair.equals(pair2));
	assertTrue(pair.equals(pair));

    }

}
