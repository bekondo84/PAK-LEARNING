package cm.nzock.eLearning.process;

import cm.nzock.eLearning.dao.ParticipantDao;
import cm.nzock.eLearning.populators.TestActivityToResultPopulator;
import cm.platform.basecommerce.core.enums.TestResultState;
import cm.platform.basecommerce.core.process.Action;
import cm.platform.basecommerce.exceptions.BusinessProcessException;
import cm.platform.basecommerce.services.EnumerationService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.ParticipantModel;
import cm.platform.myleaninig.core.TestActivityModel;
import cm.platform.myleaninig.core.TestResultModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class ProcessTestActivity implements Action {

    public static final Logger LOG = LoggerFactory.getLogger(ProcessTestActivity.class);

    @Autowired
    private TestActivityToResultPopulator populator ;
    @Autowired
    private ParticipantDao userDao ;
    @Autowired
    private EnumerationService enumService ;

    @Override
    public String execute(Map<String, Object> context) throws BusinessProcessException {
        final TestActivityModel testActivity = (TestActivityModel) context.get("activity");
        final String username = (String) context.get("username");
        ParticipantModel participant = userDao.findByUsername(username).orElse(null);

        if (Objects.isNull(testActivity) || Objects.isNull(participant)) {
            return "ERROR";
        }
        TestResultModel testResult = new TestResultModel();
        testResult.setConcern(participant);
        testResult.setTraining(testActivity.getTraining());
        testResult.setState(enumService.getEnumerationValue(TestResultState.INPROGRESS.getCode(), TestResultState.class));
        populator.populate(testActivity, testResult);
        //LOG.info("ProcessTestActivity Done successfully ---");
        return "OK";
    }
}
