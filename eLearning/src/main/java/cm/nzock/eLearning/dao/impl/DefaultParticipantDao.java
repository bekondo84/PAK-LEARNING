package cm.nzock.eLearning.dao.impl;

import cm.nzock.eLearning.dao.ParticipantDao;
import cm.platform.basecommerce.core.security.EmployeeModel;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.tools.persistence.RestrictionsContainer;
import cm.platform.myleaninig.core.ParticipantModel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class DefaultParticipantDao implements ParticipantDao {

    @Autowired
    private FlexibleSearchService flexibleSearchService;

    @Override
    public Optional<ParticipantModel> findByUsername(String username) {
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("code", username);
        List results =  flexibleSearchService.doSearch(ParticipantModel.class, container ,new HashMap<>(), new HashSet<>(), 0, -1);

        return CollectionUtils
                .emptyIfNull(results)
                .stream()
                .findAny();
    }
}
