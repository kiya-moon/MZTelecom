let error = document.querySelectorAll('.error_next_box')

// id 중복확인
let isDuplicateChecked = false // 중복 확인 여부 저장하는 변수

// 이메일 인증 여부 확인
let isCheckEmailCert = false


document.addEventListener('DOMContentLoaded', function() {
	const signupForm = document.getElementById('signup-form')
	const signupBtn = signupForm.querySelector('[type="submit"]')

	// 아이디 중복확인
	const duplicateCheckBtn = document.querySelector('#check-duplicate')

	duplicateCheckBtn.addEventListener('click', function() {
		const custId = document.getElementById('cust-id').value.trim() // 공백 제거

		// 중복 확인 버튼 클릭 시 중복 확인 실행
		if (custId !== '') {
			checkDuplicate()
		} else {
			alert("아이디를 입력해주세요")
		}
	})

	function checkDuplicate() {
		const custId = document.getElementById('cust-id').value

		fetch('/checkDuplicate?custId=' + custId)
			.then(function(response) {
				return response.text()
			})
			.then(function(data) {
				const statusElement = document.getElementById('id-status')
				const custId = document.getElementById('cust-id')
				statusElement.innerText = data

				if (data.includes('사용 가능한')) {
					alert(data)
					statusElement.style.color = '#33A188'
					custId.style.borderColor = '#33A188'
					signupBtn.disabled = false // 가입 버튼 활성화

					return true

				} else {
					alert(data)
					statusElement.style.color = 'red'
					custId.style.borderColor = 'red'
					signupBtn.disabled = true // 가입 버튼 비활성화

					return false
				}
			})

			.then(function(isDuplicate) {
				isDuplicateChecked = isDuplicate
			})

			.catch(function(error) {
				console.log('에러 발생: ' + error)
			})
	}

	// 이메일 관련
	const sendCertBtn = document.querySelector('#email-cerification')
	const emailBox = document.getElementById('email-box')
	const emailDomainBox = document.getElementById('email-domain-box')
	const emailDomainList = document.getElementById('email-domain-list')

	let certCode = ''

	emailDomainBox.readOnly = false

	emailDomainList.addEventListener("change", function() {
		let selectedOption = emailDomainList.options[emailDomainList.selectedIndex].value
		if (selectedOption === "directInput") {
			emailDomainBox.readOnly = false
			emailDomainBox.value = ''
		} else {
			emailDomainBox.readOnly = true
			emailDomainBox.value = selectedOption
		}
	})

	sendCertBtn.addEventListener('click', function() {
		if (emailBox.value === '' || emailDomainBox.value === '') {
			alert("이메일을 입력해주세요")
		} else {
			checkEmailDuplicate()
		}
	})
	
	// 이메일 중복 확인
	function checkEmailDuplicate(){
		const custEmail = `${emailBox.value}@${emailDomainBox.value}`
		console.log(custEmail)
		fetch(`/checkEmailDuplicate?custEmail=${custEmail}`)
			.then(function(response) {
				return response.text()
			})
			.then(function(data) {
				if (data.includes('사용 중인')) {
					alert(data)
				} else {
					emailCertView()
					sendEmailCert()
				}
			})
			.catch(function(error) {
				console.log('에러 발생: ' + error)
			})
	}

	// 인증요청 클릭 시 인증번호 체크 입력 칸 보이게
	function emailCertView() {
		const handleEmailCertView = document.getElementById('email-cert-num')
		handleEmailCertView.style.display = 'block'
	}

	// 인증번호 요청
	function sendEmailCert() {
		const custEmail = `${emailBox.value}@${emailDomainBox.value}`
		console.log(custEmail)
		fetch(`/sendEmailCert?custEmail=${custEmail}`)
			.then(function(response) {
				return response.text()
			})
			.then(function(data) {
				certCode = data
			})
			.catch(function(error) {
				console.log('에러 발생: ' + error)
			})
	}
	
	// 인증번호 확인
	const certNumCheckBtn = document.querySelector('#cert-num-check-btn')
	const mismatchCertNum = document.querySelector('.mismatch-cert-num')
	const matchCertNum = document.querySelector('.match-cert-num')
	const certNumInputted = document.getElementById('cert-num-input')
	
	certNumCheckBtn.addEventListener('click', function(){ checkCertNum() })
	
	function checkCertNum(){
		console.log(`certNumInputted.value :: ${certNumInputted.value}, certCode :: ${certCode}`)
		if (certNumInputted.value === certCode) {
			isCheckEmailCert = true
			mismatchCertNum.classList.add('hide')
			matchCertNum.classList.remove('hide')
			certNumInputted.style.borderColor = '#33A188'
		} else {
			mismatchCertNum.classList.remove('hide')
			matchCertNum.classList.add('hide')
			certNumInputted.style.borderColor = 'red'
		}
	}
	
	// 비밀번호 확인 이벤트 발생
	const handlePasswordCheck = document.querySelector('#cust-password-check')
	const custPassword = document.getElementById('cust-password')
	const custPasswordCheck = document.getElementById('cust-password-check')
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
	
	// 전체 동의
	const handleAgreeAll = document.querySelector('#agree-all')
	const handleAgreePersonalInfo = document.querySelector('#agree-personal-info')
	const handleAgreeUniqueInfo = document.querySelector('#agree-unique-info')
	const handleAgreeServicelInfo = document.querySelector('#agree-service-terms')
	const handleAgreeTelecomInfo = document.querySelector('#agree-telecom-terms')
	
	const agreeCheckboxes  = [handleAgreePersonalInfo, handleAgreeUniqueInfo, 
								handleAgreeServicelInfo, handleAgreeTelecomInfo]
	
	handleAgreeAll.addEventListener('click', function(){
		console.log('전체동의 체크')
		
		if(handleAgreeAll.checked) {
			// 전체 동의 클릭 시 개별 동의 체크 박스 전체 체크
			agreeCheckboxes.forEach(agree => {
				console.log(agree)
				agree.checked = true
			})
		} else {
			// 전체 동의 재클릭 시 개별 동의 체크 박스 전체 해제
			agreeCheckboxes.forEach(agree => {
				console.log(agree)
				agree.checked = false
			})
		}
	})
	
	agreeCheckboxes.forEach(agree => {
	    agree.addEventListener('click', function() {
			console.log('개별동의 체크 해제')
	        // 개별 동의 체크박스 중 하나라도 체크가 해제되면 전체 동의 체크박스도 해제
	        if (!agree.checked) {
	            handleAgreeAll.checked = false
	        }
	    });
	});
	
	// 가입하기 버튼 클릭 시
	signupForm.addEventListener('submit', function(event) {
		if (!isDuplicateChecked) {
			// 중복 확인을 거치지 않고 제출 시 알림
			event.preventDefault()
			alert('중복 확인을 해주세요.')
		}
		if (!isCheckEmailCert) {
			// 이메일 인증을 거치지 않고 제출 시 알림
			event.preventDefault()
			alert('이메일을 인증해주세요.')
		}
	    if (!agreeCheckboxes.every(agree => agree.checked)) {
	        // 하나라도 동의하지 않은 경우
	        event.preventDefault();
	        alert('전체 동의해주세요.');
	    }
		})
})
