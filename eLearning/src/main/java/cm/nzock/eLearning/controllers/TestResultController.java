package cm.nzock.eLearning.controllers;

import cm.nzock.eLearning.services.AnswerService;
import cm.nzock.eLearning.services.TestResultService;
import cm.platform.basecommerce.core.utils.IsisConstants;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping(IsisConstants.ApiScope.API+"/test-result")
public class TestResultController {
    public static final Logger LOG = LoggerFactory.getLogger(TestResultController.class);

    @Autowired
    private TestResultService resultService;
    @Autowired
    private AnswerService answerService;

    @GetMapping("/{activity}")
    public TestResultModel getResult(@PathVariable("activity")String activity, Principal principal) {
         return  resultService.getTestResultFor(activity, principal.getName());
    }

    @PutMapping("/evaluate/trueorfalse/{answer}/{questionId}")
    public TrueOrFalseAnswerModel evaluateTrueOrFalse(final @PathVariable("answer")String answer, @PathVariable("questionId") Long questionId) throws ModelServiceException {
        return answerService.evaluateTrueOrFalseQuestion(questionId, answer) ;
    }

    @PutMapping("/evaluate/multi/{answer}/{questionId}")
    public MultiAnswerModel evaluateMulti(final @PathVariable("answer")String answer, @PathVariable("questionId") Long questionId) throws ModelServiceException {
       return answerService.evaluateMultiAnswerQuestion(questionId, answer) ;
    }

    @PutMapping("/evaluate/open")
    public OpenQuestionAnswerModel evaluateOpen(@RequestBody Map<String, String> context) throws ModelServiceException {
        return answerService.evaluateOpenQuestion(context.get("pk"), context.get("answer"));
    }

    @PutMapping("/mark-as-done/{test}")
    public TestResultModel closeEvaluation(@PathVariable("test")Long test) throws ModelServiceException {
         return  resultService.markAsDone(test);
    }
}
