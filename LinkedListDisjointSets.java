package MyFirstProject;

/**
 * Libreria utilizzate
 * <p>
 * 1) LinkedList Usata per la gestione del magazzino
 * di insiemi disgniunti singoletti che andavano uniti
 * 2) Set per poter salvare gruppi di  variabili richieste dalle API
 * come esempio i vari rappresentant degl'elementi presenti sulla LinkedList
 * oppure anche i vari elementi che erano presenti nei vari insiemi disgiunti
 **/

import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;

/**
 * @author Danilo Quattrini  danilo.quattrini@studenti.unicam.it
 * <p>
 * REGOLA DEL RAPPRESENTANTE --Questo per ricordarmi della regola non legga--
 * se si chiede il rappresentante di un insieme disgiunto due volte e,
 * tra le due richieste, non è stata fatta nessuna modifica all’insieme stesso allora il rappresentante
 * deve risultare essere lo stesso elemento. Se invece l’insieme viene modificato allora il rappresentante
 * può cambiare.
 * <p>
 * I COMMENTI SONO PRESENTI SU OGNI REDIFIZIONE IMPLEMENTATA
 **/
public class LinkedListDisjointSets implements DisjointSets {

    /**
     * Classe che utilizzo per poter far si
     * che possa inserire tutti i vari insiemi disgniunti e i loro rappresentanti
     */
    private LinkedList<DisjointSetElement> InsiemdegliInsiemi;

    /**
     * Crea una collezione vuota di insiemi disgiunti.
     */
    public LinkedListDisjointSets() {
        InsiemdegliInsiemi = new LinkedList<>();
    }

    /*
     * Nella rappresentazione con liste concatenate un elemento è presente in
     * qualche insieme disgiunto se il puntatore al suo elemento rappresentante
     * (ref1) non è null.
     */

    /**
     * Semplice controllo che vado
     * a prendere il getRef1 della classe MyIntLinkedListDisjoinedSetElement
     * e restituisco
     *
     * @return true --> se il riferimento1 è diverso da null
     * else
     * @return false --> se il riferimento2 è uguale a null
     **/
    @Override
    public boolean isPresent(DisjointSetElement e) {
        return e.getRef1() != null;
    }

    /*
     * Nella rappresentazione con liste concatenate un nuovo insieme disgiunto è
     * rappresentato da una lista concatenata che contiene l'unico elemento. Il
     * rappresentante deve essere l'elemento stesso e la cardinalità deve essere
     * 1.
     */

    /**
     * Semplice creazione dove prima controllo che elemento {@code e}
     * sia uguale a null per lanciare l'eccezione
     *
     * @throws new NullPointerException();
     *             se non la lancia allara controllo all'interno della mia LinkedList nominata
     *             {@parm InsiemedegliInsiemi}.
     *             Svolgo il controllo andando a svolgere un for-each e controllando che ogni
     *             elemento al suo interno sia diverso da quello che vorrei inserire, se lo è allora
     *             lancia una
     * @throws new IllegalArgumentException();
     *             <p>
     *             assegno all'elemento il suo rappresentante e un valore iniziale che sarebbe
     *             la sua dimensione {@param size} e poi  vado ad aggiungere l'elemento
     *             {@param e} all'interno della LinkedList
     **/
    @Override
    public void makeSet(DisjointSetElement e) {

        if (e == null) throw new NullPointerException("Elemento che si vuole mettere è nullo");
        // Verifica se l'elemento è già presente in un insieme disgiunto
        for (DisjointSetElement insiemedisgiunto : InsiemdegliInsiemi) {
            if (insiemedisgiunto == e)
                throw new IllegalArgumentException("L'elemento è già presente in un insieme disgiunto");
        }
        e.setRef1(e);
        e.setNumber(1);
        InsiemdegliInsiemi.add(e);
    }

    /*
     * Nella rappresentazione con liste concatenate per trovare il
     * rappresentante di un elemento basta far riferimento al suo puntatore
     * ref1.
     */

    /**
     * Analisi FindSet
     * <p>
     * il find set fa sempre il controllo iniziale per vedere
     * se e == a null e lancia l'eccezzione di NullPointerException.
     * Nel for each scorra la LinkedList e ogni volta che l'elemento interno
     * alla linked list è uguale a quello passato svolge una
     *
     * @return insiemedisgiunti.getRef1() --> dove si va a stampare
     * la medesima referenza dell'elemento che si è trovato
     **/
    @Override
    public DisjointSetElement findSet(DisjointSetElement e) {
        if (e == null) throw new NullPointerException("Elemento che si vuole mettere è nullo");

        for (DisjointSetElement insiemedisgiunti : InsiemdegliInsiemi) {
            if (insiemedisgiunti == e) {
                return insiemedisgiunti.getRef1();
            }
        }

        throw new IllegalArgumentException("Elemento non presente in nessuno degl'insiemi disgiunti");
    }

