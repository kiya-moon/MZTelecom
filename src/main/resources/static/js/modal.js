// id, pw 찾기 관련 modal 변수
var pwOpnModalBtn = document.getElementById("resetPwModalBtn");		// 비밀번호찾기 버튼
var idOpnModalBtn = document.getElementById("findIdBtn");			// 아이디찾기 버튼
var findPwModal= document.getElementById("findPwModal");			// 비밀번호찾기 모달창
var findIdModal= document.getElementById("findIdModal");			// 아이디찾기 모달창
var closeModalBtn = document.getElementById("closeModalBtn");		// 모달창 닫기 버튼
var pwEmailSendElement = document.querySelector('.pw_email_msg');	// 비밀번호찾기 메시지
var idSendElement = document.querySelector('.id_msg');				// 아이디찾기 메시지
var pwEmailInput = document.querySelector('#pw-email-box');			// 비밀번호찾기 이메일 인풋
var idEmailInput = document.querySelector('#id-email-box');			// 아이디찾기 이메일 인풋
var pwDomainSelect = document.querySelector('#pw-domain-list');		// 비밀번호찾기 이메일 도메인
var idDomainSelect = document.querySelector('#id-domain-list');		// 아이디찾기 이메일 도메인
var emailChk = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*$/;					// 이메일 정규식
var openModalBtn;	// 모달창 오픈버튼
var opnModalPg; 	// 모달창 오픈페이지(PW: 비밀번호 찾기 , ID: 아이디 찾기)
var modalOpnYN;		// 모달 오픈 여부
var emailChkYn;		// 이메일 형식 체크여부

/*modal 열기 Start*/
pwOpnModalBtn.addEventListener("click", (e) => {
	
	console.log('modal 열기');
	
	e.preventDefault();
	
	openModalBtn = 	pwOpnModalBtn;
	opnModalPg = "PW";
	
	openModalChk();
	
});

idOpnModalBtn.addEventListener("click", (e) => {
	
	console.log('modal 열기');
	
	e.preventDefault();
	
	openModalBtn = idOpnModalBtn;
	opnModalPg = "ID";
	
	openModalChk();
	
});
/*modal 열기 End*/

/*modal 닫기 Start*/
// 모달 닫기 버튼 클릭 시
var closeButtons = document.getElementsByClassName("close");
for (var i = 0; i < closeButtons.length; i++) {
    closeButtons[i].addEventListener("click", function() {
        var modal = this.parentElement.parentElement;
        modal.style.display = "none";
    });
}

// 모달 바깥 영역 클릭 시 닫기
window.addEventListener("click", function(event) {
    if (event.target.classList.contains("modal")) {
        event.target.style.display = "none";
    }
});
/*modal 닫기 End*/


// input 요소 또는 select 요소의 값이 변경될 때마다 이벤트를 처리합니다.
if(opnModalPg == "PW")
{
	pwEmailInput.addEventListener('input', findPwModalOpn);
	pwDomainSelect.addEventListener('change', findPwModalOpn);
}
else if(opnModalPg == "ID")
{
	idEmailInput.addEventListener('input', findIdModalOpn);
	idDomainSelect.addEventListener('change', findIdModalOpn);
}

/* id찾기/pw찾기 처리 구분 Start*/
function openModalChk(){
	
	if(opnModalPg == "PW")
	{
		console.log('비밀번호 찾기');
		findPwInpChk();

		modal = findPwModal;
		
		if(modalOpnYN == "Y" && emailChkYn =="Y")
		{
			modal.style.display = "block";
		}
		
		findPwModalOpn();
	}
	else if(opnModalPg == "ID")
	{
		console.log('아이디 찾기');
		findIdInpChk();
		
		modal = findIdModal;
	
		if(modalOpnYN == "Y" && emailChkYn =="Y")  
		{
			modal.style.display = "block";
		}
		
		findIdModalOpn();
	}
	
}
/*id찾기 / pw찾기 처리 구분 End*/

