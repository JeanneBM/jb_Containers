/*
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class DaneZlozone {

    // Lista przechowująca taksony
    static ArrayList listaTaksonow = new ArrayList&lt;&gt;();
    static String sciezka;
    static String naglowki;

    public static void main(String[] args) {
        Character opcja = 'x';
        // Główna pętla programu
        while (!opcja.equals('k')) {
            // Jeżeli nie ma danych, to nalezy je najpierw wczytać
            if (listaTaksonow.isEmpty()) {
                listaTaksonow = czytajPlik();
            }
            // wydruk menu i pobranie decyzji użytkownika
            opcja = menu();

            switch (opcja) {
                case 'o':
                    listaTaksonow = czytajPlik();
                    break;
                case 'w':
                    wypiszDane();
                    break;
                case 'd':
                    dodajDane();
                    break;
                case 'u':
                    usunDane();
                    break;
                case 'z':
                    zapiszDane();
                    break;
                case 'k':
                    System.out.println("KONIEC");
                    break;
                default:
                    System.out.println("Nie rozumiem");
            }

        }
    }

    // Metoda wczytująca plik z danymi
    public static ArrayList czytajPlik() {
        Scanner skaner = new Scanner(System.in);
        System.out.println("Podaj ścieżkę do pliku: ");
        System.out.print("&gt; ");
        sciezka = skaner.nextLine();
        Path sciezkaDoPliku = Paths.get(sciezka);
        // Lista będzie przechowywała kolejne linie odczytane z pliku jako String
        ArrayList odczyt = new ArrayList();
        try {
            // Wczytanie wszystkich linii do listy
            odczyt = (ArrayList) Files.readAllLines(sciezkaDoPliku);
        } catch (IOException ex) {
            System.out.println("Brak pliku!");
        }

        // Pobranie pierwszej linii z nagłówkami...
        naglowki = odczyt.get(0);
        // ... następnie usunięcie jej
        odczyt.remove(0);
        // Wywołanie metody tworzącej obiekty i wypełniające je danymi z pliku
        ArrayList taksony = doObiektow(odczyt);
        return taksony;
    }

    // Metoda pobiera po serii danych, odpowiadających linii w pliku 
    // a następnie tworzy obiekty typu Takson i wypełnia je danymi
    public static ArrayList doObiektow(ArrayList odczyt) {
        ArrayList taksony = new ArrayList&lt;&gt;();
        for (String linia : odczyt) {
            // Dzielenie na poszczególne pola (oodzielone przecinkami)
            String[] l = linia.split(",");
            // Tworzenie obiektu i wywołanie konstruktora, który przyjmuje 
            // jako argumenty dane
            Takson takson = new Takson(
                    Integer.parseInt(l[0]), // nr
                    l[1], // rodzaj
                    l[2], // gatunek
                    Integer.parseInt(l[3]), // n2
                    Integer.parseInt(l[4]) // x
            );
            taksony.add(takson);
        }
        return taksony;
    }

    // Wypisanie danych
    public static void wypiszDane() {
        // Zamiana przecinków na tabulatory w nagłówku - bardziej przejrzyste
        System.out.println(naglowki.replace(",", "\t"));
        System.out.println("===============================================");
        // Pobieranie każdego taksonu i wywołanie metody toString przyjmującej
        // jako argument rodzaj reparatora do wyświetlenia, tu jest to tabulator
        for (Takson takson : listaTaksonow) {
            System.out.println(takson.toString("\t"));
        }
    }

    // Dodane nowego taksonu
    public static void dodajDane() {
        // numer nowego taksonu powinien być o 1 większy niż
        // najwyższy obecnie istniejący
        int nowyId = znajdzMaxId() + 1;
        // Tworzenie obiektu, wykorzystanie konstruktora przyjmującego
        // jako argument numer
        Takson nowy = new Takson(nowyId);
        Scanner skaner = new Scanner(System.in);
        System.out.println("Podaj rodzaj: ");
        nowy.setRodzaj(skaner.nextLine());
        System.out.println("Podaj gatunek: ");
        nowy.setGatunek(skaner.nextLine());
        System.out.println("Podaj 2n: ");
        nowy.setN2(Integer.parseInt(skaner.nextLine()));
        System.out.println("Podaj x: ");
        nowy.setX(Integer.parseInt(skaner.nextLine()));

        // Dodanie taksonu do listy
        listaTaksonow.add(nowy);
    }

    // Metoda wyszukuje największy numer, przyda się do wyznaczenia numeru
    // dla nowego taksonu
    public static int znajdzMaxId() {
        int max = 0;
        for (Takson t : listaTaksonow) {
            if (t.getNr() &gt; max) {
                max = t.getNr();
            }
        }
        return max;
    }

    // Wyświetlenie menu i pobranie decyzji od użytkownika
    public static Character menu() {
        Scanner skaner = new Scanner(System.in);
        Character opcja = 'x';
/*
        // Pętla działa dopóki uzytkownik nie wybierze zrozumiałej opcji
        while ((!opcja.equals('o') &amp;&amp; !opcja.equals('w') &amp;&amp; !opcja.equals('d')
                &amp;&amp; !opcja.equals('u')) &amp;&amp; !opcja.equals('z')
                &amp;&amp; !opcja.equals('k')) {
            System.out.println(" *** Menu *** ");
            System.out.println("o - otwórz plik");
            System.out.println("w - wypisz dane");
            System.out.println("d - dodaj takson");
            System.out.println("u - usuń takson");
            System.out.println("z - zapisz dane");
            System.out.println("k - koniec");
            System.out.print("&gt; ");
            opcja = skaner.next().charAt(0);
        }
        return opcja;
    }

    // Usuwanie taksonu z listy
    private static void usunDane() {
        wypiszDane();
        System.out.println("Podaj numer taksonu, który chcesz usunąć");
        Scanner skaner = new Scanner(System.in);
        int numer = Integer.parseInt(skaner.nextLine());
        boolean usuniety = false;

        // Wyszukanie taksonu
        for (int i = 0; i &lt; listaTaksonow.size(); i++) {
            Takson takson = listaTaksonow.get(i);
            int numerTaksonu = takson.getNr();
            if (numerTaksonu == numer) {
                listaTaksonow.remove(i);
                // zaznacz jeśli dokonane zostało usunięcie
                usuniety = true;
                break;
            }
        }
        if (usuniety) {
            System.out.println("Takson nr. " + numer + "usuniety");
        } else {
            System.out.println("Nie ma taksonu o numerze " + numer);
        }

    }

    // Zapis danych
    private static void zapiszDane() {

        Path sciezkaDoPliku = Paths.get(sciezka);

        System.out.println("Podaj ścieżkę i plik do zapisu \n"
                + "Jeśli chcesz zapisać do bieżącego pliku: \n"
                + sciezka + "\n wciśnij enter");

        Scanner skaner = new Scanner(System.in);
        String sc = skaner.nextLine();

        // Jeśli użytkownik wpisał ścieżkę
        if (sc.length() &gt; 0) {
            sciezkaDoPliku = Paths.get(sc);
        }

        ArrayList out = new ArrayList();
        out.add(naglowki);
        for (Takson takson : listaTaksonow) {
            out.add(takson.toString(","));
        }
        try {
            Files.write(sciezkaDoPliku, out);
        } catch (IOException ex) {
            System.out.println("Niestety, nie mogę utworzyć pliku!");
        }
    }
}
*/
