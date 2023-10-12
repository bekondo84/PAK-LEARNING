package cm.nzock.mylearning.actions;

import cm.platform.basecommerce.core.actions.AbstractAction;
import cm.platform.basecommerce.core.actions.annotations.Action;
import cm.platform.basecommerce.core.actions.annotations.ActionService;
import cm.platform.basecommerce.core.enums.GenderType;
import cm.platform.basecommerce.core.enums.ParticipantCategory;
import cm.platform.basecommerce.core.exception.NzockException;
import cm.platform.basecommerce.services.EnumerationService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.MetaTypeService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.myleaninig.core.ParticipantGroupModel;
import cm.platform.myleaninig.core.ParticipantModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@ActionService("participantAction")
public class ParticipantAction extends AbstractAction {
    private static final Logger LOG  = LoggerFactory.getLogger(ParticipantAction.class);

    @Autowired
    private ModelService modelService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private EnumerationService enumService ;

    @Action(value = "upload", scope = HttpMethod.POST)
    public Map uploadParticipants(Map context) throws IOException, ModelServiceException {
        final MultipartFile file = (MultipartFile) context.get("file");
        if (Objects.nonNull(file)) {
            final String filename = file.getOriginalFilename();
            if (!filename.endsWith(".csv")) {
                throw new NzockException(String.format("Upload only concer csv file "));
            }
            BufferedReader csvReader = null ;
            List<List<String>> csvList = new ArrayList<>();
            String csvRecord =  null ;

            csvReader = new BufferedReader(new InputStreamReader(file.getInputStream(), "UTF-8"));
            List<String> headers = null;
            int index = 0 ;
            while ((csvRecord = csvReader.readLine()) != null) {
                if (index == 0) {
                    headers = Arrays.asList(csvRecord.split(";"));
                }
                //Process content
                List<String> record = Arrays.asList(csvRecord.split(";"));
                if (headers.size() != record.size()) {
                    continue;
                }
                if (StringUtils.isBlank(record.get(headers.indexOf("code")))) {
                    continue;
                }
                ParticipantModel participant = new ParticipantModel();
                Optional isParticipantExist = flexibleSearchService.find(record.get(headers.indexOf("code")), "code", ParticipantModel._TYPECODE);
                if (isParticipantExist.isPresent()) {
                    participant = (ParticipantModel) isParticipantExist.get();
                }

                fillParticipant(headers, record, participant);

                modelService.save(participant);
                index++ ;
            }
        }
        //LOG.info(String.format("Upload participant action with context : %s", context));
        return context ;
    }


    private void fillParticipant(List<String> headers, List<String> record, ParticipantModel participant) {
        for (String header : headers) {
            final String value = record.get(headers.indexOf(header));
            if (header.equalsIgnoreCase("code")) {
                participant.setCode(value);
            } else if (header.equalsIgnoreCase("name")) {
                participant.setName(value);
            } else if (header.equalsIgnoreCase("email")) {
                participant.setEmail(value);
            } else if (header.equalsIgnoreCase("gender")) {
                participant.setGender(GenderType.F);
                if (value.equalsIgnoreCase("Masculin")) {
                    participant.setGender(GenderType.M);
                }
            } else if (header.equalsIgnoreCase("category")) {
                if (StringUtils.isNotBlank(value)) {
                    participant.setCategory(enumService.getEnumerationValue(value, ParticipantCategory.class));
                }
            } else if (header.equalsIgnoreCase("password")) {
                if (StringUtils.isNotBlank(value)) {
                    participant.setPassword(new BCryptPasswordEncoder().encode(value));
                }
            } else if (header.equalsIgnoreCase("groupes")) {
                if (StringUtils.isNotBlank(value)) {
                    List<String> groupes = Arrays.asList(value.split(","));
                    for (String groupe : CollectionUtils.emptyIfNull(groupes)) {
                        final ParticipantGroupModel grp = (ParticipantGroupModel) flexibleSearchService.find(groupe, "code", ParticipantGroupModel._TYPECODE).orElse(null);
                        if (Objects.nonNull(grp)) {
                            List<ParticipantGroupModel> groups = new ArrayList<>(participant.getGroupes());
                            groups.add(grp);
                            participant.setGroupes(groups);
                        }
                    }
                }
            }
        }
    }


    @Override
    protected ModelService getModelService() {
        return modelService;
    }

    @Override
    protected FlexibleSearchService getFlexibleSearchService() {
        return flexibleSearchService;
    }

    @Override
    protected MetaTypeService getMetaTypeService() {
        return metaTypeService;
    }
}
