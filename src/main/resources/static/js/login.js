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