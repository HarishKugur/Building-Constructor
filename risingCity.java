import java.io.*;
import java.lang.*;
import java.util.*;

class risingCity
{

    static minHeapBuildingNode activeBuilding = null;

    public static void main(String args[]) throws IOException
    {
        /* inputFileReader is used to read the input file */
        BufferedReader inputFileReader = new BufferedReader(new FileReader(args[0]));
        
        /* Finding maximum building construction time for the contruction Counter to run
        * timeReaderFile helps to calculate the maximum execution time of all the buildings */
        BufferedReader timeReaderFile = new BufferedReader(new FileReader(args[0]));
        
        List<Long> constructionInfoInteger = new ArrayList<>();
        List<String> constructionInfoString = new ArrayList<>();

        int constructionCounter = 0, isConstructionActive = 0;
        boolean buildingConstructed = false;
        long maxBuildingConstructTime = 0;

        String buildingCommand;
        while((buildingCommand = timeReaderFile.readLine()) != null)
        {
            if(buildingCommand.split(" ")[1].split("\\(")[0].equalsIgnoreCase("Insert"))
                maxBuildingConstructTime =  maxBuildingConstructTime + calculateMaxTotalTime(buildingCommand);
        }

        int buildingNum1, buildingNum2, totalBuildingTime = 0, buildingExecutionTime = 0;;
        Building building = new Building();

        /*Read the input file commands to capture the building number, total time of each building*/
        String inputFileLine = inputFileReader.readLine();

        /*Split the RHS of the Input Line by separating the command occurence time
        * Example : Insert(10,50)
        * */
        String commandInInputFile = inputFileLine.split(" ")[1];
        //System.out.println("RHS of Input Line " + commandInInputFile);

        /*Split the command and store it whether Insert or PrintBuilding
        * Example : Insert or PrintBuilding
        * */
        String inputCommand = (commandInInputFile.split("\\("))[0];
        //System.out.println("command" + inputCommand);

        /*To extract building number followed by total building time
        * Example : 10,50)
        * */
        String EachBuildingInfo = (commandInInputFile.split("\\("))[1];

        long openingTime = System.nanoTime();

        long executionTimeCounter = 0;
        long commandOccurrenceTime = Integer.parseInt(inputFileLine.split(":")[0]);

        for(executionTimeCounter = 0; executionTimeCounter <= maxBuildingConstructTime; executionTimeCounter++)
        {
            /* When commandOccurrenceTime is equal to executionTimeCounter, then read the input command line
            * Input Commands can be
            * 1. Insert
            * 2. PrintBuilding
            * */
            if(commandOccurrenceTime == executionTimeCounter) {

            /*If inputCommand is Insert, then get the building number and total building time to insert it into Heap*/
                if(inputCommand.equalsIgnoreCase("Insert")) {

                    buildingNum1 = getArguments(EachBuildingInfo, 1);
                    totalBuildingTime = getArguments(EachBuildingInfo, 2);
                    building.insertBuildingInfo(buildingNum1, totalBuildingTime, buildingExecutionTime);
                }

                if(inputCommand.equalsIgnoreCase("PrintBuilding") && constructionInfoInteger.contains(commandOccurrenceTime)) {

                   if(EachBuildingInfo.split("\\)")[0].split(",").length==1)
                    {
                        buildingNum1 = getArguments(EachBuildingInfo, 1);
                        building.PrintWayneBuilding(buildingNum1);
                    }

                    if(EachBuildingInfo.split("\\)")[0].split(",").length==2)
                    {
                        buildingNum1 = getArguments(EachBuildingInfo, 1);
                        buildingNum2 = getArguments(EachBuildingInfo, 2);
                        building.PrintWayneBuilding(buildingNum1,buildingNum2);
                    }
                }

                if((inputFileLine = inputFileReader.readLine()) != null)
                {
                    commandOccurrenceTime = Integer.parseInt(inputFileLine.split(":")[0]);
                    commandInInputFile = inputFileLine.split(" ")[1];
                    inputCommand = (commandInInputFile.split("\\("))[0];
                    EachBuildingInfo = (commandInInputFile.split("\\("))[1];

                    if(inputCommand.equalsIgnoreCase("PrintBuilding")) {
                            constructionInfoInteger.add(commandOccurrenceTime);
                            constructionInfoString.add(EachBuildingInfo);
                    }
                }

                //building.WritetoOutput();

            }

            /* To check if any building is in construction state
            * If isConstructionActive is 0 then it extracts the building with Minimum Execution Time */
            if(isConstructionActive == 0)
            {
                if(building.heap.isEmpty())
                {
                    continue;
                }
                else
                {
                    activeBuilding = building.heap.extractMin();
                    isConstructionActive=1;
                    buildingConstructed = false;
                }
            }

            /* To check if any building is in construction state
             * If isConstructionActive is 1 then it extracts the building with Minimum Execution Time */
            if(isConstructionActive==1)
            {
                constructionCounter ++;
                activeBuilding.currentConstructedTime += 1;
                activeBuilding.redBlackNode.building_ExecutionTime += 1;

                if(activeBuilding.redBlackNode.building_ExecutionTime == activeBuilding.totalBuildingTime) {
                    if(constructionInfoInteger.contains(executionTimeCounter + 1)) {
                        int buildingIndex = constructionInfoInteger.indexOf(executionTimeCounter + 1);

                            if (constructionInfoString.get(buildingIndex).split("\\)")[0].split(",").length == 2) {
                                buildingNum1 = getArguments(EachBuildingInfo, 1);
                                buildingNum2 = getArguments(EachBuildingInfo, 2);
                                constructionInfoInteger.remove(buildingIndex);
                                constructionInfoString.remove(buildingIndex);
                                building.PrintWayneBuilding(buildingNum1, buildingNum2);
                            }
                            else {
                                buildingNum1 = getArguments(EachBuildingInfo, 1);
                                constructionInfoInteger.remove(buildingIndex);
                                constructionInfoString.remove(buildingIndex);
                                building.PrintWayneBuilding(buildingNum1);
                            }
                    }
                    buildingConstructed = true;
                    building.printWayneDeletedBuilding(activeBuilding.redBlackNode.ID,(executionTimeCounter + 1));
                    //building.WritetoOutput();
                    building.rbTree.deleteRedBlackNode(activeBuilding.redBlackNode);

                    if(constructionCounter != 5) {
                        constructionCounter = 0;
                        isConstructionActive = 0;
                    }
                }
                else  {
                    buildingConstructed = false;
                }

            /*Check if the building has been constructed for 5 consecutive days
            * If yes, then push back the building into Heap and then extract building with least execution time
            * */
                if(constructionCounter == 5)
                {
                    constructionCounter = 0;
                    if(!buildingConstructed) {
                        building.heap.insertIntoMinHeap(activeBuilding.totalBuildingTime, activeBuilding.currentConstructedTime, activeBuilding.redBlackNode);
                    }
                    isConstructionActive = 0;
                }
            }
        }

        building.WritetoOutput();
        long closingTime = System.nanoTime();
        //System.out.println("Total time taken for all buildings construction:" + ((double)(closingTime - openingTime)/1000000000));
        inputFileReader.close();
    }

    private static long calculateMaxTotalTime(String buildingCommand) {
        return  Long.parseLong(buildingCommand.split(",")[1].split("\\)")[0]) + Long.parseLong(buildingCommand.split(":")[0]);
    }

    private static int getArguments(String buildingData, int argumentNumber) {
        if(argumentNumber == 1) {
            return Integer.parseInt(buildingData.split("\\)")[0].split(",")[0]);
        }
        else {
            return Integer.parseInt(buildingData.split("\\)")[0].split(",")[1]);
        }
    }
}
