package org.iptime.yoon.blog.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author rival
 * @since 2023-09-01
 */

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(indexes = @Index(name = "index_category_root", columnList = "root"))
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;


    private String root;

    @Column(unique = true)
    private String category;

    @Builder.Default
    private Integer fileCount = 0;

    public void increaseFileCount(){
        this.fileCount++;
    }
}