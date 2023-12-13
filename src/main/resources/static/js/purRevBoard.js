/*구매후기 카테고리*/

var revSelect = document.querySelector(".rev-select");
var revItems = document.querySelector(".rev-select-items");
var selectedCategoryInput = document.getElementById("selectedCategory");


revSelect.addEventListener("click", function() {
	revItems.classList.toggle("rev-select-items-active");
});

var options = revItems.querySelectorAll("div");
options.forEach(function(option) {
	option.addEventListener("click", function() {
		var selectedValue = option.textContent;
		revSelect.querySelector(".rev-select-selected").textContent = selectedValue;
	
		selectedCategoryInput.value = selectedValue;
	});
});


/*검색시*/
function updatePage(page) {
    var keyword = document.getElementById('keyWord').value;
    var catgo = document.getElementById('catgo').value;
    var url = '/purRevBoard';
    
    if (keyword) {
        url += '?page=' + page + '&catgo=' + catgo + '&keyWord=' + keyword;
    } else {
        url += '?page=' + page;
    }

    window.location.href = url;
}

// 확인버튼 클릭시 Form 작성 체크
function validateForm() {
        // selectedCategory 값 확인
        var selectedCategory = document.getElementById("selectedCategory").value;
        if (isNull(selectedCategory)) {
            alert("카테고리를 선택하세요.");
            return false;
        }

        // title 값 확인
        var title = document.getElementsByName("title")[0].value;
        if (isNull(title.trim())) {
            alert("제목을 입력하세요.");
            return false;
        }

        // contents 값 확인
        var contents = document.getElementsByName("contents")[0].value;
        if (isNull(contents.trim())) {
            alert("내용을 입력하세요.");
            return false;
        }

        return true;
    }
    
// 취소버튼 클릭시
function goBack() {
    // 또는 특정 페이지로 이동
    window.location.href = '/purRevBoard'; // 여기에 이동하고 싶은 페이지의 경로를 지정하세요.
}

// 수정버튼 클릭시
 window.onload = function() {
        // 현재 제목과 구매내용 입력 필드의 상태를 확인
        var isDisabled = document.getElementById('rev-title-input').disabled;

        // 초기 상태에 따라 버튼을 표시
        toggleButtons(isDisabled);
    };

/* 수정 버튼을 눌렀을 때 실행되는 함수 */
function enableEdit() {
    // 제목 입력 필드를 활성화
    document.getElementById('rev-title-input').disabled = false;

    // 구매후기 내용 입력 필드를 활성화
    document.getElementById('content').disabled = false;

    // 버튼을 표시
    toggleButtons(false);
}

/* 수정 완료 버튼을 눌렀을 때 실행되는 함수 */
function completeEdit(id) {
    // 수정된 제목과 내용 값을 가져옵니다
    const updatedTitle = document.getElementById('rev-title-input').value;
    const updatedContent = document.getElementById('content').value;
    const updatedWriter = document.getElementById('writer').value;
    const updatedIntmNm = document.getElementById('intmNm').value;

    // 서버로 보낼 데이터를 FormData로 준비합니다
    const formData = new FormData();
    formData.append('boardTitle', updatedTitle);
    formData.append('boardDetail', updatedContent);
    formData.append('writer', updatedWriter);
    formData.append('intmNm', updatedIntmNm);

    // CSRF 토큰을 가져옵니다
    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    // 수정된 데이터를 서버로 전송합니다
    fetch('/purRevView/' + id, {
        method: 'POST',
        headers: {
            [csrfHeader]: csrfToken,
        },
        body: formData,
    })
    .then(function(response) {
        console.log('111111');
        return response.text();
    })
    .catch(function(error) {
        alert('잠시 후 다시 실행 해주세요');
    });

    // 입력 필드를 비활성화합니다
    document.getElementById('rev-title-input').disabled = true;
    document.getElementById('content').disabled = true;

    // 버튼 상태를 토글합니다
    toggleButtons(true);
}

/* 버튼을 표시/숨김 처리하는 함수 */
function toggleButtons(isDisabled) {
    var editButton = document.getElementById('edit-button');
    var completeButton = document.getElementById('complete-button');

    if (isDisabled) {
        // 입력 필드가 비활성화 상태일 때는 수정 버튼 표시, 완료 버튼 숨김
        editButton.style.display = 'inline-block';
        completeButton.style.display = 'none';
    } else {
        // 입력 필드가 활성화 상태일 때는 수정 완료 버튼 표시, 수정 버튼 숨김
        editButton.style.display = 'none';
        completeButton.style.display = 'inline-block';
    }
}

