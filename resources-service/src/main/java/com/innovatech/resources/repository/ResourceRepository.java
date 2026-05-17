package com.innovatech.resources.repository;

import com.innovatech.resources.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long> {}
