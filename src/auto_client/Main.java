package auto_client;

import auto_server.ejb.Fahrzeug;
import auto_server.ejb.Kunde;
import auto_server.soap.AutoServerSoapWebservice;
import auto_server.soap.Auto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.ws.Holder;

/**
 *
 * @author Claudia
 */
public class Main {

    private final AutoServerSoapWebservice ws;
    private final BufferedReader fkey;

    public static void main(String[] args) throws IOException, DatatypeConfigurationException {
        Main main = new Main();
        main.runMainMenu();
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
    }

    public Main() {
        Auto auto = new Auto();
        this.ws = Auto.getFahrzeugverleihSoapWebservicePort();
        this.fkey = new BufferedReader(new InputStreamReader(System.in));
    }

    public void runMainMenu() throws IOException, DatatypeConfigurationException {
        System.out.println("GANz Ganz tolles Programm");
        boolean quit = false;

        while (!quit) {
            System.out.println("=========");
            System.out.println("Hauptmenü");
            System.out.println("=========");
            System.out.println();

            System.out.println("  [K] Kunde anlegen");
            System.out.println("  [F] Fahrzeug Anlegen");
            System.out.println("  [A] Fahrzeug ausleihen");
            System.out.println("  [L] Leihverträge auflisten");
            System.out.println("  [E] Ende");
            System.out.println();

            System.out.print("Deine Auswahl: ");
            String command = this.fkey.readLine();
            System.out.println();

            switch (command.toUpperCase()) {
                case "K": //Kunde anlegen
                    this.kundeAnlegen();
                    break;
                case "F": //Fahrzeug anlegen
                    this.fahrzeugAnlegen();
                    break;
                case "A": //Fahrzeug ausleihen
                    this.fahrzeugAusleihen();
                    break;
                case "L": //Leihverträge auflsiten
                    this.leihvertraegeAuflisten();
                    break;
                case "E": //quitten
                    quit = true;
                    break;
                default:
                    System.out.println("Sorry, ich habe dich nicht verstanden …");
                    System.out.println();
            }

        }
    }

    public void kundeAnlegen() throws IOException, DatatypeConfigurationException {
        System.out.println("================");
        System.out.println("Kunde anlegen");
        System.out.println("================");
        System.out.println();

        System.out.println("Bitte geben Sie den Vornamen ein");
        String vorname = this.fkey.readLine();

        System.out.println("Bitte geben Sie den Nachname ein");
        String nachname = this.fkey.readLine();

        System.out.println("Bitte geben Sie den Straße ein");
        String strasse = this.fkey.readLine();

        System.out.println("Bitte geben Sie die Hausnummer ein");
        String hausnummer = this.fkey.readLine();

        System.out.println("Bitte geben Sie die PLZ ein");
        String plz = this.fkey.readLine();

        System.out.println("Bitte geben Sie den Ort ein");
        String ort = this.fkey.readLine();

        System.out.println("Bitte geben Sie das Land ein");
        String land = this.fkey.readLine();

        Kunde kunde = new Kunde();
        kunde.setVorname(vorname);
        kunde.setNachname(nachname);
        kunde.setStrasse(strasse);
        kunde.setHausnummer(hausnummer);
        kunde.setPlz(plz);
        kunde.setOrt(ort);

        Holder<Kunde> hKunde = new Holder<>(kunde);
        ws.createNewKunde(hKunde);

    }

    public void fahrzeugAnlegen() throws IOException, DatatypeConfigurationException {
        System.out.println("================");
        System.out.println("Fahrzeug anlegen");
        System.out.println("================");
        System.out.println();

        System.out.println("Bitte geben Sie den Hersteller ein");
        String hersteller = this.fkey.readLine();

        System.out.println("Bitte geben Sie das Modell ein");
        String modell = this.fkey.readLine();

        System.out.println("Bitte geben Sie das Baujahr ein");
        String baujahr = this.fkey.readLine();

        Fahrzeug fahrzeug = new Fahrzeug();
        fahrzeug.setHersteller(hersteller);
        fahrzeug.setModell(modell);
        fahrzeug.setBaujahr(baujahr);

        Holder<Fahrzeug> hFahrzeug = new Holder<>(fahrzeug);
        ws.createNewFahrzeug(hFahrzeug);

    }

    public void fahrzeugAusleihen() throws IOException, DatatypeConfigurationException {

    }

    public void leihvertraegeAuflisten() throws IOException, DatatypeConfigurationException {

    }
}
