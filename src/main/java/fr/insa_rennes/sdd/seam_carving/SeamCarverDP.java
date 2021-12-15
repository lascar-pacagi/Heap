package fr.insa_rennes.sdd.seam_carving;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.function.BiFunction;

import fr.insa_rennes.sdd.graph.Coordinate;

public class SeamCarverDP extends SeamCarver {
	
	public SeamCarverDP(Picture picture) {
		super(picture);		
	}
	
	public SeamCarverDP(Picture picture, BiFunction<Double, Double, Double> energyFunction) {
		super(picture, energyFunction);
	}
	
	@Override
	public void reduceToSize(int width, int height) {
		int w = picture.width();
		int h = picture.height();
		if (width < 0 || height < 0) {
			throw new IllegalArgumentException();
		}
		while (w > width) {			
			cropVertical(verticalSeam());
			w--;
		}
		while (h > height) {
			cropHorizontal(horizontalSeam());			
			h--;
		}
	}

	public Deque<Coordinate> horizontalSeam() {		
		double[][] dp = energyMap();
		int w = picture.width();
		int h = picture.height();
		for (int col = 1; col < w; col++) {
			for (int row = 0; row < h; row++) {
				double minValue = dp[row][col - 1];
				if (row != 0) {
					minValue = Math.min(minValue, dp[row - 1][col - 1]);
				}
				if (row != h - 1) {
					minValue = Math.min(minValue, dp[row + 1][col - 1]);
				}
				dp[row][col] += minValue;
			}
		}	
		Deque<Coordinate> res = new ArrayDeque<>();
		int current = 0;
		double minValue = Double.POSITIVE_INFINITY;
		for (int row = 0; row < h; row++) {
			if (dp[row][w - 1] < minValue) {
				current = row;
				minValue = dp[row][w - 1];
			}
		}
		res.add(new Coordinate(current, w - 1));
		for (int col = w - 2; col >= 0; col--) {
			int next = current;
			double v = dp[current][col];
			double v1;
			if (current != 0 && (v1 = dp[current - 1][col]) < v) {
				v = v1;
				next = current - 1;
			}
			if (current != h - 1 && (v1 = dp[current + 1][col]) < v) {
				v = v1;
				next = current + 1;
			}
			res.addFirst(new Coordinate(next, col));
			current = next;
		}
		return res;
	}
	
	public Deque<Coordinate> verticalSeam() {
		double[][] dp = energyMap();
		int w = picture.width();
		int h = picture.height();
		for (int row = 1; row < h; row++) {
			for (int col = 0; col < w; col++) {
				double minValue = dp[row - 1][col];				
				if (col != 0) {
					minValue = Math.min(minValue, dp[row - 1][col - 1]);
				}
				if (col != w - 1) {
					minValue = Math.min(minValue, dp[row - 1][col + 1]);
				}
				dp[row][col] += minValue;
			}
		}	
		Deque<Coordinate> res = new ArrayDeque<>();
		int current = 0;
		double minValue = Double.POSITIVE_INFINITY;
		for (int col = 0; col < w; col++) {
			if (dp[h - 1][col] < minValue) {
				current = col;
				minValue = dp[h - 1][col];
			}
		}
		res.add(new Coordinate(h - 1, current));
		for (int row = h - 2; row >= 0; row--) {
			int next = current;
			double v = dp[row][current];
			double v1;
			if (current != 0 && (v1 = dp[row][current - 1]) < v) {
				v = v1;
				next = current - 1;
			}
			if (current != w - 1 && (v1 = dp[row][current + 1]) < v) {
				v = v1;
				next = current + 1;
			}
			res.addFirst(new Coordinate(row, next));
			current = next;
		}
		return res;
	}
	
}
