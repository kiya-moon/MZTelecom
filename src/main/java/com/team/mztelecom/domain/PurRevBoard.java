package com.team.mztelecom.domain;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

	@OneToMany(mappedBy = "purRevBoard", cascade = CascadeType.REMOVE) //게시글 삭제시 첨부파일 먼저 삭제 되도록
	private List<PurRevAttachment> purRevAttachments;			// 첨부파일
	
	@ManyToOne(fetch = FetchType.LAZY)
	private CustBas custBas;			// 작성자

	@Builder
	public PurRevBoard(long id, String intmNm, String boardTitle, String boardDate,
		 String boardDetail, String writer, PurRevAttachment purRevAttachment, CustBas custBas) {
		
		this.id = id;
		this.intmNm = intmNm;
		this.boardTitle = boardTitle;
		this.boardDate = boardDate;
		this.boardDetail = boardDetail;
		this.writer = writer;
		this.custBas = custBas;
	}
	
	public void updatePurRev(String boardTitle, String boardDetail, List<PurRevAttachment> purRevAttachments) {
        this.boardTitle = boardTitle;
        this.boardDetail = boardDetail;
        this.purRevAttachments = purRevAttachments;
    }
	
	// 첨부파일 넣어주는 곳
	public void addPurRevAttachment(PurRevAttachment attachment) {
        if (purRevAttachments == null) {
        	purRevAttachments = new ArrayList<>();
        }
        purRevAttachments.add(attachment);
        attachment.setPurRevBoard(this);
    }
	
	
	
}
