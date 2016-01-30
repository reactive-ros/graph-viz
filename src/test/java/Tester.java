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
    	Stream<?> stream = 
            Stream.just(1, 2, 3, 4, 5).collect((Func0<HashMap<Integer, String>>) HashMap::new, (m, i) -> {
                    if (i % 2 == 0) m.put(i, Integer.toString(i));
                }).map(h -> new HashSet<>(h.values())).map(c -> {
                    String ret = "";
                    for (String s : c) ret += s;
                    return ret;
                });
    	
    	// Visualize
    	viz.display(stream);

    	try { Thread.sleep(Long.MAX_VALUE); }
    	catch(Exception ignored) {}
    }    

}
