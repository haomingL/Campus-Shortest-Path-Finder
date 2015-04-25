package hw6.test;

/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph, the Marvel parser, and your BFS
 * algorithm.
 **/
import hw5.Edge; 
import hw5.Graph;
import hw5.Node;
import hw6.MarvelParser.MalformedDataException;
import hw6.MarvelPaths;

import java.io.*;
import java.util.*;

/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph.
 **/
public class HW6TestDriver {

    public static void main(String args[]) {
        try {
            if (args.length > 1) {
                printUsage();
                return;
            }

            HW6TestDriver td;

            if (args.length == 0) {
                td = new HW6TestDriver(new InputStreamReader(System.in),
                                       new OutputStreamWriter(System.out));
            } else {

                String fileName = args[0];
                File tests = new File (fileName);

                if (tests.exists() || tests.canRead()) {
                    td = new HW6TestDriver(new FileReader(tests),
                                           new OutputStreamWriter(System.out));
                } else {
                    System.err.println("Cannot read from " + tests.toString());
                    printUsage();
                    return;
                }
            }

            td.runTests();

        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        }
    }

    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("to read from a file: java hw6.test.HW6TestDriver <name of input script>");
        System.err.println("to read from standard in: java hw6.test.HW6TestDriver");
    }

    /** String -> Graph: maps the names of graphs to the actual graph **/
    //TODO for the student: Parameterize the next line correctly.
    private final Map<String, Graph<String, String>> graphs = new HashMap<String, Graph<String, String>>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @requires r != null && w != null
     *
     * @effects Creates a new HW6TestDriver which reads command from
     * <tt>r</tt> and writes results to <tt>w</tt>.
     **/
    public HW6TestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @effects Executes the commands read from the input and writes results to the output
     * @throws IOException if the input or output sources encounter an IOException
     * @throws MalformedDataException 
     **/
    public void runTests()
        throws IOException
    {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            }
            else
            {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            if (command.equals("LoadGraph")) {
                loadGraph(arguments);
            } else if (command.equals("AddNode")) {
                addNode(arguments);
            } else if (command.equals("CreateGraph")) {
            	createGraph(arguments);
            } else if (command.equals("FindPath")) {
            	findPath(arguments);
            } else if (command.equals("AddEdge")) {
            	addEdge(arguments);
            } else if (command.equals("ListChildren")) {
            	listChildren(arguments);
            } else if (command.equals("ListNodes")) {
            	listNodes(arguments);
            } else {
                output.println("Unrecognized command: " + command);
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
        }
    }

    private void loadGraph(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }
        String graphName = arguments.get(0);
        String properPath = "src/hw6/data/" + arguments.get(1);
        try {
			loadGraph(graphName, properPath);
		} catch (MalformedDataException e) {
			System.out.println("Given file is not well formed");
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Unexpected exception" + e.toString());
			e.printStackTrace();
		}
    }

    private void loadGraph(String graphName, String path)  throws MalformedDataException {
    	MarvelPaths p = new MarvelPaths();
    	Graph<String, String> g = p.buildPaths(path);
    	graphs.put(graphName, g);
    	output.println("loaded graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to addNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
    	graphs.get(graphName).addNode(nodeName);
    	output.println("added node " + nodeName + " to " + graphName);
    }
    
    private void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
    	graphs.put(graphName, new Graph<String, String>());
    	output.println("created graph " + graphName);
    }
    
    private void findPath(List<String> arguments) {
    	if (arguments.size() != 3) {
    		throw new CommandException("Bad arguments to CreateGraph: " + arguments);
    	}
    	String graphName = arguments.get(0);
    	Node<String> start = new Node<String>(arguments.get(1).replaceAll("_", " "));
    	Node<String> end = new Node<String>(arguments.get(2).replaceAll("_", " "));
    	findPath(graphName, start, end);
    }

    private void findPath(String graphName, Node<String> start, Node<String> end) {
    	List<Edge<String, String>> path = new LinkedList<Edge<String, String>>();
    	MarvelPaths p = new MarvelPaths();
    	Graph<String, String> g = graphs.get(graphName);
    	if (!g.contains(start) && !g.contains(end)) {
    		output.println("unknown character " + start.getData());
    		if (!start.equals(end)) {
    			output.println("unknown character " + end.getData());
    		}
    	} else if (!g.contains(start)) {
			output.println("unknown character " + start.getData());
		} else if (!g.contains(end)) {
			output.println("unknown character " + end.getData());
		} else {
	    	boolean find = p.findPaths(start, end, g, path);
	    	output.println("path from " + start.getData() + " to " + end.getData() + ":");
	    	if (find) {
		    	for (int i = 0; i < path.size(); i++) {
		    		output.println(path.get(i).toString());
		    	}
	    	} else {
	    		output.println("no path found");
	    	}
		}
	}
    
    private void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to addEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
            String edgeLabel) {
    	Node<String> parent = new Node<String>(parentName);
    	Node<String> child = new Node<String>(childName);
    	Edge<String, String> newEdge = new Edge<String, String>(parent, child, edgeLabel);
    	graphs.get(graphName).addEdge(newEdge);
    	output.println("added edge " + newEdge.getLabel() + " from "
    			+ newEdge.getStart().getData() + " to " + newEdge.getEnd().getData()
    			+ " in " + graphName);
    }
    
    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to listNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
    	List<Node<String>> listNodes = graphs.get(graphName).listNodes();
    	output.print(graphName + " contains:");
    	for (Node<String> n: listNodes) {
    		output.print(" " + n.getData());
    	}
    	output.println();
    }
    
    private void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to listChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
    	Node<String> parent = new Node<String>(parentName);
    	List<Edge<String, String>> listChildren = graphs.get(graphName).listEnds(parent);
    	output.print("the children of " + parentName + " in "+ graphName + " are:");
    	for (Edge<String, String> e: listChildren) {
    		output.print(" " + e.getEnd().getData() + "(" + e.getLabel() + ")");
    	}
    	output.println();
    }

	/**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }
        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}

