package com.team.mztelecom.dto;

import java.util.List;

import com.team.mztelecom.domain.PurRevBoard;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PurRevBoardDTO {
	
	Long 	id;
	String	intmNm;
	String	boardTitle;
	String	boardDate;
	String	boardDetail;
	String	writer;
	Long	fileId;
	
	List<PurRevAttachmentDTO> purRevAttachmentDTO;
	
	Long custId;
	
	List<CustBasDTO> custBasDTO;
	
	public PurRevBoard toEntity() {
		PurRevBoard build = PurRevBoard.builder()
				.intmNm(intmNm)
				.boardTitle(boardTitle)
				.boardDate(boardDate)
				.boardDetail(boardDetail)
				.writer(writer)
				.build();
				return build;
	}
	
	@Builder
	public PurRevBoardDTO(Long id, String intmNm, String boardTitle, String boardDate, String boardDetail,
			String writer, List<PurRevAttachmentDTO> purRevAttachmentDTO, Long custId, List<CustBasDTO> custBasDTO) {
		this.id = id;
		this.intmNm = intmNm;
		this.boardTitle = boardTitle;
		this.boardDate = boardDate;
		this.boardDetail = boardDetail;
		this.writer = writer;
		this.purRevAttachmentDTO = purRevAttachmentDTO;
		this.custId = custId;
		this.custBasDTO = custBasDTO;
	}
	
	

}
