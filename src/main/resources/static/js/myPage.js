window.onload = function(){
	// 주문내역 페이지 기본 날짜 설정
	const startDate = new Date();
	const endDate = new Date();
	
	startDate.setMonth(endDate.getMonth() - 1);
	
	document.getElementById('startDate').value = startDate.toISOString().substring(0, 10);
	document.getElementById('endDate').value = endDate.toISOString().substring(0, 10);
	
}

// 3개월 버튼 클릭 시
function setThreeMonth() {
	const startDate = new Date();
	const endDate = new Date();
	
	startDate.setMonth(endDate.getMonth() - 3);
	
	document.getElementById('startDate').value = startDate.toISOString().substring(0, 10);
	document.getElementById('endDate').value = endDate.toISOString().substring(0, 10);
}

// 6개월 버튼 클릭 시
function setSixMonth() {
	const startDate = new Date();
	const endDate = new Date();
	
	startDate.setMonth(endDate.getMonth() - 6);
	
	document.getElementById('startDate').value = startDate.toISOString().substring(0, 10);
	document.getElementById('endDate').value = endDate.toISOString().substring(0, 10);
}

// 1년 버튼 클릭 시
function setOneYear() {
	const startDate = new Date();
	const endDate = new Date();
	
	startDate.setFullYear(endDate.getFullYear() - 1);
	
	document.getElementById('startDate').value = startDate.toISOString().substring(0, 10);
	document.getElementById('endDate').value = endDate.toISOString().substring(0, 10);
}

// 회원정보수정
// 핸드폰번호 수정
function phoneNumModify() {
	console.log("phone함수호출")
	const phoneModify = document.getElementById('phone-modify-container');
	phoneModify.classList.toggle("modify");
}

// 비밀번호 수정
function modifyPW() {
	console.log("pw함수호출")
	const pwModify = document.getElementById('pw-modify-container');
	pwModify.classList.toggle("modify");
}
	