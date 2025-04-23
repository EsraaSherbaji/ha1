package htw.berlin.prog2.ha1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Retro calculator")
class CalculatorTest {

    @Test
    @DisplayName("should display result after adding two positive multi-digit numbers")
    void testPositiveAddition() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(2);
        calc.pressDigitKey(0);
        calc.pressBinaryOperationKey("+");
        calc.pressDigitKey(2);
        calc.pressDigitKey(0);
        calc.pressEqualsKey();

        String expected = "40";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("should display result after getting the square root of two")
    void testSquareRoot() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(2);
        calc.pressUnaryOperationKey("√");

        String expected = "1.41421356";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("should display error when dividing by zero")
    void testDivisionByZero() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(7);
        calc.pressBinaryOperationKey("/");
        calc.pressDigitKey(0);
        calc.pressEqualsKey();

        String expected = "Error";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }


    @Test
    @DisplayName("should not allow multiple decimal dots")
    void testMultipleDecimalDots() {
        Calculator calc = new Calculator();

        calc.pressDigitKey(1);
        calc.pressDotKey();
        calc.pressDigitKey(7);
        calc.pressDotKey();
        calc.pressDigitKey(8);

        String expected = "1.78";
        String actual = calc.readScreen();

        assertEquals(expected, actual);
    }


    //TODO hier weitere Tests erstellen

    @Test
    void NeuerTest() {
        Calculator calc = new Calculator();
        calc.pressDigitKey(2);
        calc.pressBinaryOperationKey("+");
        calc.pressDigitKey(3);
        calc.pressEqualsKey();

        String erwartet = "5";
        String wirklich = calc.readScreen();

        if (!wirklich.equals(erwartet)) {
            System.out.println("FEHLER: Erwartet '" + erwartet + "', aber war '" + wirklich + "'");
        } else {
            System.out.println("Test bestanden ✅");
        }

        assertEquals(erwartet, wirklich);
    }

    @Test
    void testClearKeyLoeschtNichtAlles() {
        Calculator calc = new Calculator();
        calc.pressDigitKey(8);
        calc.pressBinaryOperationKey("+");
        calc.pressDigitKey(3);
        calc.pressClearKey(); // Bildschirm soll gelöscht werden, nicht die Rechnung
        calc.pressDigitKey(4);
        calc.pressEqualsKey();

        String erwartet = "12";
        String wirklich = calc.readScreen();

        if (!wirklich.equals(erwartet)) {
            System.out.println("FEHLER: Erwartet '" + erwartet + "', aber war '" + wirklich + "'");
        } else {
            System.out.println("Test bestanden ✅");
        }
        assertEquals(erwartet, wirklich);
    }


    @Test
    void testKehrwertVonNullGibtFehler() {
        Calculator calc = new Calculator();
        calc.pressDigitKey(0);
        calc.pressUnaryOperationKey("1/x");

        String erwartetesErgebnis = "Error";
        String wirklichesErgebnis = calc.readScreen();

        if (!wirklichesErgebnis.equals(erwartetesErgebnis)) {
            System.out.println("FEHLER: Erwartet '" + erwartetesErgebnis + "', aber war '" + wirklichesErgebnis + "'");
        } else {
            System.out.println("Test bestanden ✅");
        }
        assertEquals(erwartetesErgebnis, wirklichesErgebnis);
    }
}


