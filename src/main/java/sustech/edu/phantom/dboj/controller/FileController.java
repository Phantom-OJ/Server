package sustech.edu.phantom.dboj.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import sustech.edu.phantom.dboj.entity.JudgeDatabase;
import sustech.edu.phantom.dboj.entity.JudgeScript;
import sustech.edu.phantom.dboj.entity.User;
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.service.UploadService;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/6 22:02
 */
@Controller
@RequestMapping(value = "/api")
@Slf4j
public class FileController {

    @Value("${filerootpath.windows}")
    private String UPLOAD_FOLDER_WINDOWS;

    @Value("${filerootpath.linux}")
    private String UPLOAD_FOLDER_LINUX;

    @Autowired
    UploadService uploadService;

    @RequestMapping(value = "/upload/avatar", method = RequestMethod.POST)
    @PreAuthorize("hasRole('ROLE_STUDENT')")
    public ResponseEntity<GlobalResponse<String>> uploadAvatar(HttpServletRequest request) {
        String resPath = getResourcesPath(null, true);
        MultipartHttpServletRequest params = (MultipartHttpServletRequest) request;
        List<MultipartFile> avatar = params.getFiles("avatar");
        log.info(avatar.toString());
        MultipartFile file;
        BufferedOutputStream stream;
        String picturePath = null;
        ResponseMsg res;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            for (MultipartFile multipartFile : avatar) {
                System.err.println(multipartFile.getContentType());
                file = multipartFile;
                if (!file.isEmpty()) {
                    log.info("图片不为空，开始上传");
                    String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                    String fileName = user.getUsername().substring(0, user.getUsername().indexOf("@")) + "." + extName;
                    String filePath = resPath + fileName;
                    log.info(filePath);

                    new File(filePath).createNewFile();

                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(filePath));
                    stream.write(bytes);
                    stream.close();
                    picturePath = filePath;
                } else {
                    log.info("文件为空，退出");
                    res = ResponseMsg.EMPTY_FILE;
                    return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).data(null).build(), res.getStatus());
                }
                break;
            }
            //TODO: 上传图片
            if (uploadService.uploadAvatar(picturePath, user.getId())) {
                //TODO: 更新图片
                res = ResponseMsg.OK;
            } else {
                log.info("Something happens inside the server.");
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
            }

        } catch (Exception e) {
            System.out.println("You failed to upload " + " => "
                    + e.getMessage());
            res = ResponseMsg.BAD_REQUEST;
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).data(null).build(), res.getStatus());
    }

    @RequestMapping(value = "/upload/judgedb", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> uploadJudgeDB(HttpServletRequest request) {
        String resPath = getResourcesPath("judge_database", false);
        MultipartHttpServletRequest params = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = params.getFiles("file");
        String keyword = params.getParameter("keyword");
        log.info("keyword is " + keyword);
        MultipartFile file;
        BufferedOutputStream stream;
        String judgeDatabasePath = null;
        ResponseMsg res;
//        if (file.isEmpty()) {
//            res = ResponseMsg.EMPTY_FILE;
//        } else {
//            String extName = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//            String fileName = UUID.randomUUID().toString() + extName;
//            String filePath = resPath + fileName;
//            File toFile = new File(filePath);
//            if (!toFile.getParentFile().exists()) {
//                toFile.getParentFile().mkdirs();
//            }
//            try {
//                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(toFile));
//            } catch (Exception e) {
//                log.error(Arrays.toString(e.getStackTrace()));
//            }
//        }
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!user.getPermissionList().contains("upload judge database")) {
                res = ResponseMsg.FORBIDDEN;
            } else {
                for (MultipartFile multipartFile : files) {
                    file = multipartFile;
                    if (!file.isEmpty()) {
                        String extName = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                        String fileName = UUID.randomUUID().toString() + extName;
                        String filePath = resPath + fileName;
                        byte[] bytes = file.getBytes();
                        stream = new BufferedOutputStream(new FileOutputStream(filePath));
                        stream.write(bytes);
                        stream.close();
                        judgeDatabasePath = filePath;
                    } else {
                        res = ResponseMsg.EMPTY_FILE;
                        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).data(null).build(), res.getStatus());
                    }
                    break;
                }
                JudgeDatabase judgeDatabase = JudgeDatabase.builder().keyword(keyword).databaseUrl(judgeDatabasePath).build();
                if (uploadService.saveJudgeDB(judgeDatabase)) {
                    res = ResponseMsg.OK;
                } else {
                    res = ResponseMsg.INTERNAL_SERVER_ERROR;
                }
            }
        } catch (ClassCastException e) {
            // TODO: 所有的未验证的访问全部显示 The visit from (IPv4) at <timestamp> is not signed in.
            log.error("The visit from " + request.getRemoteAddr() + " is not signed in.");
            res = ResponseMsg.UNAUTHORIZED;
        } catch (Exception e) {
            stream = null;
            System.out.println("You failed to upload " + " => "
                    + e.getMessage());
            res = ResponseMsg.BAD_REQUEST;
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).data(null).build(), res.getStatus());
    }

    @RequestMapping(value = "/upload/judgescript", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> uploadJudgeScript(HttpServletRequest request) {
        String resPath = getResourcesPath("judge_script", false);
        MultipartHttpServletRequest params = (MultipartHttpServletRequest) request;
        List<MultipartFile> files = params.getFiles("file");
        String keyword = params.getParameter("keyword");
        log.info("keyword is " + keyword);
        MultipartFile file;
        BufferedOutputStream stream;
        String judgeScriptPath = null;
        ResponseMsg res;
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!user.getPermissionList().contains("upload judge script")) {
                log.info("you have not such permission.");
                res = ResponseMsg.FORBIDDEN;
            } else {
                for (MultipartFile multipartFile : files) {
                    file = multipartFile;
                    if (!file.isEmpty()) {
                        log.info("文件不为空，开始上传");
                        String extName = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                        String fileName = UUID.randomUUID().toString() + "." + extName;
                        String filePath = resPath + fileName;
                        log.info(filePath);

                        new File(filePath).createNewFile();

                        byte[] bytes = file.getBytes();
                        stream = new BufferedOutputStream(new FileOutputStream(filePath));
                        stream.write(bytes);
                        stream.close();
                        judgeScriptPath = filePath;
                    } else {
                        log.info("文件为空，退出");
                        res = ResponseMsg.EMPTY_FILE;
                        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).data(null).build(), res.getStatus());
                    }
                    break;
                }
                JudgeScript judgeScript = JudgeScript.builder().keyword(keyword).script(judgeScriptPath).build();
                if (uploadService.saveJudgeScript(judgeScript)) {
                    res = ResponseMsg.OK;
                } else {
                    log.info("Something happens inside the server.");
                    res = ResponseMsg.INTERNAL_SERVER_ERROR;
                }
            }
        } catch (ClassCastException e) {
            // TODO: 所有的未验证的访问全部显示 The visit from (IPv4) at <timestamp> is not signed in.
            log.error("The visit from " + request.getRemoteAddr() + " is not signed in.");
            res = ResponseMsg.UNAUTHORIZED;
        } catch (Exception e) {
            System.out.println("You failed to upload " + " => "
                    + e.getMessage());
            res = ResponseMsg.BAD_REQUEST;
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).data(null).build(), res.getStatus());
    }

    private String getResourcesPath(String type, boolean pic) {
        String osName = System.getProperty("os.name");
        String path = null;
        if (Pattern.matches("Linux.*", osName) || Pattern.matches("Mac.*", osName)) {
            if (pic) {
                path = UPLOAD_FOLDER_LINUX + "avatar/";
            } else {
                path = UPLOAD_FOLDER_LINUX + "data/" + type + "/";
            }
        } else if (Pattern.matches("Windows.*", osName)) {
            if (pic) {
                path = UPLOAD_FOLDER_WINDOWS + "avatar\\";
            } else {
                path = UPLOAD_FOLDER_WINDOWS + "data\\" + type + "\\";
            }
        }
        try {
            assert path != null;
            File filePath = new File(path);
            if (!filePath.exists() && filePath.mkdirs()) {
                log.info("创建文件夹路径为：" + filePath);
            }
        } catch (Exception e) {
            log.error("出错啦!");
        }
        return path;
    }
}
