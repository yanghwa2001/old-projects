/**
 * PS4: Bacon Game
 * Author: Ryan Lee, CS10, Winter 2022
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class BaconUI {
    protected char currCommand;
    protected Graph<String, Set<String>> initialGraph; // graph that holds all actors and all movies
    protected String centerOfUniverse = "Kevin Bacon"; // center of universe at moment, default is Kevin Bacon
    protected Graph<String, Set<String>> graphFromCenter;

    public char getCharCommand() {
        return currCommand;
    }

    public void setCurrCommand(char inputChar) {
        currCommand = inputChar;
    }

    public BaconUI() {
        initialGraph = createInitialGraph();
    } // This constructs the game

    public boolean handleInputModeChange(char input) { // handle possible commands from user
        if (input == 'n' || input == 'm' || input == 's' || input == 'a' || input == 'q' || input == 'o') {
            currCommand = input;
            return true; // return true––valid command
        } else {
            System.out.println("Invalid input! Try again.");
            return false; // return false––invalid command
        }
    }

    public Graph<String, Set<String>> createInitialGraph() {
        Graph<String, Set<String>> returnedGraph = new AdjacencyMapGraph<>();
        try {
            BufferedReader Actors = new BufferedReader(new FileReader("ps4/actors.txt"));
            BufferedReader Movies = new BufferedReader(new FileReader("ps4/movies.txt"));
            BufferedReader ActorIDtoMovieID = new BufferedReader(new FileReader("ps4/movie-actors.txt"));

            try {
                String actorLine;
                Map<String, String> actorMap = new HashMap<String, String>(); //create map for actors ID and their names
                while ((actorLine = Actors.readLine()) != null) { // read through actor file
                    String[] allWords = actorLine.split("\\|");
                    String actorID = allWords[0]; // get actorID map
                    String actorInfo = allWords[1]; // get actor name
                    returnedGraph.insertVertex(actorInfo); // add actor to graph
                    actorMap.put(actorID, actorInfo); // add ID and actor to actor
                }
                String moviesLine;
                Map<String, String> moviesMap = new HashMap<String, String>(); //create map for movie ID and their names
                while ((moviesLine = Movies.readLine()) != null) {// read through movies file
                    String[] allWords = moviesLine.split("\\|");
                    String MovieID = allWords[0]; // get movie ID
                    String MovieName = allWords[1]; // get movie name
                    moviesMap.put(MovieID, MovieName); // add ID and movie to movie
                }

                String IDsLine;
                Map<String, Set<String>> movieActorIDMap = new HashMap<>(); //create map of movies & set of the actors who starred in them
                while ((IDsLine = ActorIDtoMovieID.readLine()) != null) {
                    String[] allWords = IDsLine.split("\\|");
                    String MovieID = allWords[0]; // get movie ID
                    String ActorID = allWords[1]; // ©et actor ID
                    if (movieActorIDMap.containsKey(MovieID)) { // if Movie already
                        movieActorIDMap.get(MovieID).add(ActorID); // add actor ID
                    } else { //create set of actor IDs for each movie ID
                        Set<String> movieIDSet = new HashSet<>();
                        movieIDSet.add(ActorID);
                        movieActorIDMap.put(MovieID, movieIDSet);

                    }
                }

                for (String movieID : movieActorIDMap.keySet()) { // loop through all Movies in Movie ID and Actor ID Map
                    if (movieActorIDMap.get(movieID).size() > 1) { // if more than one actor starred in a movie
                        for (String actorID : movieActorIDMap.get(movieID)) { //loop through each actor for a movie twice in the map
                            for (String ActorID2 : movieActorIDMap.get(movieID)) {
                                if (!returnedGraph.hasEdge(actorMap.get(actorID),
                                        actorMap.get(ActorID2))) { // if actors have not costarred yet (direct neighbors)
                                    Set<String> edgeSet = new HashSet<>(); //add the actor ID to the set
                                    edgeSet.add(moviesMap.get(movieID));

                                    returnedGraph.insertDirected(actorMap.get(actorID), actorMap.get(ActorID2), edgeSet);
                                } else { // condition for costarred actors
                                    returnedGraph.getLabel(actorMap.get(actorID),
                                            actorMap.get(ActorID2)).add(moviesMap.get(movieID)); // add movie name to set of costarred movies
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
                try { // try to close all files
                    Actors.close();
                    Movies.close();
                    ActorIDtoMovieID.close();
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return returnedGraph;
    }


    public static void main(String[] args) {
        System.out.print("Welcome to the Bacon Game!\nCommands:\nn: select new center of the universe (default: Kevin Bacon) \n\t (enter name)\n" +
                "s: choose new actor to find shortest path to center of universe \n\t (enter name)\n" +
                "m: find # of actors with lowest average separation either connected to center of universe\n" +
                "o: find actors of specified # with most outDegree neighbors\n" +
                "a: finds specified # of actors with lowest average separation (either in current universe or among all actors)\n" +
                "q: quit game\n");
        BaconUI game = new BaconUI(); // initialize game by constructing initialGraph
        boolean gameOn = true; //game on = true
        Scanner userInput = new Scanner(System.in); // create new scanner object for user input
        boolean validCommand = false; // valid command not given yet

        game.graphFromCenter = BaconGameGraphLib.bfs(game.initialGraph, game.centerOfUniverse); //bfs from the default center of the universe

        while (gameOn){
            //tells game depending on the center of the Universe
            System.out.println("\n" + game.centerOfUniverse + " game >");
            System.out.println("\nInput Command:");
            String input = userInput.nextLine();

            if (input.length() == 1){ //if the length of the input is 1, it could be a valid input
                validCommand = game.handleInputModeChange(input.charAt(0));

                if (validCommand){ //now if it is a valid command, check if
                    if (game.getCharCommand() == 'n') { // command sets new center graph has actor name universe
                        String lineInput = userInput.nextLine();
                        if (game.initialGraph.hasVertex(lineInput)) { // if it does have that vertex
                            game.centerOfUniverse = lineInput; // set new center of the Universe
                            System.out.println("\nEnter new center of the Universe:");
                            game.graphFromCenter = BaconGameGraphLib.bfs(game.initialGraph, game.centerOfUniverse); // creates subgraph from bfs from center
                            game.setCurrCommand('n'); // checks the size command to give user information about new center of universe
                        }
                        else {
                            System.out.print("\nNot a valid name, try different command"); // user message if primal graph does not hold actor name
                        }
                    }
                    else if (game.getCharCommand() == 's') { //get path from actor to center of universe
                        System.out.println("\nEnter the origin of the path to center of the Universe:");
                        String pathName = userInput.nextLine();
                        if (pathName.equals(game.centerOfUniverse)){
                            System.out.print("\nNo path from center of universe to itself, try different command");
                        }
                        else if (game.graphFromCenter.hasVertex(pathName)) {
                            List<String> list = BaconGameGraphLib.getPath(game.graphFromCenter, pathName); // get list of actors to connect center and actor of interest
                            for (int i = 0; i < list.size() - 1; i++) { // print message connecting actors in list until reach center
                                System.out.println(list.get(i) + " and " + list.get(i + 1) + " appeared in: " + game.graphFromCenter.getLabel(list.get(i), list.get(i + 1)));
                            }
                        }
                        else if (game.initialGraph.hasVertex(pathName)){ // if actor not in subgraph, but in primal, tell user that is the case
                            System.out.print("\nName is not in same universe as the center, try new command");
                        }
                        else { //ask for new command if invalid actor name given
                            System.out.print("\nInvalid Name, try new command");
                        }
                    }

                    else if (game.getCharCommand() == 'a') {
                        PriorityQueue<StringDouble> averageSeparationPQ = new PriorityQueue<StringDouble> ((StringDouble s1, StringDouble s2) -> (int)(100 * (s1.Double - s2.Double))); //priority queue of every actor's average separation
                        //ask whether to find average separation for actors or just ones in the universe of the center

                        System.out.println("\nDo you want the list to include all actors (enter 'a')\n" + "or just the ones connected to the current center(enter 'c')?");
                        String answer = userInput.nextLine(); //only valid response if enter "a" or "c"
                        if (answer.equals("a") || answer.equals("c")) {
                            System.out.println("\nWIP...");
                            if (answer.equals("a")) { // if request for all actors
                                for (String actor : game.initialGraph.vertices())
                                { // loop through the initial graph
                                    double averageSeparation = BaconGameGraphLib.averageSeparation(game.initialGraph, actor); //find average separation
                                    averageSeparationPQ.add(new StringDouble(actor, averageSeparation)); // create StringDouble object of actor and average separation and put into priority queue
                                }
                            }
                            else if (answer.equals("c")) { // if user requested only those who are connected to center analyzed
                                for (String Actor :
                                        game.graphFromCenter.vertices()) {// loop through subgraph
                                    double averageSeparation = BaconGameGraphLib.averageSeparation(game.initialGraph, Actor); //find average separation
                                    averageSeparationPQ.add(new StringDouble(Actor, averageSeparation)); // create StringDouble object of actor and average separation and put into priority queue
                                }
                            }

                            //request user input about how many of the actors with the lowest average separation they want
                            System.out.println("\nHow many top actors with the shortest average separation do you want?");
                            Integer numAnswer = Integer.parseInt(userInput.nextLine()); //give actor, place & average separation for number of actors wanted

                            for (int i = 1; i <= numAnswer && i <= averageSeparationPQ.size(); i++) {
                                StringDouble actorInfo = averageSeparationPQ.remove();
                                System.out.println(i + ": " + actorInfo.getString() + " with average separation: " + actorInfo.getDouble());
                            }
                        }
                        else { //ask for new command if invalid answer
                            System.out.print("\nInvalid response, try new command");
                        }
                    }
                    else if (game.getCharCommand() == 'o') {
                        System.out.println("\nWIP...");
                        PriorityQueue outNeighborPQ = new PriorityQueue<StringDouble>((StringDouble s1, StringDouble s2) -> (int) (100 * (s2.Double - s1.Double)));
                        for (String Actor : game.initialGraph.vertices()) {
                            outNeighborPQ.add(new StringDouble(Actor, game.initialGraph.outDegree(Actor)));
                        }
                        System.out.println("\nHow many top actors with the most outDegrees do you want?");
                        Integer numDemandedAnswer = Integer.parseInt(userInput.nextLine());

                        for (int i = 1; i <= numDemandedAnswer && i <= outNeighborPQ.size(); i++) {
                            StringDouble actorInfo = (StringDouble) outNeighborPQ.remove();
                            System.out.println(i + ": " + actorInfo.getString() + "with outDegrees: " + actorInfo.getDouble());
                        }
                    }
                    else if (game.getCharCommand() == 'q') { // command to quit came (ends while loop)
                        System.out.println("\nGame terminated.");
                        gameOn = false;
                    }
                    if (game.getCharCommand() == 'm') { // number of connected actors & their average separation
                        int connectedActors = game.graphFromCenter.numVertices(); //connected # = # of vertices in the subgraph
                        int totalNumberOfActors = game.initialGraph.numVertices();
                        System.out.println(game.centerOfUniverse + " is currently the center of the acting universe, connected to " + connectedActors +
                                "/" + totalNumberOfActors + " actors with average separation of: " +
                                BaconGameGraphLib.averageSeparation(game.initialGraph, game.centerOfUniverse));
                    }
                }
            } //end of the if input.length() == 1
            else {System.out.println("\nInvalid input!");}
        }
    }
}
