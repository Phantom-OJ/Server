package sustech.edu.phantom.dboj.mapper;

import org.apache.ibatis.annotations.Param;
import sustech.edu.phantom.dboj.entity.po.Tag;

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

    Integer saveOneProblemTags(@Param("list") List<Integer> list,@Param("pid") int pid);

    int invalidateProblemTag(@Param("pid") int pid, @Param("tid") int tid);

    int saveTag(Tag tag);
}
