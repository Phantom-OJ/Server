package sustech.edu.phantom.dboj.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
import sustech.edu.phantom.dboj.entity.enumeration.ResponseMsg;
import sustech.edu.phantom.dboj.entity.po.User;
import sustech.edu.phantom.dboj.entity.response.GlobalResponse;
import sustech.edu.phantom.dboj.service.UploadService;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author Shilong Li (Lori)
 * @version 1.0
 * @date 2020/12/6 22:02
 */
@Controller
@RequestMapping(value = "/api")
@Slf4j
@Api(tags = "File uploading functions")
@PreAuthorize("hasRole('ROLE_STUDENT')")
public class AvatarController {

    @Value("${file-root-path.windows}")
    private String UPLOAD_FOLDER_WINDOWS;

    @Value("${file-root-path.linux-abs}")
    private String UPLOAD_FOLDER_LINUX;

    @Value("${file-root-path.linux}")
    private String DEFAULT;

    @Value("${file-root-path.default-avatar-filename}")
    private String DEFAULT_AVATAR;

    @Autowired
    UploadService uploadService;

    @ApiOperation("上传头像")
    @RequestMapping(value = "/upload/avatar", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse<String>> uploadAvatar(HttpServletRequest request) {
        String resPath = getResourcesPath(null, true);
        MultipartHttpServletRequest params = (MultipartHttpServletRequest) request;
        List<MultipartFile> avatar = params.getFiles("file");
        log.info(avatar.toString());
        MultipartFile file;
        BufferedOutputStream stream;
        String picturePath = null;
        String dbfilepath = null;
        ResponseMsg res;
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            for (MultipartFile multipartFile : avatar) {
                System.err.println(multipartFile.getContentType());
                if (!Objects.requireNonNull(multipartFile.getContentType()).matches("^image/\\w+$")) {
                    throw new Exception("Not images!");
                }
                file = multipartFile;
                if (!file.isEmpty()) {
                    log.info("图片不为空，开始上传");
                    String extName = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
                    String fileName = user.getUsername().substring(0, user.getUsername().indexOf("@")) + "." + extName;
                    String filePath = resPath + fileName;
                    dbfilepath = DEFAULT + fileName;
                    log.info(filePath);
                    byte[] bytes = file.getBytes();
                    if (!isImage(bytes)) {
                        throw new Exception("Not images!");
                    }
                    String originalName = user.getAvatar().substring(user.getAvatar().lastIndexOf(File.separator) + 1);
                    if (!originalName.equals(DEFAULT_AVATAR)) {
                        log.info(originalName);
                        File file1 = new File(user.getAvatar());
                        file1.delete();
                    }

                    new File(filePath).createNewFile();

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
            if (uploadService.uploadAvatar(dbfilepath, user.getId())) {
                res = ResponseMsg.OK;
            } else {
                log.info("Something happens inside the server.");
                res = ResponseMsg.INTERNAL_SERVER_ERROR;
            }

        } catch (Exception e) {
            System.out.println("You failed to upload " + " => " + e.getMessage());
            res = ResponseMsg.BAD_REQUEST;
        }
        return new ResponseEntity<>(GlobalResponse.<String>builder().msg(res.getMsg()).data(null).build(), res.getStatus());
    }

    /**
     * 获取路径信息
     *
     * @param type 文件类型
     * @param pic  是否是头像
     * @return 路径
     */
    private String getResourcesPath(String type, boolean pic) {
        String osName = System.getProperty("os.name");
        String usrName = System.getProperty("user.name");
        String path = null;
        if (Pattern.matches("Linux.*", osName) || Pattern.matches("Mac.*", osName)) {
            if (pic) {
                path = UPLOAD_FOLDER_LINUX + "avatar" + File.separator;
            } else {
                path = UPLOAD_FOLDER_LINUX + "data" + File.separator + type + File.separator;
            }
        } else if (Pattern.matches("Windows.*", osName)) {
            if (pic) {
                path = UPLOAD_FOLDER_WINDOWS + usrName + File.separator + "Desktop" + File.separator +
                        "avatar" + File.separator;
            } else {
                path = UPLOAD_FOLDER_WINDOWS + usrName + File.separator + "Desktop" + File.separator + "data" + File.separator + type + File.separator;
            }
        }
        try {
            assert path != null;
            File filePath = new File(path);
            if (!filePath.exists() && filePath.mkdirs()) {
                log.info("创建文件夹路径为: " + filePath);
            }
        } catch (Exception e) {
            log.error("出错啦!");
        }
        return path;
    }

    private static boolean isBMP(byte[] buf) {
        byte[] markBuf = "BM".getBytes();  //BMP图片文件的前两个字节
        return compare(buf, markBuf);
    }

    private static boolean isICON(byte[] buf) {
        byte[] markBuf = {0, 0, 1, 0, 1, 0, 32, 32};
        return compare(buf, markBuf);
    }

    private static boolean isWEBP(byte[] buf) {
        byte[] markBuf = "RIFF".getBytes(); //WebP图片识别符
        return compare(buf, markBuf);
    }

    private static boolean isGIF(byte[] buf) {
        byte[] markBuf1 = "GIF89a".getBytes(); //GIF识别符
        byte[] markBuf2 = "GIF87a".getBytes();
        return compare(buf, markBuf1) || compare(buf, markBuf2);
    }


    private static boolean isPNG(byte[] buf) {
        byte[] markBuf = {(byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A}; //PNG识别符
        // new String(buf).indexOf("PNG")>0 //也可以使用这种方式
        return compare(buf, markBuf);
    }

    private static boolean isJPEGHeader(byte[] buf) {
        byte[] markBuf = {(byte) 0xff, (byte) 0xd8}; //JPEG开始符
        return compare(buf, markBuf);
    }

    private static boolean isJPEGFooter(byte[] buf) {//JPEG结束符
        byte[] markBuf = {(byte) 0xff, (byte) 0xd9};
        return compare(buf, markBuf);
    }

    private static boolean compare(byte[] buf, byte[] markBuf) {
        for (int i = 0; i < markBuf.length; i++) {
            byte b = markBuf[i];
            byte a = buf[i];
            if (a != b) {
                return false;
            }
        }
        return true;
    }

    public static boolean isImage(byte[] imageContent) {
        if (imageContent == null || imageContent.length == 0) {
            return false;
        }
        Image img;
        try (InputStream is = new ByteArrayInputStream(imageContent)) {
            img = ImageIO.read(is);
            return img != null && img.getWidth(null) > 0
                    && img.getHeight(null) > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public static byte[] subBytes(byte[] src, int begin, int count) {
        byte[] bs = new byte[count];
        System.arraycopy(src, begin, bs, 0, count);
        return bs;
    }

    public static boolean isPic(byte[] imageBytes) {
        byte[] start = subBytes(imageBytes, 0, 8);
        byte[] end = subBytes(imageBytes, imageBytes.length - 2, 2);
        return false;
    }

}
