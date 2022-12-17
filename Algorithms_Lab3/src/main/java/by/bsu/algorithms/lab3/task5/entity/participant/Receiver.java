package by.bsu.algorithms.lab3.task5.entity.participant;

import by.bsu.algorithms.lab3.task5.entity.RequesterStatus;
import by.bsu.algorithms.lab3.task5.entity.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public final class Receiver extends Vertex implements Participant {
    private final List<Requester> requesters;
    private final Map<Integer, Integer> priorityMap;

    public Receiver(int index) {
        super(index);
        requesters = new ArrayList<>();
        priorityMap = new HashMap<>();
    }

    public void setRequestersPriority(List<Requester> requesters, int...indexes) {
        for (int i = 0; i < requesters.size(); i++) {
            priorityMap.put(requesters.get(i).getIndex(), indexes[i]);
        }
    }

    public Optional<Requester> getChoice() {
        return requesters.size() == 1 ? Optional.of(requesters.get(0)) : Optional.empty();
    }

    @Override
    public void makeMove() {
        if (requesters.isEmpty()) {
            return;
        }
        int highestPriority = -1;
        Requester requesterWithHighestPriority = null;
        for (Requester requester : requesters) {
            int priority = priorityMap.get(requester.getIndex());
            if (priority > highestPriority) {
                highestPriority = priority;
                requesterWithHighestPriority = requester;
            }
        }
        var iterator = requesters.iterator();
        while (iterator.hasNext()) {
            Requester requester = iterator.next();
            if (requester == null) {
                iterator.remove();
            } else {
                if (requester != requesterWithHighestPriority) {
                    requester.setStatus(RequesterStatus.FREE);
                    iterator.remove();
                } else {
                    requester.setStatus(RequesterStatus.BUSY);
                }
            }
        }
    }

    public void request(Requester requester) {
        requesters.add(requester);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Receiver receiver = (Receiver) o;

        if (!requesters.equals(receiver.requesters)) return false;
        return priorityMap.equals(receiver.priorityMap);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + requesters.hashCode();
        result = 31 * result + priorityMap.hashCode();
        return result;
    }
}