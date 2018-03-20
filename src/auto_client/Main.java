package auto_client;

import auto.soap.Autoverleih;
import auto.soap.AutoverleihSoapWebservice;
import auto.soap.Fahrzeug;
import auto.soap.Kunde;
import auto.soap.Leihvertrag;
import auto.soap.NotAvailableException_Exception;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;

/**
 *
 * @author Claudia
 */
public class Main {

    private final AutoverleihSoapWebservice ws;
    private final BufferedReader fkey;

    public static void main(String[] args) throws IOException, DatatypeConfigurationException, NotAvailableException_Exception {
        Main main = new Main();
        main.runMainMenu();
    }    

    public Main() {
        Autoverleih autoverleih = new Autoverleih();
        this.ws = autoverleih.getAutoverleihWebservicePort();
        this.fkey = new BufferedReader(new InputStreamReader(System.in));
    }

    public void runMainMenu() throws IOException, DatatypeConfigurationException, NotAvailableException_Exception {
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
    
        System.out.println("Fahrzeugverleih");
        
        // Bild
        
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

        System.out.println("Vorname: ");
        String vorname = this.fkey.readLine();

        System.out.println("Nachname: ");
        String nachname = this.fkey.readLine();

        System.out.println("Straße: ");
        String strasse = this.fkey.readLine();

        System.out.println("Hausnummer: ");
        String hausnummer = this.fkey.readLine();

        System.out.println("Postleitzahl: ");
        String plz = this.fkey.readLine();

        System.out.println("Ort: ");
        String ort = this.fkey.readLine();

        System.out.println("Land: ");
        String land = this.fkey.readLine();

        System.out.println();
        
        Kunde kunde = new Kunde();
        kunde.setVorname(vorname);
        kunde.setNachname(nachname);
        kunde.setStrasse(strasse);
        kunde.setHausnummer(hausnummer);
        kunde.setPlz(plz);
        kunde.setOrt(ort);
        kunde.setLand(land);

        Holder<Kunde> hKunde = new Holder<>(kunde);
        ws.createNewKunde(hKunde);

        System.out.println("Kunde mit der ID " + hKunde.value.getId() + " wurde angelegt.");
        System.out.println();
        
    }

    public void fahrzeugAnlegen() throws IOException, DatatypeConfigurationException {
        System.out.println("================");
        System.out.println("Fahrzeug anlegen");
        System.out.println("================");
        System.out.println();

        System.out.println("Hersteller :");
        String hersteller = this.fkey.readLine();

        System.out.println("Modell: ");
        String modell = this.fkey.readLine();

        System.out.println("Baujahr: ");
        String baujahr = this.fkey.readLine();

        System.out.println();
        
        Fahrzeug fahrzeug = new Fahrzeug();
        fahrzeug.setHersteller(hersteller);
        fahrzeug.setModell(modell);
        fahrzeug.setBaujahr(baujahr);

        Holder<Fahrzeug> hFahrzeug = new Holder<>(fahrzeug);
        ws.createNewFahrzeug(hFahrzeug);
        
        System.out.println("Fahrzeug mit der ID " + hFahrzeug.value.getId() + " wurde angelegt.");
        System.out.println();

    }

    public void fahrzeugAusleihen() throws IOException, DatatypeConfigurationException, NotAvailableException_Exception {
        System.out.println("================");
        System.out.println("Fahrzeug ausleihen");
        System.out.println("================");
        System.out.println();
        
        System.out.println("Folgende Fahzeuge stehen zur Verfügung:");
        
        List<Fahrzeug> alleFahrzeuge = ws.fahrzeugAuflisten();
        
        for (Fahrzeug fahrzeug: alleFahrzeuge) {
            System.out.print(fahrzeug.getHersteller());
            System.out.print(" " + fahrzeug.getModell() + ", Baujahr");
            System.out.print(" " + fahrzeug.getBaujahr() + ", ID");
            System.out.print(" " + fahrzeug.getId());
            System.out.println();   
        }
        
        System.out.print("Kundennummer:");
        Long kundeId = Long.parseLong(this.fkey.readLine());
        Kunde kunde = ws.findKundeById(kundeId);
        
        System.out.print("Fahrzeug-ID:");
        Long fahrzeugId = Long.parseLong(this.fkey.readLine());
        Fahrzeug fahrzeug = ws.findFahrzeugById(fahrzeugId);
          
              
        System.out.print("Abholung am (yyyy-mm-dd):");
        String beginndatumVon = this.fkey.readLine();
        DatatypeFactory dtf = DatatypeFactory.newInstance();
        XMLGregorianCalendar beginndatum = dtf.newXMLGregorianCalendar(beginndatumVon);
        
        System.out.print("Rückgabe am (yyyy-mm-dd):");
        String enddatumBis = this.fkey.readLine();
        DatatypeFactory dtf2 = DatatypeFactory.newInstance();
        XMLGregorianCalendar enddatum = dtf2.newXMLGregorianCalendar(enddatumBis);
        
        Holder<Leihvertrag> hLeihvertrag = new Holder<> (ws.createNewLeihvertrag(kunde, fahrzeug, beginndatum, enddatum));
        
        System.out.println("Alles klar! Leihvertrag mit der ID " + hLeihvertrag.value.getId() + " wurde angelegt.");

    }

    public void leihvertraegeAuflisten() throws IOException, DatatypeConfigurationException {
        
        System.out.println("================");
        System.out.println("Leihverträge auflisten");
        System.out.println("================");
        System.out.println();
        System.out.println("Folgende Leihverträge liegen vor:");
        System.out.println();

        List<Leihvertrag> leihvertraglist = this.ws.findAllLeihvertraege();

        int counter = 0;

        for (Leihvertrag leihvertrag : leihvertraglist) {
            Fahrzeug fahrzeug = ws.findFahrzeugById(leihvertrag.getFahrzeugid());
            Kunde kunde = ws.findKundeById(leihvertrag.getKundenid());

            System.out.println(++counter + ".)");
            System.out.println("  Fahrzeug: " + fahrzeug.getHersteller() + " " + fahrzeug.getModell() + ", Baujahr " + fahrzeug.getBaujahr());
            System.out.println("  Ausleihende Person: " + kunde.getVorname() + " " + kunde.getNachname());
            System.out.println("  Zeitraum: " + leihvertrag.getBeginndatum() + leihvertrag.getEnddatum());
            System.out.println();

        }
    }
}
