package com.amagi.wordtransform;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class GraphDistance {
	private Map<Node, Boolean> vis = new HashMap<Node, Boolean>();
	private Map<Node, Node> prev = new HashMap<Node, Node>();

	public List<Node> getDirections(Node start, Node finish){
	    List<Node> directions = new LinkedList<Node>();
	    Queue<Node> q = new LinkedList<Node>();
	    Node current = start;
	    q.add(current);
	    vis.put(current, true);
	    while(!q.isEmpty()){
	        current = q.remove();
	        if (current.equals(finish)){
	            break;
	        }else{
	            for(Node node : current.neighbours){
	                if(!vis.containsKey(node)){
	                    q.add(node);
	                    vis.put(node, true);
	                    prev.put(node, current);
	                }
	            }
	        }
	    }
	    if (!current.equals(finish)){
	        System.out.println("can't reach destination");
	    }
	    for(Node node = finish; node != null; node = prev.get(node)) {
	        directions.add(node);
	    }
	    return directions;
	}
}
