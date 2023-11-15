const mainSlideSwiper = new Swiper('.main-slide', {
    loop: true,
    autoplay: {
        delay: 3000,
    },
    navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev'
    }
});

// 마우스 오버 시 자동 재생 중지
mainSlideSwiper.el.addEventListener('mouseenter', function () {
    mainSlideSwiper.autoplay.stop();
});

// 마우스 나갈 시 자동 재생 재개
mainSlideSwiper.el.addEventListener('mouseleave', function () {
    mainSlideSwiper.autoplay.start();
});


const tabSlideSwiper = new Swiper('.sub-slide', {
	loop: true,
	slidesPerView: 'auto',
	navigation: {
        nextEl: '.swiper-button-next',
        prevEl: '.swiper-button-prev'
	},
	
	 on: {
        slideChange: function() {
            const currentIndex = this.realIndex;
            activateTab(currentIndex);
        }
    }
});


function activateTab(tabIndex) {
    const tabTitles = document.querySelectorAll('.tab-title');
    
    tabTitles.forEach((tab, index) => {
      if (index === tabIndex) {
        tab.classList.add('active');
      } else {
        tab.classList.remove('active');
      }
    });
}

function changeTab(index) {
    activateTab(index);
    tabSlideSwiper.slideTo(index);
}

// 슬라이더 이전 버튼 클릭 이벤트
tabSlideSwiper.on('click', '.swiper-button-prev', function() {
    const currentIndex = tabSlideSwiper.realIndex;
    activateTab(currentIndex);
});

// 슬라이더 다음 버튼 클릭 이벤트
tabSlideSwiper.on('click', '.swiper-button-next', function() {
    const currentIndex = tabSlideSwiper.realIndex;
    activateTab(currentIndex);
});
    

