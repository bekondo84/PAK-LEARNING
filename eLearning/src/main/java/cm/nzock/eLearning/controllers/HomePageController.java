package cm.nzock.eLearning.controllers;

import cm.nzock.eLearning.beans.*;
import cm.nzock.eLearning.builders.HomepageComponentBuilder;
import cm.nzock.eLearning.converters.UserDataToParticipantConverter;
import cm.nzock.eLearning.dao.ParticipantDao;
import cm.nzock.eLearning.facades.TrainingFacade;
import cm.nzock.eLearning.services.ActivityService;
import cm.platform.basecommerce.core.utils.IsisConstants;
import cm.platform.basecommerce.exceptions.BusinessProcessException;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.LeconContentModel;
import cm.platform.myleaninig.core.ParticipantModel;
import cm.platform.myleaninig.core.RequestSendModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@RestController
@RequestMapping(IsisConstants.ApiScope.API)
public class HomePageController {
   private static final Logger LOG = LoggerFactory.getLogger(HomePageController.class);

    @Autowired
    private HomepageComponentBuilder builder ;
    @Autowired
    private TrainingFacade trainingFacade;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private ParticipantDao userDao;
    @Autowired
    private ModelService modelService;
    @Autowired
    private UserDataToParticipantConverter userDataToParticipantConverter;

    @GetMapping("/cms/home-cms")
    public HomePageComponentData menuCms(Principal principal) {
        final String username = principal.getName() ;
        return builder.build(username);
    }

    @GetMapping("/training/instance/{code}")
    public TrainingData getTrainingForCode(@PathVariable("code")String code) {
        return trainingFacade.getTraining(code);
    }
    @GetMapping("/training")
    public List<InvolveData> getTrainingToRegister(Principal principal) {
          return trainingFacade.getTrainingToRegisterForUsername(principal.getName());
    }

    @GetMapping("/training/register")
    public List<InvolveData> getRegisterTrainings(Principal principal) {
        return  trainingFacade.getRegisterTraining(principal.getName());
    }

    @GetMapping("/training/done")
    public List<InvolveData> getDoneTrainings(Principal principal) {
        return  trainingFacade.getDoneTrainings(principal.getName());
    }

    @GetMapping("/training/online")
    public Set<WorkgroupActivityData> getOnLineTrainings(final Principal principal) {
        return  trainingFacade.getOnlineTraining(principal.getName());
    }

    @PostMapping("/training/online/{groupe}")
    public InvolveData subscribeOnlineTraining(@PathVariable("groupe")String groupe, Principal principal) throws ModelServiceException {
        return trainingFacade.subscribeOnlineTraining(groupe, principal.getName());
    }

    @GetMapping("/training/workgroup/{training}")
    public List<WorkgroupActivityData> getWorkgroupForTraining(@PathVariable("training")String training) {
        return  trainingFacade.getWorkgroupForTraining(training);
    }

    @PostMapping("/training/register")
    public void register(@RequestBody final Map data) throws ModelServiceException {
        final Long involve = Long.valueOf(String.valueOf(data.get("involve")));
        final String group = (String) data.get("group");
        trainingFacade.registerForTraining(involve, group);
    }

    @PostMapping("/training/start")
    public void start(@RequestBody final Map data) throws ModelServiceException {
        final Long involve = Long.valueOf(String.valueOf(data.get("involve")));
        trainingFacade.startTraining(involve);
    }


    @PostMapping("/training/unregister")
    public void unregister(@RequestBody final Map data) throws ModelServiceException {
        final Long involve = Long.valueOf(String.valueOf(data.get("involve")));
        final String group = (String) data.get("group");
        trainingFacade.unregisterForTraining(involve, group);
    }

    @PostMapping("/activity/{code}")
    public void startActivity(@PathVariable("code")String code, Principal principal) throws BusinessProcessException {
        final String username = principal.getName();
        activityService.startActivity(code, username);
    }

    @GetMapping("/course/{activity}/{code}")
    public LeconContentModel getCourse(@PathVariable("activity")String activity,
                                       @PathVariable("code")String code, Principal principal) {
            return (LeconContentModel) flexibleSearchService.find(code, "id", LeconContentModel._TYPECODE).orElse(null);
    }


    @PutMapping("/reset-password/{password}")
    public void  resetPassword(@PathVariable("password")String password, Principal principal) throws ModelServiceException {
        final ParticipantModel user = userDao.findByUsername(principal.getName()).orElse(null);
        BCryptPasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder.encode(password));
        modelService.save(user);
    }

    @GetMapping("/password-matcher/{password}")
    public Boolean passwordMatch(@PathVariable("password")String password, Principal principal) {
        final ParticipantModel user = userDao.findByUsername(principal.getName()).orElse(null);
        BCryptPasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();
       return user.getPassword().equals(passwordEncoder.encode(password));
    }

    @PutMapping("/user-profil/update")
    public  void profilUpdate(@RequestBody UserData userData) throws ModelServiceException {
        final ParticipantModel participant = userDataToParticipantConverter.convert(userData);
        modelService.save(participant);
    }

    @PostMapping("/send-request")
    public void sendRequest(@RequestBody RequestSendModel request) throws ModelServiceException {
         assert Objects.nonNull(request) : "Request can't be null";
         modelService.save(request);
    }
}
