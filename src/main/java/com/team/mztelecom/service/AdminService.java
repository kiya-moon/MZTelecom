package com.team.mztelecom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.team.mztelecom.domain.CustBas;
import com.team.mztelecom.domain.IntmBas;
import com.team.mztelecom.domain.IntmImg;
import com.team.mztelecom.domain.QnA;
import com.team.mztelecom.dto.InquiryCustDTO;
import com.team.mztelecom.dto.IntmBasDTO;
import com.team.mztelecom.dto.IntmImgDTO;
import com.team.mztelecom.dto.QnADTO;
import com.team.mztelecom.repository.AdminRepository;
import com.team.mztelecom.repository.ImgRepository;
import com.team.mztelecom.repository.ProductRepository;
import com.team.mztelecom.repository.QnARepository;
import com.team.util.StringUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminService {
	private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
	private final AdminRepository adminRepository;
	
	private final QnARepository qnARepository;

	private final ProductRepository productRepository;

	private final ImgRepository imgRepository;

	// 1. 사용자 정보 담기
	public List<InquiryCustDTO> getCustInfoList() {
		logger.debug("Admin 서비스");

		List<CustBas> custBasList = adminRepository.findAllCustInfo();
		List<InquiryCustDTO> inquiryCustDTO = new ArrayList<>();

		for (CustBas custBas : custBasList) {
			logger.debug("inquiryCustDTO :: " + StringUtil.toString(inquiryCustDTO));

			InquiryCustDTO result = new InquiryCustDTO(custBas.getCustId(), custBas.getCustNm(),
					custBas.getCustBirth(), custBas.getCustNo(), custBas.getCustSex(),
					custBas.getCustAddress(), custBas.getCustEmail(), custBas.getIntmPurStusYn(),
					custBas.getCreateDate());

			inquiryCustDTO.add(result);
		}

		logger.debug("inquiryCustDTO :: " + StringUtil.toString(inquiryCustDTO));

		return inquiryCustDTO;
	}

	public Page<InquiryCustDTO> getCustInfoPage(String keyword, Pageable pageable) {
		if (StringUtils.hasText(keyword)) {
			return adminRepository.findByCustNmContaining(keyword, pageable).map(this::mapToDTO);
		} else {
			return adminRepository.findAll(pageable).map(this::mapToDTO);
		}
	}

	private InquiryCustDTO mapToDTO(CustBas custBas) {
		return InquiryCustDTO.builder().custId(custBas.getCustId()).custNm(custBas.getCustNm()).custBirth(custBas.getCustBirth())
				.custNo(custBas.getCustNo()).custSex(custBas.getCustSex()).custAddress(custBas.getCustAddress())
				.custEmail(custBas.getCustEmail()).intmPurStusYn(custBas.getIntmPurStusYn()).createDate(custBas.getCreateDate()).build();
	}

	// 2. 상품 정보 담기
	public void addProduct(IntmBasDTO intmBasDTO, List<IntmImgDTO> inList) {
		logger.debug("상품 담기 서비스 :: ");
		
		logger.debug(" inList ::: " + StringUtil.toString(inList.get(0)));
		
		for(IntmImgDTO intmImgDTO : inList) {
			
			IntmImg IntmImgAdd = IntmImg.builder()
					.intmNm(intmImgDTO.getIntmNm())
					.imgName(intmImgDTO.getImgName())
					.imgDetailNm(intmImgDTO.getImgDetailNm())
					.imgPath(intmImgDTO.getImgPath())
					.imgDetailPath(intmImgDTO.getImgDetailPath())
					.build();
			
			imgRepository.save(IntmImgAdd);
		}
		
		List<IntmImg> outIntmImg = imgRepository.findAll();
		
		IntmBas intmBasAdd =  IntmBas.builder()
				.intmModelColor(intmBasDTO.getIntmModelColor())
				.intmNm(intmBasDTO.getIntmNm())
				.intmKorNm(intmBasDTO.getIntmKorNm())
				.intmGB(intmBasDTO.getIntmGB())
				.intmPrice(intmBasDTO.getIntmPrice())
				.intmImgs(outIntmImg)
				.fee(intmBasDTO.getFee())
				.createdAt(intmBasDTO.getCreatedAt())
				.build();
		
		productRepository.save(intmBasAdd);
	}
	
	// 3. 문의 정보 담기
	public void deleteQna(QnADTO inQnADTO) {
		
		QnA inQnA = inQnADTO.toEntity();
		
		Optional<QnA> outQnA = qnARepository.findById(inQnA.getId());
		logger.debug("outQnA :: " + StringUtil.toString(outQnA.get()));
		
		qnARepository.deleteById(outQnA.get().getId());
		
		
	}
	
	

}
