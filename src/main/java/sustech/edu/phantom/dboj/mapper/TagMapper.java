package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.Tag;

import java.util.List;

public interface TagMapper {
    List<Tag> allTagList();

    List<Tag> getProblemTags(int pid);
}
