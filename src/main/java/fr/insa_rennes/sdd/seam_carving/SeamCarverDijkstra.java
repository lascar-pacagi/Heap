package fr.insa_rennes.sdd.seam_carving;

import java.util.Deque;
import java.util.function.BiFunction;
import fr.insa_rennes.sdd.dijkstra.Dijkstra;
import fr.insa_rennes.sdd.graph.Coordinate;
import fr.insa_rennes.sdd.graph.LeftToRightGridGraph;
import fr.insa_rennes.sdd.graph.TopToBottomGridGraph;

public class SeamCarverDijkstra extends SeamCarver {
	
	public SeamCarverDijkstra(Picture picture) {
		super(picture);		
	}
	
	public SeamCarverDijkstra(Picture picture, BiFunction<Double, Double, Double> energyFunction) {
		super(picture, energyFunction);
	}
	
	public void reduceToSize(int width, int height) {
		int w = picture.width();
		int h = picture.height();
		if (width < 0 || height < 0) {
			throw new IllegalArgumentException();
		}
		while (w > width) {
			cropVertical(verticalSeam());
			/*double[][] energyMap = energyMap();
			Dijkstra<Coordinate> dj = new Dijkstra<>(new TopToBottomGridGraph(energyMap), Coordinate.TOP);
			Deque<Coordinate> verticalSeam = dj.getPathTo(Coordinate.BOTTOM);
			verticalSeam.removeFirst();
			verticalSeam.removeLast();
			cropVertical(verticalSeam);*/
			w--;
		}
		while (h > height) {
			cropHorizontal(horizontalSeam());
			/*double[][] energyMap = energyMap();
			Dijkstra<Coordinate> dj = new Dijkstra<>(new LeftToRightGridGraph(energyMap), Coordinate.LEFT);
			Deque<Coordinate> horizontalSeam = dj.getPathTo(Coordinate.RIGHT);
			horizontalSeam.removeFirst();
			horizontalSeam.removeLast();
			cropHorizontal(horizontalSeam);*/			
			h--;
		}
	}
		
	public Deque<Coordinate> horizontalSeam() {		
		double[][] energyMap = energyMap();
		Dijkstra<Coordinate> dj = new Dijkstra<>(new LeftToRightGridGraph(energyMap), Coordinate.LEFT);
		Deque<Coordinate> seam = dj.getPathTo(Coordinate.RIGHT);
		seam.removeFirst();
		seam.removeLast();
		return seam;
	}
	
	public Deque<Coordinate> verticalSeam() {
		double[][] energyMap = energyMap();
		Dijkstra<Coordinate> dj = new Dijkstra<>(new TopToBottomGridGraph(energyMap), Coordinate.TOP);
		Deque<Coordinate> seam = dj.getPathTo(Coordinate.BOTTOM);
		seam.removeFirst();
		seam.removeLast();
		return seam;
	}
	
}
