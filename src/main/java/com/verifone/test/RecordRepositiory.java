package com.verifone.test;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepositiory extends JpaRepository<Record, Long> {

}
