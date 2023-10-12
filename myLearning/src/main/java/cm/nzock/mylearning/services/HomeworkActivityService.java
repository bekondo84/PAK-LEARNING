package cm.nzock.mylearning.services;

import cm.platform.basecommerce.services.exceptions.ModelServiceException;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public interface HomeworkActivityService {

    Map<String, String> create(Map<String, String> context) throws ModelServiceException, ClassNotFoundException, JsonProcessingException;

    List fetchItems(Map context) throws ClassNotFoundException;
    long nbreOfFetchItems(Map context) throws ClassNotFoundException;

}
