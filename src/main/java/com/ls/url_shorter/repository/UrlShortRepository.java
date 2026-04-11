package com.ls.url_shorter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlShortRepository extends JpaRepository<UrlShorterEntity, String> {
}