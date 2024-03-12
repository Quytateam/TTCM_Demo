package com.example.demo.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comic")
public class Comic {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "DESCRIPTION",columnDefinition = "TEXT")
    @Lob
    private String description;
    
    @Column(name = "name", unique = true)
    private String name;
    
    private String publisher;

    private String writer;
    
    private String artist;
    
    // @Column(name = "IMAGE",columnDefinition = "TEXT")
    // @Lob
    private String image;
    
    private Integer status;
    
    @Column(name = "view_count")
    private int view;
    
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp createdAt;
    
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    private int enable;

    @Transient
    private String imageLink;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "comic_genre",joinColumns = @JoinColumn(name = "comic_id"),inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres = new HashSet<>();

    @Transient
    @OneToMany(mappedBy = "comic", fetch = FetchType.LAZY)
    private List<Chapter> chapters;

    @Transient
    @OneToMany(mappedBy = "comic", fetch = FetchType.LAZY)
    private List<Comment> comments;

    // cascade = CascadeType.ALL, fetch = FetchType.EAGER
}
