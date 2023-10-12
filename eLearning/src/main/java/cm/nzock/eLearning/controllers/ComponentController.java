package cm.nzock.eLearning.controllers;

import cm.nzock.eLearning.beans.LoginComponentData;
import cm.nzock.eLearning.converters.LoginComponentConverter;
import cm.platform.basecommerce.core.utils.IsisConstants;
import cm.platform.cms.services.CMSPageTemplateService;
import cm.platform.myleaninig.cms.LoginComponentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(IsisConstants.ApiScope.PUBLIC+"/cms")
public class ComponentController {

    @Autowired
    private CMSPageTemplateService cmsService;
    @Autowired
    private LoginComponentConverter converter ;

    @GetMapping("/login/{name}")
    public LoginComponentData loginComponent(final @PathVariable("name") String name) {
         LoginComponentModel component = cmsService.getComponent(LoginComponentModel.class, name);
         return  converter.convert(component);
    }
}
