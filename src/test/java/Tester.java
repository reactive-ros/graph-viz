import org.junit.Test;
import org.rhea_core.Stream;
import graph_viz.GraphVisualizer;
import test_data.utilities.Threads;

/**
 * @author Orestis Melkonian
 */
public class Tester {   
    
    @Test
    public void test() {

    	// Define dataflow
    	Stream<?> s = Stream
				.nat()
				.map(i -> i + 1)
				.map(i -> i + 1);

		Stream<?> s2 = Stream.just(0).id().take(1);
    	
    	// Visualize
    	GraphVisualizer.display(s);
		GraphVisualizer.displayAt(s2, 250, 0);

        Threads.sleep();
    }    

}
