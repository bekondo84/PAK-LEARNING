package cm.nzock.eLearning.builders;

import cm.nzock.eLearning.beans.HomePageComponentData;
import cm.nzock.eLearning.beans.MenuItemData;
import cm.nzock.eLearning.dao.ParticipantDao;
import cm.nzock.eLearning.services.TrainingService;
import cm.platform.basecommerce.services.I18NService;
import cm.platform.myleaninig.core.ParticipantModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.Objects;

@Component
public class HomepageComponentBuilder {

    @Autowired
    private ParticipantDao userDao;
    @Autowired
    private I18NService i18NService;
    @Autowired
    private TrainingService trainingService;

    public HomePageComponentData build(final String username) {
        HomePageComponentData component = new HomePageComponentData();
        ParticipantModel user = userDao.findByUsername(username).orElse(null);
        if (Objects.isNull(user)) {
            throw new EntityNotFoundException(String.format("No user found with username : %", username));
        }
        //Menu myLearning
        MenuItemData menuItem = new MenuItemData();
        menuItem.setCode("my-learning")
                .setText(i18NService.getLabel("my.learning" ,"my.learning"))
                .setSequence(1)
                .setTotal(Long.valueOf(trainingService.countTrainingToRegisterForUsername(username)).intValue())
                .setBadge("error");
        component.addMenuItem(menuItem);
        menuItem = new MenuItemData();
        menuItem.setCode("my-learning-inprogress")
                .setText(i18NService.getLabel("my.learning.inprogress" ,"my.learning.inprogress"))
                .setSequence(2)
                .setTotal(Long.valueOf(trainingService.countRegisterTraining(username)).intValue())
                .setBadge("secondary");
        component.addMenuItem(menuItem);
        menuItem = new MenuItemData();
        menuItem.setCode("my-learning-done")
                .setText(i18NService.getLabel("my.learning.done" ,"my.learning.done"))
                .setSequence(3)
                .setTotal(Long.valueOf(trainingService.countDoneTraining(username)).intValue())
                .setBadge("primary");
        component.addMenuItem(menuItem);
        menuItem = new MenuItemData();
        menuItem.setCode("my-learning-online")
                .setText(i18NService.getLabel("my.learning.online" ,"my.learning.online"))
                .setSequence(4)
                .setTotal(Long.valueOf(trainingService.countOnlineTraining(username)).intValue())
                .setBadge("warning");
        component.addMenuItem(menuItem);

       return component;
    }
}
