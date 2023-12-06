package com.team.mztelecom.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.team.mztelecom.dto.PurRevBoardDTO;

@Service
public interface PurRevBoardService {
	
	/**
	 * 게시글 목록 조회	- 김시우
	 * 
	 * @param pageable 페이징 처리
	 * @return 게시글 목록 (페이징)
	 */
	Page<PurRevBoardDTO> PurRevBoardList(Pageable pageable);
	
	/**
	 * 검색 조회 	- 김시우
	 * @param pageable	페이징처리
	 * @param keyword	검색어
	 * @param catgo		검색 카테고리
	 * @return
	 */
	Page<PurRevBoardDTO> searchingPurRevBoard(String keyword, Pageable pageable, String catgo);
	
}
