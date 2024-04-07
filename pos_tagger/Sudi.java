import java.io.*;
import java.util.*;

/**
 * PS5
 * Author: Ryan Lee
 */

public class Sudi{
    public HashMap<String, HashMap<String, Double>> observations;
    public HashMap<String, HashMap<String, Double>> transitions;
    public String textFile;
    public String tagsFile;
    public double unseen = -10000.0; //number subtracted when unseen

    public Sudi(String textFile, String tagsFile) {
        try {
            observationsTraining(textFile, tagsFile);
            transitionsTraining(tagsFile);
        }
        catch (IOException e) {
            System.out.println("Incorrect path name");
        }
    }

    /**
     * 
     * Reads two files consisting of sentences and its corresponding pos tags to count frequencies of each words
     * observed as specific pos tags. Creates a hashmap with the pos tag as the key and another hashmap with <word, frequency> as its value.
     * 
     * @param sentenceFile
     * @param tagsFile
     * @throws IOException
     */
    public void observationsTraining(String sentenceFile, String tagsFile) throws IOException {
        BufferedReader sentenceInput = null;
        BufferedReader tagsInput = null;
        observations = new HashMap<>();
        try {
            //try to open files
            sentenceInput = new BufferedReader(new FileReader(sentenceFile));
            tagsInput = new BufferedReader(new FileReader(tagsFile));
            String sentenceLine = sentenceInput.readLine();
            String tagsLine = tagsInput.readLine();
            while (sentenceLine != null && tagsLine != null) {
                //apply lowercase and split words & tags from " "
                sentenceLine = sentenceLine.toLowerCase();
                String[] textArray = sentenceLine.split(" ");
                String[] tagsArray = tagsLine.split(" ");

                //go over each pos
                for (int i = 0; i < textArray.length; i++) {

                    //if the map already contains that pos
                    if (observations.containsKey(tagsArray[i])){
                        HashMap<String, Double> wordCount = observations.get(tagsArray[i]);

                        //add frequency of 1
                        if (wordCount.containsKey(textArray[i])){
                            wordCount.put(textArray[i], wordCount.get(textArray[i]) + 1.0);
                        }
                        //or make a new one with frequaency of 1 to start with
                        else {
                            wordCount.put(textArray[i], 1.0);
                        }
                    }
                    //else, add new key of tagsArray[i] map with a new map inside
                    else {
                        observations.put(tagsArray[i], new HashMap<>());
                        observations.get(tagsArray[i]).put(textArray[i], 1.0);
                    }
                }
                //read the next lines in each of the files
                sentenceLine = sentenceInput.readLine();
                tagsLine = tagsInput.readLine();
            }
        }
        //Catch exceptions
        catch (Exception e){
            System.out.println("Incorrect path name or file error");
        }
        //close both files if they aren't null
        finally{
            if (sentenceInput != null) sentenceInput.close();
            if (tagsInput != null) tagsInput.close();
            // Normalize by converting inner maps of observations into logs of the fractions
            if (!observations.isEmpty()) {
                for (String tag : observations.keySet()) {
                    Double total = 0.0;
                    HashMap<String, Double> innerMap = observations.get(tag);
                    for (String word : innerMap.keySet()) {
                        total += innerMap.get(word);
                    }
                    for (String word : innerMap.keySet()) {
                        innerMap.put(word, Math.log(innerMap.get(word)/total));
                    }
                }
            }
        }
    }


    /**
     * Transition training method
     * @throws IOException
     */
    public void transitionsTraining(String tagsFile) throws IOException {
        BufferedReader input = null;
        transitions = new HashMap<>();

        try {
            //Opens the tag file
            input = new BufferedReader(new FileReader(tagsFile));
            String tagLine = input.readLine();
            while (tagLine != null) {
                String[] allLines = tagLine.split(" ");
                for (int i = 0; i < allLines.length; i++) {
                    if (i == 0){
                        //start with #
                        if (transitions.containsKey("#")) {
                            HashMap<String, Double> currentState = transitions.get("#");
                            if (currentState.containsKey(allLines[0])) {
                                currentState.put(allLines[0], currentState.get(allLines[0]) + 1.0);
                                transitions.put("#", currentState);
                            }
                            else {
                                currentState.put(allLines[0], 1.0);
                                transitions.put("#", currentState);
                            }
                        }
                        else {
                            transitions.put("#", new HashMap<>());
                            transitions.get("#").put(allLines[0], 1.0);
                        }
                    }
                    String next = allLines[i + 1];
                    if (transitions.containsKey(allLines[i])) {
                        // currentState: inner map
                        HashMap<String, Double> currentState = transitions.get(allLines[i]);
                        //if inner map has the next part of speech, add it
                        if (currentState.containsKey(next)) {
                            currentState.put(next, currentState.get(next) + 1);
                            transitions.put(allLines[i], currentState);
                        }
                        // if it doesn't have that, just add to inner map
                        else {
                            currentState.put(next, 1.0);
                            transitions.put(allLines[i], currentState);
                        }
                    }
                    // tag is not in the transition map, then create a new map
                    else {
                        transitions.put(allLines[i], new HashMap<>());
                        transitions.get(allLines[i]).put(next, 1.0);
                    }
                }
                tagLine = input.readLine();
            }
        }
        catch (Exception e){
            System.out.println("Incorrect path name or file error");
        }

        //close file
        finally{
            if(input!= null) input.close();
            //normalize the values
            if (!transitions.isEmpty()) {
                for (String tag : transitions.keySet()) {
                    Double total = 0.0;
                    HashMap<String, Double> innerMap = transitions.get(tag);
                    for (String word : innerMap.keySet()) {
                        total += innerMap.get(word);
                    }
                    for (String word : innerMap.keySet()) {
                        innerMap.put(word, Math.log(innerMap.get(word)/total));
                    }
                }
            }
        }
    }

