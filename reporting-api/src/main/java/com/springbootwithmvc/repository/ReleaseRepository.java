package com.springbootwithmvc.repository;


import com.springbootwithmvc.entity.Release;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseRepository extends JpaRepository<Release, Long> {
}