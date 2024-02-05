function selectRow(row) {
    row.classList.toggle('select');
}


document.addEventListener('DOMContentLoaded', function() {
    const qnaRows = document.querySelectorAll('.clickQna');

    qnaRows.forEach(function(row) {
        row.addEventListener('click', function() {
            // 테이블 숨기기
            document.querySelector('.admin-container table').style.display = 'none';

            // 해당하는 admin-answer div 표시
            const targetId = this.getAttribute('data-target');
            document.getElementById(targetId).style.display = 'block';
        });
    });
});



function updateModal() {
    const selectedRows = document.querySelectorAll('.select');
	
    if (selectedRows.length !== 1) {
        alert('하나만 선택해 주세요.');
    }

    const selectedRow = selectedRows[0];
    const cells = selectedRow.cells;
    const modalFields = ['name', 'kor', 'capacity', 'price', 'color', 'image', 'imageDetail'];

    modalFields.forEach((field, index) => {
        const element = document.getElementById(`update-${field}`);
        const inputElements = cells[index].querySelectorAll('.form_control');

        if (field === 'image' || field === 'imageDetail') {
            const fileInput = document.getElementById(`update-${field}`);
            
            // 이미지의 alt 값을 가져오도록 수정
            const altValue = document.getElementById(`image${field === 'image' ? '' : '-detail'}`).getAttribute("alt");

            const uploadNameClass = `upload-name${field === 'image' ? '1' : '2'}`;
            document.querySelector(`.${uploadNameClass}`).value = altValue;

            fileInput.addEventListener('change', function() {
                if (fileInput.files.length > 0) {
                    // 파일이 선택되었을 때 파일 이름을 가져와서 표시
                    const fileName = fileInput.files[0].name;

                    // 동적으로 생성된 클래스에 대한 upload-name 처리
                    document.querySelector(`.${uploadNameClass}`).value = fileName;
                } else {
                    // 파일이 선택되지 않았을 때의 처리
                    document.querySelector(`.${uploadNameClass}`).value = '';
                }
            });
        } else {
            // 해당하는 필드의 모든 값을 가져와서 설정
            let values = [];
            inputElements.forEach(inputElement => {
                if (inputElement.type === 'text') {
                    values.push(inputElement.value);
                }
            });

            element.value = values.length > 1 ? values : values[0];
        }
    });

    // 모달을 표시합니다.
    document.getElementById('adminUpdateModal').style.display = 'block';
}


function closeModal() {
    document.getElementById('adminUpdateModal').style.display = 'none';
}

function addProduct() {
   document.getElementById('adminAddModal').style.display = 'block';

}

function closeAddModal() {
    document.getElementById('adminAddModal').style.display = 'none';
}

// 상품수정
function updateSave() {
	
	const selectedRows = document.querySelectorAll('.select');
	
	const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
    
	const selectedRow = selectedRows[0];
    const productId = selectedRow.querySelector('[name="id"]').value;
    
    var productName = document.getElementById('update-name').value;
    var productKorName = document.getElementById('update-kor').value;
    var productCapacity = document.getElementById('update-capacity').value;
    var productPrice = document.getElementById('update-price').value;
    var productColor = document.getElementById('update-color').value;
    var productImage = document.getElementById('update-image').files[0];
    var productImageDetail = document.getElementById('update-imageDetail').files[0];
    
    var formData = new FormData();
    formData.append('id', productId);
    formData.append('productName', productName);
    formData.append('productKorName', productKorName);
    formData.append('productCapacity', productCapacity);
    formData.append('productPrice', productPrice);
    formData.append('productColor', productColor);
    formData.append('productImage', productImage);
    formData.append('productImageDetail', productImageDetail);

    // Fetch를 이용한 서버에 데이터 전송
    fetch('/update/' + productId, {
        method: 'PUT', // 또는 다른 HTTP 메소드 사용
        headers: {
            [csrfHeader]: csrfToken,
        },
        body: formData
    })
    .then(response => response.json())
    .then(data => {
        console.log('상품 수정 완료:', data);
        location.href = '/admin?tab=product';
    })
    .catch(error => {
        console.error('상품 수정 에러:', error);
        // 오류 발생 시 에러 처리
    });
}


// 상품삭제
function deleteProduct() {
    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

    var selectedRows = document.querySelectorAll('.select');

    if (selectedRows.length === 0) {
        return alert('선택된 상품이 없습니다.');
    }

    const ids = Array.from(selectedRows).map(row => row.querySelector('input[name="id"]').value);

    fetch("/delete-multiple", {
        method: 'DELETE',
        headers: {
            [csrfHeader]: csrfToken,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ ids }),
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.text();
    })
    .then(result => {
        // 선택된 행 삭제 후 어떤 동작을 수행할지 정의
        alert(`선택된 상품이 성공적으로 삭제되었습니다.`);
        location.href = '/admin?tab=product';
    })
    .catch(error => {
        alert(`상품 삭제 중 오류 발생: ${error.message}`);
    });
}

