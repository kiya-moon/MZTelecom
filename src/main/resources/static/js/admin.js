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

function selectRow(row) {
    row.classList.toggle('selected');
}

function showModal() {
    const selectedRows = document.querySelectorAll('.selected');

    if (selectedRows.length !== 1) {
        alert('하나만 선택해 주세요.');
        return;
    }

	const selectedRow = selectedRows[0];
    const cells = selectedRow.cells;
    const modalFields = ['id', 'name', 'image', 'capacity', 'dscCapacity', 'dscPrice', 'fee', 'finalPrice'];

    modalFields.forEach((field, index) => {
		const element = document.getElementById(`update-${field}`);
		
		if (field === 'image') {
		    element.value = ''; // 이미지 필드 초기화
		} else {
		    element.value = cells[index].textContent;
		}
   });

   // 모달을 표시합니다.
   document.getElementById('adminModal').style.display = 'block';

}

function closeModal() {
    document.getElementById('adminModal').style.display = 'none';
}