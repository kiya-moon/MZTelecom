function qnaToggleMenu(itemId) {
	var item = document.getElementById(itemId);
	item.classList.toggle("hidden");

	var icon = item.previousElementSibling.querySelector('.feather');

	if (icon) {
		icon.dataset.feather = (item.classList.contains('hidden')) ? 'plus' : 'minus';
		feather.replace(); // Feather 아이콘 새로고침
	}
}

var customSelect = document.querySelector(".custom-select");
var selectItems = document.querySelector(".select-items");

customSelect.addEventListener("click", function() {
	selectItems.classList.toggle("select-items-active");
});

var options = selectItems.querySelectorAll("div");
options.forEach(function(option) {
	option.addEventListener("click", function() {
		var selectedValue = option.textContent;
		customSelect.querySelector(".select-selected").textContent = selectedValue;
	});
});