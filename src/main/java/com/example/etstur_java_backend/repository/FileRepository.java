package com.example.etstur_java_backend.repository;

import com.example.etstur_java_backend.entity.model.FileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FileRepository extends JpaRepository<FileModel,Long> {
    @Query(value = "select * from _file where user_id = :id order by id", nativeQuery = true)
    List<FileModel> findFileByUserId(Long id);

    @Override
    <S extends FileModel> S save(S entity);


}
