package com.team.mztelecom.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
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

    private int wishCnt;					// 찜한 갯수

    @OneToMany(mappedBy = "intmBas")	
    private List<IntmImg> intmImgs;			// 상품 이미지
    
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "intmBas")
	private List<CustWish> CustWish;
	
	private String fee;
    
    
    @Builder
	public IntmBas(Long id, List<String> intmModelColor, String intmNm, String intmKorNm, List<String> intmGB,
			List<String> intmPrice, int wishCnt, List<IntmImg> intmImgs, String fee) {
		
    	this.id = id;
		this.intmModelColor = intmModelColor;
		this.intmNm = intmNm;
		this.intmKorNm = intmKorNm;
		this.intmGB = intmGB;
		this.intmPrice = intmPrice;
		this.wishCnt = wishCnt;
		this.intmImgs = intmImgs;
		this.fee = fee;
	}
    
    public void UpdateWishd(int wishCnt) {
    	this.wishCnt = wishCnt;
    }

}
