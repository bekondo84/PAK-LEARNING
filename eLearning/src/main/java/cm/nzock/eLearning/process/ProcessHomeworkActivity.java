package cm.nzock.eLearning.process;

import cm.nzock.eLearning.dao.ParticipantDao;
import cm.nzock.eLearning.services.HomeworkService;
import cm.platform.basecommerce.core.enums.TestResultState;
import cm.platform.basecommerce.core.process.Action;
import cm.platform.basecommerce.exceptions.BusinessProcessException;
import cm.platform.basecommerce.services.EnumerationService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.basecommerce.tools.persistence.RestrictionsContainer;
import cm.platform.myleaninig.core.HomeWorkResultModel;
import cm.platform.myleaninig.core.HomeworkActivityModel;
import cm.platform.myleaninig.core.ParticipantModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Component
public class ProcessHomeworkActivity implements Action {
   private static final Logger LOG = LoggerFactory.getLogger(ProcessHomeworkActivity.class);

    @Autowired
    private ParticipantDao userDao ;
    @Autowired
    private ModelService modelService;
    @Autowired
    private HomeworkService homeworkService;
    @Autowired
    private EnumerationService enumService ;

    @Override
    public String execute(Map<String, Object> context) throws BusinessProcessException {
        final HomeworkActivityModel activity = (HomeworkActivityModel) context.get("activity");
        final String username = (String) context.get("username");
        ParticipantModel participant = userDao.findByUsername(username).orElse(null);

        if (Objects.isNull(activity) || Objects.isNull(participant)) {
            return "ERROR";
        }

        if (Objects.isNull(homeworkService.getHomework(activity.getCode(), username))) {
            createHomeworkResult(activity, participant);
        }
        return "OK";
    }

    private void createHomeworkResult(HomeworkActivityModel activity, ParticipantModel participant) throws BusinessProcessException {
        final HomeWorkResultModel homeWork = new HomeWorkResultModel();
        homeWork.setActivity(activity);
        homeWork.setConcern(participant);
        homeWork.setMark(0.0f);
        homeWork.setPercent(activity.getPercent());
        homeWork.setTraining(activity.getTraining());
        homeWork.setBeginAt(new Date());
        homeWork.setState(enumService.getEnumerationValue(TestResultState.INPROGRESS.getCode(), TestResultState.class));
        try {
            modelService.save(homeWork);
        } catch (ModelServiceException e) {
            throw new BusinessProcessException(e);
        }
    }
}
