package org.bs.x2.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component // 클래스를 Spring 컨테이너의 구성 요소(component)로 등록
public class FileUploader {

    @Value("${org.bs.upload.path}")
    private String path;

    public List<String> uploadFiles(List<MultipartFile> files ,boolean makeThumbnail){

        // 파일이 존재하지 않을 떄
        if(files == null || files.size() == 0){
            throw new UploadException("No File");
        }

        List<String> uploadFileNames = new ArrayList<>();

        log.info("path: " + path);

        log.info(files);

        // loop 돌려서 세이브
        for (MultipartFile mfile : files){

            // 오리지날 파일 읽기
            String originalFileName = mfile.getOriginalFilename();

            //uuid
            String uuid = UUID.randomUUID().toString();

            // 파일의 이름은 + uuid+_+오리지날 파일네임 - 썸네일 생성 시 간편
            String saveFileName = uuid+"_"+originalFileName;

            // 새로운 파일 생성 ( 원본 파일 )
            File saveFile = new File(path, saveFileName);

            try ( InputStream in = mfile.getInputStream();
                OutputStream out = new FileOutputStream(saveFile);
            ) {
                
                // 원본 파일 카피
                FileCopyUtils.copy(in, out);

                // 썸네일이 필요하다면
                if(makeThumbnail){

                    // out 파일 생성
                    File thumbOutFile = new File(path,"s_"+saveFileName);

                    // 썸네일 파일 생성
                    Thumbnailator.createThumbnail(saveFile,thumbOutFile,200,200);

                } // if end

                // 파일 추가
                uploadFileNames.add(saveFileName);

            } catch (Exception e) {
                // 오류나면 UploadException 에러 메세지 보내기
                throw new UploadException("Uplaod Fail: " + e.getMessage());
            }
        }

        return uploadFileNames;
    }

    public static class UploadException extends RuntimeException{

        public UploadException(String msg){
            super(msg);
        }
    }

    // 파일 삭제 함수
    public void delete(List<String> fileNames){
        // 파일이 없는 경우 리턴
        if(fileNames == null || fileNames.size() == 0){
            return;
        }

        for (String fname :
                fileNames) {

            // 원본 파일
            File original = new File(path,fname);

            // 썸네일
            File thumb = new File(path,"s_"+fname);

            // 썸네일이 있다면 삭제처리
            if(thumb.exists()){
                thumb.delete();
            }

            original.delete();
        }


    }
    
}
