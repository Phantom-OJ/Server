package sustech.edu.phantom.dboj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sustech.edu.phantom.dboj.entity.Assignment;
import sustech.edu.phantom.dboj.mapper.AssignmentMapper;

@Service
public class AssignmentService {

    @Autowired
    AssignmentMapper assignmentMapper;

    public Assignment getOneAssignment(){
        return assignmentMapper.getOneAssignment(1);
    }
}
