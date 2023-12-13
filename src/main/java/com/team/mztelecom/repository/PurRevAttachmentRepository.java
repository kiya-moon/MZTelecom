package com.team.mztelecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.PurRevAttachment;

@Repository
public interface PurRevAttachmentRepository extends JpaRepository<PurRevAttachment, Long>{

}
