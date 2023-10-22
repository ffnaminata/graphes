public class Start {
    public static void main(String[] args) {
        Graph graph = new Graph("metroV.txt", "metroE.txt");

        /*
        //graph.afficherAretes(graph.lireAretesDepuisFichier("metroV.txt","metroE.txt"));
        Sommet sommet1 = new Sommet(1, "nom", "ligne", false, 0);
        graph.ajouterSommet(sommet1);

        Sommet sommet2 = new Sommet(2, "nom", "ligne", false, 0);
        graph.ajouterSommet(sommet2);

        Sommet sommet3 = new Sommet(3, "nom", "ligne", false, 0);
        graph.ajouterSommet(sommet3);

        Sommet sommet4 = new Sommet(4, "nom", "ligne", false, 0);
        graph.ajouterSommet(sommet4);

        Sommet sommet5 = new Sommet(5, "nom", "ligne", false, 0);
        graph.ajouterSommet(sommet5);

        Sommet sommet6 = new Sommet(6, "nom", "ligne", false, 0);
        graph.ajouterSommet(sommet6);


        Arete arete = new Arete(sommet1,sommet2,0);
        Arete arete1 = new Arete(sommet2,sommet3,0);
        Arete arete3 = new Arete(sommet3,sommet4,0);
        Arete arete4 = new Arete(sommet4,sommet5,0);
        Arete arete5 = new Arete(sommet4,sommet6,0);
        Arete arete6 = new Arete(sommet6, sommet5, 0);


        graph.ajouterArete(arete);
        graph.ajouterArete(arete1);
        graph.ajouterArete(arete3);
        graph.ajouterArete(arete4);
        graph.ajouterArete(arete5);
        graph.ajouterArete(arete6);

        */


        System.out.println(graph.estConnexe());


    }

}
