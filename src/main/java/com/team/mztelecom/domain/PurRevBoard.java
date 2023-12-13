package com.team.mztelecom.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PurRevBoard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull
	private String intmNm;				// 상품명
	
	@NotNull
	@Column(length = 100)
	private String boardTitle;			// 글제목

	@NotNull
	private String boardDate;			// 작성일
	
	@NotNull
	@Column(length = 1000)
	private String boardDetail;			// 글내용
	
	private String writer;

	private Long attachmentId;			// 첨부파일
	
	@ManyToOne(fetch = FetchType.LAZY)
	private CustBas custBas;			// 작성자

	@Builder
	public PurRevBoard(long id, String intmNm, String boardTitle, String boardDate,
		 String boardDetail, String writer, Long attachmentId, CustBas custBas) {
		
		this.id = id;
		this.intmNm = intmNm;
		this.boardTitle = boardTitle;
		this.boardDate = boardDate;
		this.boardDetail = boardDetail;
		this.writer = writer;
		this.attachmentId = attachmentId;
		this.custBas = custBas;
	}
	
	
	
	
}
