// 변수값 null 체크 isNull
function isNull(obj){
	return (typeof obj != "undefined" && obj != null && obj != "")? false : true;
}

// NVL 처리
function nvl(obj, defaultStr) {
	var rtnStr = defaultStr;
	
	if(typeof obj != "undefined"){
		rtnStr = '';
	}
	return isNull(obj) ? rtnStr : obj;
}

// 콤마찍기
function comma(str) {
	str = String(str);
	return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

// 콤마풀기
function uncomma(str) {
	str = String(str);
	return str.replace(/[^\d]+/g, '');
}

