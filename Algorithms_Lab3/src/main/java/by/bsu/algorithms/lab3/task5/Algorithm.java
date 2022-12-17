package by.bsu.algorithms.lab3.task5;

import by.bsu.algorithms.lab3.task5.entity.RequesterStatus;
import by.bsu.algorithms.lab3.task5.entity.participant.Participant;
import by.bsu.algorithms.lab3.task5.entity.participant.Receiver;
import by.bsu.algorithms.lab3.task5.entity.participant.Requester;

import java.util.List;

public class Algorithm {
    private List<Receiver> receivers;
    private List<Requester> requesters;

    public Algorithm(List<Participant> participants) {
        participants.forEach(participant -> {
            if (participant instanceof Requester requester) {
                requesters.add(requester);
            }
            else {
                receivers.add((Receiver) participant);
            }
        });
    }

    public Algorithm(List<Receiver> receivers, List<Requester> requesters) {
        this.receivers = receivers;
        this.requesters = requesters;
    }

    public void doAlgorithm() {
        do {
            requesters.forEach(Requester::makeMove);
            receivers.forEach(Receiver::makeMove);
        } while (!isComplete());
        printAlgorithmResults();
    }

    private boolean isComplete() {
        int busyCounter = 0;
        for (Requester requester : requesters) {
            if (requester.getStatus() == RequesterStatus.BUSY) {
                busyCounter++;
            }
            requester.isFinished();
        }
        return busyCounter == requesters.size()
                || busyCounter == receivers.size();
    }

    private void printAlgorithmResults() {
        receivers.forEach(receiver -> System.out.printf("requester(%s) - receiver(%s)\n", receiver.getChoice().orElse(null), receiver));
    }
}
