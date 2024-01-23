/*구매후기 카테고리*/
var revSelect = document.querySelector(".rev-select");
var revItems = document.querySelector(".rev-select-items");
var selectedCategoryInput = document.getElementById("selectedCategory");
var imgDelBtns = document.getElementsByClassName('img-del-btn');
var savedImg = document.getElementsByClassName('saved-img');


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
    // 게시판 목록 페이지로 이동
    window.location.href = '/purRevBoard';
}

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
	console.log("수정완료 버튼 클릭");
	
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
    formData.append('id', id);
    
    // 파일 input 요소에서 선택된 파일들을 FormData에 추가합니다
    var fileInput = document.querySelector('.img-attachment');
    for (var file of fileInput.files) {
        formData.append('files', file);
    }

    // CSRF 토큰을 가져옵니다
    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    // 수정된 데이터를 서버로 전송합니다
    fetch('/purRevView/' + id, {
        method: 'POST',
        headers: {
            [csrfHeader]: csrfToken,
        },
        body: formData
    })
    .then(function(response) {
		window.location.href = '/purRevBoard';
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
    var editButton		= document.getElementById('edit-button');
    var completeButton	= document.getElementById('complete-button');
    var fileGruop		= document.getElementById('file-group');


    if (isDisabled) {
        // 입력 필드가 비활성화 상태일 때는 수정 버튼 표시, 완료 버튼 숨김
        editButton.style.display		= 'inline-block';
        completeButton.style.display	= 'none';
        fileGruop.style.display			= 'none';

		// 모든 이미지 버튼을 숨김
        for (var i = 0; i < savedImg.length; i++) {
            imgDelBtns[i].style.display = 'none';
        }
        
    } 
    else
    {
        // 입력 필드가 활성화 상태일 때는 수정 완료 버튼 표시, 수정 버튼 숨김
        editButton.style.display		= 'none';
        completeButton.style.display	= 'inline-block';
        fileGruop.style.display			= 'inline-block';
        
		// 각 이미지 버튼을 표시
        for (var i = 0; i < savedImg.length; i++) {
            imgDelBtns[i].style.display = 'flex';
        }
    }
}

/*삭제 버튼 클릭시*/
function removeReview(id) {
	console.log("삭제 버튼 클릭");
	
    const formData = new FormData();
    formData.append('id', id);
    
    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    // 수정된 데이터를 서버로 전송합니다
    fetch('/purRevView/' + id + '/remove', {
        method: 'POST',
        headers: {
            [csrfHeader]: csrfToken,
        },
        body: formData,
    })
    .then(function(response) {
    	window.location.href = '/purRevBoard';
        return response.text();
    })
    .catch(function(error) {
		console.log(error);
        alert('잠시 후 다시 실행 해주세요');
    });
    
}

// 기존의 첨부된 파일 삭제
function deleteImage(imageId) {
    // 파일 삭제 로직 구현
    console.log('fileId ::: ' + imageId);
    
	var imageElement = document.querySelector('[data-id="' + imageId + '"]');
	var deleteButton = document.querySelector('[data-btn="' + imageId + '"]');
	
	imageElement.remove();
	deleteButton.remove();

    const formData = new FormData();
    formData.append('imageId', imageId);
    
    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
	
    fetch('/purRevView/tempDeleteFile', {
        method: 'POST',
        headers: {
            [csrfHeader]: csrfToken,
        },
        body: formData,
    })
    .then(function(response) {
        return response.text();
    })
    .catch(function(error) {
		console.log(error);
        alert('잠시 후 다시 실행 해주세요');
    });
}

// 파일 입력이 변경될 때 이미지 표시
function displaySelectedImages(input) {
	console.log("222");
    var imagePreviewContainer = document.getElementById('image-preview-container');
    var imgItem = document.querySelectorAll('.img-item');
    imagePreviewContainer.innerHTML = ''; // 이전에 표시된 이미지 제거
    
    console.log("imgItem ::: " + imgItem.length);
    var maxFileCount = 3;
    var imgCnt = imgItem.length;
    var selectedFileCount = input.files.length;
    var total = imgCnt + selectedFileCount

    if (total > maxFileCount) {
		
		if(!isNull(imgCnt))
		{
            alert('최대 ' + maxFileCount + '개의 파일만 선택할 수 있습니다. \n'+'(기존 첨부된 이미지 포함)');
		}
		else
		{
			alert('최대 ' + maxFileCount + '개의 파일만 선택할 수 있습니다.');
		}
        // 더 많은 파일을 선택하지 못하도록 선택된 파일 초기화
        input.value = null;
    }

    if (input.files && input.files.length > 0) {
        for (var i = 0; i < input.files.length; i++) {
            var reader = new FileReader();

            reader.onload = function (e) {
                var imgElement = document.createElement('img');
                imgElement.src = e.target.result;
                imgElement.style.width = '150px';
                imgElement.style.height = '150px';
                imagePreviewContainer.appendChild(imgElement);
            };

            reader.readAsDataURL(input.files[i]);
        }
    }
}

// 라벨을 클릭했을 때 파일 입력 실행
document.querySelector('label[for="fileInput"]').addEventListener('click', function () {
    document.getElementById('fileInput').click();
});
