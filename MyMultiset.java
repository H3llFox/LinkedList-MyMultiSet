package MyFirstProject;

//Libreria per le hashmap che utilizzo nel programma

import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.HashSet;
//librearia utilizzata per identifcare la modifica nell'hash table
//e lanciare anche l'eccezione
import java.util.ConcurrentModificationException;

/**
 * Creo una variabile di tipo HashMap con nome di multiset per contare
 * il numero di occorenza dell'elemento nell'insieme, ad ogni elemento che c'è ne conto le ripetizioni
 * l'elemento sarà la key della Map e il value sarà le sue occorenze, questa variabile cambia ,
 * nel corso del programma per cancellazioni o aggiunte di nuovi elementi e il numero
 * di occorenze non deve superare  la soglia di Integer.MAX_VALUE,che ho assegnato ad una variabile massimoOccorenza, ho utilizzato l'hashmap
 * per far si che,  in termini di spazio in memoria non si allochi niente ad ogni occorenza richiamata.
 * Ho utilizzato anche una variabile di appoggio chiamata beforechanger per salvare in alcuni casi i valori
 * di un certo elemento prima che avvengano le modifiche effettive
 *
 * @param <E> il tipo degli elementi del multiset
 * @author Danilo Quattrini danilo.quattrini@studenti.unicam.it
 */
public class MyMultiset<E> implements Multiset<E> {

    /**
     * Assegniamo ad una variabile int il valore massimo
     * che una occorenza può possedere,questa è static perchè
     * sarà condivisa con tutte le istanze della classe
     * ed è final perchè non dovrà mai subire dei cambiamenti
     * nell'esecuzione
     **/
    private static final int massimoOccorenza = Integer.MAX_VALUE;


    /**
     * Creiamo il nostro multinsieme
     * dove dentro metteremmo i nostri elementi
     * che andremmo poi a contarne le occorenze
     * Integer uguale a 0 vuol dire che l'elemento E non c'è nell'insieme
     * Integer < 0 return NullPointException errore
     **/
    private Map<E, Integer> multiset;
    /**
     * Variabile di tipo intero dove salvo i valori
     * prima che vengano modificati dai metodi della classe
     **/
    private int beforechanges;

    /**
     * Variabile di tipo intero dove salvo il totale
     * delle modifiche fatte, questa mi server per far si
     * che nell'iteratore se è svolta una modifica
     * con il suo metodo get la richiamo e stampo come
     * errore
     *
     * @throw new
     **/
    private int modificato;

    /**
     * Crea un multiset vuoto.
     */
    public MyMultiset() {
        multiset = new HashMap<E, Integer>();
    }

    @Override
    /**
     * Restituisce la dimensione del multinsieme considerando anche le occorenze al suo interno
     * in modo che possa considerare anche le ripetizoni il codice
     * presenta  un for each dove parto da un Integer chiamato valori e conta tutti i
     * values di multiset in modo che possa anche contarci le occorenze e non escluderle
     **/
    public int size() {
        int elementipresenti = 0;
        for (Integer valori : multiset.values()) elementipresenti += valori;
        return elementipresenti;
    }

    /**
     * Spiegazione del metodo count
     * 1) if di controllo utilizzato in caso in cuoi il valore di element sia uguale a null
     * 2) Il secondo if controlla che l'elemento sia presente nel multiset e se lo è;
     * allora
     *
     * @return numero di occorenze dell'elemento ricercato presente nel multiset
     * else
     * @return ritorna 0
     **/
    @Override
    public int count(Object element) {
        if (element == null)
            throw new NullPointerException("L'elemento da inserire è nullo");
        return multiset.getOrDefault(element, 0);
    }

