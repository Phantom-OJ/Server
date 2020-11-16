package sustech.edu.phantom.dboj.mapper;

import sustech.edu.phantom.dboj.entity.Code;

public interface CodeMapper {
    int saveCode(Code code);

    Code queryCode(int id);

    int invalidateCode(int id);
}
