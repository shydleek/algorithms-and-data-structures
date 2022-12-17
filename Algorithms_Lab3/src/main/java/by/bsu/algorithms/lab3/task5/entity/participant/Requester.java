package by.bsu.algorithms.lab3.task5.entity.participant;

import by.bsu.algorithms.lab3.task5.entity.RequesterStatus;
import by.bsu.algorithms.lab3.task5.entity.Vertex;
import lombok.NonNull;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public final class Requester extends Vertex implements Participant {
    private final Queue<Receiver> wishQueue;
    private RequesterStatus status;

    public Requester(int index) {
        super(index);
        status = RequesterStatus.FREE;
        this.wishQueue = new LinkedList<>();
    }

    public RequesterStatus getStatus() {
        return status;
    }

    public void setStatus(@NonNull RequesterStatus status) {
        this.status = status;
    }

    public void setReceiversPriority(List<Receiver> receivers, int...indexes) {
        Arrays.stream(indexes).forEach(i -> wishQueue.add(receivers.get(i)));
    }

    public boolean isFinished() {
        return wishQueue.isEmpty();
    }

    @Override
    public void makeMove() {
        if (!wishQueue.isEmpty()) {
            if (status == RequesterStatus.BUSY) {
                return;
            }
            wishQueue.poll().request(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Requester requester = (Requester) o;

        if (!wishQueue.equals(requester.wishQueue)) return false;
        return status == requester.status;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + status.hashCode();
        for (Receiver receiver: wishQueue) {
            result = 31 * result + receiver.getIndex();
        }
        return result;
    }
}