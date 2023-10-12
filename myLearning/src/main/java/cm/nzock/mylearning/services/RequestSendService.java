package cm.nzock.mylearning.services;

import java.util.Map;

public interface RequestSendService {
    long countRequestToProcess(Map context) throws ClassNotFoundException;
}
