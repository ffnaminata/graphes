public class Start {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.afficherAretes(graph.lireAretesDepuisFichier("metroV.txt","metroE.txt"));
    }

}