    /*
     * Dopo l'unione di due insiemi effettivamente disgiunti il rappresentante
     * dell'insieme unito è il rappresentate dell'insieme che aveva il numero
     * maggiore di elementi tra l'insieme di cui faceva parte {@code e1} e
     * l'insieme di cui faceva parte {@code e2}. Nel caso in cui entrambi gli
     * insiemi avevano lo stesso numero di elementi il rappresentante
     * dell'insieme unito è il rappresentante del vecchio insieme di cui faceva
     * parte {@code e1}.
     *
     * Questo comportamento è la risultante naturale di una strategia che
     * minimizza il numero di operazioni da fare per realizzare l'unione nel
     * caso di rappresentazione con liste concatenate.
     *
     */

    /**
     * Nella union facciamo prima di tutti il controllo degl'elementi passati
     * e vedo se tutti e due o uno dei due non sia null, se lo fossero
     * allora si viene a stampare.
     *
     * @throws new NullPointerException
     *             <p>
     *             Poi si viene a creare due variabile di tipo DisjointSetElement,
     *             che rappresentano l'insieme che andremmo a svolgere l'attach delle
     *             reference 1 e 2  infatti dopo gli andremmo ad assegnare col findSet
     *             precedentemente definito i due insiemi su cui stiamo lavorando Sx = e1
     *             e Sy = e2 l'ordine non sarà importante perchè dopo il controllo che
     *             l'assegnazione sia andata a buon fine e che uno dei due non sia nullo e
     *             e lanci una
     * @throws new IllegalArgumentException
     *             <p>
     *             Allora passeremo alla 2 fase, denominata  i 3 controlli 4 se ci mettiamo pure
     *             la diversità.
     *             il primo vede se Sx e Sy siano diversi e se lo sono si passa alla fase dei
     *             controllo dei suoi number se non lo sono non si fa NULLA.
     *             Nella fase dei controlli dei number si viene a prendere il number di Sx con
     *             Sx.getNumber, quello di Sy e si vede quali fra i due siano i più grandi.
     *             Nel caso di due singoletti passati con dimensione 1 allora Sx e Sy avranno dimensione 1
     *             quindi non ci sono regole per il rappresentante1 dei due insiemi e si può decidere,
     *             come si vuole per il nuovo rappresentante.
     *             Nel caso invece ci sia Sx che ha più elementi interni di Sy
     *             si assegna, ad ogni elemento di Sy il suo ref1 andrà ad essere puntato al ref1 di Sx.
     *             Caso contrario invece se fosse stato Sy più grande di Sx.
     *             E alla fine la dimensione cambia e si va a legare la dimensione del vecchio insieme
     *             a quello nuovo.
     *             <p>
     *             Fine. :>
     *             <p>
     **/
    @Override
    public void union(DisjointSetElement e1, DisjointSetElement e2) {

        if (e1 == null || e2 == null)
            throw new NullPointerException("Elemento Passato è nullo");
// Inserisco gl'elementi che devo unire in due insiemi differenti per poterci lavorare
        DisjointSetElement Sx = null;
        DisjointSetElement Sy = null;
        for (DisjointSetElement insiemedisgiunti : InsiemdegliInsiemi) {
            if (insiemedisgiunti == e1) Sx = findSet(e1);
            if (insiemedisgiunti == e2) Sy = findSet(e2);
        }
        // Se uno degl'insiemi passati è nullo allora lancia un'eccezione
        if (Sx == null || Sy == null)
            throw new IllegalArgumentException("Uno dei due elementi passati non è presente");

        //Controllo dove vedo se i rap1 sono diversi, se non lo sono salto l'union
        if (Sx != Sy) {
            /**Caso dove Sx a meno elementi nell'insieme di Sy**/
            if (Sx.getNumber() < Sy.getNumber()) {
                //Caso sccorro i miei insiemi singoletti
                for (DisjointSetElement insiemedisgiunti : InsiemdegliInsiemi) {
                    //Caso in cui se il referimento 1
                    if (insiemedisgiunti.getRef1() == Sx) {
                        insiemedisgiunti.setRef1(Sy);
                    }
                }
                Sy.setNumber(Sy.getNumber() + Sx.getNumber());
                if (Sy.getRef2() != null) Sy.getRef2().setNumber(Sy.getNumber());
                Sx.setNumber(Sy.getNumber());
                //Caso dove Sy a meno elementi nell'insieme di Sx
            } else if (Sx.getNumber() > Sy.getNumber()) {
                for (DisjointSetElement insiemedisgiunti : InsiemdegliInsiemi) {
                    if (insiemedisgiunti.getRef1() == Sy) {
                        insiemedisgiunti.setRef1(Sx);
                    }
                }
                Sx.setNumber(Sy.getNumber() + Sx.getNumber());
                if (Sx.getRef2() != null) Sx.getRef2().setNumber(Sx.getNumber());
                Sy.setNumber(Sx.getNumber());

            } else if (Sx.getNumber() == Sy.getNumber()) {

                for (DisjointSetElement insiemedisgiunti : InsiemdegliInsiemi) {
                    if (insiemedisgiunti.getRef1() == Sx) insiemedisgiunti.setRef2(Sy);
                    if (insiemedisgiunti.getRef1() == Sy) insiemedisgiunti.setRef1(Sx);
                }
                Sx.setNumber(Sy.getNumber() + Sx.getNumber());
                Sy.setNumber(Sx.getNumber());
            }

        }

    }

