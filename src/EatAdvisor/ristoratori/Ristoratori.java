package EatAdvisor.ristoratori;
import EatAdvisor.EatAdvisor;
import EatAdvisor.Giudizio;

import java.io.*;
import java.util.Scanner;

public class Ristoratori extends EatAdvisor implements java.io.Serializable {

    private String nome;
    private String tipoIndirizzo;
    private String nomeIndirizzo;
    private String civico;
    private String comune;
    private String provincia;
    private String cap;
    private String telefono;
    private String url;
    private String tipologia;
    private Giudizio[] giudizi = null;

    public Ristoratori (String nome, String tipoIndirizzo, String nomeIndirizzo, String civico, String comune,
                       String provincia, String cap, String telefono, String url, String tipologia) {

        this.nome = nome;
        this.tipoIndirizzo = tipoIndirizzo;
        this.nomeIndirizzo = nomeIndirizzo;
        this.civico = civico;
        this.comune = comune;
        this.provincia = provincia;
        this.cap = cap;
        this.telefono = telefono;
        this.url = url;
        this.tipologia = tipologia;
    }

    public String getNome() {
        return nome;
    }

    public String getTipoIndirizzo() {
        return tipoIndirizzo;
    }

    public String getNomeIndirizzo() {
        return nomeIndirizzo;
    }

    public String getCivico() {
        return civico;
    }