/*비밀번호 찾기 입력값 체크 Start*/
function findPwInpChk(){
	
	console.log('비밀번호 찾기 입력값 체크');
	
	var custId = document.getElementById("custId");
	var custIdValue = custId.value;
	
	// 아이디 입력 체크	
	if (isNull(custIdValue)) 
	{
		alert("아이디를 입력 해주세요.");
		modalOpnYN = "N";
    }
	else 
	{
		modalOpnYN = "Y";
		// 이메일 정규식 체크
		if(emailChk.test(pwEmailInput.value) && !isNull(pwEmailInput))
		{
			emailChkYn = "Y";
		}
		else
		{
			emailChkYn = "N";
			alert("다시 확인 해주세요.");
		}
    }
}
/*비밀번호 찾기 입력값 체크 End*/

/*아이디 찾기 입력값 체크 Start*/
function findIdInpChk(){
	
	console.log('아이디 찾기 입력값 체크');
	
	var custNm = document.getElementById("custNm");
	var custNmValue = custNm.value;
	
	console.log(custNm.value);
	
	if (isNull(custNmValue)) 
	{
		alert("이름을 입력 해주세요.");
		modalOpnYN = "N";
    } 
	else 
	{
		modalOpnYN = "Y";
		
		// 이메일 정규식 체크
		if(emailChk.test(idEmailInput.value) && !isNull(idEmailInput))
		{
			emailChkYn = "Y";
		}
		else
		{
			emailChkYn = "N";
			alert("다시 확인 해주세요.");
		}
    }
}
/*아이디 찾기 입력값 체크 End*/

/*아이디 찾기 ajax*/
function findIdModalOpn() {
	
	console.log("아이디 찾기 controller 통신");

	var listEmpYn;
	var msg;
	var custId;
	var custNm;
    var httpRequest;
    
    httpRequest = new XMLHttpRequest();
    
    console.log(httpRequest);
    
    httpRequest.onreadystatechange = () => {
		if (httpRequest.readyState === XMLHttpRequest.DONE){
			if(httpRequest.status === 200) {
				console.log("성공");
				
				var response = JSON.parse(httpRequest.responseText);
				custId = response.custId;
				custNm = response.custNm;
				listEmpYn = response.listEmpYn;
				console.log("custId: " + custId);
				console.log("custNm: " + custNm);
				console.log("listEmpYn: " + listEmpYn);
				
				if(!isNull(listEmpYn))
				{
					if(listEmpYn == 'Y')
					{
						msg = "찾는 고객 정보가 없습니다."
						idSendElement.textContent = msg ;
					}
					else
					{
						idSendElement.textContent = custNm + '님의 아이디는 ' + custId + ' 입니다.' ;
					}
				}
			}
			else {
				alert("시스템 오류. 다시 시도 해주세요.");
			}
		}
	};
	
	// 넘겨줄 데이터
	var formData = new FormData(document.getElementById('find-id-form'));
    
    // 서버로 데이터를 전송
    httpRequest.open('POST', 'findId.do');
    httpRequest.send(formData);
}

/*비밀번호 찾기 ajax*/
function findPwModalOpn(){
	
	console.log("비밀번호 controller 통신 시작");
	
	var listEmpYn;
	var msg;
	var email;
    var httpRequest;

    httpRequest = new XMLHttpRequest();
    console.log(httpRequest);
    
    httpRequest.onreadystatechange = () => {
		if (httpRequest.readyState === XMLHttpRequest.DONE){
			if(httpRequest.status === 200) {
				console.log("성공");
				
				var response = JSON.parse(httpRequest.responseText);
				email = response.cust_email;
				listEmpYn = response.listEmpYn;
				console.log("email ::: " + email);
				console.log("listEmpYn ::: " + listEmpYn);
				
				if(!isNull(listEmpYn))
				{
					if(listEmpYn == 'Y')
					{
						msg = "찾는 고객 정보가 없습니다."
						pwEmailSendElement.textContent = msg ;
					}
					else
					{
						pwEmailSendElement.textContent = '임시 비밀번호를 ' + email + ' 로 발송 했습니다.' ;
					}
				}
			}
			else {
				alert("시스템 오류. 다시 시도 해주세요.");
			}
		}
	};
	
	// 넘겨줄 데이터
	var formData = new FormData(document.getElementById('find-pw-form'));
    
    // 서버로 데이터를 전송
    httpRequest.open('POST', 'findPw.do');
    httpRequest.send(formData);
}


/* admin */
const UpdateBtn = document.querySelector('.update-btn');

UpdateBtn.addEventListener("click", () => {
	
	openModalChk();
	
});
