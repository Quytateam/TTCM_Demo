package com.example.demo.entity;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "chapter")
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "comic_id")
    private Long comicId;

    // @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    // @JoinColumn(name = "comic_id", referencedColumnName = "id", insertable = false, updatable = false)
    // private Comic comic;

    @Column(name = "comic_name")
    private String comicName;

    @Column(name = "name")
    private String name;

    @Column(name = "content")
    private String content;

    private int enable;

    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Transient
    private List<ImageInfo> imageInfo;
}
