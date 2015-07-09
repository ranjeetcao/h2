package com.amagi.wordtransform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessedDictionary {
	WordDistanceCalculator wdc;
	Map<String, Node> dictionary;

	public ProcessedDictionary() {
		dictionary = new HashMap<String, Node>();
		wdc = new WordDistanceCalculator();
		ArrayList<Node> nodes = load();
		process(nodes);
	}

	//FIXME: processing can be done in parallel using multiple threads depending on cores
	private void process(ArrayList<Node> nodes) {
		for (Node n1 : nodes) {
			for (Node n2 : nodes) {
				if (n1 != n2) {
					if (wdc.distance(n1.word, n2.word) == 1) {
						n1.neighbours.add(n2);
					}
				}
			}
		}
	}

	private ArrayList<Node> load() {
		ArrayList<Node> nodes = new ArrayList<Node>();
		
		InputStream in = this.getClass().getResourceAsStream("/validwords.txt");
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = "";
		
		try {
			while((line = reader.readLine()) != null) {
				line = line.trim();
				if(line.length() > 0) {
					Node node = new Node(line.toLowerCase());
					nodes.add(node);
					dictionary.put(node.word, node);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nodes;
	}
	
	public int findMinDistance(String source, String dest) {
		if(source == null && dest == null) {
			System.out.println("invalid input");
			return -1;
		} else if(source.equals(dest)) {
			return 0;
		}
		
		Node sourceNode = dictionary.get(source.toLowerCase());
		Node destNode = dictionary.get(dest.toLowerCase());
		
		if(sourceNode == null || destNode == null) {
			System.out.println("transformation not possible");
			return -1;
		}
		
		GraphDistance gd = new GraphDistance();
		List<Node> directions = gd.getDirections(sourceNode, destNode);
		System.out.println(directions);
		return directions.size()-1;
	}
}
