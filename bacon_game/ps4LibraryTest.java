/**
 * Author: Ryan Lee, CS10, Winter 2022
 */

import java.util.List;

public class ps4LibraryTest {
    public static void main(String[] args) {
        Graph test1 = new AdjacencyMapGraph();
        // create graph as described
        test1.insertVertex("Kevin Bacon");
        test1.insertVertex("Alice");
        test1.insertVertex("Bob");
        test1.insertVertex("Charlie");
        test1.insertVertex("Dartmouth (Earl thereof)");
        test1.insertVertex("Nobody");
        test1.insertVertex("Nobody's Friend");
        test1.insertUndirected("Kevin Bacon", "Alice", "A Movie, E Movie");
        test1.insertUndirected("Kevin Bacon", "Bob", "A Movie");
        test1.insertUndirected("Alice", "Bob", "A Movie");
        test1.insertUndirected("Charlie", "Bob", "C Movie");
        test1.insertUndirected("Charlie", "Alice", "D Movie");
        test1.insertUndirected("Charlie", "Dartmouth (Earl thereof)", "B Movie");
        test1.insertUndirected("Nobody", "Nobody's Friend", "F Movie");
        // test: average separation should be 1.25
        System.out.println("Alice's average separation: " + BaconGameGraphLib.averageSeparation(test1, "Alice"));
        // create bfsTree and shortest path list using methods
        Graph bfsTest = BaconGameGraphLib.bfs(test1, "Alice");
        List<String> pathToDartmouth = BaconGameGraphLib.getPath(bfsTest, "Dartmouth (Earl thereof)");
        // print out the shortest path between two actors and show the movie they appeared in
        // (D and Charlie should appear in B, Charlie and Alice should appear in D)
        for (int i = 0; i<pathToDartmouth.size() - 1; i++){
            System.out.println(pathToDartmouth.get(i) + " and " + pathToDartmouth.get(i + 1) + " appeared in: " + bfsTest.getLabel(pathToDartmouth.get(i), pathToDartmouth.get(i + 1))); }
                // print out names which exist in the primal graph holding all actors, but not in the subtree used for bfs
        System.out.println(BaconGameGraphLib.missingVertices(test1, bfsTest));
    }
}
