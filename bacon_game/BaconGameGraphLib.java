/**
 * PS4: Bacon Game Algorithm
 * Author: Ryan Lee, CS10, Winter 2022
 */

import java.util.*;

public class BaconGameGraphLib {

    public static <V,E> Graph<V,E> bfs(Graph<V,E> g, V source){

        Graph<V,E> pathTree = new AdjacencyMapGraph<>();

        Queue<V> queue = new LinkedList<V>(); //queue to implement BFS

        queue.add(source); //enqueue start vertex
        pathTree.insertVertex(source);

        while (!queue.isEmpty()) { //loop until no more vertices
            V u = queue.remove(); //dequeue
            for (V v : g.outNeighbors(u)) { //loop over out neighbors
                if (!pathTree.hasVertex(v)) { //if neighbor not visited, then neighbor is discovered from this vertex
                    queue.add(v); //enqueue neighbor

                    pathTree.insertVertex(v);
                    pathTree.insertDirected(v, u, g.getLabel(u, v));
                }
            }
        }
        return pathTree;
    }

    public static <V,E> List<V> getPath(Graph<V,E> tree, V v){
        List<V> path = null;
        V currentNode = v;

        if(tree.hasVertex(currentNode)){
            path = new ArrayList<V>(); //this will hold the path from start to end vertex
            path.add(currentNode);
            while (tree.outDegree(currentNode) != 0){
                for (V vertex : tree.outNeighbors(currentNode)){
                    currentNode = vertex;
                    path.add(currentNode);
                }
            }
        }
        return path;
    }

    public static <V,E> Set<V> missingVertices(Graph<V,E> graph, Graph<V,E> subgraph){
        Set<V> verticesSet = new HashSet<>();

        for (V v : graph.vertices()){
            if(!subgraph.hasVertex(v)){
                verticesSet.add(v);
            }
        }
        return verticesSet;
    }

    public static <V,E> double averageSeparation(Graph<V,E> tree, V root){
        Graph<V,E> subtree = bfs(tree, root);
        double numChildren = subtree.numVertices() - 1;
        double totalSizePaths = 0;

        for(V neighbors : subtree.inNeighbors(root)){
            totalSizePaths += averageSeparationHelper(subtree, neighbors, 1);
        }
        return (totalSizePaths / numChildren);
    }

    public static <V,E> double averageSeparationHelper(Graph<V,E> subtree, V currentRoot, int currentLevel){
        double totalSizePaths = currentLevel;
        for (V neighbors : subtree.inNeighbors(currentRoot)){
            totalSizePaths += averageSeparationHelper(subtree, neighbors, currentLevel + 1);
        }
        return totalSizePaths;
    }

}
