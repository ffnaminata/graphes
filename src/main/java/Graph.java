import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {

    private List<Sommet> sommets;
    private List<Arete> aretes;

    public List<Sommet> getSommets() {
        return sommets;
    }

    public List<Arete> getAretes(){
        return aretes;
    }
    Graph() {
        sommets = new ArrayList<>();
        aretes = new ArrayList<>();
    }

    public void ajouterSommet(Sommet sommet) {
        this.sommets.add(sommet);
    }

    public void ajouterArete(Arete arete) {
        this.aretes.add(arete);
    }



    Graph(String nomFichierSommets, String nomFichierAretes ){
        sommets = lireSommetsDepuisFichier(nomFichierSommets);
        aretes = lireAretesDepuisFichier(nomFichierSommets, nomFichierAretes);
    }

    public static void afficherSommets(List<Sommet> sommets) {
        for (Sommet sommet : sommets) {
            System.out.println("Numéro : " + sommet.numero);
            System.out.println("Nom : " + sommet.nom);
            System.out.println("Ligne : " + sommet.ligne);
            System.out.println("Terminus : " + sommet.terminus);
            System.out.println("Branchement : " + sommet.branchement);
            System.out.println();
        }
    }

    public static void afficherAretes(List<Arete> aretes){
        for (Arete arete : aretes){
            System.out.println("Sommet de départ : " + arete.sommet1.nom);
            System.out.println("Sommet d'arrivée : " + arete.sommet2.nom);
            System.out.println("Temps (en secondes) : " + arete.temps);
            System.out.println();
        }
    }



    /**
     * La méthode permet de créer une liste sommet depuis un fichier
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
     * La méthode permet de créer une liste sommet depuis un fichier
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
                        //ajouterArete(sommet1,sommet2,temps);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aretes;
    }



    public boolean estConnexe() {
        // Sélectionnez un sommet arbitraire (par exemple, le premier sommet)
        Sommet sommetDeDepart = sommets.get(0);

        // Utilisez BFS pour parcourir le graphe à partir du sommet de départ
        Set<Sommet> sommetsVisites = new HashSet<>();
        Queue<Sommet> fileAttente = new LinkedList<>();

        fileAttente.add(sommetDeDepart);
        sommetsVisites.add(sommetDeDepart);

        while (!fileAttente.isEmpty()) {
            Sommet sommetCourant = fileAttente.poll();

            // Pour chaque sommet adjacent non visité, ajoutez-le à la file d'attente
            for (Sommet voisin : obtenirVoisinsNonVisites(sommetCourant, sommetsVisites)) {
                fileAttente.add(voisin);
                sommetsVisites.add(voisin);
            }
        }

        // Si tous les sommets ont été visités, le graphe est connexe
        return sommetsVisites.size() == sommets.size();
    }

    private List<Sommet> obtenirVoisinsNonVisites(Sommet sommet, Set<Sommet> sommetsVisites) {
        List<Sommet> voisins = new ArrayList<>();

        // Remplacez ceci par la logique pour obtenir les voisins du sommet depuis vos arêtes
        for (Arete arete : aretes) {
            if (arete.sommet1.equals(sommet) && !sommetsVisites.contains(arete.sommet2)) {
                voisins.add(arete.sommet2);
                System.out.println(aretes.get(1).toString());
            } else if (arete.sommet2.equals(sommet) && !sommetsVisites.contains(arete.sommet1)) {
                voisins.add(arete.sommet1);
                System.out.println(aretes.get(1).toString());
            }
        }

        return voisins;


    }

    Sommet recherche(int id){
        for (Sommet sommet: getSommets()){
            if (sommet.numero==id){
                return sommet;
            }
        }
        return null;
    }
    public void bellmanFord(Sommet source, Sommet cible) {
        // Assurez-vous que les sommets source et cible existent dans la liste des sommets
        if (!sommets.contains(source) || !sommets.contains(cible)) {
            System.out.println("Le sommet source ou le sommet cible n'existe pas.");
            return;
        }

        // Initialfisation des distances
        Map<Sommet, Integer> distances = new HashMap<>();
        Map<Sommet, Sommet> predecesseurs = new HashMap<>();

        for (Sommet sommet : sommets) {
            distances.put(sommet, Integer.MAX_VALUE);
            predecesseurs.put(sommet, null);
        }
        distances.put(source, 0);

        // Relaxation des arêtes
        for (int i = 1; i < sommets.size(); i++) {
            for (Arete arete : aretes) {
                Sommet sommet1 = arete.sommet1;
                Sommet sommet2 = arete.sommet2;
                int poids = arete.temps;

                if (distances.get(sommet1) != null && distances.get(sommet1) + poids < distances.get(sommet2)) {
                    distances.put(sommet2, distances.get(sommet1) + poids);
                    predecesseurs.put(sommet2, sommet1);
                }
            }
        }

        // Vérification des cycles négatifs
        for (Arete arete : aretes) {
            Sommet sommet1 = arete.sommet1;
            Sommet sommet2 = arete.sommet2;
            int poids = arete.temps;

            if (distances.get(sommet1) != Integer.MAX_VALUE && distances.get(sommet1) + poids < distances.get(sommet2)) {
                System.out.println("Le graphe contient un cycle de poids négatif.");
                return;
            }
        }

        System.out.println("Chemin le plus court depuis le sommet " + source.nom+ " vers " + cible.nom + ":");
        System.out.println("Distance = " + distances.get(cible));
        List<Sommet> chemin = construireChemin(source, cible, predecesseurs);
        System.out.print("Chemin = ");
        for (Sommet sommet : chemin) {
            System.out.print(sommet.nom + " "+sommet.ligne+ " -> ");
        }
        System.out.println();
    }


    public void trouverPlusCourtsChemins(Sommet source, Sommet cible) {
        // Assurez-vous que les sommets source et cible existent dans la liste des sommets
        if (!sommets.contains(source) || !sommets.contains(cible)) {
            System.out.println("Le sommet source ou le sommet cible n'existe pas.");
            return;
        }

        // Initialisation des distances
        Map<Sommet, Integer> distances = new HashMap<>();
        Map<Sommet, Sommet> predecesseurs = new HashMap<>();

        for (Sommet sommet : sommets) {
            distances.put(sommet, Integer.MAX_VALUE);
            predecesseurs.put(sommet, null);
        }
        distances.put(source, 0);

        // Relaxation des arêtes
        for (int i = 1; i < sommets.size(); i++) {
            for (Arete arete : aretes) {
                Sommet sommet1 = arete.sommet1;
                Sommet sommet2 = arete.sommet2;
                int poids = arete.temps;

                if (distances.get(sommet1) != null && distances.get(sommet1) + poids < distances.get(sommet2)) {
                    distances.put(sommet2, distances.get(sommet1) + poids);
                    predecesseurs.put(sommet2, sommet1);
                }
            }
        }

        // Vérification des cycles négatifs
        for (Arete arete : aretes) {
            Sommet sommet1 = arete.sommet1;
            Sommet sommet2 = arete.sommet2;
            int poids = arete.temps;

            if (distances.get(sommet1) != null && distances.get(sommet1) + poids < distances.get(sommet2)) {
                System.out.println("Le graphe contient un cycle de poids négatif.");
                return;
            }
        }

        // Affichage du plus court chemin
        System.out.println("Chemin le plus court depuis le sommet source " + source.nom + " vers " + cible.nom + ":");
        Integer distanceCible = distances.get(cible);
        if (distanceCible == null || distanceCible == Integer.MAX_VALUE) {
            System.out.println("Aucun chemin trouvé.");
        } else {
            List<Sommet> chemin = construireChemin(source, cible, predecesseurs);
            System.out.println("Distance = " + distanceCible);
            System.out.println("Chemin = " + chemin);
        }
    }



    private List<Sommet> construireChemin(Sommet source, Sommet cible, Map<Sommet, Sommet> predecesseurs) {
        List<Sommet> chemin = new ArrayList<>();
        Sommet sommetCourant = cible;



        while (sommetCourant != null) {
            chemin.add(sommetCourant);
            sommetCourant = predecesseurs.get(sommetCourant);

            if (sommetCourant == source) {
                chemin.add(sommetCourant);
                break; // Arrêtez si le sommet courant est le sommet source
            }
        }



        return chemin;
    }



    public Graph rendreFortementConnexe(Sommet sommetSource) {
        // Étape 1 : Exécutez une recherche en profondeur (DFS) pour obtenir l'ordre des sommets dans le graphe original
        List<Sommet> ordre = new ArrayList<>(); //On créer une liste de sommet qui s'appelle ordre
        Set<Sommet> sommetsVisites = new HashSet<>();

        if (!sommetSource.equals(sommets.get(0))) {
            parcoursEnProfondeur(sommetSource, sommetsVisites, ordre);
        }

        for (Sommet sommet : sommets) {
            if (!sommetsVisites.contains(sommet)) {
                parcoursEnProfondeur(sommet, sommetsVisites, ordre);
            }
        }

        // Étape 2 : Inverser les arêtes du graphe
        Graph grapheInverse = inverserAretes();

        // Étape 3 : Exécutez une nouvelle DFS sur le graphe inversé en utilisant l'ordre obtenu à l'étape 1
        Set<Sommet> composanteCourante = new HashSet<>();
        List<Graph> composantesFortementConnexes = new ArrayList<>();

        for (int i = ordre.size() - 1; i >= 0; i--) {
            Sommet sommet = ordre.get(i);

            if (!composanteCourante.contains(sommet)) {
                Graph composante = new Graph();
                parcoursEnProfondeur(grapheInverse, sommet, composanteCourante, composante);
                composantesFortementConnexes.add(composante);
            }
        }

        // Étape 4 : Fusionnez les composantes fortement connexes en un seul graphe
        Graph grapheFortementConnexe = new Graph();

        for (Graph composante : composantesFortementConnexes) {
            grapheFortementConnexe.fusionner(composante);
        }

        return grapheFortementConnexe;
    }

    public void fusionner(Graph autreGraphe) {
        // Fusionner les sommets
        for (Sommet sommet : autreGraphe.getSommets()) {
            ajouterSommet(sommet);
        }

        // Fusionner les arêtes
        for (Arete arete : autreGraphe.getAretes()) {
            ajouterArete(arete);
        }
    }
    private void parcoursEnProfondeur(Sommet sommet, Set<Sommet> sommetsVisites, List<Sommet> ordre) {
        sommetsVisites.add(sommet);

        for (Sommet voisin : obtenirVoisinsNonVisites(sommet, sommetsVisites)) {
            parcoursEnProfondeur(voisin, sommetsVisites, ordre);
        }

        ordre.add(sommet);
    }

    private Graph inverserAretes() {
        Graph grapheInverse = new Graph();

        for (Sommet sommet : sommets) {
            grapheInverse.ajouterSommet(sommet);
        }

        for (Arete arete : aretes) {
            grapheInverse.ajouterArete(new Arete(arete.sommet2, arete.sommet1, arete.temps));
        }

        return grapheInverse;
    }

    public int obtenirPoidsArete(Sommet sommetDeDepart, Sommet sommetDarrivee) {
        for (Arete arete : aretes) {
            if ((arete.sommet1.equals(sommetDeDepart) && arete.sommet2.equals(sommetDarrivee)) ||
                    (arete.sommet1.equals(sommetDarrivee) && arete.sommet2.equals(sommetDeDepart))) {
                return arete.temps;
            }
        }
        // Si l'arête n'est pas trouvée, vous pouvez renvoyer une valeur par défaut ou lever une exception.
        return -1; // Par exemple, -1 pour indiquer que l'arête n'existe pas.
    }


    private void parcoursEnProfondeur(Graph graphe, Sommet sommet, Set<Sommet> composanteCourante, Graph composante) {
        composanteCourante.add(sommet);
        composante.ajouterSommet(sommet);

        for (Sommet voisin : graphe.obtenirVoisinsNonVisites(sommet, composanteCourante)) {
            composante.ajouterSommet(voisin);
            composante.ajouterArete(new Arete(sommet, voisin, graphe.obtenirPoidsArete(sommet, voisin)));

            parcoursEnProfondeur(graphe, voisin, composanteCourante, composante);
        }
    }








}