    public String viterbi(String lineInput) {
        // default start value
        String currentStates = "#";
        Map<String, Double> currScores = new HashMap<>();
        currScores.put(currentStates, 0.0);

        //initialize: blank line at first
        String lineOut = "";
        String lastTag = "";

        //highest score: negative infinity to ensure it get swapped out
        double highScore = Double.NEGATIVE_INFINITY;
        List<Map<String, String>> backtrack = new ArrayList<>();
        Stack<String> printStrings = new Stack<>();

        // loop through words
        String[] textArray = lineInput.split(" ");
        for (int k = 0; k < textArray.length; k++) {
            Set<String> nextStates = new HashSet<>();
            Map<String, Double> nextScores = new HashMap<>();
            double nextScore;

            //loop previous stages
            for (String current : currScores.keySet()) {

                //check state during transitions
                if (transitions.containsKey(current) && !transitions.get(current).isEmpty()) {
                    for (String next : transitions.get(current).keySet()) {
                        nextStates.add(next);
                        //change the score, with a penalty if it's not in observations
                        if (observations.containsKey(next) && observations.get(next).containsKey(textArray[k])) {
                            nextScore = currScores.get(current) + transitions.get(current).get(next) + observations.get(next).get(textArray[k]);
                        }
                        else {
                            nextScore = currScores.get(current) + transitions.get(current).get(next) + unseen;
                        }
                        if (!nextScores.containsKey(next) || nextScore > nextScores.get(next)) {
                            nextScores.put(next, nextScore);
                            if (backtrack.size() <= k) {
                                backtrack.add(new HashMap<>());
                            }
                            backtrack.get(k).put(next, current);
                        }
                    }
                }
            }
            //advance to the next set of scores
            currScores = nextScores;
        }

        // go through previous scores to find one with the highest score
        for (String current : currScores.keySet()) {
            if (currScores.get(current) > highScore) {
                highScore = currScores.get(current);
                lastTag = current;
            }
        }

        printStrings.push(lastTag);
        // go backwards
        for (int i = textArray.length - 1; i > 0; i--) {
            printStrings.push(backtrack.get(i).get(printStrings.peek()));
        }
        //pop everything in the stack, if it contains anything
        while (!printStrings.isEmpty()) {
            lineOut += printStrings.pop() + " ";
        }
        //return the output
        return lineOut;
    }

    public String createFile(String textFile) throws IOException {
        String sudiFile = null;
        BufferedReader input = null;
        BufferedWriter output = null;

        try {
            // rename file, will return the name of the new file
            sudiFile = textFile.substring(0, textFile.length() - 4) + "-sudi.txt";
            input = new BufferedReader(new FileReader(textFile));
            output = new BufferedWriter(new FileWriter(sudiFile));

            String lineInput;
            String sudiLine;
            //read through, makes the viterbi alg. read each line
            while ((lineInput = input.readLine()) != null) {
                sudiLine = this.viterbi(lineInput);
                output.write(sudiLine + "\n");
            }
        }
        catch (Exception e) {
            System.out.println("Empty tag file");
        }
        finally {
            if (input != null) input.close();
            if (output != null) output.close();
            return sudiFile;
        }
    }

    public void accuracyTest(String tagsFile, String sudiFile) throws IOException {
        BufferedReader inputTags = null;
        BufferedReader inputSudi = null;
        try{
            //input both the answer and the one that sudi analyzed
            inputTags = new BufferedReader(new FileReader(tagsFile));
            inputSudi = new BufferedReader(new FileReader(sudiFile));
            String[] tagsArray;
            String[] sudiArray;
            // total tags
            int count = 0;
            //correct score, which starts from 0
            int correct = 0;

            String sudiLine = inputSudi.readLine();
            String tagsLine = inputTags.readLine();
            while(tagsLine != null){
                tagsArray = tagsLine.split(" ");
                sudiArray = sudiLine.split(" ");
                for(int i=0; i < tagsArray.length; i++){
                    // if tags are equal, increment correct
                    if(tagsArray[i].equals(sudiArray[i])){
                        correct++;
                    }
                }
                count += tagsArray.length;
                //move to next line
                tagsLine = inputTags.readLine();
                sudiLine = inputSudi.readLine();
            }
            System.out.println("Testing: " + tagsFile);
            System.out.println("# of tags: " + count);
            System.out.println((float) correct / count * 100 + "% correct. Ratio: " + correct + " correct vs " +(count-correct) + " incorrect");

        }
        catch(Exception e){
            System.out.println("Path name error, check if file created.");
        }
        finally{
            if (inputTags != null) inputTags.close();
            if (inputSudi != null) inputSudi.close();
        }
    }

    //The Console
    public static void runConsole(Sudi sudi){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter your sentence: ");
        String lineIn = scan.nextLine();
        try {
            String lineOut = sudi.viterbi(lineIn);
            System.out.println(lineOut + "\n");
        }
        catch(Exception E) {
            System.out.println("Check file again!");
        }
    }

    public static void main(String[] args) throws IOException {
        //Simple-test test runs
        Sudi simpleTest = new Sudi("simple-train-sentences.txt", "simple-train-tags.txt");
        String sudiFile = simpleTest.createFile("simple-test-sentences.txt");
        simpleTest.accuracyTest("simple-test-tags.txt", sudiFile);
        System.out.println();

        //Brown test runs
        Sudi brownTest = new Sudi("brown-train-sentences.txt", "brown-train-tags.txt");
        String brownFile = brownTest.createFile("brown-test-sentences.txt");
        brownTest.accuracyTest("brown-test-tags.txt", brownFile);
        System.out.println();

        //console run
        runConsole(simpleTest);
    }
}
