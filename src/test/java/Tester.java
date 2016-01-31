import org.junit.Test;
import org.reactive_ros.Stream;
import graph_viz.GraphVisualizer;
import org.reactive_ros.util.functions.Func0;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Orestis Melkonian
 */
public class Tester {   
    
    @Test
    public void test() {
    	// Setup
    	GraphVisualizer viz = new GraphVisualizer();

    	// Define dataflow
    	Stream<?> stream = Stream
				.nat()
				.map(i -> i + 1)
				.map(i -> i + 1);
    	
    	// Visualize
    	viz.display(stream);

    	try { Thread.sleep(Long.MAX_VALUE); }
    	catch(Exception ignored) {}
    }    

}
