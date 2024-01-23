/* 클릭시 하트 색변경 */
function toggleHeartColor(button, isLiked) {
    button.classList.toggle('red', isLiked);
}

/* 찜하기 기능 */
function clickLiked(event, productId){
	
	event.preventDefault();

    const button = event.currentTarget;
	
    // CSRF 토큰을 가져옵니다
    const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
    const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

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

        selectedPriceListInput.value =  dataValue === '0' ? priceDisplay[0].textContent : priceDisplay[1].textContent;
    });
});
