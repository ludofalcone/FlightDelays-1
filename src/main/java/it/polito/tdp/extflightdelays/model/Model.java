package it.polito.tdp.extflightdelays.model;

import java.util.*;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {
	
	private Graph<Airport, DefaultWeightedEdge> grafo;
	private ExtFlightDelaysDAO dao;
	private Map<Integer, Airport> idmap;
	
	public Model() {
//	this.grafo= new SimpleWeightedGraph(DefaultWeightedEdge.class);
	this.dao= new ExtFlightDelaysDAO();
	this.idmap= new HashMap<Integer, Airport>();
	this.dao.loadAllAirports(idmap);
}
	
	public void creaGrafo(int nAirlines) {
		this.grafo= new SimpleWeightedGraph(DefaultWeightedEdge.class);
		Graphs.addAllVertices(this.grafo, this.dao.getVertici(nAirlines, idmap));
		List<Rotta> archi= this.dao.getRotte(idmap);
		for(Rotta r: archi) {
			Airport origine= r.getOrigine();
			Airport destinazione= r.getDestinazione();
			int N= r.getN();
			if(grafo.vertexSet().contains(origine) && grafo.vertexSet().contains(destinazione)) {
			DefaultWeightedEdge edge= this.grafo.getEdge(origine, destinazione);
			if(edge!=null) {
				double peso=this.grafo.getEdgeWeight(edge);
				peso+=N;
				this.grafo.setEdgeWeight(origine,  destinazione, peso);
			}
			else {
				this.grafo.addEdge(origine, destinazione);
				this.grafo.setEdgeWeight(origine, destinazione, N);
			}
		}
	}
		System.out.println("Grafo creato!");
		System.out.println("Ci sono "+this.grafo.vertexSet()+" vertici");
		System.out.println("Ci sono "+this.grafo.edgeSet()+" archi");
	}
}

















