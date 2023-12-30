const custId = document.querySelector('#cust-id');
const custName = document.querySelector('#cust-nm');
const custEmail = document.querySelector('#cust-email');

const custPassword = document.getElementById('cust-password');
const custPasswordCheck = document.getElementById('password-check');
const mismatchMessage = document.querySelector('mismatch-message');
// const custEmailDomain = document.getElementsById('email-domain-list');
const emailDomainBox = document.getElementsByClassName('direct-input');

const signEmail = document.querySelector('#sign_email');
const signName = document.querySelector('#sign_name');
const signPassword = document.querySelector('#sign_password');
const signPassword_check = document.querySelector('#sign_password_check');
const signPhone = document.querySelector('#sign_phone');
const signAddr = document.querySelector('#sign_addr');

let error = document.querySelectorAll('.error_next_box');

// 이름 한글 체크
// 주민등록번호 숫자만 입력되게 만들기
// 핸드폰번호 숫자만 입력되게 만들기

// id 중복확인
let isDuplicateChecked = false; // 중복 확인 여부 저장하는 변수

// 이메일 인증 여부 확인
let isCheckEmailCert = false;


document.addEventListener('DOMContentLoaded', function() {
	const signupForm = document.getElementById('signup-form');
	const signupBtn = signupForm.querySelector('[type="submit"]');
	const duplicateCheckBtn = document.querySelector('#check-duplicate');
	const sendCertBtn = document.querySelector('#email-cerification');

	duplicateCheckBtn.addEventListener('click', function() {
		const custId = document.getElementById('cust-id').value.trim(); // 공백 제거

		// 중복 확인 버튼 클릭 시 중복 확인 실행
		if (custId !== '') {
			checkDuplicate();
		} else {
			alert("아이디를 입력해주세요");
		}
	});

	sendCertBtn.addEventListener('click', function() {
		emailCertView();
		sendEmailCert();
	})

	signupForm.addEventListener('submit', function(event) {
		if (!isDuplicateChecked) {
			// 중복 확인을 거치지 않고 제출 시 알림
			event.preventDefault();
			alert('중복 확인을 해주세요.');
		}
		if (!isCheckEmailCert) {
			// 이메일 인증을 거치지 않고 제출 시 알림
			event.preventDefault();
			alert('이메일을 인증해주세요.');
		}
	});

	function checkDuplicate() {
		const custId = document.getElementById('cust-id').value;

		fetch('/checkDuplicate?custId=' + custId)
			.then(function(response) {
				return response.text();
			})
			.then(function(data) {
				const statusElement = document.getElementById('id-status');
				statusElement.innerText = data;

				if (data.includes('사용 가능한')) {
					alert(data);
					statusElement.style.color = '#33A188';
					signupBtn.disabled = false; // 가입 버튼 활성화

					return true;

				} else {
					alert(data);
					statusElement.style.color = 'red';
					signupBtn.disabled = true; // 가입 버튼 비활성화

					return false;
				}
			})

			.then(function(isDuplicate) {
				isDuplicateChecked = isDuplicate;
			})

			.catch(function(error) {
				console.log('에러 발생: ' + error);
			});
	}

	// 인증요청 클릭 시 인증번호 체크 입력 칸 보이게
	function emailCertView() {
		const handleEmailCertView = document.getElementById('emailCertNum');
		handleEmailCertView.style.display = 'block';
	}

	// 인증번호 요청
	function sendEmailCert() {
		const custEmailId = document.getElementById('email-box').value;
		const custEmailDomain = document.getElementById('email-domain-box').value;
		const custId = document.getElementById('cust-id').value;

		fetch('/checkDuplicate?custId=' + custId)
			.then(function(response) {
				return response.text();
			})
			.then(function(data) {
				const statusElement = document.getElementById('id-status');
				statusElement.innerText = data;

				if (data.includes('사용 가능한')) {
					alert(data);
					statusElement.style.color = '#33A188';
					signupBtn.disabled = false; // 가입 버튼 활성화

					return true;

				} else {
					alert(data);
					statusElement.style.color = 'red';
					signupBtn.disabled = true; // 가입 버튼 비활성화

					return false;
				}
			})

			.then(function(isDuplicate) {
				isDuplicateChecked = isDuplicate;
			})

			.catch(function(error) {
				console.log('에러 발생: ' + error);
			});
	}


});


// 비밀번호 체크
function isMatch(password1, password2) {
	return password1 === password2;
}

custPasswordCheck.onkeyup = function() {
	console.log("키보드 이벤트 발생")
	if (custPasswordCheck.value.length !== 0) {
		if (isMatch(custPassword.value, custPasswordCheck.value)) {
			mismatchMessage.classList.add('hide'); // 실패 메시지가 가려져야 함
			custPassword.style.borderColor = 'green';
			custPasswordCheck.style.borderColor = 'green';
		}
		else {
			mismatchMessage.classList.remove('hide'); // 실패 메시지가 보여야 함
			custPassword.style.borderColor = 'red';
			custPasswordCheck.style.borderColor = 'red';
		}
	}
	else {
		mismatchMessage.classList.add('hide'); // 실패 메시지가 가려져야 함
	}
};

// 이메일 직접 입력인 경우 안했나...?
directInput.onKeyup = function() { }










