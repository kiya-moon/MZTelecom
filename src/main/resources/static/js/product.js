const csrfToken = document.querySelector("meta[name='_csrf']").getAttribute("content");
const csrfHeader = document.querySelector("meta[name='_csrf_header']").getAttribute("content");

function toggleHeartColor(button, isLiked) {
    button.classList.toggle('red', isLiked);
}

function handleLikeClick(event, productId, liked) {
    event.preventDefault();

    const button = event.currentTarget;

    if (!csrfToken || !csrfHeader) {
        console.error('CSRF token or header not found!');
        return;
    }
    
    const formData = new FormData();
    formData.append('liked', liked);

    fetch('/product/' + productId + '/liked', {
        method: 'PUT',
        headers: {
            [csrfHeader]: csrfToken,
            'Content-Type': 'application/json'
        },
        
         body: JSON.stringify({ liked: liked }) 
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
    .catch(error => {
        console.error('Error:', error);
    });
}

document.addEventListener('DOMContentLoaded', function() {
    const buttons = document.querySelectorAll('.btnLike');

    buttons.forEach(button => {
        const productId = button.getAttribute('data-product-id');
        const storedLikes = JSON.parse(localStorage.getItem('likedProducts')) || {};
        const isLiked = storedLikes[productId];
		
		if(isLiked) {
            button.classList.toggle('red');
		}
		
    });
});


var radios = document.querySelectorAll('.option');
var priceDisplay = document.querySelectorAll('.priceDisplay');

radios.forEach(function(radio, index) {
    radio.addEventListener('change', function() {
        var dataValue = priceDisplay[index].getAttribute('data-value');
        var radioValue = radio.value;
        
        if (dataValue === radioValue) {
            priceDisplay[index].textContent = priceDisplay[index].textContent;
        } 
        priceDisplay[0].style.display = dataValue === '0' ? 'block' : 'none';
        priceDisplay[1].style.display = dataValue === '1' ? 'block' : 'none';
    });
});
