package cm.nzock.mylearning.actions;

import cm.nzock.mylearning.services.InvolveService;
import cm.platform.basecommerce.core.actions.AbstractAction;
import cm.platform.basecommerce.core.actions.annotations.Action;
import cm.platform.basecommerce.core.actions.annotations.ActionService;
import cm.platform.basecommerce.core.enums.InvolveState;
import cm.platform.basecommerce.core.enums.InvolveType;
import cm.platform.basecommerce.core.exception.NzockException;
import cm.platform.basecommerce.core.utils.IsisConstants;
import cm.platform.basecommerce.services.EnumerationService;
import cm.platform.basecommerce.services.FlexibleSearchService;
import cm.platform.basecommerce.services.MetaTypeService;
import cm.platform.basecommerce.services.ModelService;
import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import cm.platform.basecommerce.tools.persistence.RestrictionsContainer;
import cm.platform.myleaninig.core.InvolveModel;
import cm.platform.myleaninig.core.ParticipantModel;
import cm.platform.myleaninig.core.TrainingModel;
import cm.platform.myleaninig.core.WorkGroupActivityModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

@ActionService("involveAction")
public class InvolveAction extends AbstractAction {
    private static final Logger LOG = LoggerFactory.getLogger(InvolveAction.class);

    @Autowired
    private ModelService modelService;
    @Autowired
    private FlexibleSearchService flexibleSearchService;
    @Autowired
    private MetaTypeService metaTypeService;
    @Autowired
    private ObjectMapper mapper ;
    @Autowired
    private InvolveService involveService;
    @Autowired
    private EnumerationService enumService ;

    @Action(value = "upload", scope = HttpMethod.POST)
    public  Map uplaodInvolve(Map context) throws ModelServiceException, IOException {
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
                if (StringUtils.isBlank(record.get(headers.indexOf("concern")))) {
                    continue;
                }
                if (StringUtils.isBlank(record.get(headers.indexOf("training")))) {
                    continue;
                }
                //Find Concern
                Optional concern = flexibleSearchService.find(record.get(headers.indexOf("concern")), "code", ParticipantModel._TYPECODE);
                Optional training = flexibleSearchService.find(record.get(headers.indexOf("training")), "code", TrainingModel._TYPECODE);

                if (concern.isPresent() && training.isPresent()) {
                    final InvolveModel involve = getInvolve(concern, training);

                    fillInvolve(headers, record, concern, training, involve);
                    modelService.save(involve);
                    //Update Workgroup reserve
                    if (Objects.nonNull(involve.getWorkgroup())) {
                        final WorkGroupActivityModel wgrp = (WorkGroupActivityModel) flexibleSearchService.find(involve.getWorkgroup().getPK(), WorkGroupActivityModel._TYPECODE).orElse(null);
                        wgrp.setReserve((short) wgrp.getMembers().size());
                        modelService.save(wgrp);
                    }
                }
                index++ ;
            }
        }
        return context ;
    }

    private void fillInvolve(List<String> headers, List<String> record, Optional concern, Optional training, InvolveModel involve) {
        for (String header : headers) {
            final String value = record.get(headers.indexOf(header));
            if (header.equalsIgnoreCase("concern")) {
                concern.ifPresent(c -> involve.setConcern((ParticipantModel) c));
            } else if (header.equalsIgnoreCase("training")) {
                training.ifPresent(t -> {
                    involve.setTraining((TrainingModel) t);
                    involve.setTrainingCode(((TrainingModel)t).getCode());
                });
            } else if (header.equalsIgnoreCase("workgroup")) {
                if (StringUtils.isNotBlank(value)) {
                    flexibleSearchService.find(value, "code", WorkGroupActivityModel._TYPECODE)
                            .ifPresent(w -> involve.setWorkgroup((WorkGroupActivityModel) w));
                }
            } else if (header.equalsIgnoreCase("type")) {
                involve.setType(enumService.getEnumerationValue(value, InvolveType.class));
            }
        }
        if (Objects.nonNull(involve.getWorkgroup())) {
            involve.setRegisterDate(new Date());
            involve.setRegister(Boolean.TRUE);
            involve.setState(enumService.getEnumerationValue(InvolveState.REGISTER.getCode(), InvolveState.class));
        }
    }

    private InvolveModel getInvolve(Optional concern, Optional training) {
        InvolveModel involve = new InvolveModel() ;
        if (concern.isPresent() && training.isPresent()) {
            ParticipantModel participant = (ParticipantModel) concern.get();
            TrainingModel traini = (TrainingModel) training.get();
            RestrictionsContainer container = RestrictionsContainer.newInstance();
            container.addEq("concern.code", participant.getCode());
            container.addEq("training.code", traini.getCode());
            List involves = flexibleSearchService.doSearch(InvolveModel.class, container, new HashMap<>(), new HashSet<>(), 0, -1);
            if (CollectionUtils.isNotEmpty(involves)) {
                involve = (InvolveModel) involves.get(0);
            }
        }
        return involve;
    }

    @Transactional
    @Override
    public Map<String, String> createOrUpdate(Map<String, String> context) throws ModelServiceException, ClassNotFoundException, JsonProcessingException {
        String parentId = context.get("parent");
        InvolveModel involve = mapper.readValue(context.get("data"), InvolveModel.class);
        if (Objects.nonNull(involve.getTraining()) && Objects.nonNull(involve.getWorkgroup().getTraining())) {
            involve.setTraining(involve.getWorkgroup().getTraining());
        } else  {
            if (StringUtils.isNotBlank(parentId)) {
                Long trainingId = Long.valueOf(parentId);
                TrainingModel training = (TrainingModel) flexibleSearchService.find(trainingId, TrainingModel._TYPECODE).orElse(null);
                involve.setTraining(training);
            }
        }
        if (StringUtils.isNotBlank(parentId)) {
            WorkGroupActivityModel activity = (WorkGroupActivityModel) flexibleSearchService.find(Long.valueOf(parentId), WorkGroupActivityModel._TYPECODE).orElse(null);
            involve.setWorkgroup(activity);
        }
        involve = (InvolveModel) modelService.save(involve);
        context.put("data", modelService.findAndConvertToJson(involve));
        return context;
    }

    @Override
    public Map getItems(Map context) throws ClassNotFoundException {
        context.put("data", involveService.fetchItems(context));
        context.put("count", involveService.nbreOfFetchItems(context));
        return context;
    }

    @Action(value = "participantCount", scope = HttpMethod.GET)
    public Map  participantCount(final Map context) throws ClassNotFoundException {
        context.put("type", InvolveModel.class.getTypeName());
        context.put(IsisConstants.ActionsKeys.NUMBEROFITEMS, involveService.nbreOfFetchItems(context));
        return context;
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
