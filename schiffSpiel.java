import java.util.Scanner;

public class schiffSpiel {
    public static void main(String args[]) throws Exception {
        Scanner scanner = new Scanner(System.in);
        int schiffe[][] = new int[10][10];
        int spieler[][] = new int[10][10];
        int schiff[] = {0, 4, 3, 2, 1};
        int row = 10;
        int col = 10;
        int schiffZahl = 6;

        // Sechs Schiffseingaben vom Benutzer anfordern
        for (int i = 0; i < schiffZahl; i++) {
            System.out.println("Schiff #" + (i + 1) + " den Standort festlegen:");
            schiffId(scanner, schiffe);
            spielPrint(schiffe);
        }
    }

    // Eine Methode, die es dem Benutzer ermöglicht, das Schiff zu identifizieren
    public static void schiffId(Scanner scanner, int[][] schiffe) {
        int row = schiffe.length;
        int col = schiffe[0].length;

        System.out.println("Geben Sie die Startposition an (x- und y-Koordinaten), ab 0");
        int x = scanner.nextInt();
        int y = scanner.nextInt();

        System.out.println("Geben Sie die Schiffslänge an (1-4): ");
        int länge = scanner.nextInt();

        System.out.println("Geben Sie die Schiffsausrichtung an (vertikal/horizontal):");
        String ausrichtung = scanner.next();

        try {
            platzierenSchiff(schiffe, x, y, ausrichtung, länge);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            schiffId(scanner, schiffe);
        }
    }

    // Verfahren zum Drucken der Spielkarte auf dem Terminal
    public static void spielPrint(int[][] karte) {
        for (int[] spalte : karte) {
            for (int zelle : spalte) {
                if (zelle == 0)
                    System.out.print(". "); // Punkt für leere Zellen
                else
                    System.out.print("* "); // Stern für Schiffsteile
            }
            System.out.println();
        }
        System.out.println();
    }

    // Methode zur Überprüfung, ob die Position zu einem Schiff gehört
    public static boolean istSchiffslage(int[][] schiffe, int spalte, int zeile) {
        return schiffe[spalte][zeile] != 0;
    }

    // Methode zur Platzierung des Schiffes auf der Spielkarte
    public static void platzierenSchiff(int[][] schiffe, int startSpalte, int startZeile, String ausrichtung, int länge) {
        int row = schiffe.length;
        int col = schiffe[0].length;

        // Die Größe der Schiffe wird kontrolliert
        if (länge < 1 || länge > 4) {
            throw new IllegalArgumentException("Ungültige Schiffslänge: " + länge);
        }

        // Überprüft die Startposition
        if (startSpalte < 0 || startSpalte >= row || startZeile < 0 || startZeile >= col) {
            throw new IllegalArgumentException("Die Ausgangsposition ist außerhalb der Spielkarte.");
        }

        // Richtung wird geprüft
        if (!ausrichtung.equalsIgnoreCase("vertikal") && !ausrichtung.equalsIgnoreCase("horizontal")) {
            throw new IllegalArgumentException("Ungültige Ausrichtung: " + ausrichtung);
        }

        // Es wird geprüft, ob genügend Platz zum Andocken des Schiffes vorhanden ist
        if (ausrichtung.equalsIgnoreCase("vertikal")) {
            if (startSpalte + länge > row)
                throw new IllegalArgumentException("Das Schiff entfernt sich aus dem Gebiet.");
            for (int i = startSpalte; i < startSpalte + länge; i++) {
                if (schiffe[i][startZeile] != 0)
                    throw new IllegalArgumentException("Gemi alan dışına taşıyor.");
            }
            for (int i = startSpalte; i < startSpalte + länge; i++) {
                schiffe[i][startZeile] = länge;
            }
        } else if (ausrichtung.equalsIgnoreCase("horizontal")) {
            if (startZeile + länge > col)
                throw new IllegalArgumentException("Das Schiff entfernt sich aus dem Gebiet.");
            for (int i = startZeile; i < startZeile + länge; i++) {
                if (schiffe[startSpalte][i] != 0)
                    throw new IllegalArgumentException("Das Schiff entfernt sich aus dem Gebiet.");
            }
            for (int i = startZeile; i < startZeile + länge; i++) {
                schiffe[startSpalte][i] = länge;
            }
        }
    }
}


