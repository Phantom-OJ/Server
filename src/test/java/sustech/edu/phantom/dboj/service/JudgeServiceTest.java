package sustech.edu.phantom.dboj.service;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sustech.edu.phantom.dboj.form.CodeForm;

import java.io.IOException;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("sustech.edu.phantom.dboj")
class JudgeServiceTest {

    @Autowired
    JudgeService judgeService;

    static String ACanswer="SELECT title, country, year_released FROM movies WHERE country <>'us' AND year_released = 1991 AND title LIKE 'The%'";
    static String WAanswer="SELECT title, country, year_released FROM movies WHERE year_released = 1991 AND title LIKE 'The%'";
    static String trigger_AC="set search_path = \"trigger_db\";create or replace function valid_check()\n    returns trigger\nas $$\n    declare\n        address_code varchar;\n        city varchar;\n        province varchar;\n        address varchar;\n        birthday varchar;\n        checksum int;\n        origin_cs int;\n    begin\n\n        --address\n        address_code := substring(new.id, 1, 6);\n        select name into address\n        from district\n        where code = address_code;\n        if address is null\n        then\n            raise exception 'invalid address code';\n        else\n            if substring(address_code, 3, 4) = '0000'\n            then\n                new.address := address;\n            elseif substring(address_code, 5, 2) = '00'\n            then\n                select name into province\n                from district where code = substring(address_code, 1, 2) || '0000';\n                new.address := province || ',' || address;\n            else\n                select name into province\n                from district where code = substring(address_code, 1, 2) || '0000';\n                select name into city\n                from district where code = substring(address_code, 1, 4) || '00';\n                if city is not null\n                then\n                    new.address := province || ',' || city || ',' || address;\n                else\n                    new.address := province || ',' || address;\n                end if;\n            end if;\n        end if;\n\n        --birthday\n        select to_char(to_date(substring(new.id, 7, 8), 'yyyyMMdd'), 'yyyyMMdd') into birthday;\n        if birthday <> substring(new.id, 7, 8)\n        then\n            raise exception 'invalid birthday';\n        elseif to_date(substring(new.id, 7, 8), 'yyyyMMdd') < '1900-01-01'::date\n        then\n            raise exception 'invalid birthday';\n        else\n            new.birthday := birthday;\n        end if;\n\n        --checksum\n        checksum := 0;\n        for i in 1 .. 17 loop\n            checksum := checksum + (mod(pow(2,18-i)::integer, 11) * substring(new.id, i, 1)::integer);\n        end loop;\n        checksum := mod(12 - mod(checksum, 11), 11);\n        if substring(new.id, 18, 1) = 'X'\n        then\n            origin_cs := 10;\n        else\n            origin_cs := substring(new.id, 18, 1)::integer;\n        end if;\n        if origin_cs <> checksum\n        then\n            raise exception 'wrong checksum';\n        end if;\n        return new;\n    end;\n    $$\nlanguage plpgsql;";

    @Test
    public void judgeCode() throws SQLException, InterruptedException, IOException {
        CodeForm codeForm=new CodeForm();
        codeForm.setCode(trigger_AC);
        codeForm.setDialect("pgsql");
        codeForm.setSubmitTime(12345L);
        judgeService.judgeCode(2,codeForm,1);
    }

    @Test
    void receiveJudgeResult() {
    }

    @Test
    void judgeOnePoint() {
    }
}