package problems;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonkAndHisUniqueTrip2 {

	public static void main(String args[] ) throws Exception {
		Map<Integer, Collection<Integer>> cityToCities = findCityToCities();
		Map<Integer, Collection<Integer>> citiesPerState = findCitiesPerState(cityToCities);
		Map<Integer, Collection<Integer>> stateToStates = findStateToStates(cityToCities, citiesPerState);
		Collection<List<Integer>> interStateRoutes = findInterStateRoutes(stateToStates);
		List<Integer> mostExpensiveRoute = interStateRoutes.stream().max((x, y) -> Integer.compare(x.size(), y.size())).get();
		int minimumAmountRequired = mostExpensiveRoute.size() > 0 ? mostExpensiveRoute.size() - 1 : 0;

		System.out.println(cityToCities);
		System.out.println(citiesPerState);
		System.out.println(stateToStates);
		System.out.println(interStateRoutes);
		System.out.println(mostExpensiveRoute);
		System.out.println(minimumAmountRequired);
	}

	private static Collection<List<Integer>> findInterStateRoutes(Map<Integer, Collection<Integer>> stateToStates) {
		Collection<List<Integer>> routes = new ArrayList<>();
		List<Integer> visited = new ArrayList<>();
		int lastCaputureIdx = 0;

		for(Integer src: stateToStates.keySet()) {
			executeDfs(stateToStates, src, visited);
			if (lastCaputureIdx < visited.size()) {
				List<Integer> newVisited = new ArrayList<>(visited.subList(lastCaputureIdx, visited.size()));
				routes.add(newVisited);
			}
			lastCaputureIdx = visited.size();
		}

		return routes;
	}

	private static Map<Integer, Collection<Integer>> findStateToStates(Map<Integer, Collection<Integer>> cityToCities,
			Map<Integer, Collection<Integer>> citiesPerState) {

		Map<Integer, Integer> cityToState = new HashMap<>();
		citiesPerState.entrySet().stream().forEach(e -> {
			e.getValue().stream().forEach(city -> {
				cityToState.put(city, e.getKey());
			});
		});

		Map<Integer, Collection<Integer>> stateToStates = new HashMap<>();

		for(Integer state: citiesPerState.keySet()) {
			Collection<Integer> currStatecities = citiesPerState.get(state);
			Set<Integer> linkedStates = new HashSet<>();

			currStatecities.stream().forEach(c -> {
				Collection<Integer> nextCities = Optional.ofNullable(cityToCities.get(c)).orElse(Collections.emptyList());
				nextCities.forEach(nCity -> {
					linkedStates.add(cityToState.get(nCity));
				});
			});
			linkedStates.remove(state);

			stateToStates.put(state, linkedStates);
		}

		return stateToStates;
	}

	private static Map<Integer, Collection<Integer>> findCitiesPerState(Map<Integer, Collection<Integer>> cityToCities) {
		Set<Integer> cities = new HashSet<>();
		cityToCities.entrySet().stream().forEach(e -> {
			cities.add(e.getKey());
			cities.addAll(e.getValue());
		});

		Collection<Integer> visited = new HashSet<>();
		LinkedList<Integer> stack = new LinkedList<>();

		for (Integer src: cities) {
			executeDfs(cityToCities, src, visited, stack);
		}

		Map<Integer, Collection<Integer>> cityToCitiesReversed = reverseGraph(cityToCities);

		Map<Integer, Collection<Integer>> citiesPerState = new HashMap<>();
		visited = new ArrayList<>();
		int stateId = 1;
		int lastCapturedIdx = 0;

		for (Integer src: stack) {
			executeDfs(cityToCitiesReversed, src, visited);

			if (lastCapturedIdx < visited.size()) {
				List<Integer> sameStateCities = new ArrayList<>(((List<Integer>) visited).subList(lastCapturedIdx, visited.size())); 
				citiesPerState.put(stateId++, sameStateCities);
				lastCapturedIdx = visited.size();
			}
		}

		return citiesPerState;
	}

	private static void executeDfs(Map<Integer, Collection<Integer>> map, Integer src, Collection<Integer> visited, LinkedList<Integer> stack) {
		if (!visited.contains(src)) {
			visited.add(src);
			for (Integer dest: Optional.ofNullable(map.get(src)).orElse(Collections.emptyList())) {
				executeDfs(map, dest, visited, stack);
			}
			stack.push(src);
		}
	}

	private static void executeDfs(Map<Integer, Collection<Integer>> map, Integer src, Collection<Integer> visited) {
		if (!visited.contains(src)) {
			visited.add(src);
			for (Integer dest: Optional.ofNullable(map.get(src)).orElse(Collections.emptyList())) {
				executeDfs(map, dest, visited);
			}
		}
	}

	private static Map<Integer, Collection<Integer>> reverseGraph(Map<Integer, Collection<Integer>> map) {
		Map<Integer, Collection<Integer>> reversed = new HashMap<>();
		for(Entry<Integer, Collection<Integer>> entry: map.entrySet()) {
			Collection<Integer> values = entry.getValue();
			for (Integer val: values) {
				Collection<Integer> candidate = Optional.ofNullable(reversed.get(val)).orElse(new ArrayList<>());
				candidate.add(entry.getKey());
				reversed.put(val, candidate);
			}
		}
		return reversed;
	}

	@SuppressWarnings("unused")
	private static Map<Integer, Collection<Integer>> getRoutes() {
		try(Scanner sc = new Scanner(System.in)) {
			Integer numberOfCities = sc.nextInt();
			Integer numberOfRoutes = sc.nextInt();

			Map<Integer, Collection<Integer>> cityToCities = new HashMap<>();

			for(int i=0; i<numberOfRoutes; i++) {

				int from = sc.nextInt();
				int to = sc.nextInt();

				Collection<Integer> destinations = Optional.ofNullable(cityToCities.get(from))
						.orElse(new ArrayList<>());
				destinations.add(to);

				cityToCities.put(from, destinations);
			}

			return cityToCities;
		}
	}
	
	private static Map<Integer, Collection<Integer>> findCityToCities() throws IOException {
		Map<Integer, Collection<Integer>> cityToCities = new HashMap<>();
		Pattern p = Pattern.compile("(\\d+)\\s(\\d+)");
		
		try(var lines = Files.lines(Paths.get("src/test/resources/MonkAndHisUniqueTrip.in"))) {
			lines.skip(1).forEach(line -> {
				Matcher m = p.matcher(line);
				if (m.find()) {
					int from = Integer.parseInt(m.group(1));
					int to = Integer.parseInt(m.group(2));
					
					Collection<Integer> destinations = Optional.ofNullable(cityToCities.get(from))
							.orElse(new ArrayList<>());
					destinations.add(to);

					cityToCities.put(from, destinations);
				}
			});
		};
		return cityToCities;
	}

}
