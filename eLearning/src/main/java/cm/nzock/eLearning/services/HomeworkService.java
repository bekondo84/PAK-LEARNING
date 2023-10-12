package cm.nzock.eLearning.services;

import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.HomeWorkResultModel;

public interface HomeworkService {

    HomeWorkResultModel getHomework(final String code, final String username) ;
    HomeWorkResultModel markAsDone(final HomeWorkResultModel homework) throws ModelServiceException;
}
