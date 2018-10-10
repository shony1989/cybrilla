package com.cybrilla.urlshortner.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cybrilla.urlshortner.domainobject.ShortenUrl;

public interface URLRepository extends JpaRepository<ShortenUrl, Long> {
	

}
