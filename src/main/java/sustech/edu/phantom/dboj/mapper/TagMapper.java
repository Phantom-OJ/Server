package sustech.edu.phantom.dboj.mapper;

import java.util.Map;

public interface TagMapper {

    /**
     * get the tag map
     * @return Map that maps keyword to id
     */
    Map<String, Integer> getTagMap();
}
