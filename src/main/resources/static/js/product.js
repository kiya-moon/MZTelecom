
/* 클릭시 하트 색변경 */
function toggleHeartColor(button, isLiked) {
	button.classList.toggle('red', isLiked);
}

/* 찜하기 기능 */
function clickLiked(event, productId) {
	// CSRF 토큰을 가져옵니다
	const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
	const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

	event.preventDefault();

	const button = event.currentTarget;


	// 수정된 데이터를 서버로 전송합니다
	fetch('/product/' + productId + '/liked', {
		method: 'POST',
		headers: {
			[csrfHeader]: csrfToken,
		}
	})
		.then(response => {
			if (!response.ok) {
				throw new Error('Network response was not ok');
			}
			return response.json();
		})
		.then(data => {
			const isLiked = data.isLiked;
			const isLoggedIn = data.isLoggedIn;

			if (!isLoggedIn) {
				alert('로그인이 필요합니다!');
			} else {
				toggleHeartColor(button, isLiked);
				console.log(isLiked ? "상품이 좋아요되었습니다" : "상품 좋아요가 취소되었습니다");

				const storedLikes = JSON.parse(localStorage.getItem('likedProducts')) || {};
				storedLikes[productId] = isLiked;
				localStorage.setItem('likedProducts', JSON.stringify(storedLikes));
			}
		})
		.catch(function(error) {
			alert('잠시 후 다시 실행 해주세요');
		});


}

/* 컬러 옵션 / 용량 선택시 가격 변경 등 */
var radios = document.querySelectorAll('.option');
var priceDisplay = document.querySelectorAll('.priceDisplay');
var selectedPriceListInput = document.getElementById('selectedPriceList');

radios.forEach(function(radio, index) {
	radio.addEventListener('change', function() {
		var dataValue = priceDisplay[index].getAttribute('data-value');
		var radioValue = radio.value;


		if (dataValue === radioValue) {
			priceDisplay[index].textContent = priceDisplay[index].textContent;
		}
		priceDisplay[0].style.display = dataValue === '0' ? 'block' : 'none';
		priceDisplay[1].style.display = dataValue === '1' ? 'block' : 'none';

		selectedPriceListInput.value = dataValue === '0' ? priceDisplay[0].textContent : priceDisplay[1].textContent;
	});
});

var errMsg = document.getElementById('err-msg').value;

/* 장바구니 */
function addCart(id) {
	const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
	const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");
	
	
	const formData = new FormData();
	formData.append('id', id);
	
	console.log(id);

	fetch("/cart/add", {
		method: "POST",
		headers: {
			[csrfHeader]: csrfToken,
		},
		body: formData
	})
		.then(response => {
		    if (!response.ok) {
		        if (response.status === 401) { // Unauthorized 응답일 때
		            alert("로그인 후 이용해주세요");
		            location.href = '/login';
		        } else if (response.status === 400) { // Bad Request 응답일 때
		            response.text().then(errorMessage => {
		                alert(errorMessage); // 서버에서 반환된 오류 메시지 표시
		            });
		        } else {
		            console.error("서버 오류 상세 정보:", response.status);
		            alert("오류가 발생했습니다: 서버에서 " + response.status + " 응답이 왔습니다.");
		        }
		        throw new Error(response.status); // 에러 throw
		    }
		    return response.json(); // 정상 응답의 경우 JSON 데이터를 읽어옴
		})
		.catch(error => {
		    console.error("클라이언트 오류 상세 정보:", error);
		});
}

