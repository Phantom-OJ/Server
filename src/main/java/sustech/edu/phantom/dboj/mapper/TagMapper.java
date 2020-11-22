package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.Tag;

import java.util.List;

/**
 * @author Lori
 */
public interface TagMapper {
    /**
     *
     * @return
     */
    List<Tag> allTagList();

    /**
     *
     * @param pid
     * @return
     */
    List<Tag> getProblemTags(int pid);
}
