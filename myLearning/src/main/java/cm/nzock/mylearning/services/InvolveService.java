package cm.nzock.mylearning.services;

import java.util.List;
import java.util.Map;

public interface InvolveService {
    List fetchItems(Map context) throws ClassNotFoundException;
    long nbreOfFetchItems(Map context) throws ClassNotFoundException;
}
