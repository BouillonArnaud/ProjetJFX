package utils;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import model.Case;

class CaseTest {
	private final Case case1 = new Case(1, 0, 0);
    private final Case case1Copy = new Case(1, 0, 0);
    private final Case case2 = new Case(1, 10, 9);
    private final Case caseDiffIndex = new Case(0, 0, 0);
    private final Case caseDiffX = new Case(1,9,0);
    private final Case caseDiffY = new Case(1,0,10);

    @Test
    void equals_ShouldBeReflexive() {
        assertEquals(case1, case1);
    }

    @Test
    void equals_ShouldBeSymmetric() {
        assertEquals(case1.equals(case1Copy), case1Copy.equals(case1));
    }

    @Test
    void equals_ShouldBeTransitive() {
        Case case3 = new Case(1, 0, 0);
        assertAll(
            () -> assertEquals(case1, case1Copy),
            () -> assertEquals(case1Copy, case3),
            () -> assertEquals(case1, case3)
        );
    }
    
    @Test
    void equals_ShouldReturnTrueForSameValues() {
        assertTrue(case1.equals(case1Copy));
    }

    @Test
    void equals_ShouldReturnFalseForDifferentIndex() {
        assertFalse(case1.equals(caseDiffIndex));
    }

    @Test
    void equals_ShouldReturnFalseForDifferentX() {
        assertFalse(case1.equals(caseDiffX));
    }

    @Test
    void equals_ShouldReturnFalseForDifferentY() {
        assertFalse(case1.equals(caseDiffY));
    }
    
    @Test
    void equals_ShouldReturnFalseForNull() {
        assertFalse(case1.equals(null));
    }

    @Test
    void equals_ShouldReturnFalseForDifferentClass() {
        assertFalse(case1.equals("Ceci est une String"));
    }
}