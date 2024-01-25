package com.team.mztelecom.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class TemporarySaveDTO {
	
	List<Long>		deleteFileId;	// fileId 임시저장
	List<String>	orderTmp;		// 주문내역 임시저장
	String 			tmpIntmModelId;	// 대표기기모델 임시저장 
	
	// fileId 임시저장 메서드
    public List<Long> getDeleteFileId() {
        if (deleteFileId == null) {
            deleteFileId = new ArrayList<>();
        }
        return deleteFileId;
    }
	
	// 초기화 메서드
    public void clear() {
    	deleteFileId.clear();
    }
	
    public List<String> getOrderTmp() {
        if (orderTmp == null) {
        	orderTmp = new ArrayList<>();
        }
        return orderTmp;
    }
    
    public String getTmpIntmModelId() {
    	if(tmpIntmModelId == null) {
    		tmpIntmModelId = "";
    	}
    	
    	return tmpIntmModelId;
    }
	
    

}