    public String getComune() {
        return comune;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getCap() {
        return cap;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getUrl() {
        return url;
    }

    public String getTipologia() {
        return tipologia;
    }

    public Giudizio[] getGiudizi() {
        return giudizi;
    }

    public void addGiudizio(Giudizio g) {
        if (giudizi == null) {
            giudizi = new Giudizio[1];
            giudizi[0] = g;
        } else {
            Giudizio[] newGiudizi = new Giudizio[giudizi.length+1];
            for (int i = 0; i < newGiudizi.length; i++) {
                if (i == 0) {
                    newGiudizi[i] = g;
                } else {
                    newGiudizi[i] = giudizi[i-1];
                }
            }
            giudizi = newGiudizi;
        }
    }

    private void registraRistorante () {
        String filename = "data/EatAdvisor.dati";
        File f = new File(filename);
        if (f.exists() && !f.isDirectory()) {
            try {
                // lettura array ristoratori da EatAdvisor.dati
                FileInputStream fileInput = new FileInputStream(filename);
                ObjectInputStream in = new ObjectInputStream(fileInput);

                Ristoratori[] ristoratoriTemp = (Ristoratori[]) in.readObject();

                in.close();
                fileInput.close();

                Ristoratori[] ristoratori = new Ristoratori[ristoratoriTemp.length+1];

                for (int i = 0; i < ristoratoriTemp.length; i++) {
                    if (ristoratoriTemp[i].nome.equals(this.nome) && ristoratoriTemp[i].tipoIndirizzo.equals(this.tipoIndirizzo) &&
                            ristoratoriTemp[i].nomeIndirizzo.equals(this.nomeIndirizzo) &&
                            ristoratoriTemp[i].civico.equals(this.civico) && ristoratoriTemp[i].comune.equals(this.comune)) {
                        System.out.println("E' gia' presente un ristorante con questo nome a questo indirizzo.\n");
                        throw new Exception();
                    } else {
                        ristoratori[i] = ristoratoriTemp[i];
                    }
                }

                // array ristoratori deserializzato

                ristoratori[ristoratori.length - 1] = this;
                sortRistorantiNome(ristoratori);

                FileOutputStream fileOutput = new FileOutputStream(filename);
                ObjectOutputStream out = new ObjectOutputStream(fileOutput);

                // serializzazione oggetto nel file EatAdvisor.dati
                out.writeObject(ristoratori);

                out.close();
                fileOutput.close();

                System.out.println("Dati inseriti con successo!\n");

            } catch (Exception e) {
                System.out.println("Dati non inseriti");
            }
        } else {
            try {
                Ristoratori[] ristoratori = new Ristoratori[1];
                ristoratori[0] = this;

                FileOutputStream fileOutput = new FileOutputStream(filename);
                ObjectOutputStream out = new ObjectOutputStream(fileOutput);

                // serializzazione oggetto nel file EatAdvisor.dati
                out.writeObject(ristoratori);

                out.close();
                fileOutput.close();

                System.out.println("Dati inseriti con successo!\n");

            } catch (Exception e) {
                System.out.println("Dati non inseriti");
            }
        }
    }

    public void aggiorna() {
        String filename = "data/EatAdvisor.dati";
        File f = new File(filename);
        if (f.exists() && !f.isDirectory()) {
            try {
                // lettura array ristoratori da EatAdvisor.dati
                FileInputStream fileInput = new FileInputStream(filename);
                ObjectInputStream in = new ObjectInputStream(fileInput);

                Ristoratori[] ristoratori = (Ristoratori[]) in.readObject();

                in.close();
                fileInput.close();

                for (int i = 0; i < ristoratori.length; i++) {
                    if (ristoratori[i].nome.equals(this.nome)) {
                        ristoratori[i] = this;
                    }
                }

                // array ristoratori deserializzato

                FileOutputStream fileOutput = new FileOutputStream(filename);
                ObjectOutputStream out = new ObjectOutputStream(fileOutput);

                // serializzazione oggetto nel file EatAdvisor.dati
                out.writeObject(ristoratori);

                out.close();
                fileOutput.close();

                System.out.println("Giudizio inserito con successo!\n");

            } catch (Exception e) {
                System.out.println("Giudizio non inserito");
            }
        } else {
            System.out.println("Qualcosa e' andato storto e non e' stato possibile inserire il giudizio.");
        }
    }

    public static void main(String[] args) {
        boolean finish = false;

        while(!finish) {
        System.out.println("Gentile ristoratore, benvenuto in EatAdvisor, versione Ristoratore!\n\n" +
                "Per inserire un ristorante, inserire 1\nPer uscire, inserire 0\n");
            Scanner input = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
            int n = Integer.parseInt(input(input, 0));


            switch (n) {
                case 0: finish = true; break;
                case 1:
                    boolean ok = false;
                    String nome = "";
                    String tipoIndirizzo = "";
                    String nomeIndirizzo = "";
                    String civico = "";
                    String comune = "";
                    String provincia = "";
                    String cap = "";
                    String telefono = "";
                    String url = "";
                    String tipologiaRistorante = "";

                    while (!ok) {
                        System.out.println("\nHa scelto di inserire un ristorante.");
                        System.out.println("Inserire il nome del ristorante: ");
                        nome = input(input, 2);
                        System.out.println("\nOk!\nInserire la tipologia di indirizzo (via, viale, corso, piazza, ...): ");
                        tipoIndirizzo = input(input, 3);
                        System.out.println("\nOk!\nInserire l'indirizzo del ristorante: ");
                        nomeIndirizzo = input(input, 4);
                        System.out.println("\nOk!\nInserire il numero civico del ristorante (42, 42A, 42B, ...): ");
                        civico = input(input, 5);
                        System.out.println("\nOk!\nInserire il comune del ristorante (Varese, Milano, Roma, ...): ");
                        comune = input(input, 6);
                        System.out.println("\nOk!\nInserire la sigla della provincia in cui si trova " +
                                "il comune del ristorante (VA, MI, RM, ...): ");
                        provincia = input(input, 7);
                        System.out.println("\nOk!\nInserire il CAP del comune " +
                                "dove si trova il ristorante (21100, 20100, 00100, ...): ");
                        cap = input(input, 8);
                        System.out.println("\nOk!\nInserire il numero di telefono del ristorante " +
                                "(+39 1234567890, 1234567890, 0332 123456): ");
                        telefono = input(input, 9);
                        System.out.println("\nOk!\nInserire la url del sito web del ristorante: ");
                        url = input(input, 10);
                        System.out.println("\nOk!\nInserire la tipologia del del ristorante (italiano, etnico, fusion): ");
                        tipologiaRistorante = input(input, 11);

                        System.out.println("\nDati inseriti:");
                        System.out.println("Nome: " + nome + "\nIndirizzo: " + tipoIndirizzo + " " + nomeIndirizzo + " " +
                                civico + ", " + comune + ", " + provincia + ", " + cap + "\nTelefono: " + telefono +
                                "\nSito web: " + url + "\nTipologia: " + tipologiaRistorante + "\n");
                        System.out.println("Inserire 1 per confermare, inserire qualunque altro carattere per reinserire: ");

                        if (input.nextLine().equals("1")) ok = true;
                    }

                    Ristoratori r = new Ristoratori(nome, tipoIndirizzo, nomeIndirizzo, civico, comune, provincia, cap, telefono, url, tipologiaRistorante);

                    r.registraRistorante();
                    break;
            }
        }

    }
}