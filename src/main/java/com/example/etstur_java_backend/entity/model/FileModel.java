package com.example.etstur_java_backend.entity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "_file")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Data
public class FileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    private String fileName;
    private String fileExtension;
    private String path;
    private long fileSize;
    private Date createDate;
    private Date updateDate;

}
