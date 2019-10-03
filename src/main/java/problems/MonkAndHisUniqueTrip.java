package problems;

import java.util.ArrayList;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;

//unsolved

public class MonkAndHisUniqueTrip {
    
    public static void main(String args[] ) throws Exception {

        List<Route> routes = getRoutes();
        HashMap<Integer, List<Integer>> forwardCityRoutes = new HashMap<>();
        HashMap<Integer, List<Integer>> reverseCityRoutes = new HashMap<>();
        for(Route r: routes) {
        	if (forwardCityRoutes.containsKey(r.from)) {
        		forwardCityRoutes.get(r.from).add(r.to);
        	} else {
        		forwardCityRoutes.put(r.from, new ArrayList<>(r.to));
        	}
        	
        	if (reverseCityRoutes.containsKey(r.to)) {
        		reverseCityRoutes.get(r.to).add(r.from);
        	} else {
        		reverseCityRoutes.put(r.to, new ArrayList<>(r.from));
        	}
        }
        
        int initialSrc = routes.stream().findFirst().get().from;
        
        List<Collection<Integer>> citiesByState = new ArrayList<>();
        HashSet<Integer> visited = new HashSet<>();
        for(Entry<Integer, List<Integer>> e: forwardCityRoutes.entrySet()) {
        	visit(forwardCityRoutes, visited, initialSrc, e.getKey());
        	citiesByState.add(visited);
        	visited.clear();
        }
        
        System.out.println(citiesByState);
        
//        HashMap<Integer, List<Integer>> citiesByState = new HashMap();
//        int start = 0;
//        int end = 0;
//        int state = 0;
//        for(Route route: routes) {
//        	start = route.from;
//        	if (end != start) {
//        		break;
//        	}
//            route = routes.stream().filter(x -> x.from == route.to).findFirst().orElse(null);
//            if (route == null) break;
//            if (citiesByState.containsKey(route)) {
//            	citiesByState.put(state, Arrays.asList(route.from));
//            } else {
//            	citiesByState.get(state).add(route.from);
//            }
//            int to = route.to;
//        }

    }
    
    private static void visit(HashMap<Integer, List<Integer>> forwardCityRoutes, Set<Integer> visited, Integer initialSrc, Integer src) {
    	visited.add(src);
    	List<Integer> destinations = forwardCityRoutes.get(src);
    	if (destinations != null) {
    		for (Integer dest: destinations) {
    			if (!initialSrc.equals(dest)) {
    				visit(forwardCityRoutes, visited, initialSrc, dest);
    			}
    		}
    	}
    }
    
    private static List<Route> getRoutes() {
        Scanner sc = new Scanner(System.in);
        List<Route> routes = new ArrayList();
    	Integer numberOfCities = sc.nextInt();
        Integer numberOfRoutes = sc.nextInt();
        for(int i=0; i<numberOfRoutes; i++) {
            routes.add(new Route(sc.nextInt(), sc.nextInt()));
        }
        return routes;
    }
    
    private static class Route {
        int from;
        int to;
        
        public Route(int from, int to){
            this.from = from;
            this.to = to;
        }
        
        public String toString() {
            return String.format("{%d -> %d}", from, to);
        }
    }
}
