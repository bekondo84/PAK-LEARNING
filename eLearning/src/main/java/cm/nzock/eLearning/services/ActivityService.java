package cm.nzock.eLearning.services;

import cm.platform.basecommerce.exceptions.BusinessProcessException;

public interface ActivityService {

    void startActivity(String code, String username) throws BusinessProcessException;

    long nbreOfActivitiesComplete(Long involve, Long training);
}
