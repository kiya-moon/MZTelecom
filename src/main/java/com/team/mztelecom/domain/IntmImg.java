package com.team.mztelecom.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class IntmImg {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
    private String intmNm;
	
	@NotNull
    private String repIntmModelId;
	
	@NotNull
    private String imgName;

	@NotNull
    private String imgPath;

    @ManyToOne
    private IntmBas intmBas;

    @Builder
	public IntmImg(Long id, String intmNm, String repIntmModelId, String imgName,
			String imgPath, IntmBas intmBas) {
    	
		this.id = id;
		this.intmNm = intmNm;
		this.repIntmModelId = repIntmModelId;
		this.imgName = imgName;
		this.imgPath = imgPath;
		this.intmBas = intmBas;
	}


}
