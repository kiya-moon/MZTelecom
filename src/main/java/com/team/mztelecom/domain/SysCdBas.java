package com.team.mztelecom.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SysCdBas {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
    
	private String sys_cd_group_id;		//시스템코드그룹아이디
	
	private String sys_cd_id;			//시스템코드아이디
	
	private LocalDateTime efct_st_date;		//유효시작일자
	
	private LocalDateTime efct_fns_date;		//유효종료일자
	
	private String rfrn_val1;			//참고값1
	
	private String rfrn_val2;			//참고값2
	
	private String rfrn_val3;			//참고값3
	
	private String rfrn_val4;			//참고값4
	
	private String rfrn_val5;			//참고값5

	
	@Builder
	public SysCdBas(Long id, String sys_cd_group_id, String sys_cd_id, LocalDateTime efct_st_date, LocalDateTime efct_fns_date,
			String rfrn_val1, String rfrn_val2, String rfrn_val3, String rfrn_val4, String rfrn_val5) {
		
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
	
	
	
}
