public class Start {
    public static void main(String[] args) {
        Graph graph = new Graph();
        graph.afficherSommets(graph.lireSommetsDepuisFichier("metro.txt"));

    }

}
