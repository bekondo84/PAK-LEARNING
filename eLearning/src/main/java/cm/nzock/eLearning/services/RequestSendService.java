package cm.nzock.eLearning.services;

import cm.nzock.eLearning.beans.RequestSendData;
import cm.platform.basecommerce.exceptions.BusinessProcessException;

public interface RequestSendService {
    void startRequestProcess(final RequestSendData request) throws BusinessProcessException;
}
