package com.team.mztelecom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.PurRevAttachment;

@Repository
public interface PurRevAttachmentRepository extends JpaRepository<PurRevAttachment, Long>{
	
	@Query(value = "select * from pur_rev_attachment where pur_rev_board_id = :id", nativeQuery = true)
	List<PurRevAttachment> findByPurRevBoardId(@Param("id") Long id);
	
	@Query(value = "select orig_file_name from pur_rev_attachment where pur_rev_board_id = :purRevBoardId", nativeQuery = true)
	List<String> findbyOrigFileName(@Param("purRevBoardId")Long purRevBoardId);

}
