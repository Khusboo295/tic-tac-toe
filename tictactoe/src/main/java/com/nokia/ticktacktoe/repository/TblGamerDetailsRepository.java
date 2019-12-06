package com.nokia.ticktacktoe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nokia.ticktacktoe.domain.TblGamerDetails;

/**
 * Repository interface for TblGamerDetails table
 */
@Repository
public interface TblGamerDetailsRepository extends JpaRepository<TblGamerDetails, Long> {

}
