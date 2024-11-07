package com.phishing.userservice.domain.news.repository;

import com.phishing.userservice.domain.news.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public interface NewsRepository extends JpaRepository<News, Long>{
    @Modifying
    @Transactional
    @Query("delete from News n where n.createdAt < :yesterday")
    void deleteByCreatedAtBefore(@Param("yesterday") LocalDateTime yesterday);

    @Transactional
    @Query("select n from News n where n.createdAt > :yesterday")
    List<News> findAllByAfterYesterday(@Param("yesterday") LocalDateTime yesterday);
}
