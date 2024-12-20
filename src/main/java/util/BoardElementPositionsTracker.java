package util;

import model.BoardElement;

import java.util.*;

public class BoardElementPositionsTracker implements Tracker<BoardElement,Position> {

    private final Map<Class<? extends BoardElement>, List<Position>> tracker;

    public BoardElementPositionsTracker() {
        this.tracker = new HashMap<>();
    }

    @Override
    public <T extends BoardElement> void track(T element) {
        Class<? extends BoardElement> elementType = element.getClass();
        getTracker()
                .computeIfAbsent(elementType, k -> new ArrayList<>())
                .add(element.position());
    }

    @Override
    public <T extends BoardElement> void untrack(T element) {
        if (element == null)
            return;
        Class<? extends BoardElement> elementType = element.getClass();
        List<Position> positions = getTracker().get(elementType);
        if (positions != null) {
            positions.remove(element.position());
        }
    }

    @Override
    public <T extends BoardElement> List<Position> getValues(Class<T> elementType) {
        return getTracker().getOrDefault(elementType, Collections.emptyList());
    }

    @Override
    public Map<Class<? extends BoardElement>, List<Position>> getTracker() {
        return tracker;
    }
}
