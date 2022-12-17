package by.bsu.algorithms.lab3.task5;

import by.bsu.algorithms.lab3.task5.entity.participant.Receiver;
import by.bsu.algorithms.lab3.task5.entity.participant.Requester;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Requester> requesters = new ArrayList<>();
        List<Receiver> receivers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            requesters.add(new Requester(i));
        }
        for (int i = 0; i < 4; i++) {
            receivers.add(new Receiver(i));
        }
        int[][] receiversPriority = new int[][] {
                {0, 2, 1, 3},
                {3, 2, 1, 0},
                {2, 0, 1, 3},
                {1, 3, 0, 2}
        };
        int[][] requesterPriority = new int[][] {
                {0, 2, 1, 3},
                {0, 3, 2, 1},
                {1, 2, 0, 3},
                {3, 0, 2, 1}
        };
        for (int i = 0; i < requesters.size(); i++) {
            Requester requester = requesters.get(i);
            requester.setReceiversPriority(receivers, receiversPriority[i]);
        }

        for (int i = 0; i < receivers.size(); i++) {
            Receiver receiver = receivers.get(i);
            receiver.setRequestersPriority(requesters, requesterPriority[i]);
        }
        Algorithm algorithm = new Algorithm(receivers, requesters);
        algorithm.doAlgorithm();
    }
}