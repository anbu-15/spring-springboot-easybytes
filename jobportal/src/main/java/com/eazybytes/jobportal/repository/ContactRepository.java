package com.eazybytes.jobportal.repository;

import com.eazybytes.jobportal.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    List<Contact> findContactsByStatus(String status);

    List<Contact> findContactsByStatus(String status, Sort sort);

    Page<Contact> findContactsByStatus(String status, Pageable sort);

    List<Contact> findContactsByStatusOrderByCreatedAtAsc(String status);

    @Modifying
//    @Modifying(flushAutomatically = true,clearAutomatically = true)
    int updateStatusById(@Param("status") String status,
                         @Param("id") Long id,
                         @Param("updatedBy") String updatedBy);
}