    @Override
    /**
     *  Spiegazione del metodo add 1°
     *  1) if di controllo utilizzato in caso in cuoi il valore di element sia uguale a null
     *  2) if di controllo nel caso occurences sia un valore negativo
     *  3) salvo il valore della occorenza prima della modifica sulla variabile beforechanges, come richiesto da API
     *  alla fine ritornerò il suo valore
     * @return this.beforechanges
     **/
    public int add(E element, int occurrences) {

        if (element == null)
            throw new NullPointerException("L'elemento da inserire è nullo");
        if (occurrences < 0)
            throw new IllegalArgumentException("il valore della occorenza che vuoi inserire  è più piccolo di 0 o supera il massimo impostabile");
        this.beforechanges = multiset.getOrDefault(element, 0);
        // Controllo se l'operazione comporterebbe più di Integer.MAX_VALUE occorrenze dell'elemento
        if (occurrences > this.massimoOccorenza - this.beforechanges)
            throw new IllegalArgumentException("L'operazione comporterebbe più di Integer.MAX_VALUE occorrenze dell'elemento");
        multiset.put(element, occurrences + this.beforechanges);
        this.modificato++;
        return this.beforechanges;

    }

    @Override
    /**
     * Spiegazione del metodo add 2°
     *
     * 1) if di controllo utilizzato in caso in cuoi il valore di element sia uguale a null
     * 2) se elements non è vuoto utilizzo il put per associale all'oggetto element che viene passato
     * un valore di occorenza che do a 0 perchè non so se questi siano presenti più volte o no
     * e il getOrDefaul fa che se la chiave che sarebbe nel nostro caso element è gia presente
     * allora ritorna il suo valore se no aggiunge
     **/
    public void add(E element) {
        if (element == null)
            throw new NullPointerException("L'elemento che si cerca di inserire è vuoto");
        this.beforechanges = multiset.getOrDefault(element, 0);
        // Controllo se l'operazione comporterebbe più di Integer.MAX_VALUE occorrenze dell'elemento
        if (this.massimoOccorenza == this.beforechanges)
            throw new IllegalArgumentException("L'operazione comporterebbe più di Integer.MAX_VALUE occorrenze dell'elemento");
        multiset.put(element, multiset.getOrDefault(element, 0) + 1);
        this.modificato++;
    }

    /**
     * Spiegazione del metodo remove 1°
     * 1) if di controllo utilizzato in caso in cuoi il valore di element sia uguale a null
     * 2) if di controllo nel caso occurences sia un valore negativo
     * 3) salvo il valore della occorenza prima della modifica sulla variabile beforechanges, come richiesto da API
     * alla fine ritornerò il suo valore
     * 4) if dove ci sarà il puntamento  dell'elemento da eliminare
     * 5) if interno dove controllo se il valore dell'occorenza di quell'elemento è minore o uguale
     * dell'occorenza passata
     * 6) se e così allora rimuove tutte le occorenze presenti con remove(Object element)
     * 7) caso contrario faccio un cast di element e tolgo le occorenze presenenti con quelle
     * passate
     *
     * @return this.beforechanges
     **/
    @Override
    public int remove(Object element, int occurrences) {
        if (element == null)
            throw new NullPointerException("L'elemento che si cerca di inserire è vuoto");
        if (occurrences < 0)
            throw new IllegalArgumentException("il valore di occurrences è negativo");
        //variabile d'appoggio per salvare il valore delle occorenze prima della modifica
        this.beforechanges = multiset.getOrDefault(element, 0);

        if (multiset.containsKey(element)) {
            if (occurrences != 0) {
                if (multiset.get(element) <= occurrences) {
                    multiset.remove(element);
                } else {
                    multiset.put((E) element, beforechanges - occurrences);
                }
                this.modificato++;
            }
        }

        return this.beforechanges;
    }

    /**
     * Spiegazione del metodo remove 2°
     * <p>
     * 1) creo una variabile locale per poter salvare lo stato nel caso è stato eliminata oppure no la singola occorenza
     * 2) if di controllo utilizzato in caso in cuoi il valore di element sia uguale a null
     * 3) if dove ci sarà il puntamento  dell'elemento, se presente nell'hashmap che sarebbe  da eliminare
     * 4) creo una variabile locale dove inserico la occorenza dell'elemento che sto puntanto
     * 5) con la put svolgo il decremento di una occorenza
     * 6) imposto il valore della variabile booleana eliminato a true
     *
     * @return eliminato
     **/
    @Override
    public boolean remove(Object element) {
        boolean eliminato = false;
        if (element == null)
            throw new NullPointerException("L'elemento che si cerca di inserire è vuoto");

        if (multiset.containsKey(element)) {
            int valueOfelement = multiset.get(element);
            multiset.put((E) element, valueOfelement - 1);
            eliminato = true;
            this.modificato++;
        }
        return eliminato;

    }


