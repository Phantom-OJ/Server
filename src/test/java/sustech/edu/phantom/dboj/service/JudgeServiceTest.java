package sustech.edu.phantom.dboj.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sustech.edu.phantom.dboj.form.CodeForm;

import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("sustech.edu.phantom.dboj")
class JudgeServiceTest {

    @Autowired
    JudgeService judgeService;

    static String answer="SELECT title, country, year_released FROM movies WHERE country <>'us' AND year_released = 1991 AND title LIKE 'The%'";

    @Test
    public void judgeCode() throws SQLException, InterruptedException {
        CodeForm codeForm=new CodeForm();
        codeForm.setCode(answer);
        codeForm.setDialect("pgsql");
        codeForm.setSubmitTime(12345L);
        judgeService.judgeCode(1,codeForm,1);

    }

    @Test
    void receiveJudgeResult() {
    }

    @Test
    void judgeOnePoint() {
    }
}