package cm.nzock;

import cm.platform.basecommerce.core.annotations.RemoteMethod;
import cm.platform.basecommerce.core.annotations.RemoteService;
import cm.platform.basecommerce.core.setup.IsisSetupService;
import cm.platform.basecommerce.exceptions.ImpexParseException;
import cm.platform.basecommerce.services.ImpexImportService;
import cm.platform.basecommerce.services.MetaTypeCvsService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

@RemoteService("setupService")
public class SetupService implements IsisSetupService {
    private static final Logger LOG = LoggerFactory.getLogger(SetupService.class);

    @Autowired
    private MetaTypeCvsService metaTypeCvsService;
    @Autowired
    private ImpexImportService impexImportService;

    @Override
    public void onStartUp() throws URISyntaxException, ModelServiceException, ImpexParseException, IOException, NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        final URL path = getClass().getClassLoader().getResource("import"+ File.separator+"init-data");
        File initFile = new File(path.toURI());
        for(File file : initFile.listFiles()){
            if(file.getName().contains("-metatype")){//It is generate inti file
                metaTypeCvsService.initializedModule(file);
            }
        }
    }

    @RemoteMethod(name = "setup")
    @Override
    public void setup() throws ImpexParseException {
        impexImportService.importImpexFromResources("import/init-data/printers-data.csv");
    }
}