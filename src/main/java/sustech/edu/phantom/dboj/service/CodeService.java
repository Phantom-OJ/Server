package sustech.edu.phantom.dboj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sustech.edu.phantom.dboj.entity.Code;
import sustech.edu.phantom.dboj.form.CodeForm;
import sustech.edu.phantom.dboj.mapper.CodeMapper;

import java.nio.charset.StandardCharsets;

@Service
public class CodeService {
    @Autowired
    CodeMapper codeMapper;

    public int saveCode(CodeForm codeForm) {
        Code code = Code.builder()
                .code(codeForm.getCode())
                .codeLength(codeForm.getCode().getBytes(StandardCharsets.UTF_8).length)
                .submitTime(codeForm.getSubmitTime())
                .build();
        return codeMapper.saveCode(code);
    }

    public Code queryCode(int id) {
        return codeMapper.queryCode(id);
    }

    public int invalidateCode(int id) {
        return codeMapper.invalidateCode(id);
    }
}
