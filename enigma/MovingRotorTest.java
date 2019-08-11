package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import java.util.HashMap;

import static enigma.TestUtils.*;

/** The suite of all JUnit tests for the Permutation class.
 *  @author
 */
public class MovingRotorTest {

    /** Testing time limit. */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Rotor rotor;
    private String alpha = UPPER_STRING;

    /** Check that rotor has an alphabet whose size is that of
     *  FROMALPHA and TOALPHA and that maps each character of
     *  FROMALPHA to the corresponding character of FROMALPHA, and
     *  vice-versa. TESTID is used in error messages. */
    private void checkRotor(String testId,
                            String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, rotor.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d (%c)", ci, c),
                         ei, rotor.convertForward(ci));
            assertEquals(msg(testId, "wrong inverse of %d (%c)", ei, e),
                         ci, rotor.convertBackward(ei));
        }
    }

    /** Set the rotor to the one with given NAME and permutation as
     *  specified by the NAME entry in ROTORS, with given NOTCHES. */
    private void setRotor(String name, HashMap<String, String> rotors,
                               String notches) {
        rotor = new MovingRotor(name, new Permutation(rotors.get(name), UPPER),
                                notches);
    }

    private void setFixRotor(String name, HashMap<String, String> rotors) {
        rotor = new FixedRotor(name, new Permutation(rotors.get(name), UPPER));
    }
    /* ***** TESTS ***** */

    @Test
    public void fixedCheckRotorAtA() {
        setFixRotor("II", NAVALA);
        checkRotor("Rotor II (A)", UPPER_STRING, NAVALA_MAP.get("II"));
    }

    @Test
    public void fixedCheckRotorAdvance() {
        setFixRotor("II", NAVALB);
        rotor.advance();
        checkRotor("Rotor II advanced", UPPER_STRING, NAVALB_MAP.get("II"));
        setFixRotor("II", NAVALA);
        rotor.set(1);
        checkRotor("Rotor II advanced", UPPER_STRING, NAVALB_MAP.get("II"));
    }

    @Test
    public void fixedCheckRotorSet() {
        setFixRotor("II", NAVALZ);
        checkRotor("Rotor I set", UPPER_STRING, NAVALZ_MAP.get("II"));
        setFixRotor("II", NAVALA);
        rotor.set(25);
        checkRotor("Rotor II set", UPPER_STRING, NAVALZ_MAP.get("II"));
    }
    @Test
    public void checkRotorAtAII() {
        setRotor("II", NAVALA, "");
        checkRotor("Rotor II (A)", UPPER_STRING, NAVALA_MAP.get("II"));
    }

    @Test
    public void checkRotorAdvanceII() {
        setRotor("II", NAVALA, "");
        rotor.advance();
        checkRotor("Rotor II advanced", UPPER_STRING, NAVALB_MAP.get("II"));
    }

    @Test
    public void checkRotorSetII() {
        setRotor("II", NAVALA, "");
        rotor.set(25);
        checkRotor("Rotor II set", UPPER_STRING, NAVALZ_MAP.get("II"));
    }
    @Test
    public void checkRotorAtA() {
        setRotor("I", NAVALA, "");
        checkRotor("Rotor I (A)", UPPER_STRING, NAVALA_MAP.get("I"));
    }

    @Test
    public void checkRotorAdvance() {
        setRotor("I", NAVALA, "");
        rotor.advance();
        checkRotor("Rotor I advanced", UPPER_STRING, NAVALB_MAP.get("I"));
    }

    @Test
    public void checkRotorSet() {
        setRotor("I", NAVALA, "");
        rotor.set(25);
        checkRotor("Rotor I set", UPPER_STRING, NAVALZ_MAP.get("I"));
    }

}
