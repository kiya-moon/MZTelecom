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
var modal;			
var openModalBtn;	// 모달창 오픈버튼
var opnModalPg; 	// 모달창 오픈페이지(PW: 비밀번호 찾기 , ID: 아이디 찾기)
var modalOpnYN;		// 모달 오픈 여부
var emailChkYn;		// 이메일 형식 체크여부
var email;			// 이메일 값

/*이메일 발송 메시지 Start*/

/*modal 열기 Start*/
pwOpnModalBtn.addEventListener("click", () => {
	
	openModalBtn = 	pwOpnModalBtn;
	opnModalPg = "PW";
	
	openModal();
	
});

idOpnModalBtn.addEventListener("click", () => {
	
	openModalBtn = idOpnModalBtn;
	opnModalPg = "ID";
	
	openModal();
	
});
/*modal 닫기 Start*/

/*modal 닫기 공통*/
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


// input 요소 또는 select 요소의 값이 변경될 때마다 이벤트를 처리합니다.
if(opnModalPg == "PW")
{
	pwEmailInput.addEventListener('input', updateEmailText);
	pwDomainSelect.addEventListener('change', updateEmailText);
}
else if(opnModalPg == "ID")
{
	idEmailInput.addEventListener('input', updateEmailText);
	idDomainSelect.addEventListener('change', updateEmailText);
}

// modal open 공통 함수
function openModal(){
	
	if(opnModalPg == "PW")
	{
		resetPw();

		modal = findPwModal;
		
		if(modalOpnYN == "Y" && emailChkYn =="Y")
		{
			modal.style.display = "block";
		}
	}
	else if(opnModalPg == "ID")
	{
		findId();
		modal = findIdModal;
	
		if(modalOpnYN == "Y" && emailChkYn =="Y")  
		{
			modal.style.display = "block";
		}
	}
	
	updateEmailText();
}
/* modal 공통 End*/		

/*비밀번호 재설정 Start*/
// 비밀번호 재설정 함수
function resetPw(){
	
	console.log("resetPw 들어오냐");
	
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
			console.log("true");
		}
		else
		{
			emailChkYn = "N";
			console.log("false");
			alert("다시 확인 해주세요.");
		}
    }

	console.log("modalOpnYN ::" + modalOpnYN);
	console.log("emailChkYn ::" + emailChkYn);
	
}
/*비밀번호 재설정 End*/

/*아이디 찾기 Start*/
function findId(){
	
	var custNm = document.getElementById("custNm");
	var custNmValue = custNm.value;
	
	console.log(custNm.value);
	console.log("findId 들어오냐");
	
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
			console.log("true");
		}
		else
		{
			emailChkYn = "N";
			console.log("false");
			alert("다시 확인 해주세요.");
		}
    }
}
/*아이디 찾기 End*/

/*이메일 발송 메시지 Start*/
// 이메일 텍스트 업데이트 함수 정의
function updateEmailText() {
    // input 요소와 select 요소에서 값을 가져와서 이메일 주소를 구성합니다.
	if(opnModalPg == "PW")
	{
		email = pwEmailInput.value + '@' + pwDomainSelect.value;
		console.log("비밀번호찾기 ::: " + email);
   	 	// 이메일 주소를 pw_email_send 요소에 설정합니다.
    	pwEmailSendElement.textContent = '임시 비밀번호를 ' + email + ' 로 발송 했습니다.' ;
	}
	else if(opnModalPg == "ID")
	{
		email = idEmailInput.value + '@' + idDomainSelect.value;
		console.log("id찾기 ::: " + email);
   	 	// 아이디를 id_msg 요소에 설정합니다.
    	idSendElement.textContent = '"아무개"님의 아이디는 ' + "admin" + ' 입니다.' ;
	}
    
}
/*이메일 발송 메시지 End*/


/* damin */

const UpdateBtn = document.querySelector('.update-btn');

UpdateBtn.addEventListener("click", () => {
	
	openModal();
	
});
