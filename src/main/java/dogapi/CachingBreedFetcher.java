package dogapi;

import java.util.*;

/**
 * This BreedFetcher caches fetch request results to improve performance and
 * lessen the load on the underlying data source. An implementation of BreedFetcher
 * must be provided. The number of calls to the underlying fetcher are recorded.
 *
 * If a call to getSubBreeds produces a BreedNotFoundException, then it is NOT cached
 * in this implementation. The provided tests check for this behaviour.
 *
 * The cache maps the name of a breed to its list of sub breed names.
 */
public class CachingBreedFetcher implements BreedFetcher {
    // TODO Task 2: Complete this class
    private int callsMade = 0;
    private Map<String, List<String>> cache;
    private BreedFetcher fetcher;
    public CachingBreedFetcher(BreedFetcher fetcher) {
        this.fetcher = fetcher;
        cache = new HashMap<>();
    }

    @Override
    public List<String> getSubBreeds(String breed) throws BreedNotFoundException {
        List<String> subBreeds;
        if (cache.containsKey(breed)) {
            subBreeds = cache.get(breed);
        } else {
            callsMade++;
            try {
                subBreeds = fetcher.getSubBreeds(breed);
            } catch (BreedNotFoundException e) {
                throw new BreedNotFoundException(breed);
            }
            cache.put(breed, subBreeds);
        }
        return subBreeds;
    }

    public int getCallsMade() {
        return callsMade;
    }
}