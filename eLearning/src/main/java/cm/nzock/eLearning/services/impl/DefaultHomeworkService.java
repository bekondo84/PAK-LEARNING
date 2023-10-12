package cm.nzock.eLearning.services.impl;

import cm.nzock.eLearning.services.HomeworkService;
import cm.platform.basecommerce.core.enums.TestResultState;
import cm.platform.basecommerce.services.EnumerationService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.basecommerce.tools.persistence.RestrictionsContainer;
import cm.platform.myleaninig.core.HomeWorkResultModel;
import cm.platform.myleaninig.core.HomeworkActivityModel;
import cm.platform.myleaninig.core.TestResultModel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DefaultHomeworkService implements HomeworkService {

    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private EnumerationService enumService ;
    @Autowired
    private ModelService modelService;

    @Override
    public HomeWorkResultModel getHomework(String code, final String username) {
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("activity.code", code);
        container.addEq("concern.code", username);
        List resultat = flexibleSearchService.doSearch(HomeWorkResultModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);

        return CollectionUtils.isEmpty(resultat) ? null : (HomeWorkResultModel) resultat.get(0);
    }

    @Override
    public HomeWorkResultModel markAsDone(final HomeWorkResultModel homework) throws ModelServiceException {
        homework.setState(enumService.getEnumerationValue(TestResultState.DONE.getCode(), TestResultState.class));
        homework.setEndAt(new Date());
        modelService.save(homework);
        return homework;
    }
}
