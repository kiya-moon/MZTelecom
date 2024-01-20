const customSelect = document.querySelector(".custom-select");
const selectItems = document.querySelector(".select-items");
var selectedCategoryInput = document.getElementById("selected-category");
var qnaBtn = document.getElementById("send-qna");					// 문의 버튼
var emailChk = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*$/;					// 이메일 정규식
var emailInput = document.querySelector('#email');					// 이메일 인풋
var contentsTextarea = document.getElementById('contents');			// 문의내용
var contents;
var agreeCheckbox = document.getElementById('agree');				// 체크박스


function qnaToggleMenu(itemId) {
	var item = document.getElementById(itemId);
	item.classList.toggle("hidden");

	var icon = item.previousElementSibling.querySelector('.feather');

	if (icon) {
		icon.dataset.feather = (item.classList.contains('hidden')) ? 'plus' : 'minus';
		feather.replace(); // Feather 아이콘 새로고침
	}
}


customSelect.addEventListener("click", function() {
	selectItems.classList.toggle("active");
});

var options = selectItems.querySelectorAll("div");
options.forEach(function(option) {
	option.addEventListener("click", function() {
		var selectedValue = option.textContent;
		customSelect.querySelector(".select-selected").textContent = selectedValue;
		
		selectedCategoryInput.value = selectedValue;
	});
});

qnaBtn.addEventListener("click", (e) => {
	
	e.preventDefault();
	
	contents = contentsTextarea.value;
	
    if(isNull(selectedCategoryInput.value))
    {
		alert("카테고리를 선택해주세요.");	
	}
	else
	{
	    if (isNull(emailInput.value)) 
	    {
			alert("이메일을 입력해주세요.");
	    } 
	    else if (!emailChk.test(emailInput.value)) 
	    {
			alert("이메일을 확인해주세요.");
	    } 
	    else 
	    {
	        console.log("올바른 이메일입니다.");
	        
	        if(isNull(contents.trim()))
	        {
				alert("문의내용을 적어주세요.");
			}
			else
			{
				if (agreeCheckbox.checked) 
				{
					sendQnA();
				} 
				else 
				{
    				alert('필수 동의를 확인해주세요.');
				}
			}
	    }
	}
});

function sendQnA() {
	
    // 수정된 제목과 내용 값을 가져옵니다
    const email = document.getElementById('email').value;
    const qnaDomainList = document.getElementById('qna-domain-list').value;


    // 서버로 보낼 데이터를 FormData로 준비합니다
    const formData = new FormData();
    formData.append('selectedCategory', selectedCategoryInput.value);
    formData.append('email', email);
    formData.append('qnaDomainList', qnaDomainList);
    formData.append('contents', contents);
    
    // CSRF 토큰을 가져옵니다
    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    // 수정된 데이터를 서버로 전송합니다
    fetch('/sendQnA', {
        method: 'POST',
        headers: {
            [csrfHeader]: csrfToken,
        },
        body: formData
    })
    .then(function(response) {
		window.location.href = '/';
        return response.text();
    })
    .catch(function(error) {
        alert('잠시 후 다시 실행 해주세요');
    });
}

