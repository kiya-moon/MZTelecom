package com.team.mztelecom.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class IntmImg {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@NotNull
    private String intmNm;
	
	@NotNull
    private String imgName;
	
	@NotNull
    private String imgDetailNm;

	@NotNull
    private String imgPath;
	
	@NotNull
    private String imgDetailPath;

    @ManyToOne
    private IntmBas intmBas;
    
    @Builder
	public IntmImg(Long id, String intmNm, String imgName, String imgDetailNm,
			String imgPath, String imgDetailPath, IntmBas intmBas) {
    	
		this.id = id;
		this.intmNm = intmNm;
		this.imgName = imgName;
		this.imgDetailNm = imgDetailNm;
		this.imgPath = imgPath;
		this.imgDetailPath = imgDetailPath;
		this.intmBas = intmBas;
	}
    
    
    public void updateImage(String intmNm, String imgName,
			String imgPath, String imgDetailNm, String imgDetailPath) {
    	
		this.intmNm = intmNm;
		this.imgName = imgName;
		this.imgPath = imgPath;
		this.imgDetailNm = imgDetailNm;
		this.imgDetailPath = imgDetailPath;
	}
    
    public void updateImg (String intmNm, String imgName, String imgPath) {
    	
		this.intmNm = intmNm;
		this.imgName = imgName;
		this.imgPath = imgPath;
	}
    
    public void updateImgDetail (String intmNm, String imgDetailNm, String imgDetailPath) {
    	
		this.intmNm = intmNm;
		this.imgDetailNm = imgDetailNm;
		this.imgDetailPath = imgDetailPath;
	}


    
}
