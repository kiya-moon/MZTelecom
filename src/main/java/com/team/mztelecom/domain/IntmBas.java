package com.team.mztelecom.domain;

import java.util.List;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class IntmBas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Convert(converter = StringListConverter.class)
    private List<String> intmModelColor;	// 기기색상
    
    @NotNull
    private String intmNm;					// 단말기 이름
    
    @NotNull
    private String intmKorNm;				// 단말기 한글 이름
    
    @NotNull
    @Convert(converter = StringListConverter.class)
    private List<String> intmGB;			// 기기 용량
    
    @NotNull
    @Convert(converter = StringListConverter.class)
    private List<String> intmPrice;			// 기기 값

    private boolean isLiked;				// 찜하기

    @OneToMany(mappedBy = "intmBas")	
    private List<IntmImg> intmImgs;			// 상품 이미지
    
    
    @Builder
	public IntmBas(Long id, List<String> intmModelColor, String intmNm, String intmKorNm, List<String> intmGB,
			List<String> intmPrice, boolean isLiked, List<IntmImg> intmImgs) {
		
    	this.id = id;
		this.intmModelColor = intmModelColor;
		this.intmNm = intmNm;
		this.intmKorNm = intmKorNm;
		this.intmGB = intmGB;
		this.intmPrice = intmPrice;
		this.isLiked = isLiked;
		this.intmImgs = intmImgs;
	}

}
