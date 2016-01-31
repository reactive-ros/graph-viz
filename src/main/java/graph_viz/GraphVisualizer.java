package graph_viz;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.*;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.JGraphLayout;
import com.jgraph.layout.graph.JGraphAnnealingLayout;
import com.jgraph.layout.graph.JGraphSimpleLayout;
import com.jgraph.layout.graph.JGraphSpringLayout;
import com.jgraph.layout.hierarchical.JGraphHierarchicalLayout;
import com.jgraph.layout.organic.JGraphOrganicLayout;
import com.jgraph.layout.organic.JGraphSelfOrganizingOrganicLayout;
import com.jgraph.layout.tree.JGraphCompactTreeLayout;
import com.jgraph.layout.tree.JGraphRadialTreeLayout;
import com.jgraph.layout.tree.JGraphTreeLayout;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import org.jgrapht.Graphs;
import org.jgrapht.ext.JGraphModelAdapter;
import org.jgrapht.graph.DirectedPseudograph;
import org.reactive_ros.Stream;

/**
 * @author Orestis Melkonian
 */
public final class GraphVisualizer {
    private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
    private static final Dimension DEFAULT_SIZE = new Dimension(1200, 600);

    public static void display(Stream stream) {
        display(stream.getGraph());
    }

    public static <V, E> void display(DirectedPseudograph<V, E> g) {
        JGraphModelAdapter adapter = new JGraphModelAdapter<>(g);

        // Setup JGraph
        JGraph jgraph = new JGraph(adapter);
//        jgraph.setPreferredSize(DEFAULT_SIZE);
//        jgraph.setBackground(DEFAULT_BG_COLOR);

        // Layout nodes
        JGraphFacade facade =
                new JGraphFacade(jgraph, g.vertexSet()
                                          .stream()
                                          .filter(v -> Graphs.predecessorListOf(g, v).isEmpty())
                                          .collect(Collectors.toList())
                                          .toArray());
        JGraphLayout layout = new JGraphHierarchicalLayout();
        layout.run(facade);
        Map nested = facade.createNestedMap(true, true);
        jgraph.getGraphLayoutCache().edit(nested);

        // Show in Frame
        JFrame frame = new JFrame();
//        frame.setSize(DEFAULT_SIZE);
//        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.getContentPane().add(new JScrollPane(jgraph));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(0, 0);
        frame.pack();
        frame.setVisible(true);
    }
}


// -------------------------- LAYOUTS --------------------------------------
/*JGraphLayout layout = new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_CIRCLE);
JGraphFacade facade = new JGraphFacade(jgraph);
Map nested = facade.createNestedMap(true, true);
jgraph.getGraphLayoutCache().edit(nested);*/

/*Set<V> visited = new HashSet<>();
List<V> previous = g.vertexSet()
        .stream()
        .filter(v -> Graphs.predecessorListOf(g, v).isEmpty())
        .collect(Collectors.toList());
visited.addAll(previous);
List<List<V>> all = new ArrayList<>();
while (true) {
    all.add(previous);
    List<V> next = previous.stream()
            .flatMap(v -> Graphs.successorListOf(g, v).stream())
            .filter(v -> !visited.contains(v))
            .collect(Collectors.toList());

    if (next.isEmpty()) break; // stop searching
    visited.addAll(next);
    previous = next;
}

int x = 100;
boolean dir = true;
for (List<V> cur : all) {
    int y = 100 + (dir ? 0 : -50);
    for (V v : cur) {
        positionVertexAt(v, x, y);
        y += 100;
    }
    x += 150;
    dir = !dir;

private static void positionVertexAt(JGraphModelAdapter adapter, Object vertex, int x, int y) {
    DefaultGraphCell cell = adapter.getVertexCell(vertex);
    Map attr = cell.getAttributes();
    Rectangle2D b = GraphConstants.getBounds(attr);

    GraphConstants.setBounds(attr, new Rectangle(x, y, ((int) b.getWidth()), (int) b.getHeight()));

    Map cellAttr = new HashMap();
    cellAttr.put(cell, attr);
    adapter.edit(cellAttr, null, null, null);
}*/
