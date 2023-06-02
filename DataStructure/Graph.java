package DataStructure;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    private int[][] adjacencyMatrix;
    private int numVertices;

    public Graph (int numVertices){ 
        this.numVertices = numVertices;
        this.adjacencyMatrix = createRandomAdjacencyMatrix(numVertices);
        System.out.println("Adjacency Matrix:");
        printAdjacencyMatrix();
    }

    public void setAdjacencyMatrix(int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    public int[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    public int getNumVertices() {
        return numVertices;
    }

    public int[][] createRandomAdjacencyMatrix(int numVertices){
        int[][] adjacencyMatrix = new int[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++)
            for (int j = 0; j < numVertices; j++)
                if(i == j)
                    adjacencyMatrix[i][j] = 0;
                else if(i < j)
                    adjacencyMatrix[i][j] = (int) (Math.random() * 2);
                else
                    adjacencyMatrix[i][j] = adjacencyMatrix[j][i];
                
        return adjacencyMatrix;
    }

    public void printAdjacencyMatrix(){
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++)
                System.out.print(adjacencyMatrix[i][j] + " ");
            System.out.println();
        }
    }

    // Method to Graph coloring
    public void colorGraph(int numColors) {
        List<Thread> threads = new ArrayList<>();

        // Create a thread for each vertex
        for (int i = 0; i < numVertices; i++) {
            final int vertex = i;
            Thread thread = new Thread(() -> {
                // Color the vertex
                colorVertex(vertex, numColors);
            });
            threads.add(thread);
            thread.start();
        }

        // Wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Colored Adjacency Matrix:");
        printAdjacencyMatrix();

        if (verifyGraphColoring()) {
            
            System.out.println("Graph coloring is correct!");
            
        } else {
            System.out.println("Graph coloring is incorrect!");
        }
    }

    // Method to color a vertex
    private void colorVertex(int vertex, int numColors) {
        // Get the colors used by adjacent vertices
        List<Integer> usedColors = new ArrayList<>();
        for (int i = 0; i < numVertices; i++) {
            if (adjacencyMatrix[vertex][i] == 1 && i < vertex) {
                usedColors.add(adjacencyMatrix[i][i]);
            } else if (adjacencyMatrix[vertex][i] == 1 && i > vertex) {
                usedColors.add(adjacencyMatrix[i][i - 1]);
            }
        }

        // Find the first available color
        int color = 1;
        while (usedColors.contains(color)) {
            color++;
        }

        // Assign the color
        adjacencyMatrix[vertex][vertex] = color;
    }

    private boolean verifyGraphColoring() {
        for (int i = 0; i < numVertices; i++) {
            for (int j = i + 1; j < numVertices; j++) {
                if (adjacencyMatrix[i][j] == 1 && adjacencyMatrix[i][i] == adjacencyMatrix[j][j]) {
                    return false;
                }
            }
        }
        return true;
    }

}
