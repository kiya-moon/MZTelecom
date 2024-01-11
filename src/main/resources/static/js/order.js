function daumPostCode() {
    new daum.Postcode({
        oncomplete: function(data) {
			const isRoadAddr = data.userSelectedType === 'R';
			var address = isRoadAddr ? data.roadAddress : data.jibunAddress;
	
		    if(data.bname) {
				address += ' (' + data.bname + ') ';
			}
		
			if(data.buildingName) {
				address += ', ' + data.buildingName;
			}
	
	
	         // 우편번호와 주소 정보를 해당 필드에 넣는다.
	         document.getElementById('zipcode').value = data.zonecode;
	         document.getElementById("address1").value = address;
        }
    }).open();
    
    var address2Input = document.getElementById('address2');
    address2Input.removeAttribute('readonly');
}

const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");


// 포트원 본인인증
function identityVerification() {
	const ChName = document.getElementById('name').value;
    const ChResidentNum1 = document.getElementById('residentNumber1').value;
    const ChResidentNum2 = document.getElementById('residentNumber2').value;
    const ChPhone1 = document.getElementById('phone1').value;
    const ChPhone2 = document.getElementById('phone2').value;
    const ChPhone3 = document.getElementById('phone3').value;
    const ChZip = document.getElementById('zipcode').value;
    const ChAddress1 = document.getElementById('address1').value;
    const ChAddress2 = document.getElementById('address2').value;
	const ChBox = document.getElementById('agree');
	
	const AllPhone = ChPhone1.concat("-",  ChPhone2, "-", ChPhone3);
	console.log(AllPhone);
	
	if(ChName == '') {
		alert("이름을 입력해주세요.");
		
	} else if(ChResidentNum1 == '' || ChResidentNum2 == '' ) {
		alert("주민등록번호를 입력해주세요.");
		
	} else if(ChPhone1 == '' || ChPhone2 == '' || ChPhone3 == '') {
		alert("핸드폰 번호를 입력해주세요.");
		
	} else if(ChZip == '') {
		alert("우편 번호를 입력해주세요.");
		
	} else if(ChAddress1 == '' || ChAddress2 == '') {
		alert("주소를 입력해주세요.");
		
	} else if(!ChBox.checked) {
		alert("개인정보 수집 내용에 동의해주세요. ");
		
	} else {
		
		const formData = new FormData();
		formData.append('ChName', ChName);
		formData.append('AllPhone', AllPhone);
		formData.append('ChZip', ChZip);
		formData.append('ChAddress1', ChAddress1);
		formData.append('ChAddress2', ChAddress2);
		
		var IMP = window.IMP; 
		IMP.init('imp05087430');
		
		IMP.certification({ // param
			pg: 'inicis_unified.MIIiasTest',
			merchant_uid: 'merchant_' + new Date().getTime() // 주문 번호 개인적으로 설정 가능 
		}, function(rsp) { // callback
		
			if (rsp.success) {
				console.log("본인인증이 성공적으로 처리되었습니다.");
				
				fetch('/postOrder', {
					method: 'POST',
					headers: {
			            [csrfHeader]: csrfToken,
			        },
			        body: formData
				})
				.then(function(rsp) {
					window.location.href = '/order';
					
					return rsp.text();
				})
				.catch(function(error){
					alert("에러" + error);
				})
			} else {
				alert("인증에 실패하였습니다. 에러 내용: " + rsp.error_msg);
			}
		});
		
	}
	
}

/*function requestPay() {
	var IMP = window.IMP; 
	IMP.init('imp05087430');
	
	IMP.request_pay(
		{
			pg: "inicis_unified.MIIiasTest", 					// PG값
			pay_method: "card",						// 결제 방법
			merchant_uid: "57008833-33004", 		// 주문번호
			name: "테스트",							// 상품 명
			amount: 1004,							// 금액
			// (buyer_ 부분은 꼭 작성하지 않아도된다. (선택사항))
			buyer_email: "test@naver.com",			// 주문자 이메일
			buyer_name: "test",						// 주문자명
			buyer_tel: "010-1234-5678",				// 주문자 연락처
			buyer_addr: "서울특별시 강남구 삼성동", // 주문자 우편번호
			buyer_postcode: "123-456",				// 사용 시 가맹점 endpoint url 설정
		},
		function(rsp) {
			if (rsp.success) {
			//rsp.imp_uid 값으로 결제 단건조회 API를 호출하여 결제결과를 판단합니다.
				axios({
					url: '/iamport/' + rsp.imp_uid,
					method: "post",
					headers: { "Content-Type": "application/json" },
					data: {
					  imp_uid: rsp.imp_uid,
					  merchant_uid: rsp.merchant_uid
					}
				  }).then((data) => {
					alert("결제 성공");
				 })
			} else {
				alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
			}
		}
	);
}*/