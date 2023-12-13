package com.team.mztelecom.dto;

import com.team.mztelecom.domain.PurRevBoard;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurRevBoardDTO {
	
	private Long id;
	
	private String intmNm;
	
	private String boardTitle;

	private String boardDate;
	
	private String boardDetail;
	
	private String writer;
	
	private Long attachmentId;
	
	private Long custId;
	
//	private PurRevAttachmentDTO purRevAttachmentDTO;
	

	public PurRevBoard toEntity() {
		PurRevBoard build = PurRevBoard.builder()
//				.id(id)
				.intmNm(intmNm)
				.boardTitle(boardTitle)
				.boardDate(boardDate)
				.boardDetail(boardDetail)
				.writer(writer)
				.attachmentId(attachmentId)
//				.attachmentId(purRevAttachmentDTO.getId())
				.build();
				return build;
	}
	
//	@Builder
//	public PurRevBoardDTO(PurRevBoard purRevBoard) {
//		this.id = purRevBoard.getId();
//		this.intmNm = purRevBoard.getIntmNm();
//		this.boardTitle = purRevBoard.getBoardTitle();
//		this.boardDate = purRevBoard.getBoardDate();
//		this.boardDetail = purRevBoard.getBoardDetail();
//		this.attachmentId = purRevBoard.getAttachmentId();
//		this.writer = purRevBoard.getCustBas().getCustId();
//		this.custId = purRevBoard.getCustBas().getId();
//	}
	
	@Builder
	public PurRevBoardDTO(Long id, String intmNm, String boardTitle, String boardDate, String boardDetail,
			String writer, Long attachmentId, Long custId) {
		this.id = id;
		this.intmNm = intmNm;
		this.boardTitle = boardTitle;
		this.boardDate = boardDate;
		this.boardDetail = boardDetail;
		this.writer = writer;
//		this.purRevAttachmentDTO = purRevAttachmentDTO;
		this.attachmentId = attachmentId;
		this.custId = custId;
	}
	
	

}
