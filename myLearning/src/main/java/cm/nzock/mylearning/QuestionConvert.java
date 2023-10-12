package cm.nzock.mylearning;

import cm.platform.basecommerce.populator.Converter;
import cm.platform.myleaninig.core.AbstractQuestionModel;
import org.springframework.stereotype.Component;

@Component
public class QuestionConvert implements Converter<Object, AbstractQuestionModel> {

    @Override
    public AbstractQuestionModel convert(final Object source) {
        final AbstractQuestionModel question = (AbstractQuestionModel) source;
        final AbstractQuestionModel output = new AbstractQuestionModel();
        output.setPK(question.getPK());
        output.setCode(question.getCode());
        output.setGeneralFeeback(question.getGeneralFeeback());
        output.setMark(question.getMark());
        output.setName(question.getName());
        output.setPenality(question.getPenality());
        output.setReady(question.getReady());
        output.setText(question.getText());
        output.setTheme(question.getTheme());
        output.setDateCreation(question.getDateCreation());
        output.setDateModification(question.getDateModification());
        return output;
    }
}
