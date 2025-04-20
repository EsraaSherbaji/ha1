package htw.berlin.prog2.ha1;

/**
 * Eine Klasse, die das Verhalten des Online Taschenrechners imitiert, welcher auf
 * https://www.online-calculator.com/ aufgerufen werden kann (ohne die Memory-Funktionen)
 * und dessen Bildschirm bis zu zehn Ziffern plus einem Dezimaltrennzeichen darstellen kann.
 * Enthält mit Absicht noch diverse Bugs oder unvollständige Funktionen.
 */
public class Calculator {

    private String screen = "0";

    private double latestValue;

    private String latestOperation = "";

    /**
     * @return den aktuellen Bildschirminhalt als String
     */
    public String readScreen() {
        return screen;
    }

    /**
     * Empfängt den Wert einer gedrückten Zifferntaste. Da man nur eine Taste auf einmal
     * drücken kann muss der Wert positiv und einstellig sein und zwischen 0 und 9 liegen.
     * Führt in jedem Fall dazu, dass die gerade gedrückte Ziffer auf dem Bildschirm angezeigt
     * oder rechts an die zuvor gedrückte Ziffer angehängt angezeigt wird.
     * @param digit Die Ziffer, deren Taste gedrückt wurde
     */
    public void pressDigitKey(int digit) {
        if(digit > 9 || digit < 0) throw new IllegalArgumentException();

        if(screen.equals("0") || latestValue == Double.parseDouble(screen)) screen = "";

        screen = screen + digit;
    }

    /**
     * Führt die Funktion der C- oder CE-Taste aus:
     *
     * - Beim ersten Drücken wird nur der Bildschirm gelöscht (zeigt "0"),
     *   gespeicherte Werte und Operationen bleiben erhalten.
     *
     * - Beim zweiten Drücken hintereinander wird alles zurückgesetzt:
     *   Bildschirm, gespeicherte Werte und letzte Operation.
     */

    private boolean clearPressedOnce = false;

    public void pressClearKey() {
        if (!clearPressedOnce) {
            screen = "0";
            clearPressedOnce = true;
        } else {
            screen = "0";
            latestOperation = "";
            latestValue = 0.0;
            clearPressedOnce = false;
        }
    }


    /**
     * Empfängt den Wert einer gedrückten binären Operationstaste, also eine der vier Operationen
     * Addition, Substraktion, Division, oder Multiplikation, welche zwei Operanden benötigen.
     * Beim ersten Drücken der Taste wird der Bildschirminhalt nicht verändert, sondern nur der
     * Rechner in den passenden Operationsmodus versetzt.
     * Beim zweiten Drücken nach Eingabe einer weiteren Zahl wird direkt des aktuelle Zwischenergebnis
     * auf dem Bildschirm angezeigt. Falls hierbei eine Division durch Null auftritt, wird "Error" angezeigt.
     * @param operation "+" für Addition, "-" für Substraktion, "x" für Multiplikation, "/" für Division
     */
    public void pressBinaryOperationKey(String operation)  {
        latestValue = Double.parseDouble(screen);
        latestOperation = operation;
    }
    /**
     * Rechnet Wurzel, Prozent oder Kehrwert vom aktuellen Wert.
     * Wenn "1/x" mit 0 gemacht wird, zeigt der Bildschirm "Error".
     */
    public void pressUnaryOperationKey(String operation) {
        double value = Double.parseDouble(screen);
        double result = 0;

        if (operation.equals("√")) {
            result = Math.sqrt(value);
        } else if (operation.equals("%")) {
            result = value / 100;
        } else if (operation.equals("1/x")) {
            if (value == 0) {
                screen = "Error";
                return;
            } else {
                result = 1 / value;
            }
        } else {
            screen = "Error";
            return;
        }

        screen = Double.toString(result);
    }



    /**
     * Empfängt den Befehl der gedrückten Dezimaltrennzeichentaste, im Englischen üblicherweise "."
     * Fügt beim ersten Mal Drücken dem aktuellen Bildschirminhalt das Trennzeichen auf der rechten
     * Seite hinzu und aktualisiert den Bildschirm. Daraufhin eingegebene Zahlen werden rechts vom
     * Trennzeichen angegeben und daher als Dezimalziffern interpretiert.
     * Beim zweimaligem Drücken, oder wenn bereits ein Trennzeichen angezeigt wird, passiert nichts.
     */
    public void pressDotKey() {
        if(!screen.contains(".")) screen = screen + ".";
    }

    /**
     * Empfängt den Befehl der gedrückten Vorzeichenumkehrstaste ("+/-").
     * Zeigt der Bildschirm einen positiven Wert an, so wird ein "-" links angehängt, der Bildschirm
     * aktualisiert und die Inhalt fortan als negativ interpretiert.
     * Zeigt der Bildschirm bereits einen negativen Wert mit führendem Minus an, dann wird dieses
     * entfernt und der Inhalt fortan als positiv interpretiert.
     */
    public void pressNegativeKey() {
        screen = screen.startsWith("-") ? screen.substring(1) : "-" + screen;
    }

    /**
     * Empfängt den Befehl der gedrückten "="-Taste.
     * Wurde zuvor keine Operationstaste gedrückt, passiert nichts.
     * Wurde zuvor eine binäre Operationstaste gedrückt und zwei Operanden eingegeben, wird das
     * Ergebnis der Operation angezeigt. Falls hierbei eine Division durch Null auftritt, wird "Error" angezeigt.
     * Wird die Taste weitere Male gedrückt (ohne andere Tasten dazwischen), so wird die letzte
     * Operation (ggf. inklusive letztem Operand) erneut auf den aktuellen Bildschirminhalt angewandt
     * und das Ergebnis direkt angezeigt.
     */
    public void pressEqualsKey() {
        double result = 0;

        // Berechne das Ergebnis je nach gewählter Operation
        switch(latestOperation) {
            case "+":
                result = latestValue + Double.parseDouble(screen);
                break;
            case "-":
                result = latestValue - Double.parseDouble(screen);
                break;
            case "x":
                result = latestValue * Double.parseDouble(screen);
                break;
            case "/":
                // Prüfen auf Division durch 0
                if (Double.parseDouble(screen) == 0) {
                    screen = "Error";  // Division durch Null ergibt Error
                    return;
                }
                result = latestValue / Double.parseDouble(screen);
                break;
            default:
                // Wenn keine gültige Operation gesetzt ist, gebe einen Fehler aus
                throw new IllegalArgumentException("Ungültige Operation");
        }

        // Setze das Ergebnis als Bildschirmwert
        screen = Double.toString(result);

        // Entferne unnötige Nachkommastellen (z. B. ".0")
        if (screen.endsWith(".0")) {
            screen = screen.substring(0, screen.length() - 2);
        }

        // Wenn das Ergebnis zu lang ist, beschränke es auf 10 Zeichen
        if (screen.contains(".") && screen.length() > 11) {
            screen = screen.substring(0, 10);
        }

        // Falls das Ergebnis "Infinity" oder "NaN" ist, zeige "Error" an
        if (screen.equals("Infinity") || screen.equals("NaN")) {
            screen = "Error";
        }
    }

}
