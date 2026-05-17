package com.innovatech.projects.repository;

import com.innovatech.projects.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> { }