    /**
     * Analisi getCurrentRepresentatives
     * 1) Si crea una variabile rappresentanti di tipo Set che prende variabili
     * DisjointSetElement
     * 2) Con un for each vado a aggiungere nella variabile locale
     * che ho creato i vari ref1 di ogni insieme disgiunto prensente
     **/
    @Override
    public Set<DisjointSetElement> getCurrentRepresentatives() {
        Set<DisjointSetElement> rappresentanti = new HashSet<>();
        for (DisjointSetElement insiemedisgiunti : InsiemdegliInsiemi) {
            rappresentanti.add(insiemedisgiunti.getRef1());
        }
        return rappresentanti;
    }

    /**
     * Analisi getCurrentRepresentatives
     * 0) Creazione della variabile Set per il salvataggio dei valori
     * 1) Controllo iniziale per vedere se e sia diverso da null
     * 2) Ciclo for-each dove all'interno c'è un if e controllo
     * che il ref1 passatomi sia == a quello presente nella mia LinkedList
     * se lo è allora lo aggiunge alla mia variabile {@parm elementiInsieme}
     * <p>
     * Se la variabile {@parm elementiInsieme} non contenesse nessun elemento allora
     * lancia l'eccezioene
     *
     * @return throw new IllegalArgumentException("L'elemento passato non è contenuto in nessun insieme disgiunto")
     **/
    @Override
    public Set<DisjointSetElement> getCurrentElementsOfSetContaining(DisjointSetElement e) {
        Set<DisjointSetElement> elementiInsieme = new HashSet<>();
        if (e == null) throw new NullPointerException("Elemento Passato è nullo");

        for (DisjointSetElement insiemedisgiunti : InsiemdegliInsiemi) {
            if (insiemedisgiunti.getRef1() == e.getRef1()) {
                elementiInsieme.add(insiemedisgiunti);
            }
        }
        if (elementiInsieme.isEmpty())
            throw new IllegalArgumentException("L'elemento passato non è contenuto in nessun insieme disgiunto");
        return elementiInsieme;

    }

    /**
     * Analisi getCardinalityOfSetContaining
     * 0) Creazione della variabile di tipo intero che si utilizza per salvare la cardinalità
     * 1) Controllo se l'elemento passato che se sia uguale a null mi lanci l'eccezzione
     *
     * @return throw new NullPointerException("Elemento Passato è nullo");
     * 2) ciclo for-each dove si va a fare la stessa cosa del metodo sopra spiegato
     * ma si incrementa la cardinatlià
     * 3) si controlla se la cardinalità è 0 e se lo è allora boom
     * @return throw new IllegalArgumentException();
     * se no non succede niente e ritorna la cardinalità
     * <p>
     * Fine pt2.
     **/
    @Override
    public int getCardinalityOfSetContaining(DisjointSetElement e) {
        int cardinality = 0;
        if (e == null) throw new NullPointerException("Elemento Passato è nullo");
        for (DisjointSetElement insiemedisgiunti : InsiemdegliInsiemi) {
            if (insiemedisgiunti.getRef1() == e.getRef1()) cardinality++;
        }
        if (cardinality == 0)
            throw new IllegalArgumentException("L'elemento passato non è contenuto in nessun insieme disgiunto");
        return cardinality;
    }

}
