package cm.nzock.eLearning.controllers;

import cm.nzock.eLearning.services.HomeworkService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.HomeWorkResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static cm.platform.basecommerce.core.utils.IsisConstants.ApiScope;

@RestController
@RequestMapping(ApiScope.API+"/homework-result")
public class HomeworkController {

    @Autowired
    private HomeworkService homeworkService;

    @GetMapping("/{activity}")
    public HomeWorkResultModel getHomework(@PathVariable("activity")final String code, Principal principal) {
          return  homeworkService.getHomework(code, principal.getName());
    }

    @PostMapping("/mark-as-done")
    public HomeWorkResultModel closeEvaluation(@RequestBody final HomeWorkResultModel homework) throws ModelServiceException {
        return  homeworkService.markAsDone(homework);
    }
}
