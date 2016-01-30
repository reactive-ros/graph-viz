package graph_viz;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import com.jgraph.layout.JGraphFacade;
import com.jgraph.layout.JGraphLayout;
import com.jgraph.layout.graph.JGraphSimpleLayout;
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
public class GraphVisualizer {
    private static final Color DEFAULT_BG_COLOR = Color.decode("#FAFBFF");
    private static final Dimension DEFAULT_SIZE = new Dimension(1200, 600);

    private JGraphModelAdapter adapter;

    public void display(Stream stream) {
        display(stream.getGraph());
    }

    public <V, E> void display(DirectedPseudograph<V, E> g) {
        adapter = new JGraphModelAdapter<>(g);

        // Setup JGraph
        JGraph jgraph = new JGraph(adapter);
        jgraph.setPreferredSize(DEFAULT_SIZE);
        jgraph.setBackground(DEFAULT_BG_COLOR);

        JGraphLayout layout = new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_CIRCLE);
        JGraphFacade facade = new JGraphFacade(jgraph);
        Map nested = facade.createNestedMap(true, true);
        jgraph.getGraphLayoutCache().edit(nested);

        // Layout nodes
        List<V> previous = new ArrayList<>();
        for (V v : g.vertexSet())
            if (Graphs.predecessorListOf(g, v).isEmpty())
                previous.add(v);

        List<List<V>> all = new ArrayList<>();
        while (true) {
            all.add(previous);

            List<V> next = new ArrayList<>();

            for (V v : previous)
                next.addAll(Graphs.successorListOf(g, v));

            if (next.isEmpty()) break;
            previous = next;
        }
        int x = 100;
        for (List<V> cur : all) {
            int y = 100;
            for (V v : cur) {
                positionVertexAt(v, x, y);
                y += 100;
            }
            x += 150;
        }

        // Show in Frame
        JFrame frame = new JFrame();
        frame.setSize(DEFAULT_SIZE);
//        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        frame.getContentPane().add(new JScrollPane(jgraph));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocation(0, 0);
        frame.pack();
        frame.setVisible(true);
    }

    private void positionVertexAt(Object vertex, int x, int y) {
        DefaultGraphCell cell = adapter.getVertexCell(vertex);
        Map attr = cell.getAttributes();
        Rectangle2D b = GraphConstants.getBounds(attr);

        GraphConstants.setBounds(attr, new Rectangle(x, y, ((int) b.getWidth()), (int) b.getHeight()));

        Map cellAttr = new HashMap();
        cellAttr.put(cell, attr);
        adapter.edit(cellAttr, null, null, null);
    }
}


/* -------------------------- LAYOUTS --------------------------------------
JGraphLayout layout = new JGraphSimpleLayout(JGraphSimpleLayout.TYPE_CIRCLE);
JGraphFacade facade = new JGraphFacade(jgraph);
Map nested = facade.createNestedMap(true, true);
jgraph.getGraphLayoutCache().edit(nested);*/

/*JGraphFacade facade = new JGraphFacade(jgraph, roots.toArray());
JGraphLayout layout = new JGraphOrganicLayout();
layout.run(facade);
Map nested = facade.createNestedMap(true, true);
jgraph.getGraphLayoutCache().edit(nested);*/

/*TopologicalOrderIterator<Transformer, DefaultEdge> iterator = new TopologicalOrderIterator<Transformer, DefaultEdge>(g);
int x = 0, y = 0;
Transformer last = null;
while (iterator.hasNext()) {
    Transformer cur = iterator.next();
    if (last == null)
        positionVertexAt(cur, x, y);
    else if ()
    x += 150;
    last = cur;
}*/
