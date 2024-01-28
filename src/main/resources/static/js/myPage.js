const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

window.onload = function(){
	// 주문내역 페이지 기본 날짜 설정
	const startDate = new Date();
	const endDate = new Date();
	
	startDate.setMonth(endDate.getMonth() - 1);
	
	document.getElementById('startDate').value = startDate.toISOString().substring(0, 10);
	document.getElementById('endDate').value = endDate.toISOString().substring(0, 10);
	
}

// 3개월 버튼 클릭 시
function setThreeMonth() {
	const startDate = new Date();
	const endDate = new Date();
	
	startDate.setMonth(endDate.getMonth() - 3);
	
	document.getElementById('startDate').value = startDate.toISOString().substring(0, 10);
	document.getElementById('endDate').value = endDate.toISOString().substring(0, 10);
}

// 6개월 버튼 클릭 시
function setSixMonth() {
	const startDate = new Date();
	const endDate = new Date();
	
	startDate.setMonth(endDate.getMonth() - 6);
	
	document.getElementById('startDate').value = startDate.toISOString().substring(0, 10);
	document.getElementById('endDate').value = endDate.toISOString().substring(0, 10);
}

// 1년 버튼 클릭 시
function setOneYear() {
	const startDate = new Date();
	const endDate = new Date();
	
	startDate.setFullYear(endDate.getFullYear() - 1);
	
	document.getElementById('startDate').value = startDate.toISOString().substring(0, 10);
	document.getElementById('endDate').value = endDate.toISOString().substring(0, 10);
}

// 회원정보수정

	// 비밀번호 확인 이벤트 발생
	const handlePasswordCheck = document.querySelector('#chkPw')
	const custPassword = document.getElementById('updatePw')
	const custPasswordCheck = document.getElementById('chkPw')
	const mismatchMessage = document.querySelector('.mismatch-message')
	const matchMessage = document.querySelector('.match-message')
	
	handlePasswordCheck.addEventListener('keyup', function(){
		if (isMatch(custPassword.value, custPasswordCheck.value)) {
			mismatchMessage.classList.add('hide')
			matchMessage.classList.remove('hide')
			custPassword.style.borderColor = '#33A188'
			custPasswordCheck.style.borderColor = '#33A188'
		}
		else {
			mismatchMessage.classList.remove('hide')
			matchMessage.classList.add('hide')
			custPassword.style.borderColor = 'red'
			custPasswordCheck.style.borderColor = 'red'
		}
	})
	
	// 비밀번호 체크
	function isMatch(password1, password2) {
		return password1 === password2
	}


// 핸드폰번호 수정
function phoneNumModify() {
	console.log("phone함수호출")
	const phoneModify = document.getElementById('phone-modify-container');
	phoneModify.classList.toggle("modify");
}

// 비밀번호 수정
function modifyPW() {
	console.log("pw함수호출")
	const pwModify = document.getElementById('pw-modify-container');
	pwModify.classList.toggle("modify");
}

// 회원정보 수정
function updateCustInfo(){
	
	const formData = new FormData();
	
	var custId = document.getElementById('custId').value;
	var updatePw = document.getElementById('updatePw').value;	// 수정할 비밀번호
	var existiPw = document.getElementById('existi-pw').value;	// 수정할 비밀번호
	var chkPw = document.getElementById('chkPw').value;			// 수정할 비밀번호 체크
	var phoneStart = document.getElementById('phoneStart').value;			
	var phoneMiddle = document.getElementById('phoneMiddle').value;			
	var phoneEnd = document.getElementById('phoneEnd').value;	
	var phoneNo;
	var updatePwYn = 'N';		
	var updateNoYn = 'N';
	var updateChk;
	
	// 변경할 비밀번호 입력 유무 및 패턴 체크
	if(!isNull(updatePw) && !isNull(chkPw))
	{
		
//		console.log(existiPw);
		
//		if(existiPw === updatePw)
//		{
//			alert('기존 비밀번호랑 다르게 입력해주세요.');
//			return;
//		}
		
	    // 패턴 체크
	    var passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,20}$/;
	    
	    if (!passwordPattern.test(updatePw)) 
	    {
	        alert('비밀번호는 최소 8자 ~ 20자, 문자,숫자,특수문자 조합이어야 합니다.');
	        return;
	    }
	    
		updatePwYn = 'Y';
	}
	
	// 핸드폰 번호 입력 유무 및 조합
	if(!isNull(phoneStart) && !isNull(phoneMiddle) && !isNull(phoneEnd))
	{
		phoneNo = phoneStart.concat('-').concat(phoneMiddle).concat('-').concat(phoneEnd)
		
		updateNoYn = 'Y';
	}
	
	// 비밀번호, 핸드폰 번호 변경시 formData 세팅
	if(updatePwYn == 'Y')
	{
		if(updateNoYn == 'Y')	// 비밀번호 변경o , 전화번호 변경o
		{
			updateChk = true;
			formData.append('custPassword', updatePw);
			formData.append('custNo', phoneNo);
		}
		else					// 비밀번호 변경o , 전화번호 변경x
		{
			updateChk = true;
			formData.append('custPassword', updatePw);
		}
	}
	else if(updatePwYn == 'N')
	{
		if(updateNoYn == 'Y')	// 비밀번호 변경x , 전화번호 변경o
		{
			updateChk = true;
			formData.append('custNo', phoneNo);
		}
		else					// 비밀번호 변경x , 전화번호 변경x
		{
			updateChk = false;	
		}
	}
	
	// 서버 통신
	if(updateChk)
	{
		
		formData.append('custId', custId);
		
		fetch('/update/custInfo', {
		method: 'POST',
		headers: {
	    	[csrfHeader]: csrfToken,
		},
			body: formData
    	})
		.then(function(response) {
			alert('회원 정보가 수정되었습니다.');
			location.reload();
    		return response.text(); // 서버의 응답 텍스트를 반환
		})
    	.catch(function(error) {
			console.log(error);
        	alert('잠시 후 다시 실행 해주세요');
    	});
	}
	
}


// 회원탈퇴 버튼
function withdrawal(){
	
	var custId = document.getElementById('custId').value;
	
	const formData = new FormData();
	formData.append('custId', custId);
	 
	fetch('/custWithdrawal', {
		method: 'POST',
		headers: {
	    	[csrfHeader]: csrfToken,
		},
		body: formData
    })
	.then(function(response) {
		alert('회원 탈퇴가 되었습니다.');
    	window.location.href = '/logout'; // 예를 들어, 서버에서 받은 경로로 리다이렉트
    	return response.text(); // 서버의 응답 텍스트를 반환
	})
    .catch(function(error) {
		console.log(error);
        alert('잠시 후 다시 실행 해주세요');
    });
	
}

	