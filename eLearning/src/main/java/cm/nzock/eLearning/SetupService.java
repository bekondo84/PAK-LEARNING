package cm.nzock.eLearning;

import cm.platform.basecommerce.core.setup.IsisSetupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SetupService implements IsisSetupService {
    private static final Logger LOG = LoggerFactory.getLogger(SetupService.class);

    @Override
    public void onStartUp() {

    }

    @Override
    public void setup() {
        LOG.info("CALLING SETUP SERVICE FOR HAC");
    }
}