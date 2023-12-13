
/* signup checkbox */
const signupForm = document.getElementById('signup-form');
const agreeAllCheckbox = document.getElementById('agreeAll');
const otherCheckboxes = document.querySelectorAll('.signup-checkbox input[type="checkbox"]');

// 다른 체크박스들의 클릭 이벤트 모니터링
otherCheckboxes.forEach(checkbox => {
    checkbox.addEventListener('click', function() {
        // 모든 체크박스가 선택되었는지 확인
        const allChecked = Array.from(otherCheckboxes).every(checkbox => checkbox.checked);

        // 전체 동의 체크박스 상태 업데이트
        agreeAllCheckbox.checked = allChecked;
    });
});

// 전체 동의 체크박스의 클릭 이벤트 처리
agreeAllCheckbox.addEventListener('click', function() {
    const isChecked = agreeAllCheckbox.checked;
    otherCheckboxes.forEach(checkbox => checkbox.checked = isChecked);
});


signupForm.addEventListener('submit', function(e) {
	if (!agreeAllCheckbox.checked) {
		e.preventDefault();
		alert('체크 박스를 모두 체크해주세요.');
	}
});

// login
function clearInput(inputId) {
    document.getElementById(inputId).value = '';
}

function togglePassword() {
    const passwordField = document.getElementById('password');
    passwordField.type = (passwordField.type === "password") ? "text" : "password";
}

// id 기억하기
function saveUsername() {
    const rememberCheckbox = document.getElementById('remember');
    const usernameInput = document.getElementById('username');

    if (rememberCheckbox.checked) {
        localStorage.setItem('savedUsername', usernameInput.value);
    } else {
        localStorage.removeItem('savedUsername');
    }
    
}


if(localStorage.getItem('savedUsername')){
	document.getElementById('username').value = localStorage.getItem('savedUsername');
	document.getElementById('remember').checked = true;
}