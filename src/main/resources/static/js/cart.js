/*장바구니 아이템 삭제*/
function deleteCartItem(id) {
    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
	const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
	
	const formData = new FormData();
	formData.append('id', id);
	
    fetch("/cart/" + id, {
        method: 'DELETE',
        headers: {
			[csrfHeader]: csrfToken,
		},
		
		body: formData
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
    })
    .then(result => {
        location.href = '/cart';
    })
    .catch(error => {
        alert(error.message);
    });
}

