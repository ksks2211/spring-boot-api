package org.iptime.yoon.blog.category;

import jakarta.persistence.*;
import lombok.*;
import org.iptime.yoon.blog.post.entity.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rival
 * @since 2023-09-01
 */

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Table(indexes = @Index(name = "index_category_root", columnList = "root"))
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Setter
    private Long id;


    // Equal to username of owner
    private String root;


    // root + category name e.g. "user123/java/spring_boot
    @Column(unique = true)
    @Setter
    private String fullName;


    // category name  e.g. /java/spring_boot
    @Setter
    private String name;


    // number of posts in the category
    @Builder.Default
    private Integer postCount = 0;



    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @Builder.Default
    private List<Post> posts = new ArrayList<>();

    public void increasePostCount(){
        this.postCount++;
    }

    public void decreasePostCount(){
        this.postCount--;
    }
}
