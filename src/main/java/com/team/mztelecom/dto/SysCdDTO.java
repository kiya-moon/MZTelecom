package com.team.mztelecom.dto;

import java.time.LocalDateTime;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.domain.SysCdBas;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SysCdDTO {

	Long id;
    
	String sys_cd_group_id;		//시스템코드그룹아이디
	
	String sys_cd_id;			//시스템코드아이디
	
	LocalDateTime efct_st_date;		//유효시작일자
	
	LocalDateTime efct_fns_date;		//유효종료일자
	
	String rfrn_val1;			//참고값1
	
	String rfrn_val2;			//참고값2
	
	String rfrn_val3;			//참고값3
	
	String rfrn_val4;			//참고값4
	
	String rfrn_val5;			//참고값5

	@Builder
	public SysCdDTO(Long id, String sys_cd_group_id, String sys_cd_id, LocalDateTime efct_st_date,
			LocalDateTime efct_fns_date, String rfrn_val1, String rfrn_val2, String rfrn_val3, String rfrn_val4,
			String rfrn_val5) {
		this.id = id;
		this.sys_cd_group_id = sys_cd_group_id;
		this.sys_cd_id = sys_cd_id;
		this.efct_st_date = efct_st_date;
		this.efct_fns_date = efct_fns_date;
		this.rfrn_val1 = rfrn_val1;
		this.rfrn_val2 = rfrn_val2;
		this.rfrn_val3 = rfrn_val3;
		this.rfrn_val4 = rfrn_val4;
		this.rfrn_val5 = rfrn_val5;
	}
	
	// dto -> entity
	public SysCdBas toEntity() {
		return SysCdBas.builder()
					.sys_cd_group_id(sys_cd_group_id)
					.sys_cd_id(sys_cd_id)
					.efct_st_date(efct_st_date)
					.efct_fns_date(efct_fns_date)
					.rfrn_val1(rfrn_val1)
					.rfrn_val2(rfrn_val2)
					.rfrn_val3(rfrn_val3)
					.rfrn_val4(rfrn_val4)
					.rfrn_val5(rfrn_val5)
					.build();
	}
	
	
}
