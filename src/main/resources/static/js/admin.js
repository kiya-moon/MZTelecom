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

function udateModal() {
    const selectedRows = document.querySelectorAll('.select');

    if (selectedRows.length !== 1) {
        alert('하나만 선택해 주세요.');
        return;
    }

	const selectedRow = selectedRows[0];
    const cells = selectedRow.cells;
    const modalFields = ['name', 'kor', 'capacity', 'price', 'color', 'image', 'imageDetail'];

    modalFields.forEach((field, index) => {
		const element = document.getElementById(`update-${field}`);
		
		if (field === 'image' || field === 'imageDetail') {
		    element.value = ''; // 이미지 필드 초기화
		    
		} else {
		    const inputElement = cells[index].querySelectorAll('.form_control');
		    
		    for (let i = 0; i < inputElement.length; i++) {
				if (inputElement[i].type == 'text') {
	                element.value = inputElement[i].value;
	                break;
            	}
			}
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