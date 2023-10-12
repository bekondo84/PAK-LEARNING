package cm.nzock.eLearning.controllers;

import cm.nzock.eLearning.services.LeconResultService;
import cm.platform.basecommerce.core.utils.IsisConstants;
import cm.platform.basecommerce.exceptions.BusinessProcessException;
import cm.platform.myleaninig.core.LeconEvaluationModel;
import cm.platform.myleaninig.core.LeconResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping(IsisConstants.ApiScope.API+"/lecon-result")
public class LeconResultController {

    @Autowired
    private LeconResultService leconResultService;

    @GetMapping("/{activity}")
    public LeconResultModel startLecon(@PathVariable("activity") final String activity, final Principal principal) {
          return  leconResultService.getLeconResultFor(activity, principal.getName());
    }

    @PutMapping("/close/{pk}")
    public LeconResultModel closeActivity(@PathVariable("pk")Long pk) throws BusinessProcessException {
       return leconResultService.closeActivity(pk);
    }

    @PutMapping("/next-step/{pk}")
    public LeconResultModel nextStep(@PathVariable("pk")Long pk) throws BusinessProcessException {
        return  leconResultService.nextStep(pk);
    }

    @PutMapping("/previous-step/{pk}")
    public LeconResultModel previousStep(@PathVariable("pk")Long pk) throws BusinessProcessException {
        return  leconResultService.previousStep(pk);
    }

    @GetMapping("/evaluation/{question}/{result}")
    public LeconEvaluationModel getEvaluations(@PathVariable("question")Long question, @PathVariable("result")Long result) throws BusinessProcessException {
          return  leconResultService.getEvaluation(question, result);
    }

    @GetMapping("/evaluation-mark/{question}/{result}")
    public Float getEvalationMark(@PathVariable("question")Long question, @PathVariable("result")Long result) throws BusinessProcessException {
        Optional<LeconEvaluationModel> evaluation = Optional.ofNullable(leconResultService.getEvaluation(question, result));
        return evaluation.isPresent() ? evaluation.get().getMark() : 0.0f;
    }

    @PutMapping("/submit-evaluation/{pk}")
    public void submitEvaluation(@PathVariable("pk") Long pk) throws BusinessProcessException {
        leconResultService.submitEvaluation(pk);
    }
}
