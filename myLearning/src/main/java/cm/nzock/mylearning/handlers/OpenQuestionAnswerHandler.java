package cm.nzock.mylearning.handlers;

import cm.platform.basecommerce.core.handlers.AttributHander;
import cm.platform.myleaninig.core.OpenQuestionAnswerModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component("openQuestionAnswerHandler")
public class OpenQuestionAnswerHandler implements AttributHander<OpenQuestionAnswerModel> {
    private static final Logger LOG = LoggerFactory.getLogger(OpenQuestionAnswerHandler.class);

    @Override
    public void handler(OpenQuestionAnswerModel item, String property) {
           item.setText(item.getQuestion().getText(Locale.getDefault()));
           LOG.info("INSIDE ---- OpenQuestionAnswerHandler ------ : "+item.getText());
    }
}
