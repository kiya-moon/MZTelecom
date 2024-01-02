package com.team.mztelecom.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.team.mztelecom.domain.PurRevBoard;

@Repository
public interface PurRevBoardRepository extends JpaRepository<PurRevBoard, Long> {
	
	/**
	 * 글제목으로 검색 - 김시우
	 * 
	 * @param pageable
	 * @param keyword
	 * @return
	 */
	Page<PurRevBoard> findByBoardTitleContains(String boardTitle, Pageable pageable);
	
	/**
	 * 작성자로 검색 - 김시우
	 * 
	 * @param pageable
	 * @param keyword
	 * @return
	 */
	Page<PurRevBoard> findByWriterContains(String writer, Pageable pageable);
	
	
}
