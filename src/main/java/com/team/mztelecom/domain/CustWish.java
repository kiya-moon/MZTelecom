package com.team.mztelecom.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class CustWish {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="custBasId")
    private CustBas custBas;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="intmBasId")
    private IntmBas intmBas;
    

    @Builder
	public CustWish(Long id, CustBas custBas, IntmBas intmBas) {
		this.id = id;
		this.custBas = custBas;
		this.intmBas = intmBas;
	}
    
    
    
    
    
}
