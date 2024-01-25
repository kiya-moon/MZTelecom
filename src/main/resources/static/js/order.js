function daumPostCode() {
	new daum.Postcode({
		oncomplete: function(data) {
			const isRoadAddr = data.userSelectedType === 'R';
			var address = isRoadAddr ? data.roadAddress : data.jibunAddress;

			if (data.bname) {
				address += ' (' + data.bname + ') ';
			}

			if (data.buildingName) {
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

	const AllPhone = ChPhone1.concat("-", ChPhone2, "-", ChPhone3);
	console.log(AllPhone);

	if (ChName == '') {
		alert("이름을 입력해주세요.");

	} else if (ChResidentNum1 == '' || ChResidentNum2 == '') {
		alert("주민등록번호를 입력해주세요.");

	} else if (ChPhone1 == '' || ChPhone2 == '' || ChPhone3 == '') {
		alert("핸드폰 번호를 입력해주세요.");

	} else if (ChZip == '') {
		alert("우편 번호를 입력해주세요.");

	} else if (ChAddress1 == '' || ChAddress2 == '') {
		alert("주소를 입력해주세요.");

	} else if (!ChBox.checked) {
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
					.catch(function(error) {
						alert("에러" + error);
					})
			} else {
				alert("인증에 실패하였습니다. 에러 내용: " + rsp.error_msg);
			}
		});

	}

}

function requestPay() {
	const OrderIntmName = document.getElementById('OrderIntmName').value;
	const OrderColor = document.getElementById('OrderColor').value;
	const OrderCpcty = document.getElementById('OrderCpcty').value;
	const OrderPrice = document.getElementById('OrderPrice').value;
	const OrderAddress1 = document.getElementById('OrderAddress1').value;
	const OrderAddress2 = document.getElementById('OrderAddress2').value;
	const OrderName = document.getElementById('OrderName').value;
	const OrderPhone = document.getElementById('OrderPhone').value;
	
	const Alladdress = OrderAddress1 + OrderAddress2;
	
	var IMP = window.IMP;
	IMP.init('imp05087430');

	IMP.request_pay(
		{
			pg: 'kakaopay',	//html5_inicis							// PG값
			pay_method: "card",										// 결제 방법
			merchant_uid: 'merchant_' + new Date().getTime(), 		// 주문번호
			name: OrderIntmName,									// 상품 명
			amount: OrderPrice,										// 금액
			// (buyer_ 부분은 꼭 작성하지 않아도된다. (선택사항))
			buyer_name: OrderName,									// 주문자명
			buyer_tel: OrderPhone,									// 주문자 연락처
			buyer_addr: Alladdress, 								// 주문자 우편번호
		},
		function(rsp) {
			if (rsp.success) {
				$.ajax({
					url: "/validation/" + rsp.imp_uid,
					method: "POST",
					headers: { 
						"Content-Type": "application/json",
						[csrfHeader]: csrfToken
					},
					data: JSON.stringify({
						"address": Alladdress			// 주소
					})
				}).done(function(res) {
					console.log(res);
						$.ajax({
			                method: "POST",
			                url: "/order/payment",
			                headers: { 
								"Content-Type": "application/json",
								[csrfHeader]: csrfToken
							},
							data: JSON.stringify({
								"paymentUid": rsp.imp_uid,     // 결제 고유번호
								"orderUid": rsp.merchant_uid,  // 주문번호
								"intmKorNm": OrderIntmName,
								"price": OrderPrice,
							})
			            })
					
					alert('결제가 완료되었습니다.');
					window.location.href = "/";
				})
			} else {
				alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
			}
		}
	);
}