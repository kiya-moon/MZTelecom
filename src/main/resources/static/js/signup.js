const cust_id = document.querySelector('#cust-id');
const cust_name = document.querySelector('#cust-nm');
const cust_email = document.querySelector('#cust-email');
const cust_password = document.querySelector('#cust-password');
const cust_passwordCheck = document.querySelector('#password-check');

const sign_email = document.querySelector('#sign_email');
const sign_name = document.querySelector('#sign_name');
const sign_password = document.querySelector('#sign_password');
const sign_password_check = document.querySelector('#sign_password_check');
const sign_phone = document.querySelector('#sign_phone');
const sign_addr = document.querySelector('#sign_addr');

const error = document.querySelectorAll('.error_next_box');

// 이름 한글 체크

// 비밀번호 8 ~ 20자리 사이
// 주민등록번호 숫자만 입력되게 만들기
// 핸드폰번호 숫자만 입력되게 만들기


// id 중복확인
var isDuplicateChecked = false; // 중복 확인 여부 저장하는 변수


document.addEventListener('DOMContentLoaded', function() {
	const signupForm = document.getElementById('signup-form');
	const signupBtn = signupForm.querySelector('[type="submit"]');
	const duplicateCheckBtn = document.querySelector('.signup-button');
	
	duplicateCheckBtn.addEventListener('click', function() {
		const custId = document.getElementById('cust-id').value.trim(); // 공백 제거
		
		// 중복 확인 버튼 클릭 시 중복 확인 실행
		if(custId !== '') {
			checkDuplicate();
		} else {
			alert("아이디를 입력해주세요");
		}
	});

	signupForm.addEventListener('submit', function(event) {
		if (!isDuplicateChecked) {
			// 중복 확인을 거치지 않고 제출 시 알림
			event.preventDefault();
			alert('중복 확인을 해주세요.');
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
});

