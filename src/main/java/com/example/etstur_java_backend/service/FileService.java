package com.example.etstur_java_backend.service;

import com.example.etstur_java_backend.entity.dao.FileModelResponseDAO;
import com.example.etstur_java_backend.entity.dto.FileResponseDTO;
import com.example.etstur_java_backend.entity.model.FileModel;
import com.example.etstur_java_backend.entity.model.User;
import com.example.etstur_java_backend.error.FileDoesNotBelongToUserException;
import com.example.etstur_java_backend.error.FileNotAllowedException;
import com.example.etstur_java_backend.error.FileNotFoundException;
import com.example.etstur_java_backend.error.FileSizeLimitExceededException;
import com.example.etstur_java_backend.error.TokenIsNotValidException;
import com.example.etstur_java_backend.generic.GenericResponse;
import com.example.etstur_java_backend.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final UserCRUDService userCRUDService;


    private String path = "C:\\Users\\safak\\Desktop\\etstur_java_backend\\savedFile\\";

    public GenericResponse saveFile(MultipartFile file, String token) throws Exception {
        allowedFiles(file);
        try {
            User u = userCRUDService.findUserToToken(token);
            Date now = new Date();

            FileModel fileToSave = FileModel.builder()
                    .fileName(file.getOriginalFilename())
                    .fileExtension(allowedFiles(file))
                    .fileSize(file.getSize())
                    .user(u)
                    .createDate(now)
                    .updateDate(now)
                    .build();
            FileModel temp = fileRepository.save(fileToSave);
            temp.setPath(path + temp.getId());
            fileRepository.save(temp);
            Path p = Paths.get(path + temp.getId());
            Files.copy(file.getInputStream(), p);
            return GenericResponse.of(FileResponseDTO.builder()
                    .message("File saved successfully.")
                    .build());
        } catch (Exception e) {
            throw new TokenIsNotValidException();
        }
    }

    public GenericResponse getAllFiles(String token) {
        try {
            User u = userCRUDService.findUserToToken(token);
            return GenericResponse.of(fileRepository.findFileByUserId(u.getId()));
        } catch (Exception e) {
            throw new TokenIsNotValidException();
        }
    }

    public GenericResponse getFileDetails(String token, Long id) {

        try {
            FileModel file = fileRepository.findById(id).orElseThrow(com.example.etstur_java_backend.error.FileNotFoundException::new);
            User u = userCRUDService.findUserToToken(token);
            if (file.getUser().getId().equals(u.getId())) {
                return GenericResponse.of(FileModelResponseDAO.builder()
                        .fileName(file.getFileName())
                        .fileExtension(file.getFileExtension())
                        .path(file.getPath())
                        .fileSize(file.getFileSize())
                        .createDate(file.getCreateDate())
                        .updateDate(file.getUpdateDate())
                        .userName(u.getUsername())
                        .build());
            } else {
                throw new FileDoesNotBelongToUserException();
            }

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (FileDoesNotBelongToUserException e) {
            throw new FileDoesNotBelongToUserException();
        } catch (Exception e) {
            throw new TokenIsNotValidException();
        }
    }

    public byte[] getFileAsByteArray(String token, Long id) {
        try {
            User u = userCRUDService.findUserToToken(token);
            FileModel file = fileRepository.findById(id).orElseThrow(com.example.etstur_java_backend.error.FileNotFoundException::new);
            if (file.getUser().getId().equals(u.getId())) {
                byte[] byteArray = readBytesFromFile(file.getPath());
                return byteArray;
            } else {
                throw new FileDoesNotBelongToUserException();
            }

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (FileDoesNotBelongToUserException e) {
            throw new FileDoesNotBelongToUserException();
        } catch (Exception e) {
            throw new TokenIsNotValidException();
        }
    }

    public GenericResponse updateFile(String token, Long id, MultipartFile updatedFile) {
        try {
            User u = userCRUDService.findUserToToken(token);
            FileModel file = fileRepository.findById(id).orElseThrow(com.example.etstur_java_backend.error.FileNotFoundException::new);
            if (file.getUser().getId().equals(u.getId())) {
                deleteFile(file.getPath());
                file.setFileExtension(allowedFiles(updatedFile));
                file.setFileName(updatedFile.getOriginalFilename());
                file.setFileSize(updatedFile.getSize());
                file.setUpdateDate(new Date());
                fileRepository.save(file);
                Path p = Paths.get(path + file.getId());
                Files.copy(updatedFile.getInputStream(), p);
                return GenericResponse.of(FileResponseDTO.builder()
                        .message("File update successfully.")
                        .build());
            } else {
                throw new FileDoesNotBelongToUserException();
            }

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (FileDoesNotBelongToUserException e) {
            throw new FileDoesNotBelongToUserException();
        } catch (Exception e) {
            throw new TokenIsNotValidException();
        }
    }

    public GenericResponse deieteFile(String token, Long id) {
        try {
            User u = userCRUDService.findUserToToken(token);
            FileModel file = fileRepository.findById(id).orElseThrow(com.example.etstur_java_backend.error.FileNotFoundException::new);
            if (file.getUser().getId().equals(u.getId())) {
                deleteFile(file.getPath());
                fileRepository.deleteById(file.getId());
                return GenericResponse.of(FileResponseDTO.builder()
                        .message("File deleted successfully.")
                        .build());
            } else {
                throw new FileDoesNotBelongToUserException();
            }

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException();
        } catch (FileDoesNotBelongToUserException e) {
            throw new FileDoesNotBelongToUserException();
        } catch (Exception e) {
            throw new TokenIsNotValidException();
        }
    }

    private static byte[] readBytesFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        System.out.println("s");
        byte[] byteArray = new byte[(int) file.length()];
        fileInputStream.read(byteArray);
        fileInputStream.close();
        return byteArray;
    }

    private String allowedFiles(MultipartFile file) {
        if (file.getSize() > 5000000)
            throw new FileSizeLimitExceededException();
        if (file.getOriginalFilename().endsWith(".png")) {
            return ".png";
        } else if (file.getOriginalFilename().endsWith(".jpeg")) {
            return ".jpeg";
        } else if (file.getOriginalFilename().endsWith(".jpg")) {
            return ".jpg";
        } else if (file.getOriginalFilename().endsWith(".docx")) {
            return ".docx";
        } else if (file.getOriginalFilename().endsWith(".pdf")) {
            return ".pdf";
        } else if (file.getOriginalFilename().endsWith(".xlsx")) {
            return ".xlsx";
        } else {
            throw new FileNotAllowedException();
        }

    }

    private void deleteFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        Files.delete(path);
    }


}
