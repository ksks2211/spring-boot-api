package org.iptime.yoon.blog.repository;

import org.iptime.yoon.blog.entity.Category;
import org.iptime.yoon.blog.entity.Post;
import org.iptime.yoon.blog.repository.projection.PostPreviewProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author rival
 * @since 2023-08-10
 */
public interface PostRepository extends JpaRepository<Post,Long> {
    @Query("select p.id as id, p.title as title, p.writerName as writerName, p.createdAt as createdAt, p.updatedAt as updatedAt, p.description as description " +
        "from Post p "+
        "where p.deleted = false")
    Page<PostPreviewProjection> findPostList(Pageable pageable);

    @Query("select p.id as id, p.title as title, p.writerName as writerName, p.createdAt as createdAt, p.updatedAt as updatedAt, p.description as description " +
        "from Post p " +
        "where p.deleted = false and p.id > :id " +
        "order by p.id asc limit 1")
    Optional<PostPreviewProjection> findNextPost(Long id);

    @Query("select p.id as id, p.title as title, p.writerName as writerName, p.createdAt as createdAt, p.updatedAt as updatedAt, p.description as description " +
        "from Post p " +
        "where p.deleted = false and p.id < :id " +
        "order by p.id desc limit 1")
    Optional<PostPreviewProjection> findPrevPost(Long id);



    @Query("select p.id as id, p.title as title, p.writerName as writerName, p.createdAt as createdAt, p.updatedAt as updatedAt, p.description as description " +
        "from Post p "+
        "where p.deleted = false and p.category = :category")
    Page<PostPreviewProjection> findPostListByCategory(Pageable pageable, Category category);

}
