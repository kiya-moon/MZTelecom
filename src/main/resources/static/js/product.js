const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

function toggleHeartColor(button) {
	button.classList.toggle('red');
}

function handleLikeClick(event, productId, liked) {
	event.preventDefault();

	const button = event.currentTarget;
	toggleHeartColor(button);

	if (!csrfToken || !csrfHeader) {
		console.error('CSRF token or header not found!');
		return;
	}

	fetch('/product/' + productId + '/like', {
		method: 'PUT',
		headers: {
			[csrfHeader]: csrfToken,
			'Content-Type': 'application/json'
		}
	})
		.then(response => {
			if (!response.ok) {
				toggleHeartColor(button); // 실패 시 다시 원래 색상으로 되돌림
			}
			return response.json(); // 여기서 서버 응답을 JSON 형태로 파싱
		})
		.then(data => {
			const isLoggedIn = data.isLoggedIn; // 서버에서 전달한 로그인 상태 필드
			// 이후 작업: 로그인 상태에 따른 동작 수행
			if (!isLoggedIn) {
				alert('로그인이 필요합니다!');
				toggleHeartColor(button);
			}
		})
		.catch(error => {
			console.error('Error:', error);
			toggleHeartColor(button); // 에러 발생 시 다시 원래 색상으로 되돌림
		});
}