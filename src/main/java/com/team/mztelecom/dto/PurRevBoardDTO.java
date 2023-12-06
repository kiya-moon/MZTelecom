package com.team.mztelecom.dto;

import com.team.mztelecom.domain.PurRevBoard;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PurRevBoardDTO {
	
	private Long Id;
	
	private String intmNm;
	
	private String boardTitle;

	private String boardDate;
	
	private String boardDetail;
	
	private String boardAttachment;
	
	private String writer;
	
	private Long custId;
	
	
	// DTO <-> entity
	@Builder
	public PurRevBoardDTO(PurRevBoard purRevBoard) {
		Id = purRevBoard.getId();
		this.intmNm = purRevBoard.getIntmNm();
		this.boardTitle = purRevBoard.getBoardTitle();
		this.boardDate = purRevBoard.getBoardDate();
		this.boardDetail = purRevBoard.getBoardDetail();
		this.boardAttachment = purRevBoard.getBoardAttachment();
		this.writer = purRevBoard.getCustBas().getCustId();
		this.custId = purRevBoard.getCustBas().getId();
	}

}
