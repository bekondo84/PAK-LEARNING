package cm.nzock.eLearning.process;

import cm.nzock.eLearning.dao.ParticipantDao;
import cm.nzock.eLearning.populators.LeconActivityToResultPopulator;
import cm.platform.basecommerce.core.enums.TestResultState;
import cm.platform.basecommerce.core.process.Action;
import cm.platform.basecommerce.exceptions.BusinessProcessException;
import cm.platform.basecommerce.services.EnumerationService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.basecommerce.tools.persistence.RestrictionsContainer;
import cm.platform.myleaninig.core.*;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProcessLeconActivity implements Action {
    private static final Logger LOG = LoggerFactory.getLogger(ProcessLeconActivity.class);

    @Autowired
    private ParticipantDao userDao ;
    @Autowired
    private EnumerationService enumService ;
    @Autowired
    private LeconActivityToResultPopulator populator;
    @Autowired
    private ModelService modelService ;
    @Autowired
    private FlexibleSearchService flexibleSearchService;

    @Override
    public String execute(Map<String, Object> context) throws BusinessProcessException {
        try {
            final LeconActivityModel leconActivity = (LeconActivityModel) context.get("activity");
            final String username = (String) context.get("username");
            ParticipantModel participant = userDao.findByUsername(username).orElse(null);
            RestrictionsContainer container = RestrictionsContainer.newInstance();
            container.addEq("lecon.code", leconActivity.getCode());
            List summaries = flexibleSearchService.doSearch(SummaryEntryModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);
            if (Objects.isNull(leconActivity) || Objects.isNull(participant)
                    || CollectionUtils.isEmpty(summaries)
                    || CollectionUtils.isEmpty(leconActivity.getTraining().getWorkgroups())) {
                return "ERROR";
            }
            summaries.sort(new Comparator() {
                @Override
                public int compare(Object o1, Object o2) {
                    return ((SummaryEntryModel)o1).getSequence().compareTo(((SummaryEntryModel)o2).getSequence());
                }
            });
            LeconResultModel leconResult = getLeconResult(leconActivity, username);
            populator.populate(leconActivity, leconResult);
            leconResult.setConcern(participant);
            leconResult.setTraining(leconActivity.getTraining());
            if (Objects.isNull(leconResult.getCurrentIndex())) {
                leconResult.setCurrentIndex((SummaryEntryModel) summaries.get(0));
            }
            if (Objects.isNull(leconResult.getState())) {
                leconResult.setState(enumService.getEnumerationValue(TestResultState.INPROGRESS.getCode(), TestResultState.class));
            }
            modelService.save(leconResult);
            //Create Involve entry
            createInvolve(leconActivity, username, participant);
        } catch (ModelServiceException e) {
            throw new BusinessProcessException(e);
        }
        return "OK";
    }

    private void createInvolve(LeconActivityModel leconActivity, String username, ParticipantModel participant) throws ModelServiceException {
        InvolveModel involve = getInvolve(leconActivity, username);
        if (Objects.isNull(involve)) {
            involve = new InvolveModel();
            involve.setRegisterDate(new Date());
            involve.setRegister(Boolean.TRUE);
            involve.setConcern(participant);
            if (CollectionUtils.isNotEmpty(leconActivity.getTraining().getWorkgroups())) {
              involve.setWorkgroup(leconActivity.getTraining().getWorkgroups().get(0));
            }
            modelService.save(involve);
        }
    }

    private InvolveModel getInvolve(LeconActivityModel leconActivity, String username) {
        RestrictionsContainer container1 = RestrictionsContainer.newInstance();
        container1.addEq("concern.code", username);
        container1.addEq("training.code", leconActivity.getTraining().getCode());
        List involves = flexibleSearchService.doSearch(InvolveModel.class, container1, new HashMap<>(), new HashSet<>(), 0, -1);
        InvolveModel involve = CollectionUtils.isEmpty(involves) ? null : (InvolveModel) involves.get(0);
        return involve;
    }

    private LeconResultModel getLeconResult(LeconActivityModel leconActivity, String username) {
        LeconResultModel leconResult = new LeconResultModel();
        RestrictionsContainer container = RestrictionsContainer.newInstance();
        container.addEq("activity.code", leconActivity.getCode());
        container.addEq("concern.code", username);
        List lecons = flexibleSearchService.doSearch(LeconResultModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);
        if (CollectionUtils.isNotEmpty(lecons)) {
            leconResult = (LeconResultModel) lecons.get(0);
        }
        return leconResult;
    }
}
