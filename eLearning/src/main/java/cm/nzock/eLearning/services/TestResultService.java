package cm.nzock.eLearning.services;

import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.TestResultModel;

public interface TestResultService {

    TestResultModel getTestResultFor(final String activity, final String username);

    /**
     * Mark the test with PK as done
     * @param pk
     * @return
     */
    TestResultModel markAsDone(final Long pk) throws ModelServiceException;
}
