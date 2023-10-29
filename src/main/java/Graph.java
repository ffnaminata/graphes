import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {

    private List<Sommet> sommets = new ArrayList<>();
    private List<Arete> aretes = new ArrayList<>();



    public List<Sommet> getSommets() {
        return sommets;
    }

    public List<Arete> getAretes() {
        return aretes;
    }


    public void ajouterSommet(Sommet sommet) {
        this.sommets.add(sommet);
    }

    public void ajouterArete(Arete arete) {
        this.aretes.add(arete);
    }

    Graph() {
        sommets = new ArrayList<>();
        aretes = new ArrayList<>();
    }


    Graph(String nomFichierSommets, String nomFichierAretes) {
        this.sommets = lireSommetsDepuisFichier(nomFichierSommets);
        this.aretes = lireAretesDepuisFichier(nomFichierSommets, nomFichierAretes);

    }

    Sommet rechercheSommet(int id) {
        for (Sommet sommet : getSommets()) {
            if (sommet.numero == id) {
                return sommet;
            }
        }
        return null;
    }


    /**
     * La méthode permet de créer une liste de sommets depuis un fichier
     *
     * @param nomFichier
     */
    public List<Sommet> lireSommetsDepuisFichier(String nomFichier) {
        List<Sommet> sommets = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] tokens = ligne.split(";");

                if (tokens.length >= 5) {
                    int numero = Integer.parseInt(tokens[0].trim());
                    String nom = tokens[1].trim();
                    String ligne2 = tokens[2].trim();
                    boolean terminus = Boolean.parseBoolean(tokens[3].trim());
                    int branchement = Integer.parseInt(tokens[4].trim());

                    Sommet sommet = new Sommet(numero, nom, ligne2, terminus, branchement);
                    sommets.add(sommet);


                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sommets;
    }

    /**
     * La méthode permet de créer une liste d'arêtes depuis un fichier
     *
     * @param
     */
    public List<Arete> lireAretesDepuisFichier(String nomFichierSommets, String nomFichierAretes) {
        List<Arete> aretes = new ArrayList<>();
        List<Sommet> sommets = lireSommetsDepuisFichier(nomFichierSommets);

        try (BufferedReader br = new BufferedReader(new FileReader(nomFichierAretes))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] tokens = ligne.split(" ");

                if (tokens.length >= 3) {
                    int numsommet1 = Integer.parseInt(tokens[0].trim());
                    int numsommet2 = Integer.parseInt(tokens[1].trim());
                    int temps = Integer.parseInt(tokens[2].trim());

                    Sommet sommet1 = null;
                    Sommet sommet2 = null;

                    for (Sommet sommet : sommets) {
                        if (sommet.numero == numsommet1) {
                            sommet1 = sommet;
                        } else if (sommet.numero == numsommet2) {
                            sommet2 = sommet;
                        }
                    }

                    if (sommet1 != null && sommet2 != null) {
                        Arete arete = new Arete(sommet1, sommet2, temps);
                        //aretes.add(arete);
                        this.ajouterArete(new Arete(sommet1,sommet2,temps));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aretes;
    }

    /**
     *  Ajoute les arêtes du fichiers dans le grpah (liste des arêtes )
     * @param val nom du fichier
     */
    public void ajouteAreteT(String val){
        try (BufferedReader br = new BufferedReader(new FileReader(val))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] tokens = ligne.split(" ");

                if (tokens.length >= 3) {
                    int numsommet1 = Integer.parseInt(tokens[0]);
                    int numsommet2 = Integer.parseInt(tokens[1]);
                    int temps = Integer.parseInt(tokens[2]);

                    Sommet sommet11 = null;
                    Sommet sommet22 = null;

                    for (Sommet sommet : this.getSommets()) {
                        if (sommet.numero == numsommet1) {
                            sommet11 = sommet;
                        } else if (sommet.numero == numsommet2) {
                            sommet22 = sommet;
                        }
                    }

                    if (sommet11 != null && sommet22 != null) {
                        this.ajouterArete(new Arete(sommet11, sommet22, temps));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @return true si connexe et false sinon
     */
    public boolean estConnexe() {
        // Sélectionnez un sommet arbitraire (par exemple, le premier sommet)
        Sommet sommet1 = sommets.get(0);

        // Utilisez BFS pour parcourir le graphe à partir du sommet de départ
        Set<Sommet> sommetsVisites = new HashSet<>();
        Queue<Sommet> fileSommetsAVisiter = new LinkedList<>();

        fileSommetsAVisiter.add(sommet1);
       //sommetsVisites.add(sommet1);

        while (!fileSommetsAVisiter.isEmpty()) {
            Sommet sommetCourant = fileSommetsAVisiter.poll();

            // Pour chaque sommet adjacent non visité, ajoutez-le à la file d'attente
            for (Sommet voisin : obtenirVoisinsNonVisites(sommetCourant, sommetsVisites)) {
                fileSommetsAVisiter.add(voisin);
                sommetsVisites.add(voisin);
            }

        }

        // Si tous les sommets ont été visités, le graphe est connexe
        return sommetsVisites.size() == sommets.size();
    }

    /**
     * Uilisé par estconnex() pour connaître les voisois non visités du sommet courant
     * @param sommet
     * @param sommetsVisites
     * @return
     */
    private List<Sommet> obtenirVoisinsNonVisites(Sommet sommet, Set<Sommet> sommetsVisites) {
        List<Sommet> voisins = new ArrayList<>();

        // Remplacez ceci par la logique pour obtenir les voisins du sommet depuis vos arêtes
        for (Arete arete : aretes) {
            if (arete.sommet1.equals(sommet) && !sommetsVisites.contains(arete.sommet2)) {
                voisins.add(arete.sommet2);

            } else if (arete.sommet2.equals(sommet) && !sommetsVisites.contains(arete.sommet1)) {
                voisins.add(arete.sommet1);
            }
        }

        return voisins;


    }


    /**
     *
     * @param sommet1
     * @param sommet2
     * @return l'arête contenant le sommet 2 et sommet 2 en paramètre
     */
    public Arete getArete(Sommet sommet1, Sommet sommet2) {
        for (Arete arete : aretes) {
            if ((arete.sommet1.equals(sommet1) && arete.sommet2.equals(sommet2))) {
                return arete;
            }
        }
        return null;
    }


    /**
     *
     * @return le graph après avoir rajouté toutes les arêtes inverses pour chaque arête sauf pour la cycle de ligne 7bis et le cycle de la ligne 10
     */
    public Graph grapheOriente() {
        Graph grapheOriente = new Graph();

        for (Sommet sommet : this.sommets) {
            grapheOriente.ajouterSommet(sommet);
        }

        for (Arete arete : this.aretes) {
            Arete areteInverse = new Arete(arete.getSommet2(), arete.getSommet1(), arete.temps);
            grapheOriente.ajouterArete(arete);
            grapheOriente.ajouterArete(areteInverse);
        }

        // Botzaris = 34 ; Danube = 94 ; Pré Saint Gervais = 280 ; Place des fêtes = 248 ;
        List<Integer> numeroSommetsCycle7bis = new ArrayList<>(Arrays.asList(34, 92, 280, 248, 34));
        List<Sommet> sommetsCycle7bis = new ArrayList<>();


        //Recherche sommet à partir de numero et ajout à la liste de sommets
        for (Integer numeroSommetCycle7bis : numeroSommetsCycle7bis) {
            for (Sommet sommet : this.sommets) {
                if (sommet.numero == numeroSommetCycle7bis) {
                    sommetsCycle7bis.add(sommet);
                }
            }
        }

        for (int i = 0; i < sommetsCycle7bis.size() - 1; i++) {
            Iterator<Arete> it = grapheOriente.aretes.iterator();
            while (it.hasNext()) {
                Arete arete = it.next();
                if (arete.sommet1.numero == sommetsCycle7bis.get(i).numero && arete.sommet2.numero == sommetsCycle7bis.get(i + 1).numero) {
                    it.remove(); // Supprimer l'arête en utilisant l'itérateur
                }
            }
        }


        // Javel = 145 ; Mirabeau = 201 ; Chardon Lagache = 52 ; Michel Ange Molitor = 198 ; Boulogne Jean Jaures = 36 ; Porte d'Auteuil = 259 ; Michel Ange Auteuil = 196 ; Eglise d'Auteuil = 373 ;
        List<Integer> numeroSommetsCycle10 = new ArrayList<>(Arrays.asList(145, 201, 52, 198, 36, 259, 196, 373, 145));
        List<Sommet> sommetsCycle10 = new ArrayList<>();



        for (Integer numeroSommetCycle10 : numeroSommetsCycle10) {
            for (Sommet sommet : this.sommets) {
                if (sommet.numero == numeroSommetCycle10) {
                    sommetsCycle10.add(sommet);
                }
            }
        }

        for (int i = 0; i < sommetsCycle10.size() - 1; i++) {
            Iterator<Arete> it = grapheOriente.aretes.iterator();
            while (it.hasNext()) {
                Arete arete = it.next();
                if (arete.sommet1.numero == sommetsCycle10.get(i).numero && arete.sommet2.numero == sommetsCycle10.get(i + 1).numero) {
                    it.remove(); // Supprimer l'arête en utilisant l'itérateur
                }
            }
        }

        return grapheOriente;
    }


    public void bellmanFord(Sommet SommetDepart, Sommet SommetCible) {

        if (!sommets.contains(SommetDepart) || !sommets.contains(SommetCible)) {
            System.out.println("Le sommet source ou le sommet cible n'existe pas.");
            return;
        }


        Map<Sommet, Integer> distances = new HashMap<>();
        Map<Sommet, Sommet> predecesseurs = new HashMap<>();


        for (Sommet sommet : sommets) {
            distances.put(sommet, Integer.MAX_VALUE);
            predecesseurs.put(sommet, null);
        }
        distances.put(SommetDepart, 0);


        for (int i = 1; i < sommets.size(); i++) {
            for (Arete arete : aretes) {
                Sommet sommet1 = arete.sommet1;
                Sommet sommet2 = arete.sommet2;
                int poids = arete.temps;

                if (distances.get(sommet1) != Integer.MAX_VALUE && distances.get(sommet1) + poids < distances.get(sommet2)) {
                    distances.put(sommet2, distances.get(sommet1) + poids);
                    predecesseurs.put(sommet2, sommet1);
                }
            }
        }


        List<Sommet> chemin = new ArrayList<>();
        Sommet sommetCourant = SommetCible;

        while (sommetCourant != null) {
            chemin.add(sommetCourant);
            sommetCourant = predecesseurs.get(sommetCourant);
        }

        Collections.reverse(chemin);

        System.out.println("Distance minimale depuis " + SommetDepart.nom + " vers " + SommetCible.nom + ": " + (distances.get(SommetCible)) / 60 + " min");
        System.out.print("Chemin le plus court : ");
        for (Sommet sommet : chemin) {
            System.out.print(" ligne " + sommet.ligne + " " + sommet.nom + " -> ");
        }
        System.out.println();
    }

    public List<Arete> prim() {
        List<Arete> areteArabreMin = new ArrayList<>();
        List<Sommet> sommetsArbreMin = new ArrayList<>();
        List<Sommet> sommetsNonInclus = new ArrayList<>(sommets);

       Sommet sommetDepart = sommets.get(0);
        sommetsNonInclus.remove(sommetDepart);
        sommetsArbreMin.add(sommetDepart);

        while (!sommetsNonInclus.isEmpty()) {
            Arete areteMin = null;
            int poidsMin = Integer.MAX_VALUE;

            for (Sommet sommetInclus : sommetsArbreMin) {
                for (Sommet sommetNonInclus : sommetsNonInclus) {
                    Arete arete = getArete(sommetInclus, sommetNonInclus);
                    if (arete != null && arete.temps < poidsMin) {
                        poidsMin = arete.temps;
                        areteMin = arete;
                    }
                }
            }

            if (areteMin != null) {
                areteArabreMin.add(areteMin);
                sommetsArbreMin.add(areteMin.sommet2);
                sommetsNonInclus.remove(areteMin.sommet2);
            }
        }

        System.out.println("Dernier arbre partiel :");
        for (Arete arete : areteArabreMin) {
            System.out.println(arete.sommet1.nom + " - " + arete.sommet2.nom + " : " + arete.temps);
        }

        return areteArabreMin;
    }






}