    /**
     * Spiegazione setCount
     * <p>
     * 1) All'inizio ci sono i vari controlli richiesti dalle API dove ci sarà una
     *
     * @return beforechange --> cioè stampare il valore delle occorenze prima della modifica(aggiunta o rimozione di quest'ultima..)
     * @throws IllegalArgumentException se count è negativo
     * @throws NullPointerException     se {@code element} è nullo
     *                                  Cioè cosa vuol dire?? Vuol dire che punoto all'elemento presente nella mia Hashmap
     *                                  se il valore che trovo non c'è allora ritorno l'eccezzione
     *                                  2) faccio una if dove controllo che se l'elemento del mio insieme ha già le occorenze
     *                                  richieste allora ritorno il count prima della modifica e non faccio nulla
     *                                  3)inserisco la variabile beforechange, dove gli assegno il valore dell'elemento che sto puntando che mi ridà le occorenze
     *                                  prima della modifica, perchè come richiesto dalle API bisogna
     *                                  fare un:
     **/
    @Override
    public int setCount(E element, int count) {
        if (count < 0)
            throw new IllegalArgumentException("il valore di count non può essere più piccolo di zero");
        if (element == null)
            throw new NullPointerException("L'elemento che stai cercando per la modifica non è presente");
        //variabile d'appoggio per salvare il valore delle occorenze prima della modifica
        this.beforechanges = multiset.getOrDefault(element, 0);
        int appoggio = this.beforechanges;
        while (appoggio != count) {
            if (appoggio > count) {
                multiset.put(element, appoggio - 1);
                appoggio--;
            } else if (appoggio < count) {
                multiset.put(element, appoggio + 1);
                appoggio++;
            }
            this.modificato++;
        }
        return this.beforechanges;
    }

    /**
     * Spiegazione elementSet
     * <p>
     * questo metodo della hashMap ritorna tutti
     * gl'elementi presenti al suo interno senza le occorenze
     * 1) creando una variabile locale di tipo Set <E> dove all'interno
     * gli assegno  le chiavi presenti nel mio hashmap tramite un for-each
     * che fa una visione della hashmap con .entrySet() che poi aggiunge ad elements
     * solo il valore delle chiavi
     * 2)Infine faccio un ritorno della elements
     **/
    @Override
    public Set<E> elementSet() {
        Set<E> elements = new HashSet<>();
        if (multiset.size() != 0) {
            for (Map.Entry<E, Integer> elemento : multiset.entrySet()) {
                elements.add(elemento.getKey());
            }
        }
        return elements;
    }

    /**
     *
     **/
    @Override
    public Iterator<E> iterator() {
        return new IteratoreMultiset();
    }

    private class IteratoreMultiset implements Iterator<E> {
        //Creazione della mia iterator hashmap dove
        //tramite il mio Map.Entry che mi permette di poter iterare gl'elementi
        //che sono presente all'insterno della HashMap
        private Iterator<Map.Entry<E, Integer>> multisetIterator = multiset.entrySet().iterator();
        //Dichiamo una istanza della classe Map.entry chiamata
        //valoremomentaneo dove le assegno il valore dell'elemento che sta puntando
        //in quel specifico momento
        Map.Entry<E, Integer> elementomomentaneo;
        //variabile intero dove inserisco le numero delle occorenze di un
        //dato elemento, questo perchè mi serve per far si che si ripetano durante
        // le iterazioni
        private int occorenzeElemento;
        //ora assegno ad una variabile intera un numero iniziale
        //che indicherebbe lo stato della nostra hashmap cioè se è stata fatto modifiche o no
        private int modifichefatte = getModifiche();

        @Override
        public boolean hasNext() {
            while (multisetIterator.hasNext() && occorenzeElemento == 0) {
                elementomomentaneo = multisetIterator.next();
                occorenzeElemento = elementomomentaneo.getValue();
            }
            //Controllo se ci sono ancora occorenze sull'elemento
            return occorenzeElemento > 0;
        }

        @Override
        public E next() {
            if (modifichefatte != getModifiche())
                throw new ConcurrentModificationException("Ci sono state delle modifiche in corso");
            if (!hasNext()) {
                return null;
            }

            occorenzeElemento--;
            return elementomomentaneo.getKey();
        }
    }

    /**
     * Spiegazione metodo contains
     * 1) sempre if di controllo dove vedo se l'elemento passato sia diverso da null e se si
     *
     * @return true
     * se una delle due condizioni non si verificano allora
     * @return false
     * @throws NullPointerException --> Elemento passato è nullo
     *                              2) if di controllo che prima controlla se l'elemento passato è presente nell'hashmap e non
     *                              ritorni un valore nullo nel caso non lo trovi, poi se lo trova allora controlla, come
     *                              richiesto nelle API se ha almeno una occorenza. Se tutte e due le condizioni si verificano,
     *                              allora
     **/
    @Override
    public boolean contains(Object element) {
        if (element == null)
            throw new NullPointerException("Questo elemento è nullo...");
        if (multiset.get(element) != null && multiset.get(element) > 0) return true;
        else return false;
    }

    /**
     * Spiegazione clear
     * <p>
     * In questo metodo con il multiset.keySet() richiamo
     * tutti gli elementi(chiavi) presenti nella hashmap(multiset)e
     * richiamo il metodo removeAll della classe Set<E>
     * per svolgere la pulizia di tutte le chiavi presenti
     * al suo interno
     */
    @Override
    public void clear() {
        multiset.keySet().removeAll(multiset.keySet());
        this.modificato++;
    }

    @Override
    /** Spiegazione isEmpty
     *
     * Controllo se il mio mulinsieme sia uguale a null
     * o se il mio multinsieme ha dimensione 0 vuol dire
     * che non c'è nessuna occorenza di nessun elemento, quindi
     * @return true se si verifica uno dei due casi
     *
     * */
    public boolean isEmpty() {
        return multiset == null || multiset.size() == 0;
    }

    /*
     * Due multinsiemi sono uguali se e solo se contengono esattamente gli
     * stessi elementi (utilizzando l'equals della classe E) con le stesse
     * molteplicità.
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */

    /**
     * Spiegazione equals
     * 1) controllo che  "this" cioè l'istanza della corrente classe sia uguale
     * a l'oggetto passato
     *
     * @return true
     * 2) nel caso l'oggetto passato obj sia uguale a null oppure
     * se la getClass() della mia classe MyMultiset è diversa di quella di ob
     * @return false
     * <p>
     * poi assegnliamo alla variabile
     * @parm altro
     * il casting che facciamo
     * con l'oggetto che ci viene passato nel metodo della classe
     * mettiamo il wildcard <?> perchè,
     * non sappiamo  il tipo di oggetto che ci verrà dato
     * <p>
     * dopo facciamo tutte le operazioni di confronto con i vari
     * if per vedere se l'obj passato è uguale oppure se uno dei due sono
     * null, oppure se le loro dimensioni sono diverse, poi faremmo
     * in conclusione il controllo che l'obj passato abbia le stesse
     * key e le stesse occorenze ciclando l'hashmap con un for each
     * assegnando a due variabili di tipo Object i valore che sarebbero le occorenze
     * e le keys
     **/
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        MyMultiset<?> altro = (MyMultiset<?>) obj;
        if (this.multiset == altro.multiset) return true;
        if (this.multiset == null || altro.multiset == null) return false;
        if (this.multiset.size() != altro.multiset.size()) return false;

        for (Map.Entry<?, ?> entry : this.multiset.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();

            if (value == null || !value.equals(altro.multiset.get(key))) return false;

        }

        return true;
    }

    /*
     * Da ridefinire in accordo con la ridefinizione di equals.
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        int risultato = 1;

        for (Map.Entry<E, Integer> entry : multiset.entrySet()) {
            E elemento = entry.getKey();
            Integer occorenze = entry.getValue();

            risultato = 31 * risultato + (elemento == null ? 0 : elemento.hashCode());
            risultato = 31 * risultato + (occorenze == null ? 0 : occorenze.hashCode());
        }

        return risultato;
    }

    /**
     * Metodo getter per prendere il valore delle modifiche fatte nell'Hashmap
     **/
    public int getModifiche() {
        return this.modificato;
    }
}
